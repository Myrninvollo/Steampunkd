/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as part of the *
 * Steampunk'd Mod. Get the Source Code in Github: *
 * https://github.com/SR2610/Steampunkd * * Steampunk'd is Open Source and
 * distributed under a Creative Commons * Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.element;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.client.gui.GuiBase;

public abstract class ElementBase {

	protected GuiBase gui;
	protected ResourceLocation texture;

	protected int posX;
	protected int posY;

	protected int sizeX;
	protected int sizeY;

	public int texW = 256;
	public int texH = 256;

	protected String name;

	protected boolean visible = true;

	public ElementBase(GuiBase gui, int posX, int posY) {

		this.gui = gui;
		this.posX = gui.getGuiLeft() + posX;
		this.posY = gui.getGuiTop() + posY;
	}

	public ElementBase setName(String name) {

		this.name = name;
		return this;
	}

	public ElementBase setPosition(int posX, int posY) {

		this.posX = gui.getGuiLeft() + posX;
		this.posY = gui.getGuiTop() + posY;
		return this;
	}

	public ElementBase setSize(int sizeX, int sizeY) {

		this.sizeX = sizeX;
		this.sizeY = sizeY;
		return this;
	}

	public ElementBase setTexture(String texture, int texW, int texH) {

		this.texture = new ResourceLocation(texture);
		this.texW = texW;
		this.texH = texH;
		return this;
	}

	public ElementBase setVisible(boolean visible) {

		this.visible = visible;
		return this;
	}

	public boolean isVisible() {

		return visible;
	}

	public void update() {

	}

	public abstract void draw();

	public void draw(int x, int y) {

		posX = x;
		posY = y;
		draw();
	}

	public void addTooltip(List<String> list) {

	}

	public void drawTexturedModalRect(int x, int y, int u, int v, int width,
			int height) {

		gui.drawSizedTexturedModalRect(x, y, u, v, width, height, texW, texH);
	}

	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		return false;
	}

	public boolean intersectsWith(int mouseX, int mouseY) {

		mouseX += gui.getGuiLeft();
		mouseY += gui.getGuiTop();

		if (mouseX >= posX && mouseX <= posX + sizeX && mouseY >= posY
				&& mouseY <= posY + sizeY)
			return true;
		return false;
	}

	public String getName() {

		return name;
	}

}
