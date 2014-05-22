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

public class ElementDualScaled extends ElementBase {

	public int quantity;
	public int mode;
	public boolean background = true;

	public ElementDualScaled(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
	}

	public ElementDualScaled setBackground(boolean background) {

		this.background = background;
		return this;
	}

	public ElementDualScaled setMode(int mode) {

		this.mode = mode;
		return this;
	}

	public ElementDualScaled setQuantity(int quantity) {

		this.quantity = quantity;
		return this;
	}

	@Override
	public void draw() {

		if (!visible)
			return;
		RenderHelper.bindTexture(texture);

		if (background)
			drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
		switch (mode) {
		case 0:
			// vertical bottom -> top
			drawTexturedModalRect(posX, posY + sizeY - quantity, sizeX, sizeY
					- quantity, sizeX, quantity);
			return;
		case 1:
			// horizontal left -> right
			drawTexturedModalRect(posX, posY, sizeX, 0, quantity, sizeY);
			return;
		case 2:
			// horizontal right -> left
			drawTexturedModalRect(posX + sizeX - quantity, posY, sizeX + sizeX
					- quantity, 0, quantity, sizeY);
			return;
		}
	}

}
