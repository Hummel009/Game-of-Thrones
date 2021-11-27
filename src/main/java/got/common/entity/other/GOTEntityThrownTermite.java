package got.common.entity.other;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityThrownTermite extends EntityThrowable {
	public GOTEntityThrownTermite(World world) {
		super(world);
	}

	public GOTEntityThrownTermite(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public GOTEntityThrownTermite(World world, EntityLivingBase throwingEntity) {
		super(world, throwingEntity);
	}

	@Override
	public void onImpact(MovingObjectPosition movingobjectposition) {
		if (!worldObj.isRemote) {
			worldObj.createExplosion(this, posX, posY, posZ, 2.0f, true);
			setDead();
		}
	}
}
