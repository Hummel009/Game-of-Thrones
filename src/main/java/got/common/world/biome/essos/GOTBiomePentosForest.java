package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenDoubleFlower;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomePentosForest extends GOTBiomeEssosBase {
	public GOTBiomePentosForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(false);

		setupRuinedStructures(true);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.PENTOS;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterPentos;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtSandRedSandNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		GOTWorldGenDoubleFlower doubleFlowerGen = new GOTWorldGenDoubleFlower();
		doubleFlowerGen.setFlowerType(random.nextInt(4));
		return doubleFlowerGen;
	}
}