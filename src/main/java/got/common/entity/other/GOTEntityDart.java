package got.common.entity.other;

import got.common.item.other.GOTItemDart;
import got.common.item.weapon.GOTItemDagger;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityDart extends GOTEntityProjectileBase {
	public static float DEF_DART_DAMAGE = 1.0f;
	public float dartDamageFactor = 1.0f;

	public GOTEntityDart(World world) {
		super(world);
	}

	public GOTEntityDart(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world, entityliving, target, item, charge, inaccuracy);
	}

	public GOTEntityDart(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world, entityliving, item, charge);
	}

	public GOTEntityDart(World world, ItemStack item, double d, double d1, double d2) {
		super(world, item, d, d1, d2);
	}

	@Override
	public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
		float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		return speed * dartDamageFactor;
	}

	@Override
	public float getKnockbackFactor() {
		return 0.5f;
	}

	@Override
	public int maxTicksInGround() {
		return 1200;
	}

	@Override
	public void onCollideWithTarget(Entity entity) {
		Item item;
		ItemStack itemstack;
		if (!worldObj.isRemote && entity instanceof EntityLivingBase && (itemstack = getProjectileItem()) != null && (item = itemstack.getItem()) instanceof GOTItemDart && ((GOTItemDart) item).isPoisoned) {
			GOTItemDagger.applyStandardPoison((EntityLivingBase) entity);
		}
		super.onCollideWithTarget(entity);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		dartDamageFactor = nbt.getFloat("DartDamage");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("DartDamage", dartDamageFactor);
	}
}
