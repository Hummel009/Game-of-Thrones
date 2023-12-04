package got.common.world.biome.variant;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeVariantList {
	public float totalWeight;
	public Collection<VariantBucket> variantList = new ArrayList<>();

	public void add(GOTBiomeVariant v, float f) {
		variantList.add(new VariantBucket(v, totalWeight, totalWeight + f));
		totalWeight += f;
	}

	public void clear() {
		totalWeight = 0.0f;
		variantList.clear();
	}

	public GOTBiomeVariant get(float index) {
		float index1 = index;
		if (index1 < 0.0f) {
			index1 = 0.0f;
		}
		if (index1 >= 1.0f) {
			index1 = 0.9999f;
		}
		float f = index1 * totalWeight;
		for (VariantBucket bucket : variantList) {
			if (f < bucket.min || f >= bucket.max) {
				continue;
			}
			return bucket.variant;
		}
		return null;
	}

	public boolean isEmpty() {
		return totalWeight == 0.0f;
	}

	public static class VariantBucket {
		public GOTBiomeVariant variant;
		public float min;
		public float max;

		public VariantBucket(GOTBiomeVariant v, float f0, float f1) {
			variant = v;
			min = f0;
			max = f1;
		}
	}

}
