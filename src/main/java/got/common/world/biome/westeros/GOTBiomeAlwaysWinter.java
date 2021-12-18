package got.common.world.biome.westeros;

import java.util.Random;

import got.GOT;
import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GOTBiomeAlwaysWinter extends GOTBiome {
	public WorldGenerator boulderGenSmall;
	public WorldGenerator boulderGenLarge;
	public WorldGenerator clayBoulderGenSmall;
	public WorldGenerator clayBoulderGenLarge;

	public GOTBiomeAlwaysWinter(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.ice;
		boulderGenSmall = new GOTWorldGenBoulder(Blocks.snow, 0, 1, 4);
		boulderGenLarge = new GOTWorldGenBoulder(Blocks.snow, 0, 5, 8).setHeightCheck(6);
		clayBoulderGenSmall = new GOTWorldGenBoulder(Blocks.packed_ice, 0, 1, 4);
		clayBoulderGenLarge = new GOTWorldGenBoulder(Blocks.packed_ice, 0, 5, 10).setHeightCheck(6);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableGOTAmbientList.clear();
		SpawnListContainer[] c0 = new SpawnListContainer[1];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k2;
		int i2;
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 20; ++l) {
			i2 = i + random.nextInt(16) + 8;
			k2 = k + random.nextInt(16) + 8;
			if (random.nextInt(5) == 0) {
				clayBoulderGenSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
				continue;
			}
			boulderGenSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
		}
		for (l = 0; l < 20; ++l) {
			i2 = i + random.nextInt(16) + 8;
			k2 = k + random.nextInt(16) + 8;
			if (random.nextInt(5) == 0) {
				clayBoulderGenLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
				continue;
			}
			boulderGenLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
		}
		for (l = 0; l < 10; ++l) {
			Block block = Blocks.snow;
			if (random.nextInt(5) == 0) {
				block = Blocks.packed_ice;
			}
			for (int l2 = 0; l2 < 10; ++l2) {
				int j1;
				int k3;
				int i3 = i + random.nextInt(16) + 8;
				if (world.getBlock(i3, (j1 = world.getHeightValue(i3, k3 = k + random.nextInt(16) + 8)) - 1, k3) != block) {
					continue;
				}
				int height = j1 + random.nextInt(4);
				for (int j2 = j1; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
					world.setBlock(i3, j2, k3, block, 0, 3);
				}
			}
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