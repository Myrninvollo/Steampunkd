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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.sr2610.steampunked.api.items.ISteamUser;
import com.sr2610.steampunked.client.model.ModelMechBoots;
import com.sr2610.steampunked.common.lib.LibOptions;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMechBoots extends ItemArmor implements ISteamUser,
		ISpecialArmor {

	public static List<String> playersWith1Step = new ArrayList();
	static final int ARMOR_BOOTS = 3;

	public ItemMechBoots() {
		super(ItemArmor.ArmorMaterial.DIAMOND, 2, ARMOR_BOOTS);
		setMaxDamage(LibOptions.advBootsCapacity + 1);
		setCreativeTab(CreativeTabs.tabCombat);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot,
			String type) {

		return Reference.ModID + ":textures/models/mechboots.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":springboots");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return itemIcon;
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_BOOTS;
	}

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
	public void addSteam(int charge, ItemStack stack) {
		setDamage(stack, getCurrentSteam(stack) + charge);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumChatFormatting.AQUA + "Steam : "
				+ getCurrentSteam(par1ItemStack) + "/" + getMaxSteam());
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		double protection;
		if (armor.getItemDamage() < (armor.getMaxDamage() - 1))
			protection = 0.2;
		else
			protection = 0;

		final ArmorProperties prop = new ArmorProperties(Integer.MAX_VALUE,
				protection, Integer.MAX_VALUE);
		return prop;

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (armor.getItemDamage() < (armor.getMaxDamage() - 1))
			return 3;
		else
			return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) event.entityLiving;

			final ItemStack armor = player.getCurrentArmor(3 - armorType);
			if ((armor != null) && (armor.getItem() == this))
				tickPlayer(player);
		}
	}

	void tickPlayer(EntityPlayer player) {
		final ItemStack armor = player.getCurrentArmor(0);
		if (((ISteamUser) armor.getItem()).getCurrentSteam(armor) > 0) {
			if (player.worldObj.isRemote)
				player.stepHeight = player.isSneaking() ? 0.5F : 1F;
			if ((player.onGround || player.capabilities.isFlying)
					&& (player.moveForward > 0F))
				player.moveFlying(0F, 1F, player.capabilities.isFlying ? 0.04F
						: 0.08F);
			player.jumpMovementFactor = player.isSprinting() ? 0.05F : 0.04F;
		}
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) event.entityLiving;
			final boolean hasArmor = (player.getCurrentArmor(0) != null)
					&& (player.getCurrentArmor(0).getItem() == this);

			if (hasArmor
					&& (((ISteamUser) player.getCurrentArmor(0).getItem())
							.getCurrentSteam(player.getCurrentArmor(0)) > 0))
				player.motionY += 0.3;
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingUpdate(LivingUpdateEvent event) {
		if ((event.entityLiving instanceof EntityPlayer)
				&& event.entityLiving.worldObj.isRemote) {
			final EntityPlayer player = (EntityPlayer) event.entityLiving;

			final boolean highStepListed = playersWith1Step.contains(player
					.getCommandSenderName());
			final boolean hasHighStep = (player.getCurrentArmor(0) != null)
					&& (player.getCurrentArmor(0).getItem() == this);

			if (!highStepListed
					&& hasHighStep
					&& (((ISteamUser) player.getCurrentArmor(0).getItem())
							.getCurrentSteam(player.getCurrentArmor(0)) > 0)
					&& (player.getCurrentArmor(0) != null))
				playersWith1Step.add(player.getCommandSenderName());

			if (!hasHighStep && highStepListed) {
				playersWith1Step.remove(player.getCommandSenderName());
				player.stepHeight = 0.5F;
			}
		}
	}

	ModelBiped armorModel = new ModelBiped();

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		if (itemStack != null) {
			if (itemStack.getItem() instanceof ItemMechBoots) {
				final int type = ((ItemArmor) itemStack.getItem()).armorType;

				if (type == ARMOR_BOOTS)
					armorModel = new ModelMechBoots();
				else
					armorModel = new ModelMechBoots();
			}
			if (armorModel != null) {
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = (armorSlot == 1)
						|| (armorSlot == 2);
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = (armorSlot == 2)
						|| (armorSlot == 3);
				armorModel.bipedLeftLeg.showModel = (armorSlot == 2)
						|| (armorSlot == 3);

				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getHeldItem() != null ? 1
						: 0;
				if (entityLiving instanceof EntityPlayer)
					armorModel.aimedBow = ((EntityPlayer) entityLiving)
							.getItemInUseDuration() > 2;
				return armorModel;
			}
		}

		return null;
	}
}
