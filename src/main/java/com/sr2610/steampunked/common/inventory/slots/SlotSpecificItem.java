package com.sr2610.steampunked.common.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class SlotSpecificItem extends Slot {

	final ItemStack stack;
	int slotStackLimit = -1;

	public SlotSpecificItem(IInventory inventory, int slotIndex, int x, int y, ItemStack stack) {

		super(inventory, slotIndex, x, y);

		this.stack = stack;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return this.stack.isItemEqual(stack);
	}

	public SlotSpecificItem setSlotStackLimit(int slotStackLimit) {

		this.slotStackLimit = slotStackLimit;
		return this;
	}

	@Override
	public int getSlotStackLimit() {

		if (slotStackLimit <= 0)
			return inventory.getInventoryStackLimit();
		return slotStackLimit;
	}

}
