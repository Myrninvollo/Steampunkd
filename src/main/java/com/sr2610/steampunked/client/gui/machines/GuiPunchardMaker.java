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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.sr2610.steampunked.client.gui.GuiBaseAdv;
import com.sr2610.steampunked.client.gui.element.ElementButton;
import com.sr2610.steampunked.common.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.common.items.ModItems;
import com.sr2610.steampunked.common.lib.Reference;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;

public class GuiPunchardMaker extends GuiBaseAdv {

	TileEntityPunchardMaker tile;
	public ElementButton previousButton;
	public ElementButton nextButton;
	public ElementButton craftButton;

	public GuiPunchardMaker(InventoryPlayer inventory, TileEntity te) {
		super(new ContainerPunchcardmaker(inventory,
				(TileEntityPunchardMaker) te), TEXTURE);
		tile = (TileEntityPunchardMaker) te;
		name = tile.getInventoryName();
		xSize = 208;
		ySize = 196;
		drawInventory = false;
	}

	@Override
	public void initGui() {
		super.initGui();
		previousButton = new ElementButton(this, 5, 46, "left", 216, 3, 216,
				17, 14, 14, Reference.ModID + ":textures/gui/maker.png", null)
				.setToolTip("Previous Punchcard");

		nextButton = new ElementButton(this, 39, 46, "right", 230, 3, 230, 17,
				14, 14, Reference.ModID + ":textures/gui/maker.png", null)
				.setToolTip("Next Punchcard");

		craftButton = new ElementButton(this, 11, 70, "craft", 216, 31, 216,
				47, 36, 16, Reference.ModID + ":textures/gui/maker.png",
				"Craft");

		addElement(previousButton);
		addElement(nextButton);
		addElement(craftButton);

	}

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/maker.png");

	private static final ResourceLocation PUNCHCARD = new ResourceLocation(
			Reference.ModID, "textures/items/punchcards/punchcard_0.png");

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);

		fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 40,
				ySize - 96 + 3, 0x404040);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);

	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		if (buttonName == "right")
			tile.setInventorySlotContents(3, new ItemStack(ModItems.punchcard));

	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		mc.renderEngine.bindTexture(PUNCHCARD);
		if (tile.getStackInSlot(3) != null)
			drawTexturedModalRect(guiLeft + 57, guiTop + 29, 0, 0, 127, 61);

	}
}
