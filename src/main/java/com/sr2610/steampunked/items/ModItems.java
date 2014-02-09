package com.sr2610.steampunked.items;

import net.minecraft.item.Item;

import com.sr2610.steampunked.lib.LibNames;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModItems {
	public static Item jetpack;
	public static Item drill;
	public static Item drillDiamond;
	public static Item bucket;
	public static Item spanner;
	public static Item goggles;
	public static Item boots;
	public static Item namePlate;
	public static Item spawner;

	public static Item AutomatonUpgrade;
	public static Item punchcard;
	public static Item chasis;

	public static Item saberWood;
	public static Item saberStone;
	public static Item saberIron;
	public static Item saberGold;
	public static Item saberDiamond;

	public static void initItems() {
		System.out.println("[Steampunk'd] Initializing Items...");
		jetpack = new ItemJetpack().setUnlocalizedName(LibNames.JETPACK).setTextureName("steampunked:jetpack");
		drill = new ItemDrill()
				.setUnlocalizedName(LibNames.DRILL);
		drillDiamond = new ItemDiamondDrill()
				.setUnlocalizedName(LibNames.DRILL + "_diamond");
		bucket = new ItemSteamBucket()
				.setUnlocalizedName(LibNames.BUCKET);
		
		spanner = new ItemSpanner()
				.setUnlocalizedName(LibNames.SPANNER);

		goggles = new ItemGoggles().setUnlocalizedName(LibNames.GOGGLES);

		boots = new ItemBoots().setUnlocalizedName(LibNames.BOOTS);

		namePlate = new ItemNamePlate()
				.setUnlocalizedName(LibNames.NAMEPLATE);

		spawner = new ItemAutomotonSpawner()
				.setUnlocalizedName(LibNames.SPAWNER);

		AutomatonUpgrade = new ItemAutomatonUpgrade()
				.setUnlocalizedName(LibNames.UPGRADE);
		punchcard = new ItemPunchcard()
				.setUnlocalizedName(LibNames.PUNCHCARD);
		chasis = new ItemChasis().setUnlocalizedName(LibNames.CHASIS);

		saberWood = new ItemSaber(Item.ToolMaterial.WOOD)
				.setTexture("wood").setUnlocalizedName(LibNames.SABERWOOD);
		saberStone = new ItemSaber(Item.ToolMaterial.STONE)
				.setTexture("stone").setUnlocalizedName(LibNames.SABERSTONE);
		saberIron = new ItemSaber(Item.ToolMaterial.IRON)
				.setTexture("iron").setUnlocalizedName(LibNames.SABERIRON);
		saberGold = new ItemSaber(Item.ToolMaterial.GOLD)
				.setTexture("gold").setUnlocalizedName(LibNames.SABERGOLD);
		saberDiamond = new ItemSaber(
				Item.ToolMaterial.EMERALD).setTexture("diamond")
				.setUnlocalizedName(LibNames.SABERDIAMOND);

		registerItems();
		nameItems();
	}

	private static void registerItems() {
		GameRegistry.registerItem(boots, LibNames.BOOTS);
		GameRegistry.registerItem(jetpack, LibNames.JETPACK);
		GameRegistry.registerItem(drill, LibNames.DRILL);
		GameRegistry.registerItem(drillDiamond, LibNames.DRILL + "_diamond");
		GameRegistry.registerItem(bucket, LibNames.BUCKET);
		GameRegistry.registerItem(spanner, LibNames.SPANNER);
		GameRegistry.registerItem(goggles, LibNames.GOGGLES);
		GameRegistry.registerItem(spawner, LibNames.SPAWNER);
		GameRegistry.registerItem(namePlate, LibNames.NAMEPLATE);

		GameRegistry.registerItem(AutomatonUpgrade, LibNames.UPGRADE);
		GameRegistry.registerItem(punchcard, LibNames.PUNCHCARD);
		GameRegistry.registerItem(chasis, LibNames.CHASIS);

		GameRegistry.registerItem(saberWood, LibNames.SABERWOOD);
		GameRegistry.registerItem(saberStone, LibNames.SABERSTONE);
		GameRegistry.registerItem(saberIron, LibNames.SABERIRON);
		GameRegistry.registerItem(saberGold, LibNames.SABERGOLD);
		GameRegistry.registerItem(saberDiamond, LibNames.SABERDIAMOND);

	}

	public static void nameItems() {
		LanguageRegistry.addName(jetpack, LibNames.JETPACK);
		LanguageRegistry.addName(drill, LibNames.DRILL);
		LanguageRegistry.addName(drillDiamond, LibNames.DRILL);
		LanguageRegistry.addName(bucket, LibNames.BUCKET);
		LanguageRegistry.addName(spanner, LibNames.SPANNER);
		LanguageRegistry.addName(goggles, LibNames.GOGGLES);
		LanguageRegistry.addName(boots, LibNames.BOOTS);
		LanguageRegistry.addName(namePlate, LibNames.NAMEPLATE);
		LanguageRegistry.addName(spawner, LibNames.SPAWNER);

		LanguageRegistry.addName(chasis, LibNames.CHASIS);

		LanguageRegistry.addName(AutomatonUpgrade, LibNames.UPGRADE);
		LanguageRegistry.addName(punchcard, LibNames.PUNCHCARD);

		LanguageRegistry.addName(saberWood, LibNames.SABERWOOD);
		LanguageRegistry.addName(saberStone, LibNames.SABERSTONE);
		LanguageRegistry.addName(saberIron, LibNames.SABERIRON);
		LanguageRegistry.addName(saberGold, LibNames.SABERGOLD);
		LanguageRegistry.addName(saberDiamond, LibNames.SABERDIAMOND);

	}
}
