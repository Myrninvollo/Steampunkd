package com.sr2610.steampunked.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.common.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;

public class GuiPunchardMaker extends GuiBaseAdv {

	TileEntityPunchardMaker tile;

	public GuiPunchardMaker(InventoryPlayer inventory, TileEntity te) {
		super(new ContainerPunchcardmaker(inventory,
				(TileEntityPunchardMaker) te), TEXTURE);
		tile = (TileEntityPunchardMaker) te;
		name = tile.getInventoryName();
		xSize = 176;
		ySize = 196;
	}

	@Override
	public void initGui() {
		super.initGui();

	}

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/maker.png");

}
