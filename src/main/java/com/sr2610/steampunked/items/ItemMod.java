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