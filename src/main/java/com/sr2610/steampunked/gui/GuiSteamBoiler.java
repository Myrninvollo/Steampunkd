package com.sr2610.steampunked.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;

public class GuiSteamBoiler extends GuiMachine {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/boiler.png");

	private TileEntitySteamBoiler BoilerInventory;

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 153;
	private static final int TANK_Y = 9;

	private static final int TANK_OVERLAY_X = 176;
	private static final int TANK_OVERLAY_Y = 9;

	public GuiSteamBoiler(TileEntitySteamBoiler cs, IInventory player_inv) {
		super(new ContainerSteamBoiler(cs, player_inv));
		ySize = 166;
		BoilerInventory = cs;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Steam Boiler", 5, 6, 0x404040);
		fontRendererObj.drawString("Inventory", 8, ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUI_TEXTURE);
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

		DisplayTank(window_x, window_y, TANK_X - 18, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, BoilerInventory.GetTank(0));
		DisplayTank(window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, BoilerInventory.GetTank(1));

		int i1 = BoilerInventory.getBurnTimeRemainingScaled(12);
		drawTexturedModalRect(guiLeft + 82, guiTop + 25 + 12 - i1, 176, 75+14 - i1, 14, i1 + 2);
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X - 18, TANK_Y, 16, TANK_HEIGHT, mouse_x,
				mouse_y)) {
			DisplayTankTooltip(mouse_x, mouse_y, BoilerInventory.GetTank(0));
		}
		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y)) {
			DisplayTankTooltip(mouse_x, mouse_y, BoilerInventory.GetTank(1));
		}

	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();

	}

}
