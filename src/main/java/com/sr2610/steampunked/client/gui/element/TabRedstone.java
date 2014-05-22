/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.element;

import java.util.List;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.api.utils.IRedstoneControl;
import com.sr2610.steampunked.api.utils.IRedstoneControl.ControlMode;
import com.sr2610.steampunked.api.utils.StringHelper;
import com.sr2610.steampunked.client.gui.GuiBase;
import com.sr2610.steampunked.common.network.PacketRedstoneMode;

public class TabRedstone extends TabBase {

	public static final String[] TOOLTIPS = {
			StringHelper.localize("steampunked.gui.redstoneStateIgnored"),
			StringHelper.localize("steampunked.gui.redstoneStateLow"),
			StringHelper.localize("steampunked.gui.redstoneStateHigh") };

	IRedstoneControl myTile;
	int headerColor = 0xe1c92f;
	int subheaderColor = 0xaaafb8;
	int textColor = 0x000000;

	public TabRedstone(GuiBase gui, IRedstoneControl theTile) {

		super(gui);

		myTile = theTile;
		maxHeight = 92;
		maxWidth = 112;
		backgroundColor = 0xd0230a;
	}

	@Override
	public void draw() {

		drawBackground();
		drawTabIcon("IconRedstone");
		if (!isFullyOpened())
			return;
		GuiBase.guiFontRenderer.drawStringWithShadow(
				StringHelper.localize("steampunked.gui.redstoneControl"),
				posX + 20, posY + 6, headerColor);
		GuiBase.guiFontRenderer.drawStringWithShadow(
				StringHelper.localize("steampunked.gui.controlStatus") + ":",
				posX + 8, posY + 42, subheaderColor);
		GuiBase.guiFontRenderer.drawStringWithShadow(
				StringHelper.localize("steampunked.gui.signalRequired") + ":",
				posX + 8, posY + 66, subheaderColor);

		if (myTile.getControl() == ControlMode.DISABLED) {
			gui.drawButton("IconRedstone", posX + 28, posY + 20, 1, 1);
			gui.drawButton("IconRSTorchOff", posX + 48, posY + 20, 1, 0);
			gui.drawButton("IconRSTorchOn", posX + 68, posY + 20, 1, 0);
			GuiBase.guiFontRenderer
					.drawString(StringHelper
							.localize("steampunked.gui.redstoneControlOff"),
							posX + 16, posY + 54, textColor);
			GuiBase.guiFontRenderer.drawString(TOOLTIPS[0], posX + 16,
					posY + 78, textColor);
		} else {
			GuiBase.guiFontRenderer.drawString(
					StringHelper.localize("steampunked.gui.redstoneControlOn"),
					posX + 16, posY + 54, textColor);

			if (myTile.getControl() == ControlMode.LOW) {
				gui.drawButton("IconRedstone", posX + 28, posY + 20, 1, 0);
				gui.drawButton("IconRSTorchOff", posX + 48, posY + 20, 1, 1);
				gui.drawButton("IconRSTorchOn", posX + 68, posY + 20, 1, 0);
				GuiBase.guiFontRenderer.drawString(TOOLTIPS[1], posX + 16,
						posY + 78, textColor);
			} else {
				gui.drawButton("IconRedstone", posX + 28, posY + 20, 1, 0);
				gui.drawButton("IconRSTorchOff", posX + 48, posY + 20, 1, 0);
				gui.drawButton("IconRSTorchOn", posX + 68, posY + 20, 1, 1);
				GuiBase.guiFontRenderer.drawString(TOOLTIPS[2], posX + 16,
						posY + 78, textColor);
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addTooltip(List<String> list) {

		if (!isFullyOpened()) {
			list.add(StringHelper.localize("steampunked.gui.redstoneControl"));
			return;
		}
		int x = gui.getMouseX() - currentShiftX;
		int y = gui.getMouseY() - currentShiftY;
		if (28 <= x && x < 44 && 20 <= y && y < 36)
			list.add(TOOLTIPS[0]);
		else if (48 <= x && x < 64 && 20 <= y && y < 36)
			list.add(TOOLTIPS[1]);
		else if (68 <= x && x < 84 && 20 <= y && y < 36)
			list.add(TOOLTIPS[2]);
	}

	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {

		if (!isFullyOpened())
			return false;
		x -= currentShiftX;
		y -= currentShiftY;

		if (x < 24 || x >= 88 || y < 16 || y >= 40)
			return false;
		if (28 <= x && x < 44 && 20 <= y && y < 36) {
			if (!myTile.getControl().isDisabled())
				sendRedstonePacket((TileEntity) myTile, 0);
		} else if (48 <= x && x < 64 && 20 <= y && y < 36) {
			if (!myTile.getControl().isLow())
				sendRedstonePacket((TileEntity) myTile, 1);
		} else if (68 <= x && x < 84 && 20 <= y && y < 36)
			if (!myTile.getControl().isHigh())
				sendRedstonePacket((TileEntity) myTile, 2);
		return true;
	}

	@Override
	protected void drawBackground() {

		super.drawBackground();

		if (!isFullyOpened())
			return;
		float colorR = (backgroundColor >> 16 & 255) / 255.0F * 0.6F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F * 0.6F;
		float colorB = (backgroundColor & 255) / 255.0F * 0.6F;
		GL11.glColor4f(colorR, colorG, colorB, 1.0F);
		gui.drawTexturedModalRect(posX + 24, posY + 16, 16, 20, 64, 24);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void sendRedstonePacket(TileEntity tile, int mode) {
		Steampunked.packetPipeline.sendToServer(new PacketRedstoneMode(
				tile.xCoord, tile.yCoord, tile.zCoord, mode));

	}

}
