package com.sr2610.steampunked.proxies;

import net.minecraftforge.client.MinecraftForgeClient;

import com.sr2610.steampunked.client.render.BowRenderer;
import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.items.ModItems;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerTickHandlers() {

	}

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAutomaton.class,
				new RenderAutomoton());
		MinecraftForgeClient.registerItemRenderer(ModItems.clockworkBow, new BowRenderer());


	}
}
