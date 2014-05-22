package com.sr2610.steampunked.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemGenericBlock extends ItemBlock {

	public static final String[] names = new String[] { "oreCopper", "oreTin",
			"storageCopper", "storageTin" };

	public ItemGenericBlock(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {

		return getUnlocalizedName() + "." + names[itemstack.getItemDamage()];
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}
}
