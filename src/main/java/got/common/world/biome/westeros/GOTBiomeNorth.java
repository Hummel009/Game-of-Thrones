package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;

public class GOTBiomeNorth extends GOTBiomeWesteros {
	public GOTBiomeNorth(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_BIRCH);
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_OAK);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 400);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CIVILIAN, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[2];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		c4[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c4);

		decorator.affix(new GOTStructureNorthCity(this, 1.0f));

		GOTStructureNorthCity castle = new GOTStructureNorthCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.MormontsKeep);
		castle.affix(GOTWaypoint.DeepwoodMotte);
		castle.affix(GOTWaypoint.Karhold);
		castle.affix(GOTWaypoint.Winterfell, 1);
		castle.affix(GOTWaypoint.LastHearth);
		castle.affix(GOTWaypoint.Dreadfort);
		castle.affix(GOTWaypoint.DeepwoodMotte);
		castle.affix(GOTWaypoint.Hornwood);
		castle.affix(GOTWaypoint.BlackPool);
		castle.affix(GOTWaypoint.RamsGate);
		castle.affix(GOTWaypoint.WidowsWatch);
		castle.affix(GOTWaypoint.OldCastle);
		castle.affix(GOTWaypoint.ServinsCastle, -1, 0);
		castle.affix(GOTWaypoint.Ironrath);
		castle.affix(GOTWaypoint.Highpoint);
		castle.affix(GOTWaypoint.TorhensSquare);
		castle.affix(GOTWaypoint.RisvellsCastle, 0, 1);
		castle.affix(GOTWaypoint.RillwaterCrossing);
		castle.affix(GOTWaypoint.FlintsFinger);
		castle.affix(GOTWaypoint.Goldgrass, 0, 1);
		decorator.affix(castle);

		GOTStructureNorthCity stown = new GOTStructureNorthCity(this, 0.0f).setIsSmallTown();
		stown.affix(GOTWaypoint.Barrowtown, 0, 1, 2);
		decorator.affix(stown);

		GOTStructureNorthCity town = new GOTStructureNorthCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.WhiteHarbour, 1);
		decorator.affix(town);

		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.RamseyTower);
		decorator.affix(tower);

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_NORTH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("north");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}
}
