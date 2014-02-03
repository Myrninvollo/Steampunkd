package steampunked.entity.automatons;

import steampunked.entity.ai.EntityAIMoveHome;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTankAutomoton extends EntityAutomoton{

	public EntityTankAutomoton(World par1World) {
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
      tasks.addTask(4, new EntityAIMoveHome(this));

	}
	
	
}
