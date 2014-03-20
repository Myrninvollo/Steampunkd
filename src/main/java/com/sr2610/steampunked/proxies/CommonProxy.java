/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.proxies;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

public class CommonProxy {
	public ModelBiped getArmorModel(int id) {
		return null;
	}

	public void registerTickHandlers() {

	}

	public void registerRenderInformation() {
	}

	public long getTicks(World worldObj) {
		return worldObj.getTotalWorldTime();
	}

}
