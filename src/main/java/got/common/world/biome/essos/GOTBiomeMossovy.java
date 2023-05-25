package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.mossovy.GOTStructureMossovySettlement;

import java.util.ArrayList;

public class GOTBiomeMossovy extends GOTBiomeEssosCold {
	public GOTBiomeMossovy(int i, boolean major) {
		super(i, major);
		addBiomeVariant(GOTBiomeVariant.FOREST_PINE, 0.2f);
		decorator.addSettlement(new GOTStructureMossovySettlement(this, 1.0f));
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.MOSSOVY_WEREWOLF, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterMossovy;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("mossovy");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.MOSSOVY;
	}
}