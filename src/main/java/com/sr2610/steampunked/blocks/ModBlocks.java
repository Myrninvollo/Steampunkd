package com.sr2610.steampunked.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.lib.LibNames;
import com.sr2610.steampunked.lib.Reference;
import com.sr2610.steampunked.tileentities.TileEntityInjector;
import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModBlocks {

	public static Block Injector;
	public static Fluid steam;
	public static Block BlockFluidSteam;
	public static Block steamFurnace;
	public static Block steamFurnaceActive;
	public static Block steamBoiler;
	public static Block tinkerBench;

	public static Block oreCopper;
	public static Material materialSteam;

	public static void initBlocks() {
		System.out.println("[Steampunk'd] Initializing Blocks...");
		materialSteam = new MaterialLiquid(MapColor.ironColor);

		steam = new Steam();
		Injector = new BlockInjector(Material.iron).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.INJECTOR);

		BlockFluidSteam = new BlockSteamFluid().setBlockName(LibNames.STEAM);

		steamFurnace = new BlockSteamFurnace(false, Material.iron)
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.FURNACE);

		steamFurnaceActive = new BlockSteamFurnace(true, Material.iron)
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.FURNACE + "burning");

		steamBoiler = new BlockSteamBoiler(Material.iron).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.BOILER);

		tinkerBench = new BlockTinkerBench(Material.iron).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.BENCH);

		oreCopper = new BlockMod(Material.rock, "oreCopper",1,"pickaxe")
				.setHardness(3.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypePiston)
				.setBlockName(LibNames.ORECOPPER);

		registerBlocks();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(Injector, LibNames.INJECTOR);
		GameRegistry.registerBlock(BlockFluidSteam,
				"Steampunked.BlockFluidSteam");
		GameRegistry.registerBlock(steamFurnace, LibNames.FURNACE);
		GameRegistry.registerBlock(steamFurnaceActive, LibNames.FURNACE
				+ "burning");
		GameRegistry.registerBlock(steamBoiler, LibNames.BOILER);
		GameRegistry.registerBlock(tinkerBench, LibNames.BENCH);
		GameRegistry.registerBlock(oreCopper, LibNames.ORECOPPER);
		GameRegistry.registerTileEntity(TileEntityInjector.class,
				"tileEntityInjector");
		GameRegistry.registerTileEntity(TileEntitySteamFurnace.class,
				"tileEntitySteamFurnace");
		GameRegistry.registerTileEntity(TileEntitySteamBoiler.class,
				"tileEntitySteamBoiler");
		GameRegistry.registerTileEntity(TileEntityTinkerBench.class,
				"tileEntityTinkerBench");
		oreRegistration();
	}

	public static void oreRegistration() {
		OreDictionary.registerOre("oreCopper", new ItemStack(oreCopper));
	}
	
	
}
