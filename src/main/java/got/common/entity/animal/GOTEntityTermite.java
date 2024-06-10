package got.common.entity.animal;

import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityTermite extends EntityMob implements GOTBiome.ImmuneToHeat {
	private int fuseTime;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTermite(World world) {
		super(world);
		setSize(0.4f, 0.4f);
		renderDistanceWeight = 2.0;
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, GOTEntityNPC.class, 0, true));
		experienceValue = 2;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return true;
	}

	@Override
	public boolean canDespawn() {
		return false;
	}

	private void explode() {
		if (!worldObj.isRemote) {
			float explosionSize = 2.0f;
			worldObj.createExplosion(this, posX, posY, posZ, explosionSize, GOT.canGrief(worldObj));
			setDead();
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public String getDeathSound() {
		return "mob.silverfish.kill";
	}

	@Override
	public String getHurtSound() {
		return "mob.silverfish.hit";
	}

	@Override
	public String getLivingSound() {
		return "mob.silverfish.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killTermite);
			dropItem(GOTItems.termite, 1);
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!worldObj.isRemote) {
			EntityLivingBase target = getAttackTarget();
			if (target == null) {
				--fuseTime;
			} else {
				float dist = getDistanceToEntity(target);
				if (dist < 3.0f) {
					if (fuseTime == 0) {
						worldObj.playSoundAtEntity(this, "creeper.primed", 1.0f, 0.5f);
					}
					++fuseTime;
					if (fuseTime >= 20) {
						explode();
					}
				} else {
					--fuseTime;
				}
			}
			int maxFuseTime = 20;
			fuseTime = Math.min(Math.max(fuseTime, 0), maxFuseTime);
		}
	}
}