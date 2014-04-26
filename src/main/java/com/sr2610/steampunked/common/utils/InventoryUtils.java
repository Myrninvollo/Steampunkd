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
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class InventoryUtils {

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

	public static int testInventoryInsertion(IInventory inventory,
			ItemStack item) {
		if (item == null || item.stackSize == 0)
			return 0;
		if (inventory == null)
			return 0;
		int slotCount = inventory.getSizeInventory();
		/*
		 * Allows counting down the item size, without cloning or changing the
		 * object
		 */
		int itemSizeCounter = item.stackSize;
		for (int i = 0; i < slotCount && itemSizeCounter > 0; i++) {

			if (!inventory.isItemValidForSlot(i, item))
				continue;
			ItemStack inventorySlot = inventory.getStackInSlot(i);
			/*
			 * If the slot is empty, dump the biggest stack we can, taking in to
			 * consideration, the remaining amount of stack
			 */
			if (inventorySlot == null) {
				itemSizeCounter -= Math.min(
						Math.min(itemSizeCounter,
								inventory.getInventoryStackLimit()),
						item.getMaxStackSize());
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

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack) {
		insertItemIntoInventory(inventory, stack, ForgeDirection.UNKNOWN, -1);
	}

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack, ForgeDirection side, int intoSlot) {
		insertItemIntoInventory(inventory, stack, side, intoSlot, true);
	}

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove) {
		insertItemIntoInventory(inventory, stack, side, intoSlot, doMove, true);
	}

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove,
			boolean canStack) {
		if (stack == null)
			return;

		IInventory targetInventory = inventory;

		// if we're not meant to move, make a clone of the inventory

		int i = 0;
		int[] attemptSlots = new int[0];

		// if it's a sided inventory, get all the accessible slots
		if (inventory instanceof ISidedInventory
				&& side != ForgeDirection.UNKNOWN) {
			attemptSlots = ((ISidedInventory) inventory)
					.getAccessibleSlotsFromSide(side.ordinal());
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
				if (!((ISidedInventory) inventory).canInsertItem(intoSlot,
						stack, side.ordinal())) {
					i++;
					continue;
				}
			}
			tryInsertStack(targetInventory, attemptSlots[i], stack, canStack);
			i++;
		}
	}

	public static void tryInsertStack(IInventory targetInventory, int slot,
			ItemStack stack, boolean canMerge) {
		if (targetInventory.isItemValidForSlot(slot, stack)) {
			ItemStack targetStack = targetInventory.getStackInSlot(slot);
			if (targetStack == null) {
				targetInventory.setInventorySlotContents(slot, stack.copy());
				stack.stackSize = 0;
			} else if (canMerge) {
				if (targetInventory.isItemValidForSlot(slot, stack)
						&& areMergeCandidates(stack, targetStack)) {
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
	
	public static IInventory getInventoryAtLocation(World par0World,
			double par1, double par3, double par5) {
		IInventory iinventory = null;
		int i = MathHelper.floor_double(par1);
		int j = MathHelper.floor_double(par3);
		int k = MathHelper.floor_double(par5);
		TileEntity tileentity = par0World.getTileEntity(i, j, k);

		if (tileentity != null && tileentity instanceof IInventory) {
			iinventory = (IInventory) tileentity;

			if (iinventory instanceof TileEntityChest) {
				Block l = par0World.getBlock(i, j, k);

				if (l instanceof BlockChest)
					iinventory = ((BlockChest) l).func_149951_m(par0World, i,
							j, k);
			}
		}

		if (iinventory == null) {
			List list = par0World.getEntitiesWithinAABBExcludingEntity(
					(Entity) null,
					AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5,
							par1 + 1.0D, par3 + 1.0D, par5 + 1.0D),
					IEntitySelector.selectInventories);

			if (list != null && list.size() > 0)
				iinventory = (IInventory) list.get(par0World.rand.nextInt(list
						.size()));
		}

		return iinventory;
	}
	
	public static ItemStack insertStack(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2) {
		if (par0IInventory instanceof ISidedInventory && par2 > -1) {
			ISidedInventory isidedinventory = (ISidedInventory) par0IInventory;
			int[] aint = isidedinventory.getAccessibleSlotsFromSide(par2);

			for (int j = 0; j < aint.length && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++j)
				par1ItemStack = transferItems(par0IInventory, par1ItemStack,
						aint[j], par2);
		} else {
			int k = par0IInventory.getSizeInventory();

			for (int l = 0; l < k && par1ItemStack != null
					&& par1ItemStack.stackSize > 0; ++l)
				par1ItemStack = transferItems(par0IInventory, par1ItemStack, l,
						par2);
		}

		if (par1ItemStack != null && par1ItemStack.stackSize == 0)
			par1ItemStack = null;

		return par1ItemStack;
	}
	
	private static ItemStack transferItems(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2, int par3) {
		ItemStack itemstack1 = par0IInventory.getStackInSlot(par2);

		if (canInsertItemToInventory(par0IInventory, par1ItemStack, par2, par3))
			if (itemstack1 == null) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max >= par1ItemStack.stackSize) {
					par0IInventory
							.setInventorySlotContents(par2, par1ItemStack);
					par1ItemStack = null;
				} else
					par0IInventory.setInventorySlotContents(par2,
							par1ItemStack.splitStack(max));
			} else if (areItemStacksEqualItem(itemstack1, par1ItemStack)) {
				int max = Math.min(par1ItemStack.getMaxStackSize(),
						par0IInventory.getInventoryStackLimit());
				if (max > itemstack1.stackSize) {
					int l = Math.min(par1ItemStack.stackSize, max
							- itemstack1.stackSize);
					par1ItemStack.stackSize -= l;
					itemstack1.stackSize += l;
				}
			}

		return par1ItemStack;
	}
	
	private static boolean areItemStacksEqualItem(ItemStack par0ItemStack,
			ItemStack par1ItemStack) {
		return par0ItemStack.getItem() != par1ItemStack.getItem() ? false
				: par0ItemStack.getItemDamage() != par1ItemStack
						.getItemDamage() ? false
						: par0ItemStack.stackSize > par0ItemStack
								.getMaxStackSize() ? false : ItemStack
								.areItemStackTagsEqual(par0ItemStack,
										par1ItemStack);
	}
	

	private static boolean canInsertItemToInventory(IInventory par0IInventory,
			ItemStack par1ItemStack, int par2, int par3) {
		return !par0IInventory.isItemValidForSlot(par2, par1ItemStack) ? false
				: !(par0IInventory instanceof ISidedInventory)
						|| ((ISidedInventory) par0IInventory).canInsertItem(
								par2, par1ItemStack, par3);
	}

}
