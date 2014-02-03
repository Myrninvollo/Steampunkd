package steampunked.inventory.slots.tinkerbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import steampunked.items.parts.ItemArm;

public class SlotOutput extends Slot {

	public SlotOutput(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack itemstack) {

		return false;
	}

	public int getSlotStackLimit() {
		return 1;
	}

}
