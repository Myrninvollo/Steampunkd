package com.sr2610.steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.inventory.slots.tinkerbench.SlotUpgrade;
import com.sr2610.steampunked.tileentities.TileEntitySteamFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSteamFurnace extends Container {
	private TileEntitySteamFurnace furnace;
	private int lastCookTime;

	public ContainerSteamFurnace(InventoryPlayer par1InventoryPlayer,
			TileEntitySteamFurnace par2TileEntityFurnace) {
		furnace = par2TileEntityFurnace;
		addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 35));
		addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
				par2TileEntityFurnace, 1, 116, 35));
		int i;

		for (i = 0; i < 3; ++i) {
			addSlotToContainer(new SlotUpgrade(par2TileEntityFurnace, i + 2, 8,
					18 + i * 18));
		}

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, furnace.furnaceCookTime);

	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (lastCookTime != furnace.furnaceCookTime) {
				icrafting.sendProgressBarUpdate(this, 0,

				furnace.furnaceCookTime);
			}
			for (i = 0; i < crafters.size(); i++) {
				furnace.SendGUINetworkData(this, (ICrafting) crafters.get(i));
			}

		}

		lastCookTime = furnace.furnaceCookTime;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		furnace.GetGUINetworkData(par1, par2);
		if (par1 == 0) {
			furnace.furnaceCookTime = par2;
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return furnace.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 1) {
				if (!mergeItemStack(itemstack1, 1, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, 1, false)) {
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
