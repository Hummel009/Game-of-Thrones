package got.common.world.structure.essos.ghiscar;

import java.util.*;

import got.common.GOTDimension;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeGhiscar;
import got.common.world.structure.other.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.*;

public class GOTStructureGhiscarPyramidMapgen extends MapGenStructure {
	public static List spawnBiomes;
	public static int minDist;
	public static int separation;
	static {
		minDist = 12;
		separation = 24;
	}

	public int spawnChance = 10;

	@Override
	public boolean canSpawnStructureAtCoords(int i, int k) {
		GOTWorldChunkManager worldChunkMgr = (GOTWorldChunkManager) worldObj.getWorldChunkManager();
		GOTVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(i, k);
		if (cacheLocation != null) {
			return cacheLocation.isPresent();
		}
		GOTStructureGhiscarPyramidMapgen.setupSpawnBiomes();
		int i2 = MathHelper.floor_double((double) i / (double) separation);
		int k2 = MathHelper.floor_double((double) k / (double) separation);
		Random dRand = worldObj.setRandomSeed(i2, k2, 190169976);
		i2 *= separation;
		k2 *= separation;
		i2 += dRand.nextInt(separation - minDist + 1);
		if (i == i2 && k == (k2 += dRand.nextInt(separation - minDist + 1))) {
			int i1 = i * 16 + 8;
			int k1 = k * 16 + 8;
			if (worldObj.getWorldChunkManager().areBiomesViable(i1, k1, 0, spawnBiomes) && rand.nextInt(spawnChance) == 0) {
				return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
			}
		}
		return cache.markResult(i, k, LocationInfo.NONE_HERE).isPresent();
	}

	@Override
	public String func_143025_a() {
		return "GOT.TPyr";
	}

	@Override
	public StructureStart getStructureStart(int i, int j) {
		return new GOTStructureGhiscarPyramidStart(worldObj, rand, i, j);
	}

	public static void register() {
		MapGenStructureIO.registerStructure(GOTStructureGhiscarPyramidStart.class, "GOT.TPyr");
		MapGenStructureIO.func_143031_a(GOTStructureGhiscarPyramidComponent.class, "GOT.TPyr.Pyramid");
	}

	public static void setupSpawnBiomes() {
		if (spawnBiomes == null) {
			spawnBiomes = new ArrayList();
			for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
				boolean flag = false;
				if (biome instanceof GOTBiomeGhiscar) {
					flag = true;
				}
				if (!flag) {
					continue;
				}
				spawnBiomes.add(biome);
			}
		}
	}
}
