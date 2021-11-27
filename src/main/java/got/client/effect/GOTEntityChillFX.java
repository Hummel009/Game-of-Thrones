package got.client.effect;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityChillFX extends EntitySmokeFX {
	public GOTEntityChillFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleGreen = particleBlue = (MathHelper.randomFloatClamp(rand, 0.8f, 1.0f));
		particleRed = particleBlue;
		setParticleTextureIndex(rand.nextInt(8));
		particleMaxAge *= 6;
		float blue = rand.nextFloat() * 0.25f;
		particleRed *= 1.0f - blue;
		particleGreen *= 1.0f - blue;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++particleAge;
		if (particleAge >= particleMaxAge) {
			setDead();
		}
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.96;
		motionY *= 0.96;
		motionZ *= 0.96;
		motionY -= 0.005;
		if (onGround) {
			motionX *= 0.7;
			motionZ *= 0.7;
		}
	}
}
