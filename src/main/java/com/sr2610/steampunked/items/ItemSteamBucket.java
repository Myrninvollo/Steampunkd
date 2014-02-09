package com.sr2610.steampunked.items;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.core.tabs.ModCreativeTab;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteamBucket extends Item implements IFluidContainerItem {

	public ItemSteamBucket() {
		super();
		this.maxStackSize = 1;
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon("steampunked:steam_bucket");
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		return new FluidStack(ModBlocks.steam, 1000);
	}

	@Override
	public int getCapacity(ItemStack container) {
		return 1000;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		return null;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		boolean flag = true;
		MovingObjectPosition movingobjectposition = this
				.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
						flag);

		if (movingobjectposition == null) {
			return par1ItemStack;
		} else {
			FillBucketEvent event = new FillBucketEvent(par3EntityPlayer,
					par1ItemStack, par2World, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return par1ItemStack;
			}

			if (event.getResult() == Event.Result.ALLOW) {
				if (par3EntityPlayer.capabilities.isCreativeMode) {
					return par1ItemStack;
				}

				if (--par1ItemStack.stackSize <= 0) {
					return event.result;
				}

				if (!par3EntityPlayer.inventory
						.addItemStackToInventory(event.result)) {
					par3EntityPlayer.dropPlayerItemWithRandomChoice(
							event.result, false);
				}

				return par1ItemStack;
			}
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)) {
					return par1ItemStack;
				}

				if (flag) {
					if (!par3EntityPlayer.canPlayerEdit(i, j, k,
							movingobjectposition.sideHit, par1ItemStack)) {
						return par1ItemStack;
					}

					Material material = par2World.getBlock(i, j, k)
							.getMaterial();
					int l = par2World.getBlockMetadata(i, j, k);

					if (material == Material.water && l == 0) {
						par2World.setBlockToAir(i, j, k);
						return this.canPlace(par1ItemStack, par3EntityPlayer,
								Items.water_bucket);
					}

				} else {
					if (!par3EntityPlayer.canPlayerEdit(i, j, k,
							movingobjectposition.sideHit, par1ItemStack)) {
						return par1ItemStack;
					}

					if (this.tryPlaceContainedLiquid(par2World, i, j, k)
							&& !par3EntityPlayer.capabilities.isCreativeMode) {
						return new ItemStack(Items.bucket);
					}
				}
			}

			return par1ItemStack;
		}
	}

	private ItemStack canPlace(ItemStack p_150910_1_, EntityPlayer p_150910_2_,
			Item p_150910_3_) {
		if (p_150910_2_.capabilities.isCreativeMode) {
			return p_150910_1_;
		} else if (--p_150910_1_.stackSize <= 0) {
			return new ItemStack(p_150910_3_);
		} else {
			if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(
					p_150910_3_))) {
				p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(
						p_150910_3_, 1, 0), false);
			}

			return p_150910_1_;
		}
	}

	/**
	 * Attempts to place the liquid contained inside the bucket.
	 */
	public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3,
			int par4)

	{
		Material material = par1World.getBlock(par2, par3, par4).getMaterial();
		boolean flag = !material.isSolid();

		if (!par1World.isAirBlock(par2, par3, par4) && !flag) {
			return false;
		} else {

			if (!par1World.isRemote && flag && !material.isLiquid()) {
				par1World.func_147480_a(par2, par3, par4, true);
			}

			par1World.setBlock(par2, par3, par4, ModBlocks.BlockFluidSteam, 0,
					3);
		}

		return true;
	}

}
