package com.sr2610.steampunked.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.inventory.container.ContainerSteamFurnace;
import com.sr2610.steampunked.lib.LibOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySteamFurnace extends TileEntityMachine implements
		ISidedInventory, IFluidHandler {

	static private final int NETDATAID_TANK_FLUID = 1;
	static private final int NETDATAID_TANK_AMOUNT = 2;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1 };
	private static final int[] slots_sides = new int[] { 1 };
	private ItemStack[] inventory;
	private FluidTank tank;
	private FluidTankInfo[] tank_info;

	public TileEntitySteamFurnace() {
		super();

		tank = new FluidTank(LibOptions.furnaceCapacity);

		tank_info = new FluidTankInfo[1];
		tank_info[0] = new FluidTankInfo(tank);
		inventory = new ItemStack[6];

	}

	/**
	 * The ItemStacks that hold the items currently being used in the furnace
	 */
	private ItemStack[] furnaceItemStacks = new ItemStack[6];

	/** The number of ticks that the current item has been cooking for */
	public int furnaceCookTime;

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.furnaceItemStacks[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack;

			if (this.furnaceItemStacks[par1].stackSize <= par2) {
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);

				if (this.furnaceItemStacks[par1].stackSize == 0) {
					this.furnaceItemStacks[par1] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack = this.furnaceItemStacks[par1];
			this.furnaceItemStacks[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.furnaceItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.furnaceCookTime * par1 / (LibOptions.furnaceCookTime / 10);
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		if (this.furnaceItemStacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
					this.furnaceItemStacks[0]);
			if (itemstack == null)
				return false;
			if (this.furnaceItemStacks[1] == null)
				return true;
			if (!this.furnaceItemStacks[1].isItemEqual(itemstack))
				return false;
			int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
			return (result <= getInventoryStackLimit() && result <= itemstack
					.getMaxStackSize());
		}
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
					this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[1] == null) {
				this.furnaceItemStacks[1] = itemstack.copy();
			} else if (this.furnaceItemStacks[1].isItemEqual(itemstack)) {
				furnaceItemStacks[1].stackSize += itemstack.stackSize;
			}

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize <= 0) {
				this.furnaceItemStacks[0] = null;
			}
		}
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public void update() {
		boolean flag1 = false;
		if (!this.worldObj.isRemote) {

			if (this.canSmelt() && tank.getFluidAmount() > 10) {
				++this.furnaceCookTime;
				tank.drain(10, true);

				if (this.furnaceCookTime == LibOptions.furnaceCookTime / 10) {
					this.furnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}

			if (flag1) {
				this.markDirty();
			}
		}
	}

	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return true;
	}

	public void GetGUINetworkData(int id, int value) {
		switch (id) {
		case NETDATAID_TANK_FLUID:
			if (tank.getFluid() == null) {
				tank.setFluid(new FluidStack(value, 0));
			} else {
				tank.getFluid().fluidID = value;
			}
			break;
		case NETDATAID_TANK_AMOUNT:
			if (tank.getFluid() == null) {
				tank.setFluid(new FluidStack(0, value));
			} else {
				tank.getFluid().amount = value;
			}
			break;
		}
	}

	public void SendGUINetworkData(ContainerSteamFurnace container,
			ICrafting crafting) {
		crafting.sendProgressBarUpdate(container, NETDATAID_TANK_FLUID,
				tank.getFluid() != null ? tank.getFluid().fluidID : 0);
		crafting.sendProgressBarUpdate(container, NETDATAID_TANK_AMOUNT,
				tank.getFluid() != null ? tank.getFluid().amount : 0);
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
				: player.getDistanceSq((double) xCoord + 0.5D,
						(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
	}

	static private final int[] INSERT_SLOTS = { 0 };
	static private final int[] EXTRACT_SLOTS = { 1 };

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
		if (resource.isFluidEqual(tank.getFluid())) {
			return tank.drain(resource.amount, doDrain);
		}
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

	protected boolean isFluidFuel(FluidStack fuel) {
		String name = getFluidName(fuel);
		if (name == null)
			return false;
		return name.equals("steam")
				|| fuel.getFluid() == ModBlocks.steam
				|| fuel.getFluid().getLocalizedName().trim().toLowerCase() == "steam";
	}

	protected String getFluidName(FluidStack fluid) {
		if (fluid == null || fluid.getFluid() == null)
			return null;
		String name = fluid.getFluid().getName();
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
	protected void UpdateEntityClient() {

	}

	public World getWorld() {
		return worldObj;
	}

	@Override
	public FluidTank GetTank(int slot) {
		if (slot != 0) {
			return null;
		}
		return tank;
	}

	@Override
	public int GetTankCount() {
		return 1;
	}

	@Override
	protected void UpdateEntityServer() {

	}

	@Override
	public String getInventoryName() {
		return "SteamFurnace";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

}
