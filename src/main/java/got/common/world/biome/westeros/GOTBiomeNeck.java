package got.common.world.biome.westeros;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeNeck extends GOTBiome {
	public GOTBiomeNeck(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityMidges.class, 10, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySwan.class, 2, 1, 2));
		clearBiomeVariants();
		variantChance = 1.0f;
		addBiomeVariant(GOTBiomeVariant.SWAMP_LOWLAND);
		decorator.sandPerChunk = 0;
		decorator.clayPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 2;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.flowersPerChunk = 0;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 2;
		decorator.dryReedChance = 1.0f;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WILLOW, 100);
		registerDefaultFlowers();

		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(1).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNeck;
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
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
