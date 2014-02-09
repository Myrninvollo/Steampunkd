package com.sr2610.steampunked.core.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SteampunkedEventHandler {

	@SubscribeEvent
	public void livingFall(LivingFallEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer))
			return;
		else {
			EntityPlayer eventPlayer = (EntityPlayer) event.entityLiving;
			if (eventPlayer.inventory.armorInventory[3] != null) {
				ItemStack boots = eventPlayer.inventory.armorInventory[3];
				if ((boots.getItem() == ModItems.boots)
						&& (boots.getMaxDamage() - boots.getItemDamage() >= 1)) {
					if (event.distance > 10F)
						eventPlayer.worldObj.playSoundAtEntity(event.entity,
								"tile.piston.out", 0.5F, 1.0F);
					int d = (int) (event.distance / 2);
					boots.setItemDamage(boots.getItemDamage() + d);
					event.isCanceled();
				}
			}
		}

	}

}
