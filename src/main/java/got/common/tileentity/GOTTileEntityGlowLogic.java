package got.common.tileentity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class GOTTileEntityGlowLogic {
	private static final float[] LIGHT_VALUE_SQRTS = new float[16];
	private static final int MAX_GLOW_TICK = 120;

	static {
		for (int i = 0; i <= 15; ++i) {
			LIGHT_VALUE_SQRTS[i] = MathHelper.sqrt_float(i / 15.0f);
		}
	}

	private int glowTick;
	private int prevGlowTick;
	private int playerRange = 8;

	public float getGlowBrightness(World world, int i, int j, int k, float tick) {
		float glow = (prevGlowTick + (glowTick - prevGlowTick) * tick) / MAX_GLOW_TICK;
		float fullGlow = 0.7f;
		glow *= fullGlow;
		float sun = world.getSunBrightness(tick);
		float sunNorml = (sun - 0.2F) / 0.8F;
		float night = 1.0F - sunNorml;
		night -= 0.5F;
		if (night < 0.0F) {
			night = 0.0F;
		}
		night *= 2.0F;
		float skylight = LIGHT_VALUE_SQRTS[world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k)];
		return glow * night * skylight;
	}

	public void update(World world, int i, int j, int k) {
		prevGlowTick = glowTick;
		if (world.isRemote) {
			boolean playersNearby = world.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, playerRange) != null;
			if (playersNearby && glowTick < MAX_GLOW_TICK) {
				++glowTick;
			} else if (!playersNearby && glowTick > 0) {
				--glowTick;
			}
		}
	}

	public GOTTileEntityGlowLogic setPlayerRange(int i) {
		playerRange = i;
		return this;
	}
}