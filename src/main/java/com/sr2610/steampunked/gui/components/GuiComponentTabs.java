/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [8 Mar 2014, 19:40:36 (GMT)]
 */
package com.sr2610.steampunked.gui.components;

import net.minecraft.client.Minecraft;

public class GuiComponentTabs extends BaseComponent {

	protected GuiComponentTab activeTab;

	public GuiComponentTabs(int x, int y) {
		super(x, y);
	}

	@Override
	public BaseComponent addComponent(BaseComponent component) {
		super.addComponent(component);
		if (component instanceof GuiComponentTab)
			((GuiComponentTab) component).setContainer(this);
		return this;
	}

	@Override
	public void render(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY) {
		super.render(minecraft, offsetX, offsetY, mouseX, mouseY);
		int oY = 0;
		for (BaseComponent component : components)
			if (component instanceof GuiComponentTab) {
				component.setY(oY);
				oY += ((GuiComponentTab) component).getHeight() - 1;
			}
	}

	public void onTabClicked(GuiComponentTab tab) {
		if (tab != activeTab) {
			if (activeTab != null)
				activeTab.setActive(false);
			tab.setActive(true);
			activeTab = tab;
		} else {
			tab.setActive(false);
			activeTab = null;
		}
	}

	@Override
	public int getWidth() {
		int maxWidth = 0;
		for (BaseComponent component : components)
			if (component.getX() + component.getWidth() > maxWidth)
				maxWidth = component.getX() + component.getWidth();
		return maxWidth;
	}

	@Override
	public int getHeight() {
		int maxHeight = 0;
		for (BaseComponent component : components)
			if (component.getY() + component.getHeight() > maxHeight)
				maxHeight = component.getY() + component.getHeight();
		return maxHeight;
	}

}