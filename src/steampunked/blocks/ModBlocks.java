package steampunked.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import steampunked.lib.LibIds;
import steampunked.lib.LibNames;
import steampunked.tileentities.TileEntityInjector;
import steampunked.tileentities.TileEntitySteamBoiler;
import steampunked.tileentities.TileEntitySteamFurnace;
import steampunked.tileentities.TileEntityTinkerBench;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModBlocks {

	public static Block Injector;
	public static Fluid steam;
	public static Block BlockFluidSteam;
	public static Block steamFurnace;
	public static Block steamBoiler;
	public static Block tinkerBench;
	public static Material materialSteam;

	public static void initBlocks() {
		System.out.println("[Steampunk'd] Initializing Blocks...");
		materialSteam = new MaterialLiquid(MapColor.ironColor);

		steam = new Steam();
		Injector = new BlockInjector(LibIds.idInjector, Material.iron)
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName("Injector");

		BlockFluidSteam = new BlockSteamFluid(LibIds.idSteam)
				.setUnlocalizedName("Steam");
		steamFurnace = new BlockSteamFurnace(LibIds.idFurnace, Material.iron)
				.setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName("steamFurnace");
		
		steamBoiler = new BlockSteamBoiler(LibIds.idBoiler, Material.iron).setHardness(5.0F).setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName("steamBoiler");
		
		tinkerBench = new BlockTinkerBench(LibIds.idBench, Material.iron).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName("tinkerBench");

		registerBlocks();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(Injector, "Steampunked.Injector");
		GameRegistry.registerBlock(BlockFluidSteam,
				"Steampunked.BlockFluidSteam");
		GameRegistry.registerBlock(steamFurnace, "BlockSteamFurnace");
		GameRegistry.registerBlock(steamBoiler, "BlockSteamBoiler");
		GameRegistry.registerBlock(tinkerBench,"BlockTinkerBench");
		GameRegistry.registerTileEntity(TileEntityInjector.class,
				"tileEntityInjector");
		GameRegistry.registerTileEntity(TileEntitySteamFurnace.class,
				"tileEntitySteamFurnace");
		GameRegistry.registerTileEntity(TileEntitySteamBoiler.class,
				"tileEntitySteamBoiler");
		GameRegistry.registerTileEntity(TileEntityTinkerBench.class,
				"tileEntityTinkerBench");

		FluidRegistry.registerFluid(steam);

		nameBlocks();

	}

	private static void nameBlocks() {
		LanguageRegistry.addName(Injector, LibNames.INJECTOR);
		LanguageRegistry.addName(BlockFluidSteam, "Steam");
		LanguageRegistry.addName(steamFurnace, "Steam Heated Furnace");
		LanguageRegistry.addName(steamBoiler, "Steam Boiler");
		LanguageRegistry.addName(tinkerBench, "Automaton Tinkering Bench");



	}

}
