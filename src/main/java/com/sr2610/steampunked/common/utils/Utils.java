/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.utils;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Utils {

	public static String getFluidLocalisedName(FluidStack stack) {
		return StatCollector.translateToLocal("fluid."
				+ FluidRegistry.getFluidName(stack));

	}

	public static ItemStack consumeItem(ItemStack stack) {
		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem())
				return stack.getItem().getContainerItem(stack);
			else
				return null;
		} else {
			stack.splitStack(1);

			return stack;
		}
	}

	public static int getIntDirFromDirection(ForgeDirection dir) {
		switch (dir) {
		case DOWN:
			return 0;
		case EAST:
			return 5;
		case NORTH:
			return 2;
		case SOUTH:
			return 3;
		case UNKNOWN:
			return 0;
		case UP:
			return 1;
		case WEST:
			return 4;
		default:
			return 0;
		}
	}
	
	public static int testInventoryInsertion(IInventory inventory, ItemStack item) {
		if (item == null || item.stackSize == 0) return 0;
		if (inventory == null) return 0;
		int slotCount = inventory.getSizeInventory();
		/*
		 * Allows counting down the item size, without cloning or changing the
		 * object
		 */
		int itemSizeCounter = item.stackSize;
		for (int i = 0; i < slotCount && itemSizeCounter > 0; i++) {

			if (!inventory.isItemValidForSlot(i, item)) continue;
			ItemStack inventorySlot = inventory.getStackInSlot(i);
			/*
			 * If the slot is empty, dump the biggest stack we can, taking in to
			 * consideration, the remaining amount of stack
			 */
			if (inventorySlot == null) {
				itemSizeCounter -= Math.min(Math.min(itemSizeCounter, inventory.getInventoryStackLimit()), item.getMaxStackSize());
			}
			/* If the slot is not empty, check that these items stack */
			else if (areMergeCandidates(item, inventorySlot)) {
				/* If they stack, decrement by the amount of space that remains */

				int space = inventorySlot.getMaxStackSize()
						- inventorySlot.stackSize;
				itemSizeCounter -= Math.min(itemSizeCounter, space);
			}
		}
		// itemSizeCounter might be less than zero here. It shouldn't be, but I
		// don't trust me. -NC
		if (itemSizeCounter != item.stackSize) {
			itemSizeCounter = Math.max(itemSizeCounter, 0);
			return item.stackSize - itemSizeCounter;
		}
		return 0;
	}
	
	public static boolean areMergeCandidates(ItemStack source, ItemStack target) {
		return source.isItemEqual(target)
				&& ItemStack.areItemStackTagsEqual(source, target)
				&& target.stackSize < target.getMaxStackSize();
	}
	
	public static void insertItemIntoInventory(IInventory inventory, ItemStack stack) {
		insertItemIntoInventory(inventory, stack, ForgeDirection.UNKNOWN, -1);
	}

	public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot) {
		insertItemIntoInventory(inventory, stack, side, intoSlot, true);
	}

	public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove) {
		insertItemIntoInventory(inventory, stack, side, intoSlot, doMove, true);
	}

	public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove, boolean canStack) {
		if (stack == null) return;

		IInventory targetInventory = inventory;

		// if we're not meant to move, make a clone of the inventory
		
		int i = 0;
		int[] attemptSlots = new int[0];

		// if it's a sided inventory, get all the accessible slots
		if (inventory instanceof ISidedInventory
				&& side != ForgeDirection.UNKNOWN) {
			attemptSlots = ((ISidedInventory)inventory).getAccessibleSlotsFromSide(side.ordinal());
			if (attemptSlots == null) {
				attemptSlots = new int[0];
			}
		} else {
			// if it's just a standard inventory, get all slots
			attemptSlots = new int[inventory.getSizeInventory()];
			for (int a = 0; a < inventory.getSizeInventory(); a++) {
				attemptSlots[a] = a;
			}
		}
		// if we've defining a specific slot, we'll just use that
		if (intoSlot > -1) {
			Set<Integer> x = new HashSet<Integer>();
			for (int attemptedSlot : attemptSlots) {
				x.add(attemptedSlot);
			}
			if (x.contains(intoSlot)) {
				attemptSlots = new int[] { intoSlot };
			} else {
				attemptSlots = new int[0];
			}
		}
		while (stack.stackSize > 0 && i < attemptSlots.length) {
			if (side != ForgeDirection.UNKNOWN
					&& inventory instanceof ISidedInventory) {
				if (!((ISidedInventory)inventory).canInsertItem(intoSlot, stack, side.ordinal())) {
					i++;
					continue;
				}
			}
			tryInsertStack(targetInventory, attemptSlots[i], stack, canStack);
			i++;
		}
	}
	
	public static void tryInsertStack(IInventory targetInventory, int slot, ItemStack stack, boolean canMerge) {
		if (targetInventory.isItemValidForSlot(slot, stack)) {
			ItemStack targetStack = targetInventory.getStackInSlot(slot);
			if (targetStack == null) {
				targetInventory.setInventorySlotContents(slot, stack.copy());
				stack.stackSize = 0;
			} else if (canMerge) {
				if (targetInventory.isItemValidForSlot(slot, stack) &&
						areMergeCandidates(stack, targetStack)) {
					int space = targetStack.getMaxStackSize()
							- targetStack.stackSize;
					int mergeAmount = Math.min(space, stack.stackSize);
					ItemStack copy = targetStack.copy();
					copy.stackSize += mergeAmount;
					targetInventory.setInventorySlotContents(slot, copy);
					stack.stackSize -= mergeAmount;
				}
			}
		}
	}
}
