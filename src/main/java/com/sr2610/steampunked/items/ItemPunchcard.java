package com.sr2610.steampunked.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
		for (int x = 11; x < 13; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":punchcard");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {

		if (par3EntityLivingBase instanceof EntityAutomaton) {
			EntityAutomaton entityAutomoton = (EntityAutomaton) par3EntityLivingBase;
			if (entityAutomoton.getProgram() != true) {
				switch (par1ItemStack.getItemDamage()) {
				case 1: {
					entityAutomoton.setProgram(par1ItemStack.getItemDamage());
					--par1ItemStack.stackSize;
					return true;
				}
				case 2: {
					entityAutomoton.setProgram(par1ItemStack.getItemDamage());
					--par1ItemStack.stackSize;
					return true;
				}
				}
			} else {
				if (!par2EntityPlayer.worldObj.isRemote)
					par2EntityPlayer
							.addChatComponentMessage(new ChatComponentTranslation(
									StatCollector
											.translateToLocal("steampunked.oneProgram.name")));
				return false;
			}

		}

		else {
			if (!par2EntityPlayer.worldObj.isRemote)
				par2EntityPlayer
						.addChatComponentMessage(new ChatComponentTranslation(
								StatCollector
										.translateToLocal("steampunked.onlyAutomaton.name")));
			return false;
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 20);
		return super.getUnlocalizedName() + "." + i;
	}

}
