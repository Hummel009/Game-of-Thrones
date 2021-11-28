package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;

public class GOTBiomeYiTiFlat extends GOTBiomeYiTi {
	public GOTBiomeYiTiFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container = new SpawnListContainer[3];
		container[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		container[2] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_SAMURAI, 2).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureYiTiCity town = new GOTStructureYiTiCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.TraderTown, 0, -1);
		town.affix(GOTWaypoint.SiQo, 1, 0);
		town.affix(GOTWaypoint.Tiqui, 0, -1);
		town.affix(GOTWaypoint.Asabhad, -1, 0);
		town.affix(GOTWaypoint.Yin, 0, 1, 2);
		town.affix(GOTWaypoint.Jinqi, -1, 0, 3);
		town.affix(GOTWaypoint.Huiji);
		town.affix(GOTWaypoint.Vaibei, 0, -1);
		town.affix(GOTWaypoint.Manjin, 1, 0, 3);
		town.affix(GOTWaypoint.Lizhao, 1, 0);
		town.affix(GOTWaypoint.Baoji, 0, 1, 2);
		town.affix(GOTWaypoint.Yibin, -1, 0, 3);
		town.affix(GOTWaypoint.Yunnan, 1, 0, 1);
		town.affix(GOTWaypoint.Eijiang, 0, 1, 2);
		town.affix(GOTWaypoint.LesserMoraq);
		town.affix(GOTWaypoint.PortMoraq);
		town.affix(GOTWaypoint.Vahar);
		town.affix(GOTWaypoint.Faros);
		town.affix(GOTWaypoint.Zabhad);
		town.affix(GOTWaypoint.Marahai);
		town.affix(GOTWaypoint.Turrani);
		town.affix(GOTWaypoint.LengYi);
		town.affix(GOTWaypoint.LengMa);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}