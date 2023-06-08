package got.common.entity.dragon;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelDragonAnimaton;
import got.client.model.GOTModelDragonBodyHelper;
import got.common.entity.ai.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class GOTEntityDragon extends GOTEntityFlyingTameable {
	public static Logger L = LogManager.getLogger();
	public static double BASE_SPEED_GROUND = 0.3;
	public static double BASE_SPEED_AIR = 1.5;
	public static double BASE_DAMAGE = 8;
	public static double BASE_HEALTH = 1000;
	public static float BASE_WIDTH = 4;
	public static float BASE_HEIGHT = 3.0f;
	public static int HOME_RADIUS = 256;
	public static Item FAVORITE_FOOD = Items.fish;
	public static int INDEX_SADDLED = 20;
	public static int INDEX_BREEDER = 21;
	public static int INDEX_BREED = 22;
	public static int INDEX_REPRO_COUNT = 23;
	public static String NBT_SADDLED = "Saddle";
	public static String[] DATAWATCHER_WATCHEDOBJECTS = {"watchedObjects", "field_75695_b"};
	public static String[] ENTITYAITASKS_EXECUTINGTASKENTRIES = {"executingTaskEntries", "field_75780_b"};
	public static String[] ENTITYLIVING_BODYHELPER = {"bodyHelper", "field_70762_j"};
	public static String[] GUIMAINMENU_SPLASHTEXT = {"splashText", "field_73975_c"};
	public Map<Class<? extends GOTDragonHelper>, GOTDragonHelper> helpers;
	public GOTModelDragonAnimaton animator;
	public BitSet controlFlags;

	public GOTEntityDragon(World world) {
		super(world);
		try {
			ReflectionHelper.setPrivateValue(EntityLiving.class, this, new GOTModelDragonBodyHelper(this), ENTITYLIVING_BODYHELPER);
		} catch (Exception ex) {
			L.warn("Can't override EntityBodyHelper", ex);
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

	public static Item consumeEquipped(EntityPlayer player, Item... items) {
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

		if (itemStack == null) {
			return false;
		}

		return itemStack.getItem() == item;
	}

	public static boolean hasEquippedFood(EntityPlayer player) {
		ItemStack itemStack = player.getCurrentEquippedItem();

		if (itemStack == null) {
			return false;
		}

		return itemStack.getItem() instanceof ItemFood;
	}

	public static boolean hasEquippedUsable(EntityPlayer player) {
		ItemStack itemStack = player.getCurrentEquippedItem();

		if (itemStack == null) {
			return false;
		}

		return itemStack.getItemUseAction() != EnumAction.none;
	}

	public void addHelper(GOTDragonHelper helper) {
		L.trace("addHelper({})", helper.getClass().getName());
		if (helpers == null) {
			helpers = new HashMap<>();
		}
		helpers.put(helper.getClass(), helper);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();

		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		setAttributes();

		for (GOTDragonHelper helper : helpers.values()) {
			helper.applyEntityAttributes();
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
	public boolean attackEntityFrom(DamageSource src, float par2) {
		if (isInvulnerableTo(src)) {
			return false;
		}
		aiSit.setSitting(false);

		return super.attackEntityFrom(src, par2);
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
		return super.canRenderOnFire() && !getBreedHelper().getBreed().isImmuneToDamage(DamageSource.inFire);
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

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(INDEX_SADDLED, (byte) 0);
		addHelper(new GOTDragonBreedHelper(this, INDEX_BREED));
		addHelper(new GOTDragonLifeStageHelper(this));
		addHelper(new GOTDragonReproductionHelper(this, INDEX_BREEDER, INDEX_REPRO_COUNT));
		addHelper(new GOTDragonParticleHelper(this));
		if (isClient()) {
			animator = new GOTModelDragonAnimaton(this);
		}
	}

	public GOTModelDragonAnimaton getAnimator() {
		return animator;
	}

	public boolean getBooleanData(int index) {
		return (dataWatcher.getWatchableObjectByte(index) & 1) != 0;
	}

	public GOTDragonBreed getBreed() {
		return getBreedHelper().getBreed();
	}

	public void setBreed(GOTDragonBreed breed) {
		getBreedHelper().setBreed(breed);
	}

	public GOTDragonBreedHelper getBreedHelper() {
		return getHelper(GOTDragonBreedHelper.class);
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
	public EnumCreatureAttribute getCreatureAttribute() {
		return getBreed().getCreatureAttribute();
	}

	@Override
	public String getDeathSound() {
		return "mob.zombie.woodbreak";
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

	public <T extends GOTDragonHelper> T getHelper(Class<T> clazz) {
		return (T) helpers.get(clazz);
	}

	@Override
	public String getHurtSound() {
		if (isEgg()) {
			return "mob.zombie.wood";
		}
		return "mob.enderdragon.hit";
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

	public int getMaxDeathTime() {
		return 120;
	}

	@Override
	public double getMountedYOffset() {
		return (isSitting() ? 1.7f : 2.2f) * getScale();
	}

	public GOTDragonParticleHelper getParticleHelper() {
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

	public void setRidingPlayer(EntityPlayer player) {
		L.trace("setRidingPlayer({})", player.getCommandSenderName());
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
		return 5.0F;
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
			if (isServer() && consumeEquipped(player, FAVORITE_FOOD) != null) {
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
			if (isServer()) {
				player.addChatMessage(new ChatComponentTranslation("dragon.owned"));
			}
		} else if (!isChild() && riddenByEntity == null) {
			if (!isSaddled() && consumeEquipped(player, Items.saddle) != null) {
				if (isServer()) {
					setSaddled(true);
				}
			} else if (hasEquipped(player, Items.bone)) {
				if (isServer()) {
					aiSit.setSitting(!isSitting());
					isJumping = false;
					setPathToEntity(null);
				}
			} else if (getReproductionHelper().canReproduce() && consumeEquipped(player, FAVORITE_FOOD) != null) {
				if (isClient()) {
					getParticleHelper().spawnBodyParticles("heart");
				}
				func_146082_f(player);
			} else if (isSaddled() && !hasEquippedUsable(player) && isServer()) {
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

	@Override
	public boolean isGroundAIEnabled() {
		return super.isGroundAIEnabled() && !isEgg();
	}

	public boolean isHatchling() {
		return getLifeStageHelper().isHatchling();
	}

	public boolean isInvulnerableTo(DamageSource src) {
		Entity srcEnt = src.getEntity();
		if (srcEnt != null && (srcEnt == this || srcEnt == riddenByEntity)) {
			return true;
		}
		if ("drown".equals(src.damageType) && isEgg()) {
			return true;
		}
		return getBreed().isImmuneToDamage(src);
	}

	public boolean isJuvenile() {
		return getLifeStageHelper().isJuvenile();
	}

	@Override
	public boolean isOnLadder() {
		return false;
	}

	public boolean isSaddled() {
		return getBooleanData(INDEX_SADDLED);
	}

	public void setSaddled(boolean saddled) {
		L.trace("setSaddled({})", saddled);
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

		if (isEgg() || deathTime >= getMaxDeathTime()) {
			setDead();
		}

		deathTime++;
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
				setHomeArea((int) owner.posX, (int) owner.posY, (int) owner.posZ, HOME_RADIUS);
			}
		}

		super.onLivingUpdate();
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
			v *= 0.5;
			p *= 0.5;
		}
		playSound(sound, v, p);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSaddled(nbt.getBoolean(NBT_SADDLED));

		for (GOTDragonHelper helper : helpers.values()) {
			helper.readFromNBT(nbt);
		}
		setAttributes();
	}

	public void setAttackDamage(double damage) {
		L.trace("setAttackDamage({})", damage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(damage);
	}

	public void setAttributes() {
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(BASE_SPEED_GROUND);
		getEntityAttribute(MOVE_SPEED_AIR).setBaseValue(BASE_SPEED_AIR);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(BASE_HEALTH);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(BASE_DAMAGE);
	}

	public void setBooleanData(int index, boolean value) {
		dataWatcher.updateObject(index, value ? (byte) 1 : (byte) 0);
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
	public void updateAITasks() {
		if (!isEgg()) {
			super.updateAITasks();
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

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean(NBT_SADDLED, isSaddled());

		for (GOTDragonHelper helper : helpers.values()) {
			helper.writeToNBT(nbt);
		}
	}
}
