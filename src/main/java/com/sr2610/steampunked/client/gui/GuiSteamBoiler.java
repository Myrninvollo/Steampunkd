package com.sr2610.steampunked.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.client.gui.element.ElementDualScaled;
import com.sr2610.steampunked.client.gui.element.ElementFluidTank;
import com.sr2610.steampunked.client.gui.element.TabRedstone;
import com.sr2610.steampunked.common.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBoiler;

public class GuiSteamBoiler extends GuiBaseAdv {
	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/boiler.png");

	private final TileEntitySteamBoiler tileInv;

	ElementDualScaled duration;

	public GuiSteamBoiler(TileEntitySteamBoiler tile, IInventory player_inv) {
		super(new ContainerSteamBoiler(tile, player_inv), TEXTURE);
		ySize = 166;
		tileInv = tile;

	}

	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementFluidTank(this, 135, 9, tileInv.GetTank(0)));

		addElement(new ElementFluidTank(this, 153, 9, tileInv.GetTank(1)));
		duration = (ElementDualScaled) addElement(new ElementDualScaled(this,
				80, 22).setSize(16, 16).setTexture(
				Reference.ModID + ":textures/gui/elements/flame.png", 32, 16));

		addTab(new TabRedstone(this, tileInv));
	}

	@Override
	protected void updateElements() {
		duration.setQuantity(tileInv.getBurnTimeRemainingScaled(12));
	}

}
