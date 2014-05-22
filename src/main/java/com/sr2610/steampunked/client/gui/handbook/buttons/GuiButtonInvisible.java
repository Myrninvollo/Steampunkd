package com.sr2610.steampunked.client.gui.handbook.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonInvisible extends GuiButton {

	public GuiButtonInvisible(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		field_146123_n = par2 >= xPosition && par3 >= yPosition
				&& par2 < xPosition + width && par3 < yPosition + height;
		final int k = getHoverState(field_146123_n);

		final FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		final boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);
		String stringDisplay;
		if (k == 2)
			stringDisplay = "\u00a71" + displayString + "\u00a70";
		else
			stringDisplay = displayString;
		renderer.drawString(stringDisplay, xPosition, yPosition + (height - 8)
				/ 2, 0);
		par1Minecraft.fontRenderer.setUnicodeFlag(unicode);
	}

}
