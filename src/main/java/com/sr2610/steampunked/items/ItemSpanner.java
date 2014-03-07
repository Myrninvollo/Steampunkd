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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;

import com.sr2610.steampunked.blocks.BlockSteamFurnace;
import com.sr2610.steampunked.core.tabs.ModCreativeTab;
import com.sr2610.steampunked.tileentities.TileEntityPipe;
import com.sr2610.steampunked.Utils;

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
		for (Class<? extends Block> shift : shiftRotations)
			if (shift.isAssignableFrom(cls))
				return true;
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

		if (block.hasTileEntity(0) == true)
			if (world.getTileEntity(x, y, z) instanceof TileEntityPipe) {
				TileEntityPipe pipe = (TileEntityPipe) world.getTileEntity(x,
						y, z);
				if (!world.isRemote && pipe.tank.getFluid() != null)
					player.addChatMessage(new ChatComponentTranslation(
							"Fluid: "
									+ Utils.getFluidLocalisedName(pipe.tank.getFluid())
									+ " Amount: " + pipe.tank.getFluidAmount()
									+ "mB"));
			}

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
		itemIcon = par1IconRegister.registerIcon("steampunked:spanner");
	}

}
