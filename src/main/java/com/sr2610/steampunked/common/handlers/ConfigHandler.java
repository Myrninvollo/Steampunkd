/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class ConfigHandler {

	private static Configuration config;

	public static boolean giveHandbook = true;
	public static int injectorCapacity = 16000;
	public static int furnaceCapacity = 16000;
	public static int jetpackCapacity = 10000;
	public static int drillCapacity = 120;
	public static int bootsCapacity = 250;
	public static int advBootsCapacity = 1500;
	public static int boilerCapacity = 16000;
	public static int furnaceCookTime = 500;
	


	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		String desc;

		desc = "Set to false to disable giving the player a handbook on startup";
		giveHandbook = loadPropBool("handbook.shouldGive", desc, giveHandbook);
		

		config.save();
	}

	public static int loadPropInt(String propName, String desc, int defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, defaultValue);
		prop.comment = desc;
		return prop.getInt(defaultValue);
	}

	public static double loadPropDouble(String propName, String desc, double defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, defaultValue);
		prop.comment = desc;
		return prop.getDouble(defaultValue);
	}

	public static boolean loadPropBool(String propName, String desc, boolean defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, defaultValue);
		prop.comment = desc;
		return prop.getBoolean(defaultValue);

	}

}
