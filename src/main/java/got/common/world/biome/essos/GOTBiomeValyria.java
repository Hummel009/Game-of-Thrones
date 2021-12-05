package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeValyria extends GOTBiome {
	public GOTBiomeValyria(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableGOTAmbientList.clear();
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 200);
		decorator.addTree(GOTTreeType.OAK_PARTY, 200);
		decorator.addTree(GOTTreeType.OAK_LARGE, 2000);
		decorator.treesPerChunk = 7;
		biomeColors.setFoggy(true);
		biomeColors.setGrass(0x808080);
		biomeColors.setFoliage(0x808080);
		biomeColors.setSky(0x808080);
		biomeColors.setClouds(0x808080);
		biomeColors.setFog(0x808080);
		biomeColors.setWater(0x808080);
		
		SpawnListContainer[] c = new SpawnListContainer[1];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.VALYRIA, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_VALYRIA;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("valyria");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.VALYRIA;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.VALYRIA;
	}
}