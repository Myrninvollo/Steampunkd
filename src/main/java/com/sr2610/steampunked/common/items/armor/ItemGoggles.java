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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGoggles extends ItemArmor {

	static final int ARMOR_HELMET = 0;

	public ItemGoggles() {
		super(ItemArmor.ArmorMaterial.CLOTH, 2, ARMOR_HELMET);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot,
			String type) {
		return Reference.ModID + ":textures/models/goggles.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":goggles");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return itemIcon;
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_HELMET;
	}
}
