/*******************************************************************************
 * This class was created by <SR2610>. It's distributed as part of the
 * Steampunk'd Mod. Get the Source Code in Github:
 * https://github.com/SR2610/Steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 ******************************************************************************/
package com.sr2610.steampunked.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.sr2610.steampunked.common.blocks.ModBlocks;

import cpw.mods.fml.common.IWorldGenerator;

public class OreGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void generateEnd(World world, Random random, int i, int j) {
	}

	private void generateSurface(World world, Random random, int i, int j) {
		for (int k = 0; k < 10; k++) {
			final int blockXCoord = i + random.nextInt(16);
			final int blockYCoord = random.nextInt(64);
			final int blockZCoord = j + random.nextInt(16);
			new WorldGenMinable(ModBlocks.oreCopper, 8).generate(world, random,
					blockXCoord, blockYCoord, blockZCoord);

		}

		for (int k = 0; k < 6; k++) {
			final int blockXCoord = i + random.nextInt(16);
			final int blockYCoord = random.nextInt(64);
			final int blockZCoord = j + random.nextInt(16);
			new WorldGenMinable(ModBlocks.oreTin, 6).generate(world, random,
					blockXCoord, blockYCoord, blockZCoord);

		}

		for (int k = 0; k < 4; k++) {
			final int blockXCoord = i + random.nextInt(16);
			final int blockYCoord = random.nextInt(64);
			final int blockZCoord = j + random.nextInt(16);
			if (world.getBiomeGenForCoords(blockXCoord, blockZCoord) == BiomeGenBase.extremeHills)
				new WorldGenMinable(ModBlocks.slate, 20).generate(world,
						random, blockXCoord, blockYCoord, blockZCoord);

		}

	}

	private void generateNether(World world, Random random, int i, int j) {
	}
}
