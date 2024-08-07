package got.common.world.biome.westeros;

import got.GOT;
import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeAlwaysWinter extends GOTBiomeWesterosBase {
	private static final WorldGenerator SNOW_BOULDER_GEN_SMALL = new GOTWorldGenBoulder(Blocks.snow, 0, 1, 4);
	private static final WorldGenerator SNOW_BOULDER_GEN_LARGE = new GOTWorldGenBoulder(Blocks.snow, 0, 5, 8).setHeightCheck(6);
	private static final WorldGenerator ICE_BOULDER_GEN_SMALL = new GOTWorldGenBoulder(Blocks.packed_ice, 0, 1, 4);
	private static final WorldGenerator ICE_BOULDER_GEN_LARGE = new GOTWorldGenBoulder(Blocks.packed_ice, 0, 5, 10).setHeightCheck(6);

	public GOTBiomeAlwaysWinter(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.ice;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupPolarTrees(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BEYOND_WALL;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterAlwaysWinter;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
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
				ICE_BOULDER_GEN_SMALL.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
			} else {
				SNOW_BOULDER_GEN_SMALL.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
			}
		}
		for (l = 0; l < 20; ++l) {
			i2 = i + random.nextInt(16) + 8;
			k2 = k + random.nextInt(16) + 8;
			if (random.nextInt(5) == 0) {
				ICE_BOULDER_GEN_LARGE.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
			} else {
				SNOW_BOULDER_GEN_LARGE.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
			}
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
}