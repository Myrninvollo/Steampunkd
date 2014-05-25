/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.machines;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.sr2610.steampunked.client.gui.GuiBaseAdv;
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
