/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.handbook;

import com.sr2610.steampunked.api.SteampunkedAPI;
import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.api.handbook.HandbookPage;

public class SPHandbookEntry extends HandbookEntry {

	public SPHandbookEntry(String unlocalizedName, HandbookCatagory category) {
		super(unlocalizedName, category);
		SteampunkedAPI.addEntry(this, category);
	}

	@Override
	public HandbookEntry setHandbookPages(HandbookPage... pages) {
		for (HandbookPage page : pages)
			page.unlocalizedName = "steampunked.page."
					+ getLazyUnlocalizedName() + page.unlocalizedName;

		return super.setHandbookPages(pages);
	}

	@Override
	public String getUnlocalizedName() {
		return "steampunked.entry." + super.getUnlocalizedName();
	}

	public String getLazyUnlocalizedName() {
		return super.getUnlocalizedName();
	}

}
