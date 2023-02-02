package got.common.entity.animal;

import java.util.List;

import got.GOT;
import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.util.GOTReflection;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTEntityWoolyRhino extends GOTEntityHorse implements GOTBiome.ImmuneToFrost {
	public GOTEntityWoolyRhino(World world) {
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
	public EntityAgeable createChild(EntityAgeable entity) {
		return new GOTEntityWoolyRhino(worldObj);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.0, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int j = rand.nextInt(2) + rand.nextInt(1 + i);
		for (int k = 0; k < j; ++k) {
			dropItem(GOTRegistry.rhinoHorn, 1);
		}
		int meat = 3 + rand.nextInt(2);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTRegistry.rhinoCooked, 1);
			} else {
				dropItem(GOTRegistry.rhinoRaw, 1);
			}
			dropItem(GOTRegistry.fur, 1);
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
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed *= 1.2);
		double jumpStrength = getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue();
		getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength *= 0.5);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			if (riddenByEntity instanceof EntityLivingBase) {
				EntityLivingBase rhinoRider = (EntityLivingBase) riddenByEntity;
				float momentum = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				if (momentum > 0.2f) {
					setSprinting(true);
				} else {
					setSprinting(false);
				}
				if (momentum >= 0.32f) {
					float strength = momentum * 15.0f;
					Vec3.createVectorHelper(posX, posY, posZ);
					Vec3 look = getLookVec();
					float sightWidth = 1.0f;
					double range = 0.5;
					List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.contract(1.0, 1.0, 1.0).addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(sightWidth, sightWidth, sightWidth));
					boolean hitAnyEntities = false;
					for (Object element : list) {
						EntityLiving entityliving;
						EntityLivingBase entity;
						Entity obj = (Entity) element;
						if (!(obj instanceof EntityLivingBase) || (entity = (EntityLivingBase) obj) == rhinoRider || rhinoRider instanceof EntityPlayer && !GOT.canPlayerAttackEntity((EntityPlayer) rhinoRider, entity, false) || rhinoRider instanceof EntityCreature && !GOT.canNPCAttackEntity((EntityCreature) rhinoRider, entity, false) || !entity.attackEntityFrom(DamageSource.causeMobDamage(this), strength)) {
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
				if (momentum > 0.2f) {
					setSprinting(true);
				} else {
					setSprinting(false);
				}
			} else {
				setSprinting(false);
			}
		}
	}
}
