/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.entitys.ai;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

import com.sr2610.steampunked.common.entitys.EntityAutomoton;

public class EntityAIMoveHome extends EntityAIBase {

	private EntityAutomoton auto = null;

	private PathNavigate pathFinder;

	public EntityAIMoveHome(EntityAutomoton auto) {
		this.auto = auto;
		pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if (!pathFinder.noPath())
			return false;
		if (auto.worldObj != null)
			return true;
		return false;
	}

	@Override
	public void resetTask() {
		pathFinder.clearPathEntity();
	}

	@Override
	public boolean continueExecuting() {
		return auto.isEntityAlive() && !pathFinder.noPath();

	}

	@Override
	public void startExecuting() {
		if (auto.posX != auto.homeX)
			pathFinder.tryMoveToXYZ(auto.homeX, auto.homeY, auto.homeZ, auto
					.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.getAttributeValue());
	}

	@Override
	public void updateTask() {
		super.updateTask();

	}
}
