package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.other.*;

import java.util.ArrayList;

public class GOTBiomeValyria extends GOTBiome {
	public GOTBiomeValyria(int i, boolean major) {
		super(i, major);
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		biomeColors.setGrass(0x808080);
		biomeColors.setFoliage(0x808080);
		biomeColors.setSky(0x808080);
		biomeColors.setClouds(0x808080);
		biomeColors.setFog(0x808080);
		biomeColors.setWater(0x808080);
		biomeColors.setFoggy(true);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 200);
		decorator.addTree(GOTTreeType.OAK_TALLER, 200);
		decorator.addTree(GOTTreeType.OAK_GIANT, 20);
		decorator.addTree(GOTTreeType.OAK_PARTY, 200);
		decorator.addTree(GOTTreeType.OAK_LARGE, 2000);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.treesPerChunk = 7;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VALYRIA, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterValyria;
	}


	@Override
	public Region getBiomeWaypoints() {
		return Region.VALYRIA;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion(biomeName);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_COBBLE;
	}
}