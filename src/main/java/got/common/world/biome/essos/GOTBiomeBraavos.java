package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
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

public class GOTBiomeBraavos extends GOTBiomeEssos {
	public GOTBiomeBraavos(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.BEECH_PARTY, 2);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 200);
		decorator.addTree(GOTTreeType.OAK, 200);
		decorator.addTree(GOTTreeType.OAK_LARGE, 30);
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
		decorator.addTree(GOTTreeType.MAPLE, 100);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 10);
		decorator.addTree(GOTTreeType.MAPLE_PARTY, 2);
		decorator.addTree(GOTTreeType.ARAMANT, 5);
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
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterBraavos;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BRAAVOS;
	}
}