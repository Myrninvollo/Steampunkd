package com.sr2610.steampunked.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrafting extends Item {

	public static final String[] itemNames = new String[] { "canister",
			"undefined" };
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemCrafting() {
		super();
		setHasSubtypes(true);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < itemNames.length - 1; x++)
			par3List.add(new ItemStack(this, 1, x));

	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		final int j = MathHelper.clamp_int(par1, 0, 3);
		return icons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[itemNames.length];
		for (int i = 0; i < itemNames.length; ++i)
			icons[i] = par1IconRegister.registerIcon(Reference.ModID
					+ ":crafting_" + i);
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		final int i = MathHelper
				.clamp_int(par1ItemStack.getItemDamage(), 0, 20);
		return super.getUnlocalizedName() + "." + i;
	}

}
