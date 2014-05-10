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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileEntityMachine extends TileEntity implements
		IInventory {
	protected abstract boolean isFluidFuel(FluidStack fuel);

	private int redstoneMode;

	/**
	 * Links an item slot to a tank for filling/draining containers.
	 */
	public class ContainerSlot {
		public final boolean fill;
		public final int tank_slot;
		public final int slot;

		public ContainerSlot(int container_tank, int container_slot,
				boolean container_fill) {
			tank_slot = container_tank;
			slot = container_slot;
			fill = container_fill;
		}

		public void Update() {
			final ItemStack stack = getStackInSlot(slot);
			if (stack == null
					|| !(stack.getItem() instanceof IFluidContainerItem))
				return;

			final IFluidContainerItem fluid_cont = (IFluidContainerItem) stack
					.getItem();

			final FluidTank tank = GetTank(tank_slot);
			if (fill) {
				FluidStack drained = tank.drain(25, false);
				if (drained == null || drained.amount == 0)
					return;
				final int filled = fluid_cont.fill(stack, drained, false);
				if (filled == 0)
					return;
				drained = tank.drain(filled, true);
				fluid_cont.fill(stack, drained, true);
				UpdateTank(tank_slot);
				UpdateInventoryItem(slot);
			} else {
				FluidStack drained = fluid_cont.drain(stack, 25, false);
				if (drained == null || drained.amount == 0)
					return;

				final int filled = tank.fill(drained, false);
				if (filled == 0)
					return;
				drained = fluid_cont.drain(stack, filled, true);
				tank.fill(drained, true);
				UpdateTank(tank_slot);
				UpdateInventoryItem(slot);
			}
		}
	}

	private final List<ContainerSlot> conatiner_slots;
	private NBTTagCompound packet;
	private boolean initialized;

	protected boolean last_redstone_signal;
	protected boolean redstone_signal;

	protected final void AddContainerSlot(ContainerSlot cs) {
		conatiner_slots.add(cs);
	}

	public abstract FluidTank GetTank(int slot);

	public abstract int GetTankCount();

	public TileEntityMachine() {
		conatiner_slots = new ArrayList<ContainerSlot>();

		initialized = false;
	}

	@Override
	public void invalidate() {
		initialized = false;
		super.invalidate();
	}

	protected final void UpdateTank(int slot) {
		if (packet == null)
			return;
		WriteTankToNBT(packet, slot);
	}

	protected final void UpdateInventoryItem(int slot) {
		if (packet == null)
			return;
		WriteInventoryItemToNBT(packet, slot);
	}

	protected final void WriteTankToNBT(NBTTagCompound compound, int slot) {
		final NBTTagCompound tag = new NBTTagCompound();
		GetTank(slot).writeToNBT(tag);
		compound.setTag("Tank_" + String.valueOf(slot), tag);
	}

	protected final void WriteInventoryItemToNBT(NBTTagCompound compound,
			int slot) {
		final ItemStack is = getStackInSlot(slot);
		final NBTTagCompound tag = new NBTTagCompound();
		if (is != null) {
			tag.setBoolean("empty", false);
			is.writeToNBT(tag);
		} else
			tag.setBoolean("empty", true);
		compound.setTag("Item_" + String.valueOf(slot), tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		setRedstoneMode(compound.getInteger("redstoneMode"));

		int i;
		for (i = 0; i < GetTankCount(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) compound.getTag("Tank_"
					+ String.valueOf(i));
			if (tag != null) {
				final FluidTank tank = GetTank(i);
				tank.setFluid(null);
				tank.readFromNBT(tag);
			}
		}

		for (i = 0; i < getSizeInventory(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) compound.getTag("Item_"
					+ String.valueOf(i));
			if (tag != null) {
				ItemStack stack = null;
				if (!tag.getBoolean("empty"))
					stack = ItemStack.loadItemStackFromNBT(tag);
				setInventorySlotContents(i, stack);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		int i;
		super.writeToNBT(compound);

		for (i = 0; i < GetTankCount(); i++)
			WriteTankToNBT(compound, i);
		for (i = 0; i < getSizeInventory(); i++)
			WriteInventoryItemToNBT(compound, i);

		compound.setInteger("redstoneMode", getRedstoneMode());

	}

	protected final void UpdateValue(String name, int value) {
		if (packet == null)
			return;
		packet.setInteger(name, value);
	}

	protected final void UpdateNBTTag(String name, NBTTagCompound compound) {
		if (packet == null)
			return;
		packet.setTag(name, compound);
	}

	@Override
	public void updateEntity() {

		if (!(initialized || isInvalid()))
			UpdateRedstone();

		if (!worldObj.isRemote) {
			update();
			packet = new NBTTagCompound();
			super.writeToNBT(packet);
			for (final ContainerSlot cs : conatiner_slots)
				cs.Update();
			packet = null;
		}
		last_redstone_signal = redstone_signal;
	}

	public void update() {

	}

	public void UpdateRedstone() {
		redstone_signal = worldObj.isBlockIndirectlyGettingPowered(xCoord,
				yCoord, zCoord);
	}

	protected List<ForgeDirection> surroundingTanks = new ArrayList<ForgeDirection>();

	public TileEntity getTileInDirection(TileEntity tile,
			ForgeDirection direction) {
		final int targetX = tile.xCoord + direction.offsetX;
		final int targetY = tile.yCoord + direction.offsetY;
		final int targetZ = tile.zCoord + direction.offsetZ;
		return worldObj.getTileEntity(targetX, targetY, targetZ);
	}

	public void refreshSurroundingTanks(TileEntity currentTile) {
		final HashSet<ForgeDirection> checkSides = new HashSet<ForgeDirection>();

		checkSides.addAll(Arrays.asList(ForgeDirection.VALID_DIRECTIONS));

		surroundingTanks = new ArrayList<ForgeDirection>();
		for (final ForgeDirection side : checkSides) {
			final TileEntity tile = getTileInDirection(currentTile, side);
			if (tile instanceof IFluidHandler)
				surroundingTanks.add(side);
		}
	}

	public int getRedstoneMode() {
		return redstoneMode;
	}

	public void setRedstoneMode(int redstoneMode) {
		this.redstoneMode = redstoneMode;
	}

}
