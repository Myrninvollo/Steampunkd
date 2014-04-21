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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.StatCollector;

public class HandbookEntry implements Comparable<HandbookEntry> {

	public final String unlocalizedName;
	public final HandbookCatagory category;

	public List<HandbookPage> pages = new ArrayList<HandbookPage>();
	private boolean priority = false;

	/**
	 * @param unlocalizedName
	 *            The unlocalized name of this entry. This will be localized by
	 *            the client display.
	 */
	public HandbookEntry(String unlocalizedName, HandbookCatagory category) {
		this.unlocalizedName = unlocalizedName;
		this.category = category;
	}

	/**
	 * Sets this page as prioritized, as in, will appear before others in the
	 * lexicon.
	 */
	public HandbookEntry setPriority() {
		priority = true;
		return this;
	}

	public boolean isPriority() {
		return priority;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	/**
	 * Sets what pages you want this entry to have.
	 */
	public HandbookEntry setHandbookPages(HandbookPage... pages) {
		this.pages.addAll(Arrays.asList(pages));

		for (int i = 0; i < this.pages.size(); i++)
			this.pages.get(i).onPageAdded(this, i);

		return this;
	}

	/**
	 * Adds a page to the list of pages.
	 */
	public void addPage(HandbookPage page) {
		pages.add(page);
	}

	public final String getNameForSorting() {
		return (priority ? 0 : 1)
				+ StatCollector.translateToLocal(getUnlocalizedName());
	}

	@Override
	public int compareTo(HandbookEntry o) {
		return getNameForSorting().compareTo(o.getNameForSorting());
	}
}
