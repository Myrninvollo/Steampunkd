/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.api.utils.Utils;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.lib.LibOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SteampunkedEventHandler {

	public boolean resetRender = false;
	boolean avoidRecursion = false;
	public static final String GIVEN_HANDBOOK_TAG = "givenHandbook";

	@SubscribeEvent
	public void livingFall(LivingFallEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer))
			return;
		else {
			final EntityPlayer eventPlayer = (EntityPlayer) event.entityLiving;
			if (eventPlayer.inventory.armorInventory[0] != null) {
				final ItemStack boots = eventPlayer.inventory.armorInventory[0];
				if ((boots.getItem() == ModItems.boots || boots.getItem() == ModItems.mechBoots)
						&& boots.getMaxDamage() - boots.getItemDamage() >= 1) {
					final int d = (int) (event.distance / 2);
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
	public void entityColorRender(RenderLivingEvent.Pre event) {
		final String s = EnumChatFormatting
				.getTextWithoutFormattingCodes(event.entity
						.getCommandSenderName());
		resetRender = true;

		if (s.toLowerCase().equals("sr2610")
				&& (!(event.entity instanceof EntityPlayer) || !((EntityPlayer) event.entity)
						.getHideCape())) {
			GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.85F);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			resetRender = true;
		}

		if (s.toLowerCase().equals("danh37")
				&& (!(event.entity instanceof EntityPlayer) || !((EntityPlayer) event.entity)
						.getHideCape())) {
			GL11.glColor4f(1F, 0.392F, 0.392F, 1.0F);
			resetRender = true;
		}

		if (s.toLowerCase().equals("flarehayr")
				&& (!(event.entity instanceof EntityPlayer) || !((EntityPlayer) event.entity)
						.getHideCape())) {
			GL11.glColor4f(1F, 0.7F, 0.392F, 1.0F);
			resetRender = true;
		}

	}

	@SubscribeEvent
	public void entityColorRender(RenderLivingEvent.Post event) {
		if (!avoidRecursion && resetRender) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3042);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.entity;
		if (!event.world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound persistTag = Utils.getModPlayerPersistTag(player,
					"Steampunked");

			boolean shouldGiveHandbook = !persistTag
					.getBoolean(GIVEN_HANDBOOK_TAG)
					&& ConfigHandler.giveHandbook == true;
			if (shouldGiveHandbook) {
				ItemStack manual = new ItemStack(ModItems.handBook);
				if (!player.inventory.addItemStackToInventory(manual)) {
					Utils.dropItemStackInWorld(player.worldObj, player.posX,
							player.posY, player.posZ, manual);
				}
				persistTag.setBoolean(GIVEN_HANDBOOK_TAG, true);
			}

		}
	}

	@SubscribeEvent
	public void checkVersion(EntityJoinWorldEvent event) {

		try {
			if (Utils.isUpdateAvailable()) {
				if (event.entity instanceof EntityPlayer)
					if (!event.entity.worldObj.isRemote) {
						Steampunked.logger.info("Update Avalible");

						Utils.sendPlayerChatMessage(
								(EntityPlayer) event.entity,
								EnumChatFormatting.RED
										+ "[Steampunk'd] "
										+ EnumChatFormatting.RESET
										+ "A new version is available to download");
					}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
