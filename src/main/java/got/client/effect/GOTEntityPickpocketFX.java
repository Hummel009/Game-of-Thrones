package got.client.effect;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class GOTEntityPickpocketFX extends EntityFX {
	public float bounciness;
	public double motionBeforeGround;

	public GOTEntityPickpocketFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		motionX = xSpeed;
		motionY = ySpeed;
		motionZ = zSpeed;
		particleGravity = 1.0f;
		particleMaxAge = 30 + rand.nextInt(40);
		noClip = false;
		bounciness = 1.0f;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		updatePickpocketIcon();
		if (!onGround) {
			motionBeforeGround = motionY;
		} else {
			motionY = motionBeforeGround * (-bounciness);
		}
	}

	public void updatePickpocketIcon() {
		setParticleTextureIndex(160 + particleAge / 2 % 8);
	}
}
