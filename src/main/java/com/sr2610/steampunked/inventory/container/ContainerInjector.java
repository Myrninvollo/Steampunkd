/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.inventory.slots.SlotArmor;
import com.sr2610.steampunked.tileentities.TileEntityInjector;

public class ContainerInjector extends Container {
	private TileEntityInjector te_injector;
	private static final int SLOT_INVENTORY_X = 8;
	private static final int SLOT_INVENTORY_Y = 84;

	private static final int SLOT_HOTBAR_X = 8;
	private static final int SLOT_HOTBAR_Y = 142;

	public ContainerInjector(TileEntityInjector injector,
			IInventory player_inventory) {
		te_injector = injector;
		te_injector.openInventory();
		int i, j;

		addSlotToContainer(new Slot(injector, 0, 81, 19));

		for (i = 0; i < 4; ++i)
			addSlotToContainer(new SlotArmor(null, player_inventory,
					player_inventory.getSizeInventory() - 1 - i, 8 + i * 18,
					49, i));

		for (i = 0; i < 3; ++i)
			for (j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(player_inventory, j + i * 9 + 9,
						SLOT_INVENTORY_X + j * 18, SLOT_INVENTORY_Y + i * 18));
		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(player_inventory, i, SLOT_HOTBAR_X + i
					* 18, SLOT_HOTBAR_Y));
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return te_injector.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		te_injector.closeInventory();
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
			te_injector.SendGUINetworkData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public void updateProgressBar(int i, int j) {
		te_injector.GetGUINetworkData(i, j);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 0) {
				if (!mergeItemStack(itemstack1, 1, 37, true))
					return null;
			} else {
				if (((Slot) inventorySlots.get(0)).getHasStack()
						|| !((Slot) inventorySlots.get(0))
								.isItemValid(itemstack1))
					return null;

				if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1) {
					((Slot) inventorySlots.get(0)).putStack(itemstack1.copy());
					itemstack1.stackSize = 0;
				} else if (itemstack1.stackSize >= 1) {
					((Slot) inventorySlots.get(0))
							.putStack(new ItemStack(itemstack1.getItem(), 1,
									itemstack1.getItemDamage()));
					--itemstack1.stackSize;
				}
			}

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}
