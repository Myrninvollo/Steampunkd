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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBoiler;

public class BlockSteamBoiler extends BlockContainer {

	public BlockSteamBoiler(Material par2Material) {
		super(par2Material);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(LibNames.BOILER);;

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int i, float f, float g, float t) {
		final TileEntity tile_entity = world.getTileEntity(x, y, z);

		if (tile_entity == null || player.isSneaking())
			return false;
		else
			player.openGui(Steampunked.instance, 2, world, x, y, z);
		return true;

	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block i, int j) {

		dropItems(world, x, y, z);

		super.breakBlock(world, x, y, z, i, j);
	}

	private void dropItems(World world, int x, int y, int z) {
		final Random rand = new Random();

		final TileEntity tile_entity = world.getTileEntity(x, y, z);

		if (!(tile_entity instanceof IInventory))
			return;

		final IInventory inventory = (IInventory) tile_entity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			final ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				final float rx = rand.nextFloat() * 0.6F + 0.1F;
				final float ry = rand.nextFloat() * 0.6F + 0.1F;
				final float rz = rand.nextFloat() * 0.6F + 0.1F;

				final EntityItem entity_item = new EntityItem(world, x + rx, y
						+ ry, z + rz, new ItemStack(item.getItem(),
						item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound())
					entity_item.writeToNBT((NBTTagCompound) item
							.getTagCompound().copy());

				final float factor = 0.5F;

				entity_item.motionX = rand.nextGaussian() * factor;
				entity_item.motionY = rand.nextGaussian() * factor + 0.2F;
				entity_item.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entity_item);
				item.stackSize = 0;
			}
		}
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntitySteamBoiler();
	}

}
