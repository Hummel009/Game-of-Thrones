package got.common.world.genlayer;

import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTIntCache {
	private static final GOTIntCache SERVER = new GOTIntCache();
	private static final GOTIntCache CLIENT = new GOTIntCache();

	private final Collection<int[]> inUseSmallArrays = new ArrayList<>();
	private final Collection<int[]> inUseLargeArrays = new ArrayList<>();
	private final List<int[]> freeSmallArrays = new ArrayList<>();
	private final List<int[]> freeLargeArrays = new ArrayList<>();
	private int intCacheSize = 256;

	public static GOTIntCache get(World world) {
		if (!world.isRemote) {
			return SERVER;
		}
		return CLIENT;
	}

	public int[] getIntArray(int size) {
		if (size <= 256) {
			if (freeSmallArrays.isEmpty()) {
				int[] ints = new int[256];
				inUseSmallArrays.add(ints);
				return ints;
			}
			int[] ints = freeSmallArrays.remove(freeSmallArrays.size() - 1);
			inUseSmallArrays.add(ints);
			return ints;
		}
		if (size > intCacheSize) {
			intCacheSize = size;
			freeLargeArrays.clear();
			inUseLargeArrays.clear();
			int[] ints = new int[intCacheSize];
			inUseLargeArrays.add(ints);
			return ints;
		}
		if (freeLargeArrays.isEmpty()) {
			int[] ints = new int[intCacheSize];
			inUseLargeArrays.add(ints);
			return ints;
		}
		int[] ints = freeLargeArrays.remove(freeLargeArrays.size() - 1);
		inUseLargeArrays.add(ints);
		return ints;
	}

	public void resetIntCache() {
		if (!freeLargeArrays.isEmpty()) {
			freeLargeArrays.remove(freeLargeArrays.size() - 1);
		}
		if (!freeSmallArrays.isEmpty()) {
			freeSmallArrays.remove(freeSmallArrays.size() - 1);
		}
		freeLargeArrays.addAll(inUseLargeArrays);
		freeSmallArrays.addAll(inUseSmallArrays);
		inUseLargeArrays.clear();
		inUseSmallArrays.clear();
	}
}