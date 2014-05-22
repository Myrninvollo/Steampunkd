package com.sr2610.steampunked.common.handbook;

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
		for (final HandbookPage page : pages)
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
