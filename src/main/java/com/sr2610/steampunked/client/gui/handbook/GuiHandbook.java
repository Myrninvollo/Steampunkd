/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.client.gui.handbook;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.sr2610.steampunked.api.SteampunkedAPI;
import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.client.gui.handbook.buttons.GuiButtonInvisible;
import com.sr2610.steampunked.common.lib.Reference;

public class GuiHandbook extends GuiScreen {

	public static GuiHandbook currentOpenHandbook = new GuiHandbook();

	public static final ResourceLocation texture = new ResourceLocation(
			Reference.ModID, "textures/gui/handbook/handbook.png");
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top;

	@Override
	public void initGui() {
		super.initGui();
		currentOpenHandbook = this;

		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;

		buttonList.clear();
		if (isIndex()) {
			final int x = 18;
			for (int i = 0; i < 12; i++) {
				final int y = 16 + i * 12;
				buttonList.add(new GuiButtonInvisible(i, left + x, top + y,
						110, 10, ""));
			}
			populateIndex();
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

		drawHeader();

		super.drawScreen(par1, par2, par3);
	}

	void drawHeader() {
		final boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(true);
		fontRendererObj.drawSplitString(StatCollector
				.translateToLocal("steampunked.gui.handbook.header"),
				left + 15, top + 20, 110, 0);
		fontRendererObj.setUnicodeFlag(unicode);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		final int i = par1GuiButton.id - 3;
		if (i < 0)
			return;

		final List<HandbookCatagory> categoryList = SteampunkedAPI
				.getAllCategories();
		final HandbookCatagory category = i >= categoryList.size() ? null
				: categoryList.get(i);

		if (category != null)
			mc.displayGuiScreen(new GuiHandbookIndex(category));
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	boolean isIndex() {
		return true;
	}

	void populateIndex() {
		final List<HandbookCatagory> categoryList = SteampunkedAPI
				.getAllCategories();
		for (int i = 3; i < 12; i++) {
			final int i_ = i - 3;
			final GuiButtonInvisible button = (GuiButtonInvisible) buttonList
					.get(i);
			final HandbookCatagory category = i_ >= categoryList.size() ? null
					: categoryList.get(i_);
			if (category != null)
				button.displayString = StatCollector.translateToLocal(category
						.getUnlocalizedName());
			else
				button.displayString = "";
		}
	}

	String getTitle() {
		return null;
	}

}
