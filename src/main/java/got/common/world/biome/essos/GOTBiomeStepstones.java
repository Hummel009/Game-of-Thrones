package got.common.world.biome.essos;

import java.util.*;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeStepstones extends GOTBiome {
	public static NoiseGeneratorPerlin noiseGrass = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
	public static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
	public static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(267215026920L), 1);

	public GOTBiomeStepstones(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.stone;
		topBlockMeta = 0;
		fillerBlock = topBlock;
		fillerBlockMeta = topBlockMeta;
		setupExoticFauna();
		biomeTerrain.setXZScale(30.0);
		decorator.addSoil(new WorldGenMinable(GOTRegistry.redClay, 32, Blocks.dirt), 40.0f, 0, 80);
		decorator.addTree(GOTTreeType.ACACIA, 1000);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		decorator.addTree(GOTTreeType.MANGO, 1);
		decorator.addTree(GOTTreeType.OAK_DESERT, 300);
		decorator.addTree(GOTTreeType.PALM, 4000);
		decorator.clayGen = new GOTWorldGenSand(GOTRegistry.redClay, 5, 1);
		decorator.clayPerChunk = 4;
		decorator.doubleFlowersPerChunk = 1;
		decorator.doubleGrassPerChunk = 12;
		decorator.flowersPerChunk = 3;
		decorator.grassPerChunk = 10;
		decorator.treesPerChunk = 1;
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(5).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(5).add(c1);
		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(1).add(c2);
		setDarkUnreliable();
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseGrass.func_151601_a(i * 0.06, k * 0.06);
		double d2 = noiseGrass.func_151601_a(i * 0.47, k * 0.47);
		double d3 = noiseDirt.func_151601_a(i * 0.06, k * 0.06);
		double d4 = noiseDirt.func_151601_a(i * 0.47, k * 0.47);
		double d5 = noiseSand.func_151601_a(i * 0.06, k * 0.06);
		double d6 = noiseSand.func_151601_a(i * 0.47, k * 0.47);
		double d7 = noiseSandstone.func_151601_a(i * 0.06, k * 0.06);
		if (d7 + noiseSandstone.func_151601_a(i * 0.47, k * 0.47) > 0.8) {
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

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("stepstones");
	}
}
