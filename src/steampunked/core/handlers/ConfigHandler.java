package steampunked.core.handlers;

import java.io.File;

import net.minecraftforge.common.Configuration;
import steampunked.lib.LibIds;
import steampunked.lib.LibNames;
import steampunked.lib.LibOptions;

public final class ConfigHandler {

	private static Configuration config;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();

		LibIds.idJetpack = loadItem(LibNames.JETPACK, LibIds.idJetpack);
		LibIds.idDrill = loadItem(LibNames.DRILL, LibIds.idDrill);
		LibIds.idBucket = loadItem(LibNames.BUCKET, LibIds.idBucket);
		LibIds.idInjector = loadBlock(LibNames.INJECTOR, LibIds.idInjector);
		LibIds.idSpanner = loadItem(LibNames.SPANNER, LibIds.idSpanner);
		LibIds.idFurnace = loadBlock(LibNames.FURNACE, LibIds.idFurnace);
		LibIds.idBoiler = loadBlock(LibNames.BOILER,LibIds.idBoiler);
		LibIds.idGoggles = loadItem(LibNames.GOGGLES, LibIds.idGoggles);
		LibIds.idBoots = loadItem(LibNames.BOOTS, LibIds.idBoots);
		LibIds.idDiamondDrill = loadItem(LibNames.DRILL + " (Diamond)", LibIds.idDiamondDrill);
		

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
		config.get(Configuration.CATEGORY_GENERAL,
				"Capacity for the Steam Furnace in mB (1000mB = 1 Buckets Worth)",
				LibOptions.furnaceCapacity).getInt();
		config.get(Configuration.CATEGORY_GENERAL,
				"Volume of Steam Taken for the Steam Furnace to Smelt 1 Item in mB (1000mb = 1 Buckets Worth)  !Warning, Anything Below 500 will mean that it can become an infinite steam generator!",
				LibOptions.furnaceCookTime).getInt();

		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem("id_item." + label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock("id_item." + label, defaultID).getInt(defaultID);
	}

}