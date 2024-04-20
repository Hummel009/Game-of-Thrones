package got.common.world.structure.sothoryos.sothoryos;

import got.common.GOTDimension;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosJungle;
import got.common.world.structure.other.GOTSettlementPositionCache;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GOTStructureSothoryosPyramidMapgen extends MapGenStructure {
	private static List<GOTBiome> spawnBiomes;

	public static void register() {
		MapGenStructureIO.registerStructure(GOTStructureSothoryosPyramidStart.class, "GOT.TPyr");
		MapGenStructureIO.func_143031_a(GOTStructureSothoryosPyramidComponent.class, "GOT.TPyr.Pyramid");
	}

	private static void setupSpawnBiomes() {
		if (spawnBiomes == null) {
			spawnBiomes = new ArrayList<>();
			for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.getBiomeList()) {
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
		GOTSettlementPositionCache cache = worldChunkMgr.getStructureCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(i, k);
		if (cacheLocation != null) {
			return cacheLocation.isPresent();
		}
		setupSpawnBiomes();
		int separation = 24;
		int i2 = MathHelper.floor_double((double) i / separation);
		int k2 = MathHelper.floor_double((double) k / separation);
		Random dRand = worldObj.setRandomSeed(i2, k2, 190169976);
		i2 *= separation;
		k2 *= separation;
		int minDist = 12;
		i2 += dRand.nextInt(separation - minDist + 1);
		if (i == i2 && k == k2 + dRand.nextInt(separation - minDist + 1)) {
			int i1 = i * 16 + 8;
			int k1 = k * 16 + 8;
			int spawnChance = 10;
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
		return new GOTStructureSothoryosPyramidStart(rand, i, j);
	}
}