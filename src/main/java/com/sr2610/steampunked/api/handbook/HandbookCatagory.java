package com.sr2610.steampunked.api.handbook;

import java.util.ArrayList;
import java.util.List;

public final class HandbookCatagory {

	public final String unlocalizedName;
	public final List<HandbookEntry> entries = new ArrayList<HandbookEntry>();

	/**
	 * @param unlocalizedName
	 *            The unlocalized name of this category. This will be localized
	 *            by the client display.
	 */
	public HandbookCatagory(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
}
