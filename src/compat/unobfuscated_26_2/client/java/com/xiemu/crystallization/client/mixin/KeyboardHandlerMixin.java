package com.xiemu.crystallization.client.mixin;

import com.xiemu.crystallization.client.CrystallizationClient;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {
	@Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
	private void crystallization$handleShortcut(
			long window, int action, KeyEvent event, CallbackInfo callback
	) {
		if (action == GLFW.GLFW_PRESS && CrystallizationClient.handleKeyboardShortcut(event)) {
			callback.cancel();
		}
	}
}
