/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.api.utils.IconRegistry;

public abstract class GuiBaseAdv extends GuiBase {

	public static final String TEX_ARROW_LEFT = GuiProps.PATH_ELEMENTS
			+ "Progress_Arrow_Left.png";
	public static final String TEX_ARROW_RIGHT = GuiProps.PATH_ELEMENTS
			+ "Progress_Arrow_Right.png";

	public static final String TEX_FLAME = GuiProps.PATH_ELEMENTS
			+ "Scale_Flame.png";
	public static final String TEX_SNOWFLAKE = GuiProps.PATH_ELEMENTS
			+ "Scale_Snowflake.png";

	public static final String TEX_TANK = GuiProps.PATH_ELEMENTS
			+ "FluidTank.png";

	public static final int PROGRESS = 24;
	public static final int SPEED = 16;

	public GuiBaseAdv(Container container, ResourceLocation texture) {

		super(container, texture);
	}

	/* HELPERS */
	@Override
	public void drawButton(IIcon icon, int x, int y, int spriteSheet, int mode) {

		switch (mode) {
		case 0:
			drawIcon(IconRegistry.getIcon("IconButton"), x, y, 1);
			break;
		case 1:
			drawIcon(IconRegistry.getIcon("IconButtonHighlight"), x, y, 1);
			break;
		default:
			drawIcon(IconRegistry.getIcon("IconButtonInactive"), x, y, 1);
			break;
		}
		drawIcon(icon, x, y, spriteSheet);
	}

	@Override
	public IIcon getIcon(String name) {

		return IconRegistry.getIcon(name);
	}

}
