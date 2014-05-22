/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.blocks.pipes;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sr2610.steampunked.api.utils.Vector3fMax;
import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityPipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPipe extends BlockContainer {

	private static Vector3fMax blockBounds = new Vector3fMax(0.25f, 0.25f,
			0.25f, 0.75f, 0.75f, 0.75f);

	public BlockPipe(Material mat) {
		super(mat);
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(LibNames.PIPE);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		final TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityPipe) {
			final TileEntityPipe tileG = (TileEntityPipe) tileEntity;

			float minX = blockBounds.getXMin();
			float minY = blockBounds.getYMin();
			float minZ = blockBounds.getZMin();
			float maxX = blockBounds.getXMax();
			float maxY = blockBounds.getYMax();
			float maxZ = blockBounds.getZMax();

			if (tileG.isPipeConnected(ForgeDirection.UP))
				maxY = 1.0F;
			if (tileG.isPipeConnected(ForgeDirection.DOWN))
				minY = 0.0F;

			if (tileG.isPipeConnected(ForgeDirection.WEST))
				minX = 0.0F;
			if (tileG.isPipeConnected(ForgeDirection.EAST))
				maxX = 1.0F;

			if (tileG.isPipeConnected(ForgeDirection.NORTH))

				minZ = 0.0F;
			if (tileG.isPipeConnected(ForgeDirection.SOUTH))

				maxZ = 1.0F;

			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
		final TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityPipe) {

			final float minX = blockBounds.getXMin();
			final float minY = blockBounds.getYMin();
			final float minZ = blockBounds.getZMin();
			final float maxX = blockBounds.getXMax();
			final float maxY = blockBounds.getYMax();
			final float maxZ = blockBounds.getZMax();

			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
			super.addCollisionBoxesToList(world, x, y, z, axisalignedbb,
					arraylist, par7Entity);
			final TileEntity tile1 = world.getTileEntity(x, y, z);
			if (tile1 instanceof TileEntityPipe) {
				final TileEntityPipe tileG = (TileEntityPipe) tile1;

				if (tileG.isPipeConnected(ForgeDirection.EAST)) {
					setBlockBounds(0.0F, minY, minZ, maxX, maxY, maxZ);
					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}

				if (tileG.isPipeConnected(ForgeDirection.WEST)) {
					setBlockBounds(minX, minY, minZ, 1.0F, maxY, maxZ);
					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}

				if (tileG.isPipeConnected(ForgeDirection.SOUTH)) {
					setBlockBounds(minX, minY, minZ, maxX, maxY, 1.0F);

					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}
				if (tileG.isPipeConnected(ForgeDirection.NORTH)) {
					setBlockBounds(minX, minY, 0.0F, maxX, maxY, maxZ);
					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}
				if (tileG.isPipeConnected(ForgeDirection.UP)) {
					setBlockBounds(minX, minY, minZ, maxX, 1.0F, maxZ);
					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}
				if (tileG.isPipeConnected(ForgeDirection.DOWN)) {
					setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
					super.addCollisionBoxesToList(world, x, y, z,
							axisalignedbb, arraylist, par7Entity);
				}
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPipe();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Reference.ModID + ":pipe");
	}

}
