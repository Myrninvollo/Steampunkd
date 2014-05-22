/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.api.utils;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconRegistry {

	private static TMap<String, IIcon> icons = new THashMap<String, IIcon>();

	private IconRegistry() {

	}

	public static void addIcon(String iconName, String iconLocation,
			IIconRegister ir) {

		icons.put(iconName, ir.registerIcon(iconLocation));
	}

	public static void addIcon(String iconName, IIcon icon) {

		icons.put(iconName, icon);
	}

	public static IIcon getIcon(String iconName) {

		return icons.get(iconName);
	}

	public static IIcon getIcon(String iconName, int iconOffset) {

		return icons.get(iconName + iconOffset);
	}

}
