package got.common.world.structure.sothoryos.sothoryos;

import got.common.GOTDimension;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosJungle;
import got.common.world.structure.other.GOTVillagePositionCache;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GOTStructureSothoryosPyramidMapgen extends MapGenStructure {
	public static List spawnBiomes;
	public static int minDist = 12;
	public static int separation = 24;
	public int spawnChance = 10;

	public static void register() {
		MapGenStructureIO.registerStructure(GOTStructureSothoryosPyramidStart.class, "GOT.TPyr");
		MapGenStructureIO.func_143031_a(GOTStructureSothoryosPyramidComponent.class, "GOT.TPyr.Pyramid");
	}

	public static void setupSpawnBiomes() {
		if (spawnBiomes == null) {
			spawnBiomes = new ArrayList<>();
			for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
				boolean flag = biome instanceof GOTBiomeSothoryosJungle;
				if (!flag) {
					continue;
				}
				spawnBiomes.add(biome);
			}
		}
	}

	@Override
	public boolean canSpawnStructureAtCoords(int i, int k) {
		GOTWorldChunkManager worldChunkMgr = (GOTWorldChunkManager) worldObj.getWorldChunkManager();
		GOTVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(i, k);
		if (cacheLocation != null) {
			return cacheLocation.isPresent();
		}
		setupSpawnBiomes();
		int i2 = MathHelper.floor_double((double) i / separation);
		int k2 = MathHelper.floor_double((double) k / separation);
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
		return new GOTStructureSothoryosPyramidStart(worldObj, rand, i, j);
	}
}
