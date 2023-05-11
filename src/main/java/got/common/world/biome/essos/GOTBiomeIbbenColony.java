package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ibben.GOTStructureIbbenVillage;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;

public class GOTBiomeIbbenColony extends GOTBiomeEssos {
	public GOTBiomeIbbenColony(int i, boolean major) {
		super(i, major);
		decorator.addVillage(new GOTStructureIbbenVillage(this, 1.0f));
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_MILITARY, 2).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("ibbenColony");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IBBEN;
	}

}