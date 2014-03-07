package com.sr2610.steampunked.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.sr2610.steampunked.core.tabs.ModCreativeTab;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMod extends Block {

	public String textureName;

	protected BlockMod(Material material, String name, int harvestLevel,
			String harvestTool) {
		super(material);
		textureName = name;
		this.setHarvestLevel(harvestTool, harvestLevel);
		setCreativeTab(ModCreativeTab.INSTANCE);

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