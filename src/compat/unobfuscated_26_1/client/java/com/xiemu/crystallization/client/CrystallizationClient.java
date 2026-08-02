package com.xiemu.crystallization.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.xiemu.crystallization.Crystallization;
import com.xiemu.crystallization.network.TogglePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class CrystallizationClient implements ClientModInitializer {
	public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(Crystallization.MOD_ID, "general")
	);
	public static final KeyMapping TOGGLE_KEY = KeyMappingHelper.registerKeyMapping(
			new KeyMapping("key.crystallization.toggle", InputConstants.Type.KEYSYM,
					InputConstants.KEY_F, KEY_CATEGORY)
	);
	private static final int NEON_GREEN = 0x39FF14;
	private static boolean shortcutWasDown;

	@Override
	public void onInitializeClient() {
		CrystallizationConfig.load();
		Crystallization.setClientEnabled(CrystallizationConfig.isEnabled());
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean shortcutDown = TOGGLE_KEY.isDown() && client.hasAltDown();
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
				client.player.sendOverlayMessage(Component.translatable(key).withColor(NEON_GREEN));
			}
		}
	}

	public static Component shortcutText() {
		return Component.translatable("option.crystallization.shortcut", TOGGLE_KEY.getTranslatedKeyMessage());
	}

	public static boolean handleKeyboardShortcut(KeyEvent event) {
		Minecraft client = Minecraft.getInstance();
		if (client.screen != null || client.player == null
				|| !event.hasAltDown() || !TOGGLE_KEY.matches(event)) {
			return false;
		}
		setEnabled(!CrystallizationConfig.isEnabled(), true);
		return true;
	}

	private static void syncEnabledState() {
		if (ClientPlayNetworking.canSend(TogglePayload.TYPE)) {
			ClientPlayNetworking.send(new TogglePayload(CrystallizationConfig.isEnabled()));
		}
	}
}
