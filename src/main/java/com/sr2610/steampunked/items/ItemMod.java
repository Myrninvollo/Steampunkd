package com.sr2610.steampunked.items;

import com.sr2610.steampunked.lib.Reference;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMod extends Item {
	private String texturename;

	public ItemMod(String name) {
		super();
		texturename = name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":" + texturename);
	}
}