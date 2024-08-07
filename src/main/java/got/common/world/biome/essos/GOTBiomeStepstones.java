package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenDoubleFlower;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeStepstones extends GOTBiomeEssosBase {
	public GOTBiomeStepstones(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(false);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(false);
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(true);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c1);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SOUTHERN_FREE_CITIES;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterStepstones;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtSandRedSandNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		GOTWorldGenDoubleFlower doubleFlowerGen = new GOTWorldGenDoubleFlower();
		doubleFlowerGen.setFlowerType(random.nextInt(4));
		return doubleFlowerGen;
	}
}