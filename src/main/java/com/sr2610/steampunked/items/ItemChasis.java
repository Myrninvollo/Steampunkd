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

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.core.tabs.ModAutomatonTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChasis extends Item {

	public ItemChasis() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(ModAutomatonTab.INSTANCE);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++)
			par3List.add(new ItemStack(this, 1, x));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack,
			EntityPlayer par2EntityPlayer, List list, boolean par4) {

		switch (itemstack.getItemDamage()) {
		case 0:
			list.add("Iron");
			return;
		case 1:
			list.add("Copper");
			return;

		case 2:
			list.add("Undefined");
			return;
		}
	}

}
