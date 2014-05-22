package com.sr2610.steampunked.common.inventory.slots.tinkerbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.common.items.interfaces.IUpgrade;

public class SlotUpgrade extends Slot {

	public SlotUpgrade(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {

		if (itemstack.getItem() instanceof IUpgrade)
			return true;
		else
			return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
