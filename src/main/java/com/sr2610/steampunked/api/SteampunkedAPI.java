package com.sr2610.steampunked.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.api.handbook.HandbookEntry;

public final class SteampunkedAPI {

	private static List<HandbookCatagory> categories = new ArrayList<HandbookCatagory>();
	private static List<HandbookEntry> allEntries = new ArrayList<HandbookEntry>();

	/**
	 * Adds a category to the list of registered categories to appear in the
	 * Handbook
	 */
	public static void addCategory(HandbookCatagory category) {
		categories.add(category);
	}

	/**
	 * Gets all registered categories.
	 */
	public static List<HandbookCatagory> getAllCategories() {
		return categories;
	}

	/**
	 * Registers a Lexicon Entry and adds it to the category passed in.
	 */
	public static void addEntry(HandbookEntry entry, HandbookCatagory category) {
		allEntries.add(entry);
		category.entries.add(entry);
	}

	/**
	 * Gets the last recipe to have been added to the recipe list.
	 */
	public static IRecipe getLatestAddedRecipe() {
		final List<IRecipe> list = CraftingManager.getInstance()
				.getRecipeList();
		return list.get(list.size() - 1);
	}

	/**
	 * Gets the last x recipes added to the recipe list.
	 */
	public static List<IRecipe> getLatestAddedRecipes(int x) {
		final List<IRecipe> list = CraftingManager.getInstance()
				.getRecipeList();
		final List<IRecipe> newList = new ArrayList();
		for (int i = x - 1; i >= 0; i--)
			newList.add(list.get(list.size() - 1 - i));

		return newList;
	}

}
