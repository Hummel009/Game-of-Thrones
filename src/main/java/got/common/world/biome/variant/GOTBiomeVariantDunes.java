package got.common.world.biome.variant;

import got.common.util.GOTFunctions;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class GOTBiomeVariantDunes extends GOTBiomeVariant {
	public static NoiseGeneratorPerlin duneWaveNoise = new NoiseGeneratorPerlin(new Random(305620668206968L), 1);
	public static NoiseGeneratorPerlin duneHeightNoise = new NoiseGeneratorPerlin(new Random(5729475867259682L), 1);

	public GOTBiomeVariantDunes(int i, String s) {
		super(i, s, GOTBiomeVariant.VariantScale.SMALL);
		disableSettlements();
	}

	public int getDuneHeightAt(int i, int k) {
		double d1 = duneWaveNoise.func_151601_a(i * 0.02, k * 0.02);
		double d2 = duneWaveNoise.func_151601_a(i * 0.7, k * 0.7);
		double d3 = d1 * 0.9 + d2 * 0.1;
		d3 = MathHelper.clamp_double(d3, -1.0, 1.0);
		int maxDuneHeight = 12;
		return Math.round(GOTFunctions.normalisedSin((i + (float) (d3 * 15.0)) * 0.09f) * maxDuneHeight);
	}

	@Override
	public float getHeightBoostAt(int i, int k) {
		return getDuneHeightAt(i, k) / 22.0f;
	}
}
