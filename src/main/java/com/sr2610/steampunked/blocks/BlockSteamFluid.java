package com.sr2610.steampunked.blocks;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFluid extends BlockFluidFinite {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public IIcon flowingIcon;

	public BlockSteamFluid() {
		super(ModBlocks.steam, ModBlocks.materialSteam);
		this.quantaPerBlock = 6;
		this.setTickRate(5);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
			return stillIcon;

	}

	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.stillIcon = par1IconRegister.registerIcon("steampunked:steam_still");
		this.flowingIcon = par1IconRegister.registerIcon("steampunked:steam_still");

	}
	
	@Override
	public Fluid getFluid(){
		return ModBlocks.steam;
	}

}
