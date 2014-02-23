package com.sr2610.steampunked.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

public class EntityAiNearestTarget extends EntityAITarget {
	private final Class targetClass;
	private final int targetChance;
	/**
	 * Instance of EntityAINearestAttackableTargetSorter.
	 */
	private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
	/**
	 * This filter is applied to the Entity search. Only matching entities will
	 * be targetted. (null -> no restrictions)
	 */
	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;
	public EntityAiNearestTarget(EntityCreature par1EntityCreature,
			Class par2Class, int par3, boolean par4) {
		this(par1EntityCreature, par2Class, par3, par4, false);
	}

	public EntityAiNearestTarget(EntityCreature par1EntityCreature,
			Class par2Class, int par3, boolean par4, boolean par5) {
		this(par1EntityCreature, par2Class, par3, par4, par5,
				(IEntitySelector) null);
	}

	public EntityAiNearestTarget(EntityCreature par1EntityCreature,
			Class par2Class, int par3, boolean par4, boolean par5,
			final IEntitySelector par6IEntitySelector) {
		super(par1EntityCreature, par4, par5);
		targetClass = par2Class;
		targetChance = par3;
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(
				par1EntityCreature);
		setMutexBits(1);
		targetEntitySelector = new IEntitySelector() {
			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			@Override
			public boolean isEntityApplicable(Entity par1Entity) {
				return !(par1Entity instanceof EntityLivingBase) ? false
						: par6IEntitySelector != null
						&& !par6IEntitySelector
						.isEntityApplicable(par1Entity) ? false
								: EntityAiNearestTarget.this.isSuitableTarget(
										(EntityLivingBase) par1Entity, false);
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!(taskOwner instanceof EntityAutomaton)) {
			return false;
		} else

		{
			EntityAutomaton EA = (EntityAutomaton) taskOwner;
			if (!EA.getAttackMobs())
				return false;
			else if (targetChance > 0
					&& taskOwner.getRNG().nextInt(targetChance) != 0) {
				return false;
			} else {
				double d0 = getTargetDistance();
				List list = taskOwner.worldObj.selectEntitiesWithinAABB(
						targetClass,
						taskOwner.boundingBox.expand(d0, 4.0D, d0),
						targetEntitySelector);
				Collections.sort(list, theNearestAttackableTargetSorter);

				if (list.isEmpty()) {
					return false;
				} else {
					targetEntity = (EntityLivingBase) list.get(0);
					return true;
				}
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator {
		private final Entity theEntity;
		public Sorter(Entity par1Entity) {
			theEntity = par1Entity;
		}

		public int compare(Entity par1Entity, Entity par2Entity) {
			double d0 = theEntity.getDistanceSqToEntity(par1Entity);
			double d1 = theEntity.getDistanceSqToEntity(par2Entity);
			return d0 < d1 ? -1 : d0 > d1 ? 1 : 0;
		}

		@Override
		public int compare(Object par1Obj, Object par2Obj) {
			return this.compare((Entity) par1Obj, (Entity) par2Obj);
		}
	}
}