/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
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
