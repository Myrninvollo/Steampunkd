package com.sr2610.steampunked.items;

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

import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemClockworkBow extends ItemBow {
	public static final String[] bowPullIconNameArray = new String[] {
			"bow_pull_0", "bow_pull_1", "bow_pull_2" };
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray = new IIcon[4];

	public ItemClockworkBow() {
		super();
		maxStackSize = 1;
		setMaxDamage(384);
		setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int j = getMaxItemUseDuration(par1ItemStack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer,
				par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = par3EntityPlayer.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(
						Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D) {
				return;
			}

			if (f > 0.7F) {
				f = 1.1F;
			}

			EntityArrow entityarrow = new EntityArrow(par2World,
					par3EntityPlayer, f * 2.0F);
			entityarrow.setDamage(entityarrow.getDamage() + 1.0);

			if (f == 1.1F) {
				entityarrow.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.power.effectId, par1ItemStack);

			if (k > 0) {
				entityarrow
						.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.punch.effectId, par1ItemStack);

			if (l > 0) {
				entityarrow.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(
					Enchantment.flame.effectId, par1ItemStack) > 0) {
				entityarrow.setFire(100);
			}

			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
			}

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(entityarrow);
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer,
				par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result;
		}
		if (par3EntityPlayer.isSneaking()) {
			if (par1ItemStack.stackTagCompound != null) {
				int mode = par1ItemStack.stackTagCompound.getInteger("Mode");

				switch (mode) {
				case 0:
					mode = 5;
				case 1:
					mode = 5;
				case 2:
					mode = 5;

				}
				par1ItemStack.stackTagCompound.setInteger("Mode", mode);

			}
		}
		if (par3EntityPlayer.capabilities.isCreativeMode
				|| par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			par3EntityPlayer.setItemInUse(par1ItemStack,
					getMaxItemUseDuration(par1ItemStack));

		}

		return par1ItemStack;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player,
			List list, boolean par4) {
		if (!itemStack.hasTagCompound()) {

			itemStack.stackTagCompound = new NBTTagCompound();
			itemStack.stackTagCompound.setInteger("Mode", 100);
		} else {

			int mode = itemStack.stackTagCompound.getInteger("Mode");
			list.add("Mode: " + mode);
		}
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {

		itemIcon = par1IconRegister.registerIcon(Reference.ModID
				+ ":bow/bowClockwork" + "_0");
		for (int N = 0; N < 4; N++) {
			iconArray[N] = par1IconRegister.registerIcon(Reference.ModID
					+ ":bow/bowClockwork" + "_" + N);

		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		if (player.getItemInUse() == null)
			return itemIcon;
		int time = stack.getMaxItemUseDuration() - useRemaining;
		if (time >= 13) {
			return iconArray[3];
		} else if (time > 8) {
			return iconArray[2];
		} else if (time > 0) {
			return iconArray[1];
		}
		return iconArray[0];
	}

	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setInteger("Mode", 0);

	}
}
