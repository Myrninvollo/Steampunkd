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

	public static boolean enableVersionCheck = false;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		String desc;

		desc = "Set to false to disable giving the player a handbook on startup";
		giveHandbook = loadPropBool("handbook.shouldGive", desc, giveHandbook);

		desc = "Set to false to disable version checking";
		enableVersionCheck = loadPropBool("version.shouldCheck", desc,
				enableVersionCheck);

		config.save();
	}

	public static int loadPropInt(String propName, String desc, int defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName,
				defaultValue);
		prop.comment = desc;
		return prop.getInt(defaultValue);
	}

	public static double loadPropDouble(String propName, String desc,
			double defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName,
				defaultValue);
		prop.comment = desc;
		return prop.getDouble(defaultValue);
	}

	public static boolean loadPropBool(String propName, String desc,
			boolean defaultValue) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName,
				defaultValue);
		prop.comment = desc;
		return prop.getBoolean(defaultValue);

	}

}
