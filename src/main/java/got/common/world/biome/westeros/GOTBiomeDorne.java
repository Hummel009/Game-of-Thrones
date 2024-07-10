package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.entity.other.GOTEntityNPC;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.other.GOTStructureStoneRuin;
import got.common.world.structure.westeros.dorne.GOTStructureDorneSettlement;
import got.common.world.structure.westeros.dorne.GOTStructureDorneWatchfort;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeDorne extends GOTBiomeWesterosBase {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	private static final NoiseGeneratorPerlin NOISE_SAND = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	private static final NoiseGeneratorPerlin NOISE_RED_SAND = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);

	public GOTBiomeDorne(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(false);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(true);
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(true);

		decorator.addSettlement(new GOTStructureDorneSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureDorneWatchfort(false), 800);

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CONQUEST, 4).setSpawnChance(SPAWN * 0.5));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_MILITARY, 10).setSpawnChance(SPAWN * 0.5));
		npcSpawnList.newFactionList(5).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.RED_SCORPION, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c3);
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.DORNE;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorne;
	}

	@Override
	public Class<? extends GOTEntityNPC> getBanditEntityClass() {
		return GOTEntityDarkSkinBandit.class;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_DIRT.func_151601_a(i * 0.002, k * 0.002);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.07, k * 0.07);
		double d3 = NOISE_DIRT.func_151601_a(i * 0.25, k * 0.25);
		double d4 = NOISE_SAND.func_151601_a(i * 0.002, k * 0.002);
		double d5 = NOISE_SAND.func_151601_a(i * 0.07, k * 0.07);
		double d6 = NOISE_SAND.func_151601_a(i * 0.25, k * 0.25);
		double d7 = NOISE_RED_SAND.func_151601_a(i * 0.002, k * 0.002);
		if (d7 + NOISE_RED_SAND.func_151601_a(i * 0.07, k * 0.07) + NOISE_RED_SAND.func_151601_a(i * 0.25, k * 0.25) > 0.9) {
			topBlock = Blocks.sand;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d4 + d5 + d6 > 1.2) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 + d3 > 0.4) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}
}