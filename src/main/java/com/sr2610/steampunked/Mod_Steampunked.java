package com.sr2610.steampunked;

import net.minecraftforge.common.MinecraftForge;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.core.handlers.ConfigHandler;
import com.sr2610.steampunked.core.handlers.SteampunkedEventHandler;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.entity.automatons.EntityTankAutomoton;
import com.sr2610.steampunked.gui.GuiHandler;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketPipeline;
import com.sr2610.steampunked.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid =Mod_Steampunked.name , name = "Steampunkd", version = Mod_Steampunked.version)
public class Mod_Steampunked {

	public static final String version = "0.0.1";
	public static final String name = "Steampunked";

	@SidedProxy(serverSide = "com.sr2610.steampunked.proxies.CommonProxy", clientSide = "com.sr2610.steampunked.proxies.ClientProxy")
	public static CommonProxy proxy;

	@Instance
	public static Mod_Steampunked instance = new Mod_Steampunked();

	private GuiHandler guiHandler = new GuiHandler();

	public static final String STEAMPUNKED = "STEAMPUNKED";

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		proxy.registerTickHandlers();
		proxy.registerRenderInformation();
		ModBlocks.initBlocks();
		ModItems.initItems();
		EntityRegistry.registerModEntity(EntityAutomaton.class, "Automoton", 1,
				this, 80, 3, true);
		EntityRegistry.registerModEntity(EntityTankAutomoton.class,
				"AutomotonTank", 2, this, 80, 3, true);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
	}

	@EventHandler
	public void load(FMLInitializationEvent e) {
		packetPipeline.initalise();
		MinecraftForge.EVENT_BUS.register(new SteampunkedEventHandler());

	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		System.out.println("[Steampunk'd] Initialized Sucsessfully");

	}
}
