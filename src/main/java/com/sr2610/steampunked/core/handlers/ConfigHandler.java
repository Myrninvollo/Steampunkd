package com.sr2610.steampunked.core.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.sr2610.steampunked.lib.LibOptions;

public final class ConfigHandler {

	private static Configuration config;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();

		config.get(
				Configuration.CATEGORY_GENERAL,
				"Capacity for the Steam injector in mB (1000mB = 1 Buckets Worth)",
				LibOptions.injectorCapacity).getInt();
		config.get(
				Configuration.CATEGORY_GENERAL,
				"Capacity for the Mining Drill in mB (1000mB = 1 Buckets Worth)",
				LibOptions.drillCapacity).getInt();
		config.get(Configuration.CATEGORY_GENERAL,
				"Capacity for the Jetpack in mB (1000mB = 1 Buckets Worth)",
				LibOptions.jetpackCapacity).getInt();
		config.get(
				Configuration.CATEGORY_GENERAL,
				"Capacity for the Steam Furnace in mB (1000mB = 1 Buckets Worth)",
				LibOptions.furnaceCapacity).getInt();
		config.get(
				Configuration.CATEGORY_GENERAL,
				"Volume of Steam Taken for the Steam Furnace to Smelt 1 Item in mB (1000mb = 1 Buckets Worth)  !Warning, Anything Below 500 will mean that it can become an infinite steam generator!",
				LibOptions.furnaceCookTime).getInt();

		config.get(Configuration.CATEGORY_GENERAL, "Mech Boots Capacity",
				LibOptions.advBootsCapacity).getInt();
		config.get(Configuration.CATEGORY_GENERAL, "Piston Boots Capacity",
				LibOptions.bootsCapacity).getInt();

		config.save();
	}

}