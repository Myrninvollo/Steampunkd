package com.sr2610.steampunked.client;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.sr2610.steampunked.api.utils.IconRegistry;
import com.sr2610.steampunked.client.render.BootsItemRenderer;
import com.sr2610.steampunked.client.render.BowRenderer;
import com.sr2610.steampunked.client.render.PipeItemRenderer;
import com.sr2610.steampunked.client.render.PipeRendererTESR;
import com.sr2610.steampunked.client.render.RenderAutomoton;
import com.sr2610.steampunked.common.CommonProxy;
import com.sr2610.steampunked.common.blocks.ModBlocks;
import com.sr2610.steampunked.common.entitys.EntityAutomoton;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityPipe;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	public static int pipeRenderID = -1;

	public final static PipeItemRenderer pipeItemRenderer = new PipeItemRenderer();

	public final static BootsItemRenderer bootsItemRenderer = new BootsItemRenderer();

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAutomoton.class,
				new RenderAutomoton());
		MinecraftForgeClient.registerItemRenderer(ModItems.clockworkBow,
				new BowRenderer());
		final PipeRendererTESR rp = new PipeRendererTESR();
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.pipe), pipeItemRenderer);

		MinecraftForgeClient.registerItemRenderer(ModItems.mechBoots,
				bootsItemRenderer);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, rp);

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		if (event.map.getTextureType() == 1) {

			IconRegistry.addIcon("IconButton", Reference.ModID
					+ ":icons/Icon_Button", event.map);
			IconRegistry.addIcon("IconButtonHighlight", Reference.ModID
					+ ":icons/Icon_Button_Highlight", event.map);
			IconRegistry.addIcon("IconButtonInactive", Reference.ModID
					+ ":icons/Icon_Button_Inactive", event.map);

			IconRegistry.addIcon("IconGunpowder",
					Items.gunpowder.getIconFromDamage(0));
			IconRegistry.addIcon("IconRedstone",
					Items.redstone.getIconFromDamage(0));
			IconRegistry.addIcon("IconRSTorchOff", Reference.ModID
					+ ":icons/Icon_RSTorchOff", event.map);
			IconRegistry.addIcon("IconRSTorchOn", Reference.ModID
					+ ":icons/Icon_RSTorchOn", event.map);
		}
	}

	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(this);

	}

}
