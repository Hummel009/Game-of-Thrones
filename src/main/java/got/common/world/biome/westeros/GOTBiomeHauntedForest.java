package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingVillage;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeHauntedForest extends GOTBiome {
	public GOTBiomeHauntedForest(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.grass;
		fillerBlock = Blocks.snow;
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDirewolf.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWoolyRhino.class, 4, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityMammoth.class, 2, 1, 1));
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		decorator.treesPerChunk = 10;

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c5 = new SpawnListContainer[1];
		c5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c5);

		SpawnListContainer[] c6 = new SpawnListContainer[1];
		c6[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_GUARDIAN, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c6);

		decorator.addVillage(new GOTStructureWildlingVillage(this, 0.1f));

		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterHauntedForest;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("hauntedForest");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ICE;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int getWallTop() {
		return 150;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}