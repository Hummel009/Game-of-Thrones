package got.common.tileentity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.*;

public class GOTTileEntityGlowLogic {
	private static float[] lightValueSqrts = new float[16];
	static {
		for (int i = 0; i <= 15; ++i) {
			GOTTileEntityGlowLogic.lightValueSqrts[i] = MathHelper.sqrt_float(i / 15.0f);
		}
	}
	private int glowTick;
	private int prevGlowTick;
	private int maxGlowTick = 120;
	private int playerRange = 8;

	private float fullGlow = 0.7f;

	public float getGlowBrightness(World world, int i, int j, int k, float tick) {
		float glow = (prevGlowTick + (glowTick - prevGlowTick) * tick) / maxGlowTick;
		glow *= fullGlow;
		world.getSunBrightness(tick);
		float night = 0.5f;
		if (night < 0.0f) {
			night = 0.0f;
		}
		float skylight = lightValueSqrts[world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k)];
		return glow * (night *= 2.0f) * skylight;
	}

	public GOTTileEntityGlowLogic setFullGlow(float f) {
		fullGlow = f;
		return this;
	}

	public GOTTileEntityGlowLogic setGlowTime(int i) {
		maxGlowTick = i;
		return this;
	}

	public GOTTileEntityGlowLogic setPlayerRange(int i) {
		playerRange = i;
		return this;
	}

	public void update(World world, int i, int j, int k) {
		prevGlowTick = glowTick;
		if (world.isRemote) {
			boolean playersNearby = world.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, playerRange) != null;
			if (playersNearby && glowTick < maxGlowTick) {
				++glowTick;
			} else if (!playersNearby && glowTick > 0) {
				--glowTick;
			}
		}
	}
}
