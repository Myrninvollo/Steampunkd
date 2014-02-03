package steampunked.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import steampunked.core.tabs.ModCreativeTab;
import steampunked.items.interfaces.ISteamUser;
import steampunked.lib.LibOptions;
import steampunked.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiamondDrill extends ItemPickaxe implements ISteamUser {

	public static final Block[] blocksEffectiveAgainst = new Block[] {
			Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab,
			Block.stone, Block.sandStone, Block.cobblestoneMossy,
			Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
			Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice,
			Block.netherrack, Block.oreLapis, Block.blockLapis,
			Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail,
			Block.railDetector, Block.railPowered, Block.railActivator,
			Block.dirt, Block.sand };

	public ItemDiamondDrill(int itemID) {
		super(itemID, EnumToolMaterial.EMERALD);
		setMaxStackSize(1);
		setMaxDamage(LibOptions.drillCapacity*2);
		setCreativeTab(ModCreativeTab.INSTANCE);


	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack,
			ItemStack par2ItemStack) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World,
			int par3, int par4, int par5, int par6,
			EntityLivingBase par7EntityLivingBase) {
		if (getCurrentSteam(par1ItemStack) > 0) {
			return super.onBlockDestroyed(par1ItemStack, par2World, par3, par4,
					par5, par6, par7EntityLivingBase);
		} else {
			return true;
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Reference.ModID + ":diamond_drill");
	}

	 @Override
	    public float getStrVsBlock(ItemStack stack, Block block, int meta) 
	    {
	   return 4.0F;
	    }

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		if (itemStack.getItem() instanceof ISteamUser) {
			if (((ISteamUser) itemStack.getItem()).getCurrentSteam(itemStack) > 0) {
				return super.canHarvestBlock(par1Block, itemStack);
			} else {
				return false;
			}
		}
		return super.canHarvestBlock(par1Block, itemStack);
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		itemstack.setItemDamage(itemstack.getMaxDamage() - 1);
	}

	@Override
	public int getCurrentSteam(ItemStack itemStack) {
		return this.getMaxDamage() - this.getDamage(itemStack);
	}

	@Override
	public int getMaxSteam() {
		return this.getMaxDamage();
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
		par3List.add(EnumChatFormatting.ITALIC + "Diamond Tipped");
	}

	@Override
	public void addCharge(int charge, ItemStack stack) {
		this.setDamage(stack, getCurrentSteam(stack) + charge);
	}

}