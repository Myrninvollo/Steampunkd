package com.sr2610.steampunked.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPunchcard extends Item {

	public ItemPunchcard() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack,
			EntityPlayer par2EntityPlayer, List list, boolean par4) {

		switch (itemstack.getItemDamage()) {
		case 0:
			list.add("Gather Program");
			break;
		case 1:
			list.add("Attack Program");
			break;

		case 2:
			list.add("Blank");
			break;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":punchcard");
	}

	public boolean itemInteractionForEntity(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {

		if (par3EntityLivingBase instanceof EntityAutomaton) {
			EntityAutomaton entityAutomoton = (EntityAutomaton) par3EntityLivingBase;
			if (entityAutomoton.getProgram() != true) {
				switch (par1ItemStack.getItemDamage()) {
				case 0: {
					entityAutomoton.setPickup(true);
					entityAutomoton.setProgram(true);
					--par1ItemStack.stackSize;
					return true;
				}
				case 1: {
					entityAutomoton.setAttackMobs(true);
					entityAutomoton.setProgram(true);

					--par1ItemStack.stackSize;
					return true;
				}
				}
			} else {
				if (!par2EntityPlayer.worldObj.isRemote)
					par2EntityPlayer
							.addChatComponentMessage(new ChatComponentTranslation(
									"steampunked.automatons.oneProgram"));
				return false;
			}

		}

		else {
			if (!par2EntityPlayer.worldObj.isRemote)
				par2EntityPlayer
						.addChatComponentMessage(new ChatComponentTranslation(
								"steampunked.automatons.onlyAutomaton"));
			return false;
		}
		return false;
	}

}
