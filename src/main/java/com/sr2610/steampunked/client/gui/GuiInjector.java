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

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.client.gui.components.BaseComponent;
import com.sr2610.steampunked.client.gui.components.GuiComponentIconButton;
import com.sr2610.steampunked.client.gui.components.GuiComponentLabel;
import com.sr2610.steampunked.client.gui.components.GuiComponentTab;
import com.sr2610.steampunked.client.gui.components.GuiComponentTabs;
import com.sr2610.steampunked.client.gui.components.IComponentListener;
import com.sr2610.steampunked.client.gui.components.BaseComponent.TabColor;
import com.sr2610.steampunked.client.utils.FakeIcon;
import com.sr2610.steampunked.common.inventory.container.ContainerInjector;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.network.PacketMachineRedstone;
import com.sr2610.steampunked.common.tileentities.TileEntityInjector;

public class GuiInjector extends GuiMachine implements IComponentListener {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/injector.png");

	private static final int TANK_HEIGHT = 60;
	private static final int TANK_X = 153;
	private static final int TANK_Y = 9;

	private static final int TANK_OVERLAY_X = 176;
	private static final int TANK_OVERLAY_Y = 9;

	private TileEntityInjector injectorInventory;

	private GuiComponentTabs tabs;

	private GuiComponentTab tabRedstone;
	private GuiComponentLabel labelRedstoneControl;
	private GuiComponentLabel labelInfo;
	private GuiComponentLabel labelInfoLow;
	private GuiComponentLabel labelInfoHigh;

	private GuiComponentIconButton buttonDisable;
	private GuiComponentIconButton buttonLow;
	private GuiComponentIconButton buttonHigh;

	public GuiInjector(TileEntityInjector cs, IInventory player_inv) {
		super(new ContainerInjector(cs, player_inv));
		ySize = 166;
		injectorInventory = cs;
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

		fontRendererObj.drawString("Steam Injector", 5, 6, 0x404040);
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
				TANK_OVERLAY_X, TANK_OVERLAY_Y, injectorInventory.GetTank(0));
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float par3) {
		super.drawScreen(mouse_x, mouse_y, par3);

		if (func_146978_c(TANK_X, TANK_Y, 16, TANK_HEIGHT, mouse_x, mouse_y))
			DisplayTankTooltip(mouse_x, mouse_y, injectorInventory.GetTank(0));

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
					injectorInventory.xCoord, injectorInventory.yCoord,
					injectorInventory.zCoord, 0));
		if (component.getName().equals("btnLow"))
			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					injectorInventory.xCoord, injectorInventory.yCoord,
					injectorInventory.zCoord, 1));
		if (component.getName().equals("btnHigh"))
			Steampunked.packetPipeline.sendToServer(new PacketMachineRedstone(
					injectorInventory.xCoord, injectorInventory.yCoord,
					injectorInventory.zCoord, 2));
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
		switch (injectorInventory.getRedstoneMode()) {
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
