package com.sr2610.steampunked.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.client.gui.element.ElementFluidTank;
import com.sr2610.steampunked.client.gui.element.TabRedstone;
import com.sr2610.steampunked.common.inventory.container.ContainerInjector;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityInjector;

public class GuiInjector extends GuiBaseAdv {

	static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID + ":textures/gui/injector.png");
	static final String INFO = "Generates Redstone Flux using Steam.\n\nSolid Fuels and Water can be used to generate Steam.\n\nGeneration rate varies according to energy demand.";
	TileEntityInjector myTile;

	public GuiInjector(InventoryPlayer inventory, TileEntity theTile) {
		super(new ContainerInjector(inventory, (TileEntityInjector) theTile),
				TEXTURE);
		myTile = (TileEntityInjector) theTile;
		name = myTile.getInventoryName();
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTank(this, 153, 9, myTile.GetTank(0)));

		addTab(new TabRedstone(this, myTile));
	}

}
