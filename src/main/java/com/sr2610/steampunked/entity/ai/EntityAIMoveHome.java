package com.sr2610.steampunked.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

public class EntityAIMoveHome extends EntityAIBase {

	private EntityAutomaton auto = null;

	private PathNavigate pathFinder;

	private double speed = 0.25;


	public EntityAIMoveHome(EntityAutomaton auto) {
		this.auto = auto;
		pathFinder = auto.getNavigator();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		speed = auto.speed;
		if (!pathFinder.noPath()) {
			return false;
		}
		if (auto.worldObj != null) {
			return true;

		}
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
		if (auto.posX != auto.homeX) {
			pathFinder.tryMoveToXYZ(auto.homeX, auto.homeY, auto.homeZ, speed);
		}
	}

	@Override
	public void updateTask() {
		super.updateTask();

	}
}
