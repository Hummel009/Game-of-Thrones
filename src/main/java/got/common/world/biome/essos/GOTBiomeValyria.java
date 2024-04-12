package got.common.world.biome.essos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.other.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeValyria extends GOTBiome {
	public GOTBiomeValyria(int i, boolean major) {
		super(i, major);
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
		biomeColors.setGrass(new Color(0x808080));
		biomeColors.setFoliage(new Color(0x808080));
		biomeColors.setSky(new Color(0x808080));
		biomeColors.setClouds(new Color(0x808080));
		biomeColors.setFog(new Color(0x808080));
		biomeColors.setWater(0x808080);
		biomeColors.setFoggy(true);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALLER, 100);
		decorator.addTree(GOTTreeType.OAK_GIANT, 1);
		decorator.addTree(GOTTreeType.OAK_PARTY, 100);
		decorator.addTree(GOTTreeType.OAK_LARGE, 1000);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.setTreesPerChunk(7);
		decorator.setGrassPerChunk(6);
		decorator.setDoubleGrassPerChunk(1);
		decorator.setFlowersPerChunk(3);
		decorator.setDoubleFlowersPerChunk(1);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VALYRIA, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterValyria;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ESSOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_COBBLE;
	}
}