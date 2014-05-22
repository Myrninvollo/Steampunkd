package com.sr2610.steampunked.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHandbook extends Item {

	public ItemHandbook() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName(LibNames.HANDBOOK);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(Reference.ModID + ":handbook");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.openGui(Steampunked.instance, 6, par2World, 0, 0, 0);
		return par1ItemStack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		par2EntityPlayer.openGui(Steampunked.instance, 6, par3World, 0, 0, 0);

		return false;
	}

}
