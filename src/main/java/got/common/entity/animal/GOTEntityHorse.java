package got.common.entity.animal;

import got.GOT;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIHiredHorseRemainStill;
import got.common.entity.ai.GOTEntityAIHorseFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHorseMoveToRiderTarget;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRegistry;
import got.common.entity.other.GOTEntityUtils;
import got.common.entity.other.GOTNPCMount;
import got.common.item.other.GOTItemMountArmor;
import got.common.util.GOTReflection;
import got.common.world.biome.essos.GOTBiomeDothrakiSea;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;
import java.util.Objects;

public class GOTEntityHorse extends EntityHorse implements GOTNPCMount {
	public boolean isMoving;
	public ItemStack prevMountArmor;
	public EntityAIBase attackAI;
	public EntityAIBase panicAI;
	public boolean prevIsChild = true;

	public GOTEntityHorse(World world) {
		super(world);
		tasks.addTask(0, new GOTEntityAIHiredHorseRemainStill(this));
		tasks.addTask(0, new GOTEntityAIHorseMoveToRiderTarget(this));
		tasks.addTask(0, new GOTEntityAIHorseFollowHiringPlayer(this));
		EntityAITasks.EntityAITaskEntry panic = GOTEntityUtils.removeAITask(this, EntityAIPanic.class);
		assert panic != null;
		tasks.addTask(panic.priority, panic.action);
		panicAI = panic.action;
		attackAI = createMountAttackAI();
		if (isMountHostile()) {
			targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		}
	}

	@Override
	public boolean allowLeashing() {
		if (getBelongsToNPC()) {
			return false;
		}
		return super.allowLeashing();
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		if (isMountHostile()) {
			getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		}
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
		if (flag && isChild() && isMountHostile() && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
			List<? extends Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12.0, 12.0, 12.0));
			for (Entity element : list) {
				if (element instanceof GOTEntityHorse && !((GOTEntityHorse) element).isChild() && !((GOTEntityHorse) element).isTame()) {
					((GOTEntityHorse) element).setAttackTarget((EntityLivingBase) attacker);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean canDespawn() {
		return getBelongsToNPC() && riddenByEntity == null;
	}

	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 12.0, 48.0);
	}

