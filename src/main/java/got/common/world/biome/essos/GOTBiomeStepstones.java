package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenSand;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.other.GOTStructureStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeStepstones extends GOTBiomeEssos {
	private static final NoiseGeneratorPerlin NOISE_GRASS = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
	private static final NoiseGeneratorPerlin NOISE_SAND = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
	private static final NoiseGeneratorPerlin NOISE_SANDSTONE = new NoiseGeneratorPerlin(new Random(267215026920L), 1);

	public GOTBiomeStepstones(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.stone;
		topBlockMeta = 0;
		fillerBlock = topBlock;
		fillerBlockMeta = topBlockMeta;
		decorator.setClayPerChunk(4);
		decorator.setDoubleFlowersPerChunk(1);
		decorator.setDoubleGrassPerChunk(12);
		decorator.setFlowersPerChunk(3);
		decorator.setGrassPerChunk(10);
		decorator.setTreesPerChunk(1);
		decorator.setClayGen(new GOTWorldGenSand(GOTBlocks.redClay, 5, 1));
		decorator.addSoil(new WorldGenMinable(GOTBlocks.redClay, 32, Blocks.dirt), 40.0f, 0, 80);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c2);
		banditEntityClass = GOTEntityDarkSkinBandit.class;
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_GRASS.func_151601_a(i * 0.06, k * 0.06);
		double d2 = NOISE_GRASS.func_151601_a(i * 0.47, k * 0.47);
		double d3 = NOISE_DIRT.func_151601_a(i * 0.06, k * 0.06);
		double d4 = NOISE_DIRT.func_151601_a(i * 0.47, k * 0.47);
		double d5 = NOISE_SAND.func_151601_a(i * 0.06, k * 0.06);
		double d6 = NOISE_SAND.func_151601_a(i * 0.47, k * 0.47);
		double d7 = NOISE_SANDSTONE.func_151601_a(i * 0.06, k * 0.06);
		if (d7 + NOISE_SANDSTONE.func_151601_a(i * 0.47, k * 0.47) > 0.8) {
			topBlock = Blocks.sandstone;
			topBlockMeta = 0;
			fillerBlock = Blocks.sand;
			fillerBlockMeta = 0;
		} else if (d5 + d6 > 0.6) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d3 + d4 > 0.5) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.4) {
			topBlock = Blocks.grass;
			topBlockMeta = 0;
			fillerBlock = Blocks.dirt;
			fillerBlockMeta = 0;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterStepstones;
	}
}