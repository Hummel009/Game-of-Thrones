package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Random;

public class GOTBiomeYeen extends GOTBiomeSothoryosJungle {
	public NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
	public NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
	public NoiseGeneratorPerlin noiseObsidianGravel = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);

	public GOTBiomeYeen(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		spawnableCreatureList.clear();
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 0;
		decorator.doubleFlowersPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.canePerChunk = 0;
		biomeColors.setFoggy(true);
		npcSpawnList.clear();
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.09, k * 0.09);
		double d2 = noiseDirt.func_151601_a(i * 0.6, k * 0.6);
		double d3 = noiseGravel.func_151601_a(i * 0.09, k * 0.09);
		double d4 = noiseGravel.func_151601_a(i * 0.6, k * 0.6);
		double d5 = noiseObsidianGravel.func_151601_a(i * 0.09, k * 0.09);
		if (d5 + noiseObsidianGravel.func_151601_a(i * 0.6, k * 0.6) > 0.5) {
			topBlock = GOTRegistry.obsidianGravel;
			topBlockMeta = 0;
		} else if (d3 + d4 > 0.6) {
			topBlock = Blocks.gravel;
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
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterYeen;
	}

}