package steampunked.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import steampunked.inventory.slots.tinkerbench.SlotArm;
import steampunked.inventory.slots.tinkerbench.SlotBody;
import steampunked.inventory.slots.tinkerbench.SlotHead;
import steampunked.inventory.slots.tinkerbench.SlotLeg;
import steampunked.inventory.slots.tinkerbench.SlotOutput;
import steampunked.inventory.slots.tinkerbench.SlotUpgrade;
import steampunked.tileentities.TileEntityTinkerBench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTinkerBench extends Container {

	private TileEntityTinkerBench tinkerTable;
	private int lastCraftProgress;

	public ContainerTinkerBench(TileEntityTinkerBench cs, IInventory player_inv) {
		this.tinkerTable = cs;

		this.addSlotToContainer(new SlotHead(cs, 0, 31, 28));
		this.addSlotToContainer(new SlotArm(cs, 1, 8, 51));
		this.addSlotToContainer(new SlotBody(cs, 2, 31, 51));
		this.addSlotToContainer(new SlotArm(cs, 3, 54, 51));
		this.addSlotToContainer(new SlotLeg(cs, 4, 20, 74));
		this.addSlotToContainer(new SlotLeg(cs, 5, 42, 74));

		this.addSlotToContainer(new SlotUpgrade(cs, 6, 148, 40));
		this.addSlotToContainer(new SlotUpgrade(cs, 7, 166, 40));
		this.addSlotToContainer(new SlotUpgrade(cs, 8, 148, 58));
		this.addSlotToContainer(new SlotUpgrade(cs, 9, 166, 58));
		this.addSlotToContainer(new SlotUpgrade(cs, 10, 148, 76));
		this.addSlotToContainer(new SlotUpgrade(cs, 11, 166, 76));

		this.addSlotToContainer(new SlotOutput(cs, 12, 178, 103));

		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
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

	        return null;
	}
}
