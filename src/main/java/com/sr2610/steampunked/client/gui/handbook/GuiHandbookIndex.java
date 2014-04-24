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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.sr2610.steampunked.api.handbook.HandbookCatagory;
import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.client.gui.handbook.buttons.GuiButtonBack;
import com.sr2610.steampunked.client.gui.handbook.buttons.GuiButtonInvisible;
import com.sr2610.steampunked.client.gui.handbook.buttons.GuiButtonPage;

public class GuiHandbookIndex extends GuiHandbook {

	HandbookCatagory category;
	String title;
	int page = 0;

	GuiButton leftButton, rightButton;

	List<HandbookEntry> entriesToDisplay = new ArrayList();

	public GuiHandbookIndex(HandbookCatagory category) {
		this.category = category;
		title = StatCollector.translateToLocal(category.getUnlocalizedName());
	}

	@Override
	void drawHeader() {
		// NO-OP
	}

	@Override
	String getTitle() {
		return title;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonBack(12, (left + (guiWidth / 2)) - 8, top
				+ guiHeight + 2));
		buttonList.add(leftButton = new GuiButtonPage(13, left,
				(top + guiHeight) - 10, false));
		buttonList.add(rightButton = new GuiButtonPage(14,
				(left + guiWidth) - 18, (top + guiHeight) - 10, true));

		entriesToDisplay.clear();
		entriesToDisplay.addAll(category.entries);
		// Collections.sort(entriesToDisplay);

		updatePageButtons();
		populateIndex();
	}

	@Override
	void populateIndex() {
		for (int i = page * 12; i < ((page + 1) * 12); i++) {
			final GuiButtonInvisible button = (GuiButtonInvisible) buttonList
					.get(i - (page * 12));
			final HandbookEntry entry = i >= entriesToDisplay.size() ? null
					: entriesToDisplay.get(i);
			if (entry != null)
				button.displayString = (entry.isPriority() ? EnumChatFormatting.ITALIC
						: "")
						+ StatCollector.translateToLocal(entry
								.getUnlocalizedName());
			else
				button.displayString = "";
		}
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
		case 12:
			mc.displayGuiScreen(new GuiHandbook());
			break;
		case 13:
			page--;
			updatePageButtons();
			populateIndex();

			break;
		case 14:
			page++;
			updatePageButtons();
			populateIndex();

			break;
		default:
			final int index = par1GuiButton.id + (page * 12);
			if (index >= entriesToDisplay.size())
				return;

			final HandbookEntry entry = entriesToDisplay.get(index);
			mc.displayGuiScreen(new GuiHandbookEntry(entry, this));
		}
	}

	public void updatePageButtons() {
		leftButton.enabled = page != 0;
		rightButton.enabled = page < ((entriesToDisplay.size() - 1) / 12);
	}
}
