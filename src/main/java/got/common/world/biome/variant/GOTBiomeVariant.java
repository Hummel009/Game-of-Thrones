package got.common.world.biome.variant;

import java.util.*;

import got.common.database.GOTRegistry;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GOTBiomeVariant {
	public static GOTBiomeVariant[] allVariants = new GOTBiomeVariant[256];
	public static GOTBiomeVariant STANDARD = new GOTBiomeVariant(0, "standard", VariantScale.LARGE);
	public static GOTBiomeVariant FLOWERS = new GOTBiomeVariant(1, "flowers", VariantScale.SMALL).setFlowers(10.0f);
	public static GOTBiomeVariant FOREST = new GOTBiomeVariantForest(2, "forest");
	public static GOTBiomeVariant FOREST_LIGHT = new GOTBiomeVariant(3, "forest_light", VariantScale.LARGE).setTrees(3.0f).setGrass(2.0f);
	public static GOTBiomeVariant STEPPE = new GOTBiomeVariant(4, "standard", VariantScale.LARGE).setHeight(0.0f, 0.1f).setTrees(0.01f).setGrass(3.0f).setFlowers(0.8f);
	public static GOTBiomeVariant HILLS = new GOTBiomeVariant(6, "hills", VariantScale.LARGE).setHeight(0.5f, 1.5f).setGrass(0.5f);
	public static GOTBiomeVariant HILLS_FOREST = new GOTBiomeVariant(7, "hills_forest", VariantScale.LARGE).setHeight(0.5f, 1.5f).setTrees(3.0f);
	public static GOTBiomeVariant MOUNTAIN = new GOTBiomeVariant(8, "mountain", VariantScale.LARGE).setHeight(1.2f, 3.0f);
	public static GOTBiomeVariant CLEARING = new GOTBiomeVariant(9, "clearing", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(2.0f).setFlowers(3.0f);
	public static GOTBiomeVariant DENSEFOREST_OAK = new GOTBiomeVariantDenseForest(10, "denseForest_oak").addTreeTypes(0.5f, GOTTreeType.OAK_LARGE, 600, GOTTreeType.OAK_PARTY, 100);
	public static GOTBiomeVariant DENSEFOREST_SPRUCE = new GOTBiomeVariantDenseForest(11, "denseForest_spruce").addTreeTypes(0.5f, GOTTreeType.SPRUCE_MEGA, 100);
	public static GOTBiomeVariant DENSEFOREST_OAK_SPRUCE = new GOTBiomeVariantDenseForest(12, "denseForest_oak_spruce").addTreeTypes(0.5f, GOTTreeType.OAK_LARGE, 600, GOTTreeType.OAK_PARTY, 200, GOTTreeType.SPRUCE_MEGA, 200);
	public static GOTBiomeVariant DEADFOREST_OAK = new GOTBiomeVariantDeadForest(13, "deadForest_oak").addTreeTypes(0.5f, GOTTreeType.OAK_DEAD, 100);
	public static GOTBiomeVariant DEADFOREST_SPRUCE = new GOTBiomeVariantDeadForest(14, "deadForest_spruce").addTreeTypes(0.5f, GOTTreeType.SPRUCE_DEAD, 100);
	public static GOTBiomeVariant DEADFOREST_OAK_SPRUCE = new GOTBiomeVariantDeadForest(15, "deadForest_oak_spruce").addTreeTypes(0.5f, GOTTreeType.OAK_DEAD, 100, GOTTreeType.SPRUCE_DEAD, 100);
	public static GOTBiomeVariant SHRUBLAND_OAK = new GOTBiomeVariant(16, "shrubland_oak", VariantScale.LARGE).setTrees(6.0f).addTreeTypes(0.7f, GOTTreeType.OAK_SHRUB, 100);
	public static GOTBiomeVariant DENSEFOREST_BIRCH = new GOTBiomeVariantDenseForest(17, "denseForest_birch").addTreeTypes(0.5f, GOTTreeType.BIRCH_LARGE, 600, GOTTreeType.BIRCH_PARTY, 100);
	public static GOTBiomeVariant SWAMP_LOWLAND = new GOTBiomeVariant(18, "swampLowland", VariantScale.SMALL).setHeight(-0.12f, 0.2f).setTrees(0.5f).setGrass(5.0f).setMarsh();
	public static GOTBiomeVariant SWAMP_UPLAND = new GOTBiomeVariant(19, "swampUpland", VariantScale.SMALL).setHeight(0.12f, 1.0f).setTrees(6.0f).setGrass(5.0f);
	public static GOTBiomeVariant SAVANNAH_BAOBAB = new GOTBiomeVariant(20, "savannahBaobab", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(1.5f).setGrass(0.5f).addTreeTypes(0.6f, GOTTreeType.BAOBAB, 100);
	public static GOTBiomeVariant LAKE = new GOTBiomeVariant(21, "lake", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f);
	public static GOTBiomeVariant BOULDERS_RED = new GOTBiomeVariant(23, "boulders_red", VariantScale.SMALL).setBoulders(new GOTWorldGenBoulder(GOTRegistry.redSandstone, 1, 1, 3), 2, 4);
	public static GOTBiomeVariant VINEYARD = new GOTBiomeVariant(26, "vineyard", VariantScale.SMALL).setHeight(0.0f, 0.0f).setTrees(0.0f).setGrass(0.0f).setFlowers(0.0f).disableStructuresVillages();
	public static GOTBiomeVariant FOREST_ASPEN = new GOTBiomeVariantForest(27, "forest_aspen").addTreeTypes(0.8f, GOTTreeType.ASPEN, 1000, GOTTreeType.ASPEN_LARGE, 50);
	public static GOTBiomeVariant FOREST_BIRCH = new GOTBiomeVariantForest(28, "forest_birch").addTreeTypes(0.8f, GOTTreeType.BIRCH, 1000, GOTTreeType.BIRCH_LARGE, 150);
	public static GOTBiomeVariant FOREST_BEECH = new GOTBiomeVariantForest(29, "forest_beech").addTreeTypes(0.8f, GOTTreeType.BEECH, 1000, GOTTreeType.BEECH_LARGE, 150);
	public static GOTBiomeVariant FOREST_MAPLE = new GOTBiomeVariantForest(30, "forest_maple").addTreeTypes(0.8f, GOTTreeType.MAPLE, 1000, GOTTreeType.MAPLE_LARGE, 150);
	public static GOTBiomeVariant FOREST_LARCH = new GOTBiomeVariantForest(31, "forest_larch").addTreeTypes(0.8f, GOTTreeType.LARCH, 1000);
	public static GOTBiomeVariant FOREST_PINE = new GOTBiomeVariantForest(32, "forest_pine").addTreeTypes(0.8f, GOTTreeType.PINE, 1000);
	public static GOTBiomeVariant ORCHARD_APPLE_PEAR = new GOTBiomeVariantOrchard(34, "orchard_apple_pear").addTreeTypes(1.0f, GOTTreeType.APPLE, 100, GOTTreeType.PEAR, 100);
	public static GOTBiomeVariant ORCHARD_ORANGE = new GOTBiomeVariantOrchard(35, "orchard_orange").addTreeTypes(1.0f, GOTTreeType.ORANGE, 100);
	public static GOTBiomeVariant ORCHARD_LEMON = new GOTBiomeVariantOrchard(36, "orchard_lemon").addTreeTypes(1.0f, GOTTreeType.LEMON, 100);
	public static GOTBiomeVariant ORCHARD_LIME = new GOTBiomeVariantOrchard(37, "orchard_lime").addTreeTypes(1.0f, GOTTreeType.LIME, 100);
	public static GOTBiomeVariant ORCHARD_ALMOND = new GOTBiomeVariantOrchard(38, "orchard_almond").addTreeTypes(1.0f, GOTTreeType.ALMOND, 100);
	public static GOTBiomeVariant ORCHARD_OLIVE = new GOTBiomeVariantOrchard(39, "orchard_olive").addTreeTypes(1.0f, GOTTreeType.OLIVE, 100);
	public static GOTBiomeVariant ORCHARD_PLUM = new GOTBiomeVariantOrchard(40, "orchard_plum").addTreeTypes(1.0f, GOTTreeType.PLUM, 100);
	public static GOTBiomeVariant RIVER = new GOTBiomeVariant(41, "river", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f);
	public static GOTBiomeVariant SCRUBLAND = new GOTBiomeVariantScrubland(42, "scrubland", Blocks.stone).setHeight(0.0f, 0.8f);
	public static GOTBiomeVariant HILLS_SCRUBLAND = new GOTBiomeVariantScrubland(43, "hills_scrubland", Blocks.stone).setHeight(0.5f, 2.0f);
	public static GOTBiomeVariant WASTELAND = new GOTBiomeVariantWasteland(44, "wasteland", Blocks.stone).setHeight(0.0f, 0.5f);
	public static GOTBiomeVariant ORCHARD_DATE = new GOTBiomeVariantOrchard(45, "orchard_date").addTreeTypes(1.0f, GOTTreeType.DATE_PALM, 100);
	public static GOTBiomeVariant DENSEFOREST_DARK_OAK = new GOTBiomeVariantDenseForest(46, "denseForest_darkOak").addTreeTypes(0.5f, GOTTreeType.DARK_OAK, 600, GOTTreeType.DARK_OAK_PARTY, 100);
	public static GOTBiomeVariant ORCHARD_POMEGRANATE = new GOTBiomeVariantOrchard(47, "orchard_pomegranate").addTreeTypes(1.0f, GOTTreeType.POMEGRANATE, 100);
	public static GOTBiomeVariant DUNES = new GOTBiomeVariantDunes(48, "dunes");
	public static GOTBiomeVariant SCRUBLAND_SAND = new GOTBiomeVariantScrubland(49, "scrubland_sand", Blocks.sandstone).setHeight(0.0f, 0.8f);
	public static GOTBiomeVariant HILLS_SCRUBLAND_SAND = new GOTBiomeVariantScrubland(50, "hills_scrubland_sand", Blocks.sandstone).setHeight(0.5f, 2.0f);
	public static GOTBiomeVariant WASTELAND_SAND = new GOTBiomeVariantWasteland(51, "wasteland_sand", Blocks.sandstone).setHeight(0.0f, 0.5f);
	public static GOTBiomeVariant FOREST_CHERRY = new GOTBiomeVariantForest(52, "forest_cherry").addTreeTypes(0.8f, GOTTreeType.CHERRY, 1000);
	public static GOTBiomeVariant FOREST_LEMON = new GOTBiomeVariantForest(53, "forest_lemon").addTreeTypes(0.8f, GOTTreeType.LEMON, 1000);
	public static GOTBiomeVariant FOREST_LIME = new GOTBiomeVariantForest(54, "forest_lime").addTreeTypes(0.8f, GOTTreeType.LIME, 1000);
	public static GOTBiomeVariant FOREST_WEIRWOOD = new GOTBiomeVariantForest(57, "forest_weirwood").addTreeTypes(0.8f, GOTTreeType.WEIRWOOD);
	public static GOTBiomeVariant[] SET_SWAMP = { SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_UPLAND };
	public static GOTBiomeVariant[] SET_MOUNTAINS = { FOREST, FOREST_LIGHT };
	public static NoiseGeneratorPerlin marshNoise = new NoiseGeneratorPerlin(new Random(444L), 1);
	public static NoiseGeneratorPerlin podzolNoise = new NoiseGeneratorPerlin(new Random(58052L), 1);
	public int variantID;
	public String variantName;
	public VariantScale variantScale;
	public float tempBoost = 0.0f;
	public float rainBoost = 0.0f;
	public boolean absoluteHeight = false;
	public float absoluteHeightLevel = 0.0f;
	public float heightBoost = 0.0f;
	public float hillFactor = 1.0f;
	public float treeFactor = 1.0f;
	public float grassFactor = 1.0f;
	public float flowerFactor = 1.0f;
	public boolean hasMarsh = false;
	public boolean disableStructures = false;
	public boolean disableVillages = false;
	public List<GOTTreeType.WeightedTreeType> treeTypes = new ArrayList<>();
	public float variantTreeChance = 0.0f;
	public WorldGenerator boulderGen;
	public int boulderChance = 0;
	public int boulderMax = 1;

	public GOTBiomeVariant(int i, String s, VariantScale scale) {
		if (allVariants[i] != null) {
			throw new IllegalArgumentException("got Biome variant already exists at index " + i);
		}
		variantID = i;
		GOTBiomeVariant.allVariants[i] = this;
		variantName = s;
		variantScale = scale;
	}

	public GOTBiomeVariant addTreeTypes(float f, Object... trees) {
		variantTreeChance = f;
		for (int i = 0; i < trees.length / 2; ++i) {
			Object obj1 = trees[i * 2];
			Object obj2 = trees[i * 2 + 1];
			treeTypes.add(new GOTTreeType.WeightedTreeType((GOTTreeType) obj1, (Integer) obj2));
		}
		return this;
	}

	public void decorateVariant(World world, Random random, int i, int k, GOTBiome biome) {
	}

	public GOTBiomeVariant disableStructuresVillages() {
		disableStructures = true;
		disableVillages = true;
		return this;
	}

	public GOTBiomeVariant disableVillages() {
		disableVillages = true;
		return this;
	}

	public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, GOTBiome biome) {
	}

	public float getHeightBoostAt(int i, int k) {
		return heightBoost;
	}

	public GOTTreeType getRandomTree(Random random) {
		WeightedRandom.Item item = WeightedRandom.getRandomItem(random, treeTypes);
		return ((GOTTreeType.WeightedTreeType) item).treeType;
	}

	public String getUnlocalizedName() {
		return "got.variant." + variantName + ".name";
	}

	public GOTBiomeVariant setAbsoluteHeight(float height, float hills) {
		absoluteHeight = true;
		absoluteHeightLevel = height;
		float f = height;
		f -= 2.0f;
		heightBoost = f += 0.2f;
		hillFactor = hills;
		return this;
	}

	public GOTBiomeVariant setBoulders(WorldGenerator boulder, int chance, int num) {
		if (num < 1) {
			throw new IllegalArgumentException("n must be > 1");
		}
		boulderGen = boulder;
		boulderChance = chance;
		boulderMax = num;
		return this;
	}

	public GOTBiomeVariant setFlowers(float f) {
		flowerFactor = f;
		return this;
	}

	public GOTBiomeVariant setGrass(float f) {
		grassFactor = f;
		return this;
	}

	public GOTBiomeVariant setHeight(float height, float hills) {
		heightBoost = height;
		hillFactor = hills;
		return this;
	}

	public GOTBiomeVariant setMarsh() {
		hasMarsh = true;
		return this;
	}

	public GOTBiomeVariant setTemperatureRainfall(float temp, float rain) {
		tempBoost = temp;
		rainBoost = rain;
		return this;
	}

	public GOTBiomeVariant setTrees(float f) {
		treeFactor = f;
		return this;
	}

	public static GOTBiomeVariant getVariantForID(int i) {
		GOTBiomeVariant variant = allVariants[i];
		if (variant == null) {
			return STANDARD;
		}
		return variant;
	}

	public enum VariantScale {
		LARGE, SMALL, NONE;

	}

}