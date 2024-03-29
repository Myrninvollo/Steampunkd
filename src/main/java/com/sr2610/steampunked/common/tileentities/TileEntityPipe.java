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

import java.util.Collections;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityPipe extends TileEntityMachine implements IFluidHandler {

	public FluidTank tank;
	private final FluidTankInfo[] tank_info;

	public TileEntityPipe() {
		super();

		tank = new FluidTank(100);
		tank_info = new FluidTankInfo[1];
		tank_info[0] = new FluidTankInfo(tank);
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
	}

	@Override
	public String getInventoryName() {
		return "Pipe";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return false;
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

	@Override
	public void update() {
		autoOutputToSides(100, this);
	}

	public void autoOutputToSides(int amountPerTick, TileEntity currentTile) {

		if (worldObj == null)
			return;
		refreshSurroundingTanks(currentTile);

		if (tank.getFluidAmount() > 0 && surroundingTanks.size() > 0) {
			FluidStack drainedFluid = tank.drain(
					Math.min(tank.getFluidAmount(), amountPerTick), true);
			if (drainedFluid != null) {
				Collections.shuffle(surroundingTanks);
				for (final ForgeDirection side : surroundingTanks) {
					final TileEntity otherTank = getTileInDirection(this, side);
					if (drainedFluid.amount > 0) {
						drainedFluid = drainedFluid.copy();
						if (otherTank instanceof IFluidHandler)
							drainedFluid.amount -= ((IFluidHandler) otherTank)
									.fill(side.getOpposite(), drainedFluid,
											true);
					}
				}
				if (drainedFluid.amount > 0)
					tank.fill(drainedFluid, true);
			}
		}

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
		return true;
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
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return tank_info;
	}

	public boolean[] pipeConnectionsBuffer = new boolean[6];

	public boolean isPipeConnected(ForgeDirection direct) {
		final TileEntity tile = getTileInDirection(this, direct);
		if (tile != null && tile instanceof IFluidHandler)
			return true;
		return false;
	}

	public void checkPipeConnections() {
		if (isPipeConnected(ForgeDirection.WEST))
			pipeConnectionsBuffer[5] = true;

		else
			pipeConnectionsBuffer[5] = false;

		if (isPipeConnected(ForgeDirection.EAST))
			pipeConnectionsBuffer[4] = true;

		else
			pipeConnectionsBuffer[4] = false;

		if (isPipeConnected(ForgeDirection.DOWN))
			pipeConnectionsBuffer[1] = true;
		else
			pipeConnectionsBuffer[1] = false;

		if (isPipeConnected(ForgeDirection.UP))
			pipeConnectionsBuffer[0] = true;
		else
			pipeConnectionsBuffer[0] = false;

		if (isPipeConnected(ForgeDirection.NORTH))
			pipeConnectionsBuffer[2] = true;
		else
			pipeConnectionsBuffer[2] = false;

		if (isPipeConnected(ForgeDirection.SOUTH))
			pipeConnectionsBuffer[3] = true;
		else
			pipeConnectionsBuffer[3] = false;

	}

}
