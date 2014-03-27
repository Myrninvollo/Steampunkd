/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.entity.ai;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

public class EntityAIHurt extends EntityAITarget {
	boolean entityCallsForHelp;
	private int field_142052_b;
	private EntityAutomaton auto = null;

	public EntityAIHurt(EntityCreature par1EntityCreature, boolean par2) {
		super(par1EntityCreature, false);
		entityCallsForHelp = par2;
		setMutexBits(1);
		auto = (EntityAutomaton) taskOwner;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (taskOwner instanceof EntityAutomaton)
			if (auto.getAttackMobs() == true) {
				int i = taskOwner.func_142015_aE();
				return i != field_142052_b
						&& isSuitableTarget(taskOwner.getAITarget(), false);
			}
		return false;

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(taskOwner.getAITarget());
		field_142052_b = taskOwner.func_142015_aE();

		if (entityCallsForHelp) {
			double d0 = getTargetDistance();
			List list = taskOwner.worldObj.getEntitiesWithinAABB(
					taskOwner.getClass(),
					AxisAlignedBB
							.getAABBPool()
							.getAABB(taskOwner.posX, taskOwner.posY,
									taskOwner.posZ, taskOwner.posX + 1.0D,
									taskOwner.posY + 1.0D,
									taskOwner.posZ + 1.0D)
							.expand(d0, 10.0D, d0));
			Iterator iterator = list.iterator();

			while (iterator.hasNext()) {
				EntityCreature entitycreature = (EntityCreature) iterator
						.next();

				if (taskOwner != entitycreature
						&& entitycreature.getAttackTarget() == null
						&& !entitycreature
								.isOnSameTeam(taskOwner.getAITarget()))
					entitycreature.setAttackTarget(taskOwner.getAITarget());
			}
		}

		super.startExecuting();
	}
}
