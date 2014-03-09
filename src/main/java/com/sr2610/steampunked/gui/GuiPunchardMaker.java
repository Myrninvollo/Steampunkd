package com.sr2610.steampunked.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.gui.components.BaseComponent.TabColor;
import com.sr2610.steampunked.gui.components.GuiComponentLabel;
import com.sr2610.steampunked.gui.components.GuiComponentTab;
import com.sr2610.steampunked.gui.components.GuiComponentTabs;
import com.sr2610.steampunked.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.items.ItemPunchcard;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketPunchcardMaker;
import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;

public class GuiPunchardMaker extends GuiMachine {

	private TileEntityPunchardMaker inventory;

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/maker.png");

	public GuiPunchardMaker(TileEntityPunchardMaker cs, IInventory player_inv) {
		super(new ContainerPunchcardmaker(cs, player_inv));
		ySize = 166;
		xSize = 176;
		inventory = cs;

	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(1, width / 2 + 40, height / 2 - 35, 40,
				20, "Stamp"));

	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			ItemStack is = inventory.getStackInSlot(0);
			ItemStack is2 = inventory.getStackInSlot(1);
			ItemStack is3 = inventory.getStackInSlot(2);
			ItemStack is4 = inventory.getStackInSlot(3);
			if (is4 != null && is4.getItem() == Items.dye
					&& is4.getItemDamage() == 0)
				if (is != null && is.getItem() instanceof ItemPunchcard
						&& is.getItemDamage() == 0 && is3 == null
						&& is2 != null
						&& is2.getItem() instanceof ItemPunchcard
						&& is2.getItemDamage() >= 11) {
					ItemStack stack = new ItemStack(ModItems.punchcard, 1,
							is2.getItemDamage() - 10);
					inventory.setInventorySlotContents(2, stack);
					updateServer(stack);
					inventory.markDirty();
				}
		}
	}

	void updateServer(ItemStack stack) {
		Steampunked.packetPipeline.sendToServer(new PacketPunchcardMaker(
				inventory.xCoord, inventory.yCoord, inventory.zCoord, stack));

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Punchcard Maker", 5, 6, 0x404040);
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

	}
}
