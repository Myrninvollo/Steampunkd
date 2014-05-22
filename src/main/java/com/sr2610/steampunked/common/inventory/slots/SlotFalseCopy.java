package com.sr2610.steampunked.common.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class SlotFalseCopy extends Slot {

	public int slotIndex = 0;

	public SlotFalseCopy(IInventory inventory, int slot, int x, int z) {

		super(inventory, slot, x, z);
		slotIndex = slot;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {

		// putStack(null);
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return true;
	}

	@Override
	public void putStack(ItemStack stack) {

		if (stack != null)
			stack.stackSize = 1;
		inventory.setInventorySlotContents(slotIndex, stack);
		onSlotChanged();
	}

}
