/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.sr2610.steampunked.Steampunked;

public class Utils {

	public static String getFluidLocalisedName(FluidStack stack) {
		return StatCollector.translateToLocal("fluid."
				+ FluidRegistry.getFluidName(stack));

	}

	public static void sendPlayerChatMessage(EntityPlayer player, String message) {
		player.addChatMessage(new ChatComponentTranslation(message));
	}

	public static void sendPlayerLocalisedChatMessage(EntityPlayer player,
			String message) {
		player.addChatMessage(new ChatComponentTranslation(StatCollector
				.translateToLocal(message)));
	}

	public static int getIntDirFromDirection(ForgeDirection dir) {
		switch (dir) {
		case DOWN:
			return 0;
		case EAST:
			return 5;
		case NORTH:
			return 2;
		case SOUTH:
			return 3;
		case UNKNOWN:
			return 0;
		case UP:
			return 1;
		case WEST:
			return 4;
		default:
			return 0;
		}
	}

	public static NBTTagCompound getModPlayerPersistTag(EntityPlayer player,
			String modName) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound persistTag = null;
		if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		else {
			persistTag = new NBTTagCompound();
			tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag);
		}

		NBTTagCompound modTag = null;
		if (persistTag.hasKey(modName))
			modTag = persistTag.getCompoundTag(modName);
		else {
			modTag = new NBTTagCompound();
			persistTag.setTag(modName, modTag);
		}

		return modTag;
	}

	public static EntityItem dropItemStackInWorld(World worldObj, double x,
			double y, double z, ItemStack stack) {
		float f = 0.7F;
		float d0 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		float d1 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		float d2 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		EntityItem entityitem = new EntityItem(worldObj, x + d0, y + d1,
				z + d2, stack);
		entityitem.delayBeforeCanPickup = 10;
		if (stack.hasTagCompound())
			entityitem.getEntityItem().setTagCompound(
					(NBTTagCompound) stack.getTagCompound().copy());
		worldObj.spawnEntityInWorld(entityitem);
		return entityitem;
	}

	public static boolean isUpdateAvailable() throws IOException,
			MalformedURLException {
		BufferedReader versionFile = new BufferedReader(new InputStreamReader(
				new URL("http://pastebin.com/B7HSWkFD").openStream()));
		String curVersion = versionFile.readLine();

		if (!curVersion.contains(Steampunked.version)) {
			Steampunked.logger.info("Update Avalible - Version " + curVersion);
			return true;
		}

		versionFile.close();

		return false;
	}

}
