/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.items.automotons;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.sr2610.steampunked.common.creativetabs.ModAutomatonTab;

public class ItemReprogrammer extends Item {

	public ItemReprogrammer() {
		super();
		setCreativeTab(ModAutomatonTab.INSTANCE);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		if (player.isSneaking()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("X", x);
			stack.stackTagCompound.setInteger("Y", y);
			stack.stackTagCompound.setInteger("Z", z);
			stack.stackTagCompound.setInteger("Side", side);

			player.worldObj.playSoundAtEntity(player, "random.wood_click",
					1.0F, 1.0F);

		}
		return false;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setInteger("X", 0);
		itemStack.stackTagCompound.setInteger("Y", 0);
		itemStack.stackTagCompound.setInteger("Z", 0);
		itemStack.stackTagCompound.setInteger("Side", 0);

	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			final int X = itemStack.stackTagCompound.getInteger("X");
			final int Y = itemStack.stackTagCompound.getInteger("Y");
			final int Z = itemStack.stackTagCompound.getInteger("Z");
			final int Side = itemStack.stackTagCompound.getInteger("Side");

			list.add("X: " + X);
			list.add("Y: " + Y);
			list.add("Z: " + Z);
			list.add("Side: " + Side);

		} else
			list.add("Position not Defined");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack,
			EntityPlayer player, EntityLivingBase entityLiving) {

		/*
		 * if (entityLiving instanceof EntityAutomaton) { EntityAutomaton
		 * automaton = (EntityAutomaton) entityLiving; if
		 * (itemStack.stackTagCompound != null) { int X =
		 * itemStack.stackTagCompound.getInteger("X"); int Y =
		 * itemStack.stackTagCompound.getInteger("Y"); int Z =
		 * itemStack.stackTagCompound.getInteger("Z"); int Side =
		 * itemStack.stackTagCompound.getInteger("Side");
		 * 
		 * automaton.homeX = X; automaton.homeY = Y; automaton.homeZ = Z;
		 * automaton.side = Side; if (!player.worldObj.isRemote)
		 * player.addChatComponentMessage(new ChatComponentTranslation(
		 * StatCollector .translateToLocal("steampunked.boundDone")));
		 * player.worldObj.playSoundAtEntity(player, "random.successful_hit",
		 * 1.0F, 1.0F);
		 * 
		 * return true;
		 * 
		 * } }
		 */
		return false;
	}
}
