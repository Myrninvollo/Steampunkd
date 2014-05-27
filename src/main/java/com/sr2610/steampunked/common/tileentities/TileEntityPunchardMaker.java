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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPunchardMaker extends TileEntity implements IInventory {

	private ItemStack[] benchItemStacks = new ItemStack[4];

	@Override
	public int getSizeInventory() {
		return benchItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return benchItemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (benchItemStacks[par1] != null) {
			ItemStack itemstack;

			if (benchItemStacks[par1].stackSize <= par2) {
				itemstack = benchItemStacks[par1];
				benchItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = benchItemStacks[par1].splitStack(par2);

				if (benchItemStacks[par1].stackSize == 0)
					benchItemStacks[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (benchItemStacks[par1] != null) {
			final ItemStack itemstack = benchItemStacks[par1];
			benchItemStacks[par1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		benchItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName() {
		return "Punchcard Maker";
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

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		final NBTTagList nbttaglist = par1NBTTagCompound
				.getTagList("Items", 10);
		benchItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			final NBTTagCompound nbttagcompound1 = nbttaglist
					.getCompoundTagAt(i);
			final byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < benchItemStacks.length)
				benchItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		final NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < benchItemStacks.length; ++i)
			if (benchItemStacks[i] != null) {
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				benchItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1NBTTagCompound.setTag("Items", nbttaglist);

	}

}
