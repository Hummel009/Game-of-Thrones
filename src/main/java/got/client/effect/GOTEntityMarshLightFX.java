package got.client.effect;

import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.world.World;

public class GOTEntityMarshLightFX extends EntityFlameFX {
	public GOTEntityMarshLightFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		motionX = d3;
		motionY = d4;
		motionZ = d5;
		setParticleTextureIndex(49);
		particleMaxAge = 40 + rand.nextInt(20);
		particleGreen = particleBlue = 0.75f + rand.nextFloat() * 0.25f;
		particleRed = particleBlue;
	}
}
