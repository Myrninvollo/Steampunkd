/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.components;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

public abstract class GuiComponentButton extends GuiComponentBox {

	protected boolean buttonEnabled = true;

	public GuiComponentButton(int x, int y, int width, int height, int color) {
		super(x, y, width, height, 0, 10, color);
	}

	public void setButtonEnabled(boolean enabled) {
		buttonEnabled = enabled;
	}

	public boolean isButtonEnabled() {
		return buttonEnabled;
	}

	@Override
	public void render(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY) {
		final boolean pressed = isMouseOver(mouseX, mouseY)
				&& Mouse.isButtonDown(0);
		u = buttonEnabled ? pressed ? 20 : 0 : 40;
		super.render(minecraft, offsetX, offsetY, mouseX, mouseY);
		renderContents(minecraft, offsetX, offsetY, mouseX, mouseY, pressed);
	}

	protected abstract void renderContents(Minecraft minecraft, int offsetX,
			int offsetY, int mouseX, int mouseY, boolean pressed);

}
