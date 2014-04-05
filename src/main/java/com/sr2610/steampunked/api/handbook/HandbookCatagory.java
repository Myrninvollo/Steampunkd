/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.api.handbook;

import java.util.ArrayList;
import java.util.List;

	public final class HandbookCatagory {

		public final String unlocalizedName;
		public final List<HandbookEntry> entries = new ArrayList<HandbookEntry>();

		/**
		 * @param unlocalizedName The unlocalized name of this category. This will be localized by the client display.
		 */
		public HandbookCatagory(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}

		public String getUnlocalizedName() {
			return unlocalizedName;
		}
	}

