package steampunked.proxies;

import steampunked.client.render.RenderAutomoton;
import steampunked.client.render.RenderTankAutomaton;
import steampunked.entity.automatons.EntityAutomoton;
import steampunked.entity.automatons.EntityTankAutomoton;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy{
	@Override
	public void registerTickHandlers() {

	}

	  public void registerRenderInformation() {
          RenderingRegistry.registerEntityRenderingHandler(EntityAutomoton.class,
                          new RenderAutomoton());
          RenderingRegistry.registerEntityRenderingHandler(EntityTankAutomoton.class,
                  new RenderTankAutomaton());
	
}
}
