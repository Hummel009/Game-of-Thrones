package got.common.world.biome.sothoryos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeSothoryosMangrove extends GOTBiome {
	public GOTBiomeSothoryosMangrove(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.sand;
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		decorator.addSoil(new WorldGenMinable(Blocks.dirt, 1, 60, Blocks.sand), 12.0f, 60, 90);
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 5;
		decorator.grassPerChunk = 8;
		decorator.addTree(GOTTreeType.MANGROVE, 1000);
		decorator.addTree(GOTTreeType.ACACIA, 10);
		decorator.addTree(GOTTreeType.OAK_DESERT, 5);
		registerSwampFlowers();
		SpawnListContainer[] container = new SpawnListContainer[2];
		container[0] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container[1] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(container1);
		decorator.addVillage(new GOTStructureSothoryosVillage(this, 1.0f));
	}

	@Override
	public boolean canSpawnHostilesInDay() {
		return true;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i1;
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 20; ++l) {
			int j1;
			int k1;
			i1 = i + random.nextInt(16);
			if (!world.getBlock(i1, j1 = 50 + random.nextInt(15), k1 = k + random.nextInt(16)).isOpaqueCube() || world.getBlock(i1, j1 + 1, k1).getMaterial() != Material.water) {
				continue;
			}
			int height = 2 + random.nextInt(3);
			for (int j2 = j1; j2 <= j1 + height; ++j2) {
				world.setBlock(i1, j2, k1, GOTRegistry.wood3, 3, 2);
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SOTHORYOS_MANGROVE;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosMangrove");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.SOTHORYOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.4f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.SOTHORYOS;
	}
}
