package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.pentos.GOTStructurePentosCity;

public class GOTBiomePentos extends GOTBiomeEssos {
	public GOTBiomePentos(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);

		SpawnListContainer[] c0 = new SpawnListContainer[1];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		decorator.addVillage(new GOTStructurePentosCity(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.BRAAVOS, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterPentos;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("pentos");
	}
}