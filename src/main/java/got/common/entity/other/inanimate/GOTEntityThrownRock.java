package got.common.entity.other.inanimate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityThrownRock extends EntityThrowable {
	private int rockRotation;
	private float damage;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityThrownRock(World world) {
		super(world);
		setSize(4.0f, 4.0f);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityThrownRock(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		setSize(4.0f, 4.0f);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityThrownRock(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
		setSize(4.0f, 4.0f);
	}

	@Override
	public float func_70182_d() {
		return 0.75f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.1f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			for (int l = 0; l < 32; ++l) {
				GOT.proxy.spawnParticle("largeStone", posX + rand.nextGaussian() * width, posY + rand.nextDouble() * height, posZ + rand.nextGaussian() * width, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (!worldObj.isRemote) {
			boolean flag = m.entityHit != null && m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), damage);
			if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				flag = true;
			}
			if (flag) {
				worldObj.setEntityState(this, (byte) 15);
				int drops = 1 + rand.nextInt(3);
				for (int l = 0; l < drops; ++l) {
					dropItem(Item.getItemFromBlock(Blocks.cobblestone), 1);
				}
				playSound("got:troll.rockSmash", 2.0f, (1.0f + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
				setDead();
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!inGround) {
			++rockRotation;
			if (rockRotation > 60) {
				rockRotation = 0;
			}
			rotationPitch = rockRotation / 60.0f * 360.0f;
			while (rotationPitch - prevRotationPitch < -180.0f) {
				prevRotationPitch -= 360.0f;
			}
			while (rotationPitch - prevRotationPitch >= 180.0f) {
				prevRotationPitch += 360.0f;
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		damage = nbt.getFloat("RockDamage");
	}

	@SuppressWarnings("unused")
	public float getDamage() {
		return damage;
	}

	public void setDamage(float f) {
		damage = f;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("RockDamage", damage);
	}
}