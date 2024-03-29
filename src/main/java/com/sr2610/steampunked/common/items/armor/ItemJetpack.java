/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.items.armor;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ISpecialArmor;

import com.sr2610.steampunked.api.items.ISteamUser;
import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.lib.LibOptions;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetpack extends ItemArmor implements ISteamUser, ISpecialArmor {

	static final int ARMOR_CHEST = 1;

	public ItemJetpack() {
		super(ItemArmor.ArmorMaterial.IRON, 2, ARMOR_CHEST);
		setMaxDamage(LibOptions.jetpackCapacity + 1);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_CHEST;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot,
			String type) {
		return Reference.ModID + ":textures/models/armor/jetpackBasic.png";
	}

	/*
	 * @Override public void onArmorTick(World world, EntityPlayer player,
	 * ItemStack itemStack) { final ItemStack armor = player.getCurrentArmor(3 -
	 * 0); // final Minecraft mc = FMLClientHandler.instance().getClient(); if
	 * (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && player.posY <= 200 &&
	 * getCurrentSteam(itemStack) > 0 ) { player.fallDistance = 0F;
	 * player.motionY += 0.10; setDamage(itemStack, getDamage(itemStack) + 2);
	 * 
	 * }
	 * 
	 * else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (armor != null &&
	 * armor.getItem() == ModItems.goggles) && getCurrentSteam(itemStack) > 0 )
	 * { player.fallDistance = 0F; player.motionY += 0.10; setDamage(itemStack,
	 * getDamage(itemStack) + 2);
	 * 
	 * }
	 * 
	 * }
	 */

	@Override
	public int getCurrentSteam(ItemStack itemStack) {
		return this.getMaxDamage() - getDamage(itemStack) - 1;
	}

	@Override
	public int getMaxSteam() {
		return this.getMaxDamage() - 1;
	}

	@Override
	public int fill(ItemStack target, int energyAvailable) {
		if (energyAvailable > getDamage(target)) {
			final int remainder = energyAvailable - getDamage(target);
			setDamage(target, 0);
			return remainder;
		} else {
			setDamage(target, getDamage(target) - energyAvailable);
			return 0;
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumChatFormatting.AQUA + "Steam : "
				+ getCurrentSteam(par1ItemStack) + "/" + getMaxSteam());
	}

	@Override
	public void addSteam(int charge, ItemStack stack) {
		setDamage(stack, getCurrentSteam(stack) + charge);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		double protection;
		if (armor.getItemDamage() < armor.getMaxDamage() - 1)
			protection = 0.2;
		else
			protection = 0;

		final ArmorProperties prop = new ArmorProperties(Integer.MAX_VALUE,
				protection, Integer.MAX_VALUE);
		return prop;

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (armor.getItemDamage() < armor.getMaxDamage() - 1)
			return 3;
		else
			return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
	}

}
