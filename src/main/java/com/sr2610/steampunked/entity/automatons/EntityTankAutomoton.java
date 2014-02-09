package com.sr2610.steampunked.entity.automatons;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

import com.sr2610.steampunked.entity.ai.EntityAIMoveHome;

public class EntityTankAutomoton extends EntityAutomaton{

	public EntityTankAutomoton(World par1World) {
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
      tasks.addTask(4, new EntityAIMoveHome(this));

	}
	
	
}
