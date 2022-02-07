package got.client.effect;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityWhiteSmokeFX extends EntitySmokeFX {
	public GOTEntityWhiteSmokeFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleGreen = particleBlue = MathHelper.randomFloatClamp(rand, 0.6f, 1.0f);
		particleRed = particleBlue;
	}
}
