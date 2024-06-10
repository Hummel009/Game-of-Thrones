package got.common.entity.animal;

import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.util.GOTReflection;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityRhino extends GOTEntityHorse {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityRhino(World world) {
		super(world);
		setSize(1.7f, 1.9f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 20.0, 50.0);
	}

	@Override
	public double clampChildJump(double jump) {
		return MathHelper.clamp_double(jump, 0.2, 0.8);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.12, 0.42);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(2) + rand.nextInt(1 + i);
		for (int k = 0; k < j; ++k) {
			dropItem(GOTItems.rhinoHorn, 1);
		}
		int meat = 3 + rand.nextInt(2);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTItems.rhinoCooked, 1);
			} else {
				dropItem(GOTItems.rhinoRaw, 1);
			}
		}
	}

	@Override
	public String getAngrySoundName() {
		return "got:rhino.say";
	}

	@Override
	public String getDeathSound() {
		return "got:rhino.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		return "got:rhino.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:rhino.say";
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.wheat;
	}

	@Override
	public boolean isMountHostile() {
		return true;
	}

	@Override
	public void onGOTHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		maxHealth *= 1.5;
		maxHealth = Math.max(maxHealth, 40.0);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed * 1.2);
		double jumpStrength = getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue();
		getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength * 0.5);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer && isMountSaddled()) {
			GOTLevelData.getData((EntityPlayer) riddenByEntity).addAchievement(GOTAchievement.rideRhino);
		}
		if (!worldObj.isRemote) {
			if (riddenByEntity instanceof EntityLivingBase) {
				EntityLivingBase rhinoRider = (EntityLivingBase) riddenByEntity;
				float momentum = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				setSprinting(momentum > 0.2f);
				if (momentum >= 0.32f) {
					float strength = momentum * 15.0f;
					Vec3 look = getLookVec();
					float sightWidth = 1.0f;
					double range = 0.5;
					List<? extends Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.contract(1.0, 1.0, 1.0).addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(sightWidth, sightWidth, sightWidth));
					boolean hitAnyEntities = false;
					for (Entity element : list) {
						EntityLiving entityliving;
						EntityLivingBase entity;
						if (!(element instanceof EntityLivingBase) || (entity = (EntityLivingBase) element) == rhinoRider || rhinoRider instanceof EntityPlayer && !GOT.canPlayerAttackEntity((EntityPlayer) rhinoRider, entity, false) || rhinoRider instanceof EntityCreature && !GOT.canNPCAttackEntity((EntityCreature) rhinoRider, entity, false) || !entity.attackEntityFrom(DamageSource.causeMobDamage(this), strength)) {
							continue;
						}
						float knockback = strength * 0.05f;
						entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockback, knockback, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockback);
						hitAnyEntities = true;
						if (!(entity instanceof EntityLiving) || (entityliving = (EntityLiving) entity).getAttackTarget() != this) {
							continue;
						}
						entityliving.getNavigator().clearPathEntity();
						entityliving.setAttackTarget(rhinoRider);
					}
					if (hitAnyEntities) {
						worldObj.playSoundAtEntity(this, "got:giant.ologHai_hammer", 1.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
					}
				}
			} else if (getAttackTarget() != null) {
				float momentum = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				setSprinting(momentum > 0.2f);
			} else {
				setSprinting(false);
			}
		}
	}
}