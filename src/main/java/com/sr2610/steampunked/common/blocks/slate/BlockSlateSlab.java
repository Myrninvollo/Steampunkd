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

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.lib.LibNames;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlateSlab extends BlockSlab {

	public BlockSlateSlab(boolean par2) {
		super(par2, Material.rock);
		setHardness(0.8F);
		setResistance(10F);

		if (!par2) {
			setLightOpacity(0);
			setCreativeTab(CreativeTabs.tabBlock);
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return ModBlocks.slate.getBlockTextureFromSide(par1);
	}

	@Override
	public ItemStack createStackedBlock(int par1) {
		return new ItemStack(ModBlocks.slate);
	}

	@Override
	public String func_150002_b(int i) {
		return "tile." + LibNames.SLATE + "Slab";
	}

}
