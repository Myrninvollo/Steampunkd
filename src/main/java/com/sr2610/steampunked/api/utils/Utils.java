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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Utils {

	public static String getFluidLocalisedName(FluidStack stack) {
		return StatCollector.translateToLocal("fluid."
				+ FluidRegistry.getFluidName(stack));

	}
	
	public static void sendPlayerChatMessage(EntityPlayer player, String message){
		player.addChatMessage(new ChatComponentTranslation(message));
	}
	
	public static void sendPlayerLocalisedChatMessage(EntityPlayer player, String message){
		player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal(message)));
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

}