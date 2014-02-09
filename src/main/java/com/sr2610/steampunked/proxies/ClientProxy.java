package com.sr2610.steampunked.proxies;

import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.client.render.RenderTankAutomaton;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.entity.automatons.EntityTankAutomoton;

import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy{
	@Override
	public void registerTickHandlers() {

	}

	  public void registerRenderInformation() {
          RenderingRegistry.registerEntityRenderingHandler(EntityAutomaton.class,
                          new RenderAutomoton());
          RenderingRegistry.registerEntityRenderingHandler(EntityTankAutomoton.class,
                  new RenderTankAutomaton());
	
}
}
