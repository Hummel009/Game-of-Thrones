package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiAltar;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiSpiderPit;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeShadowTown extends GOTBiomeShadowLand {
	public NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
	public NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
	public NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);

	public GOTBiomeShadowTown(int i, boolean major) {
		super(i, major);
		decorator.flowersPerChunk = 0;
		decorator.addStructure(new GOTStructureAsshaiAltar(false), 250);
		decorator.addStructure(new GOTStructureAsshaiSpiderPit(false), 150);
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ASSHAI_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		double d6;
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.09, k * 0.09);
		double d2 = noiseDirt.func_151601_a(i * 0.6, k * 0.6);
		double d3 = noiseGravel.func_151601_a(i * 0.09, k * 0.09);
		double d4 = noiseGravel.func_151601_a(i * 0.6, k * 0.6);
		double d5 = noiseStone.func_151601_a(i * 0.09, k * 0.09);
		d6 = noiseStone.func_151601_a(i * 0.6, k * 0.6);
		if (d5 + d6 > 0.5) {
			topBlock = GOTBlocks.rock;
			topBlockMeta = 0;
		} else if (d3 + d4 > 0.6) {
			topBlock = GOTBlocks.basaltGravel;
			topBlockMeta = 0;
		} else if (d1 + d2 > 0.6) {
			topBlock = GOTBlocks.asshaiDirt;
			topBlockMeta = 0;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterAsshai;
	}

}