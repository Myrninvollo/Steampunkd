/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;
import com.sr2610.steampunked.common.handbook.HandbookData;
import com.sr2610.steampunked.common.handlers.CraftingHandler;
import com.sr2610.steampunked.common.handlers.SteampunkedEventHandler;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.world.OreGeneration;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public ModelBiped getArmorModel(int id) {
		return null;
	}

	public void registerRenderInformation() {
	}

	public long getTicks(World worldObj) {
		return worldObj.getTotalWorldTime();
	}

	public void init() {
		ModBlocks.initBlocks();
		ModItems.initItems();
		registerRenderInformation();

		MinecraftForge.EVENT_BUS.register(new SteampunkedEventHandler());
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		GameRegistry.registerWorldGenerator(new OreGeneration(), 1);
		CraftingHandler.init();
		HandbookData.init();

		EntityRegistry.registerModEntity(EntityAutomoton.class, "Automoton", 1,
				Steampunked.instance, 80, 3, true);

	}

}
