package got.common.entity.other;

import got.common.database.GOTRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityConker extends EntityThrowable {
	public GOTEntityConker(World world) {
		super(world);
	}

	public GOTEntityConker(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public GOTEntityConker(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	@Override
	public float func_70182_d() {
		return 1.0f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.04f;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (m.entityHit != null) {
			m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1.0f);
		}
		if (!worldObj.isRemote) {
			entityDropItem(new ItemStack(GOTRegistry.chestnut), 0.0f);
			setDead();
		}
	}
}
