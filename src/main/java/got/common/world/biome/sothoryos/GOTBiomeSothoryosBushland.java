package got.common.world.biome.sothoryos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeSothoryosBushland extends GOTBiome {
	private static final WorldGenerator BOULDER_GEN = new GOTWorldGenBoulder(Blocks.stone, 0, 1, 3);

	public GOTBiomeSothoryosBushland(int i, boolean major) {
		super(i, major);
		setupExoticFauna();
		biomeVariants.add(GOTBiomeVariant.FLOWERS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setBiomeGemFactor(2.0f);
		decorator.setBiomeOreFactor(2.0f);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(1);
		decorator.setGrassPerChunk(16);
		decorator.setDoubleGrassPerChunk(10);
		decorator.setCornPerChunk(4);
		decorator.addTree(GOTTreeType.ACACIA, 300);
		decorator.addTree(GOTTreeType.ACACIA_DEAD, 5);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		decorator.addTree(GOTTreeType.CEDAR, 300);
		decorator.addTree(GOTTreeType.CEDAR_LARGE, 150);
		decorator.addTree(GOTTreeType.CYPRESS, 500);
		decorator.addTree(GOTTreeType.CYPRESS_LARGE, 50);
		decorator.addTree(GOTTreeType.DATE_PALM, 50);
		decorator.addTree(GOTTreeType.LEMON, 5);
		decorator.addTree(GOTTreeType.LIME, 5);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 10);
		decorator.addTree(GOTTreeType.ORANGE, 5);
		decorator.addTree(GOTTreeType.PALM, 500);
		decorator.addTree(GOTTreeType.DRAGONBLOOD, 50);
		decorator.addTree(GOTTreeType.KANUKA, 50);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);
		decorator.addSettlement(new GOTStructureSothoryosSettlement(this, 1.0f));
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i1;
		int k1;
		int l;
		super.decorate(world, random, i, k);
		if (enableTermite()) {
			if (random.nextInt(32) == 0) {
				int boulders = 1 + random.nextInt(4);
				for (l = 0; l < boulders; ++l) {
					i1 = i + random.nextInt(16) + 8;
					k1 = k + random.nextInt(16) + 8;
					BOULDER_GEN.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
				}
			}
			if (random.nextInt(16) == 0) {
				int termites = 1 + random.nextInt(4);
				for (l = 0; l < termites; ++l) {
					i1 = i + random.nextInt(16) + 8;
					k1 = k + random.nextInt(16) + 8;
					int j1 = world.getHeightValue(i1, k1);
					new GOTWorldGenBoulder(GOTBlocks.termiteMound, 0, 1, 4).generate(world, random, i1, j1, k1);
					for (int x = 0; x < 5; ++x) {
						int k2;
						int j2;
						int i2 = i1 - random.nextInt(5) + random.nextInt(5);
						if (!world.getBlock(i2, (j2 = world.getHeightValue(i2, k2 = k1 - random.nextInt(5) + random.nextInt(5))) - 1, k1).isOpaqueCube()) {
							continue;
						}
						int j3 = j2 + random.nextInt(4);
						for (int j4 = j2; j4 <= j3; ++j4) {
							world.setBlock(i2, j4, k2, GOTBlocks.termiteMound);
							world.getBlock(i2, j4 - 1, k2).onPlantGrow(world, i2, j4 - 1, k2, i2, j4 - 1, k2);
						}
					}
				}
			}
		}
	}

	protected boolean enableTermite() {
		return true;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosBushland;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.SOTHORYOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SOTHORYOS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SOTHORYOS;
	}
}