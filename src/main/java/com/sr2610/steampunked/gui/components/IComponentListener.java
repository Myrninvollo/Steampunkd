/*******************************************************************************
 * * This class was created by <SR2610>. It's distributed as
 *  * part of the Steampunk'd Mod. Get the Source Code in github:
 *  * https://github.com/SR2610/steampunkd
 *  * 
 *  * Steampunk'd is Open Source and distributed under a
 *  * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 *  * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.gui.components;

public interface IComponentListener {
	void componentMouseDown(BaseComponent component, int offsetX, int offsetY,
			int button);

	void componentMouseDrag(BaseComponent component, int offsetX, int offsetY,
			int button, long time);

	void componentMouseMove(BaseComponent component, int offsetX, int offsetY);

	void componentMouseUp(BaseComponent component, int offsetX, int offsetY,
			int button);

	void componentKeyTyped(BaseComponent component, char par1, int par2);
}
