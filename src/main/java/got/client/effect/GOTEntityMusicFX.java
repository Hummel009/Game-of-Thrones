package got.client.effect;

import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.world.World;

public class GOTEntityMusicFX extends EntityNoteFX {
	public double noteMoveX;
	public double noteMoveY;
	public double noteMoveZ;

	public GOTEntityMusicFX(World world, double d, double d1, double d2, double d3, double d4, double d5, double pitch) {
		super(world, d, d1, d2, pitch, 0.0, 0.0);
		noteMoveX = d3;
		noteMoveY = d4;
		noteMoveZ = d5;
		particleMaxAge = 8 + rand.nextInt(20);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		double decel = 0.98;
		noteMoveX *= decel;
		noteMoveY *= decel;
		noteMoveZ *= decel;
		motionX = noteMoveX;
		motionY = noteMoveY;
		motionZ = noteMoveZ;
	}
}
