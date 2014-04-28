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

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.client.gui.handbook.GuiHandbook;

public class GuiButtonPage extends GuiButton {

	boolean right;

	public GuiButtonPage(int par1, int par2, int par3, boolean right) {
		super(par1, par2, par3, 18, 10, "");
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (enabled) {
			field_146123_n = par2 >= xPosition && par3 >= yPosition
					&& par2 < xPosition + width && par3 < yPosition + height;
			final int k = getHoverState(field_146123_n);

			par1Minecraft.renderEngine.bindTexture(GuiHandbook.texture);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			drawTexturedModalRect(xPosition, yPosition, k == 2 ? 18 : 0,
					right ? 180 : 190, 18, 10);

			/*
			 * if(k == 2) RenderHelper.renderTooltip(par2, par3,
			 * Arrays.asList(StatCollector.translateToLocal(right ?
			 * "botaniamisc.nextPage" : "botaniamisc.prevPage")));
			 */}
	}

}
