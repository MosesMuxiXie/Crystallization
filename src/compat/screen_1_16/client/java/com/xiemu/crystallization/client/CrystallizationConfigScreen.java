package com.xiemu.crystallization.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public final class CrystallizationConfigScreen extends Screen {
	private static final int BUTTON_WIDTH = 200;
	private final Screen parent;

	public CrystallizationConfigScreen(Screen parent) {
		super(new TranslatableComponent("screen.crystallization.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int left = this.width / 2 - BUTTON_WIDTH / 2;
		int firstRow = this.height / 2 - 34;
		this.addButton(new Button(left, firstRow, BUTTON_WIDTH, 20, toggleText(), button -> {
			CrystallizationClient.setEnabled(!CrystallizationConfig.isEnabled(), false);
			button.setMessage(toggleText());
		}));
		this.addButton(new Button(left, firstRow + 24, BUTTON_WIDTH, 20,
				CrystallizationClient.shortcutText(),
				button -> this.minecraft.setScreen(new ControlsScreen(this, this.minecraft.options))));
		this.addButton(new Button(left, firstRow + 60, BUTTON_WIDTH, 20,
				CommonComponents.GUI_DONE, button -> this.onClose()));
	}

	private Component toggleText() {
		return new TranslatableComponent("option.crystallization.enabled")
				.append(": ")
				.append(CrystallizationConfig.isEnabled()
						? CommonComponents.OPTION_ON
						: CommonComponents.OPTION_OFF);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		drawCenteredString(poseStack, this.font, this.title,
				this.width / 2, this.height / 2 - 66, 0xFFFFFFFF);
		drawCenteredString(poseStack, this.font,
				new TranslatableComponent("option.crystallization.alt_hint"),
				this.width / 2, this.height / 2 + 13, 0xFFA0A0A0);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}
}
