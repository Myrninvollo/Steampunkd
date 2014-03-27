/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.entity.ai;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

public class EntityAICollectItem extends EntityAIBase {

	private EntityAutomaton auto = null;

	private PathNavigate pathFinder;

	private EntityItem targetItem = null;

	private double range = 5.0;

	public EntityAICollectItem(EntityAutomaton auto) {
		this.auto = auto;
		pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		range = auto.range;
		if (!pathFinder.noPath())
			return false;
		if (auto.getPickup() != true)
			return false;

		else if (auto.worldObj != null)
			if (auto.getStackInSlot(0) == null) {

				List<EntityItem> items = auto.worldObj.getEntitiesWithinAABB(
						EntityItem.class,
						AxisAlignedBB
								.getAABBPool()
								.getAABB(auto.posX - 1, auto.posY - 1,
										auto.posZ - 1, auto.posX + 1,
										auto.posY + 1, auto.posZ + 1)
								.expand(range, range, range));
				EntityItem closest = null;
				double closestDistance = Double.MAX_VALUE;
				for (EntityItem item : items)
					if (!item.isDead && item.onGround) {
						double dist = item.getDistanceToEntity(auto); // Check

						if (closest == null || dist < closestDistance
								&& !item.isInWater()) {
							closest = item;
							closestDistance = dist;
						}
					}
				if (closest != null) {
					targetItem = closest;
					return true;
				}
			}
		return false;
	}

	@Override
	public void resetTask() {
		pathFinder.clearPathEntity();
		targetItem = null;
	}

	@Override
	public boolean continueExecuting() {
		return auto.isEntityAlive() && !pathFinder.noPath()
				&& !targetItem.isDead;
	}

	@Override
	public void startExecuting() {
		if (targetItem != null)
			pathFinder.tryMoveToXYZ(targetItem.posX, targetItem.posY,
					targetItem.posZ, 1.0D);
	}

	@Override
	public void updateTask() {
		super.updateTask();
		if (!auto.worldObj.isRemote)
			if (targetItem != null
					&& auto.getDistanceToEntity(targetItem) < 1.5) {
				ItemStack stack = targetItem.getEntityItem();
				auto.setInventorySlotContents(0, stack);
				auto.markDirty();
				targetItem.setDead();

			}
	}
}
