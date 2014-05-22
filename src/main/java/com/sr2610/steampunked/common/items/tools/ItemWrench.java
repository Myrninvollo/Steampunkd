package com.sr2610.steampunked.common.items.tools;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSign;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sr2610.steampunked.common.blocks.machines.BlockSteamFurnace;
import com.sr2610.steampunked.common.creativetabs.ModCreativeTab;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWrench extends Item {

	private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();

	public ItemWrench() {
		super();
		setFull3D();
		setMaxStackSize(1);
		setCreativeTab(ModCreativeTab.INSTANCE);
		shiftRotations.add(BlockChest.class);
		shiftRotations.add(BlockFurnace.class);
		shiftRotations.add(BlockSteamFurnace.class);
		shiftRotations.add(BlockSign.class);
		setCreativeTab(ModCreativeTab.INSTANCE);

	}

	private boolean isShiftRotation(Class<? extends Block> cls) {
		for (final Class<? extends Block> shift : shiftRotations)
			if (shift.isAssignableFrom(cls))
				return true;
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		final Block block = world.getBlock(x, y, z);
		player.swingItem();

		if (block == null)
			return false;

		/*
		 * if (block.hasTileEntity(0) == true) if (world.getTileEntity(x, y, z)
		 * instanceof TileEntityPipe) { TileEntityPipe pipe = (TileEntityPipe)
		 * world.getTileEntity(x, y, z); if (!world.isRemote &&
		 * pipe.tank.getFluid() != null) player.addChatMessage(new
		 * ChatComponentTranslation( "Fluid: " +
		 * Utils.getFluidLocalisedName(pipe.tank .getFluid()) + " Amount: " +
		 * pipe.tank.getFluidAmount() + "mB")); }
		 */

		if (player.isSneaking() != isShiftRotation(block.getClass()))
			return false;

		if (block.rotateBlock(world, x, y, z,
				ForgeDirection.getOrientation(side)))
			return !world.isRemote;
		return false;
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z,
			EntityPlayer player) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon("steampunked:wrench");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
		if (par3EntityLivingBase instanceof EntityAutomoton) {
			EntityAutomoton ea = (EntityAutomoton) par3EntityLivingBase;
			if (!par2EntityPlayer.isSneaking())
				ea.startUp();
			else
				par3EntityLivingBase.setDead();
			return true;
		}
		return false;
	}

}
