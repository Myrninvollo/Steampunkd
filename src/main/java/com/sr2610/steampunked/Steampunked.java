package com.sr2610.steampunked;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.core.handlers.ConfigHandler;
import com.sr2610.steampunked.core.handlers.SteampunkedEventHandler;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.gui.GuiHandler;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.network.PacketPipeline;
import com.sr2610.steampunked.proxies.CommonProxy;
import com.sr2610.steampunked.world.OreGeneration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Steampunked.name, name = "Steampunkd", version = Steampunked.version)
public class Steampunked {

	public static final String version = "0.0.1";
	public static final String name = "Steampunked";
    public static final Logger logger = LogManager.getLogger("Steampunked");

	
	
	@SidedProxy(serverSide = "com.sr2610.steampunked.proxies.CommonProxy", clientSide = "com.sr2610.steampunked.proxies.ClientProxy")
	public static CommonProxy proxy;

	@Instance
	public static Steampunked instance = new Steampunked();

	private GuiHandler guiHandler = new GuiHandler();

	public static final String STEAMPUNKED = "STEAMPUNKED";

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		proxy.registerTickHandlers();
		ModBlocks.initBlocks();
		ModItems.initItems();
		proxy.registerRenderInformation();
		EntityRegistry.registerModEntity(EntityAutomaton.class, "Automoton", 1,
				this, 80, 3, true);
		MinecraftForge.EVENT_BUS.register(new SteampunkedEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		GameRegistry.registerWorldGenerator(new OreGeneration(), 1);

	}

	@EventHandler
	public void load(FMLInitializationEvent e) {
		packetPipeline.initalise();

	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		logger.info("[Steampunk'd] Initialised Sucsessfully");
	}
}
