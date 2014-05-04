/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the Steampunk'd Mod. 
 * Get the Source Code in Github: https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.entitys.ai.programs.combat;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public class TargetSelector implements IEntitySelector {

	@Override
	public boolean isEntityApplicable(Entity entity) {
		return false;
	}

}
