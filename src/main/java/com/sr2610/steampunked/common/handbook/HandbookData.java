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
				CraftingHandler.recipeWrench));

		hammer = new SPHandbookEntry("hammer", categoryBasics);
		hammer.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeHammer));

		pistonBoots = new SPHandbookEntry("pistonBoots", categoryArmor);
		pistonBoots.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipePistonBoots));

		mechBoots = new SPHandbookEntry("mechBoots", categoryArmor);
		mechBoots.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipeMechBoots));
		jetpack = new SPHandbookEntry("jetpack", categoryArmor);
		jetpack.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeJetpack));
		goggles = new SPHandbookEntry("goggles", categoryArmor);
		goggles.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeGoggles));

		sabers = new SPHandbookEntry("sabers", categoryItems);
		sabers.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeSabers));

		slate = new SPHandbookEntry("slate", categoryBlocks);
		slate.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.slateSlab), new PageCraftingRecipe("2",
				CraftingHandler.slateStairs), new PageCraftingRecipe("3",
				CraftingHandler.slateBrick), new PageCraftingRecipe("4",
				CraftingHandler.slateBrickSlab), new PageCraftingRecipe("4",
				CraftingHandler.slateBrickStairs));

		// MACHINES//

		pipes = new SPHandbookEntry("pipes", categoryMachines);
		pipes.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipePipe));

		injector = new SPHandbookEntry("injector", categoryMachines);
		injector.setHandbookPages(new PageText("0"), new PageCraftingRecipe(
				"1", CraftingHandler.recipeInjector));

		blaster = new SPHandbookEntry("steamBlaster", categoryMachines);
		blaster.setHandbookPages(new PageText("0"), new PageCraftingRecipe("1",
				CraftingHandler.recipeBlaster));

	}

}
