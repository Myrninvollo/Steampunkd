/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
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
