package steampunked.entity.ai;

import java.util.Iterator;
import java.util.List;

import steampunked.entity.automatons.EntityAutomoton;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIHurt extends EntityAITarget
{
    boolean entityCallsForHelp;
    private int field_142052_b;
    private EntityAutomoton auto = null;

    public EntityAIHurt(EntityCreature par1EntityCreature, boolean par2)
    {
        super(par1EntityCreature, false);
        this.entityCallsForHelp = par2;
        this.setMutexBits(1);
        this.auto = (EntityAutomoton) this.taskOwner;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(this.taskOwner instanceof EntityAutomoton){
    		if(auto.getAttackMobs() == true){
    		 int i = this.taskOwner.func_142015_aE();
            return i != this.field_142052_b && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    	}
    	}
		return false;
       
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.field_142052_b = this.taskOwner.func_142015_aE();

        if (this.entityCallsForHelp)
        {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityCreature entitycreature = (EntityCreature)iterator.next();

                if (this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && !entitycreature.isOnSameTeam(this.taskOwner.getAITarget()))
                {
                    entitycreature.setAttackTarget(this.taskOwner.getAITarget());
                }
            }
        }

        super.startExecuting();
    }
}
