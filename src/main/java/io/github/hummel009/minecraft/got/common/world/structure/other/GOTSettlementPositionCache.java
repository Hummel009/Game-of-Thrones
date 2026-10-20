package io.github.hummel009.minecraft.got.common.world.structure.other;

import java.util.HashMap;
import java.util.Map;

public class GOTSettlementPositionCache {
	private static final int MAX_SIZE = 20000;

	private final Map<Long, LocationInfo> cacheMap = new HashMap<>();

	private static long key(int chunkX, int chunkZ) {
		return (long) chunkX << 32 | chunkZ & 0xFFFFFFFFL;
	}

	private void clearCache() {
		cacheMap.clear();
	}

	public LocationInfo getLocationAt(int chunkX, int chunkZ) {
		return cacheMap.get(key(chunkX, chunkZ));
	}

	public LocationInfo markResult(int chunkX, int chunkZ, LocationInfo result) {
		if (cacheMap.size() >= MAX_SIZE) {
			clearCache();
		}
		cacheMap.put(key(chunkX, chunkZ), result);
		return result;
	}
}