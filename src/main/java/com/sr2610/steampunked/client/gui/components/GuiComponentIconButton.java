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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiComponentIconButton extends GuiComponentButton {

	private ResourceLocation texture;
	private final IIcon icon;

	public GuiComponentIconButton(int x, int y, int color, IIcon icon) {
		super(x, y, icon.getIconWidth() + 4, icon.getIconHeight() + 4, color);
		this.icon = icon;
	}

	public GuiComponentIconButton(int x, int y, int color, IIcon icon,
			ResourceLocation texture) {
		this(x, y, color, icon);
		this.texture = texture;
	}

	@Override
	public void renderContents(Minecraft minecraft, int offsetX, int offsetY,
			int mouseX, int mouseY, boolean pressed) {
		if (texture != null)
			minecraft.renderEngine.bindTexture(texture);

		drawTexturedModelRectFromIcon(offsetX + x, offsetY + y, icon,
				icon.getIconWidth(), icon.getIconHeight());
	}

}
