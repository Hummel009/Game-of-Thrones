package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeLhazar extends GOTBiomeEssosBase {
	public GOTBiomeLhazar(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		invasionSpawns.addInvasion(GOTInvasions.DOTHRAKI, GOTEventSpawner.EventChance.COMMON);

		decorator.addSettlement(new GOTStructureLhazarSettlement(this, 1.0f));

		setupRuinedStructures(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LHAZAR_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LHAZAR_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		biomeWaypoints = GOTWaypoint.Region.LHAZAR;
		biomeAchievement = GOTAchievement.enterLhazar;
		banditEntityClass = GOTEntityDarkSkinBandit.class;
	}
}