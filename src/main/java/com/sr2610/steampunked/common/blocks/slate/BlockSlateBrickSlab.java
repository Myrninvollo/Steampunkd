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

public class BlockSlateBrickSlab extends BlockSlab {

	public BlockSlateBrickSlab(boolean par2) {
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
		return ModBlocks.slateBrick.getBlockTextureFromSide(par1);
	}

	@Override
	public ItemStack createStackedBlock(int par1) {
		return new ItemStack(ModBlocks.slateBrick);
	}

	@Override
	public String func_150002_b(int i) {
		return "tile." + LibNames.SLATE + "BrickSlab";
	}
}
