package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.qarth.GOTStructureQarthSettlement;
import got.common.world.structure.other.GOTStructureBurntHouse;
import got.common.world.structure.other.GOTStructureRottenHouse;
import got.common.world.structure.other.GOTStructureRuinedHouse;

import java.util.ArrayList;

public class GOTBiomeQarthColony extends GOTBiomeSothoryosSavannah {
	public GOTBiomeQarthColony(int i, boolean major) {
		super(i, major);
		decorator.clearSettlements();
		decorator.addSettlement(new GOTStructureQarthSettlement(this, 1.0f).setIsCastle());
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		npcSpawnList.clear();
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.QARTH_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQarthColony;
	}


	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}