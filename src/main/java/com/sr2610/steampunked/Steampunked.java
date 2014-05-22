package com.sr2610.steampunked;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sr2610.steampunked.client.gui.GuiHandler;
import com.sr2610.steampunked.common.CommonProxy;
import com.sr2610.steampunked.common.handlers.ConfigHandler;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.network.PacketPipeline;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Steampunked.name, name = "Steampunkd", version = Steampunked.version)
public class Steampunked {

	public static final String version = "0.0.1";
	public static final String name = "Steampunked";
	public static final Logger logger = LogManager.getLogger("Steampunked");
	private final GuiHandler guiHandler = new GuiHandler();

	@SidedProxy(serverSide = Reference.commonProxy, clientSide = Reference.clientProxy)
	public static CommonProxy proxy;

	@Instance
	public static Steampunked instance = new Steampunked();

	public static final String STEAMPUNKED = "STEAMPUNKED";

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);

	}

	@EventHandler
	public void load(FMLInitializationEvent e) {
		packetPipeline.initalise();

	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		logger.info("Initialised Sucsessfully");
	}

}
