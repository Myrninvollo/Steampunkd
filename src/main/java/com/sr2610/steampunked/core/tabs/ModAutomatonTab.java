package com.sr2610.steampunked.core.tabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.items.ModItems;

public class ModAutomatonTab extends CreativeTabs {

	public static ModAutomatonTab INSTANCE = new ModAutomatonTab();
	List list;
	ItemStack displayItem;

	public ModAutomatonTab() {
		super("automatons");
	}

	@Override
	public void displayAllReleventItems(List list) {
		this.list = list;
		addBlock(ModBlocks.tinkerBench);
		addItem(ModItems.chasis);
		addItem(ModItems.spawner);
		addItem(ModItems.namePlate);
		addItem(ModItems.AutomatonUpgrade);
		addItem(ModItems.punchcard);

		addItem(ModItems.basicCore);
		addItem(ModItems.mediumCore);
		addItem(ModItems.advancedCore);
		
		addItem(ModItems.reprogrammer);

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
			return new ItemStack(ModItems.spawner).getItem();

		return displayItem.getItem();
	}
}