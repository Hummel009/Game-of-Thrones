package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeDisputedLandsForest extends GOTBiomeEssosBase {
	public GOTBiomeDisputedLandsForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.SOUTHERN_FREE_CITIES;
		biomeAchievement = GOTAchievement.enterDisputedLands;
		roadBlock = GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtSandRedSandNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}