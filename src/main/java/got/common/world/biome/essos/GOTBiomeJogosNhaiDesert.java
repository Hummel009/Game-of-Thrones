package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeJogosNhaiDesert extends GOTBiomeEssosBase implements GOTBiome.Desert {
	public GOTBiomeJogosNhaiDesert(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.whiteSand;
		fillerBlock = GOTBlocks.whiteSand;

		preseter.setupDesertView();
		preseter.setupDesertFlora();
		preseter.setupDesertFauna();
		preseter.setupDesertTrees();

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DESERT_SCORPION, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ESSOS_SEPARATOR;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterJogosNhaiDesert;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YI_TI;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		decorator.decorateDesert(world, random, i, k);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDesertNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		return new GOTBiome.GrassBlockAndMeta(GOTBlocks.aridGrass, 0);
	}
}