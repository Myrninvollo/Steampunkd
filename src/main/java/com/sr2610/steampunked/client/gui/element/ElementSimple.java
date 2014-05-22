/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.element;

import com.sr2610.steampunked.client.gui.GuiBase;
import com.sr2610.steampunked.client.gui.RenderHelper;

public class ElementSimple extends ElementBase {

	int texU = 0;
	int texV = 0;

	public ElementSimple(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
	}

	public ElementSimple setTextureOffsets(int u, int v) {

		texU = u;
		texV = v;
		return this;
	}

	@Override
	public void draw() {

		if (!visible)
			return;
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, texU, texV, sizeX, sizeY);
	}

}
