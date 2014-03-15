/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [15 Mar 2014, 18:26:37 (GMT)]
 */
package com.sr2610.steampunked.items.tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.sr2610.steampunked.lib.Reference;

public class ItemHammer extends ItemPickaxe {

	private static final Set blockSet = Sets.newHashSet(new Block[] {
			Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab,
			Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone,
			Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore,
			Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore,
			Blocks.diamond_block, Blocks.ice, Blocks.netherrack,
			Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore,
			Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail,
			Blocks.golden_rail, Blocks.activator_rail });

	public ItemHammer() {
		super(Item.ToolMaterial.IRON);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world,
			Block block, int par3, int par4, int par5, EntityLivingBase entity) {

		return super.onBlockDestroyed(itemstack, world, block, par3, par4,
				par5, entity);

	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {

		itemIcon = iconRegister.registerIcon(Reference.ModID + ":hammer");

	}

	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		return 1.0F;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {

		return super.canHarvestBlock(par1Block, itemStack);
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity) {

		if (entity instanceof EntityLiving) {
			((EntityLiving) entity).addPotionEffect(new PotionEffect(
					Potion.moveSlowdown.id, 75, 1));
			((EntityLiving) entity).addPotionEffect(new PotionEffect(
					Potion.confusion.id, 50, 1));
		}
		return false;
	}

}
