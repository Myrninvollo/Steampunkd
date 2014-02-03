package steampunked.core.handlers;

import steampunked.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SteampunkedEventHandler {

	@ForgeSubscribe
	public void livingFall(LivingFallEvent event) {

		if (!(event.entityLiving instanceof EntityPlayer))
			return;
		EntityPlayer eventPlayer = (EntityPlayer) event.entityLiving;
		if (eventPlayer.getCurrentItemOrArmor(1) != null) {
			ItemStack boots = eventPlayer.getCurrentItemOrArmor(1);
			if ((boots.getItem() == ModItems.boots) &&  (boots.getMaxDamage()-boots.getItemDamage() >= 1)) {
				if (event.distance > 10F)
					eventPlayer.worldObj.playSoundAtEntity(event.entity,
							"tile.piston.out", 0.5F, 1.0F);
				int d = (int) (event.distance/10);
				boots.setItemDamage(boots.getItemDamage()+d);
				event.distance = 0F;

			}
		}

	}

}
