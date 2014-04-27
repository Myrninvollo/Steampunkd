/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.entitys.ai.programs;

import com.sr2610.steampunked.common.entitys.EntityAutomoton;
import com.sr2610.steampunked.common.utils.InventoryUtils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityAIInsertItem extends EntityAIBase {

	private EntityAutomoton auto = null;

	private PathNavigate pathFinder;

	public EntityAIInsertItem(EntityAutomoton auto) {
		this.auto = auto;
		this.pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if ((auto.getDistance(auto.homeX, auto.homeY, auto.homeZ) < 2.5D))
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
		if (!auto.worldObj.isRemote) {
			insertItemToInventory();

		}
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
