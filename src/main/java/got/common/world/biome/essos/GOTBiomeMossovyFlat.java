package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyVillage;

public class GOTBiomeMossovyFlat extends GOTBiomeMossovy {
	public GOTBiomeMossovyFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		npcSpawnList.clear();
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureMossovyVillage capital = new GOTStructureMossovyVillage(this, 0.0f);
		capital.affix(GOTWaypoint.Kadar);
		decorator.affix(capital);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}