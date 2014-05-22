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

import com.sr2610.steampunked.client.gui.element.TabBase;

public class TabTracker {

	private static Class<? extends TabBase> openedLeftTab;
	private static Class<? extends TabBase> openedRightTab;

	public static Class<? extends TabBase> getOpenedLeftTab() {

		return openedLeftTab;
	}

	public static Class<? extends TabBase> getOpenedRightTab() {

		return openedRightTab;
	}

	public static void setOpenedLeftTab(Class<? extends TabBase> tabClass) {

		openedLeftTab = tabClass;
	}

	public static void setOpenedRightTab(Class<? extends TabBase> tabClass) {

		openedRightTab = tabClass;
	}

}
