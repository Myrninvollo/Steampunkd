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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBoiler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamBoiler extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon frontIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;

	private final boolean isActive = false;
	private final boolean hasWater = false;

	public BlockSteamBoiler(Material par2Material) {
		super(par2Material);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(LibNames.BOILER);
		;

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
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntitySteamBoiler();
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setMeta(world, x, y, z);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0 && side == 3)
			return frontIcon;
		else if (side == 1)
			return topIcon;
		else if (side == 0)
			return bottomIcon;
		else if (side == meta)
			return frontIcon;
		else
			return blockIcon;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Reference.ModID + ":machine");
		if (hasWater)
			if (isActive)
				frontIcon = par1IconRegister.registerIcon(Reference.ModID
						+ ":boiler_front_on_full");
			else
				frontIcon = par1IconRegister.registerIcon(Reference.ModID
						+ ":boiler_front_off_full");
		else
			frontIcon = par1IconRegister.registerIcon(Reference.ModID
					+ ":boiler_front_off_empty");

		topIcon = par1IconRegister
				.registerIcon(Reference.ModID + ":machineTop");
		bottomIcon = par1IconRegister.registerIcon(Reference.ModID
				+ ":machineBottom");
	}

	private void setMeta(World world, int x, int y, int z) {
		if (!world.isRemote) {
			final Block block = world.getBlock(x, y, z - 1);
			final Block block1 = world.getBlock(x, y, z + 1);
			final Block block2 = world.getBlock(x - 1, y, z);
			final Block block3 = world.getBlock(x + 1, y, z);
			byte b0 = 3;

			if (block.func_149730_j() && !block1.func_149730_j())
				b0 = 3;

			if (block1.func_149730_j() && !block.func_149730_j())
				b0 = 2;

			if (block2.func_149730_j() && !block3.func_149730_j())
				b0 = 5;

			if (block3.func_149730_j() && !block2.func_149730_j())
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack itemstack) {
		final int l = MathHelper
				.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if (l == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if (l == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if (l == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);

	}

}
