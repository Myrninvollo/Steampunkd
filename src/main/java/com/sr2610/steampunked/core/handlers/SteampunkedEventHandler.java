package com.sr2610.steampunked.core.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SteampunkedEventHandler {
	
	  public boolean resetRender = false;
	  public static List<String> holograms = new ArrayList();
	  boolean avoidRecursion = false;

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
	
	@SubscribeEvent
	 
	  public void entityColorRender(RenderLivingEvent.Pre event)
	  {
	    String s = EnumChatFormatting.getTextWithoutFormattingCodes(event.entity.getCommandSenderName());
	this.resetRender = true;
	    
	    if ((holograms.contains(s)) && ((!(event.entity instanceof EntityPlayer)) || (!((EntityPlayer)event.entity).isInvisible())))
	    {
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.45F);
	      GL11.glEnable(3042);
	      GL11.glBlendFunc(770, 771);
	      this.resetRender = true;
	    }
	    if (((s.toLowerCase()).equals("sr2610")) && ((!(event.entity instanceof EntityPlayer)) || (!((EntityPlayer)event.entity).isInvisible())))
	    {
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.65F);
	      GL11.glEnable(3042);
	      GL11.glBlendFunc(770, 771);
	      this.resetRender = true;
	    }
	    
	    if (((s.toLowerCase()).equals("danh37")) && ((!(event.entity instanceof EntityPlayer))))
	    {
	      GL11.glColor4f(1F,0.392F, 0.392F, 1.0F);
	      this.resetRender = true;
	    }
	    
	    if (((s.toLowerCase()).equals("flarehayr")) && ((!(event.entity instanceof EntityPlayer))))
	    {
	      GL11.glColor4f(1F,0.7F, 0.392F, 1.0F);
	      this.resetRender = true;
	    }

}
	  
	  @SubscribeEvent
	  public void entityColorRender(RenderLivingEvent.Post event)
	  {
	    if ((!this.avoidRecursion) && (this.resetRender))
	    {
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glDisable(3042);
	    }
	  }

}
