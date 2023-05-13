package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityRegistry;
import got.common.entity.other.GOTEntityUtils;
import got.common.entity.other.GOTRandomSkinEntity;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class GOTEntityBison extends EntityCow implements GOTRandomSkinEntity, GOTBiome.ImmuneToFrost {
	public EntityAIBase attackAI;
	public EntityAIBase panicAI;
	public boolean prevIsChild = true;
	public float bisonWidth;
	public float bisonHeight;

	public GOTEntityBison(World world) {
		super(world);
		bisonWidth = 1.5f;
		bisonHeight = 1.7f;
		setSize(bisonWidth, bisonHeight);
		EntityAITasks.EntityAITaskEntry panic = GOTEntityUtils.removeAITask(this, EntityAIPanic.class);
		assert panic != null;
		tasks.addTask(panic.priority, panic.action);
		panicAI = panic.action;
		attackAI = createBisonAttackAI();
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float f = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		if (flag) {
			float kb = 0.75f;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f, 0.0, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f);
		}
		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity attacker;
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && isChild() && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12.0, 12.0, 12.0));
			for (Object element : list) {
				GOTEntityBison bison;
				Entity entity = (Entity) element;
				if (entity.getClass() != getClass() || (bison = (GOTEntityBison) entity).isChild()) {
					continue;
				}
				bison.setAttackTarget((EntityLivingBase) attacker);
			}
		}
		return flag;
	}

	public EntityAIBase createBisonAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.7, true);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new GOTEntityBison(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hides = 2 + rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hides; ++l) {
			dropItem(Items.leather, 1);
		}
		int meats = 2 + rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meats; ++l) {
			if (isBurning()) {
				dropItem(Items.cooked_beef, 1);
				continue;
			}
			dropItem(Items.beef, 1);
		}
		dropHornItem(flag, i);
	}

	public void dropHornItem(boolean flag, int i) {
		dropItem(GOTRegistry.horn, 1);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) 0);
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "got:bison.hurt";
	}

	@Override
	public String getHurtSound() {
		return "got:bison.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:bison.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTRegistry.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.75f;
	}

	@Override
	public float getSoundVolume() {
		return 1.0f;
	}

	@Override
	public int getTalkInterval() {
		return 200;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (isBisonEnraged()) {
			return false;
		}
		return super.interact(entityplayer);
	}

	public boolean isBisonEnraged() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public void setBisonEnraged(boolean flag) {
		dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			EntityLivingBase target;
			boolean isChild = isChild();
			if (isChild != prevIsChild) {
				EntityAITasks.EntityAITaskEntry taskEntry;
				if (isChild) {
					taskEntry = GOTEntityUtils.removeAITask(this, attackAI.getClass());
					assert taskEntry != null;
					tasks.addTask(taskEntry.priority, panicAI);
				} else {
					taskEntry = GOTEntityUtils.removeAITask(this, panicAI.getClass());
					assert taskEntry != null;
					tasks.addTask(taskEntry.priority, attackAI);
				}
			}
			if (getAttackTarget() != null && (!(target = getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
				setAttackTarget(null);
			}
			if (riddenByEntity instanceof EntityLiving) {
				target = ((EntityLiving) riddenByEntity).getAttackTarget();
				setAttackTarget(target);
			} else if (riddenByEntity instanceof EntityPlayer) {
				setAttackTarget(null);
			}
			setBisonEnraged(getAttackTarget() != null);
		}
		prevIsChild = isChild();
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
