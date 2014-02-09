package steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import steampunked.inventory.slots.tinkerbench.SlotChasis;
import steampunked.inventory.slots.tinkerbench.SlotOutput;
import steampunked.inventory.slots.tinkerbench.SlotUpgrade;
import steampunked.tileentities.TileEntityTinkerBench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTinkerBench extends Container {

	private TileEntityTinkerBench tinkerTable;
	private int lastCraftProgress;

	public ContainerTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		int i;
		int j;
		this.tinkerTable = cs;

		this.addSlotToContainer(new SlotChasis(cs, 0, 16, 24));
		this.addSlotToContainer(new Slot(cs, 1, 16, 49));

		for (i = 0; i < 2; ++i) {
			for (j = 0; j < 3; ++j) {
				this.addSlotToContainer(new SlotUpgrade(cs, 3 + (j * i),
						16 + j * 18, 78 + i * 18));
			}
		}
		this.addSlotToContainer(new SlotOutput(cs, 8, 178, 103));

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player_inv, j + i * 9 + 9,
						28 + j * 18, 135 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player_inv, i, 28 + i * 18, 193));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0,
				this.tinkerTable.craftProgress);

	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCraftProgress != this.tinkerTable.craftProgress) {
				icrafting.sendProgressBarUpdate(this, 0,

				this.tinkerTable.craftProgress);
			}
			for (i = 0; i < crafters.size(); i++) {
				tinkerTable.SendGUINetworkData(this,
						(ICrafting) crafters.get(i));
			}

		}

		this.lastCraftProgress = this.tinkerTable.craftProgress;

	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		tinkerTable.GetGUINetworkData(par1, par2);
		if (par1 == 0) {
			this.tinkerTable.craftProgress = par2;
		}

	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.tinkerTable.isUseableByPlayer(par1EntityPlayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 8) {
				if (!this.mergeItemStack(itemstack1, 8,
						this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 8, false)) {
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
