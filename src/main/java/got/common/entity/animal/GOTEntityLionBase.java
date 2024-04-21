package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAILionChase;
import got.common.entity.ai.GOTEntityAILionMate;
import got.common.entity.other.GOTEntityRegistry;
import got.common.item.other.GOTItemLionRug;
import got.common.util.GOTCrashHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public abstract class GOTEntityLionBase extends EntityAnimal {
	private final EntityAIBase attackAI = new GOTEntityAIAttackOnCollide(this, 1.5, false);
	private final EntityAIBase panicAI = new EntityAIPanic(this, 1.5);
	private final EntityAIBase targetNearAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	private int hostileTick;
	private boolean prevIsChild = true;

	protected GOTEntityLionBase(World world) {
		super(world);
		setSize(1.4f, 1.6f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, panicAI);
		tasks.addTask(3, new GOTEntityAILionMate(this, 1.0));
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
	public boolean canMateWith(EntityAnimal mate) {
		GOTEntityLionBase mfMate = (GOTEntityLionBase) mate;
		if (mate == this) {
			return false;
		}
		if (getAnimalMFBaseClass() == mfMate.getAnimalMFBaseClass() && isInLove() && mate.isInLove()) {
			boolean thisMale = isMale();
			return thisMale != mfMate.isMale();
		}
		return false;
	}

	public Class<? extends EntityAnimal> getAnimalMFBaseClass() {
		return GOTEntityLionBase.class;
	}

	protected abstract boolean isMale();

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
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
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23);
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
				List<? extends Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(range, range, range));
				for (Entity entity : list) {
					if (entity instanceof GOTEntityLion && !((EntityLivingBase) entity).isChild()) {
						((GOTEntityLionBase) entity).becomeAngryAt((EntityLivingBase) attacker);
					}
				}
			} else {
				becomeAngryAt((EntityLivingBase) attacker);
			}
		}
		return flag;
	}

	private void becomeAngryAt(EntityLivingBase entity) {
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
			dropItem(GOTItems.lionFur, 1);
		}
		int meats = rand.nextInt(2) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < meats; ++l) {
			if (isBurning()) {
				dropItem(GOTItems.lionCooked, 1);
				continue;
			}
			dropItem(GOTItems.lionRaw, 1);
		}
		if (flag) {
			int rugChance = 30 - i * 5;
			if (rand.nextInt(Math.max(rugChance, 1)) == 0) {
				entityDropItem(new ItemStack(GOTItems.lionRug, 1, getLionRugType().getLionID()), 0.0f);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) 0);
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

	protected abstract GOTItemLionRug.LionRugType getLionRugType();

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
		return !isHostile() && super.interact(entityplayer);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.getItem() == Items.fish;
	}

	private boolean isHostile() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	private void setHostile(boolean flag) {
		dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
	}

	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote) {
			boolean isChild = isChild();
			if (isChild != prevIsChild) {
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
		}
		super.onLivingUpdate();
		if (!worldObj.isRemote && getAttackTarget() != null) {
			EntityLivingBase entity = getAttackTarget();
			if (!entity.isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
				setAttackTarget(null);
			}
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
		prevIsChild = isChild();
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