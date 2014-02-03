package steampunked.inventory.container;

import steampunked.tileentities.TileEntitySteamBoiler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSteamBoiler extends Container {

	private TileEntitySteamBoiler te_boiler;

	private static final int SLOTS_TE = 0;
	private static final int SLOTS_TE_SIZE = 5;
	private static final int SLOTS_INVENTORY = 5;
	private static final int SLOTS_HOTBAR = 5 + 3 * 9;

	private static final int SLOT_INVENTORY_X = 8;
	private static final int SLOT_INVENTORY_Y = 84;

	private static final int SLOT_HOTBAR_X = 8;
	private static final int SLOT_HOTBAR_Y = 142;

	public ContainerSteamBoiler(TileEntitySteamBoiler boiler,
			IInventory player_inventory) {
		te_boiler = boiler;
		te_boiler.openChest();
		int i, j;

		this.addSlotToContainer(new Slot(te_boiler, 0, 81, 39));

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
		return te_boiler.isUseableByPlayer(par1EntityPlayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
                {
                    return null;
                }

                if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
                    itemstack1.stackSize = 0;
                }
                else if (itemstack1.stackSize >= 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.itemID, 1, itemstack1.getItemDamage()));
                    --itemstack1.stackSize;
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		te_boiler.closeChest();
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			te_boiler.SendGUINetworkData(this, (ICrafting) crafters.get(i));
		}
	}

	@Override
	public void updateProgressBar(int i, int j) {
		te_boiler.GetGUINetworkData(i, j);
		 if (i == 5)
	        {
	            this.te_boiler.furnaceBurnTime = j;
	        }

	        if (i == 6)
	        {
	            this.te_boiler.currentItemBurnTime = j;
	        }
	}

}
