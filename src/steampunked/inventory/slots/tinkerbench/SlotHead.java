package steampunked.inventory.slots.tinkerbench;

import steampunked.items.parts.ItemHead;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHead extends Slot {

	public SlotHead(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack itemstack) {

		if (itemstack.getItem() instanceof ItemHead) {
			return true;
		} else
			return false;
	}

	public int getSlotStackLimit() {
		return 1;
	}

}