package got.common.entity.other;

import got.common.item.weapon.GOTItemCrossbowBolt;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityCrossbowBolt extends GOTEntityProjectileBase {
	private double boltDamageFactor = 2.0;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrossbowBolt(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrossbowBolt(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world, entityliving, target, item, charge, inaccuracy);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrossbowBolt(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world, entityliving, item, charge);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrossbowBolt(World world, ItemStack item, double d, double d1, double d2) {
		super(world, item, d, d1, d2);
	}

	@Override
	public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
		float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		return speed * 2.0f * (float) boltDamageFactor;
	}

	@Override
	public int maxTicksInGround() {
		return 1200;
	}

	@Override
	public void onCollideWithTarget(Entity entity) {
		Item item;
		ItemStack itemstack;
		if (!worldObj.isRemote && entity instanceof EntityLivingBase && (itemstack = getProjectileItem()) != null && (item = itemstack.getItem()) instanceof GOTItemCrossbowBolt && ((GOTItemCrossbowBolt) item).isPoisoned()) {
			GOTItemSword.applyStandardPoison((EntityLivingBase) entity);
		}
		super.onCollideWithTarget(entity);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("boltDamageFactor")) {
			boltDamageFactor = nbt.getDouble("boltDamageFactor");
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setDouble("boltDamageFactor", boltDamageFactor);
	}

	public double getBoltDamageFactor() {
		return boltDamageFactor;
	}

	public void setBoltDamageFactor(double boltDamageFactor) {
		this.boltDamageFactor = boltDamageFactor;
	}
}