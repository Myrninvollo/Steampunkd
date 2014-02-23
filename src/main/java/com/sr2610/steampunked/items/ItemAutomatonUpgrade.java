package com.sr2610.steampunked.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import com.sr2610.steampunked.items.interfaces.IUpgrade;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAutomatonUpgrade extends Item implements IUpgrade {

	public ItemAutomatonUpgrade() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	public static final String[] upgradeItemNames = new String[] { "range",
		"speed", "health", "undefined" };
	@SideOnly(Side.CLIENT)
	private IIcon[] upgradeIcons;

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		int j = MathHelper.clamp_int(par1, 0, 3);
		return upgradeIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		upgradeIcons = new IIcon[upgradeItemNames.length];

		for (int i = 0; i < upgradeItemNames.length; ++i) {
			upgradeIcons[i] = par1IconRegister.registerIcon(Reference.ModID
					+ ":upgrade_" + +i);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 20);
		return super.getUnlocalizedName() + "." + i;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 20);
		par3List.add(StatCollector.translateToLocal("steampunked.upgrade." + i
				+ ".name"));
	}
}
