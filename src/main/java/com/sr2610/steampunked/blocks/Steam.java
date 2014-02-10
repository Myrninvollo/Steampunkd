package com.sr2610.steampunked.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
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
		FluidRegistry.registerFluid(this);

}
}
