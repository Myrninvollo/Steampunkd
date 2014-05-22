package com.sr2610.steampunked.common.entitys.ai.programs;

import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;

import com.sr2610.steampunked.api.utils.InventoryUtils;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;

public class EntityAICollectItem extends EntityAIBase {

	private EntityAutomoton auto = null;

	private PathNavigate pathFinder;

	private EntityItem targetItem = null;

	public EntityAICollectItem(EntityAutomoton auto) {
		this.auto = auto;
		pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (!pathFinder.noPath())
			return false;
		if (auto.worldObj != null) {
			@SuppressWarnings("unchecked")
			List<EntityItem> items = auto.worldObj.getEntitiesWithinAABB(
					EntityItem.class,
					AxisAlignedBB
							.getAABBPool()
							.getAABB(auto.posX - 1, auto.posY - 1,
									auto.posZ - 1, auto.posX + 1,
									auto.posY + 1, auto.posZ + 1)
							.expand(10.0, 10.0, 10.0));
			EntityItem closest = null;
			double closestDistance = Double.MAX_VALUE;
			for (EntityItem item : items)
				if (!item.isDead && item.onGround) {
					double dist = item.getDistanceToEntity(auto);
					if (dist < closestDistance
							&& auto.canConsumeStackPartially(item
									.getEntityItem()) && !item.isInWater()) {
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
			pathFinder.tryMoveToXYZ(
					targetItem.posX,
					targetItem.posY,
					targetItem.posZ,
					auto.getEntityAttribute(
							SharedMonsterAttributes.movementSpeed)
							.getAttributeValue());
	}

	@Override
	public void updateTask() {
		super.updateTask();
		if (!auto.worldObj.isRemote)
			if (targetItem != null
					&& auto.getDistanceToEntity(targetItem) < 1.0) {
				ItemStack stack = targetItem.getEntityItem();
				int preEatSize = stack.stackSize;
				InventoryUtils.insertItemIntoInventory(auto, stack);
				auto.markDirty();
				// Check that the size changed
				if (preEatSize != stack.stackSize)
					if (stack.stackSize == 0)
						targetItem.setDead();
			}
	}
}
