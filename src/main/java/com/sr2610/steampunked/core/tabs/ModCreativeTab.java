package com.sr2610.steampunked.core.tabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.items.ModItems;

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

		addItem(ModItems.bucket);
		addBlock(ModBlocks.Injector);
		addBlock(ModBlocks.steamFurnace);
		addBlock(ModBlocks.steamBoiler);
		addBlock(ModBlocks.tinkerBench);
		addBlock(ModBlocks.punchcardMaker);
		addItem(ModItems.goggles);
		addItem(ModItems.jetpack);
		addItem(ModItems.boots);
		addItem(ModItems.spanner);
		addItem(ModItems.drill);
		addItem(ModItems.drillDiamond);
		addItem(ModItems.saberWood);
		addItem(ModItems.saberStone);
		addItem(ModItems.saberIron);
		addItem(ModItems.saberGold);
		addItem(ModItems.saberDiamond);
		addItem(ModItems.clockworkBow);
		addBlock(ModBlocks.oreCopper);
		addBlock(ModBlocks.oreTin);
		addItem(ModItems.copperIngot);
		addItem(ModItems.tinIngot);

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
			return new ItemStack(ModItems.goggles).getItem();

		return displayItem.getItem();
	}
}