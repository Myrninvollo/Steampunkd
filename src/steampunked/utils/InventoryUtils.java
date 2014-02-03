package steampunked.utils;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class InventoryUtils {

	/***
	 * Try to merge the supplied stack into the supplied slot in the target
	 * inventory
	 * 
	 * @param targetInventory
	 *            Although it doesn't return anything, it'll REDUCE the stack
	 *            size of the stack that you pass in
	 * 
	 * @param slot
	 * @param stack
	 */
	public static void tryMergeStacks(IInventory targetInventory, int slot,
			ItemStack stack) {
		if (targetInventory.isItemValidForSlot(slot, stack)) {
			ItemStack targetStack = targetInventory.getStackInSlot(slot);
			if (targetStack == null) {
				targetInventory.setInventorySlotContents(slot, stack.copy());
				stack.stackSize = 0;
			} else {
				boolean valid = targetInventory.isItemValidForSlot(slot, stack);
				if (valid
						&& stack.isItemEqual(targetStack)
						&& targetStack.stackSize < targetStack
						.getMaxStackSize()) {
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

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack) {
		insertItemIntoInventory(inventory, stack, ForgeDirection.UNKNOWN, -1);
	}

	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack, ForgeDirection side, int intoSlot) {
		insertItemIntoInventory(inventory, stack, side, intoSlot, true);
	}

	/***
	 * Insert the stack into the target inventory. Pass -1 if you don't care
	 * which slot
	 * 
	 * @param inventory
	 * @param stack
	 * @param side
	 *            The side of the block you're inserting into
	 */
	public static void insertItemIntoInventory(IInventory inventory,
			ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove) {

		if (stack == null) {
			return;
		}

		IInventory targetInventory = inventory;
		
		int i = 0;
		int[] attemptSlots = new int[0];

		if (inventory instanceof ISidedInventory
				&& side != ForgeDirection.UNKNOWN) {
			attemptSlots = ((ISidedInventory) inventory)
					.getAccessibleSlotsFromSide(side.ordinal());
			if (attemptSlots == null) {
				attemptSlots = new int[0];
			}
		} else {
			attemptSlots = new int[inventory.getSizeInventory()];
			for (int a = 0; a < inventory.getSizeInventory(); a++) {
				attemptSlots[a] = a;
			}
		}
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
		ItemStack is = new ItemStack(stack.getItem(),1, stack.getItemDamage());

		while (stack.stackSize > 0 && i < attemptSlots.length ) {
			
			if (side != ForgeDirection.UNKNOWN
					&& inventory instanceof ISidedInventory) {
				
				if (!((ISidedInventory) inventory).canInsertItem(intoSlot,
						is, side.ordinal())) {
					stack.stackSize--;
					i++;
					if (stack.stackSize ==0){
						stack = null;
					}
					continue;
				}
			}
			tryMergeStacks(targetInventory, attemptSlots[i], is);
			i++;
		}
	}

	/***
	 * Move an item from the fromInventory, into the target. The target can be
	 * an inventory or pipe. Double checks are automagically wrapped. If you're
	 * not bothered what slot you insert into, pass -1 for intoSlot. If you're
	 * passing false for doMove, it'll create a dummy inventory and make its
	 * calculations on that instead
	 * 
	 * @param fromInventory
	 *            the inventory the item is coming from
	 * @param fromSlot
	 *            the slot the item is coming from
	 * @param target
	 *            the inventory you want the item to be put into. can be BC pipe
	 *            or IInventory
	 * @param intoSlot
	 *            the target slot. Pass -1 for any slot
	 * @param maxAmount
	 *            The maximum amount you wish to pass
	 * @param direction
	 *            The direction of the move. Pass UNKNOWN if not applicable
	 * @param doMove
	 * @return The amount of items moved
	 */
	public static int moveItemInto(IInventory fromInventory, int fromSlot,
			Object target, int intoSlot, int maxAmount,
			ForgeDirection direction, boolean doMove) {

		fromInventory = InventoryUtils.getInventory(fromInventory);

		ItemStack sourceStack = fromInventory.getStackInSlot(fromSlot);
		if (sourceStack == null) {
			return 0;
		}

		if (fromInventory instanceof ISidedInventory
				&& !((ISidedInventory) fromInventory).canExtractItem(fromSlot,
						sourceStack, direction.ordinal())) {
			return 0;
		}

		ItemStack clonedSourceStack = sourceStack.copy();
		clonedSourceStack.stackSize = Math.min(clonedSourceStack.stackSize,
				maxAmount);
		int amountToMove = 1;
		int inserted = 0;

		if (target instanceof IInventory) {
			IInventory targetInventory = getInventory((IInventory) target);
			ForgeDirection side = direction.getOpposite();

			InventoryUtils.insertItemIntoInventory(targetInventory,
					clonedSourceStack, side, intoSlot, doMove);
			inserted = amountToMove - clonedSourceStack.stackSize;

		}

		if (doMove) {
			ItemStack newSourcestack = sourceStack.copy();
			newSourcestack.stackSize -= inserted;
			if (newSourcestack.stackSize == 0) {
				fromInventory.setInventorySlotContents(fromSlot, null);
			} else {
				fromInventory
				.setInventorySlotContents(fromSlot, newSourcestack);
			}
		}

		return inserted;
	}

	/***
	 * Returns the inventory at the passed in coordinates. If it's a double
	 * chest it'll wrap the inventory
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static IInventory getInventory(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if ((tileEntity != null) && ((tileEntity instanceof IInventory))) {
			int blockID = world.getBlockId(x, y, z);
			Block block = Block.blocksList[blockID];
			if ((block instanceof BlockChest)) {
				if (world.getBlockId(x - 1, y, z) == blockID) {
					return new InventoryLargeChest("Large chest",
							(IInventory) world.getBlockTileEntity(x - 1, y, z),
							(IInventory) tileEntity);
				}
				if (world.getBlockId(x + 1, y, z) == blockID) {
					return new InventoryLargeChest("Large chest",
							(IInventory) tileEntity,
							(IInventory) world.getBlockTileEntity(x + 1, y, z));
				}
				if (world.getBlockId(x, y, z - 1) == blockID) {
					return new InventoryLargeChest("Large chest",
							(IInventory) world.getBlockTileEntity(x, y, z - 1),
							(IInventory) tileEntity);
				}
				if (world.getBlockId(x, y, z + 1) == blockID) {
					return new InventoryLargeChest("Large chest",
							(IInventory) tileEntity,
							(IInventory) world.getBlockTileEntity(x, y, z + 1));
				}
			}
			return (IInventory) tileEntity;
		}
		return null;
	}

	/***
	 * Gets the inventory relative to the passed in coordinates.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param direction
	 * @return
	 */
	public static IInventory getInventory(World world, int x, int y, int z,
			ForgeDirection direction) {
		if (direction != null && direction != ForgeDirection.UNKNOWN) {
			x += direction.offsetX;
			y += direction.offsetY;
			z += direction.offsetZ;
		}
		return getInventory(world, x, y, z);

	}

	public static IInventory getInventory(IInventory inventory) {
		if (inventory instanceof TileEntity) {
			TileEntity te = (TileEntity) inventory;
			return getInventory(te.worldObj, te.xCoord, te.yCoord, te.zCoord);
		}
		return inventory;
	}

	/***
	 * Get the indexes of all slots containing a stack of the supplied item
	 * type.
	 * 
	 * @param inventory
	 * @param stack
	 * @return Returns a set of the slot indexes
	 */
	public static Set<Integer> getSlotsWithStack(IInventory inventory,
			ItemStack stack) {
		inventory = getInventory(inventory);
		Set<Integer> slots = new HashSet<Integer>();
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = inventory.getStackInSlot(i);
			if (stackInSlot != null && stackInSlot.isItemEqual(stack)) {
				slots.add(i);
			}
		}
		return slots;
	}

	/***
	 * Get the first slot containing an item type matching the supplied type.
	 * 
	 * @param inventory
	 * @param stack
	 * @return Returns -1 if none found
	 */
	public static int getFirstSlotWithStack(IInventory inventory,
			ItemStack stack) {
		inventory = getInventory(inventory);
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = inventory.getStackInSlot(i);
			if (stackInSlot != null && stackInSlot.isItemEqual(stack)) {
				return i;
			}
		}
		return -1;
	}

	/***
	 * Consume ONE of the supplied item types
	 * 
	 * @param inventory
	 * @param stack
	 * @return Returns whether or not it was able to
	 */
	public static boolean consumeInventoryItem(IInventory inventory,
			ItemStack stack) {
		int slotWithStack = getFirstSlotWithStack(inventory, stack);
		if (slotWithStack > -1) {
			ItemStack stackInSlot = inventory.getStackInSlot(slotWithStack);
			stackInSlot.stackSize--;
			if (stackInSlot.stackSize == 0) {
				inventory.setInventorySlotContents(slotWithStack, null);
			}
			return true;
		}
		return false;
	}

	/**
	 * Get the first slot index in an inventory with an item
	 * 
	 * @param invent
	 * @return The slot index, or -1 if the inventory is empty
	 */
	public static int getSlotIndexOfNextStack(IInventory invent) {
		for (int i = 0; i < invent.getSizeInventory(); i++) {
			ItemStack stack = invent.getStackInSlot(i);
			if (stack != null) {
				return i;
			}
		}
		return -1;
	}

	/***
	 * Removes an item stack from the inventory and returns a copy of it
	 * 
	 * @param invent
	 * @return A copy of the stack it removed
	 */
	public static ItemStack removeNextItemStack(IInventory invent) {
		int nextFilledSlot = getSlotIndexOfNextStack(invent);
		if (nextFilledSlot > -1) {
			ItemStack copy = invent.getStackInSlot(nextFilledSlot).copy();
			invent.setInventorySlotContents(nextFilledSlot, null);
			return copy;
		}
		return null;
	}

	/**
	 * Tests to see if an item stack can be inserted in to an inventory Does not
	 * perform the insertion, only tests the possibility
	 * 
	 * @param inventory
	 *            The inventory to insert the stack into
	 * @param item
	 *            the stack to insert
	 * @return the amount of items that could be put in to the stack
	 */
	public static int testInventoryInsertion(IInventory inventory,
			ItemStack item) {
		if (item == null || item.stackSize == 0)
			return 0;
		if (inventory == null)
			return 0;
		int slotCount = inventory.getSizeInventory();

		int itemSizeCounter = item.stackSize;
		for (int i = 0; i < slotCount && itemSizeCounter > 0; i++) {

			if (!inventory.isItemValidForSlot(i, item))
				continue;
			ItemStack inventorySlot = inventory.getStackInSlot(i);

			if (inventorySlot == null) {
				itemSizeCounter -= Math.min(
						Math.min(itemSizeCounter,
								inventory.getInventoryStackLimit()),
								item.getMaxStackSize());
			} else if (item.isItemEqual(inventorySlot)
					&& inventorySlot.stackSize < inventorySlot
					.getMaxStackSize()) {

				int space = inventorySlot.getMaxStackSize()
						- inventorySlot.stackSize;
				itemSizeCounter -= Math.min(itemSizeCounter, space);
			}
		}

		if (itemSizeCounter != item.stackSize) {
			itemSizeCounter = Math.max(itemSizeCounter, 0);
			return item.stackSize - itemSizeCounter;
		}
		return 0;
	}

	public static Set<Integer> getAllSlots(IInventory inventory) {
		inventory = getInventory(inventory);
		Set<Integer> slots = new HashSet<Integer>();
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			slots.add(i);
		}
		return slots;
	}

	public static boolean inventoryIsEmpty(IInventory inventory) {
		for (int i = 0, l = inventory.getSizeInventory(); i < l; i++)
			if (inventory.getStackInSlot(i) != null)
				return false;
		return true;
	}
}