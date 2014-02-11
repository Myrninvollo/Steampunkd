package com.sr2610.steampunked.core.handlers;

import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;

public final class CraftingHandler {

	public static void init() {

	}

	public static void initSmelting() {

		GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(
				ModItems.copperIngot), 0.7F);

	}
}
