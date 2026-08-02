package com.xiemu.crystallization.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.xiemu.crystallization.Crystallization;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class CrystallizationClient implements ClientModInitializer {
	public static final KeyMapping TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(
			new KeyMapping("key.crystallization.toggle", InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_F, "key.category.crystallization.general")
	);

	private static boolean shortcutWasDown;

	@Override
	public void onInitializeClient() {
		CrystallizationConfig.load();
		Crystallization.setClientEnabled(CrystallizationConfig.isEnabled());

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean shortcutDown = TOGGLE_KEY.isDown() && Screen.hasAltDown();
			if (shortcutDown && !shortcutWasDown && client.player != null && client.screen == null) {
				setEnabled(!CrystallizationConfig.isEnabled(), true);
			}
			shortcutWasDown = shortcutDown;
		});

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> syncEnabledState());
	}

	public static void setEnabled(boolean enabled, boolean showMessage) {
		CrystallizationConfig.setEnabled(enabled);
		Crystallization.setClientEnabled(enabled);
		syncEnabledState();

		if (showMessage) {
			Minecraft client = Minecraft.getInstance();
			if (client.player != null) {
				String key = enabled ? "message.crystallization.enabled" : "message.crystallization.disabled";
				client.player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.GREEN), true);
			}
		}
	}

	public static Component shortcutText() {
		return Component.translatable("option.crystallization.shortcut", TOGGLE_KEY.getTranslatedKeyMessage());
	}

	public static boolean handleKeyboardShortcut(int key, int scanCode, int modifiers) {
		Minecraft client = Minecraft.getInstance();
		if (client.screen != null || client.player == null
				|| (modifiers & GLFW.GLFW_MOD_ALT) == 0
				|| !TOGGLE_KEY.matches(key, scanCode)) {
			return false;
		}

		setEnabled(!CrystallizationConfig.isEnabled(), true);
		return true;
	}

	private static void syncEnabledState() {
		if (ClientPlayNetworking.canSend(Crystallization.TOGGLE_CHANNEL)) {
			FriendlyByteBuf buffer = PacketByteBufs.create();
			buffer.writeBoolean(CrystallizationConfig.isEnabled());
			ClientPlayNetworking.send(Crystallization.TOGGLE_CHANNEL, buffer);
		}
	}
}
