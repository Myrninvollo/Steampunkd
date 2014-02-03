package steampunked.proxies;

import net.minecraft.client.model.ModelBiped;
import steampunked.core.handlers.ServerTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	public ModelBiped getArmorModel(int id){
		return null;
		}

	public void registerTickHandlers() {
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);

	}
	
	  public void registerRenderInformation() {}

		}


