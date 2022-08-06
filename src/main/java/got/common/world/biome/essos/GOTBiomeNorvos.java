package got.common.world.biome.essos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.norvos.GOTStructureNorvosCity;

public class GOTBiomeNorvos extends GOTBiomeEssos {
	public GOTBiomeNorvos(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);

		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORVOS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);

		decorator.addVillage(new GOTStructureNorvosCity(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.VOLANTIS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.DOTHRAKI, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorvos;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("norvos");
	}

}
