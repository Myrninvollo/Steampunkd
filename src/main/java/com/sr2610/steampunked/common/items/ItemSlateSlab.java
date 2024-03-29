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
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

import com.sr2610.steampunked.common.blocks.ModBlocks;

public class ItemSlateSlab extends ItemSlab {

	public ItemSlateSlab(Block par1) {
		super(par1, (BlockSlab) ModBlocks.slateSlab,
				(BlockSlab) ModBlocks.slateSlabDouble, false);

	}

}
