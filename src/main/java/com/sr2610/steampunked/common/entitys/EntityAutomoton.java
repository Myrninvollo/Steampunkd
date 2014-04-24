/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.entitys;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAutomoton extends EntityTameable {

	private boolean healthUpgrade;
	private boolean speedUpgrade;

	public EntityAutomoton(World world) {
		super(world);
		setSize(0.6F, 0.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class,
				8.0F));

	}

	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		return null;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if (healthUpgrade)
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
					20.0D);
		else
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
					8.0D);

		if (speedUpgrade)
			getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(0.3D);
		else
			getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(0.5D);
	}

}
