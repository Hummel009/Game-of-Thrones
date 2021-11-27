package got.common.world.biome.sothoryos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeSothoryosSavannah extends GOTBiome {

	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 4, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 8, 4, 8));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 8, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 8, 4, 8));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 2, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 4, 6));
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.STEPPE);
		this.addBiomeVariant(GOTBiomeVariant.STEPPE_BARREN);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.SHRUBLAND_OAK);
		this.addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND, 2.0f);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.KANUKA, 100);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		SpawnListContainer[] container = new SpawnListContainer[2];
		container[0] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container[1] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container);
		decorator.affix(new GOTStructureSothoryosVillage(this, 1.0f));
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		GOTBiomeVariant variant = ((GOTWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
		int grasses = 12;
		grasses = Math.round(grasses * variant.grassFactor);
		for (int l = 0; l < grasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForGrass(random);
			grassGen.generate(world, random, i1, j1, k1);
		}
		int doubleGrasses = 4;
		doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
		for (int l = 0; l < doubleGrasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForDoubleGrass(random);
			grassGen.generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SOTHORYOS_SAVANNAH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosSavannah");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.SOTHORYOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.75f;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(5) != 0) {
			return new GOTBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		generator.func_150548_a(3);
		return generator;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.SOTHORYOS;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
