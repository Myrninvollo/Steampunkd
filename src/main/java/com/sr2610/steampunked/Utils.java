/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [7 Mar 2014, 16:51:28 (GMT)]
 */
package com.sr2610.steampunked;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Utils {

	public static String getFluidLocalisedName(FluidStack stack) {
		return StatCollector.translateToLocal("fluid."
				+ FluidRegistry.getFluidName(stack));

	}
}
