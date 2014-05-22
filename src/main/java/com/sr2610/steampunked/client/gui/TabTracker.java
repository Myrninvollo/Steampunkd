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
