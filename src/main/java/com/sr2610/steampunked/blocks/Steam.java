/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.blocks;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Steam extends Fluid {
	public Steam() {
		super("steam");

		setDensity(-200);
		setViscosity(500);
		setGaseous(true);
		setTemperature(390);
		setUnlocalizedName("steam");
		setBlock(ModBlocks.BlockFluidSteam);
		FluidRegistry.registerFluid(this);

	}
}
