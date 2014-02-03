package steampunked.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import steampunked.core.tabs.ModCreativeTab;
import steampunked.items.interfaces.ISteamUser;
import steampunked.lib.LibIds;
import steampunked.lib.Reference;

public class ItemGoggles extends ItemArmor {
	
	static final int ARMOR_HELMET = 0;


	public ItemGoggles() {
		super(LibIds.idGoggles, EnumArmorMaterial.CLOTH, 2, ARMOR_HELMET);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		return Reference.ModID + ":textures/models/goggles.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":goggles_item");
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return itemIcon;
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_HELMET;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("Useful for high altitude flying");
	}
	
}
