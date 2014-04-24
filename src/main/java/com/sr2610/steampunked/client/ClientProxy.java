/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.sr2610.steampunked.client.render.BootsItemRenderer;
import com.sr2610.steampunked.client.render.BowRenderer;
import com.sr2610.steampunked.client.render.PipeItemRenderer;
import com.sr2610.steampunked.client.render.PipeRendererTESR;
import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.common.CommonProxy;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.tileentities.TileEntityPipe;
import com.sr2610.steampunked.common.tileentities.TileEntityValvePipe;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public static int pipeRenderID = -1;

	@Override
	public void registerTickHandlers() {

	}

	public final static PipeItemRenderer pipeItemRenderer = new PipeItemRenderer();
	public final static BootsItemRenderer bootsItemRenderer = new BootsItemRenderer();

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAutomaton.class,
				new RenderAutomoton());
		MinecraftForgeClient.registerItemRenderer(ModItems.clockworkBow,
				new BowRenderer());
		PipeRendererTESR rp = new PipeRendererTESR();
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.pipe), pipeItemRenderer);

		MinecraftForgeClient.registerItemRenderer(ModItems.mechBoots,
				bootsItemRenderer);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, rp);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityValvePipe.class,
				rp);

	}
}
