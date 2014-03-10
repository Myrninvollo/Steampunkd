package com.sr2610.steampunked.proxies;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.sr2610.steampunked.blocks.ModBlocks;
import com.sr2610.steampunked.client.render.BowRenderer;
import com.sr2610.steampunked.client.render.PipeItemRenderer;
import com.sr2610.steampunked.client.render.PipeRendererTESR;
import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.entity.automatons.EntityAutomaton;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.tileentities.TileEntityPipe;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public static int pipeRenderID = -1;

	@Override
	public void registerTickHandlers() {

	}

	public final static PipeItemRenderer pipeItemRenderer = new PipeItemRenderer();

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAutomaton.class,
				new RenderAutomoton());
		MinecraftForgeClient.registerItemRenderer(ModItems.clockworkBow,
				new BowRenderer());
		PipeRendererTESR rp = new PipeRendererTESR();
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.pipe), pipeItemRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, rp);

	}
}
