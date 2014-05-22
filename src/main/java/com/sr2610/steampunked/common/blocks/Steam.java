package com.sr2610.steampunked.common.blocks;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Steam extends Fluid {
	public Steam() {
		super("steam");

		setDensity(-200);
		setViscosity(500);
		setGaseous(true);
		setTemperature(390);
		setUnlocalizedName("steampunked.steam");
		setBlock(ModBlocks.blockSteam);
		FluidRegistry.registerFluid(this);

	}
}
