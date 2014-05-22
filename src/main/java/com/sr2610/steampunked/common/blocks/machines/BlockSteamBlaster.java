/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBlaster;

public class BlockSteamBlaster extends BlockContainer {

	public static final int[] faceToSide = new int[] { 1, 0, 3, 2, 5, 4 };

	public IIcon iconSide;

	public BlockSteamBlaster() {
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(LibNames.BLASTER);

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int meta) {
		return new TileEntitySteamBlaster();
	}

	public static int getOrientation(int meta) {
		return meta & 7;
	}

	@Override
	public IIcon getIcon(int par1, int par2) {
		int var1 = getOrientation(par2);

		if (var1 > 5)
			return blockIcon;

		if (par1 == var1)
			return blockIcon;
		else
			return par1 == faceToSide[var1] ? iconSide : iconSide;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack) {
		int rotation = BlockPistonBase.determineOrientation(world, x, y, z,
				player);

		world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Reference.ModID + ":blaster");
		iconSide = par1IconRegister.registerIcon(Reference.ModID + ":machine");
	}

	@Override
	public int getRenderType() {
		return 16;
	}

}
