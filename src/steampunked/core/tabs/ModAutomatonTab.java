package steampunked.core.tabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import steampunked.blocks.ModBlocks;
import steampunked.items.ModItems;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModAutomatonTab extends CreativeTabs {

	public static ModAutomatonTab INSTANCE = new ModAutomatonTab();
	ItemStack displayItem;
	List list;

	public ModAutomatonTab() {
		super("automatons");

		LanguageRegistry.instance().addStringLocalization(
				"itemGroup.automatons", "Steampunk'd - Automatons");
	}

	@Override
	public ItemStack getIconItemStack() {
		if (displayItem == null)
			return new ItemStack(ModItems.spawner);

		return displayItem;
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


	}

	private void addItem(Item item) {
		item.getSubItems(item.itemID, this, list);
	}

	private void addBlock(Block block) {
		block.getSubBlocks(block.blockID, this, list);
	}
}