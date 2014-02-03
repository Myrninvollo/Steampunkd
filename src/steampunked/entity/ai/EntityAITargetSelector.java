package steampunked.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAITargetSelector implements IEntitySelector
{
    final IEntitySelector field_111103_c;

    final EntityAiNearestTarget field_111102_d;

    EntityAITargetSelector(EntityAiNearestTarget par1EntityAINearestAttackableTarget, IEntitySelector par2IEntitySelector)
    {
        this.field_111102_d = par1EntityAINearestAttackableTarget;
        this.field_111103_c = par2IEntitySelector;
    }

    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity par1Entity)
    {
        return !(par1Entity instanceof EntityLivingBase) ? false : (this.field_111103_c != null && !this.field_111103_c.isEntityApplicable(par1Entity) ? false : this.field_111102_d.isSuitableTarget((EntityLivingBase)par1Entity, false));
    }
}
