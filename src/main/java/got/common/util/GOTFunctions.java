package got.common.util;

import net.minecraft.util.MathHelper;

public class GOTFunctions {
	public static int[] intRange(int min, int max) {
		int[] indices = new int[max - min + 1];
		for (int i = 0; i < indices.length; ++i) {
			indices[i] = min + i;
		}
		return indices;
	}

	public static float normalisedCos(float t) {
		return (MathHelper.cos(t) + 1.0f) / 2.0f;
	}

	public static float normalisedSin(float t) {
		return (MathHelper.sin(t) + 1.0f) / 2.0f;
	}

	public static float triangleWave(float t, float min, float max, float period) {
		return min + (max - min) * (Math.abs(t % period / period - 0.5f) * 2.0f);
	}
}
