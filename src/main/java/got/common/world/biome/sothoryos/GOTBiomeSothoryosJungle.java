package got.common.world.biome.sothoryos;

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
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureStoneRuin;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeSothoryosJungle extends GOTBiome {
	public GOTBiomeSothoryosJungle(int i, boolean major) {
		super(i, major);
		topBlock = GOTRegistry.mudGrass;
		fillerBlock = GOTRegistry.mud;
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityFlamingo.class, 10, 4, 4));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 10, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 15, 4, 4));
		ArrayList<SpawnListContainer> c = new ArrayList<>();
		c.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c);
		ArrayList<SpawnListContainer> c8 = new ArrayList<>();
		c8.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 1).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(1).add(c8);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		decorator.addGem(new WorldGenMinable(GOTRegistry.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
		decorator.treesPerChunk = 40;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
		decorator.canePerChunk = 5;
		decorator.cornPerChunk = 10;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		registerJungleFlowers();
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		setDarkUnreliable();
		decorator.addVillage(new GOTStructureSothoryosVillage(this, 1.0f));
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinSothoryos(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosJungle;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosJungle");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.SOTHORYOS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.SOTHORYOS;
	}
}