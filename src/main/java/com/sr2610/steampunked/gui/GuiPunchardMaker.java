package com.sr2610.steampunked.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.inventory.container.ContainerPunchcardmaker;
import com.sr2610.steampunked.items.ItemChasis;
import com.sr2610.steampunked.items.ItemPunchcard;
import com.sr2610.steampunked.items.ModItems;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.network.PacketTinkerTable;
import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;

public class GuiPunchardMaker extends GuiMachine {

	private IInventory player_inventory;
	private TileEntityPunchardMaker inventory;

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(
			Reference.ModID, "textures/gui/maker.png");

	public GuiPunchardMaker(TileEntityPunchardMaker cs, IInventory player_inv) {
		super(new ContainerPunchcardmaker(cs, (InventoryPlayer) player_inv));
		player_inventory = player_inv;
		ySize = 166;
		inventory = cs;
	}

	@Override
	protected ResourceLocation GetGUITexture() {
		return GUI_TEXTURE;
	}

	@Override
	public void initGui() {
		super.initGui();
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiButton(1, width / 2 + 40, height / 2 - 35, 40,
				20, "Stamp"));

	}
	
	public void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			if (inventory.getStackInSlot(0) != null
					&& inventory.getStackInSlot(0).getItem() instanceof ItemPunchcard &&  inventory.getStackInSlot(0).getItemDamage() == 2) {
				ItemStack stack = new ItemStack(ModItems.punchcard, 1, 0);
				inventory.setInventorySlotContents(2, stack);
				updateServer(stack);
				inventory.markDirty();
			}
		}
	}

	void updateServer(ItemStack stack) {
		Steampunked.packetPipeline.sendToServer(new PacketMaker(
				inventory.xCoord, inventory.yCoord,
				inventory.zCoord, stack));

	}


	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

		fontRendererObj.drawString("Punchcard Maker", 5, 6, 0x404040);
		fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUI_TEXTURE);
		int window_x = (width - xSize) / 2;
		int window_y = (height - ySize) / 2;
		drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

	}
}
