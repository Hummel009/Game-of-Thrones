package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.ironborn.*;

public class GOTBiomeIronborn extends GOTBiomeWesteros {
	public GOTBiomeIronborn(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		decorator.affix(new GOTStructureIronbornCity(this, 1.0f));

		GOTStructureIronbornCity village = new GOTStructureIronbornCity(this, 0.0f);
		village.affix(GOTWaypoint.Pebbleton);
		decorator.affix(village);

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
		castle.affix(GOTWaypoint.Hammerhorn);
		decorator.affix(castle);

		GOTStructureIronbornCity town = new GOTStructureIronbornCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Lordsport);
		town.affix(GOTWaypoint.RedHaven);
		decorator.affix(town);

		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.TowerOfGlimmering);
		decorator.affix(tower);

		GOTStructureRuins ruins = new GOTStructureRuins(this, 0.0f);
		ruins.affix(GOTWaypoint.GreyironCastle);
		ruins.affix(GOTWaypoint.HoareKeep);
		ruins.affix(GOTWaypoint.HoareCastle);
		decorator.affix(ruins);

		decorator.addRandomStructure(new GOTStructureIronbornWatchfort(false), 800);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_IRONBORN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("ironborn");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IRONBORN;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
