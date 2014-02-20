package com.sr2610.steampunked.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPunchardMaker extends TileEntity implements IInventory {

	private ItemStack[] benchItemStacks = new ItemStack[4];

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
		return 64;
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
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.benchItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (nbttaglist.getCompoundTagAt(i));
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.benchItemStacks.length) {
				this.benchItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}


	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.benchItemStacks.length; ++i) {
			if (this.benchItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.benchItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

	}

}
