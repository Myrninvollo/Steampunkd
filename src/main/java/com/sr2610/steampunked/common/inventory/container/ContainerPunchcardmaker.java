/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.common.inventory.slots.SlotLocked;
import com.sr2610.steampunked.common.inventory.slots.SlotSpecificItem;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;

public class ContainerPunchcardmaker extends Container {

	private final TileEntityPunchardMaker maker;
	private static final int SLOT_INVENTORY_X = 40;
	private static final int SLOT_INVENTORY_Y = 84 + 30;

	private static final int SLOT_HOTBAR_X = 40;
	private static final int SLOT_HOTBAR_Y = 142 + 30;

	public ContainerPunchcardmaker(IInventory player_inventory,
			TileEntityPunchardMaker injector) {
		maker = injector;
		maker.openInventory();
		int i, j;

		addSlotToContainer(new SlotSpecificItem(injector, 0, 7, 7,
				new ItemStack(ModItems.handBook)));
		addSlotToContainer(new Slot(injector, 1, 187, 29));
		addSlotToContainer(new Slot(injector, 2, 187, 75));
		addSlotToContainer(new SlotLocked(injector, 3, 21, 45));

		for (i = 0; i < 3; ++i)
			for (j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(player_inventory, j + i * 9 + 9,
						SLOT_INVENTORY_X + j * 18, SLOT_INVENTORY_Y + i * 18));
		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(player_inventory, i, SLOT_HOTBAR_X + i
					* 18, SLOT_HOTBAR_Y));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return maker.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}

}
