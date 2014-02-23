package com.sr2610.steampunked.core.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SteampunkedEventHandler {

	@SubscribeEvent
	public void livingFall(LivingFallEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer))
			return;
		else {
			EntityPlayer eventPlayer = (EntityPlayer) event.entityLiving;
			if (eventPlayer.inventory.armorInventory[0] != null) {
				ItemStack boots = eventPlayer.inventory.armorInventory[0];
				if (boots.getItem() == ModItems.boots
						&& boots.getMaxDamage() - boots.getItemDamage() >= 1) {
					int d = (int) (event.distance / 2);
					if (boots.getMaxDamage() - boots.getItemDamage() > d) {

						if (event.distance > 10F)
							eventPlayer.worldObj
									.playSoundAtEntity(event.entity,
											"tile.piston.out", 0.5F, 1.0F);
						boots.setItemDamage(boots.getItemDamage() + d);
						event.distance = 0;
					}
				}
			}
		}

	}

}
