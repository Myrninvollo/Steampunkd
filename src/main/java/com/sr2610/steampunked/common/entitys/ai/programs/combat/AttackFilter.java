package com.sr2610.steampunked.common.entitys.ai.programs.combat;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;

public final class AttackFilter implements IEntitySelector {
	/**
	 * Return whether the specified entity is applicable to this filter.
	 */
	@Override
	public boolean isEntityApplicable(Entity par1Entity) {
		return par1Entity instanceof IMob
				&& !(par1Entity instanceof EntityCreeper);
	}
}
