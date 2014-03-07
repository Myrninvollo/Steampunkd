package com.sr2610.steampunked.items;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.sr2610.steampunked.core.tabs.ModCreativeTab;
import com.sr2610.steampunked.items.interfaces.ISteamUser;
import com.sr2610.steampunked.lib.LibOptions;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiamondDrill extends ItemPickaxe implements ISteamUser {

	private static final Set blocks = Sets.newHashSet(new Block[] {
			Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab,
			Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone,
			Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore,
			Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore,
			Blocks.diamond_block, Blocks.ice, Blocks.netherrack,
			Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore,
			Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail,
			Blocks.golden_rail, Blocks.activator_rail });

	public ItemDiamondDrill() {
		super(Item.ToolMaterial.EMERALD);
		setMaxStackSize(1);
		setMaxDamage(LibOptions.drillCapacity * 2);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack,
			ItemStack par2ItemStack) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world,
			Block block, int par3, int par4, int par5, EntityLivingBase entity) {

		if (getCurrentSteam(itemstack) > 0)
			return super.onBlockDestroyed(itemstack, world, block, par3, par4,
					par5, entity);
		else
			return true;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister
				.registerIcon(Reference.ModID + ":diamond_drill");
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return 4.0F;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		if (itemStack.getItem() instanceof ISteamUser)
			if (((ISteamUser) itemStack.getItem()).getCurrentSteam(itemStack) > 0)
				return super.canHarvestBlock(par1Block, itemStack);
			else
				return false;
		return super.canHarvestBlock(par1Block, itemStack);
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		itemstack.setItemDamage(itemstack.getMaxDamage() - 1);
	}

	@Override
	public int getCurrentSteam(ItemStack itemStack) {
		return this.getMaxDamage() - getDamage(itemStack);
	}

	@Override
	public int getMaxSteam() {
		return this.getMaxDamage();
	}

	@Override
	public int charge(ItemStack target, int energyAvailable) {
		if (energyAvailable > getDamage(target)) {
			int remainder = energyAvailable - getDamage(target);
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
		par3List.add(EnumChatFormatting.ITALIC + "Diamond Tipped");
	}

	@Override
	public void addCharge(int charge, ItemStack stack) {
		setDamage(stack, getCurrentSteam(stack) + charge);
	}

}