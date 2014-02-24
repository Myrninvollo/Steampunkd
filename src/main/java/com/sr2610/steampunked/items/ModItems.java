package com.sr2610.steampunked.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.lib.LibNames;
import com.sr2610.steampunked.lib.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

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
	public static Item template;
	public static Item chasis;

	public static Item saberWood;
	public static Item saberStone;
	public static Item saberIron;
	public static Item saberGold;
	public static Item saberDiamond;
	public static Item clockworkBow;

	public static Item copperIngot;
	public static Item tinIngot;

	public static Item basicCore;
	public static Item mediumCore;
	public static Item advancedCore;

	public static void initItems() {
		System.out.println("[Steampunk'd] Initializing Items...");
		jetpack = new ItemJetpack().setUnlocalizedName(LibNames.JETPACK)
				.setTextureName("steampunked:jetpack");
		drill = new ItemDrill().setUnlocalizedName(LibNames.DRILL);
		drillDiamond = new ItemDiamondDrill().setUnlocalizedName(LibNames.DRILL
				+ "_diamond");
		bucket = new ItemSteamBucket().setUnlocalizedName(LibNames.BUCKET);

		spanner = new ItemSpanner().setUnlocalizedName(LibNames.SPANNER);

		goggles = new ItemGoggles().setUnlocalizedName(LibNames.GOGGLES);

		boots = new ItemBoots().setUnlocalizedName(LibNames.BOOTS);

		namePlate = new ItemNamePlate().setUnlocalizedName(LibNames.NAMEPLATE);

		spawner = new ItemAutomotonSpawner()
		.setUnlocalizedName(LibNames.SPAWNER);

		AutomatonUpgrade = new ItemAutomatonUpgrade()
		.setUnlocalizedName(LibNames.UPGRADE);
		punchcard = new ItemPunchcard().setUnlocalizedName(LibNames.PUNCHCARD);
		chasis = new ItemChasis().setUnlocalizedName(LibNames.CHASIS);

		saberWood = new ItemSaber(Item.ToolMaterial.WOOD).setTexture("wood")
				.setUnlocalizedName(LibNames.SABERWOOD);
		saberStone = new ItemSaber(Item.ToolMaterial.STONE).setTexture("stone")
				.setUnlocalizedName(LibNames.SABERSTONE);
		saberIron = new ItemSaber(Item.ToolMaterial.IRON).setTexture("iron")
				.setUnlocalizedName(LibNames.SABERIRON);
		saberGold = new ItemSaber(Item.ToolMaterial.GOLD).setTexture("gold")
				.setUnlocalizedName(LibNames.SABERGOLD);
		saberDiamond = new ItemSaber(Item.ToolMaterial.EMERALD).setTexture(
				"diamond").setUnlocalizedName(LibNames.SABERDIAMOND);
		
		clockworkBow = new ItemClockworkBow().setUnlocalizedName(LibNames.BOW);

		copperIngot = new ItemMod("ingotCopper")
		.setUnlocalizedName(LibNames.COPPERINGOT);

		template = new ItemMod("template")
		.setUnlocalizedName(LibNames.TEMPLATE);
		tinIngot = new ItemMod("ingotTin")
		.setUnlocalizedName(LibNames.TININGOT);
		basicCore = new ItemCore("coreBasic").setUnlocalizedName(
				LibNames.COREBASIC).setMaxStackSize(1);
		mediumCore = new ItemCore("coreMedium").setUnlocalizedName(
				LibNames.COREMEDIUM).setMaxStackSize(1);
		advancedCore = new ItemCore("coreAdvanced").setUnlocalizedName(
				LibNames.COREADVANCED).setMaxStackSize(1);

		registerItems();
	}

	private static void registerItems() {
		GameRegistry.registerItem(boots, LibNames.BOOTS, Reference.ModID);
		GameRegistry.registerItem(jetpack, LibNames.JETPACK, Reference.ModID);
		GameRegistry.registerItem(drill, LibNames.DRILL, Reference.ModID);
		GameRegistry.registerItem(drillDiamond, LibNames.DRILL + "_diamond",
				Reference.ModID);
		GameRegistry.registerItem(bucket, LibNames.BUCKET, Reference.ModID);
		GameRegistry.registerItem(spanner, LibNames.SPANNER, Reference.ModID);
		GameRegistry.registerItem(goggles, LibNames.GOGGLES, Reference.ModID);
		GameRegistry.registerItem(spawner, LibNames.SPAWNER, Reference.ModID);
		GameRegistry.registerItem(namePlate, LibNames.NAMEPLATE,
				Reference.ModID);

		GameRegistry.registerItem(AutomatonUpgrade, LibNames.UPGRADE,
				Reference.ModID);
		GameRegistry.registerItem(punchcard, LibNames.PUNCHCARD,
				Reference.ModID);
		GameRegistry.registerItem(chasis, LibNames.CHASIS, Reference.ModID);

		GameRegistry.registerItem(saberWood, LibNames.SABERWOOD,
				Reference.ModID);
		GameRegistry.registerItem(saberStone, LibNames.SABERSTONE,
				Reference.ModID);
		GameRegistry.registerItem(saberIron, LibNames.SABERIRON,
				Reference.ModID);
		GameRegistry.registerItem(saberGold, LibNames.SABERGOLD,
				Reference.ModID);
		GameRegistry.registerItem(saberDiamond, LibNames.SABERDIAMOND,
				Reference.ModID);
		
		GameRegistry.registerItem(clockworkBow, LibNames.BOW,
				Reference.ModID);

		GameRegistry.registerItem(copperIngot, LibNames.COPPERINGOT,
				Reference.ModID);
		GameRegistry.registerItem(tinIngot, LibNames.TININGOT, Reference.ModID);

		GameRegistry.registerItem(template, LibNames.TEMPLATE, Reference.ModID);

		GameRegistry.registerItem(basicCore, LibNames.COREBASIC,
				Reference.ModID);
		GameRegistry.registerItem(mediumCore, LibNames.COREMEDIUM,
				Reference.ModID);
		GameRegistry.registerItem(advancedCore, LibNames.COREADVANCED,
				Reference.ModID);

		oreRegistration();

	}

	public static void oreRegistration() {
		OreDictionary.registerOre("ingotCopper", new ItemStack(copperIngot));
		OreDictionary.registerOre("ingotTin", new ItemStack(tinIngot));

	}
}
