/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.lib.LibNames;
import com.sr2610.steampunked.lib.Reference;

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
		par3EntityPlayer.openGui(Steampunked.instance, 6,
				par2World, 0, 0, 0);
		return par1ItemStack;
	}

}
