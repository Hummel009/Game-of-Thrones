package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
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

		setupRuinedStructures(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.SHRYKE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		biomeWaypoints = GOTWaypoint.Region.MOSSOVY;
		biomeAchievement = GOTAchievement.enterShrykesLand;
		chanceToSpawnAnimals = 0.1f;
		banditChance = GOTEventSpawner.EventChance.COMMON;
		wallBlock = GOTBezierType.WALL_YI_TI;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}