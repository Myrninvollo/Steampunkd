package steampunked.inventory.slots.tinkerbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import steampunked.items.interfaces.IUpgrade;

public class SlotUpgrade extends Slot {

	public SlotUpgrade(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack itemstack) {

		if (itemstack.getItem() instanceof IUpgrade) {
			return true;
		} else
			return false;
	}

	public int getSlotStackLimit() {
		return 1;
	}

}
