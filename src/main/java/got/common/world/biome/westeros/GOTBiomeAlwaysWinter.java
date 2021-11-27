package got.common.world.biome.westeros;

import java.util.Random;

import got.GOT;
import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTBiomeAlwaysWinter extends GOTBiome {
	public GOTBiomeAlwaysWinter(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		topBlock = Blocks.ice;
		fillerBlock = Blocks.ice;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableGOTAmbientList.clear();
		SpawnListContainer[] container0 = new SpawnListContainer[1];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		Block block = Blocks.ice;
		Block block2 = Blocks.snow;
		for (int l2 = 0; l2 < 100; ++l2) {
			int k3;
			int j1;
			int i3 = i + random.nextInt(16) + 8;
			if (world.getBlock(i3, (j1 = world.getHeightValue(i3, k3 = k + random.nextInt(16) + 8)) - 1, k3) != block) {
				continue;
			}
			int height = j1 + random.nextInt(4);
			for (int j2 = j1; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
				world.setBlock(i3, j2, k3, block, 0, 3);
			}
		}
		for (int l2 = 0; l2 < 100; ++l2) {
			int k3;
			int j1;
			int i3 = i + random.nextInt(16) + 8;
			if (world.getBlock(i3, (j1 = world.getHeightValue(i3, k3 = k + random.nextInt(16) + 8)) - 1, k3) != block2) {
				continue;
			}
			int height = j1 + random.nextInt(4);
			for (int j2 = j1; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
				world.setBlock(i3, j2, k3, block2, 0, 3);
			}
		}
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		int snowHeight = 100 - rockDepth;
		int stoneHeight = snowHeight - 30;
		for (int j = ySize - 1; j >= stoneHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (j >= snowHeight && block == topBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
				continue;
			}
			if (block != topBlock && block != fillerBlock) {
				continue;
			}
			blocks[index] = Blocks.snow;
			meta[index] = 0;
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_ALWAYS_WINTER;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("alwaysWinter");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ICE;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}