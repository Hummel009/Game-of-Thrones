package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornCity;

public class GOTBiomeIronbornFlat extends GOTBiomeIronborn {
	public GOTBiomeIronbornFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureIronbornCity village = new GOTStructureIronbornCity(this, 0.0f);
		village.affix(GOTWaypoint.Pebbleton);
		decorator.addVillage(village);
		GOTStructureIronbornCity castle = new GOTStructureIronbornCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Blacktyde);
		castle.affix(GOTWaypoint.Volmark);
		castle.affix(GOTWaypoint.LonelyLight);
		castle.affix(GOTWaypoint.CrowSpikeKeep);
		castle.affix(GOTWaypoint.Downdelving);
		castle.affix(GOTWaypoint.CorpseLake);
		castle.affix(GOTWaypoint.SparrCastle);
		castle.affix(GOTWaypoint.Sunderly);
		castle.affix(GOTWaypoint.Saltcliffe);
		castle.affix(GOTWaypoint.SealskinPoint);
		castle.affix(GOTWaypoint.Orkwood);
		castle.affix(GOTWaypoint.DrummCastle);
		castle.affix(GOTWaypoint.Stonehouse);
		castle.affix(GOTWaypoint.GreyGarden);
		castle.affix(GOTWaypoint.TenTowers);
		castle.affix(GOTWaypoint.HarlawHall);
		castle.affix(GOTWaypoint.HarridanHill);
		castle.affix(GOTWaypoint.StonetreeCastle);
		castle.affix(GOTWaypoint.IronHolt);
		castle.affix(GOTWaypoint.Pyke);
		castle.affix(GOTWaypoint.Blackhaven);
		castle.affix(GOTWaypoint.TawneyCastle);
		castle.affix(GOTWaypoint.Shatterstone);
		castle.affix(GOTWaypoint.MyreCastle);
		decorator.addVillage(castle);
		GOTStructureTower towerGen = new GOTStructureTower(this, 0.0f);
		towerGen.affix(GOTWaypoint.TowerOfGlimmering);
		decorator.addVillage(towerGen);
		GOTStructureIronbornCity town = new GOTStructureIronbornCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Lordsport);
		town.affix(GOTWaypoint.RedHaven);
		decorator.addVillage(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}