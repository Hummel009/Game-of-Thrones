package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.entity.animal.GOTEntityWalrus;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingSettlement;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeColdCoast extends GOTBiomeWesterosBase {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
	private static final NoiseGeneratorPerlin NOISE_GRAVEL = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
	private static final NoiseGeneratorPerlin NOISE_ICE_GRAVEL = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);

	public GOTBiomeColdCoast(int i, boolean major) {
		super(i, major);
		banditChance = GOTEventSpawner.EventChance.NEVER;

		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupFrostTrees(false);

		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWalrus.class, 60, 1, 1));

		decorator.addSettlement(new GOTStructureWildlingSettlement(this, 1.0f));

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_DIRT.func_151601_a(i * 0.09, k * 0.09);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.6, k * 0.6);
		double d3 = NOISE_GRAVEL.func_151601_a(i * 0.09, k * 0.09);
		double d4 = NOISE_GRAVEL.func_151601_a(i * 0.6, k * 0.6);
		double d5 = NOISE_ICE_GRAVEL.func_151601_a(i * 0.09, k * 0.09);
		if (d5 + NOISE_ICE_GRAVEL.func_151601_a(i * 0.6, k * 0.6) > 0.5) {
			topBlock = Blocks.ice;
			topBlockMeta = 0;
		} else if (d3 + d4 > 0.6) {
			topBlock = Blocks.dirt;
			topBlockMeta = 0;
		} else if (d1 + d2 > 0.6) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
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
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterColdCoast;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BEYOND_WALL;
	}
}