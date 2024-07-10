package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.qohor.GOTStructureQohorFortress;
import got.common.world.structure.essos.qohor.GOTStructureQohorSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeQohor extends GOTBiomeEssosBase {
	public GOTBiomeQohor(int i, boolean major) {
		super(i, major);
		preseter.setupMideratePlainsView();
		preseter.setupMideratePlainsFlora();
		preseter.setupMideratePlainsFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureQohorSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureQohorFortress(false), 800);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.QOHOR_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.QOHOR_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.QOHOR;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQohor;
	}
}