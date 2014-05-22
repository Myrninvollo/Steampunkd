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
