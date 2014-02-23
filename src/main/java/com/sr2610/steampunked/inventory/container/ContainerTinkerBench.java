package com.sr2610.steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.inventory.slots.tinkerbench.SlotChasis;
import com.sr2610.steampunked.inventory.slots.tinkerbench.SlotCore;
import com.sr2610.steampunked.inventory.slots.tinkerbench.SlotOutput;
import com.sr2610.steampunked.inventory.slots.tinkerbench.SlotUpgrade;
import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTinkerBench extends Container {

	private TileEntityTinkerBench tinkerTable;
	private int lastCraftProgress;

	public ContainerTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		int i;
		int j;
		tinkerTable = cs;

		addSlotToContainer(new SlotChasis(cs, 0, 16, 24));
		addSlotToContainer(new SlotCore(cs, 1, 16, 49));

		addSlotToContainer(new SlotUpgrade(cs, 2, 16, 78));
		addSlotToContainer(new SlotUpgrade(cs, 3, 34, 78));
		addSlotToContainer(new SlotUpgrade(cs, 4, 52, 78));
		addSlotToContainer(new SlotUpgrade(cs, 5, 16, 96));
		addSlotToContainer(new SlotUpgrade(cs, 6, 34, 96));

		addSlotToContainer(new SlotUpgrade(cs, 7, 52, 96));

		addSlotToContainer(new SlotOutput(cs, 8, 178, 103));

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player_inv, j + i * 9 + 9,
						28 + j * 18, 135 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player_inv, i, 28 + i * 18, 193));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0,
				tinkerTable.craftProgress);

	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (lastCraftProgress != tinkerTable.craftProgress) {
				icrafting.sendProgressBarUpdate(this, 0,

						tinkerTable.craftProgress);
			}
			for (i = 0; i < crafters.size(); i++) {
				tinkerTable.SendGUINetworkData(this,
						(ICrafting) crafters.get(i));
			}

		}

		lastCraftProgress = tinkerTable.craftProgress;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		tinkerTable.GetGUINetworkData(par1, par2);
		if (par1 == 0) {
			tinkerTable.craftProgress = par2;
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return tinkerTable.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 8) {
				if (!mergeItemStack(itemstack1, 8,
						inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, 8, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
