package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.braavos.GOTStructureBraavosFortress;
import got.common.world.structure.essos.braavos.GOTStructureBraavosSettlement;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeBraavos extends GOTBiomeEssosBase {
	public GOTBiomeBraavos(int i, boolean major) {
		super(i, major);
		preseter.setupMideratePlainsView();
		preseter.setupMideratePlainsFlora();
		preseter.setupMideratePlainsFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureBraavosSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureBraavosFortress(false), 800);

		invasionSpawns.addInvasion(GOTInvasions.VOLANTIS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.PENTOS, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);

		biomeWaypoints = GOTWaypoint.Region.BRAAVOS;
		biomeAchievement = GOTAchievement.enterBraavos;
	}
}