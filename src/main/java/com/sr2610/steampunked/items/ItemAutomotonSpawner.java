/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.sr2610.steampunked.core.tabs.ModAutomatonTab;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAutomotonSpawner extends Item {

	public double range;

	public ItemAutomotonSpawner() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(ModAutomatonTab.INSTANCE);

	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote)
			switch (par1ItemStack.getItemDamage()) {
			case 0: {

				EntityAutomaton entity = new EntityAutomaton(par3World);
				entity.homeX = par4;
				entity.homeY = par5;
				entity.homeZ = par6;
				entity.side = par7;
				entity.setLocationAndAngles(par4 + par8, par5 + par9 + 0.5,
						par6 + par10, par9, par10);
				entity.setOwner(par2EntityPlayer.getDisplayName());

				NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
				NBTTagDouble nbttagdouble = (NBTTagDouble) nbttagcompound
						.getTag("Range");
				if (nbttagdouble != null)
					entity.range = nbttagdouble.func_150286_g();
				else
					entity.range = 5;
				NBTTagDouble nbttagdoubleHealth = (NBTTagDouble) nbttagcompound
						.getTag("MaxHealth");
				if (nbttagdoubleHealth != null)
					entity.maxHealth = nbttagdoubleHealth.func_150286_g();
				else
					entity.maxHealth = 20;

				NBTTagDouble nbttagdoubleSpeed = (NBTTagDouble) nbttagcompound
						.getTag("Speed");
				if (nbttagdoubleSpeed != null)
					entity.speed = nbttagdoubleSpeed.func_150286_g();
				else
					entity.speed = 0.25;
				par3World.spawnEntityInWorld(entity);
				if (!par2EntityPlayer.capabilities.isCreativeMode) {
					--par1ItemStack.stackSize;

					return true;
				} else if (!par3World.isRemote)
					// par2EntityPlayer.addChatMessage(EnumChatFormatting.ITALIC+"You must place this on a block with a tank");
					return false;
			}
			}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 2; x++)
			par3List.add(new ItemStack(this, 1, x));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":automoton");
	}

	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.setTagCompound(new NBTTagCompound());
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {

			if (par1ItemStack.stackTagCompound.hasKey("MaxHealth"))
				par3List.add("Maximum Health: "
						+ par1ItemStack.stackTagCompound.getDouble("MaxHealth"));
			else
				par3List.add("Maximum Health: 20.0");

			if (par1ItemStack.stackTagCompound.hasKey("Range"))
				par3List.add("Range: "
						+ par1ItemStack.stackTagCompound.getDouble("Range")
						+ " Blocks");
			else

				par3List.add("Range: 5.0 Blocks");

			if (par1ItemStack.stackTagCompound.hasKey("Speed"))
				par3List.add("Speed: Fast");

			else

				par3List.add("Speed: Slow");

		}
	}

}
