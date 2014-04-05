/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as * part of the
 * Steampunk'd Mod. Get the Source Code in github: *
 * https://github.com/SR2610/steampunkd * * Steampunk'd is Open Source and
 * distributed under a * Creative Commons Attribution-NonCommercial-ShareAlike
 * 3.0 License * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.handbook.pages;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.api.handbook.HandbookPage;
import com.sr2610.steampunked.api.handbook.HandbookRecipeMappings;
import com.sr2610.steampunked.api.handbook.HandbookRecipeMappings.EntryData;
import com.sr2610.steampunked.api.handbook.IGuiHandbookEntry;
import com.sr2610.steampunked.gui.handbook.GuiHandbookEntry;
import com.sr2610.steampunked.utils.RenderHelper;

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

		int width = gui.getWidth() - 30;
		int height = gui.getHeight();
		int x = gui.getLeft() + 16;
		int y = gui.getTop() + height - 40;
		PageText.renderText(x, y, width, height, getUnlocalizedName());

		if (tooltipStack != null) {
			List<String> tooltipData = tooltipStack.getTooltip(
					Minecraft.getMinecraft().thePlayer, false);
			RenderHelper.renderTooltip(mx, my, tooltipData);

			int tooltipY = 8 + tooltipData.size() * 11;

			if (tooltipEntry) {
				RenderHelper
						.renderTooltipOrange(
								mx,
								my + tooltipY,
								Arrays.asList(EnumChatFormatting.GRAY
										+ StatCollector
												.translateToLocal("botaniamisc.clickToRecipe")));
				tooltipY += 18;
			}

			if (tooltipContainerStack != null)
				RenderHelper
						.renderTooltipGreen(
								mx,
								my + tooltipY,
								Arrays.asList(
										EnumChatFormatting.AQUA
												+ StatCollector
														.translateToLocal("botaniamisc.craftingContainer"),
										tooltipContainerStack.getDisplayName()));
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
		int radius = 32;
		double xPos = gui.getLeft() + Math.cos(angle * Math.PI / 180D) * radius
				+ gui.getWidth() / 2 - 8;
		double yPos = gui.getTop() + Math.sin(angle * Math.PI / 180D) * radius
				+ 53;
		ItemStack stack1 = stack.copy();
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

		int xPos = gui.getLeft() + x * 25 - 1 ;
		int yPos = gui.getTop() + y * 26 - (y * 2) + 4 - y
				- (y == 3 ? 0 : 2)- (y == 1 ? 1 : 0);
		
		if(x==2&&y==0){
			xPos= gui.getLeft() + (x+2) * 25 +3;
			yPos = gui.getTop() + (y+2) * 26-4 ;
		}
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer);
	}

	@SideOnly(Side.CLIENT)
	public void renderItem(IGuiHandbookEntry gui, int xPos, int yPos,
			ItemStack stack, boolean accountForContainer) {
		RenderItem render = new RenderItem();
		TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		// Translations required so the glint doesn't merge with the book
		// texture
		GL11.glTranslatef(0F, 0F, 200F);
		if (!ForgeHooksClient.renderInventoryItem(new RenderBlocks(),
				renderEngine, stack, render.renderWithColor, gui.getZLevel(),
				xPos, yPos))
			render.renderItemIntoGUI(fontRenderer, renderEngine, stack, xPos,
					yPos);
		render.renderItemOverlayIntoGUI(fontRenderer, renderEngine, stack,
				xPos, yPos);
		GL11.glTranslatef(0F, 0F, -200F);

		if (relativeMouseX >= xPos && relativeMouseY >= yPos
				&& relativeMouseX <= xPos + 16 && relativeMouseY <= yPos + 16) {
			tooltipStack = stack;

			EntryData data = HandbookRecipeMappings
					.getDataForStack(tooltipStack);
			if (data != null
					&& (data.entry != gui.getEntry() || data.page != gui
							.getPageOn())) {
				tooltipEntry = true;

				if (Mouse.isButtonDown(0) && GuiScreen.isShiftKeyDown()) {
					GuiHandbookEntry newGui = new GuiHandbookEntry(data.entry,
							(GuiScreen) gui);
					newGui.page = data.page;
					Minecraft.getMinecraft().displayGuiScreen(newGui);
				}
			}

			if (accountForContainer) {
				ItemStack containerStack = stack.getItem().getContainerItem(
						stack);
				if (containerStack != null && containerStack.getItem() != null)
					tooltipContainerStack = containerStack;
			}
		}

		GL11.glDisable(GL11.GL_LIGHTING);
	}

}