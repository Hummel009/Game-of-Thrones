package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeMossovyForest extends GOTBiomeMossovy {
	public GOTBiomeMossovyForest(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(10);
		decorator.clearSettlements();
		npcSpawnList.clear();
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_MILITARY, 10).setSpawnChance(SPAWN * 3));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_WEREWOLF, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c1);
		invasionSpawns.clearInvasions();
	}
}