/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSpecificItem extends Slot {

	final ItemStack stack;
	int slotStackLimit = -1;

	public SlotSpecificItem(IInventory inventory, int slotIndex, int x, int y,
			ItemStack stack) {

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
