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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class SlotLocked extends Slot {

	public SlotLocked(IInventory inventory, int x, int y, int z) {

		super(inventory, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {

		return false;
	}

}
