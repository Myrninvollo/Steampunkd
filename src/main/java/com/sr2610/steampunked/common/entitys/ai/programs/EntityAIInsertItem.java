package com.sr2610.steampunked.common.entitys.ai.programs;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

import com.sr2610.steampunked.api.utils.InventoryUtils;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;

public class EntityAIInsertItem extends EntityAIBase {

	private EntityAutomoton auto = null;

	private PathNavigate pathFinder;

	public EntityAIInsertItem(EntityAutomoton auto) {
		this.auto = auto;
		pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (auto.getDistance(auto.homeX, auto.homeY, auto.homeZ) < 2.5D)
			return true;
		else
			return false;
	}

	@Override
	public void resetTask() {
	}

	@Override
	public boolean continueExecuting() {
		return auto.isEntityAlive()
				&& auto.getDistance(auto.homeX, auto.homeY, auto.homeZ) < 2.5D;
	}

	@Override
	public void startExecuting() {
		insertItemToInventory();
	}

	@Override
	public void updateTask() {
		super.updateTask();
		if (!auto.worldObj.isRemote)
			insertItemToInventory();
	}

	private boolean insertItemToInventory() {
		IInventory iinventory = getOutputInventory(auto.worldObj, auto.homeX,
				auto.homeY, auto.homeZ);

		if (iinventory == null)
			return false;

		{

			for (int i = 0; i < auto.getSizeInventory(); ++i)
				if (auto.getStackInSlot(i) != null) {

					ItemStack itemstack = auto.getStackInSlot(i).copy();
					ItemStack itemstack1 = InventoryUtils.insertStack(
							iinventory, auto.decrStackSize(i, 1), auto.side);

					if (itemstack1 == null || itemstack1.stackSize == 0) {
						iinventory.markDirty();
						auto.markDirty();

						return true;
					}

					auto.setInventorySlotContents(i, itemstack);
				}

			return false;
		}

	}

	public static IInventory getOutputInventory(World worldObj, int x, int y,
			int z) {
		return InventoryUtils.getInventoryAtLocation(worldObj, x, y, z);
	}

}
