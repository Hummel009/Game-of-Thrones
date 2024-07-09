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
import got.common.world.structure.essos.yi_ti.GOTStructureYiTiSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeYiTi extends GOTBiomeEssosBase {
	public GOTBiomeYiTi(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(false);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(false);
		preseter.setupSouthernTrees(false);

		biomeVariants.add(GOTBiomeVariant.FOREST_CHERRY, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LEMON, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIME, 0.2f);

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureYiTiSettlement(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.JOGOS_NHAI, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YI_TI_CONQUEST, 1).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YI_TI_MILITARY, 9).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(8).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_NHAI_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 10).setSpawnChance(CONQUEST_SPAWN / 4));
		npcSpawnList.newFactionList(2).add(c2);

		biomeWaypoints = GOTWaypoint.Region.YI_TI;
		biomeAchievement = GOTAchievement.enterYiTi;
		wallBlock = GOTBezierType.WALL_YI_TI;
		roadBlock = GOTBezierType.PATH_PAVING;
	}
}