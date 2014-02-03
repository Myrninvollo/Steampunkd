package steampunked.items;

import java.util.List;

import net.minecraft.client.Minecraft;
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
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import org.lwjgl.input.Keyboard;

import steampunked.core.tabs.ModCreativeTab;
import steampunked.items.interfaces.ISteamUser;
import steampunked.lib.LibIds;
import steampunked.lib.LibOptions;
import steampunked.lib.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetpack extends ItemArmor implements ISteamUser, ISpecialArmor {

	static final int ARMOR_CHEST = 1;

	public ItemJetpack() {
		super(LibIds.idJetpack, EnumArmorMaterial.IRON, 2, ARMOR_CHEST);
		this.setMaxDamage(LibOptions.jetpackCapacity + 1);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == ARMOR_CHEST;
	}

	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		return Reference.ModID + ":textures/models/jetpack.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		itemIcon = registry.registerIcon(Reference.ModID + ":jetpack");
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return itemIcon;
	}

	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player,
			ItemStack itemStack) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (player.posY <= 200)
				&& this.getCurrentSteam(itemStack) > 0
				&& mc.currentScreen == null) {
			player.fallDistance = 0F;
			player.motionY += 0.10;
			this.setDamage(itemStack, (this.getDamage(itemStack) + 1));

		}

		else if (player.getCurrentItemOrArmor(4) != null) {
			ItemStack helmet = player.getCurrentItemOrArmor(4);

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)
					&& (helmet.getItem() == ModItems.goggles)
					&& this.getCurrentSteam(itemStack) > 0
					&& mc.currentScreen == null) {
				this.setDamage(itemStack, (this.getDamage(itemStack) + 1));

				player.fallDistance = 0F;
				player.motionY += 0.10;
			}
		}

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

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumChatFormatting.AQUA + "Steam : "
				+ this.getCurrentSteam(par1ItemStack) + "/"
				+ this.getMaxSteam());
	}

	@Override
	public void addCharge(int charge, ItemStack stack) {
		this.setDamage(stack, getCurrentSteam(stack) + charge);
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
			return 3;
		else
			return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
	}

}
