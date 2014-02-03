package steampunked;

import net.minecraftforge.common.MinecraftForge;
import steampunked.blocks.ModBlocks;
import steampunked.core.handlers.ClientPacketHandler;
import steampunked.core.handlers.ConfigHandler;
import steampunked.core.handlers.ServerPacketHandler;
import steampunked.core.handlers.SteampunkedEventHandler;
import steampunked.entity.automatons.EntityAutomoton;
import steampunked.entity.automatons.EntityTankAutomoton;
import steampunked.gui.GuiHandler;
import steampunked.items.ModItems;
import steampunked.lib.Reference;
import steampunked.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.ModID, name = "Steampunkd", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { Mod_Steampunked.STEAMPUNKED }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "Steampunked" }, packetHandler = ServerPacketHandler.class))
public class Mod_Steampunked {

	@SidedProxy(serverSide = "steampunked.proxies.CommonProxy", clientSide = "steampunked.proxies.ClientProxy")
	public static CommonProxy proxy;

	@Instance
	public static Mod_Steampunked instance = new Mod_Steampunked();

	private GuiHandler guiHandler = new GuiHandler();

	public static final String STEAMPUNKED = "STEAMPUNKED";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void load(FMLInitializationEvent e) {
		proxy.registerTickHandlers();
		proxy.registerRenderInformation();
		ModBlocks.initBlocks();
		ModItems.initItems();
		EntityRegistry.registerModEntity(EntityAutomoton.class, "Automoton", 1,
                this, 80, 3, true);
		EntityRegistry.registerModEntity(EntityTankAutomoton.class, "AutomotonTank", 2,
                this, 80, 3, true);
        LanguageRegistry.instance().addStringLocalization("entity.steampunked.Automoton.name", "en_US","Steam Automoton");
		MinecraftForge.EVENT_BUS.register(new SteampunkedEventHandler());
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("[Steampunk'd] Initialized Sucsessfully");

	}
}
