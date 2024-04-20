package got.common.entity.other;

import got.common.item.weapon.GOTItemThrowingAxe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityThrowingAxe extends GOTEntityProjectileBase {
	public int axeRotation;

	public GOTEntityThrowingAxe(World world) {
		super(world);
	}

	public GOTEntityThrowingAxe(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world, entityliving, target, item, charge, inaccuracy);
	}

	public GOTEntityThrowingAxe(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world, entityliving, item, charge);
	}

	public GOTEntityThrowingAxe(World world, ItemStack item, double d, double d1, double d2) {
		super(world, item, d, d1, d2);
	}

	@Override
	public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
		if (!isThrowingAxe()) {
			return 0.0F;
		}
		float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		float damage = ((GOTItemThrowingAxe) itemstack.getItem()).getRangedDamageMultiplier(itemstack, shootingEntity, entity);
		return speed * damage;
	}

	public boolean isThrowingAxe() {
		Item item = getProjectileItem().getItem();
		return item instanceof GOTItemThrowingAxe;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!inGround) {
			axeRotation++;
			if (axeRotation > 9) {
				axeRotation = 0;
			}
			rotationPitch = axeRotation / 9.0F * 360.0F;
		}
		if (!isThrowingAxe()) {
			setDead();
		}
	}
}