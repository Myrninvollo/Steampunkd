package com.sr2610.steampunked.items;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sr2610.steampunked.blocks.BlockInjector;
import com.sr2610.steampunked.blocks.BlockSteamFurnace;
import com.sr2610.steampunked.core.tabs.ModCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpanner extends ItemTool {

	private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();
	private boolean passSneakClick = true;

	public ItemSpanner() {
		super(1F, Item.ToolMaterial.IRON, null);
		setFull3D();
		this.setMaxDamage(-1);
		shiftRotations.add(BlockDropper.class);
		shiftRotations.add(BlockDispenser.class);
		shiftRotations.add(BlockHopper.class);
		shiftRotations.add(BlockFurnace.class);
		shiftRotations.add(BlockChest.class);
		shiftRotations.add(BlockInjector.class);
		shiftRotations.add(BlockSteamFurnace.class);

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

		if (block == null)
			return false;

		if (player.isSneaking() != isShiftRotation(block.getClass()))
			return false;

		if (block.rotateBlock(world, x, y, z,
				ForgeDirection.getOrientation(side))) {
			player.swingItem();
			return !world.isRemote;
		}
		return false;
	}

	public boolean doesSneakBypassUse(World world, int x, int y, int z,
			EntityPlayer player) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("steampunked:spanner");
	}

}
