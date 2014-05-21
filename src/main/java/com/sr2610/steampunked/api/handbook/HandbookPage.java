/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.api.handbook;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class HandbookPage {
	public String unlocalizedName;

	public HandbookPage(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	/**
	 * Does the rendering for this page.
	 * 
	 * @param gui
	 *            The active GuiScreen
	 * @param mx
	 *            The mouse's relative X position.
	 * @param my
	 *            The mouse's relative Y position.
	 */
	@SideOnly(Side.CLIENT)
	public abstract void renderScreen(IGuiHandbookEntry gui, int mx, int my);

	/**
	 * Called per update tick.
	 */
	@SideOnly(Side.CLIENT)
	public void updateScreen() {
		// NO-OP
	}

	public void onPageAdded(HandbookEntry entry, int index) {
		// NO-OP
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

}
