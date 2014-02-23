package com.sr2610.steampunked.items;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSign;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sr2610.steampunked.blocks.BlockSteamFurnace;
import com.sr2610.steampunked.core.tabs.ModCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpanner extends Item {

	private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();

	public ItemSpanner() {
		super();
		setFull3D();
		setMaxStackSize(1);
		setCreativeTab(ModCreativeTab.INSTANCE);
		shiftRotations.add(BlockChest.class);
		shiftRotations.add(BlockFurnace.class);
		shiftRotations.add(BlockSteamFurnace.class);
		shiftRotations.add(BlockSign.class);

	}

	private boolean isShiftRotation(Class<? extends Block> cls) {
		for (Class<? extends Block> shift : shiftRotations) {
			if (shift.isAssignableFrom(cls))
				return true;
		}
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		Block block = world.getBlock(x, y, z);
		player.swingItem();

		if (block == null)
			return false;

		if (player.isSneaking() != isShiftRotation(block.getClass())) {
			return false;
		}

		if (block.rotateBlock(world, x, y, z,
				ForgeDirection.getOrientation(side))) {
			return !world.isRemote;
		}
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
		itemIcon = par1IconRegister.registerIcon("steampunked:spanner");
	}

}
