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
		SpawnListContainer[] c = new SpawnListContainer[2];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_FRONTIER, 4).setSpawnChance(GOTBiome.SPAWN);
		c[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_SAMURAI, 2).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);
		clearBiomeVariants();
		GOTStructureFiveFortsTower tower = new GOTStructureFiveFortsTower(this, 0.0f);
		tower.affix(GOTWaypoint.FiveForts1);
		tower.affix(GOTWaypoint.FiveForts2);
		tower.affix(GOTWaypoint.FiveForts3);
		tower.affix(GOTWaypoint.FiveForts4);
		tower.affix(GOTWaypoint.FiveForts5, 0, 0, 3);
		decorator.affix(tower);
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