package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.fixed.GOTStructureVictarionLanding;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeNeck extends GOTBiome {
	public GOTBiomeNeck(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityMidges.class, 10, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySwan.class, 20, 4, 8));
		clearBiomeVariants();
		variantChance = 1.0f;
		addBiomeVariantSet(GOTBiomeVariant.SET_SWAMP);
		decorator.sandPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 5;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WILLOW, 100);
		registerSwampFlowers();
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(container1);
		GOTStructureNorthCity castle = new GOTStructureNorthCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.MoatKailin);
		decorator.addVillage(castle);
		GOTStructureNorthCity village = new GOTStructureNorthCity(this, 0.0f);
		village.affix(GOTWaypoint.GreywaterWatch);
		decorator.addVillage(village);
		GOTStructureVictarionLanding camp = new GOTStructureVictarionLanding(this, 0.0f);
		camp.affix(GOTWaypoint.VictarionLanding);
		decorator.addVillage(camp);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_NECK;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("neck");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
