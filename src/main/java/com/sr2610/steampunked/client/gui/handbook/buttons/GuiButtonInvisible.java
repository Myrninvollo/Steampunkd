/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.handbook.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonInvisible extends GuiButton {

	public GuiButtonInvisible(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		field_146123_n = (par2 >= xPosition) && (par3 >= yPosition)
				&& (par2 < (xPosition + width))
				&& (par3 < (yPosition + height));
		final int k = getHoverState(field_146123_n);

		final boolean unicode = par1Minecraft.fontRenderer.getUnicodeFlag();
		par1Minecraft.fontRenderer.setUnicodeFlag(true);
		par1Minecraft.fontRenderer.drawString(displayString, xPosition
				+ (k == 2 ? 5 : 0), yPosition + ((height - 8) / 2), 0);
		par1Minecraft.fontRenderer.setUnicodeFlag(unicode);
	}

}
