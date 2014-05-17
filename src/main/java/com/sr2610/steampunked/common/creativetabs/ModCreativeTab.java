/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.creativetabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.items.ModItems;

public class ModCreativeTab extends CreativeTabs {

	public static ModCreativeTab INSTANCE = new ModCreativeTab();
	ItemStack displayItem;
	List list;

	public ModCreativeTab() {
		super("steampunked");
	}

	@Override
	public void displayAllReleventItems(List list) {
		this.list = list;
		addItem(ModItems.handBook);
		addItem(ModItems.bucket);
		addBlock(ModBlocks.machineCase);
		addBlock(ModBlocks.steamBoiler);
		addBlock(ModBlocks.injector);
		addBlock(ModBlocks.steamFurnace);
		addBlock(ModBlocks.tinkerBench);
		addBlock(ModBlocks.punchcardMaker);
		addBlock(ModBlocks.steamBlaster);
		addBlock(ModBlocks.pipe);
		addItem(ModItems.goggles);
		addItem(ModItems.jetpack);
		addItem(ModItems.boots);
		addItem(ModItems.mechBoots);
		addItem(ModItems.wrench);
		addItem(ModItems.hammer);
		addItem(ModItems.drill);
		addItem(ModItems.drillDiamond);
		addItem(ModItems.saberWood);
		addItem(ModItems.saberStone);
		addItem(ModItems.saberIron);
		addItem(ModItems.saberGold);
		addItem(ModItems.saberDiamond);
		addItem(ModItems.clockworkBow);

		addBlock(ModBlocks.slateSlab);
		addBlock(ModBlocks.slate);
		addBlock(ModBlocks.slateStair);
		addBlock(ModBlocks.slateBrickSlab);
		addBlock(ModBlocks.slateBrick);
		addBlock(ModBlocks.slateBrickStair);
		addBlock(ModBlocks.genericBlock);
		addItem(ModItems.ingots);
		addItem(ModItems.craftingItems);

	}

	private void addItem(Item item) {
		item.getSubItems(item, this, list);
	}

	private void addBlock(Block block) {
		block.getSubBlocks(Item.getItemFromBlock(block), this, list);
	}

	@Override
	public Item getTabIconItem() {
		if (displayItem == null)
			return new ItemStack(ModItems.handBook).getItem();

		return displayItem.getItem();
	}
}
