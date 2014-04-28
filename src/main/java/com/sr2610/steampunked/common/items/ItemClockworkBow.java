/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemClockworkBow extends ItemBow {
	public static final String[] bowPullIconNameArray = new String[] {
			"bow_pull_0", "bow_pull_1", "bow_pull_2" };
	@SideOnly(Side.CLIENT)
	private final IIcon[] iconArray = new IIcon[4];

	public ItemClockworkBow() {
		super();
		maxStackSize = 1;
		setMaxDamage(384);
		setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int j = 72000 - par4;

		final ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer,
				par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		j = event.charge;

		final boolean flag = par3EntityPlayer.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(
						Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D)
				return;

			if (f > 0.7F)
				f = 1.1F;

			final EntityArrow entityarrow = new EntityArrow(par2World,
					par3EntityPlayer, f * 2.0F);
			entityarrow.setDamage(entityarrow.getDamage() + 1.0);

			if (f == 1.1F)
				entityarrow.setIsCritical(true);

			final int k = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.power.effectId, par1ItemStack);

			if (k > 0)
				entityarrow
						.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);

			final int l = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.punch.effectId, par1ItemStack);

			if (l > 0)
				entityarrow.setKnockbackStrength(l);

			if (EnchantmentHelper.getEnchantmentLevel(
					Enchantment.flame.effectId, par1ItemStack) > 0)
				entityarrow.setFire(100);

			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			final EntityArrow entityarrow1 = new EntityArrow(par2World,
					par3EntityPlayer, f * 2.0F);
			entityarrow1.setDamage(entityarrow1.getDamage() + 1.0);

			final EntityArrow entityarrow2 = new EntityArrow(par2World,
					par3EntityPlayer, f * 2.0F);
			entityarrow2.setDamage(entityarrow2.getDamage() + 1.0);

			if (flag) {
				entityarrow.canBePickedUp = 2;
				entityarrow1.canBePickedUp = 2;
				entityarrow2.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
				if (par1ItemStack.stackTagCompound != null) {
					final int mode = par1ItemStack.stackTagCompound
							.getInteger("Mode");
					if (mode == 2) {
						par3EntityPlayer.inventory
								.consumeInventoryItem(Items.arrow);
						par3EntityPlayer.inventory
								.consumeInventoryItem(Items.arrow);
					}
				}
			}

			if (par1ItemStack.stackTagCompound != null) {
				final int Mode = par1ItemStack.stackTagCompound
						.getInteger("Mode");
				switch (Mode) {
				case 1:
					if (!par2World.isRemote)
						par2World.spawnEntityInWorld(entityarrow);
					break;
				case 2:
					entityarrow.setDamage(entityarrow.getDamage() - 1);

					if (!par2World.isRemote) {
						par2World.spawnEntityInWorld(entityarrow);
						par2World.spawnEntityInWorld(entityarrow2);
						par2World.spawnEntityInWorld(entityarrow2);
					}
					break;
				case 3:
					if (!par2World.isRemote)
						par2World.spawnEntityInWorld(entityarrow);
					break;
				}

			} else
				par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setInteger("Mode", 1);
			par2World.spawnEntityInWorld(entityarrow);

		}
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		final ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer,
				par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		if (par3EntityPlayer.capabilities.isCreativeMode
				|| par3EntityPlayer.inventory.hasItem(Items.arrow))
			par3EntityPlayer.setItemInUse(par1ItemStack,
					getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {

		itemIcon = par1IconRegister.registerIcon(Reference.ModID
				+ ":bow/bowClockwork" + "_0");
		for (int N = 0; N < 4; N++)
			iconArray[N] = par1IconRegister.registerIcon(Reference.ModID
					+ ":bow/bowClockwork" + "_" + N);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		if (player.getItemInUse() == null)
			return itemIcon;
		final int time = stack.getMaxItemUseDuration() - useRemaining;
		if (time >= 13)
			return iconArray[3];
		else if (time > 8)
			return iconArray[2];
		else if (time > 0)
			return iconArray[1];
		return iconArray[0];
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setInteger("Mode", 1);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			final int Mode = itemStack.stackTagCompound.getInteger("Mode");
			switch (Mode) {
			case 1:
				list.add("Mode: Regular Firing");
				break;
			case 2:
				list.add("Mode: Triple Arrows");
				break;
			case 3:
				list.add("Mode: Rapid Fire");
				break;
			}

		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		if (player.isSneaking()) {
			if (stack.stackTagCompound != null) {
				int Mode = stack.stackTagCompound.getInteger("Mode");
				if (Mode < 3)
					Mode++;
				else
					Mode = 1;
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("Mode", Mode);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("Mode", 1);
			}
			return true;
		}
		return false;

	}

}
