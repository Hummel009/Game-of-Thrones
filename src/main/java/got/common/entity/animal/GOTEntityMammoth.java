package got.common.entity.animal;

import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.util.GOTReflection;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
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

public class GOTEntityMammoth extends GOTEntityHorse implements GOTBiome.ImmuneToFrost {
	public GOTEntityMammoth(World world) {
		super(world);
		setSize(2.0f, 5.0f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			float attackDamage = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
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
		return new GOTEntityMammoth(worldObj);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.0, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int meat = 7 + rand.nextInt(2);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTRegistry.elephantCooked, 1);
			} else {
				dropItem(GOTRegistry.elephantRaw, 1);
			}
			dropItem(GOTRegistry.fur, 1);
		}
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("got:giant.step", 0.75f, getSoundPitch());
	}

	@Override
	public String getAngrySoundName() {
		return "got:mammoth.grunt";
	}

	@Override
	public String getDeathSound() {
		return "got:mammoth.dying";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		return "got:mammoth.hurt";
	}

	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killMammoth;
	}

	@Override
	public String getLivingSound() {
		return "got:mammoth.grunt";
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() + 2.0;
	}

	@Override
	public float getSoundVolume() {
		return 2.0f;
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
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 2.0;
		motionY /= 2.0;
		motionZ /= 2.0;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		EntityPlayer entityplayer;
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) damagesource.getEntity();
			if (getKillAchievement() != null) {
				GOTLevelData.getData(entityplayer).addAchievement(getKillAchievement());
			}
		}
	}

	@Override
	public void onGOTHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		maxHealth *= 1.5;
		maxHealth = Math.max(maxHealth, 100.0);
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
				setSprinting(momentum > 0.2f);
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
				setSprinting(momentum > 0.2f);
			} else {
				setSprinting(false);
			}
		}
	}
}
