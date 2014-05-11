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

public class GuiComponentTextButton extends GuiComponentButton {

	private String text;

	public GuiComponentTextButton(int x, int y, int width, int height,
			int color, String text) {
		super(x, y, width, height, color);
		this.text = text;
	}

	public GuiComponentTextButton(int x, int y, int width, int height, int color) {
		this(x, y, width, height, color, "");
	}

	public GuiComponentTextButton setText(String buttonText) {
		text = buttonText;
		return this;
	}

	@Override
	public void renderContents(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY, boolean pressed) {
		final int textWidth = minecraft.fontRenderer.getStringWidth(text);
		int offX = (width - textWidth) / 2 + 1;
		int offY = 3;
		if (buttonEnabled && pressed) {
			offY++;
			offX++;
		}
		minecraft.fontRenderer.drawString(text, offsetX + x + offX, offsetY + y
				+ offY, 4210752);
	}

}