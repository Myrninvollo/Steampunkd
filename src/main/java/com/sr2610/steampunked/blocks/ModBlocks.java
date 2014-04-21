/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import com.sr2610.steampunked.Steampunked;
import com.sr2610.steampunked.blocks.pipes.BlockPipe;
import com.sr2610.steampunked.blocks.slate.BlockSlate;
import com.sr2610.steampunked.blocks.slate.BlockSlateBrickSlab;
import com.sr2610.steampunked.blocks.slate.BlockSlateBrickStairs;
import com.sr2610.steampunked.blocks.slate.BlockSlateSlab;
import com.sr2610.steampunked.blocks.slate.BlockSlateStairs;
import com.sr2610.steampunked.items.ItemSlateBrickSlab;
import com.sr2610.steampunked.items.ItemSlateSlab;
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
	public static Block pipeValve;

	public static Block oreCopper;
	public static Block oreTin;
	public static Material materialSteam;

	/* Slate */

	public static Block slate;
	public static Block slateBrick;
	public static Block slateSlab;
	public static Block slateBrickSlab;
	public static Block slateSlabDouble;
	public static Block slateBrickSlabDouble;
	public static Block slateStair;
	public static Block slateBrickStair;

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
				.setStepSound(Block.soundTypeStone)
				.setBlockName(LibNames.ORECOPPER);

		oreTin = new BlockMod(Material.rock, "oreTin", 1, "pickaxe")
				.setHardness(3.0F).setResistance(5.0F)
				.setStepSound(Block.soundTypeStone)
				.setBlockName(LibNames.ORETIN);

		pipe = new BlockPipe(Material.piston).setHardness(5.0F)
				.setResistance(10.0F).setStepSound(Block.soundTypeMetal)
				.setBlockName("pipeFluid");

		/*
		 * pipeValve = new BlockValvePipe(Material.piston).setHardness(5.0F)
		 * .setResistance(10.0F).setStepSound(Block.soundTypeMetal)
		 * .setBlockName("pipeValveFluid");
		 */

		initSlate();

		registerBlocks();
	}

	private static void initSlate() {
		slate = new BlockSlate("slate");

		slateBrick = new BlockSlate("slateBrick");

		slateSlab = new BlockSlateSlab(false);
		slateSlabDouble = new BlockSlateSlab(true);
		slateBrickSlab = new BlockSlateBrickSlab(false);
		slateBrickSlabDouble = new BlockSlateBrickSlab(true);
		slateStair = new BlockSlateStairs(slate);
		slateBrickStair = new BlockSlateBrickStairs(slateBrick);

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

		/*
		 * GameRegistry.registerBlock(pipeValve, "pipeValve");
		 * GameRegistry.registerTileEntity(TileEntityValvePipe.class,
		 * "tileEntityValvePipe");
		 */

		GameRegistry.registerBlock(slate, "slate");
		GameRegistry.registerBlock(slateBrick, "slateBrick");
		GameRegistry.registerBlock(slateSlab, ItemSlateSlab.class, "slateSlab");
		GameRegistry.registerBlock(slateSlabDouble, ItemSlateSlab.class,
				"slateSlabFull");
		GameRegistry.registerBlock(slateBrickSlab, ItemSlateBrickSlab.class,
				"slateBrickSlab");
		GameRegistry.registerBlock(slateBrickSlabDouble,
				ItemSlateBrickSlab.class, "slateBrickSlabFull");
		GameRegistry.registerBlock(slateStair, "slateStair");
		GameRegistry.registerBlock(slateBrickStair, "slateBrickStair");
		oreRegistration();
	}

	public static void oreRegistration() {
		OreDictionary.registerOre("oreCopper", new ItemStack(oreCopper));
		OreDictionary.registerOre("oreTin", new ItemStack(oreTin));

	}

}
