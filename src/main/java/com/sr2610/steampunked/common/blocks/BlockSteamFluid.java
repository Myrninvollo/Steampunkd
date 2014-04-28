/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFluid extends BlockFluidFinite {

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public BlockSteamFluid(Fluid fluid, Material material) {
		super(fluid, material);
		quantaPerBlock = 6;
		setTickRate(5);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register
				.registerIcon(Reference.ModID + ":steam" + "_still");
		flowingIcon = register.registerIcon(Reference.ModID + ":steam"
				+ "_still");
	}

}
