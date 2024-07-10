package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityShryke;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeShrykesLand extends GOTBiomeEssosBase {
	public GOTBiomeShrykesLand(int i, boolean major) {
		super(i, major);
		preseter.setupBushlandView();
		preseter.setupBushlandFlora();
		preseter.removeAllEntities();

		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK_DEAD, 500);
		decorator.addTree(GOTTreeType.BEECH_DEAD, 500);
		decorator.addTree(GOTTreeType.SPRUCE_DEAD, 500);
		decorator.addTree(GOTTreeType.BIRCH_DEAD, 500);

		addSpawnableMonster(GOTEntityShryke.class, 5, 1, 1);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.MOSSOVY;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterShrykesLand;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YI_TI;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}