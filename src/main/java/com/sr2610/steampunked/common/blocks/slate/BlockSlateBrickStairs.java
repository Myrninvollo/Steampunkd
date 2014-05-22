package com.sr2610.steampunked.common.blocks.slate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;

import com.sr2610.steampunked.common.lib.LibNames;

public class BlockSlateBrickStairs extends BlockStairs {

	public BlockSlateBrickStairs(Block block) {
		super(block, 0);
		setBlockName(LibNames.SLATE + ".brickStairs");
		setLightOpacity(0);
		setCreativeTab(CreativeTabs.tabBlock);

	}
}
