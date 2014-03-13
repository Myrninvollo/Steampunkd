package com.sr2610.steampunked.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.lib.LibNames;
import com.sr2610.steampunked.tileentities.TileEntityInjector;
import com.sr2610.steampunked.tileentities.TileEntityPipe;
import com.sr2610.steampunked.tileentities.TileEntityPunchardMaker;
import com.sr2610.steampunked.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlocks {

	public static Block Injector;
	public static Fluid steam;
	public static Block BlockFluidSteam;
	public static Block steamFurnace;
	public static Block steamFurnaceActive;
	public static Block steamBoiler;
	public static Block tinkerBench;
	public static Block punchcardMaker;
	public static Block pipe;

	public static Block oreCopper;
	public static Block oreTin;
	public static Material materialSteam;

	public static void initBlocks() {
		Steampunked.logger.info("Initialising  Blocks");
		materialSteam = new MaterialLiquid(MapColor.ironColor);

		steam = new Steam();
		Injector = new BlockInjector(Material.iron).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.INJECTOR);

		BlockFluidSteam = new BlockSteamFluid(steam, materialSteam)
				.setBlockName(LibNames.STEAM);

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

		punchcardMaker = new BlockPunchcardMaker(Material.iron)
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.MAKER);

		oreCopper = new BlockMod(Material.rock, "oreCopper", 1, "pickaxe")
				.setHardness(3.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypePiston)
				.setBlockName(LibNames.ORECOPPER);

		oreTin = new BlockMod(Material.rock, "oreTin", 1, "pickaxe")
				.setHardness(3.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypePiston)
				.setBlockName(LibNames.ORETIN);

		pipe = new BlockPipe(Material.iron).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName("pipeFluid");

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
		GameRegistry.registerBlock(oreTin, LibNames.ORETIN);
		GameRegistry.registerBlock(punchcardMaker, LibNames.MAKER);
		GameRegistry.registerTileEntity(TileEntityInjector.class,
				"tileEntityInjector");
		GameRegistry.registerTileEntity(TileEntitySteamFurnace.class,
				"tileEntitySteamFurnace");
		GameRegistry.registerTileEntity(TileEntitySteamBoiler.class,
				"tileEntitySteamBoiler");
		GameRegistry.registerTileEntity(TileEntityTinkerBench.class,
				"tileEntityTinkerBench");
		GameRegistry.registerTileEntity(TileEntityPunchardMaker.class,
				"tileEntityPunchcardMaker");

		GameRegistry.registerBlock(pipe, "pipe");
		GameRegistry.registerTileEntity(TileEntityPipe.class, "tileEntityPipe");
		oreRegistration();
	}

	public static void oreRegistration() {
		OreDictionary.registerOre("oreCopper", new ItemStack(oreCopper));
		OreDictionary.registerOre("oreTin", new ItemStack(oreTin));

	}

}
