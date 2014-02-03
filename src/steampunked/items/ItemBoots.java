package steampunked.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import steampunked.core.tabs.ModCreativeTab;
import steampunked.items.interfaces.ISteamUser;
import steampunked.lib.LibIds;
import steampunked.lib.LibOptions;
import steampunked.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoots extends ItemArmor implements ISteamUser,ISpecialArmor {

	static final int ARMOR_BOOTS = 3;

	public ItemBoots() {
		super(LibIds.idBoots, EnumArmorMaterial.IRON, 2, ARMOR_BOOTS);
		this.setMaxDamage(LibOptions.bootsCapacity + 1);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		return Reference.ModID + ":textures/models/springbootsmodel.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":springboots");
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return itemIcon;
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_BOOTS;
	}

	@Override
	public int getCurrentSteam(ItemStack itemStack) {
		return this.getMaxDamage() - this.getDamage(itemStack) - 1;
	}

	@Override
	public int getMaxSteam() {
		return this.getMaxDamage() - 1;
	}

	@Override
	public int charge(ItemStack target, int energyAvailable) {
		if (energyAvailable > getDamage(target)) {
			int remainder = energyAvailable - getDamage(target);
			this.setDamage(target, 0);
			return remainder;
		} else {
			this.setDamage(target, getDamage(target) - energyAvailable);
			return 0;
		}

	}

	@Override
	public void addCharge(int charge, ItemStack stack) {
		this.setDamage(stack, getCurrentSteam(stack) + charge);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("Useful for high altitude falling");
		par3List.add(EnumChatFormatting.AQUA + "Steam : "
				+ this.getCurrentSteam(par1ItemStack) + "/"
				+ this.getMaxSteam());
	}

	public int getItemEnchantability() {
		return 0;
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		double protection;
		if (armor.getItemDamage() < armor.getMaxDamage() -1)
			protection = 0.2;
		else
			protection = 0;

		ArmorProperties prop = new ArmorProperties(Integer.MAX_VALUE,
				protection, Integer.MAX_VALUE);
		return prop;

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (armor.getItemDamage() < armor.getMaxDamage() -1)
			return 5;
		else
			return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
	}

}
