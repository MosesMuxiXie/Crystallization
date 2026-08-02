package com.xiemu.crystallization.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class CrystallizationConfigScreen extends Screen {
	private static final int BUTTON_WIDTH = 200;
	private final Screen parent;

	public CrystallizationConfigScreen(Screen parent) {
		super(Component.translatable("screen.crystallization.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int left = this.width / 2 - BUTTON_WIDTH / 2;
		int firstRow = this.height / 2 - 34;
		this.addRenderableWidget(CycleButton.onOffBuilder(CrystallizationConfig.isEnabled())
				.create(left, firstRow, BUTTON_WIDTH, 20,
						Component.translatable("option.crystallization.enabled"),
						(button, enabled) -> CrystallizationClient.setEnabled(enabled, false)));
		this.addRenderableWidget(Button.builder(CrystallizationClient.shortcutText(),
				button -> this.minecraft.setScreen(new KeyBindsScreen(this, this.minecraft.options)))
				.bounds(left, firstRow + 24, BUTTON_WIDTH, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
				.bounds(left, firstRow + 60, BUTTON_WIDTH, 20).build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.title,
				this.width / 2, this.height / 2 - 66, 0xFFFFFFFF);
		graphics.drawCenteredString(this.font,
				Component.translatable("option.crystallization.alt_hint"),
				this.width / 2, this.height / 2 + 13, 0xFFA0A0A0);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}
}
