package got.common.world.biome.variant;

import got.common.world.feature.GOTTreeType;
import net.minecraft.block.Block;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeVariant {
	private static final GOTBiomeVariant[] ALL_VARIANTS = new GOTBiomeVariant[256];

	public static final GOTBiomeVariant STANDARD = new GOTBiomeVariant(0, "standard");
	public static final GOTBiomeVariant FLOWERS = new GOTBiomeVariant(1, "flowers").setFlowers(10.0f);
	public static final GOTBiomeVariant FOREST = new GOTBiomeVariantForest(2, "forest");
	public static final GOTBiomeVariant FOREST_LIGHT = new GOTBiomeVariant(3, "forest_light").setTrees(3.0f).setGrass(2.0f);
	public static final GOTBiomeVariant STEPPE = new GOTBiomeVariant(4, "standard").setHeight(0.0f, 0.1f).setTrees(0.0f);
	public static final GOTBiomeVariant HILLS = new GOTBiomeVariant(6, "hills").setHeight(0.5f, 1.5f).setGrass(0.5f);
	public static final GOTBiomeVariant HILLS_FOREST = new GOTBiomeVariant(7, "hills_forest").setHeight(0.5f, 1.5f).setTrees(3.0f);
	public static final GOTBiomeVariant MOUNTAIN = new GOTBiomeVariant(8, "mountain").setHeight(1.2f, 3.0f);
	public static final GOTBiomeVariant CLEARING = new GOTBiomeVariant(9, "clearing").setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(2.0f).setFlowers(3.0f);
	public static final GOTBiomeVariant SWAMP_LOWLAND = new GOTBiomeVariant(18, "swampLowland").setHeight(-0.12f, 0.2f).setTrees(0.5f).setGrass(5.0f).setMarsh();
	public static final GOTBiomeVariant SAVANNAH_BAOBAB = new GOTBiomeVariant(20, "savannahBaobab").setHeight(0.0f, 0.5f).setTrees(1.5f).setGrass(0.5f).addTreeTypes(0.6f, GOTTreeType.BAOBAB, 100);
	public static final GOTBiomeVariant LAKE = new GOTBiomeVariant(21, "lake").setAbsoluteHeight(-0.5f, 0.05f);
	public static final GOTBiomeVariant VINEYARD = new GOTBiomeVariant(26, "vineyard").setHeight(0.0f, 0.0f).setTrees(0.0f).setGrass(0.0f).setFlowers(0.0f).disableStructuresSettlements();
	public static final GOTBiomeVariant FOREST_ASPEN = new GOTBiomeVariantForest(27, "forest_aspen").addTreeTypes(0.8f, GOTTreeType.ASPEN, 1000, GOTTreeType.ASPEN_LARGE, 50);
	public static final GOTBiomeVariant FOREST_BIRCH = new GOTBiomeVariantForest(28, "forest_birch").addTreeTypes(0.8f, GOTTreeType.BIRCH, 1000, GOTTreeType.BIRCH_LARGE, 150);
	public static final GOTBiomeVariant FOREST_BEECH = new GOTBiomeVariantForest(29, "forest_beech").addTreeTypes(0.8f, GOTTreeType.BEECH, 1000, GOTTreeType.BEECH_LARGE, 150);
	public static final GOTBiomeVariant FOREST_MAPLE = new GOTBiomeVariantForest(30, "forest_maple").addTreeTypes(0.8f, GOTTreeType.MAPLE, 1000, GOTTreeType.MAPLE_LARGE, 150);
	public static final GOTBiomeVariant FOREST_LARCH = new GOTBiomeVariantForest(31, "forest_larch").addTreeTypes(0.8f, GOTTreeType.LARCH, 1000);
	public static final GOTBiomeVariant FOREST_PINE = new GOTBiomeVariantForest(32, "forest_pine").addTreeTypes(0.8f, GOTTreeType.PINE, 1000);
	public static final GOTBiomeVariant ORCHARD_APPLE_PEAR = new GOTBiomeVariantOrchard(34, "orchard_apple_pear").addTreeTypes(1.0f, GOTTreeType.APPLE, 100, GOTTreeType.PEAR, 100);
	public static final GOTBiomeVariant ORCHARD_ORANGE = new GOTBiomeVariantOrchard(35, "orchard_orange").addTreeTypes(1.0f, GOTTreeType.ORANGE, 100);
	public static final GOTBiomeVariant ORCHARD_LEMON = new GOTBiomeVariantOrchard(36, "orchard_lemon").addTreeTypes(1.0f, GOTTreeType.LEMON, 100);
	public static final GOTBiomeVariant ORCHARD_LIME = new GOTBiomeVariantOrchard(37, "orchard_lime").addTreeTypes(1.0f, GOTTreeType.LIME, 100);
	public static final GOTBiomeVariant ORCHARD_ALMOND = new GOTBiomeVariantOrchard(38, "orchard_almond").addTreeTypes(1.0f, GOTTreeType.ALMOND, 100);
	public static final GOTBiomeVariant ORCHARD_OLIVE = new GOTBiomeVariantOrchard(39, "orchard_olive").addTreeTypes(1.0f, GOTTreeType.OLIVE, 100);
	public static final GOTBiomeVariant ORCHARD_PLUM = new GOTBiomeVariantOrchard(40, "orchard_plum").addTreeTypes(1.0f, GOTTreeType.PLUM, 100);
	public static final GOTBiomeVariant RIVER = new GOTBiomeVariant(41, "river").setAbsoluteHeight(-0.5f, 0.05f);
	public static final GOTBiomeVariant ORCHARD_DATE = new GOTBiomeVariantOrchard(45, "orchard_date").addTreeTypes(1.0f, GOTTreeType.DATE_PALM, 100);
	public static final GOTBiomeVariant ORCHARD_POMEGRANATE = new GOTBiomeVariantOrchard(47, "orchard_pomegranate").addTreeTypes(1.0f, GOTTreeType.POMEGRANATE, 100);
	public static final GOTBiomeVariant FOREST_CHERRY = new GOTBiomeVariantForest(52, "forest_cherry").addTreeTypes(0.8f, GOTTreeType.CHERRY, 1000);
	public static final GOTBiomeVariant FOREST_LEMON = new GOTBiomeVariantForest(53, "forest_lemon").addTreeTypes(0.8f, GOTTreeType.LEMON, 1000);
	public static final GOTBiomeVariant FOREST_LIME = new GOTBiomeVariantForest(54, "forest_lime").addTreeTypes(0.8f, GOTTreeType.LIME, 1000);
	public static final GOTBiomeVariant FOREST_CEDAR = new GOTBiomeVariantForest(60, "forest_cedar").addTreeTypes(0.8f, GOTTreeType.CEDAR, 1000, GOTTreeType.CEDAR_LARGE, 50);
	public static final GOTBiomeVariant FOREST_CYPRESS = new GOTBiomeVariantForest(61, "forest_cypress").addTreeTypes(0.8f, GOTTreeType.CYPRESS, 1000, GOTTreeType.CYPRESS_LARGE, 50);

	public static final NoiseGeneratorPerlin MARSH_NOISE = new NoiseGeneratorPerlin(new Random(444L), 1);

	private final int variantID;
	private final String variantName;
	private final Collection<GOTTreeType.WeightedTreeType> treeTypes = new ArrayList<>();

	private float heightBoost;
	private float variantTreeChance;
	private float absoluteHeightLevel;
	private float hillFactor = 1.0f;
	private float treeFactor = 1.0f;
	private float grassFactor = 1.0f;
	private float flowerFactor = 1.0f;
	private boolean hasMarsh;
	private boolean absoluteHeight;
	private boolean disableStructures;
	private boolean disableSettlements;

	protected GOTBiomeVariant(int i, String s) {
		if (ALL_VARIANTS[i] != null) {
			throw new IllegalArgumentException("got Biome variant already exists at index " + i);
		}
		variantID = i;
		ALL_VARIANTS[i] = this;
		variantName = s;
	}

	public static GOTBiomeVariant getVariantForID(int i) {
		GOTBiomeVariant variant = ALL_VARIANTS[i];
		if (variant == null) {
			return STANDARD;
		}
		return variant;
	}

	protected GOTBiomeVariant addTreeTypes(float f, Object... trees) {
		variantTreeChance = f;
		for (int i = 0; i < trees.length / 2; ++i) {
			Object obj1 = trees[i * 2];
			Object obj2 = trees[i * 2 + 1];
			treeTypes.add(new GOTTreeType.WeightedTreeType((GOTTreeType) obj1, (Integer) obj2));
		}
		return this;
	}

	public void decorateVariant(World world, Random random, int i, int k) {
	}

	protected void disableSettlements() {
		disableSettlements = true;
	}

	private GOTBiomeVariant disableStructuresSettlements() {
		disableStructures = true;
		disableSettlements = true;
		return this;
	}

	public void generateVariantTerrain(Block[] blocks, byte[] meta, int i, int k) {
	}

	public float getHeightBoost() {
		return heightBoost;
	}

	public GOTTreeType getRandomTree(Random random) {
		WeightedRandom.Item item = WeightedRandom.getRandomItem(random, treeTypes);
		return ((GOTTreeType.WeightedTreeType) item).getTreeType();
	}

	public String getUnlocalizedName() {
		return "got.variant." + variantName + ".name";
	}

	private GOTBiomeVariant setAbsoluteHeight(float height, float hills) {
		absoluteHeight = true;
		absoluteHeightLevel = height;
		float f = height;
		f -= 2.0f;
		heightBoost = f + 0.2f;
		hillFactor = hills;
		return this;
	}

	private GOTBiomeVariant setFlowers(float f) {
		flowerFactor = f;
		return this;
	}

	protected GOTBiomeVariant setGrass(float f) {
		grassFactor = f;
		return this;
	}

	protected GOTBiomeVariant setHeight(float height, float hills) {
		heightBoost = height;
		hillFactor = hills;
		return this;
	}

	private GOTBiomeVariant setMarsh() {
		hasMarsh = true;
		return this;
	}

	protected GOTBiomeVariant setTrees(float f) {
		treeFactor = f;
		return this;
	}

	public int getVariantID() {
		return variantID;
	}

	public String getVariantName() {
		return variantName;
	}

	public boolean isAbsoluteHeight() {
		return absoluteHeight;
	}

	public float getAbsoluteHeightLevel() {
		return absoluteHeightLevel;
	}

	public float getHillFactor() {
		return hillFactor;
	}

	public float getTreeFactor() {
		return treeFactor;
	}

	public float getGrassFactor() {
		return grassFactor;
	}

	public float getFlowerFactor() {
		return flowerFactor;
	}

	public boolean isHasMarsh() {
		return hasMarsh;
	}

	public boolean isDisableStructures() {
		return disableStructures;
	}

	public boolean isDisableSettlements() {
		return disableSettlements;
	}

	public Collection<GOTTreeType.WeightedTreeType> getTreeTypes() {
		return treeTypes;
	}

	public float getVariantTreeChance() {
		return variantTreeChance;
	}
}