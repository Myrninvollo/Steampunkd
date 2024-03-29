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

public class FontHelper {
	public static boolean isFormatColor(char par0) {
		return par0 >= 48 && par0 <= 57 || par0 >= 97 && par0 <= 102
				|| par0 >= 65 && par0 <= 70;
	}

	public static boolean isFormatSpecial(char par0) {
		return par0 >= 107 && par0 <= 111 || par0 >= 75 && par0 <= 79
				|| par0 == 114 || par0 == 82;
	}

	public static String getFormatFromString(String par0Str) {
		String s1 = "";
		int i = -1;
		final int j = par0Str.length();

		while ((i = par0Str.indexOf(167, i + 1)) != -1)
			if (i < j - 1) {
				final char c0 = par0Str.charAt(i + 1);

				if (isFormatColor(c0))
					s1 = "\u00a7" + c0;
				else if (isFormatSpecial(c0))
					s1 = s1 + "\u00a7" + c0;
			}

		return s1;
	}

}
