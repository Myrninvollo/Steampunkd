/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.items;

import java.util.List;

import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class ItemIngot extends Item {

	public static final String[] names = new String[] { "copper", "tin" };

	public ItemIngot() {
		super();
		setHasSubtypes(true);
	
	}
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < names.length; x++)
			par3List.add(new ItemStack(this, 1, x));
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return icons[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[names.length];

		for (int i = 0; i < names.length; ++i)
			icons[i] = par1IconRegister.registerIcon(Reference.ModID
					+ ":ingot" + names[i]);
	}

	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
       
        return "item."+LibNames.INGOTS + names[itemstack.getItemDamage()];
    }


}
