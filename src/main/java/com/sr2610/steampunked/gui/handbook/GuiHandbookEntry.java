/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.gui.handbook;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

import com.sr2610.steampunked.api.handbook.HandbookEntry;
import com.sr2610.steampunked.api.handbook.HandbookPage;
import com.sr2610.steampunked.api.handbook.IGuiHandbookEntry;
import com.sr2610.steampunked.gui.handbook.buttons.GuiButtonBackWithShift;
import com.sr2610.steampunked.gui.handbook.buttons.GuiButtonPage;

public class GuiHandbookEntry extends GuiHandbook implements IGuiHandbookEntry {

	public int page = 0;
	HandbookEntry entry;
	GuiScreen parent;
	String title;

	GuiButton leftButton, rightButton;

	public GuiHandbookEntry(HandbookEntry entry, GuiScreen parent) {
		this.entry = entry;
		this.parent = parent;

		title = StatCollector.translateToLocal(entry.getUnlocalizedName());
	}

	@Override
	public void initGui() {
		super.initGui();

		buttonList.add(new GuiButtonBackWithShift(0, left + guiWidth / 2 - 8, top + guiHeight + 2));
		buttonList.add(leftButton = new GuiButtonPage(1, left, top + guiHeight - 10, false));
		buttonList.add(rightButton = new GuiButtonPage(2, left + guiWidth - 18, top + guiHeight - 10, true));

		updatePageButtons();
	}

	@Override
	public HandbookEntry getEntry() {
		return entry;
	}

	@Override
	public int getPageOn() {
		return page;
	}

	@Override
	boolean isIndex() {
		return false;
	}

	@Override
	void drawHeader() {
		// NO-OP
	}

	@Override
	String getTitle() {
		return String.format("%s (%s/%s)", title, page + 1, entry.pages.size());
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch(par1GuiButton.id) {
		case 0 :
			mc.displayGuiScreen(GuiScreen.isShiftKeyDown() ? new GuiHandbook() : parent);
			break;
		case 1 :
			page--;
			break;
		case 2 :
			page++;
			break;
		}
		updatePageButtons();
	}

	public void updatePageButtons() {
		leftButton.enabled = page != 0;
		rightButton.enabled = page + 1 < entry.pages.size();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		HandbookPage page = entry.pages.get(this.page);
		page.renderScreen(this, par1, par2);
	}

	@Override
	public void updateScreen() {
		HandbookPage page = entry.pages.get(this.page);
		page.updateScreen();
	}

	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getTop() {
		return top;
	}

	@Override
	public int getWidth() {
		return guiWidth;
	}

	@Override
	public int getHeight() {
		return guiHeight;
	}

	@Override
	public float getZLevel() {
		return zLevel;
	}

}