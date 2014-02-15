package com.sr2610.steampunked.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFluid extends BlockFluidFinite {

	public BlockSteamFluid(Fluid fluid, Material material) {
		super(fluid, material);
		this.quantaPerBlock = 6;
		this.setTickRate(5);
	}

	@SideOnly(Side.CLIENT)
	protected static IIcon[] theIcon;

	@Override
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? this.theIcon[1] : this.theIcon[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.theIcon = new IIcon[] {
				iconRegister.registerIcon("steampunked:" + "steam" + "_still"),
				iconRegister.registerIcon("steampunked:" + "steam" + "_still") };
	}

}
