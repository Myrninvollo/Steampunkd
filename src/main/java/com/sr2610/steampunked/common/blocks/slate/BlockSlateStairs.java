package com.sr2610.steampunked.common.blocks.slate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;

import com.sr2610.steampunked.common.lib.LibNames;

public class BlockSlateStairs extends BlockStairs {

	public BlockSlateStairs(Block block) {
		super(block, 0);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockName(LibNames.SLATE + ".stairs");
		setLightOpacity(0);

	}

}
