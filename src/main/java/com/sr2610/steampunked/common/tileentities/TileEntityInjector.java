/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.sr2610.steampunked.api.items.ISteamUser;
import com.sr2610.steampunked.api.utils.IRedstoneControl;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.inventory.container.ContainerInjector;
import com.sr2610.steampunked.common.lib.LibOptions;

public class TileEntityInjector extends TileEntityMachine implements
		ISidedInventory, IFluidHandler, IRedstoneControl {

	static private final int NETDATAID_TANK_FLUID = 1;
	static private final int NETDATAID_TANK_AMOUNT = 2;

	private final FluidTank tank;
	private final FluidTankInfo[] tank_info;

	public TileEntityInjector() {
		super();

		tank = new FluidTank(LibOptions.injectorCapacity);

		tank_info = new FluidTankInfo[1];
		tank_info[0] = new FluidTankInfo(tank);

	}

	private final ItemStack[] injectorItemSlot = new ItemStack[1];

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return isItemValidForSlot(slot, item);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return injectorItemSlot.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1) {
		return injectorItemSlot[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (injectorItemSlot[par1] != null) {
			ItemStack itemstack;

			if (injectorItemSlot[par1].stackSize <= par2) {
				itemstack = injectorItemSlot[par1];
				injectorItemSlot[par1] = null;
				return itemstack;
			} else {
				itemstack = injectorItemSlot[par1].splitStack(par2);

				if (injectorItemSlot[par1].stackSize == 0)
					injectorItemSlot[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (injectorItemSlot[par1] != null) {
			final ItemStack itemstack = injectorItemSlot[par1];
			injectorItemSlot[par1] = null;
			return itemstack;
		} else
			return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		injectorItemSlot[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	public void GetGUINetworkData(int id, int value) {
		switch (id) {
		case NETDATAID_TANK_FLUID:
			if (tank.getFluid() == null)
				tank.setFluid(new FluidStack(value, 0));
			else
				tank.getFluid().fluidID = value;
			break;
		case NETDATAID_TANK_AMOUNT:
			if (tank.getFluid() == null)
				tank.setFluid(new FluidStack(0, value));
			else
				tank.getFluid().amount = value;
			break;

		case 3:
			setControl(getModeFromInt(value));
			break;
		}
	}

	public void SendGUINetworkData(ContainerInjector container,
			ICrafting crafting) {
		crafting.sendProgressBarUpdate(container, NETDATAID_TANK_FLUID,
				tank.getFluid() != null ? tank.getFluid().fluidID : 0);
		crafting.sendProgressBarUpdate(container, NETDATAID_TANK_AMOUNT,
				tank.getFluid() != null ? tank.getFluid().amount : 0);
		crafting.sendProgressBarUpdate(container, 3, getIntFromMode(mode));

	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
				: player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
						zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

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
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (resource.isFluidEqual(tank.getFluid()))
			return tank.drain(resource.amount, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isFluidFuel(resource))
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	protected boolean isFluidFuel(FluidStack fuel) {
		final String name = getFluidName(fuel);
		if (name == null)
			return false;
		return name.equals("steam")
				|| fuel.getFluid() == ModBlocks.steam
				|| fuel.getFluid().getLocalizedName().trim().toLowerCase() == "steam";
	}

	protected String getFluidName(FluidStack fluid) {
		if (fluid == null || fluid.getFluid() == null)
			return null;
		final String name = fluid.getFluid().getName();
		if (name == null)
			return null;
		return name.trim().toLowerCase();
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
	public void update() {
		UpdateRedstone();

		if (shouldRun())
			if (canCharge() == true && tank.getFluidAmount() > 0) {
				tank.drain(1, true);
				injectorItemSlot[0].setItemDamage(injectorItemSlot[0]
						.getItemDamage() - 1);
			}

	}

	public World getWorld() {
		return worldObj;
	}

	@Override
	public FluidTank GetTank(int slot) {
		if (slot != 0)
			return null;
		return tank;
	}

	@Override
	public int GetTankCount() {
		return 1;
	}

	public int getComparatorOutput() {
		if (getItemMaxCharge() == 0)
			return 0;
		return getItemChargeLevel() * 15 / getItemMaxCharge();
	}

	public int getItemChargeLevel() {
		if (injectorItemSlot[0] == null)
			return 0;
		if (injectorItemSlot[0].getItem() instanceof ISteamUser) {
			final ISteamUser r = (ISteamUser) injectorItemSlot[0].getItem();
			return r.getCurrentSteam(injectorItemSlot[0]);
		} else
			return 0;

	}

	public int getItemMaxCharge() {
		if (injectorItemSlot[0] == null)
			return 0;

		if (injectorItemSlot[0].getItem() instanceof ISteamUser) {
			final ISteamUser r = (ISteamUser) injectorItemSlot[0].getItem();
			return r.getMaxSteam();
		} else
			return 0;
	}

	boolean canCharge() {
		if (injectorItemSlot[0] == null)
			return false;
		if (injectorItemSlot[0].getItem() instanceof ISteamUser
				&& getItemChargeLevel() != getItemMaxCharge())
			return true;
		else
			return false;

	}

	@Override
	public String getInventoryName() {
		return "Injector";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void setPowered(boolean isPowered) {
		this.isPowered = isPowered;
	}

	@Override
	public boolean isPowered() {
		return isPowered;
	}

	@Override
	public void setControl(ControlMode control) {

		mode = control;
	}

	@Override
	public ControlMode getControl() {
		return mode;
	}

	public boolean isPowered;
	protected boolean rsDisable;
	protected boolean rsSetting;
	protected boolean wasPowered;

}
