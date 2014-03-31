/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.blocks.slate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.sr2610.steampunked.core.tabs.ModCreativeTab;
import com.sr2610.steampunked.lib.LibNames;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlate extends Block {
	public String textureName;

	public BlockSlate(String name) {
		super(Material.rock);
		textureName = name;
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeStone);
		setBlockName(LibNames.SLATE + "." + textureName);

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
