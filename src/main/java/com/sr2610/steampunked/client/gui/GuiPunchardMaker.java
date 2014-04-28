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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.items.automotons.ItemPunchcard;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.network.PacketPunchcardMaker;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;

public class GuiPunchardMaker extends GuiMachine {

	private final TileEntityPunchardMaker inventory;

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
			final ItemStack is = inventory.getStackInSlot(0);
			final ItemStack is2 = inventory.getStackInSlot(1);
			final ItemStack is3 = inventory.getStackInSlot(2);
			final ItemStack is4 = inventory.getStackInSlot(3);
			if (is4 != null && is4.getItem() == Items.dye
					&& is4.getItemDamage() == 0)
				if (is != null && is.getItem() instanceof ItemPunchcard
						&& is.getItemDamage() == 0 && is3 == null
						&& is2 != null
						&& is2.getItem() instanceof ItemPunchcard
						&& is2.getItemDamage() >= 11) {
					final ItemStack stack = new ItemStack(ModItems.punchcard,
							1, is2.getItemDamage() - 10);
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
		final int window_x = (width - xSize) / 2;
		final int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

	}
}
