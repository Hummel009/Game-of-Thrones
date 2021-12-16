package got.common.world.biome.westeros;

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
import got.common.world.structure.westeros.common.GOTStructureWesterosObelisk;
import got.common.world.structure.westeros.hillmen.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeArrynMountainsFoothills extends GOTBiome {
	public GOTBiomeArrynMountainsFoothills(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 8, 1, 2));

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_CIVILIAN, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		addBiomeVariantSet(GOTBiomeVariant.SET_MOUNTAINS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LARCH, 0.4f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_PINE, 0.4f);
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.SPRUCE, 500);
		decorator.addTree(GOTTreeType.OAK_TALL, 200);
		decorator.addTree(GOTTreeType.OAK_LARGE, 20);
		decorator.addTree(GOTTreeType.CHESTNUT, 50);
		decorator.addTree(GOTTreeType.CHESTNUT_LARGE, 10);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		registerForestFlowers();

		decorator.addVillage(new GOTStructureHillmanVillage(this, 1.0f));
		decorator.addRandomStructure(new GOTStructureHillmanFort(false), 500);
		decorator.addRandomStructure(new GOTStructureWesterosObelisk(false), 1000);
		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.STONE(1, 4), 400);

	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_HILL_TRIBES;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("arrynMountainsFoothills");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ARRYN;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.75f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
