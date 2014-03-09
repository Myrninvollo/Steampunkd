package com.sr2610.steampunked.gui;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.gui.components.GuiComponentLabel;
import com.sr2610.steampunked.gui.components.GuiComponentTab;
import com.sr2610.steampunked.gui.components.GuiComponentTabs;
import com.sr2610.steampunked.gui.components.BaseComponent.TabColor;
import com.sr2610.steampunked.inventory.container.ContainerInjector;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.tileentities.TileEntityInjector;

public class GuiInjector extends GuiMachine {
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


	public GuiInjector(TileEntityInjector cs, IInventory player_inv) {
		super(new ContainerInjector(cs, player_inv));
		ySize = 166;
		injectorInventory = cs;
		tabs = new GuiComponentTabs(xSize - 3, 4);
		tabRedstone = new GuiComponentTab(TabColor.red.getColor(), new ItemStack(Items.redstone), 100, 100);
		labelRedstoneControl = new GuiComponentLabel(20, 8, StatCollector.translateToLocal("steampunked.gui.redstoneControl.name"));
		tabRedstone.addComponent(labelRedstoneControl);
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

}