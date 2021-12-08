package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ibben.GOTStructureIbbenVillage;

public class GOTBiomeIbbenColony extends GOTBiomeEssos {
	public GOTBiomeIbbenColony(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] c = new SpawnListContainer[1];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_MILITARY, 2).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
		GOTStructureIbbenVillage village = new GOTStructureIbbenVillage(this, 0.0f);
		village.affix(GOTWaypoint.NewIbbish);
		decorator.affix(village);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("ibbenColony");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IBBEN;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WOOD;
	}

	@Override
	public int getWallTop() {
		return 90;
	}
}