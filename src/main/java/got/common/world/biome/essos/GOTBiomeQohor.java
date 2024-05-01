package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.qohor.GOTStructureQohorFortress;
import got.common.world.structure.essos.qohor.GOTStructureQohorSettlement;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeQohor extends GOTBiomeEssos {
	public GOTBiomeQohor(int i, boolean major) {
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
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
		decorator.addTree(GOTTreeType.OAK_LARGE, 30);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.MAPLE, 100);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 10);
		decorator.addTree(GOTTreeType.MAPLE_PARTY, 2);
		decorator.addSettlement(new GOTStructureQohorSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureQohorFortress(false), 800);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.QOHOR_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQohor;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.QOHOR;
	}
}