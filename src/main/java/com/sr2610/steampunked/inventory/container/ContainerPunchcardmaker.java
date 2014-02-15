package com.sr2610.steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.inventory.slots.SlotArmor;
import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;

public class ContainerPunchcardmaker extends Container {

	private TileEntityPunchardMaker te_injector;
	private int lastSteam;

	private static final int SLOTS_TE = 0;
	private static final int SLOTS_TE_SIZE = 5;
	private static final int SLOTS_INVENTORY = 5;
	private static final int SLOTS_HOTBAR = 5 + 3 * 9;

	private static final int SLOT_INVENTORY_X = 8;
	private static final int SLOT_INVENTORY_Y = 84;

	private static final int SLOT_HOTBAR_X = 8;
	private static final int SLOT_HOTBAR_Y = 142;

	public ContainerPunchcardmaker(TileEntityPunchardMaker injector,
			IInventory player_inventory) {
		te_injector = injector;
		te_injector.openInventory();
		int i, j;

		this.addSlotToContainer(new Slot(injector, 0, 80, 16));
		this.addSlotToContainer(new Slot(injector, 1, 80, 35));
		this.addSlotToContainer(new Slot(injector, 2, 80, 54));

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player_inventory, j + i * 9 + 9,
						SLOT_INVENTORY_X + j * 18, SLOT_INVENTORY_Y + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player_inventory, i, SLOT_HOTBAR_X + i
					* 18, SLOT_HOTBAR_Y));
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return te_injector.isUseableByPlayer(par1EntityPlayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
					return null;
				}
			} else {
				if (((Slot) this.inventorySlots.get(0)).getHasStack()
						|| !((Slot) this.inventorySlots.get(0))
								.isItemValid(itemstack1)) {
					return null;
				}

				if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1) {
					((Slot) this.inventorySlots.get(0)).putStack(itemstack1
							.copy());
					itemstack1.stackSize = 0;
				} else if (itemstack1.stackSize >= 1) {
					((Slot) this.inventorySlots.get(0))
							.putStack(new ItemStack(itemstack1.getItem(), 1,
									itemstack1.getItemDamage()));
					--itemstack1.stackSize;
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

}
