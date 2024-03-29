/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.handbook.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.api.handbook.HandbookRecipeMappings;
import com.sr2610.steampunked.api.handbook.IGuiHandbookEntry;
import com.sr2610.steampunked.api.utils.RenderHelper;
import com.sr2610.steampunked.common.lib.Reference;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PageCraftingRecipe extends PageRecipe {

	private static final ResourceLocation craftingOverlay = new ResourceLocation(
			Reference.ModID, "textures/gui/handbook/craftingOverlay.png");

	List<IRecipe> recipes;
	int ticksElapsed = 0;
	int recipeAt = 0;
	String name;

	boolean oreDictRecipe, shapelessRecipe;

	public PageCraftingRecipe(String unlocalizedName, List<IRecipe> recipes) {
		super(unlocalizedName);
		this.recipes = recipes;
	}

	public PageCraftingRecipe(String unlocalizedName, IRecipe recipe) {
		this(unlocalizedName, Arrays.asList(recipe));
	}

	@Override
	public void onPageAdded(HandbookEntry entry, int index) {
		for (final IRecipe recipe : recipes)
			HandbookRecipeMappings.map(recipe.getRecipeOutput(), entry, index);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderRecipe(IGuiHandbookEntry gui, int mx, int my) {

		final FontRenderer fontRendererObj = ((GuiScreen) gui).mc.fontRenderer;

		final boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(true);

		String title = StatCollector
				.translateToLocal("steampunked.gui.handbook.crafting");
		drawCenteredStringNoShadow(fontRendererObj, title, gui.getLeft()
				+ fontRendererObj.getStringWidth(title), gui.getTop() + 10, 0);

		fontRendererObj.setUnicodeFlag(unicode);

		oreDictRecipe = shapelessRecipe = false;

		final IRecipe recipe = recipes.get(recipeAt);
		renderCraftingRecipe(gui, recipe);

		final TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(craftingOverlay);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getLeft(),
				gui.getTop() + 10, 0, 0, gui.getWidth(), gui.getHeight());

		int iconX = gui.getLeft() + 115;
		final int iconY = gui.getTop() + 12 + 10;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		if (shapelessRecipe) {
			((GuiScreen) gui).drawTexturedModalRect(iconX, iconY, 240, 0, 16,
					16);

			if (mx >= iconX && my >= iconY && mx < iconX + 16
					&& my < iconY + 16)
				RenderHelper
						.renderTooltip(
								mx,
								my,
								Arrays.asList(StatCollector
										.translateToLocal("steampunked.gui.handbook.shapeless")));

			iconX = gui.getLeft() + 95;
		}

		render.bindTexture(craftingOverlay);
		GL11.glEnable(GL11.GL_BLEND);

		if (oreDictRecipe) {
			((GuiScreen) gui).drawTexturedModalRect(iconX, iconY, 240, 16, 16,
					16);

			if (mx >= iconX && my >= iconY && mx < iconX + 16
					&& my < iconY + 16)
				RenderHelper.renderTooltip(mx, my, Arrays.asList(StatCollector
						.translateToLocal("steampunked.gui.handbook.oredict")));
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateScreen() {
		if (ticksElapsed % 20 == 0) {
			recipeAt++;

			if (recipeAt == recipes.size())
				recipeAt = 0;
		}
		++ticksElapsed;
	}

	@SideOnly(Side.CLIENT)
	public void renderCraftingRecipe(IGuiHandbookEntry gui, IRecipe recipe) {
		if (recipe instanceof ShapedRecipes) {
			final ShapedRecipes shaped = (ShapedRecipes) recipe;

			for (int y = 0; y < shaped.recipeHeight; y++)
				for (int x = 0; x < shaped.recipeWidth; x++)
					renderItemAtGridPos(gui, 1 + x, 1 + y, shaped.recipeItems[y
							* shaped.recipeWidth + x], true);
		} else if (recipe instanceof ShapedOreRecipe) {
			final ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;
			final int width = (Integer) ReflectionHelper.getPrivateValue(
					ShapedOreRecipe.class, shaped, 4);
			final int height = (Integer) ReflectionHelper.getPrivateValue(
					ShapedOreRecipe.class, shaped, 5);

			for (int y = 0; y < height; y++)
				for (int x = 0; x < width; x++) {
					final Object input = shaped.getInput()[y * width + x];
					if (input != null)
						renderItemAtGridPos(
								gui,
								1 + x,
								1 + y,
								input instanceof ItemStack ? (ItemStack) input
										: ((ArrayList<ItemStack>) input).get(0),
								true);
				}

			oreDictRecipe = true;
		} else if (recipe instanceof ShapelessRecipes) {
			final ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

			drawGrid: {
				for (int y = 0; y < 3; y++)
					for (int x = 0; x < 3; x++) {
						final int index = y * 3 + x;

						if (index >= shapeless.recipeItems.size())
							break drawGrid;

						renderItemAtGridPos(gui, 1 + x, 1 + y,
								(ItemStack) shapeless.recipeItems.get(index),
								true);
					}
			}

			shapelessRecipe = true;
		} else if (recipe instanceof ShapelessOreRecipe) {
			final ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

			drawGrid: {
				for (int y = 0; y < 3; y++)
					for (int x = 0; x < 3; x++) {
						final int index = y * 3 + x;

						if (index >= shapeless.getRecipeSize())
							break drawGrid;

						final Object input = shapeless.getInput().get(index);
						if (input != null)
							renderItemAtGridPos(
									gui,
									1 + x,
									1 + y,
									input instanceof ItemStack ? (ItemStack) input
											: ((ArrayList<ItemStack>) input)
													.get(0), true);
					}
			}

			shapelessRecipe = true;
			oreDictRecipe = true;
		}

		renderItemAtGridPos(gui, 2, 0, recipe.getRecipeOutput(), false);
	}

	void drawHeader(FontRenderer font, GuiScreen gui) {
		final boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);

		drawCenteredStringNoShadow(
				font,
				"Crafting",
				((IGuiHandbookEntry) gui).getLeft()
						+ font.getStringWidth("Crafting"),
				((IGuiHandbookEntry) gui).getTop() + 10, 0);

		font.setUnicodeFlag(unicode);
	}

	public void drawCenteredStringNoShadow(FontRenderer par1FontRenderer,
			String par2Str, int par3, int par4, int par5) {
		par1FontRenderer
				.drawString(par2Str,
						par3 - par1FontRenderer.getStringWidth(par2Str) / 2,
						par4, par5);
	}
}
