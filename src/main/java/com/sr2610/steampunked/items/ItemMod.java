/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.sr2610.steampunked.core.tabs.ModCreativeTab;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMod extends Item {
	private String texturename;

	public ItemMod(String name) {
		super();
		texturename = name;
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":" + texturename);
	}
}
