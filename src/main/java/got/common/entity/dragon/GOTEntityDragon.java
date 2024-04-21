package got.common.entity.dragon;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelDragonAnimaton;
import got.client.model.GOTModelDragonBodyHelper;
import got.common.entity.ai.*;
import got.common.entity.dragon.helper.GOTDragonHelper;
import got.common.entity.dragon.helper.GOTDragonLifeStageHelper;
import got.common.entity.dragon.helper.GOTDragonParticleHelper;
import got.common.entity.dragon.helper.GOTDragonReproductionHelper;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTEntityDragon extends EntityTameable implements GOTBiome.ImmuneToFrost {
	public static final float BASE_WIDTH = 4.0f;

	private static final float BASE_HEIGHT = 3.0f;

	private static final IAttribute MOVE_SPEED_AIR = new RangedAttribute("generic.movementSpeedAir", 1.5, 0.0, Double.MAX_VALUE).setDescription("Movement Speed Air").setShouldWatch(true);

	private static final String[] ENTITYAITASKS_TICKRATE = {"tickRate", "field_75779_e"};
	private static final String[] ENTITYLIVING_BODYHELPER = {"bodyHelper", "field_70762_j"};

	private static final String NBT_FLYING = "Flying";
	private static final String NBT_CAN_FLY = "CanFly";
	private static final String NBT_SADDLED = "Saddle";
	private static final Item FAVORITE_FOOD = Items.fish;

	private static final double BASE_SPEED_GROUND = 0.3;
	private static final double BASE_SPEED_AIR = 1.5;
	private static final double BASE_HEALTH = 1000;
	private static final double BASE_DAMAGE = 8;

	private static final int INDEX_FLYING = 18;
	private static final int INDEX_CAN_FLY = 19;
	private static final int INDEX_SADDLED = 20;
	private static final int INDEX_BREEDER = 21;
	private static final int INDEX_REPRO_COUNT = 23;

	private final GOTDragonFlightWaypoint waypoint;
	private final EntityAITasks airTasks;

	private Map<Class<? extends GOTDragonHelper>, GOTDragonHelper> helpers;

	private GOTModelDragonAnimaton animator;
	private BitSet controlFlags;

	private double airSpeedHorizonal = 1.5;
	private double airSpeedVertical;
	private float yawAdd;
	private int inAirTicks;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDragon(World world) {
		super(world);
		waypoint = new GOTDragonFlightWaypoint(this);
		airTasks = new EntityAITasks(world != null ? world.theProfiler : null);
		try {
			ReflectionHelper.setPrivateValue(EntityLiving.class, this, new GOTModelDragonBodyHelper(this), ENTITYLIVING_BODYHELPER);
		} catch (Exception ex) {
			GOTLog.getLogger().warn("Can't override EntityBodyHelper", ex);
		}
		setSize(BASE_WIDTH, BASE_HEIGHT);
		stepHeight = 1;
		tasks.addTask(0, new GOTEntityAIDragonCatchOwnerGround(this));
		tasks.addTask(1, new GOTEntityAIDragonRideGround(this, 1));
		tasks.addTask(2, new EntityAISwimming(this));
		tasks.addTask(3, aiSit);
		tasks.addTask(4, new GOTEntityAIDragonDragonMate(this, 0.6));
		tasks.addTask(5, new EntityAITempt(this, 0.75, FAVORITE_FOOD, false));
		tasks.addTask(6, new EntityAIAttackOnCollide(this, 1, true));
		tasks.addTask(7, new EntityAIFollowParent(this, 0.8));
		tasks.addTask(8, new GOTEntityAIDragonFollowOwner(this, 1, 12, 128));
		tasks.addTask(8, new GOTEntityAIDragonPanicChild(this, 1));
		tasks.addTask(9, new EntityAIWander(this, 1));
		tasks.addTask(10, new GOTEntityAIDragonWatchIdle(this));
		tasks.addTask(10, new GOTEntityAIDragonWatchLiving(this, 16, 0.05f));
		airTasks.addTask(0, new GOTEntityAIDragonRideAir(this));
		airTasks.addTask(0, new GOTEntityAIDragonLand(this));
		airTasks.addTask(0, new GOTEntityAIDragonCatchOwnerAir(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(4, new GOTEntityAIDragonHunt(this, EntitySheep.class, 200, false));
		targetTasks.addTask(4, new GOTEntityAIDragonHunt(this, EntityPig.class, 200, false));
		targetTasks.addTask(4, new GOTEntityAIDragonHunt(this, EntityChicken.class, 200, false));
		isImmuneToFire = true;
	}

	private static Item consumeEquipped(EntityPlayer player, Item... items) {
		ItemStack itemStack = player.getCurrentEquippedItem();
		if (itemStack == null) {
			return null;
		}
		Item equippedItem = itemStack.getItem();
		for (Item item : items) {
			if (item == equippedItem) {
				if (!player.capabilities.isCreativeMode) {
					itemStack.stackSize--;
				}
				if (itemStack.stackSize <= 0) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
				return item;
			}
		}
		return null;
	}

	public static boolean hasEquipped(EntityPlayer player, Item item) {
		ItemStack itemStack = player.getCurrentEquippedItem();
		return itemStack != null && itemStack.getItem() == item;
	}

	private static boolean hasEquippedUsable(EntityPlayer player) {
		ItemStack itemStack = player.getCurrentEquippedItem();
		return itemStack != null && itemStack.getItemUseAction() != EnumAction.none;
	}

	private void addHelper(GOTDragonHelper helper) {
		GOTLog.getLogger().trace("addHelper({})", helper.getClass().getName());
		if (helpers == null) {
			helpers = new HashMap<>();
		}
		helpers.put(helper.getClass(), helper);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(MOVE_SPEED_AIR);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		setAttributes();
		for (GOTDragonHelper helper : helpers.values()) {
			helper.applyEntityAttributes();
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(INDEX_FLYING, (byte) 0);
		dataWatcher.addObject(INDEX_CAN_FLY, (byte) 0);
		dataWatcher.addObject(INDEX_SADDLED, (byte) 0);
		addHelper(new GOTDragonLifeStageHelper(this));
		addHelper(new GOTDragonReproductionHelper(this, INDEX_BREEDER, INDEX_REPRO_COUNT));
		addHelper(new GOTDragonParticleHelper(this));
		if (isClient()) {
			animator = new GOTModelDragonAnimaton(this);
		}
	}

	@Override
	public void fall(float par1) {
		if (!isCanFly()) {
			super.fall(par1);
		}
	}

	public double getAltitude() {
		int blockX = (int) (posX - 0.5);
		int blockZ = (int) (posZ - 0.5);
		return posY - worldObj.getHeightValue(blockX, blockZ);
	}

	public GOTDragonFlightWaypoint getWaypoint() {
		return waypoint;
	}

	private boolean isAirAIEnabled() {
		return isFlying();
	}

	private boolean isCanFly() {
		return (dataWatcher.getWatchableObjectByte(INDEX_CAN_FLY) & 1) != 0;
	}

	public void setCanFly(boolean canFly) {
		GOTLog.getLogger().trace("setCanFly({})", canFly);
		dataWatcher.updateObject(INDEX_CAN_FLY, (byte) (canFly ? 1 : 0));
	}

	public boolean isClient() {
		return worldObj.isRemote;
	}

	public boolean isFlying() {
		return (dataWatcher.getWatchableObjectByte(INDEX_FLYING) & 1) != 0;
	}

	private void setFlying(boolean flying) {
		dataWatcher.updateObject(INDEX_FLYING, (byte) (flying ? 1 : 0));
	}

	private boolean isGroundAIEnabled() {
		return !isFlying() && !isEgg();
	}

	public void liftOff() {
		GOTLog.getLogger().trace("liftOff");
		if (isCanFly()) {
			jump();
			motionY += 0.5;
			inAirTicks += 20;
			waypoint.clear();
		}
	}

	@Override
	public void onLivingUpdate() {
		for (GOTDragonHelper helper : helpers.values()) {
			helper.onLivingUpdate();
		}
		if (isClient()) {
			if (!isEgg()) {
				animator.setOnGround(!isFlying());
				animator.update();
			}
		} else if (isTamed()) {
			Entity owner = getOwner();
			if (owner != null) {
				int HOME_RADIUS = 256;
				setHomeArea((int) owner.posX, (int) owner.posY, (int) owner.posZ, HOME_RADIUS);
			}
		}
		if (!isCanFly()) {
			if (isFlying()) {
				setFlying(false);
			}
			super.onLivingUpdate();
			return;
		}
		if (!worldObj.isRemote) {
			if (onGround) {
				inAirTicks = 0;
			} else {
				inAirTicks++;
			}
			int IN_AIR_THRESH = 10;
			setFlying(inAirTicks > IN_AIR_THRESH);
		}
		if (isFlying()) {
			if (isClient()) {
				onUpdateFlyingClient();
			} else {
				onUpdateFlyingServer();
			}
		} else {
			super.onLivingUpdate();
		}
	}

	private void onUpdateFlyingClient() {
		if (newPosRotationIncrements > 0) {
			double px = posX + (newPosX - posX) / newPosRotationIncrements;
			double py = posY + (newPosY - posY) / newPosRotationIncrements;
			double pz = posZ + (newPosZ - posZ) / newPosRotationIncrements;
			double newYaw = GOTModelDragonAnimaton.normDeg(newRotationYaw - rotationYaw);
			rotationYaw += (float) newYaw / newPosRotationIncrements;
			rotationPitch += ((float) newRotationPitch - rotationPitch) / newPosRotationIncrements;
			--newPosRotationIncrements;
			setPosition(px, py, pz);
			setRotation(rotationYaw, rotationPitch);
		}
	}

	private void onUpdateFlyingServer() {
		float friction = 0;
		if (!waypoint.isNear()) {
			double deltaX = waypoint.getPosX() - posX;
			double deltaY = waypoint.getPosY() - posY;
			double deltaZ = waypoint.getPosZ() - posZ;
			double speedAir = getEntityAttribute(MOVE_SPEED_AIR).getAttributeValue();
			double speedHoriz = airSpeedHorizonal * speedAir;
			double speedVert = airSpeedVertical;
			deltaY /= Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
			deltaY = GOTModelDragonAnimaton.clamp(deltaY, -speedHoriz, speedHoriz) / 3;
			double motionHypot = Math.hypot(motionX, motionZ) + 1;
			double newYaw = Math.toDegrees(Math.PI * 2 - Math.atan2(deltaX, deltaZ));
			double yawDelta = GOTModelDragonAnimaton.normDeg(newYaw - rotationYaw);
			int yawSpeed = 30;
			yawDelta = GOTModelDragonAnimaton.clamp(yawDelta, -yawSpeed, yawSpeed);
			yawAdd *= 0.8f;
			yawAdd += (float) (yawDelta * (0.7 / motionHypot));
			rotationYaw += yawAdd * 0.1f;
			Vec3 motionVec = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
			Vec3 deltaVec = Vec3.createVectorHelper(deltaX, deltaY, deltaZ).normalize();
			Vec3 rotVec = Vec3.createVectorHelper(-Math.sin(Math.toRadians(rotationYaw)), motionY, Math.cos(Math.toRadians(rotationYaw))).normalize();
			float tmp1 = (float) (rotVec.dotProduct(deltaVec) + 0.5) / 1.5f;
			if (tmp1 < 0) {
				tmp1 = 0;
			}
			float tmp2 = (float) (2 / (motionHypot + 1));
			float acc = 0.06f * (tmp1 * tmp2 + (1 - tmp2));
			motionY = deltaY + speedVert;
			moveFlying(0, (float) speedHoriz, acc);
			friction = (float) (motionVec.dotProduct(rotVec) + 1) / 2.0f;
		}
		friction = 0.8f + 0.15f * friction;
		if (inWater) {
			friction *= 0.8f;
		}
		motionX *= friction;
		motionY *= friction;
		motionZ *= friction;
		moveEntity(motionX, motionY, motionZ);
		if (isAIEnabled()) {
			worldObj.theProfiler.startSection("newAi");
			updateAITasks();
			worldObj.theProfiler.endSection();
		} else {
			worldObj.theProfiler.startSection("oldAi");
			updateEntityActionState();
			worldObj.theProfiler.endSection();
			rotationYawHead = rotationYaw;
		}
		List<? extends Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.2, 0, 0));
		if (entities != null && !entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity.canBePushed()) {
					entity.applyEntityCollision(this);
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setFlying(nbt.getBoolean(NBT_FLYING));
		setCanFly(nbt.getBoolean(NBT_CAN_FLY));
		waypoint.readFromNBT(nbt);
		setSaddled(nbt.getBoolean(NBT_SADDLED));
		for (GOTDragonHelper helper : helpers.values()) {
			helper.readFromNBT(nbt);
		}
		setAttributes();
	}

	public void setMoveSpeedAirHoriz(double airSpeedHorizonal) {
		GOTLog.getLogger().trace("setMoveSpeedAirHoriz({})", airSpeedHorizonal);
		this.airSpeedHorizonal = airSpeedHorizonal;
	}

	public void setMoveSpeedAirVert(double airSpeedVertical) {
		GOTLog.getLogger().trace("setMoveSpeedAirVert({})", airSpeedVertical);
		this.airSpeedVertical = airSpeedVertical;
	}

	private void setTasksEnabled(EntityAITasks tasks, boolean flag) {
		ReflectionHelper.setPrivateValue(EntityAITasks.class, tasks, flag ? 3 : Integer.MAX_VALUE, ENTITYAITASKS_TICKRATE);
	}

	@Override
	public void updateAITasks() {
		if (!isEgg()) {
			setTasksEnabled(tasks, isGroundAIEnabled());
			setTasksEnabled(airTasks, isAirAIEnabled());
			super.updateAITasks();
			airTasks.onUpdateTasks();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean(NBT_FLYING, isFlying());
		nbt.setBoolean(NBT_CAN_FLY, isCanFly());
		nbt.setBoolean(NBT_SADDLED, isSaddled());
		waypoint.writeToNBT(nbt);
		for (GOTDragonHelper helper : helpers.values()) {
			helper.writeToNBT(nbt);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity victim) {
		float attackDamage = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		int knockback = 0;
		if (victim instanceof EntityLivingBase) {
			attackDamage += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) victim);
			knockback += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) victim);
		}
		boolean attacked = victim.attackEntityFrom(DamageSource.causeMobDamage(this), attackDamage);
		if (attacked) {
			if (knockback > 0) {
				double vx = -Math.sin(Math.toRadians(rotationYaw)) * knockback * 0.5;
				double vy = 0.1;
				double vz = Math.cos(Math.toRadians(rotationYaw)) * knockback * 0.5;
				victim.addVelocity(vx, vy, vz);
				motionX *= 0.6;
				motionZ *= 0.6;
			}
			int fireAspect = EnchantmentHelper.getFireAspectModifier(this);
			if (fireAspect > 0) {
				victim.setFire(fireAspect * 4);
			}
			if (victim instanceof EntityLivingBase) {
				EnchantmentHelper.func_151384_a((EntityLivingBase) victim, this);
			}
			EnchantmentHelper.func_151385_b(this, victim);
			setLastAttacker(victim);
			float volume = getSoundVolume() * 0.7f;
			float pitch = getSoundPitch();
			worldObj.playSoundAtEntity(this, "random.eat", volume, pitch);
		}
		return attacked;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float amount) {
		if (damagesource == DamageSource.inFire || damagesource == DamageSource.onFire || damagesource == DamageSource.lava || damagesource == DamageSource.inWall || damagesource == DamageSource.drown || damagesource == DamageSource.starve || damagesource == DamageSource.cactus || damagesource == DamageSource.fall || damagesource == DamageSource.anvil || damagesource == DamageSource.fallingBlock) {
			return false;
		}
		aiSit.setSitting(false);
		return super.attackEntityFrom(damagesource, amount);
	}

	@Override
	public boolean canBePushed() {
		return super.canBePushed() && isEgg();
	}

	@Override
	public boolean canDespawn() {
		return false;
	}

	@Override
	public boolean canMateWith(EntityAnimal mate) {
		return getReproductionHelper().canMateWith(mate);
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable mate) {
		return getReproductionHelper().createChild(mate);
	}

	@Override
	public void dropFewItems(boolean par1, int par2) {
		super.dropFewItems(par1, par2);
		if (isSaddled()) {
			dropItem(Items.saddle, 1);
		}
	}

	public GOTModelDragonAnimaton getAnimator() {
		return animator;
	}

	private boolean getBooleanData(int index) {
		return (dataWatcher.getWatchableObjectByte(index) & 1) != 0;
	}

	@Override
	public String getCommandSenderName() {
		if (hasCustomNameTag()) {
			return getCustomNameTag();
		}
		String entName = EntityList.getEntityString(this);
		return StatCollector.translateToLocal("entity." + entName + ".name");
	}

	public BitSet getControlFlags() {
		return controlFlags;
	}

	public void setControlFlags(BitSet flags) {
		controlFlags = flags;
	}

	@Override
	public String getDeathSound() {
		return "mob.enderdragon.growl";
	}

	public int getDeathTime() {
		return deathTime;
	}

	@Override
	public float getEyeHeight() {
		float eyeHeight = super.getEyeHeight();
		if (isSitting()) {
			eyeHeight *= 0.8f;
		}
		return eyeHeight;
	}

	public double getHealthRelative() {
		return getHealth() / (double) getMaxHealth();
	}

	private <T extends GOTDragonHelper> T getHelper(Class<T> clazz) {
		return (T) helpers.get(clazz);
	}

	@Override
	public String getHurtSound() {
		if (isEgg()) {
			return "mob.zombie.wood";
		}
		return "mob.enderdragon.growl";
	}

	public GOTDragonLifeStageHelper getLifeStageHelper() {
		return getHelper(GOTDragonLifeStageHelper.class);
	}

	@Override
	public String getLivingSound() {
		if (isEgg() || isFlying()) {
			return null;
		}
		return "mob.enderdragon.growl";
	}

	@Override
	public double getMountedYOffset() {
		return (isSitting() ? 1.7f : 2.2f) * getScale();
	}

	private GOTDragonParticleHelper getParticleHelper() {
		return getHelper(GOTDragonParticleHelper.class);
	}

	@Override
	public float getRenderSizeModifier() {
		return getScale();
	}

	public GOTDragonReproductionHelper getReproductionHelper() {
		return getHelper(GOTDragonReproductionHelper.class);
	}

	public EntityPlayer getRidingPlayer() {
		if (riddenByEntity instanceof EntityPlayer) {
			return (EntityPlayer) riddenByEntity;
		}
		return null;
	}

	private void setRidingPlayer(EntityPlayer player) {
		GOTLog.getLogger().trace("setRidingPlayer({})", player.getCommandSenderName());
		player.rotationYaw = rotationYaw;
		player.rotationPitch = rotationPitch;
		player.mountEntity(this);
	}

	public float getScale() {
		return getLifeStageHelper().getScale();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize() {
		return 0;
	}

	@Override
	public float getSoundVolume() {
		return 4.0f;
	}

	@Override
	public int getTalkInterval() {
		return 240;
	}

	@Override
	public boolean interact(EntityPlayer player) {
		ItemStack playerItem = player.inventory.getCurrentItem();
		if (playerItem != null && playerItem.getItem() == Items.spawn_egg) {
			return super.interact(player);
		}
		if (isEgg()) {
			return false;
		}
		if (!isTamed() && !isChild()) {
			if (!worldObj.isRemote && consumeEquipped(player, FAVORITE_FOOD) != null) {
				tamedFor(player, rand.nextInt(3) == 0);
			}
			return true;
		}
		ItemFood food = null;
		if (getHealthRelative() < 1) {
			food = (ItemFood) consumeEquipped(player, FAVORITE_FOOD, Items.porkchop, Items.beef, Items.chicken);
		}
		if (food != null) {
			heal(food.func_150905_g(playerItem));
			float volume = getSoundVolume() * 0.7f;
			float pitch = getSoundPitch();
			worldObj.playSoundAtEntity(this, "random.eat", volume, pitch);
			return true;
		}
		if (!func_152114_e(player)) {
			if (!worldObj.isRemote) {
				player.addChatMessage(new ChatComponentTranslation("dragon.owned"));
			}
		} else if (!isChild() && riddenByEntity == null) {
			if (!isSaddled() && consumeEquipped(player, Items.saddle) != null) {
				if (!worldObj.isRemote) {
					setSaddled(true);
				}
			} else if (hasEquipped(player, Items.bone)) {
				if (!worldObj.isRemote) {
					aiSit.setSitting(!isSitting());
					isJumping = false;
					setPathToEntity(null);
				}
			} else if (getReproductionHelper().canReproduce() && consumeEquipped(player, FAVORITE_FOOD) != null) {
				if (isClient()) {
					getParticleHelper().spawnBodyParticles("heart");
				}
				func_146082_f(player);
			} else if (isSaddled() && !hasEquippedUsable(player) && !worldObj.isRemote) {
				setRidingPlayer(player);
			}
		}
		return false;
	}

	public boolean isAdult() {
		return getLifeStageHelper().isAdult();
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack item) {
		return false;
	}

	public boolean isEgg() {
		return getLifeStageHelper().isEgg();
	}

	public boolean isHatchling() {
		return getLifeStageHelper().isHatchling();
	}

	@Override
	public boolean isOnLadder() {
		return false;
	}

	public boolean isSaddled() {
		return getBooleanData(INDEX_SADDLED);
	}

	private void setSaddled(boolean saddled) {
		GOTLog.getLogger().trace("setSaddled({})", saddled);
		setBooleanData(INDEX_SADDLED, saddled);
	}

	@Override
	public void onDeathUpdate() {
		for (GOTDragonHelper helper : helpers.values()) {
			helper.onDeathUpdate();
		}
		if (riddenByEntity != null) {
			riddenByEntity.mountEntity(null);
		}
		motionX = motionY = motionZ = 0;
		rotationYaw = prevRotationYaw;
		rotationYawHead = prevRotationYawHead;
		if (isEgg() || deathTime >= 120) {
			setDead();
		}
		deathTime++;
	}

	public void onWingsDown(float speed) {
		if (!inWater) {
			float pitch = getSoundPitch() + (1 - speed);
			float volume = getSoundVolume() * 0.3f + (1 - speed) * 0.2f;
			worldObj.playSound(posX, posY, posZ, "mob.enderdragon.wings", volume, pitch, false);
		}
	}

	@Override
	public void playLivingSound() {
		String sound = getLivingSound();
		if (sound == null) {
			return;
		}
		float v = getSoundVolume();
		float p = getSoundPitch();
		if (sound.endsWith("breathe")) {
			v *= 0.5F;
			p *= 0.5F;
		}
		playSound(sound, v, p);
	}

	private void setAttributes() {
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(BASE_SPEED_GROUND);
		getEntityAttribute(MOVE_SPEED_AIR).setBaseValue(BASE_SPEED_AIR);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(BASE_HEALTH);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(BASE_DAMAGE);
	}

	private void setBooleanData(int index, boolean value) {
		dataWatcher.updateObject(index, (byte) (value ? 1 : 0));
	}

	@Override
	public void setDead() {
		for (GOTDragonHelper helper : helpers.values()) {
			helper.onDeath();
		}
		super.setDead();
	}

	@Override
	public void setScaleForAge(boolean p_98054_1_) {
	}

	public void setScalePublic(float scale) {
		double posXTmp = posX;
		double posYTmp = posY;
		double posZTmp = posZ;
		setScale(scale);
		setPosition(posXTmp, posYTmp, posZTmp);
	}

	public void tamedFor(EntityPlayer player, boolean successful) {
		if (successful) {
			setTamed(true);
			setPathToEntity(null);
			setAttackTarget(null);
			func_152115_b(player.getUniqueID().toString());
			playTameEffect(true);
			worldObj.setEntityState(this, (byte) 7);
		} else {
			playTameEffect(false);
			worldObj.setEntityState(this, (byte) 6);
		}
	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			double px = posX;
			double py = posY + getMountedYOffset() + riddenByEntity.getYOffset();
			double pz = posZ;
			Vec3 pos = Vec3.createVectorHelper(0, 0, 0.8 * getScale());
			pos.rotateAroundY((float) Math.toRadians(-renderYawOffset));
			px += pos.xCoord;
			py += pos.yCoord;
			pz += pos.zCoord;
			riddenByEntity.setPosition(px, py, pz);
			if (riddenByEntity instanceof EntityLiving) {
				EntityLiving rider = (EntityLiving) riddenByEntity;
				rider.prevRotationPitch = rider.rotationPitch;
				rider.prevRotationYaw = rider.rotationYaw;
				rider.renderYawOffset = renderYawOffset;
			}
		}
	}
}