package com.sr2610.steampunked.common.inventory.slots.tinkerbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.common.items.automotons.ItemChasis;

public class SlotChasis extends Slot {

	public SlotChasis(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {

		if (itemstack.getItem() instanceof ItemChasis)
			return true;
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
