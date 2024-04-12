package got.common.entity.other;

import got.common.GOTDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntitySnowball extends GOTEntityFireball {
	public GOTEntitySnowball(World world) {
		super(world);
		setSize(0.3125F, 0.3125F);
	}

	public GOTEntitySnowball(World world, double d1, double d2, double d3, double d4, double d5, double d6) {
		super(world, d1, d2, d3, d4, d5, d6);
		setSize(0.3125F, 0.3125F);
	}

	public GOTEntitySnowball(World world, EntityLivingBase entity, double d1, double d2, double d3) {
		super(world, entity, d1, d2, d3);
		setSize(0.3125F, 0.3125F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damage, float f) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public void onImpact(MovingObjectPosition pos) {
		if (!worldObj.isRemote) {
			if (pos.entityHit instanceof EntityPlayerMP) {
				pos.entityHit.attackEntityFrom(GOTDamage.FROST, 6.0F);
				GOTDamage.doFrostDamage((EntityPlayerMP) pos.entityHit);
			}
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		setFire(0);
	}
}
