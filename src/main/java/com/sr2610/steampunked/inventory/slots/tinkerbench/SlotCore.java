package com.sr2610.steampunked.inventory.slots.tinkerbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.items.ItemCore;

public class SlotCore extends Slot {

	public SlotCore(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {

		if (itemstack.getItem() instanceof ItemCore) {
			return true;
		}
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}