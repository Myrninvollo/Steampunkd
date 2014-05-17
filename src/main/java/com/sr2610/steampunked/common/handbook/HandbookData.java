/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.handbook;

import com.sr2610.steampunked.api.SteampunkedAPI;
import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.common.handbook.pages.PageCraftingRecipe;
import com.sr2610.steampunked.common.handbook.pages.PageText;
import com.sr2610.steampunked.common.handlers.CraftingHandler;

public final class HandbookData {

	public static HandbookCatagory categoryBasics;
	public static HandbookCatagory categoryMachines;
	public static HandbookCatagory categoryAutomotons;
	public static HandbookCatagory categoryBlocks;
	public static HandbookCatagory categoryItems;
	public static HandbookCatagory categoryArmor;

	public static HandbookEntry steam;
	public static HandbookEntry wrench;
	public static HandbookEntry hammer;
	public static HandbookEntry pistonBoots;
	public static HandbookEntry mechBoots;
	public static HandbookEntry slate;
	public static HandbookEntry jetpack;
	public static HandbookEntry goggles;
	public static HandbookEntry sabers;
	public static HandbookEntry pipes;
	public static HandbookEntry blaster;

	public static HandbookEntry injector;

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

		steam = new SPHandbookEntry("steam", categoryBasics);
		steam.setPriority().setHandbookPages(new PageText("0"));

		wrench = new SPHandbookEntry("wrench", categoryBasics);
		wrench.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeWrench, "wrench"));

		hammer = new SPHandbookEntry("hammer", categoryBasics);
		hammer.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeHammer, "hammer"));

		pistonBoots = new SPHandbookEntry("pistonBoots", categoryArmor);
		pistonBoots.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipePistonBoots, "pistonBoots"));

		mechBoots = new SPHandbookEntry("mechBoots", categoryArmor);
		mechBoots.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipeMechBoots, "mechBoots"));
		jetpack = new SPHandbookEntry("jetpack", categoryArmor);
		jetpack.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeJetpack, "jepack"));
		goggles = new SPHandbookEntry("goggles", categoryArmor);
		goggles.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeGoggles, "goggles"));

		sabers = new SPHandbookEntry("sabers", categoryItems);
		sabers.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeSabers, "sabers"));

		slate = new SPHandbookEntry("slate", categoryBlocks);
		slate.setHandbookPages(
				new PageText("0"),
				new PageCraftingRecipe("1", CraftingHandler.slateSlab, "slate"),
				new PageCraftingRecipe("2", CraftingHandler.slateStairs,
						"slate"),
				new PageCraftingRecipe("3", CraftingHandler.slateBrick, "slate"),
				new PageCraftingRecipe("4", CraftingHandler.slateBrickSlab,
						"slate"), new PageCraftingRecipe("4",
						CraftingHandler.slateBrickStairs, "slate"));

		// MACHINES//

		pipes = new SPHandbookEntry("pipes", categoryMachines);
		pipes.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipePipe, "pipes"));

		injector = new SPHandbookEntry("injector", categoryMachines);
		injector.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipeInjector, "injector"));
		
		blaster = new SPHandbookEntry("steamBlaster", categoryMachines);
		blaster.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipeBlaster, "steamBlaster"));

	}

}
