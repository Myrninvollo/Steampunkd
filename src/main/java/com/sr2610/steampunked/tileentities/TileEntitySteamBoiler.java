/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.tileentities;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.lib.LibOptions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySteamBoiler extends TileEntityMachine implements
		IFluidHandler, ISidedInventory {

	static private final int NETDATAID_INPUT_TANK_FLUID = 1;
	static private final int NETDATAID_INPUT_TANK_AMOUNT = 2;

	static private final int NETDATAID_OUTPUT_TANK_FLUID = 3;
	static private final int NETDATAID_OUTPUT_TANK_AMOUNT = 4;

	static public final int TANK_INPUT = 0;
	static public final int TANK_OUTPUT = 1;
	public int boilerBurnTime;

	private FluidTank[] tanks;
	private FluidTankInfo[] tank_info;

	public TileEntitySteamBoiler() {
		super();
		int i;
		tanks = new FluidTank[2];
		tank_info = new FluidTankInfo[2];
		for (i = 0; i < 2; i++) {
			tanks[i] = new FluidTank(LibOptions.boilerCapacity);
			tank_info[i] = new FluidTankInfo(tanks[i]);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		boilerItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < boilerItemStacks.length)
				boilerItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
		}
		boilerBurnTime = par1NBTTagCompound.getShort("BurnTime");

		currentItemBurnTime = getItemBurnTime(boilerItemStacks[0]);

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) boilerBurnTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < boilerItemStacks.length; ++i)
			if (boilerItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				boilerItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1NBTTagCompound.setTag("Items", nbttaglist);

	}

	private ItemStack[] boilerItemStacks = new ItemStack[1];
	public int currentItemBurnTime;

	@Override
	public int getSizeInventory() {
		return boilerItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return boilerItemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (boilerItemStacks[par1] != null) {
			ItemStack itemstack;

			if (boilerItemStacks[par1].stackSize <= par2) {
				itemstack = boilerItemStacks[par1];
				boilerItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = boilerItemStacks[par1].splitStack(par2);

				if (boilerItemStacks[par1].stackSize == 0)
					boilerItemStacks[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (boilerItemStacks[par1] != null) {
			ItemStack itemstack = boilerItemStacks[par1];
			boilerItemStacks[par1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		boilerItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return true;
	}

	private void SetTankFluid(FluidTank tank, int value) {
		if (tank.getFluid() == null)
			tank.setFluid(new FluidStack(value, 0));
		else
			tank.getFluid().fluidID = value;
	}

	private void SetTankAmount(FluidTank tank, int value) {
		if (tank.getFluid() == null)
			tank.setFluid(new FluidStack(0, value));
		else
			tank.getFluid().amount = value;
	}

	public void GetGUINetworkData(int id, int value) {
		switch (id) {
		case NETDATAID_INPUT_TANK_FLUID:
			SetTankFluid(tanks[TANK_INPUT], value);
			break;
		case NETDATAID_INPUT_TANK_AMOUNT:
			SetTankAmount(tanks[TANK_INPUT], value);
			break;
		case NETDATAID_OUTPUT_TANK_FLUID:
			SetTankFluid(tanks[TANK_OUTPUT], value);
			break;
		case NETDATAID_OUTPUT_TANK_AMOUNT:
			SetTankAmount(tanks[TANK_OUTPUT], value);
			break;

		case 7:
			setRedstoneMode(value);
			break;
		}
	}

	public void SendGUINetworkData(ContainerSteamBoiler container,
			ICrafting crafting) {
		crafting.sendProgressBarUpdate(container, 5, boilerBurnTime);
		crafting.sendProgressBarUpdate(container, 6, currentItemBurnTime);
		crafting.sendProgressBarUpdate(container, 7, getRedstoneMode());
		crafting.sendProgressBarUpdate(
				container,
				NETDATAID_INPUT_TANK_FLUID,
				tanks[TANK_INPUT].getFluid() != null ? tanks[TANK_INPUT]
						.getFluid().fluidID : 0);
		crafting.sendProgressBarUpdate(
				container,
				NETDATAID_INPUT_TANK_AMOUNT,
				tanks[TANK_INPUT].getFluid() != null ? tanks[TANK_INPUT]
						.getFluid().amount : 0);
		crafting.sendProgressBarUpdate(
				container,
				NETDATAID_OUTPUT_TANK_FLUID,
				tanks[TANK_OUTPUT].getFluid() != null ? tanks[TANK_OUTPUT]
						.getFluid().fluidID : 0);
		crafting.sendProgressBarUpdate(
				container,
				NETDATAID_OUTPUT_TANK_AMOUNT,
				tanks[TANK_OUTPUT].getFluid() != null ? tanks[TANK_OUTPUT]
						.getFluid().amount : 0);
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Steamboiler";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
				: player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
						zCoord + 0.5D) <= 64.0D;
	}

	@Override
	protected boolean isFluidFuel(FluidStack fuel) {
		return fuel.getFluid() == FluidRegistry.WATER;
	}

	protected String getFluidName(FluidStack fluid) {
		if (fluid == null || fluid.getFluid() == null)
			return null;
		String name = fluid.getFluid().getName();
		if (name == null)
			return null;
		return name.trim().toLowerCase();
	}

	public World getWorld() {
		return worldObj;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isFluidFuel(resource))
			return tanks[TANK_INPUT].fill(resource, doFill);
		else
			return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (resource.isFluidEqual(tanks[TANK_OUTPUT].getFluid()))
			return tanks[TANK_OUTPUT].drain(resource.amount, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tanks[TANK_OUTPUT].drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return tank_info;
	}

	@Override
	protected void UpdateEntityClient() {

	}

	@Override
	protected void UpdateEntityServer() {

	}

	@Override
	public FluidTank GetTank(int slot) {
		return tanks[slot];
	}

	@Override
	public int GetTankCount() {
		return 2;
	}

	public static int getItemBurnTime(ItemStack itemstack) {
		if (itemstack == null)
			return 0;
		else {
			Item item = itemstack.getItem();

			if (item instanceof ItemBlock
					&& Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab)
					return 150;

				if (block.getMaterial() == Material.wood)
					return 300;

				if (block == Blocks.coal_block)
					return 16000;
			}

			if (item instanceof ItemTool
					&& ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword
					&& ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe
					&& ((ItemHoe) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item == Items.stick)
				return 100;
			if (item == Items.coal)
				return 1600;
			if (item == Items.lava_bucket)
				return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling))
				return 100;
			if (item == Items.blaze_rod)
				return 2400;
			return GameRegistry.getFuelValue(itemstack);
		}
	}

	public static boolean isItemFuel(ItemStack par0ItemStack) {
		return getItemBurnTime(par0ItemStack) > 0;
	}

	@Override
	public void update() {
		UpdateRedstone();
		this.autoOutputToSides(80, this);

		if (getRedstoneMode() == 0)
			return;

		else if (getRedstoneMode() == 2 && !redstone_signal)
			return;
		else {

			if (boilerBurnTime > 0)
				--boilerBurnTime;

			if (!worldObj.isRemote) {
				if (tanks[0].getFluidAmount() != LibOptions.boilerCapacity)
					if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == FluidRegistry.WATER
							.getBlock())
						tanks[0].fill(new FluidStack(FluidRegistry.WATER, 21),
								true);

				if (tanks[0].getFluidAmount() > 0
						&& tanks[1].getFluidAmount() != LibOptions.boilerCapacity) {
					if (boilerBurnTime == 0) {
						currentItemBurnTime = boilerBurnTime = getItemBurnTime(boilerItemStacks[0]) / 32;

						if (boilerBurnTime > 0)
							if (boilerItemStacks[0] != null) {
								--boilerItemStacks[0].stackSize;

								if (boilerItemStacks[0].stackSize == 0)
									boilerItemStacks[0] = boilerItemStacks[0]
											.getItem().getContainerItem(
													boilerItemStacks[0]);
							}
					}

					if (boilerBurnTime > 0 && tanks[0].getFluidAmount() > 2) {
						tanks[0].drain(20, true);
						tanks[1].fill(new FluidStack(ModBlocks.steam, 20), true);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (currentItemBurnTime == 0)
			currentItemBurnTime = 200;

		return boilerBurnTime * par1 / currentItemBurnTime;
	}

	static private final int[] INSERT_SLOTS = { 0 };
	static private final int[] EXTRACT_SLOTS = { 0 };

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return slot == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 1 ? INSERT_SLOTS : EXTRACT_SLOTS;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	public void autoOutputToSides(int amountPerTick, TileEntity currentTile) {

		if (worldObj == null)
			return;
		refreshSurroundingTanks(currentTile);

		if (tanks[1].getFluidAmount() > 0 && surroundingTanks.size() > 0) {
			FluidStack drainedFluid = tanks[1].drain(
					Math.min(tanks[1].getFluidAmount(), amountPerTick), true);
			if (drainedFluid != null) {
				Collections.shuffle(surroundingTanks);
				for (ForgeDirection side : surroundingTanks) {
					TileEntity otherTank = getTileInDirection(this, side);
					if (drainedFluid.amount > 0) {
						drainedFluid = drainedFluid.copy();
						if (otherTank instanceof IFluidHandler)
							drainedFluid.amount -= ((IFluidHandler) otherTank)
									.fill(side.getOpposite(), drainedFluid,
											true);
					}
				}
				if (drainedFluid.amount > 0)
					tanks[1].fill(drainedFluid, true);
			}
		}

	}

}
