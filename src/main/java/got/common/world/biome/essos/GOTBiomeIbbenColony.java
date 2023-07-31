package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ibben.GOTStructureIbbenSettlement;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeIbbenColony extends GOTBiomeEssos {
	public GOTBiomeIbbenColony(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureIbbenSettlement(this, 1.0f));
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_MILITARY, 2).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IBBEN;
	}

}