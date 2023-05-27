package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarFortress;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarSettlement;
import got.common.world.structure.other.GOTStructureStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeGhiscar extends GOTBiomeEssos {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(3869098386927266L), 1);
	public static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(92726978206783582L), 1);
	public WorldGenerator boulderGen = new GOTWorldGenBoulder(Blocks.stone, 0, 1, 2);

	public GOTBiomeGhiscar(int i, boolean major) {
		super(i, major);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.cactiPerChunk = 1;
		decorator.deadBushPerChunk = 1;
		decorator.addSettlement(new GOTStructureGhiscarSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureGhiscarFortress(false), 800);
		setDarkUnreliable();
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (random.nextInt(12) == 0) {
			int boulders = 1 + random.nextInt(4);
			for (int l = 0; l < boulders; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		double d4;
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.09, k * 0.09);
		double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
		double d3 = noiseSand.func_151601_a(i * 0.09, k * 0.09);
		d4 = noiseSand.func_151601_a(i * 0.4, k * 0.4);
		if (d3 + d4 > 0.6) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.2) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterGhiscar;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.GHISCAR;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		return new GOTBiome.GrassBlockAndMeta(GOTRegistry.aridGrass, 0);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}
}