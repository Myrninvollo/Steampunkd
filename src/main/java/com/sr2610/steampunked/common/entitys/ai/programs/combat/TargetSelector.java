package com.sr2610.steampunked.common.entitys.ai.programs.combat;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public class TargetSelector implements IEntitySelector {

	@Override
	public boolean isEntityApplicable(Entity entity) {
		return false;
	}

}
