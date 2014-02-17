package com.sr2610.steampunked.proxies;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

public class CommonProxy {
	public ModelBiped getArmorModel(int id){
		return null;
		}

	public void registerTickHandlers() {

	}
	
	  public void registerRenderInformation() {}

		

public long getTicks(World worldObj) {
	return worldObj.getTotalWorldTime();
}

}

