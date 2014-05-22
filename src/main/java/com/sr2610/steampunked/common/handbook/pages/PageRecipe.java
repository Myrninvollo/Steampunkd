package com.sr2610.steampunked.common.handbook.pages;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.sr2610.steampunked.api.handbook.HandbookPage;
import com.sr2610.steampunked.api.handbook.HandbookRecipeMappings;
import com.sr2610.steampunked.api.handbook.HandbookRecipeMappings.EntryData;
import com.sr2610.steampunked.api.handbook.IGuiHandbookEntry;
import com.sr2610.steampunked.client.gui.handbook.GuiHandbookEntry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PageRecipe extends HandbookPage {

	int relativeMouseX, relativeMouseY;
	ItemStack tooltipStack, tooltipContainerStack;
	boolean tooltipEntry;

	public PageRecipe(String unlocalizedName) {
		super(unlocalizedName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderScreen(IGuiHandbookEntry gui, int mx, int my) {
		relativeMouseX = mx;
		relativeMouseY = my;

		renderRecipe(gui, mx, my);

		final int width = gui.getWidth() - 30;
		final int height = gui.getHeight();
		final int x = gui.getLeft() + 16;
		final int y = gui.getTop() + height - 80;
		PageText.renderText(x, y + 10, width, height, getUnlocalizedName());

		if (tooltipStack != null) {
			final List<String> tooltipData = tooltipStack.getTooltip(
					Minecraft.getMinecraft().thePlayer, false);
			com.sr2610.steampunked.api.utils.RenderHelper.renderTooltip(mx, my,
					tooltipData);

			if (tooltipEntry)
				tooltipData
						.add(EnumChatFormatting.GRAY
								+ StatCollector
										.translateToLocal("steampunked.gui.handbook.clickToRecipe"));

			if (tooltipContainerStack != null)
				tooltipData
						.add(EnumChatFormatting.AQUA
								+ StatCollector
										.translateToLocal("steampunked.gui.handbook.craftingContainer"));

			com.sr2610.steampunked.api.utils.RenderHelper.renderTooltip(mx, my,
					tooltipData);

		}

		tooltipStack = tooltipContainerStack = null;
		tooltipEntry = false;
		GL11.glDisable(GL11.GL_BLEND);
	}

	@SideOnly(Side.CLIENT)
	public void renderRecipe(IGuiHandbookEntry gui, int mx, int my) {
		// NO-OP
	}

	@SideOnly(Side.CLIENT)
	public void renderItemAtAngle(IGuiHandbookEntry gui, int angle,
			ItemStack stack) {
		if (stack == null || stack.getItem() == null)
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		angle -= 90;
		final int radius = 32;
		final double xPos = gui.getLeft() + Math.cos(angle * Math.PI / 180D)
				* radius + gui.getWidth() / 2 - 8;
		final double yPos = gui.getTop() + Math.sin(angle * Math.PI / 180D)
				* radius + 53;
		final ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, (int) xPos, (int) yPos, stack1, false);
	}

	@SideOnly(Side.CLIENT)
	public void renderItemAtGridPos(IGuiHandbookEntry gui, int x, int y,
			ItemStack stack, boolean accountForContainer) {
		if (stack == null || stack.getItem() == null)
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = gui.getLeft() + x * 25 - 1;
		int yPos = gui.getTop() + y * 26 - y * 2 + 4 - y - (y == 3 ? 0 : 2)
				- (y == 1 ? 1 : 0) + 10;

		if (x == 2 && y == 0) {
			xPos = gui.getLeft() + (x + 2) * 25 + 3;
			yPos = gui.getTop() + (y + 2) * 26 - 4 + 10;
		}
		final ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer);
	}

	@SideOnly(Side.CLIENT)
	public void renderItem(IGuiHandbookEntry gui, int xPos, int yPos,
			ItemStack stack, boolean accountForContainer) {
		yPos += 10;
		final RenderItem render = new RenderItem();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(
				Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft()
						.getTextureManager(), stack, xPos, yPos);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		if (relativeMouseX >= xPos && relativeMouseY >= yPos
				&& relativeMouseX <= xPos + 16 && relativeMouseY <= yPos + 16) {
			tooltipStack = stack;

			final EntryData data = HandbookRecipeMappings
					.getDataForStack(tooltipStack);
			if (data != null
					&& (data.entry != gui.getEntry() || data.page != gui
							.getPageOn())) {
				tooltipEntry = true;

				if (Mouse.isButtonDown(0)) {
					final GuiHandbookEntry newGui = new GuiHandbookEntry(
							data.entry, (GuiScreen) gui);
					newGui.page = data.page;
					Minecraft.getMinecraft().displayGuiScreen(newGui);
				}
			}

			if (accountForContainer) {
				final ItemStack containerStack = stack.getItem()
						.getContainerItem(stack);
				if (containerStack != null && containerStack.getItem() != null)
					tooltipContainerStack = containerStack;
			}
		}

		GL11.glDisable(GL11.GL_LIGHTING);
	}

}
