package com.xiemu.crystallization.client.mixin;

import com.xiemu.crystallization.client.CrystallizationClient;
import net.minecraft.client.KeyboardHandler;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {
	@Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
	private void crystallization$handleShortcut(
			long window, int key, int scanCode, int action, int modifiers, CallbackInfo callback
	) {
		if (action == GLFW.GLFW_PRESS
				&& CrystallizationClient.handleKeyboardShortcut(key, scanCode, modifiers)) {
			callback.cancel();
		}
	}
}
