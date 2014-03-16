package com.sr2610.steampunked.core.handlers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public final class CraftingHandler {

	public static void init() {
		initSmelting();

		GameRegistry
				.addShapelessRecipe(new ItemStack(Items.apple, 1, 0),
						new ItemStack(ModItems.hammer, 1,
								OreDictionary.WILDCARD_VALUE), new ItemStack(
								Items.iron_ingot));
	}

	public static void initSmelting() {

		GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(
				ModItems.copperIngot), 0.7F);
	}

	@SubscribeEvent
	public void craftingStuff(ItemCraftedEvent event) {
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				if (event.craftMatrix.getStackInSlot(i) != null) {
					ItemStack j = event.craftMatrix.getStackInSlot(i);
					if (j.getItem() != null && j.getItem() == ModItems.hammer) {
						ItemStack k = new ItemStack(ModItems.hammer, 2,
								(j.getItemDamage() + 1));
						if (k.getItemDamage() >= k.getMaxDamage()) {
							k.stackSize--;
						}
						event.craftMatrix.setInventorySlotContents(i, k);
					}
				}
		}
	}

}