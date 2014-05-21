/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
