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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public final class HandbookRecipeMappings {

	private static Map<String, EntryData> mappings = new HashMap();

	/**
	 * Maps the given stack to the given page of the entry.
	 */
	public static void map(ItemStack stack, HandbookEntry entry, int page,
			boolean force) {
		final EntryData data = new EntryData(entry, page);
		final String str = stackToString(stack);

		if (force || !mappings.containsKey(str))
			mappings.put(str, data);
	}

	public static void map(ItemStack stack, HandbookEntry entry, int page) {
		map(stack, entry, page, false);
	}

	public static EntryData getDataForStack(ItemStack stack) {
		return mappings.get(stackToString(stack));
	}

	public static String stackToString(ItemStack stack) {
		if (stack.hasTagCompound()
				&& stack.getItem() instanceof IRecipeKeyProvider)
			return ((IRecipeKeyProvider) stack.getItem()).getKey(stack);

		return stack.getUnlocalizedName() + "~" + stack.getItemDamage();
	}

	public static class EntryData {

		public final HandbookEntry entry;
		public final int page;

		public EntryData(HandbookEntry entry, int page) {
			this.entry = entry;
			this.page = page;
		}

	}
}
