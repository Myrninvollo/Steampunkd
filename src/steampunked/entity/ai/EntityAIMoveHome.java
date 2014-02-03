package steampunked.entity.ai;

import java.util.List;

import steampunked.entity.automatons.EntityAutomoton;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIMoveHome extends EntityAIBase {

    private EntityAutomoton auto = null;

    private PathNavigate pathFinder;


    public EntityAIMoveHome(EntityAutomoton auto) {
            this.auto = auto;
            this.pathFinder = auto.getNavigator();
            setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
            if (!pathFinder.noPath()) { return false; }
            if (auto.worldObj != null) {
            	return true;
       
            }
            return false;
    }

    @Override
    public void resetTask() {
            pathFinder.clearPathEntity();
    }

    @Override
    public boolean continueExecuting() {
            return auto.isEntityAlive() && !pathFinder.noPath();
                            
    }

    @Override
    public void startExecuting() {
            if (auto.posX!=auto.homeX) {
                    pathFinder.tryMoveToXYZ((double)auto.homeX,(double)auto.homeY, (double)auto.homeZ,1.0);
            }
    }

    @Override
    public void updateTask() {
            super.updateTask();
           
                    }
            }
   