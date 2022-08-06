package got.common.world.biome.westeros;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeEssos;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.dorne.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeDorne extends GOTBiomeEssos {
	public GOTBiomeDorne(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 10, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteOryx.class, 12, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityCamel.class, 2, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 10, 1, 2));
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);

		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);

		ArrayList<SpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);

		ArrayList<SpawnListContainer> c4 = new ArrayList<>();
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.RED_SCORPION, 1).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(1).add(c4);

		decorator.addVillage(new GOTStructureDorneCity(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		setDarkUnreliable();

		decorator.addRandomStructure(new GOTStructureDorneWatchfort(false), 800);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorne;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("dorne");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.DORNE;
	}
}
