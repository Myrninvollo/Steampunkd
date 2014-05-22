/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.common.blocks.machines.BlockInjector;
import com.sr2610.steampunked.common.blocks.machines.BlockPunchcardMaker;
import com.sr2610.steampunked.common.blocks.machines.BlockSteamBlaster;
import com.sr2610.steampunked.common.blocks.machines.BlockSteamBoiler;
import com.sr2610.steampunked.common.blocks.machines.BlockSteamFurnace;
import com.sr2610.steampunked.common.blocks.machines.BlockTinkerBench;
import com.sr2610.steampunked.common.blocks.pipes.BlockPipe;
import com.sr2610.steampunked.common.blocks.slate.BlockSlate;
import com.sr2610.steampunked.common.blocks.slate.BlockSlateBrickSlab;
import com.sr2610.steampunked.common.blocks.slate.BlockSlateBrickStairs;
import com.sr2610.steampunked.common.blocks.slate.BlockSlateSlab;
import com.sr2610.steampunked.common.blocks.slate.BlockSlateStairs;
import com.sr2610.steampunked.common.items.ItemGenericBlock;
import com.sr2610.steampunked.common.items.ItemSlateBrickSlab;
import com.sr2610.steampunked.common.items.ItemSlateSlab;
import com.sr2610.steampunked.common.lib.LibNames;
import com.sr2610.steampunked.common.tileentities.TileEntityInjector;
import com.sr2610.steampunked.common.tileentities.TileEntityPipe;
import com.sr2610.steampunked.common.tileentities.TileEntityPunchardMaker;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBlaster;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamBoiler;
import com.sr2610.steampunked.common.tileentities.TileEntitySteamFurnace;
import com.sr2610.steampunked.common.tileentities.TileEntityTinkerBench;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlocks {
	/* FLUIDS */
	public static Fluid steam;
	public static Block blockSteam;
	public static Material materialSteam;

	/* MACHINES */
	public static Block machineCase;
	public static Block steamBoiler;
	public static Block injector;
	public static Block steamFurnace;
	public static Block steamFurnaceActive;
	public static Block tinkerBench;
	public static Block punchcardMaker;
	public static Block pipe;
	public static Block steamBlaster;

	/* ORES */

	/* Slate */
	public static Block slate;
	public static Block slateBrick;
	public static Block slateSlab;
	public static Block slateBrickSlab;
	public static Block slateSlabDouble;
	public static Block slateBrickSlabDouble;
	public static Block slateStair;
	public static Block slateBrickStair;

	public static Block genericBlock;

	public static void initBlocks() {
		Steampunked.logger.info("Initialising  Blocks");
		/* FLUIDS */
		materialSteam = new MaterialLiquid(MapColor.ironColor);
		steam = new Steam();
		blockSteam = new BlockSteamFluid(steam, materialSteam);

		/* MACHINES */
		machineCase = new BlockMod(Material.iron, "machine", 2, "pickaxe")
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal)
				.setBlockName(LibNames.MACHINE);

		steamBoiler = new BlockSteamBoiler(Material.iron);

		injector = new BlockInjector(Material.iron);

		steamFurnace = new BlockSteamFurnace(false, Material.iron)
				.setBlockName(LibNames.FURNACE);

		steamFurnaceActive = new BlockSteamFurnace(true, Material.iron)
				.setBlockName(LibNames.FURNACE + "burning");

		tinkerBench = new BlockTinkerBench(Material.iron);

		punchcardMaker = new BlockPunchcardMaker(Material.iron);

		pipe = new BlockPipe(Material.piston);

		steamBlaster = new BlockSteamBlaster();

		genericBlock = new BlockGeneric();

		initSlate();
		registerBlocks();
		bindTileEntitys();
		oreRegistration();
	}

	private static void initSlate() {
		slate = new BlockSlate("slate").setBlockName(LibNames.SLATE);

		slateBrick = new BlockSlate("slateBrick")
				.setBlockName(LibNames.SLATEBRICK);

		slateSlab = new BlockSlateSlab(false);
		slateSlabDouble = new BlockSlateSlab(true);
		slateBrickSlab = new BlockSlateBrickSlab(false);
		slateBrickSlabDouble = new BlockSlateBrickSlab(true);
		slateStair = new BlockSlateStairs(slate);
		slateBrickStair = new BlockSlateBrickStairs(slateBrick);

	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(blockSteam, LibNames.STEAM);
		GameRegistry.registerBlock(steamBoiler, LibNames.BOILER);

		GameRegistry.registerBlock(injector, LibNames.INJECTOR);

		GameRegistry.registerBlock(steamFurnace, LibNames.FURNACE);
		GameRegistry.registerBlock(steamFurnaceActive, LibNames.FURNACE
				+ "burning");
		GameRegistry.registerBlock(tinkerBench, LibNames.BENCH);
		GameRegistry.registerBlock(punchcardMaker, LibNames.MAKER);

		GameRegistry.registerBlock(pipe, LibNames.PIPE);

		GameRegistry.registerBlock(steamBlaster, LibNames.BLASTER);

		GameRegistry.registerBlock(machineCase, LibNames.MACHINE);

		GameRegistry.registerBlock(slate, LibNames.SLATE);
		GameRegistry.registerBlock(slateBrick, "steampunked.slateBrick");
		GameRegistry.registerBlock(slateSlab, ItemSlateSlab.class,
				"steampunked.slateSlab");
		GameRegistry.registerBlock(slateSlabDouble, ItemSlateSlab.class,
				"steampunked.slateSlabFull");
		GameRegistry.registerBlock(slateBrickSlab, ItemSlateBrickSlab.class,
				"steampunked.slateBrickSlab");
		GameRegistry.registerBlock(slateBrickSlabDouble,
				ItemSlateBrickSlab.class, "steampunked.slateBrickSlabFull");
		GameRegistry.registerBlock(slateStair, "steampunked.slateStair");
		GameRegistry.registerBlock(slateBrickStair,
				"steampunked.slateBrickStair");

		GameRegistry.registerBlock(genericBlock, ItemGenericBlock.class,
				LibNames.GENERICBLOCK);

	}

	private static void bindTileEntitys() {

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
		GameRegistry.registerTileEntity(TileEntityPipe.class, "tileEntityPipe");
		GameRegistry.registerTileEntity(TileEntitySteamBlaster.class,
				"tileEntitySteamBlaster");
	}

	public static void oreRegistration() {
		OreDictionary.registerOre("oreCopper",
				new ItemStack(genericBlock, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(genericBlock, 1, 1));

	}

}
