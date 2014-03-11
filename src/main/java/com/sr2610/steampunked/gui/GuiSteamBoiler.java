package com.sr2610.steampunked.gui;

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
import com.sr2610.steampunked.inventory.container.ContainerSteamBoiler;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketMachineRedstone;
import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.utils.FakeIcon;

public class GuiSteamBoiler extends GuiMachine implements IComponentListener {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/boiler.png");

	private TileEntitySteamBoiler BoilerInventory;

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 153;
	private static final int TANK_Y = 9;

	private static final int TANK_OVERLAY_X = 176;
	private static final int TANK_OVERLAY_Y = 9;

	private GuiComponentTabs tabs;

	private GuiComponentTab tabRedstone;
	private GuiComponentLabel labelRedstoneControl;
	private GuiComponentLabel labelInfo;
	private GuiComponentLabel labelInfoLow;
	private GuiComponentLabel labelInfoHigh;

	private GuiComponentIconButton buttonDisable;
	private GuiComponentIconButton buttonLow;
	private GuiComponentIconButton buttonHigh;

	public GuiSteamBoiler(TileEntitySteamBoiler cs, IInventory player_inv) {
		super(new ContainerSteamBoiler(cs, player_inv));
		ySize = 166;
		BoilerInventory = cs;
		tabs = new GuiComponentTabs(xSize - 3, 4);
		tabRedstone = new GuiComponentTab(TabColor.red.getColor(),
				new ItemStack(Items.redstone), 100, 100);
		labelRedstoneControl = new GuiComponentLabel(20, 8,
				StatCollector
						.translateToLocal("steampunked.gui.redstoneControl"),
				true);
		labelInfo = new GuiComponentLabel(8, 64, 96, 88, getInfo(0), false);
		labelInfoLow = new GuiComponentLabel(8, 64, 96, 88, getInfo(1), false);
		labelInfoHigh = new GuiComponentLabel(8, 64, 96, 88, getInfo(2), false);

		tabRedstone.addComponent(labelInfo);
		tabRedstone.addComponent(labelInfoLow);
		tabRedstone.addComponent(labelInfoHigh);
		tabRedstone.addComponent(labelRedstoneControl);
		root.addComponent(buttonDisable = (GuiComponentIconButton) new GuiComponentIconButton(
				10, 32, 0xFFFFFF, FakeIcon.createSheetIcon(0, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this).setName(
				"btnDisable"));
		tabRedstone.addComponent(buttonDisable);
		root.addComponent(buttonLow = (GuiComponentIconButton) new GuiComponentIconButton(
				40, 32, 0xFFFFFF, FakeIcon.createSheetIcon(16, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this)
				.setName("btnLow"));
		tabRedstone.addComponent(buttonLow);
		root.addComponent(buttonHigh = (GuiComponentIconButton) new GuiComponentIconButton(
				70, 32, 0xFFFFFF, FakeIcon.createSheetIcon(32, 82, 16, 16),
				BaseComponent.TEXTURE_SHEET).addListener(this).setName(
				"btnHigh"));
		tabRedstone.addComponent(buttonHigh);

		tabs.addComponent(tabRedstone);
		root.addComponent(tabs);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Steam Boiler", 5, 6, 0x404040);
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

		DisplayTank(window_x, window_y, TANK_X - 18, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, BoilerInventory.GetTank(0));
		DisplayTank(window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT,
				TANK_OVERLAY_X, TANK_OVERLAY_Y, BoilerInventory.GetTank(1));

		int i1 = BoilerInventory.getBurnTimeRemainingScaled(12);
		drawTexturedModalRect(guiLeft + 82, guiTop + 25 + 12 - i1, 176,
				75 + 14 - i1, 14, i1 + 2);
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X - 18, TANK_Y, 16, TANK_HEIGHT, mouse_x,
				mouse_y))
			DisplayTankTooltip(mouse_x, mouse_y, BoilerInventory.GetTank(0));
		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y))
			DisplayTankTooltip(mouse_x, mouse_y, BoilerInventory.GetTank(1));

	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();

	}

	@Override
	public void componentMouseDown(BaseComponent component, int offsetX,
			int offsetY, int button) {
		if (component.getName().equals("btnDisable"))
			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					BoilerInventory.xCoord, BoilerInventory.yCoord,
					BoilerInventory.zCoord, 0));
		if (component.getName().equals("btnLow"))
			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					BoilerInventory.xCoord, BoilerInventory.yCoord,
					BoilerInventory.zCoord, 1));
		if (component.getName().equals("btnHigh"))
			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					BoilerInventory.xCoord, BoilerInventory.yCoord,
					BoilerInventory.zCoord, 2));
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
		switch (BoilerInventory.getRedstoneMode()) {
		case 0:
			buttonDisable.setButtonEnabled(false);
			buttonLow.setButtonEnabled(true);
			buttonHigh.setButtonEnabled(true);
			labelInfo.setEnabled(true);
			labelInfoLow.setEnabled(false);
			labelInfoHigh.setEnabled(false);
			break;
		case 1:
			buttonDisable.setButtonEnabled(true);
			buttonLow.setButtonEnabled(false);
			buttonHigh.setButtonEnabled(true);
			labelInfo.setEnabled(false);
			labelInfoLow.setEnabled(true);
			labelInfoHigh.setEnabled(false);
			break;
		case 2:
			buttonDisable.setButtonEnabled(true);
			buttonLow.setButtonEnabled(true);
			buttonHigh.setButtonEnabled(false);
			labelInfo.setEnabled(false);
			labelInfoLow.setEnabled(false);
			labelInfoHigh.setEnabled(true);
			break;
		}
	}

}
