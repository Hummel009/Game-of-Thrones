package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAILionChase;
import got.common.entity.ai.GOTEntityAIMFMate;
import got.common.item.other.GOTItemLionRug;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public abstract class GOTEntityLionBase extends GOTEntityAnimalMF {
	public EntityAIBase attackAI = new GOTEntityAIAttackOnCollide(this, 1.5, false);
	public EntityAIBase panicAI = new EntityAIPanic(this, 1.5);
	public EntityAIBase targetNearAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
	public int hostileTick;
	public boolean prevIsChild = true;

	protected GOTEntityLionBase(World world) {
		super(world);
		setSize(1.4f, 1.6f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, panicAI);
		tasks.addTask(3, new GOTEntityAIMFMate(this, 1.0));
		tasks.addTask(4, new EntityAITempt(this, 1.4, Items.fish, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.4));
		tasks.addTask(6, new GOTEntityAILionChase(this, 1.5));
		tasks.addTask(7, new EntityAIWander(this, 1.0));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
		tasks.addTask(9, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, targetNearAI);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float f = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity attacker;
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
			if (isChild()) {
				double range = 12.0;
				List<GOTEntityLionBase> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(range, range, range));
				for (GOTEntityLionBase obj : list) {
					GOTEntityLionBase lion;
					Entity entity = obj;
					if (!(entity instanceof GOTEntityLionBase) || (lion = (GOTEntityLionBase) entity).isChild()) {
						continue;
					}
					lion.becomeAngryAt((EntityLivingBase) attacker);
				}
			} else {
				becomeAngryAt((EntityLivingBase) attacker);
			}
		}
		return flag;
	}

	public void becomeAngryAt(EntityLivingBase entity) {
		setAttackTarget(entity);
		hostileTick = 200;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return rand.nextBoolean() ? new GOTEntityLion(worldObj) : new GOTEntityLioness(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int furs = 1 + rand.nextInt(3) + 1;
		for (int l = 0; l < furs; ++l) {
			dropItem(GOTRegistry.lionFur, 1);
		}
		int meats = rand.nextInt(2) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < meats; ++l) {
			if (isBurning()) {
				dropItem(GOTRegistry.lionCooked, 1);
				continue;
			}
			dropItem(GOTRegistry.lionRaw, 1);
		}
		if (flag) {
			int rugChance = 30 - i * 5;
			if (rand.nextInt(rugChance = Math.max(rugChance, 1)) == 0) {
				entityDropItem(new ItemStack(GOTRegistry.lionRug, 1, getLionRugType().lionID), 0.0f);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) 0);
	}

	@Override
	public Class getAnimalMFBaseClass() {
		return GOTEntityLionBase.class;
	}

	@Override
	public String getDeathSound() {
		return "got:lion.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 2 + worldObj.rand.nextInt(3);
	}

	@Override
	public String getHurtSound() {
		return "got:lion.hurt";
	}

	public abstract GOTItemLionRug.LionRugType getLionRugType();

	@Override
	public String getLivingSound() {
		return "got:lion.say";
	}

	@Override
	public int getTalkInterval() {
		return 300;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (isHostile()) {
			return false;
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.getItem() == Items.fish;
	}

	public boolean isHostile() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public void setHostile(boolean flag) {
		dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
	}

	@Override
	public void onLivingUpdate() {
		boolean isChild;
		EntityLivingBase entity;
		if (!worldObj.isRemote && (isChild = isChild()) != prevIsChild) {
			if (isChild) {
				tasks.removeTask(attackAI);
				tasks.addTask(2, panicAI);
				targetTasks.removeTask(targetNearAI);
			} else {
				tasks.removeTask(panicAI);
				if (hostileTick > 0) {
					tasks.addTask(1, attackAI);
					targetTasks.addTask(1, targetNearAI);
				} else {
					tasks.removeTask(attackAI);
					targetTasks.removeTask(targetNearAI);
				}
			}
		}
		super.onLivingUpdate();
		if (!worldObj.isRemote && getAttackTarget() != null && (!(entity = getAttackTarget()).isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
			setAttackTarget(null);
		}
		if (!worldObj.isRemote) {
			if (hostileTick > 0 && getAttackTarget() == null) {
				--hostileTick;
			}
			setHostile(hostileTick > 0);
			if (isHostile()) {
				resetInLove();
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		hostileTick = nbt.getInteger("Angry");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Angry", hostileTick);
	}
}