	public double clampChildJump(double jump) {
		return MathHelper.clamp_double(jump, 0.3, 1.0);
	}

	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.45);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable otherParent) {
		EntityHorse superChild = (EntityHorse) super.createChild(otherParent);
		GOTEntityHorse child = (GOTEntityHorse) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(getClass()), worldObj);
		child.setHorseType(superChild.getHorseType());
		child.setHorseVariant(superChild.getHorseVariant());
		double maxHealth = getChildAttribute(this, otherParent, SharedMonsterAttributes.maxHealth, 3.0);
		maxHealth = clampChildHealth(maxHealth);
		child.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		child.setHealth(child.getMaxHealth());
		double jumpStrength = getChildAttribute(this, otherParent, GOTReflection.getHorseJumpStrength(), 0.1);
		jumpStrength = clampChildJump(jumpStrength);
		child.getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
		double moveSpeed = getChildAttribute(this, otherParent, SharedMonsterAttributes.movementSpeed, 0.03);
		moveSpeed = clampChildSpeed(moveSpeed);
		child.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
		if (isTame() && ((GOTEntityHorse) otherParent).isTame()) {
			child.setHorseTamed(true);
		}
		return child;
	}

	public EntityAIBase createMountAttackAI() {
		return null;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(25, (byte) 0);
		dataWatcher.addObject(26, (byte) 1);
		dataWatcher.addObject(27, 0);
		dataWatcher.addObject(28, (byte) 0);
		dataWatcher.addObject(29, (byte) 0);
	}

	@Override
	public boolean getBelongsToNPC() {
		return dataWatcher.getWatchableObjectByte(25) == 1;
	}

	@Override
	public void setBelongsToNPC(boolean flag) {
		dataWatcher.updateObject(25, flag ? (byte) 1 : 0);
		if (flag) {
			setHorseTamed(true);
			setHorseSaddled(true);
			if (getGrowingAge() < 0) {
				setGrowingAge(0);
			}
			if (getClass() == GOTEntityHorse.class) {
				setHorseType(0);
			}
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (getBelongsToNPC() && riddenByEntity instanceof GOTEntityNPC) {
			return ((GOTEntityNPC) riddenByEntity).getBlockPathWeight(i, j, k);
		}
		return super.getBlockPathWeight(i, j, k);
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

	public double getChildAttribute(EntityAgeable parent, EntityAgeable otherParent, IAttribute stat, double variance) {
		double val2;
		double val1 = parent.getEntityAttribute(stat).getBaseValue();
		val2 = otherParent.getEntityAttribute(stat).getBaseValue();
		if (val1 <= val2) {
			return MathHelper.getRandomDoubleInRange(rand, val1 - variance, val2 + variance);
		}
		return MathHelper.getRandomDoubleInRange(rand, val2 - variance, val1 + variance);
	}

	@Override
	public String getCommandSenderName() {
		if (getClass() == GOTEntityHorse.class) {
			return super.getCommandSenderName();
		}
		if (hasCustomNameTag()) {
			return getCustomNameTag();
		}
		String s = EntityList.getEntityString(this);
		return StatCollector.translateToLocal("entity." + s + ".name");
	}

	public boolean getMountable() {
		return dataWatcher.getWatchableObjectByte(26) == 1;
	}

	public void setMountable(boolean flag) {
		dataWatcher.updateObject(26, flag ? (byte) 1 : 0);
	}

	public ItemStack getMountArmor() {
		int ID = dataWatcher.getWatchableObjectInt(27);
		byte meta = dataWatcher.getWatchableObjectByte(28);
		return new ItemStack(Item.getItemById(ID), 1, meta);
	}

	public void setMountArmor(ItemStack itemstack) {
		Objects.requireNonNull(GOTReflection.getHorseInv(this)).setInventorySlotContents(1, itemstack);
		GOTReflection.setupHorseInv(this);
		setMountArmorWatched(itemstack);
	}

	@Override
	public String getMountArmorTexture() {
		ItemStack armor = getMountArmor();
		if (armor != null && armor.getItem() instanceof GOTItemMountArmor) {
			return ((GOTItemMountArmor) armor.getItem()).getArmorTexture();
		}
		return null;
	}

	@Override
	public double getMountedYOffset() {
		double d = height * 0.5;
		if (riddenByEntity != null) {
			d += riddenByEntity.yOffset - riddenByEntity.getYOffset();
		}
		return d;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public float getStepHeightWhileRiddenByPlayer() {
		return 1.0f;
	}

	@Override
	public int getTotalArmorValue() {
		ItemStack itemstack = Objects.requireNonNull(GOTReflection.getHorseInv(this)).getStackInSlot(1);
		if (itemstack != null && itemstack.getItem() instanceof GOTItemMountArmor) {
			GOTItemMountArmor armor = (GOTItemMountArmor) itemstack.getItem();
			return armor.getDamageReduceAmount();
		}
		return 0;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (!getMountable() || isMountEnraged()) {
			return false;
		}
		if (getBelongsToNPC()) {
			if (riddenByEntity == null) {
				if (!worldObj.isRemote) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.mountOwnedByNPC"));
				}
				return true;
			}
			return false;
		}
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null && isBreedingItem(itemstack) && getGrowingAge() == 0 && !isInLove() && isTame()) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}
			}
			func_146082_f(entityplayer);
			return true;
		}
		boolean prevInLove = isInLove();
		boolean flag = super.interact(entityplayer);
		if (isInLove() && !prevInLove) {
			resetInLove();
		}
		return flag;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && GOT.isOreNameEqual(itemstack, "apple");
	}

	@Override
	public boolean isHorseSaddled() {
		return (!isMoving || !getBelongsToNPC()) && super.isHorseSaddled();
	}

	@Override
	public boolean isMountArmorValid(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemMountArmor) {
			GOTItemMountArmor armor = (GOTItemMountArmor) itemstack.getItem();
			return armor.isValid(this);
		}
		return false;
	}

	public boolean isMountEnraged() {
		return dataWatcher.getWatchableObjectByte(29) == 1;
	}

	public void setMountEnraged(boolean flag) {
		dataWatcher.updateObject(29, flag ? (byte) 1 : 0);
	}

	public boolean isMountHostile() {
		return false;
	}

	@Override
	public boolean isMountSaddled() {
		return isHorseSaddled();
	}

	@Override
	public boolean isMovementBlocked() {
		isMoving = true;
		boolean flag = super.isMovementBlocked();
		isMoving = false;
		return flag;
	}

	@Override
	public void moveEntityWithHeading(float f, float f1) {
		isMoving = true;
		super.moveEntityWithHeading(f, f1);
		isMoving = false;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		if (getBelongsToNPC()) {
			AnimalChest inv = GOTReflection.getHorseInv(this);
			assert inv != null;
			inv.setInventorySlotContents(0, null);
			inv.setInventorySlotContents(1, null);
		}
		super.onDeath(damagesource);
	}

	public void onGOTHorseSpawn() {
		int i = MathHelper.floor_double(posX);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (getClass() == GOTEntityHorse.class) {
			float healthBoost = 0.0f;
			float speedBoost = 0.0f;
			float jumpAdd = 0.0f;
			if (biome instanceof GOTBiomeDothrakiSea) {
				healthBoost = 0.5f;
				speedBoost = 0.3f;
				jumpAdd = 0.2f;
			}
			if (healthBoost > 0.0f) {
				double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * (1.0f + rand.nextFloat() * healthBoost));
				setHealth(getMaxHealth());
			}
			if (speedBoost > 0.0f) {
				double movementSpeed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed * (1.0f + rand.nextFloat() * speedBoost));
			}
			double jumpStrength = getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue();
			double jumpLimit = Math.max(jumpStrength, 1.0);
			if (jumpAdd > 0.0f) {
				jumpStrength += jumpAdd;
			}
			jumpStrength = Math.min(jumpStrength, jumpLimit);
			getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
		}
	}

	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote) {
			ItemStack armor = Objects.requireNonNull(GOTReflection.getHorseInv(this)).getStackInSlot(1);
			if (ticksExisted > 20 && !ItemStack.areItemStacksEqual(prevMountArmor, armor)) {
				playSound("mob.horse.armor", 0.5f, 1.0f);
			}
			prevMountArmor = armor;
			setMountArmorWatched(armor);
		}
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer && isInWater() && motionY < 0.0 && worldObj.func_147461_a(boundingBox.copy().addCoord(0.0, -1.0, 0.0)).isEmpty() && rand.nextFloat() < 0.55f) {
			motionY += 0.05;
			isAirBorne = true;
		}
		if (!worldObj.isRemote && isMountHostile()) {
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
			setMountEnraged(getAttackTarget() != null);
		}
		prevIsChild = isChild();
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		if (!worldObj.isRemote) {
			data = super.onSpawnWithEgg(data);
			onGOTHorseSpawn();
			setHealth(getMaxHealth());
			return data;
		}
		int j = rand.nextInt(7);
		int k = rand.nextInt(5);
		int i = j | k << 8;
		setHorseVariant(i);
		return data;
	}

	@Override
	public void openGUI(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer) && isTame()) {
			AnimalChest animalchest = GOTReflection.getHorseInv(this);
			assert animalchest != null;
			animalchest.func_110133_a(getCommandSenderName());
			entityplayer.openGui(GOT.instance, 29, worldObj, getEntityId(), animalchest.getSizeInventory(), 0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		ItemStack armor;
		double jumpStrength;
		super.readEntityFromNBT(nbt);
		boolean pre35 = false;
		if (nbt.hasKey("BelongsToNPC")) {
			pre35 = true;
			setBelongsToNPC(nbt.getBoolean("BelongsToNPC"));
		} else {
			setBelongsToNPC(nbt.getBoolean("BelongsNPC"));
		}
		if (nbt.hasKey("Mountable")) {
			setMountable(nbt.getBoolean("Mountable"));
		}
		AnimalChest inv = GOTReflection.getHorseInv(this);
		if (nbt.hasKey("GOTMountArmorItem") && (armor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("GOTMountArmorItem"))) != null && isMountArmorValid(armor)) {
			assert inv != null;
			inv.setInventorySlotContents(1, armor);
		}
		if (pre35 && getEntityAttribute(GOTReflection.getHorseJumpStrength()).getAttributeValue() > 1.0) {
			jumpStrength = 1.0;
			getEntityAttribute(GOTReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
		}
	}

	public void saddleMountForWorldGen() {
		setGrowingAge(0);
		Objects.requireNonNull(GOTReflection.getHorseInv(this)).setInventorySlotContents(0, new ItemStack(Items.saddle));
		GOTReflection.setupHorseInv(this);
		setHorseTamed(true);
	}

	public void setChestedForWorldGen() {
		setChested(true);
		GOTReflection.setupHorseInv(this);
	}

	public void setMountArmorWatched(ItemStack itemstack) {
		if (itemstack == null) {
			dataWatcher.updateObject(27, 0);
			dataWatcher.updateObject(28, (byte) 0);
		} else {
			dataWatcher.updateObject(27, Item.getIdFromItem(itemstack.getItem()));
			dataWatcher.updateObject(28, (byte) itemstack.getItemDamage());
		}
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}

	@Override
	public void super_moveEntityWithHeading(float strafe, float forward) {
		super.moveEntityWithHeading(strafe, forward);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("BelongsNPC", getBelongsToNPC());
		nbt.setBoolean("Mountable", getMountable());
		AnimalChest inv = GOTReflection.getHorseInv(this);
		assert inv != null;
		if (inv.getStackInSlot(1) != null) {
			nbt.setTag("GOTMountArmorItem", inv.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}
	}
}