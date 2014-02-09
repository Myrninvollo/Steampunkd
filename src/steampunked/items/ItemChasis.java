package steampunked.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import steampunked.core.tabs.ModAutomatonTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChasis extends Item {

	public ItemChasis(int id) {
		super(id);
		setCreativeTab(ModAutomatonTab.INSTANCE);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int x = 0; x < 3; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

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
