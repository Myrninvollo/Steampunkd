package com.sr2610.steampunked.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGeneric extends Block {

	public static final String[] names = new String[] { "oreCopper", "oreTin",
			"storageCopper", "storageTin" };

	public BlockGeneric() {
		super(Material.rock);
		setBlockName(LibNames.GENERICBLOCK);
		setCreativeTab(CreativeTabs.tabBlock);
		blockHardness = 3.0F;

	}

	@Override
	public int damageDropped(int par1) {
		return par1;
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[names.length + 1];

		for (int i = 0; i < names.length; i++)
			icons[i] = par1IconRegister.registerIcon(Reference.ModID + ":"
					+ names[i]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int i = 0; i < names.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z,
			int beaconX, int beaconY, int beaconZ) {
		int meta = worldObj.getBlockMetadata(x, y, z);
		if (meta == 2 || meta == 3)
			return true;
		else
			return false;
	}

	@Override
	public String getHarvestTool(int metadata) {
		return "pickaxe";
	}

	@Override
	public int getHarvestLevel(int metadata) {
		return 1;
	}
}
