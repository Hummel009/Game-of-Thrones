package got.common.util;

import got.common.world.biome.GOTBiome;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTCrashHandler {
	private GOTCrashHandler() {
	}

	public static BiomeGenBase getBiomeGenForCoords(IBlockAccess world, int i, int k) {
		try {
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			if (biome == null) {
				return GOTBiome.ocean;
			}
			return biome;
		} catch (Exception e) {
			return GOTBiome.ocean;
		}
	}

	@SuppressWarnings("TypeMayBeWeakened")
	public static BiomeGenBase getBiomeGenForCoords(World world, int i, int k) {
		try {
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			if (biome == null) {
				return GOTBiome.ocean;
			}
			return biome;
		} catch (Exception e) {
			return GOTBiome.ocean;
		}
	}

	public static BiomeGenBase getBiomeGenForCoords(WorldProvider world, int i, int k) {
		try {
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			if (biome == null) {
				return GOTBiome.ocean;
			}
			return biome;
		} catch (Exception e) {
			return GOTBiome.ocean;
		}
	}
}