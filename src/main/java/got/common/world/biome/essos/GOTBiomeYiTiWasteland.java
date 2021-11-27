package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructureFiveFortsTower;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeYiTiWasteland extends GOTBiomeYiTi {
	public GOTBiomeYiTiWasteland(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		SpawnListContainer[] container = new SpawnListContainer[2];
		container[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_FRONTIER, 4).setSpawnChance(GOTBiome.SPAWN);
		container[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_SAMURAI, 2).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		clearBiomeVariants();
		GOTStructureFiveFortsTower tower = new GOTStructureFiveFortsTower(this, 0.0f);
		tower.affix(GOTWaypoint.FiveForts1);
		tower.affix(GOTWaypoint.FiveForts2);
		tower.affix(GOTWaypoint.FiveForts3);
		tower.affix(GOTWaypoint.FiveForts4);
		tower.affix(GOTWaypoint.FiveForts5, 0, 0, 3);
		decorator.addVillage(tower);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_YI_TI_WASTELAND;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("yiTiWasteland");
	}
}