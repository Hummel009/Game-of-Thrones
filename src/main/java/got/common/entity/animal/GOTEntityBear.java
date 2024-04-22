package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityRegistry;
import got.common.util.GOTCrashHandler;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.entity.*;
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
import net.minecraft.world.biome.WorldChunkManager;

import java.util.List;
import java.util.Locale;

public class GOTEntityBear extends EntityAnimal implements GOTBiome.ImmuneToFrost {
	private final EntityAIBase attackAI = new GOTEntityAIAttackOnCollide(this, 1.7, false);
	private final EntityAIBase panicAI = new EntityAIPanic(this, 1.5);
	private final EntityAIBase targetNearAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	private int hostileTick;
	private boolean prevIsChild = true;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBear(World world) {
		super(world);
		setSize(1.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, panicAI);
		tasks.addTask(3, new EntityAIMate(this, 1.0));
		tasks.addTask(4, new EntityAITempt(this, 1.4, Items.fish, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.4));
		tasks.addTask(6, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, targetNearAI);
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
					if (entity instanceof GOTEntityBear && !((EntityLivingBase) entity).isChild()) {
						((GOTEntityBear) entity).becomeAngryAt((EntityLivingBase) attacker);
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

	public static boolean canWorldGenSpawnAt(GOTBiome biome, GOTBiomeVariant variant) {
		int trees = biome.getDecorator().getVariantTreesPerChunk(variant);
		return trees >= 1;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		GOTEntityBear mate = (GOTEntityBear) entity;
		GOTEntityBear child = new GOTEntityBear(worldObj);
		if (rand.nextBoolean()) {
			child.setBearType(getBearType());
		} else {
			child.setBearType(mate.getBearType());
		}
		return child;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int furs = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < furs; ++l) {
			dropItem(GOTItems.fur, 1);
		}
		if (flag) {
			int rugChance = 30 - i * 5;
			if (rand.nextInt(Math.max(rugChance, 1)) == 0) {
				entityDropItem(new ItemStack(GOTItems.bearRug, 1, getBearType().bearID), 0.0f);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
		dataWatcher.addObject(20, (byte) 0);
		setBearType(BearType.forID(rand.nextInt(BearType.values().length)));
	}

	public BearType getBearType() {
		byte i = dataWatcher.getWatchableObjectByte(18);
		return BearType.forID(i);
	}

	private void setBearType(BearType t) {
		dataWatcher.updateObject(18, (byte) t.bearID);
	}

	@Override
	public boolean getCanSpawnHere() {
		WorldChunkManager worldChunkMgr = worldObj.getWorldChunkManager();
		if (worldChunkMgr instanceof GOTWorldChunkManager) {
			int i = MathHelper.floor_double(posX);
			int k = MathHelper.floor_double(posZ);
			GOTBiome biome = (GOTBiome) GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k);
			GOTBiomeVariant variant = ((GOTWorldChunkManager) worldChunkMgr).getBiomeVariantAt(i, k);
			return super.getCanSpawnHere() && canWorldGenSpawnAt(biome, variant);
		}
		return super.getCanSpawnHere();
	}

	@Override
	public String getDeathSound() {
		return "got:bear.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 2 + worldObj.rand.nextInt(3);
	}

	@Override
	public String getHurtSound() {
		return "got:bear.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:bear.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public int getTalkInterval() {
		return 200;
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
				hostileTick--;
			}
			setHostile(hostileTick > 0);
			if (isHostile()) {
				resetInLove();
			}
		}
		prevIsChild = isChild();
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = data;
		entityData = super.onSpawnWithEgg(entityData);
		if (entityData == null) {
			entityData = new BearGroupSpawnData();
			((BearGroupSpawnData) entityData).setNumSpawned(1);
		} else if (entityData instanceof BearGroupSpawnData) {
			BearGroupSpawnData bgsd = (BearGroupSpawnData) entityData;
			if (bgsd.getNumSpawned() >= 1 && rand.nextBoolean()) {
				setGrowingAge(-24000);
			}
			bgsd.setNumSpawned(bgsd.getNumSpawned() + 1);
		}
		if (rand.nextInt(1000) == 0) {
			setCustomNameTag("Vladimir Putin");
		}
		return entityData;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("BearType")) {
			setBearType(BearType.forID(nbt.getByte("BearType")));
		}
		hostileTick = nbt.getInteger("Angry");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("BearType", (byte) getBearType().bearID);
		nbt.setInteger("Angry", hostileTick);
	}

	public enum BearType {
		LIGHT(0), DARK(1), BLACK(2);

		public final int bearID;

		BearType(int i) {
			bearID = i;
		}

		public static String[] bearTypeNames() {
			String[] names = new String[values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = values()[i].textureName();
			}
			return names;
		}

		public static BearType forID(int ID) {
			for (BearType t : values()) {
				if (t.bearID != ID) {
					continue;
				}
				return t;
			}
			return LIGHT;
		}

		public String textureName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}

	private static class BearGroupSpawnData implements IEntityLivingData {
		private int numSpawned;

		private int getNumSpawned() {
			return numSpawned;
		}

		private void setNumSpawned(int numSpawned) {
			this.numSpawned = numSpawned;
		}
	}
}