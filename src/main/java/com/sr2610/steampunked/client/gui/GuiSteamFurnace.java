/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.client.gui.element.ElementDualScaled;
import com.sr2610.steampunked.client.gui.element.ElementFluidTank;
import com.sr2610.steampunked.client.gui.element.TabRedstone;
import com.sr2610.steampunked.common.inventory.container.ContainerSteamFurnace;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamFurnace;

public class GuiSteamFurnace extends GuiBaseAdv {

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/steamFurnace.png");

	private final TileEntitySteamFurnace furnace;
	ElementDualScaled duration;
	ElementDualScaled progress;

	public GuiSteamFurnace(TileEntitySteamFurnace cs, IInventory player_inv) {
		super(new ContainerSteamFurnace((InventoryPlayer) player_inv, cs),
				TEXTURE);

		ySize = 166;
		furnace = cs;

	}

	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementFluidTank(this, 153, 9, furnace.GetTank(0)));
		duration = (ElementDualScaled) addElement(new ElementDualScaled(this,
				56, 52).setSize(16, 16).setTexture(
				Reference.ModID + ":textures/gui/elements/flame.png", 32, 16));
		progress = (ElementDualScaled) addElement(new ElementDualScaled(this,
				79, 34)
				.setMode(1)
				.setSize(24, 16)
				.setTexture(
						Reference.ModID + ":textures/gui/elements/progress.png",
						48, 16));

		addTab(new TabRedstone(this, furnace));

	}

	@Override
	protected void updateElements() {
		if (furnace.getCookProgressScaled(24) > 0)
			duration.setQuantity(16);
		else
			duration.setQuantity(0);
		progress.setQuantity(furnace.getCookProgressScaled(24));

	}

}
