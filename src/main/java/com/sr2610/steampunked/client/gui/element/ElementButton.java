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

import java.awt.Color;
import java.util.List;

import com.sr2610.steampunked.api.utils.StringHelper;
import com.sr2610.steampunked.client.gui.GuiBase;
import com.sr2610.steampunked.client.gui.RenderHelper;

public class ElementButton extends ElementBase {

	int sheetX;
	int sheetY;
	int hoverX;
	int hoverY;
	int disabledX = 0;
	int disabledY = 0;
	boolean disabled = false;
	boolean tooltipLocalized = false;
	String tooltip;
	private String text;

	public ElementButton(GuiBase gui, int posX, int posY, String name,
			int sheetX, int sheetY, int hoverX, int hoverY, int sizeX,
			int sizeY, String texture,String text) {

		super(gui, posX, posY);
		setName(name);
		setSize(sizeX, sizeY);
		setTexture(texture, texW, texH);
		this.sheetX = sheetX;
		this.sheetY = sheetY;
		this.hoverX = hoverX;
		this.hoverY = hoverY;
		this.text=text;
	}

	public ElementButton(GuiBase gui, int posX, int posY, String name,
			int sheetX, int sheetY, int hoverX, int hoverY, int disabledX,
			int disabledY, int sizeX, int sizeY, String texture,String text) {

		super(gui, posX, posY);
		setName(name);
		setSize(sizeX, sizeY);
		setTexture(texture, texW, texH);
		this.sheetX = sheetX;
		this.sheetY = sheetY;
		this.hoverX = hoverX;
		this.hoverY = hoverY;
		this.disabledX = disabledX;
		this.disabledY = disabledY;
		this.text=text;

	}

	public ElementButton clearToolTip() {

		tooltip = null;
		return this;
	}

	public ElementButton setToolTip(String tooltip) {

		this.tooltip = tooltip;
		return this;
	}

	public ElementButton setToolTipLocalized(boolean localized) {

		tooltipLocalized = localized;
		return this;
	}

	@Override
	public void draw() {

		RenderHelper.bindTexture(texture);
		if (!disabled) {
			if (intersectsWith(gui.getMouseX(), gui.getMouseY()))
				drawTexturedModalRect(posX, posY, hoverX, hoverY, sizeX, sizeY);
			else
				drawTexturedModalRect(posX, posY, sheetX, sheetY, sizeX, sizeY);
		} else
			drawTexturedModalRect(posX, posY, disabledX, disabledY, sizeX,
					sizeY);
		
		gui.drawCenteredString(gui.guiFontRenderer, text, (posX+ gui.guiFontRenderer.getStringWidth(text)-10), posY+4, Color.WHITE.hashCode());
		
	}

	@Override
	public void addTooltip(List<String> list) {

		if (tooltip != null)
			if (tooltipLocalized)
				list.add(tooltip);
			else
				list.add(StringHelper.localize(tooltip));
	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		if (!disabled) {
			gui.handleElementButtonClick(getName(), mouseButton);
			return true;
		}
		return false;
	}

	public void setSheetX(int pos) {

		sheetX = pos;
	}

	public void setSheetY(int pos) {

		sheetY = pos;
	}

	public void setHoverX(int pos) {

		hoverX = pos;
	}

	public void setHoverY(int pos) {

		hoverY = pos;
	}

	public void setActive() {

		disabled = false;
	}

	public void setDisabled() {

		disabled = true;
	}

}
