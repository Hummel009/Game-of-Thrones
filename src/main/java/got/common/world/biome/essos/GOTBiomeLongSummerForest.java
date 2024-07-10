package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeLongSummerForest extends GOTBiomeEssosBase {
	public GOTBiomeLongSummerForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.removeAllEntities();

		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK_LARGE, 1000);
		decorator.addTree(GOTTreeType.OAK, 250);
		decorator.addTree(GOTTreeType.OAK_TALLER, 250);
		decorator.addTree(GOTTreeType.OAK_PARTY, 5);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLongSummer;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_COBBLE;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}