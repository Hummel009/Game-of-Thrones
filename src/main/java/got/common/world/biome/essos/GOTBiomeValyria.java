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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeValyria extends GOTBiomeEssosBase {
	public GOTBiomeValyria(int i, boolean major) {
		super(i, major);
		preseter.setupBushlandView();
		preseter.setupBushlandFlora();
		preseter.removeAllEntities();

		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.1f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 0.9f);

		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK_LARGE, 500);
		decorator.addTree(GOTTreeType.OAK, 250);
		decorator.addTree(GOTTreeType.OAK_TALLER, 200);
		decorator.addTree(GOTTreeType.OAK_PARTY, 1);

		decorator.setTreesPerChunk(6);

		biomeColors.setGrass(new Color(0x808080));
		biomeColors.setFoliage(new Color(0x808080));
		biomeColors.setSky(new Color(0x808080));
		biomeColors.setClouds(new Color(0x808080));
		biomeColors.setFog(new Color(0x808080));
		biomeColors.setWater(new Color(0x808080));
		biomeColors.setFoggy(true);

		setupRuinedStructures(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VALYRIA, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterValyria;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_COBBLE;
	}
}