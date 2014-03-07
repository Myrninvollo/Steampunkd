package com.sr2610.steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;

public class ContainerSteamBoiler extends Container {

	private TileEntitySteamBoiler boiler;

	private static final int SLOT_INVENTORY_X = 8;
	private static final int SLOT_INVENTORY_Y = 84;

	private static final int SLOT_HOTBAR_X = 8;
	private static final int SLOT_HOTBAR_Y = 142;

	public ContainerSteamBoiler(TileEntitySteamBoiler teBoiler,
			IInventory player_inventory) {
		boiler = teBoiler;
		boiler.openInventory();
		int i, j;

		addSlotToContainer(new Slot(boiler, 0, 81, 39));

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
		return boiler.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		Slot slot = (Slot) inventorySlots.get(slotId);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemToTransfer = slot.getStack();
			ItemStack copy = itemToTransfer.copy();
			if (slotId < boiler.getSizeInventory()) {
				if (!mergeItemStack(itemToTransfer, boiler.getSizeInventory(),
						inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemToTransfer, 0,
					boiler.getSizeInventory(), false))
				return null;

			if (itemToTransfer.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (itemToTransfer.stackSize != copy.stackSize)
				return copy;
		}
		return null;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		boiler.closeInventory();
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
			boiler.SendGUINetworkData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public void updateProgressBar(int i, int j) {
		boiler.GetGUINetworkData(i, j);
		if (i == 5)
			boiler.boilerBurnTime = j;

		if (i == 6)
			boiler.currentItemBurnTime = j;
	}

}
