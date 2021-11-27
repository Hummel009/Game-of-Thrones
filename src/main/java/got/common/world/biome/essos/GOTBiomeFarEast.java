package got.common.world.biome.essos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiCity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class GOTBiomeFarEast extends GOTBiome {
	protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
	protected static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);

	public GOTBiomeFarEast(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		clearBiomeVariants();
		variantChance = 0.5f;
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.DEADFOREST_OAK);
		this.addBiomeVariant(GOTBiomeVariant.SHRUBLAND_OAK);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND, 3.0f);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND);
		this.addBiomeVariant(GOTBiomeVariant.WASTELAND, 3.0f);
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 4;
		decorator.addTree(GOTTreeType.OAK, 400);
		decorator.addTree(GOTTreeType.OAK_LARGE, 200);
		decorator.addTree(GOTTreeType.BIRCH, 50);
		decorator.addTree(GOTTreeType.BIRCH_LARGE, 20);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.DARK_OAK, 500);
		decorator.addTree(GOTTreeType.FIR, 200);
		decorator.addTree(GOTTreeType.PINE, 200);
		decorator.addTree(GOTTreeType.SPRUCE, 200);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.APPLE, 5);
		decorator.addTree(GOTTreeType.PEAR, 5);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.OAK_SHRUB, 1500);
		registerPlainsFlowers();
		decorator.affix(new GOTStructureAsshaiCity(this, 0.1f));
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
		double d2 = noiseDirt.func_151601_a(i * 0.3, k * 0.3);
		double d3 = noiseStone.func_151601_a(i * 0.07, k * 0.07);
		if (d3 + (noiseStone.func_151601_a(i * 0.3, k * 0.3)) > 1.1) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
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
		return GOTAchievement.VISIT_SHADOW_WASTELAND;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("farEastWasteland");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ASSHAI;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.5f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 1;
	}
}
