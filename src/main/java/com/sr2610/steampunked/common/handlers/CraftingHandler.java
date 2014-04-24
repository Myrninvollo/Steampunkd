/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.handlers;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.sr2610.steampunked.api.SteampunkedAPI;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.lib.LibOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public final class CraftingHandler {
	public static IRecipe recipeSpanner;
	public static IRecipe recipeHammer;
	public static List<IRecipe> recipeSabers;
	public static IRecipe recipePistonBoots;
	public static IRecipe recipeMechBoots;
	public static IRecipe recipeJetpack;
	public static IRecipe recipeInjector;

	public static IRecipe slateSlab;
	public static IRecipe slateStairs;
	public static IRecipe slateBrick;
	public static IRecipe slateBrickSlab;
	public static IRecipe slateBrickStairs;
	public static IRecipe recipeGoggles;
	public static IRecipe recipePipe;

	public static void init() {
		initSmelting();
		// addShapelessOreDictRecipe(new ItemStack(ModItems.handBook),
		// "ingotTin", Items.book);

		addOreDictRecipe(new ItemStack(ModBlocks.pipe), "ICI", "ICI", 'I',
				"ingotIron",'C',new ItemStack(ModItems.craftingItems, 1, 0));
		recipePipe = SteampunkedAPI.getLatestAddedRecipe();
		addOreDictRecipe(new ItemStack(ModItems.spanner), "x x", " x ", " x ",
				'x', "ingotIron");
		recipeSpanner = SteampunkedAPI.getLatestAddedRecipe();

		addOreDictRecipe(new ItemStack(ModItems.hammer), "xxx", "xxx", " s ",
				's', "stickWood", 'x', "ingotIron");
		recipeHammer = SteampunkedAPI.getLatestAddedRecipe();

		addOreDictRecipe(new ItemStack(ModItems.boots, 1, 250), "xbx", "pcp",
				'x', "ingotIron", 'b', new ItemStack(Items.iron_boots), 'p',
				new ItemStack(Blocks.piston), 'c', new ItemStack(
						ModItems.craftingItems, 1, 0));
		recipePistonBoots = SteampunkedAPI.getLatestAddedRecipe();
		addOreDictRecipe(new ItemStack(ModItems.mechBoots, 1, 1500), "xbx",
				"cpc", 'x', "ingotIron", 'b', new ItemStack(ModItems.boots, 1,
						250), 'p', new ItemStack(Blocks.piston), 'c',
				new ItemStack(ModItems.craftingItems, 1, 0));
		recipeMechBoots = SteampunkedAPI.getLatestAddedRecipe();
		addOreDictRecipe(new ItemStack(ModItems.jetpack, 1,
				LibOptions.jetpackCapacity), "cxc", "xpx", 'x', "ingotIron",
				'p', new ItemStack(Items.iron_chestplate), 'c', new ItemStack(
						ModItems.craftingItems, 1, 0));
		recipeJetpack = SteampunkedAPI.getLatestAddedRecipe();
		addOreDictRecipe(new ItemStack(ModItems.mechBoots, 1, 1500), "xbx",
				"cpc", 'x', "ingotIron", 'b', new ItemStack(ModItems.boots, 1,
						OreDictionary.WILDCARD_VALUE), 'p', new ItemStack(
						Blocks.piston), 'c', new ItemStack(
						ModItems.craftingItems, 1, 0));

		addOreDictRecipe(new ItemStack(ModItems.saberWood, 1), "  g", "ig ",
				"ss ", 's', "stickWood", 'g', "plankWood", 'i', "ingotIron");
		addOreDictRecipe(new ItemStack(ModItems.saberStone, 1), "  g", "ig ",
				"ss ", 's', "stickWood", 'g', "cobblestone", 'i', "ingotIron");
		addOreDictRecipe(new ItemStack(ModItems.saberIron, 1), "  g", "ig ",
				"ss ", 's', "stickWood", 'g', "ingotIron", 'i', "ingotIron");
		addOreDictRecipe(new ItemStack(ModItems.saberGold, 1), "  g", "ig ",
				"ss ", 's', "stickWood", 'g', "ingotGold", 'i', "ingotIron");
		addOreDictRecipe(new ItemStack(ModItems.saberDiamond, 1), "  g", "ig ",
				"ss ", 's', "stickWood", 'g', "gemDiamond", 'i', "ingotIron");
		recipeSabers = SteampunkedAPI.getLatestAddedRecipes(5);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.slateBrick, 4), "xx",
				"xx", 'x', new ItemStack(ModBlocks.slate));
		slateBrick = SteampunkedAPI.getLatestAddedRecipe();
		GameRegistry.addRecipe(new ItemStack(ModBlocks.slateSlab, 6), "xxx",
				'x', new ItemStack(ModBlocks.slate));
		slateSlab = SteampunkedAPI.getLatestAddedRecipe();

		GameRegistry.addRecipe(new ItemStack(ModBlocks.slateStair, 4), "x  ",
				"xx ", "xxx", 'x', new ItemStack(ModBlocks.slate));
		slateStairs = SteampunkedAPI.getLatestAddedRecipe();

		GameRegistry.addRecipe(new ItemStack(ModBlocks.slateBrickSlab, 6),
				"xxx", 'x', new ItemStack(ModBlocks.slateBrick));
		slateBrickSlab = SteampunkedAPI.getLatestAddedRecipe();

		GameRegistry.addRecipe(new ItemStack(ModBlocks.slateBrickStair, 4),
				"x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.slateBrick));
		slateBrickStairs = SteampunkedAPI.getLatestAddedRecipe();

		addOreDictRecipe(new ItemStack(ModItems.goggles), "LGL", "L L", "PCP",
				'G', "ingotGold", 'L', new ItemStack(Items.leather), 'P',
				new ItemStack(Blocks.glass_pane), 'C', "ingotCopper");
		recipeGoggles = SteampunkedAPI.getLatestAddedRecipe();
	}

	public static void initSmelting() {

		GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(
				ModItems.copperIngot), 0.7F);
		GameRegistry.addSmelting(ModBlocks.oreTin, new ItemStack(
				ModItems.tinIngot), 0.7F);
	}

	@SubscribeEvent
	public void craftingStuff(ItemCraftedEvent event) {
		for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
			if (event.craftMatrix.getStackInSlot(i) != null) {
				ItemStack j = event.craftMatrix.getStackInSlot(i);
				if (j.getItem() != null && j.getItem() == ModItems.hammer) {
					ItemStack k = new ItemStack(ModItems.hammer, 2,
							j.getItemDamage() + 1);
					if (k.getItemDamage() >= k.getMaxDamage())
						k.stackSize--;
					event.craftMatrix.setInventorySlotContents(i, k);
				}
			}
	}

	private static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapedOreRecipe(output, recipe));
	}

	private static void addShapelessOreDictRecipe(ItemStack output,
			Object... recipe) {
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(output, recipe));
	}

}
