package com.sr2610.steampunked.common.blocks.slate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlate extends Block {
	public String textureName;

	public BlockSlate(String name) {
		super(Material.rock);
		textureName = name;
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeStone);

	}

	private IIcon blockIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return blockIcon;

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Reference.ModID + ":"
				+ textureName);

	}

}
