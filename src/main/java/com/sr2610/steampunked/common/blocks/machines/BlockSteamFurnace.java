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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFurnace extends BlockContainer {

	private final Random furnaceRand = new Random();
	private final boolean isActive;
	private static boolean keepFurnaceInventory = false;

	@SideOnly(Side.CLIENT)
	private IIcon furnaceIconTop;
	@SideOnly(Side.CLIENT)
	private IIcon furnaceIconFront;
	@SideOnly(Side.CLIENT)
	private IIcon furnaceIconBottom;

	public BlockSteamFurnace(Boolean isOn, Material material) {
		super(material);

		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);

		isActive = isOn;

		if (!isOn)
			setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntitySteamFurnace();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int i, float f, float g, float t) {
		final TileEntity tile_entity = world.getTileEntity(x, y, z);
		if (tile_entity == null || player.isSneaking())
			return false;
		else
			player.openGui(Steampunked.instance, 1, world, x, y, z);
		return true;

	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		if (keepFurnaceInventory == false) {
			final TileEntitySteamFurnace tileentityfurnace = (TileEntitySteamFurnace) world
					.getTileEntity(x, y, z);

			if (tileentityfurnace != null) {
				for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					final ItemStack itemstack = tileentityfurnace
							.getStackInSlot(i1);

					if (itemstack != null) {
						final float f = furnaceRand.nextFloat() * 0.8F + 0.1F;
						final float f1 = furnaceRand.nextFloat() * 0.8F + 0.1F;
						final float f2 = furnaceRand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int j1 = furnaceRand.nextInt(21) + 10;

							if (j1 > itemstack.stackSize)
								j1 = itemstack.stackSize;

							itemstack.stackSize -= j1;
							final EntityItem entityitem = new EntityItem(world,
									x + f, y + f1, z + f2, new ItemStack(
											itemstack.getItem(), j1,
											itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
								entityitem.getEntityItem().setTagCompound(
										(NBTTagCompound) itemstack
												.getTagCompound().copy());

							final float f3 = 0.05F;
							entityitem.motionX = (float) furnaceRand
									.nextGaussian() * f3;
							entityitem.motionY = (float) furnaceRand
									.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) furnaceRand
									.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, i);
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
			return furnaceIconFront;
		else if (side == 1)
			return furnaceIconTop;
		else if (side == 0)
			return furnaceIconBottom;
		else if (side == meta)
			return furnaceIconFront;
		else
			return blockIcon;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Reference.ModID + ":furnace");
		furnaceIconFront = par1IconRegister
				.registerIcon(isActive ? Reference.ModID + ":furnaceFront_on"
						: Reference.ModID + ":furnaceFront_off");

		furnaceIconTop = par1IconRegister.registerIcon(Reference.ModID
				+ ":furnace_top");
		furnaceIconBottom = par1IconRegister.registerIcon(Reference.ModID
				+ ":machine");
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

	public static void updateFurnaceBlockState(boolean p_149931_0_,
			World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_) {
		final int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_,
				p_149931_4_);
		final TileEntity tileentity = p_149931_1_.getTileEntity(p_149931_2_,
				p_149931_3_, p_149931_4_);
		keepFurnaceInventory = true;

		if (p_149931_0_)
			p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_,
					ModBlocks.steamFurnaceActive);
		else
			p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_,
					ModBlocks.steamFurnace);

		keepFurnaceInventory = false;
		p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_,
				p_149931_4_, l, 2);

		if (tileentity != null) {
			tileentity.validate();
			p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_,
					tileentity);
		}
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_,
			int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.steamFurnace);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ModBlocks.steamFurnace);
	}

}
