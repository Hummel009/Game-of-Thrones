package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.lys.GOTStructureLysFortress;
import got.common.world.structure.essos.lys.GOTStructureLysSettlement;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeLys extends GOTBiomeEssos {
	public GOTBiomeLys(int i, boolean major) {
		super(i, major);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.addSettlement(new GOTStructureLysSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureLysFortress(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.MYR, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.VOLANTIS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.TYROSH, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LYS_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LYS_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.MYR_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.TYROSH_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public boolean disableNoise() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLys;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SOUTHERN_FREE_CITIES;
	}
}