/**
 * This class was created by <SR2610>. It's distributed as
 * part of the Steampunk'd Mod. Get the Source Code in github:
 * https://github.com/SR2610/steampunkd
 * 
 * Steampunk'd is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [12 Mar 2014, 18:22:05 (GMT)]
 */
package com.sr2610.steampunked.world;

import java.util.Random;

import com.sr2610.steampunked.blocks.ModBlocks;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class OreGeneration implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.dimensionId){
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

	private void generateEnd(World world, Random random, int i, int j) {}

	private void generateSurface(World world, Random random, int i, int j) {
		for(int k = 0; k <10; k++) {
			int blockXCoord = i + random.nextInt(16);
			int blockYCoord = random.nextInt(64);
			int blockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(ModBlocks.oreCopper, 8)).generate(world, random, blockXCoord, blockYCoord, blockZCoord);

		}
		
		for(int k = 0; k <6; k++) {
			int blockXCoord = i + random.nextInt(16);
			int blockYCoord = random.nextInt(64);
			int blockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(ModBlocks.oreTin, 6)).generate(world, random, blockXCoord, blockYCoord, blockZCoord);

		}
		
		
	}

	private void generateNether(World world, Random random, int i, int j) {}
}