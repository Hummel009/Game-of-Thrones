package got.common.world.biome.essos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenDoubleFlower;
import got.common.world.feature.GOTWorldGenSand;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.other.GOTStructureBurntHouse;
import got.common.world.structure.other.GOTStructureRottenHouse;
import got.common.world.structure.other.GOTStructureRuinedHouse;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeEssos extends GOTBiome {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	private static final NoiseGeneratorPerlin NOISE_SAND = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	private static final NoiseGeneratorPerlin NOISE_RED_SAND = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);

	public GOTBiomeEssos(int i, boolean major) {
		super(i, major);
		setupExoticFauna();
		biomeVariants.add(GOTBiomeVariant.FLOWERS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LEMON, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIME, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_CEDAR, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_CYPRESS, 0.2f);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(2);
		decorator.setFlowersPerChunk(1);
		decorator.setDoubleFlowersPerChunk(1);
		decorator.setCactiPerChunk(1);
		decorator.setCornPerChunk(4);
		if (!disableNoise()) {
			decorator.setClayGen(new GOTWorldGenSand(GOTBlocks.redClay, 5, 1));
			decorator.addSoil(new WorldGenMinable(GOTBlocks.redClay, 32, Blocks.dirt), 40.0f, 0, 80);
		}
		decorator.addTree(GOTTreeType.ACACIA, 300);
		decorator.addTree(GOTTreeType.ACACIA_DEAD, 1);
		decorator.addTree(GOTTreeType.ALMOND, 5);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		decorator.addTree(GOTTreeType.CEDAR, 300);
		decorator.addTree(GOTTreeType.CEDAR_LARGE, 150);
		decorator.addTree(GOTTreeType.CYPRESS, 500);
		decorator.addTree(GOTTreeType.CYPRESS_LARGE, 50);
		decorator.addTree(GOTTreeType.DATE_PALM, 50);
		decorator.addTree(GOTTreeType.LEMON, 5);
		decorator.addTree(GOTTreeType.LIME, 5);
		decorator.addTree(GOTTreeType.OAK_DESERT, 400);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 10);
		decorator.addTree(GOTTreeType.ORANGE, 5);
		decorator.addTree(GOTTreeType.PALM, 500);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.DRAGONBLOOD, 50);
		decorator.addTree(GOTTreeType.DRAGONBLOOD_LARGE, 1);
		decorator.addTree(GOTTreeType.KANUKA, 50);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
	}

	protected boolean disableNoise() {
		return true;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		if (!disableNoise()) {
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
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		if (!disableNoise()) {
			topBlock = topBlock_pre;
			topBlockMeta = topBlockMeta_pre;
			fillerBlock = fillerBlock_pre;
			fillerBlockMeta = fillerBlockMeta_pre;
		}
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ESSOS.getSubregion(biomeName);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		GOTWorldGenDoubleFlower doubleFlowerGen = new GOTWorldGenDoubleFlower();
		if (random.nextInt(5) == 0) {
			doubleFlowerGen.setFlowerType(3);
		} else {
			doubleFlowerGen.setFlowerType(2);
		}
		return doubleFlowerGen;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_IBBEN;
	}

	@Override
	public int getWallTop() {
		return 90;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}