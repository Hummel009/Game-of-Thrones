package got.common.world.biome.westeros;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.north.hillmen.GOTStructureNorthHillmanVillage;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeSkagos extends GOTBiome {
	public GOTBiomeSkagos(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 400);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		decorator.treesPerChunk = 2;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		registerPlainsFlowers();
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWoolyRhino.class, 2, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));

		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_HILLMEN, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		ArrayList<SpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);

		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 4).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);

		decorator.addVillage(new GOTStructureNorthHillmanVillage(this, 1.0f));

		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSkagos;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("skagos");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
