/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.handbook;

import com.sr2610.steampunked.api.SteampunkedAPI;
import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.core.handlers.CraftingHandler;
import com.sr2610.steampunked.handbook.pages.PageCraftingRecipe;
import com.sr2610.steampunked.handbook.pages.PageText;

public final class HandbookData {

	public static HandbookCatagory categoryBasics;
	public static HandbookCatagory categoryMachines;
	public static HandbookCatagory categoryAutomotons;
	public static HandbookCatagory categoryBlocks;
	public static HandbookCatagory categoryItems;
	public static HandbookCatagory categoryArmor;

	public static HandbookEntry spanner;
 public static HandbookEntry hammer;

	public static void init() {
		SteampunkedAPI.addCategory(categoryBasics = new HandbookCatagory(
				"The Basics"));
		SteampunkedAPI.addCategory(categoryMachines = new HandbookCatagory(
				"Machines"));
		SteampunkedAPI.addCategory(categoryBlocks = new HandbookCatagory(
				"Blocks"));
		SteampunkedAPI
		.addCategory(categoryItems = new HandbookCatagory("Items"));
		SteampunkedAPI
		.addCategory(categoryArmor = new HandbookCatagory("Armor"));
		SteampunkedAPI.addCategory(categoryAutomotons = new HandbookCatagory(
				"Automotons"));

		spanner = new SPHandbookEntry("spanner", categoryBasics);
		spanner.setPriority().setHandbookPages(new PageText("0"),
				new PageCraftingRecipe("1", CraftingHandler.recipeSpanner));
		
		hammer = new SPHandbookEntry("hammer", categoryBasics);
		hammer.setHandbookPages(new PageText("0"),
				new PageCraftingRecipe("1", CraftingHandler.recipeHammer));

	}

}
