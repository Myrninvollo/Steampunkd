package com.sr2610.steampunked.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPunchardMaker extends TileEntity implements IInventory {

	private ItemStack[] benchItemStacks = new ItemStack[3];

	public int getSizeInventory() {
		return this.benchItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.benchItemStacks[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.benchItemStacks[par1] != null) {
			ItemStack itemstack;

			if (this.benchItemStacks[par1].stackSize <= par2) {
				itemstack = this.benchItemStacks[par1];
				this.benchItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.benchItemStacks[par1].splitStack(par2);

				if (this.benchItemStacks[par1].stackSize == 0) {
					this.benchItemStacks[par1] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.benchItemStacks[par1] != null) {
			ItemStack itemstack = this.benchItemStacks[par1];
			this.benchItemStacks[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.benchItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "Tinkerbench";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
				: player.getDistanceSq((double) xCoord + 0.5D,
						(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

}
