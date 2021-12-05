package got.common.world.biome.westeros;

import java.util.Random;

import com.google.common.math.IntMath;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.reach.GOTStructureReachCity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class GOTBiomeReach extends GOTBiomeWesteros {
	public GOTBiomeReach(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FIELD_CORN, 0.2f);
		decorator.addTree(GOTTreeType.LEMON, 5);
		decorator.addTree(GOTTreeType.ORANGE, 5);
		decorator.addTree(GOTTreeType.LIME, 5);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 10);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		decorator.affix(new GOTStructureReachCity(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.REACH_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.REACH_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		GOTStructureReachCity village = new GOTStructureReachCity(this, 0.0f);
		village.affix(GOTWaypoint.Oxcross);
		decorator.affix(village);
		GOTStructureReachCity castle = new GOTStructureReachCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.SunHouse, 0, -1);
		castle.affix(GOTWaypoint.GarnetGrove, -1, 0);
		castle.affix(GOTWaypoint.Blackcrown);
		castle.affix(GOTWaypoint.Honeyholt);
		castle.affix(GOTWaypoint.Bandallon);
		castle.affix(GOTWaypoint.BrightwaterKeep);
		castle.affix(GOTWaypoint.Uplands);
		castle.affix(GOTWaypoint.HornHill);
		castle.affix(GOTWaypoint.Whitegrove, -1, 0);
		castle.affix(GOTWaypoint.Dunstonbury);
		castle.affix(GOTWaypoint.Southshield);
		castle.affix(GOTWaypoint.Greenshield);
		castle.affix(GOTWaypoint.Grimston);
		castle.affix(GOTWaypoint.HewettTown, 0, -1);
		castle.affix(GOTWaypoint.Appleton, 0, -1);
		castle.affix(GOTWaypoint.OldOak, -1, 0);
		castle.affix(GOTWaypoint.RedLake);
		castle.affix(GOTWaypoint.Coldmoat);
		castle.affix(GOTWaypoint.Goldengrove);
		castle.affix(GOTWaypoint.Holyhall);
		castle.affix(GOTWaypoint.DarkDell);
		castle.affix(GOTWaypoint.Hammerhal);
		castle.affix(GOTWaypoint.IvyHall);
		castle.affix(GOTWaypoint.Ring);
		castle.affix(GOTWaypoint.CiderHall);
		castle.affix(GOTWaypoint.Longtable);
		castle.affix(GOTWaypoint.NewBarrel);
		castle.affix(GOTWaypoint.GrassyVale);
		castle.affix(GOTWaypoint.Ashford, 0, 1);
		castle.affix(GOTWaypoint.Bitterbridge, 0, 1);
		decorator.affix(castle);
		GOTStructureReachCity town = new GOTStructureReachCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.StarfishHarbor);
		town.affix(GOTWaypoint.Vinetown);
		town.affix(GOTWaypoint.Ryamsport);
		town.affix(GOTWaypoint.HewettTown, 0, 1);
		town.affix(GOTWaypoint.Ashford, 0, -1);
		town.affix(GOTWaypoint.Appleton, 0, 1, 2);
		town.affix(GOTWaypoint.Tumbleton, 0, -1);
		town.affix(GOTWaypoint.Smithyton, 0, 1, 2);
		town.affix(GOTWaypoint.Oldtown, -1, 0, 3);
		decorator.affix(town);
		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.Standfast);
		tower.affix(GOTWaypoint.ThreeTowers, -1, 0, 1);
		tower.affix(GOTWaypoint.ThreeTowers, -1, -1, 1);
		tower.affix(GOTWaypoint.ThreeTowers, -1, 1, 1);
		tower.affix(GOTWaypoint.HightowerLitehouse);
		decorator.affix(tower);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int j1;
		int i1;
		super.decorate(world, random, i, k);
		if (random.nextInt(6) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(GOTRegistry.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
		double d2 = biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.1) {
			int minHeight = height - 8 - random.nextInt(6);
			for (int j = height - 1; j >= minHeight; --j) {
				int index = xzIndex * ySize + j;
				Block block = blocks[index];
				if (block != Blocks.stone) {
					continue;
				}
				blocks[index] = Blocks.sandstone;
				meta[index] = 0;
			}
		}
		boolean kukuruza;
		kukuruza = variant == GOTBiomeVariant.FIELD_CORN;
		if (kukuruza && !GOTRoads.isRoadAt(i, k)) {
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (block == null || !block.isOpaqueCube() || above != null && above.getMaterial() != Material.air) {
					continue;
				}
				IntMath.mod(i, 6);
				IntMath.mod(i, 24);
				IntMath.mod(k, 32);
				IntMath.mod(k, 64);
				double d;
				blocks[index] = Blocks.farmland;
				meta[index] = 0;
				int h = 2;
				if (biomeTerrainNoise.func_151601_a(i, k) > 0.0) {
					++h;
				}
				biomeTerrainNoise.func_151601_a(i * (d = 0.01), k * d);
				Block vineBlock = GOTRegistry.cornStalk;
				for (int j1 = 1; j1 <= h; ++j1) {
					blocks[index + j1] = vineBlock;
					meta[index + j1] = 8;
				}
				break;
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_REACH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("reach");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.REACH;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
