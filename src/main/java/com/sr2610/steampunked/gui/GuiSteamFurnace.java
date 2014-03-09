package com.sr2610.steampunked.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.gui.components.BaseComponent;
import com.sr2610.steampunked.gui.components.BaseComponent.TabColor;
import com.sr2610.steampunked.gui.components.GuiComponentIconButton;
import com.sr2610.steampunked.gui.components.GuiComponentLabel;
import com.sr2610.steampunked.gui.components.GuiComponentTab;
import com.sr2610.steampunked.gui.components.GuiComponentTabs;
import com.sr2610.steampunked.gui.components.IComponentListener;
import com.sr2610.steampunked.inventory.container.ContainerSteamFurnace;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketMachineRedstone;
import com.sr2610.steampunked.network.PacketPunchcardMaker;
import com.sr2610.steampunked.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.utils.FakeIcon;

public class GuiSteamFurnace extends GuiMachine implements IComponentListener {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/steamFurnace.png");

	private TileEntitySteamFurnace steamFurnaceInventory;

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 153;
	private static final int TANK_Y = 9;

	private static final int TANK_OVERLAY_X = 176;
	private static final int TANK_OVERLAY_Y = 9;
	private int mode;

	private GuiComponentTabs tabs;

	private GuiComponentTab tabRedstone;
	private GuiComponentLabel labelRedstoneControl;
	private GuiComponentLabel labelInfo;
	private GuiComponentIconButton buttonDisable;
	private GuiComponentIconButton buttonLow;
	private GuiComponentIconButton buttonHigh;

	public GuiSteamFurnace(TileEntitySteamFurnace cs, IInventory player_inv) {
		super(new ContainerSteamFurnace((InventoryPlayer) player_inv, cs));
		mode = cs.redstoneMode;
		ySize = 166;
		steamFurnaceInventory = cs;
		tabs = new GuiComponentTabs(xSize - 3, 4);
		tabRedstone = new GuiComponentTab(TabColor.red.getColor(),
				new ItemStack(Items.redstone), 100, 100);
		labelRedstoneControl = new GuiComponentLabel(
				20,
				8,
				StatCollector
						.translateToLocal("steampunked.gui.redstoneControl.name"));
		labelInfo = new GuiComponentLabel(8, 64, getInfo());
		tabRedstone.addComponent(labelInfo);

		tabRedstone.addComponent(labelRedstoneControl);
		root.addComponent(buttonDisable = (GuiComponentIconButton) new GuiComponentIconButton(
				8, 32, 0xFFFFFF, FakeIcon.createSheetIcon(0, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this).setName(
				"btnDisable"));
		tabRedstone.addComponent(buttonDisable);
		root.addComponent(buttonLow = (GuiComponentIconButton) new GuiComponentIconButton(
				30, 32, 0xFFFFFF, FakeIcon.createSheetIcon(16, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this)
				.setName("btnLow"));
		tabRedstone.addComponent(buttonLow);
		root.addComponent(buttonHigh = (GuiComponentIconButton) new GuiComponentIconButton(
				52, 32, 0xFFFFFF, FakeIcon.createSheetIcon(32, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this).setName(
				"btnHigh"));
		tabRedstone.addComponent(buttonHigh);

		tabs.addComponent(tabRedstone);
		root.addComponent(tabs);

	}

	private String getInfo() {
		switch (mode) {
		case 0:
			return ("Ignores Redstone");
		case 1:
			return ("Enabled");
		case 2:
			return ("Requires Redstone Signal to Operate");
		default:
			return ("Unexpected, Place and Replace");
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Steam Heated Furnace", 5, 6, 0x404040);
		fontRendererObj.drawString("Inventory", 8, ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUI_TEXTURE);
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);
		DisplayTank(window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y,
				steamFurnaceInventory.GetTank(0));
		int i1 = steamFurnaceInventory.getCookProgressScaled(24);
		drawTexturedModalRect(window_x + 79, window_y + 35, 176, 83, i1 + 1, 16);

	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y))
			DisplayTankTooltip(mouse_x, mouse_y,
					steamFurnaceInventory.GetTank(0));
	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void postRender(int mouseX, int mouseY) {
		super.postRender(mouseX, mouseY);
	}

	@Override
	public void componentMouseDown(BaseComponent component, int offsetX,
			int offsetY, int button) {
		if (component.getName().equals("btnDisable")) {

			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					steamFurnaceInventory.xCoord, steamFurnaceInventory.yCoord,
					steamFurnaceInventory.zCoord, 0));
			buttonDisable.setButtonEnabled(false);

			mode = 0;
		}
		if (component.getName().equals("btnLow")) {

			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					steamFurnaceInventory.xCoord, steamFurnaceInventory.yCoord,
					steamFurnaceInventory.zCoord, 1));
			buttonLow.setButtonEnabled(false);
			mode = 1;

		}
		if (component.getName().equals("btnHigh")) {

			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					steamFurnaceInventory.xCoord, steamFurnaceInventory.yCoord,
					steamFurnaceInventory.zCoord, 2));
			buttonHigh.setButtonEnabled(false);
			mode = 2;

		}
	}

	@Override
	public void componentMouseDrag(BaseComponent component, int offsetX,
			int offsetY, int button, long time) {
	}

	@Override
	public void componentMouseMove(BaseComponent component, int offsetX,
			int offsetY) {
	}

	@Override
	public void componentMouseUp(BaseComponent component, int offsetX,
			int offsetY, int button) {
	}

	@Override
	public void componentKeyTyped(BaseComponent component, char par1, int par2) {
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		switch (steamFurnaceInventory.redstoneMode) {
		case 0:
			buttonDisable.setButtonEnabled(false);
			buttonLow.setButtonEnabled(true);
			buttonHigh.setButtonEnabled(true);
			break;
		case 1:
			buttonDisable.setButtonEnabled(true);
			buttonLow.setButtonEnabled(false);
			buttonHigh.setButtonEnabled(true);
			break;
		case 2:
			buttonDisable.setButtonEnabled(true);
			buttonLow.setButtonEnabled(true);
			buttonHigh.setButtonEnabled(false);
			break;
		}
	}

}
