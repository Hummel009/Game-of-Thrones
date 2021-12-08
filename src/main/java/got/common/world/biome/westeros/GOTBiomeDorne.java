package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.other.GOTEntityBanditEssos;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeEssos;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.dorne.GOTStructureDorneCity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeDorne extends GOTBiomeEssos {
	public GOTBiomeDorne(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWildBoar.class, 10, 2, 4));
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
		this.addBiomeVariant(GOTBiomeVariant.VINEYARD);

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[1];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RED_SCORPION, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c4);

		decorator.affix(new GOTStructureDorneCity(this, 1.0f));

		GOTStructureDorneCity castle = new GOTStructureDorneCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Starfall, 0, -1);
		castle.affix(GOTWaypoint.HighHermitage);
		castle.affix(GOTWaypoint.Blackmont);
		castle.affix(GOTWaypoint.Kingsgrave, -1, 0);
		castle.affix(GOTWaypoint.SkyReach, 0, 1);
		castle.affix(GOTWaypoint.Yronwood, 1, 0);
		castle.affix(GOTWaypoint.Wyl, 0, -1);
		castle.affix(GOTWaypoint.Vaith);
		castle.affix(GOTWaypoint.Saltshore);
		castle.affix(GOTWaypoint.Godsgrace);
		castle.affix(GOTWaypoint.Tor);
		castle.affix(GOTWaypoint.Hellholt);
		castle.affix(GOTWaypoint.GhostHill);
		castle.affix(GOTWaypoint.Spottswood);
		castle.affix(GOTWaypoint.WaterGardens);
		castle.affix(GOTWaypoint.Lemonwood);
		decorator.affix(castle);

		GOTStructureDorneCity town = new GOTStructureDorneCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.GhastonGrey);
		town.affix(GOTWaypoint.Sunspear);
		town.affix(GOTWaypoint.PlankyTown);
		decorator.affix(town);

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		setBanditEntityClass(GOTEntityBanditEssos.class);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_DORNE;
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
