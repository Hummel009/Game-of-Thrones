package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.mossovy.GOTStructureMossovySettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeMossovy extends GOTBiomeEssosCold {
	public GOTBiomeMossovy(int i, boolean major) {
		super(i, major);
		biomeVariants.add(GOTBiomeVariant.FOREST_PINE, 0.2f);
		decorator.addSettlement(new GOTStructureMossovySettlement(this, 1.0f));
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_WEREWOLF, 10).setSpawnChance(SPAWN * 3));
		npcSpawnList.newFactionList(10).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterMossovy;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.MOSSOVY;
	}
}