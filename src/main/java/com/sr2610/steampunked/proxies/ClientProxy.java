package com.sr2610.steampunked.proxies;

import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerTickHandlers() {

	}

	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAutomaton.class,
				new RenderAutomoton());

	}
}
