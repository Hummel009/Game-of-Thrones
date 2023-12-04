package got.common.entity.other;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTBannerProtection;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.animal.GOTEntityHorse;
import got.common.faction.GOTFaction;
import got.common.inventory.GOTContainerAnvil;
import got.common.inventory.GOTContainerCoinExchange;
import got.common.inventory.GOTContainerTrade;
import got.common.inventory.GOTContainerUnitTrade;
import got.common.item.GOTWeaponStats;
import got.common.item.other.*;
import got.common.item.weapon.GOTItemBow;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.item.weapon.GOTItemSpear;
import got.common.item.weapon.GOTItemTrident;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketNPCCombatStance;
import got.common.network.GOTPacketNPCFX;
import got.common.network.GOTPacketNPCIsEating;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.*;

public abstract class GOTEntityNPC extends EntityCreature implements IRangedAttackMob, GOTRandomSkinEntity {
	public static IAttribute npcAttackDamage = new RangedAttribute("got.npcAttackDamage", 2.0, 0.0, Double.MAX_VALUE).setDescription("GOT NPC Attack Damage");
	public static IAttribute npcAttackDamageExtra = new RangedAttribute("got.npcAttackDamageExtra", 0.0, 0.0, Double.MAX_VALUE).setDescription("GOT NPC Extra Attack Damage");
	public static IAttribute npcAttackDamageDrunk = new RangedAttribute("got.npcAttackDamageDrunk", 4.0, 0.0, Double.MAX_VALUE).setDescription("GOT NPC Drunken Attack Damage");
	public static IAttribute npcRangedAccuracy = new RangedAttribute("got.npcRangedAccuracy", 1.0, 0.0, Double.MAX_VALUE).setDescription("GOT NPC Ranged Accuracy");
	public static IAttribute horseAttackSpeed = new RangedAttribute("got.horseAttackSpeed", 1.7, 0.0, Double.MAX_VALUE).setDescription("GOT Horse Attack Speed");
	public static float MOUNT_RANGE_BONUS = 1.5f;
	public AttackMode currentAttackMode = AttackMode.IDLE;
	public GOTCapes npcCape;
	public GOTEntityQuestInfo questInfo;
	public GOTFamilyInfo familyInfo;
	public GOTHiredNPCInfo hiredNPCInfo;
	public GOTInventoryHiredReplacedItems hiredReplacedInv;
	public GOTInventoryNPCItems npcItemsInv;
	public GOTShields npcShield;
	public Item seedsItem;
	public GOTTraderNPCInfo traderNPCInfo;
	public ItemStack[] festiveItems = new ItemStack[5];
	public List<GOTFaction> killBonusFactions = new ArrayList<>();
	public List<ItemStack> enpouchedDrops = new ArrayList<>();
	public Random festiveRand = new Random();
	public UUID invasionID;
	public UUID prevAttackTarget;
	public boolean addedBurningPanic;
	public boolean canBannerBearerSpawnRiding;
	public boolean canBeMarried;
	public boolean clientCombatStance;
	public boolean clientIsEating;
	public boolean combatStance;
	public boolean enpouchNPCDrops;
	public boolean firstUpdatedAttackMode;
	public boolean hurtOnlyByPlates = true;
	public boolean initFestiveItems;
	public boolean isChilly;
	public boolean isConquestSpawning;
	public boolean isImmuneToFrost;
	public boolean isLegendaryNPC;
	public boolean isNPCPersistent;
	public boolean isNotHuman;
	public boolean isPassive;
	public boolean isTargetSeeker;
	public boolean isTraderEscort;
	public boolean liftBannerRestrictions;
	public boolean liftSpawnRestrictions;
	public boolean loadingFromNBT;
	public boolean prevCombatStance;
	public boolean ridingMount;
	public boolean setInitialHome;
	public boolean spawnRidingHorse;
	public boolean spawnsInDarkness;
	public float npcHeight;
	public float npcWidth = -1.0f;
	public int combatCooldown;
	public int initHomeRange = -1;
	public int initHomeX;
	public int initHomeY;
	public int initHomeZ;
	public int nearbyBannerFactor;
	public int npcTalkTick;

	protected GOTEntityNPC(World world) {
		super(world);
		canBeMarried = false;
	}

	public static int addTargetTasks(EntityCreature entity, int index, Class<? extends GOTEntityAINearestAttackableTargetBasic> c) {
		try {
			Constructor<? extends GOTEntityAINearestAttackableTargetBasic> constructor = c.getConstructor(EntityCreature.class, Class.class, Integer.TYPE, Boolean.TYPE, IEntitySelector.class);
			entity.targetTasks.addTask(index, constructor.newInstance(entity, EntityPlayer.class, 0, true, null));
			entity.targetTasks.addTask(index, constructor.newInstance(entity, EntityLiving.class, 0, true, new GOTNPCTargetSelector(entity)));
		} catch (Exception e) {
			FMLLog.severe("Error adding GOT target tasks to entity " + entity.toString());
			e.printStackTrace();
		}
		return index;
	}

	public int addTargetTasks(boolean seekTargets) {
		return addTargetTasks(seekTargets, GOTEntityAINearestAttackableTargetBasic.class);
	}

	public int addTargetTasks(boolean seekTargets, Class<? extends GOTEntityAINearestAttackableTargetBasic> c) {
		targetTasks.taskEntries.clear();
		targetTasks.addTask(1, new GOTEntityAIHiringPlayerHurtByTarget(this));
		targetTasks.addTask(2, new GOTEntityAIHiringPlayerHurtTarget(this));
		targetTasks.addTask(3, new GOTEntityAINPCHurtByTarget(this, false));
		isTargetSeeker = seekTargets;
		if (seekTargets) {
			return addTargetTasks(this, 4, c);
		}
		return 3;
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(npcAttackDamage);
		getAttributeMap().registerAttribute(npcAttackDamageExtra);
		getAttributeMap().registerAttribute(npcAttackDamageDrunk);
		getAttributeMap().registerAttribute(npcRangedAccuracy);
		getAttributeMap().registerAttribute(horseAttackSpeed);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float damage = (float) getEntityAttribute(npcAttackDamage).getAttributeValue();
		float weaponDamage = 0.0f;
		ItemStack weapon = getEquipmentInSlot(0);
		if (weapon != null) {
			weaponDamage = GOTWeaponStats.getMeleeDamageBonus(weapon) * 0.75f;
		}
		if (weaponDamage > 0.0f) {
			damage = weaponDamage;
		}
		damage += (float) getEntityAttribute(npcAttackDamageExtra).getAttributeValue();
		if (isDrunkard()) {
			damage += (float) getEntityAttribute(npcAttackDamageDrunk).getAttributeValue();
		}
		damage += nearbyBannerFactor * 0.5f;
		int knockbackModifier = 0;
		if (entity instanceof EntityLivingBase) {
			damage += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) entity);
			knockbackModifier += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) entity);
		}
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
		if (flag) {
			if (weapon != null && entity instanceof EntityLivingBase) {
				int weaponItemDamage = weapon.getItemDamage();
				weapon.getItem().hitEntity(weapon, (EntityLivingBase) entity, this);
				weapon.setItemDamage(weaponItemDamage);
			}
			if (knockbackModifier > 0) {
				entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
				motionX *= 0.6;
				motionZ *= 0.6;
			}
			int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
			if (fireAspectModifier > 0) {
				entity.setFire(fireAspectModifier * 4);
			}
			if (entity instanceof EntityLivingBase) {
				EnchantmentHelper.func_151384_a((EntityLivingBase) entity, this);
			}
			EnchantmentHelper.func_151385_b(this, entity);
		}
		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (riddenByEntity != null && damagesource.getEntity() == riddenByEntity) {
			return false;
		}
		if (nearbyBannerFactor > 0) {
			int i = 12 - nearbyBannerFactor;
			float f1 = f * i;
			f = f1 / 12.0f;
		}
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && damagesource.getEntity() instanceof GOTEntityNPC) {
			GOTEntityNPC attacker = (GOTEntityNPC) damagesource.getEntity();
			if (attacker.hiredNPCInfo.isActive && attacker.hiredNPCInfo.getHiringPlayer() != null) {
				recentlyHit = 100;
				attackingPlayer = null;
			}
		}
		if (flag && !worldObj.isRemote && hurtOnlyByPlates) {
			hurtOnlyByPlates = damagesource.getSourceOfDamage() instanceof GOTEntityPlate;
		}
		if (flag && !worldObj.isRemote && isInvasionSpawned() && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			GOTEntityInvasionSpawner invasion = GOTEntityInvasionSpawner.locateInvasionNearby(this, invasionID);
			if (invasion != null) {
				invasion.setWatchingInvasion((EntityPlayerMP) entityplayer, true);
			}
		}
		return flag;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		npcArrowAttack(target, f);
	}

	public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
		return true;
	}

	@Override
	public boolean canDespawn() {
		return !isNPCPersistent && !isLegendaryNPC && !hiredNPCInfo.isActive && !questInfo.anyActiveQuestPlayers();
	}

	public boolean canDropRares() {
		return !hiredNPCInfo.isActive || !isNotHuman;
	}

	public boolean canGetDrunk() {
		return !isChild() && !isTrader() && !isTraderEscort && !hiredNPCInfo.isActive;
	}

	public boolean canNPCTalk() {
		return isEntityAlive() && npcTalkTick >= getNPCTalkInterval();
	}

	@Override
	public boolean canPickUpLoot() {
		return false;
	}

	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return true;
	}

	public boolean canRenameNPC() {
		return false;
	}

	public boolean conquestSpawnIgnoresDarkness() {
		return true;
	}

	public GOTMiniQuest createMiniQuest() {
		return null;
	}

	public GOTNPCMount createMountToRide() {
		return new GOTEntityHorse(worldObj);
	}

	public ItemStack createNPCPouchDrop() {
		GOTFaction faction;
		ItemStack pouch = new ItemStack(GOTItems.pouch, 1, GOTItemPouch.getRandomPouchSize(rand));
		if (rand.nextBoolean() && (faction = getFaction()) != null) {
			GOTItemPouch.setPouchColor(pouch, faction.getFactionColor());
		}
		return pouch;
	}

	public void dropChestContents(GOTChestContents itemPool, int min, int max) {
		IInventory drops = new InventoryBasic("drops", false, max * 5);
		GOTChestContents.fillInventory(drops, rand, itemPool, MathHelper.getRandomIntegerInRange(rand, min, max), true);
		for (int i = 0; i < drops.getSizeInventory(); ++i) {
			ItemStack item = drops.getStackInSlot(i);
			if (item == null) {
				continue;
			}
			entityDropItem(item, 0.0f);
		}
	}

	@Override
	public void dropEquipment(boolean flag, int i) {
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		hiredReplacedInv.dropAllReplacedItems();
		dropNPCEquipment(flag, i);
		if (flag && canDropRares()) {
			int coinChance = 8 - i * 2;
			if (rand.nextInt(Math.max(coinChance, 1)) == 0) {
				int coins = getRandomCoinDropAmount();
				dropItem(GOTItems.coin, coins * MathHelper.getRandomIntegerInRange(rand, 1, i + 1));
			}
			int rareChance = 50 - i * 5;
			if (rand.nextInt(Math.max(rareChance, 1)) == 0) {
				dropChestContents(GOTChestContents.TREASURE, 1, 1);
			}
		}
		if (flag && canDropRares()) {
			int modChance = 60;
			modChance -= i * 5;
			if (rand.nextInt(Math.max(modChance, 1)) == 0) {
				ItemStack modItem = GOTItemModifierTemplate.getRandomCommonTemplate(rand);
				entityDropItem(modItem, 0.0f);
			}
		}
	}

	public void dropItemList(Collection<ItemStack> items) {
		dropItemList(items, true);
	}

	public void dropItemList(Collection<ItemStack> items, boolean applyOwnership) {
		if (!items.isEmpty()) {
			for (ItemStack item : items) {
				npcDropItem(item, 0.0f, true, applyOwnership);
			}
		}
	}

	public void dropNPCAmmo(Item item, int i) {
		int ammo = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < ammo; ++l) {
			dropItem(item, 1);
		}
	}

	public void dropNPCArrows(int i) {
		dropNPCAmmo(Items.arrow, i);
	}

	public void dropNPCCrossbowBolts(int i) {
		dropNPCAmmo(GOTItems.crossbowBolt, i);
	}

	public void dropNPCEquipment(boolean flag, int i) {
		if (flag) {
			int j;
			int equipmentCount = 0;
			for (j = 0; j < 5; ++j) {
				if (getEquipmentInSlot(j) == null) {
					continue;
				}
				++equipmentCount;
			}
			if (equipmentCount > 0) {
				for (j = 0; j < 5; ++j) {
					ItemStack equipmentDrop = getEquipmentInSlot(j);
					if (equipmentDrop == null) {
						continue;
					}
					boolean dropGuaranteed = equipmentDropChances[j] >= 1.0f;
					if (!dropGuaranteed) {
						int chance = 20 * equipmentCount - i * 4 * equipmentCount;
						if (rand.nextInt(Math.max(chance, 1)) != 0) {
							continue;
						}
					}
					if (!dropGuaranteed) {
						int dropDamage = MathHelper.floor_double(equipmentDrop.getItem().getMaxDamage() * (0.5f + rand.nextFloat() * 0.25f));
						equipmentDrop.setItemDamage(dropDamage);
					}
					entityDropItem(equipmentDrop, 0.0f);
					setCurrentItemOrArmor(j, null);
				}
			}
		}
	}

	@Override
	public EntityItem entityDropItem(ItemStack item, float offset) {
		return npcDropItem(item, offset, true, true);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		familyInfo = new GOTFamilyInfo(this);
		questInfo = new GOTEntityQuestInfo(this);
		hiredNPCInfo = new GOTHiredNPCInfo(this);
		traderNPCInfo = new GOTTraderNPCInfo(this);
		setupNPCGender();
		setupNPCName();
		npcItemsInv = new GOTInventoryNPCItems(this);
		hiredReplacedInv = new GOTInventoryHiredReplacedItems(this);
	}

	public void fillPouchFromListAndRetainUnfilled(ItemStack pouch, List<ItemStack> items) {
		Collection<ItemStack> pouchContents = new ArrayList<>();
		while (!items.isEmpty()) {
			pouchContents.add(items.remove(0));
		}
		for (ItemStack itemstack : pouchContents) {
			if (GOTItemPouch.tryAddItemToPouch(pouch, itemstack, false)) {
				continue;
			}
			items.add(itemstack);
		}
	}

	@Override
	public void func_110163_bv() {
	}

	@Override
	public ItemStack func_130225_q(int i) {
		return getEquipmentInSlot(i + 1);
	}

	public boolean generatesControlZone() {
		return true;
	}

	public float getAlignmentBonus() {
		return 0.0f;
	}

	public String getAttackSound() {
		return null;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (liftSpawnRestrictions) {
			return 1.0f;
		}
		if (!isConquestSpawning || !conquestSpawnIgnoresDarkness()) {
			BiomeGenBase biome;
			if (spawnsInDarkness && (biome = worldObj.getBiomeGenForCoords(i, k)) instanceof GOTBiome && ((GOTBiome) biome).canSpawnHostilesInDay()) {
				return 1.0f;
			}
			if (spawnsInDarkness) {
				return 0.5f - worldObj.getLightBrightness(i, j, k);
			}
		}
		return 0.0f;
	}

	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return null;
	}

	@Override
	public boolean getCanSpawnHere() {
		return (!spawnsInDarkness || liftSpawnRestrictions || isConquestSpawning && conquestSpawnIgnoresDarkness() || isValidLightLevelForDarkSpawn()) && super.getCanSpawnHere() && (liftBannerRestrictions || !GOTBannerProtection.isProtected(worldObj, this, GOTBannerProtection.forNPC(this), false) && (isConquestSpawning || !GOTEntityNPCRespawner.isSpawnBlocked(this)));
	}

	@Override
	public String getCommandSenderName() {
		if (hasCustomNameTag()) {
			return super.getCommandSenderName();
		}
		String entityName = getEntityClassName();
		String npcName = getNPCName();
		if (GOT.isNewYear()) {
			npcName = "Dead Morose";
		}
		return getNPCFormattedName(npcName, entityName);
	}

	public float getDrunkenSpeechFactor() {
		if (rand.nextInt(3) == 0) {
			return MathHelper.randomFloatClamp(rand, 0.0f, 0.3f);
		}
		return 0.0f;
	}

	public String getEntityClassName() {
		return super.getCommandSenderName();
	}

	@Override
	public ItemStack getEquipmentInSlot(int i) {
		if (worldObj.isRemote) {
			if (!initFestiveItems) {
				festiveRand.setSeed(getEntityId() * 341873128712L);
				if (GOT.isGuyFawkes()) {
					festiveItems[4] = new ItemStack(GOTItems.anonymousMask);
				} else if (GOT.isNewYear() && festiveRand.nextInt(3) == 0) {
					ItemStack hat;
					if (rand.nextBoolean()) {
						hat = new ItemStack(GOTItems.leatherHat);
						GOTItemLeatherHat.setHatColor(hat, 13378587);
						GOTItemLeatherHat.setFeatherColor(hat, 16777215);
						festiveItems[4] = hat;
					} else {
						hat = new ItemStack(GOTItems.partyHat);
						float hue = rand.nextFloat();
						GOTItemPartyHat.setHatColor(hat, Color.HSBtoRGB(hue, 1.0f, 1.0f));
					}
					festiveItems[4] = hat;
				}
				initFestiveItems = true;
			}
			if (festiveItems[i] != null) {
				return festiveItems[i];
			}
		}
		return super.getEquipmentInSlot(i);
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 4 + rand.nextInt(3);
	}

	public GOTFaction getFaction() {
		return GOTFaction.UNALIGNED;
	}

	public float getFireArrowChance() {
		return 0.0f;
	}

	public ItemStack getHeldItemLeft() {
		if (this instanceof GOTBannerBearer) {
			GOTBannerBearer bannerBearer = (GOTBannerBearer) this;
			return new ItemStack(GOTItems.banner, 1, bannerBearer.getBannerType().bannerID);
		}
		if (isTrader() && !isLegendaryNPC && !(this instanceof GOTMercenary)) {
			boolean showCoin = npcShield == null || !clientCombatStance && hiredNPCInfo.getHiringPlayerUUID() == null;
			if (showCoin) {
				return new ItemStack(GOTItems.coin);
			}
		}
		return null;
	}

	public GOTFaction getHiringFaction() {
		return getFaction();
	}

	public UUID getInvasionID() {
		return invasionID;
	}

	public void setInvasionID(UUID id) {
		invasionID = id;
	}

	public GOTAchievement getKillAchievement() {
		return null;
	}

	public double getMaxCombatRange() {
		double d = getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
		return d * 0.95;
	}

	public double getMaxCombatRangeSq() {
		double d = getMaxCombatRange();
		return d * d;
	}

	public double getMeleeRange() {
		double d = 4.0 + width * width;
		if (ridingMount) {
			d *= MOUNT_RANGE_BONUS;
		}
		return d;
	}

	public double getMeleeRangeSq() {
		double d = getMeleeRange();
		return d * d;
	}

	public int getMiniquestColor() {
		return getFaction().getFactionColor();
	}

	public String getNPCFormattedName(String npcName, String entityName) {
		if (npcName.equals(entityName)) {
			return entityName;
		}
		return StatCollector.translateToLocalFormatted(npcName) + ", " + StatCollector.translateToLocalFormatted(entityName);
	}

	public String getNPCName() {
		return super.getCommandSenderName();
	}

	public float getNPCScale() {
		return isChild() ? 0.5f : 1.0f;
	}

	public int getNPCTalkInterval() {
		return 40;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		int id = GOTEntityRegistry.getEntityID(this);
		if (GOTEntityRegistry.spawnEggs.containsKey(id)) {
			return new ItemStack(GOTItems.spawnEgg, 1, id);
		}
		return null;
	}

	public float getPoisonedArrowChance() {
		return 0.0f;
	}

	public int getRandomCoinDropAmount() {
		return 1 + (int) Math.round(Math.pow(1.0 + Math.abs(rand.nextGaussian()), 3.0) * 0.25);
	}

	public int getSpawnCountValue() {
		if (isNPCPersistent || hiredNPCInfo.isActive) {
			return 0;
		}
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
		if (biome instanceof GOTBiome) {
			return ((GOTBiome) biome).spawnCountMultiplier();
		}
		return 1;
	}

	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	public GOTAchievement getTalkAchievement() {
		return null;
	}

	@Override
	public int getTalkInterval() {
		return 200;
	}

	public void initCreatureForHire(IEntityLivingData data) {
		spawnRidingHorse = false;
		onSpawnWithEgg(data);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && canNPCTalk()) {
			if (questInfo.interact(entityplayer) || getAttackTarget() == null && speakTo(entityplayer)) {
				return true;
			}
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isAimingRanged() {
		Item item;
		ItemStack itemstack = getHeldItem();
		if (itemstack != null && !((item = itemstack.getItem()) instanceof GOTItemSpear) && !(item instanceof GOTItemTrident) && itemstack.getItemUseAction() == EnumAction.bow) {
			EntityLivingBase target = getAttackTarget();
			return target != null && getDistanceSqToEntity(target) < getMaxCombatRangeSq();
		}
		return false;
	}

	@Override
	public boolean isChild() {
		return familyInfo.getAge() < 0;
	}

	public boolean isCivilianNPC() {
		return !isLegendaryNPC && !isTargetSeeker && !(this instanceof GOTUnitTradeable) && !(this instanceof GOTMercenary);
	}

	public boolean isDrunkard() {
		return familyInfo.isDrunk();
	}

	public boolean isFriendly(EntityPlayer entityplayer) {
		return getAttackTarget() != entityplayer && attackingPlayer != entityplayer;
	}

	public boolean isFriendlyAndAligned(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double dist) {
		EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
		return entityplayer != null && !GOTLevelData.getData(entityplayer).getMiniQuestsForEntity(this, true).isEmpty() || super.isInRangeToRenderDist(dist);
	}

	public boolean isInvasionSpawned() {
		return invasionID != null;
	}

	public boolean isLegendaryNPC() {
		return isLegendaryNPC;
	}

	public boolean isTrader() {
		return this instanceof GOTTradeable || this instanceof GOTUnitTradeable || this instanceof GOTMercenary;
	}

	public boolean isValidLightLevelForDarkSpawn() {
		BiomeGenBase biome;
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		if (spawnsInDarkness && (biome = worldObj.getBiomeGenForCoords(i, k)) instanceof GOTBiome && ((GOTBiome) biome).canSpawnHostilesInDay()) {
			return true;
		}
		if (worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > rand.nextInt(32)) {
			return false;
		}
		int l = worldObj.getBlockLightValue(i, j, k);
		if (worldObj.isThundering()) {
			int i1 = worldObj.skylightSubtracted;
			worldObj.skylightSubtracted = 10;
			l = worldObj.getBlockLightValue(i, j, k);
			worldObj.skylightSubtracted = i1;
		}
		return l <= rand.nextInt(8);
	}

	public boolean lootsExtraCoins() {
		return false;
	}

	public void markNPCSpoken() {
		npcTalkTick = 0;
	}

	public void npcArrowAttack(EntityLivingBase target, float f) {
		ItemStack heldItem = getHeldItem();
		float str = 1.3f + getDistanceToEntity(target) / 80.0f;
		float accuracy = (float) getEntityAttribute(npcRangedAccuracy).getAttributeValue();
		float poisonChance = getPoisonedArrowChance();
		float fireChance = getFireArrowChance();
		EntityArrow arrow = rand.nextFloat() < fireChance ? new GOTEntityArrowFire(worldObj, this, target, str, accuracy) : rand.nextFloat() < poisonChance ? new GOTEntityArrowPoisoned(worldObj, this, target, str, accuracy) : new EntityArrow(worldObj, this, target, str * GOTItemBow.getLaunchSpeedFactor(heldItem), accuracy);
		if (heldItem != null) {
			GOTItemBow.applyBowModifiers(arrow, heldItem);
		}
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(arrow);
	}

	public void npcCrossbowAttack(EntityLivingBase target, float f) {
		ItemStack heldItem = getHeldItem();
		float str = 1.0f + getDistanceToEntity(target) / 16.0f * 0.015f;
		boolean poison = rand.nextFloat() < getPoisonedArrowChance();
		ItemStack boltItem = new ItemStack(poison ? GOTItems.crossbowBoltPoisoned : GOTItems.crossbowBolt);
		GOTEntityCrossbowBolt bolt = new GOTEntityCrossbowBolt(worldObj, this, target, boltItem, str * GOTItemCrossbow.getCrossbowLaunchSpeedFactor(heldItem), 1.0f);
		if (heldItem != null) {
			GOTItemCrossbow.applyCrossbowModifiers(bolt, heldItem);
		}
		playSound("got:item.crossbow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(bolt);
	}

	public EntityItem npcDropItem(ItemStack item, float offset, boolean enpouch, boolean applyOwnership) {
		if (applyOwnership && item != null && item.getItem() != null && item.getMaxStackSize() == 1) {
			GOTItemOwnership.addPreviousOwner(item, getCommandSenderName());
		}
		if (enpouch && enpouchNPCDrops && item != null) {
			enpouchedDrops.add(item);
			return null;
		}
		return super.entityDropItem(item, offset);
	}

	public void onArtificalSpawn() {
	}

	public void onAttackModeChange(AttackMode mode, boolean mounted) {
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		EntityPlayer entityplayer;
		GOTEntityInvasionSpawner invasion;
		enpouchNPCDrops = true;
		hiredNPCInfo.onDeath(damagesource);
		super.onDeath(damagesource);
		if (!worldObj.isRemote && recentlyHit > 0 && canDropRares() && GOT.canDropLoot(worldObj) && rand.nextInt(60) == 0) {
			ItemStack pouch = createNPCPouchDrop();
			fillPouchFromListAndRetainUnfilled(pouch, enpouchedDrops);
			enpouchNPCDrops = false;
			entityDropItem(pouch, 0.0f);
		}
		enpouchNPCDrops = false;
		dropItemList(enpouchedDrops, false);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) damagesource.getEntity();
			if (hurtOnlyByPlates && damagesource.getSourceOfDamage() instanceof GOTEntityPlate) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killUsingOnlyPlates);
			}
			if (damagesource.getSourceOfDamage() instanceof GOTEntityPebble && ((GOTEntityPebble) damagesource.getSourceOfDamage()).isSling() && width * width * height > 5.0f) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killLargeMobWithSlingshot);
			}
			if (getKillAchievement() != null) {
				GOTLevelData.getData(entityplayer).addAchievement(getKillAchievement());
			}
		}
		if (!worldObj.isRemote && (this instanceof GOTTradeable || this instanceof GOTUnitTradeable) && !isLegendaryNPC) {
			GOTEntityTraderRespawn entity = new GOTEntityTraderRespawn(worldObj);
			entity.setLocationAndAngles(posX, boundingBox.minY + height / 2.0f, posZ, 0.0f, 0.0f);
			entity.copyTraderDataFrom(this);
			worldObj.spawnEntityInWorld(entity);
			entity.onSpawn();
		}
		questInfo.onDeath();
		if (!worldObj.isRemote && isInvasionSpawned() && (entityplayer = GOT.getDamagingPlayerIncludingUnits(damagesource)) != null && (invasion = GOTEntityInvasionSpawner.locateInvasionNearby(this, invasionID)) != null) {
			invasion.addPlayerKill(entityplayer);
			if (damagesource.getEntity() == entityplayer) {
				invasion.setWatchingInvasion((EntityPlayerMP) entityplayer, true);
			}
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		hiredNPCInfo.onKillEntity(entity);
		if (lootsExtraCoins() && !worldObj.isRemote && entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).canDropRares() && rand.nextInt(2) == 0) {
			int coins = getRandomCoinDropAmount();
			coins = (int) (coins * MathHelper.randomFloatClamp(rand, 1.0f, 3.0f));
			if (coins > 0) {
				entity.dropItem(GOTItems.coin, coins);
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		rescaleNPC(getNPCScale());
		updateCombat();
		if (ticksExisted % 10 == 0) {
			updateNearbyBanners();
		}
		familyInfo.onUpdate();
		questInfo.onUpdate();
		hiredNPCInfo.onUpdate();
		if (this instanceof GOTTradeable) {
			traderNPCInfo.onUpdate();
		}
		if ((this instanceof GOTTradeable || this instanceof GOTUnitTradeable) && !worldObj.isRemote) {
			if (!setInitialHome) {
				if (hasHome()) {
					initHomeX = getHomePosition().posX;
					initHomeY = getHomePosition().posY;
					initHomeZ = getHomePosition().posZ;
					initHomeRange = (int) func_110174_bM();
				}
				setInitialHome = true;
			}
			int preventKidnap = GOTConfig.preventTraderKidnap;
			if (preventKidnap > 0 && initHomeRange > 0 && getDistanceSq(initHomeX + 0.5, initHomeY + 0.5, initHomeZ + 0.5) > preventKidnap * preventKidnap) {
				if (ridingEntity != null) {
					mountEntity(null);
				}
				worldObj.getChunkFromBlockCoords(initHomeX, initHomeZ);
				setLocationAndAngles(initHomeX + 0.5, initHomeY, initHomeZ + 0.5, rotationYaw, rotationPitch);
			}
		}
		if (!worldObj.isRemote && !addedBurningPanic) {
			GOTEntityUtils.removeAITask(this, GOTEntityAIBurningPanic.class);
			if (shouldBurningPanic()) {
				tasks.addTask(0, new GOTEntityAIBurningPanic(this, 1.5));
			}
			addedBurningPanic = true;
		}
		if (!worldObj.isRemote && isEntityAlive() && (isTrader() || hiredNPCInfo.isActive) && getAttackTarget() == null) {
			float healAmount = 0.0f;
			if (ticksExisted % 40 == 0) {
				healAmount += 1.0f;
			}
			if (hiredNPCInfo.isActive && nearbyBannerFactor > 0 && ticksExisted % (240 - nearbyBannerFactor * 40) == 0) {
				healAmount += 1.0f;
			}
			if (healAmount > 0.0f) {
				heal(healAmount);
				if (ridingEntity instanceof EntityLivingBase && !(ridingEntity instanceof GOTEntityNPC)) {
					((EntityLivingBase) ridingEntity).heal(healAmount);
				}
			}
		}
		if (!worldObj.isRemote && isEntityAlive() && getAttackTarget() == null) {
			boolean guiOpen = false;
			if (this instanceof GOTTradeable || this instanceof GOTUnitTradeable || this instanceof GOTMercenary) {
				List<EntityPlayer> players = worldObj.playerEntities;
				for (EntityPlayer entityplayer : players) {
					Container container = entityplayer.openContainer;
					if (container instanceof GOTContainerTrade && ((GOTContainerTrade) container).theTraderNPC == this || container instanceof GOTContainerUnitTrade && ((GOTContainerUnitTrade) container).theLivingTrader == this) {
						guiOpen = true;
						break;
					}
					if (container instanceof GOTContainerCoinExchange && ((GOTContainerCoinExchange) container).theTraderNPC == this) {
						guiOpen = true;
						break;
					}
					if (!(container instanceof GOTContainerAnvil) || ((GOTContainerAnvil) container).theNPC != this) {
						continue;
					}
					guiOpen = true;
					break;
				}
			}
			if (hiredNPCInfo.isActive && hiredNPCInfo.isGuiOpen) {
				guiOpen = true;
			}
			if (questInfo.anyOpenOfferPlayers()) {
				guiOpen = true;
			}
			if (guiOpen) {
				getNavigator().clearPathEntity();
				if (ridingEntity instanceof GOTNPCMount) {
					((EntityLiving) ridingEntity).getNavigator().clearPathEntity();
				}
			}
		}
		updateArmSwingProgress();
		if (npcTalkTick < getNPCTalkInterval()) {
			++npcTalkTick;
		}
		if (!worldObj.isRemote && hasHome() && !isWithinHomeDistanceCurrentPosition()) {
			int homeX = getHomePosition().posX;
			int homeY = getHomePosition().posY;
			int homeZ = getHomePosition().posZ;
			int homeRange = (int) func_110174_bM();
			double maxDist = homeRange + 128.0;
			double distToHome = getDistance(homeX + 0.5, homeY + 0.5, homeZ + 0.5);
			if (distToHome > maxDist) {
				detachHome();
			} else if (getAttackTarget() == null && getNavigator().noPath()) {
				detachHome();
				boolean goDirectlyHome = worldObj.blockExists(homeX, homeY, homeZ) && hiredNPCInfo.isGuardMode() && distToHome < 16.0;
				double homeSpeed = 1.3;
				if (goDirectlyHome) {
					getNavigator().tryMoveToXYZ(homeX + 0.5, homeY + 0.5, homeZ + 0.5, homeSpeed);
				} else {
					Vec3 path = null;
					for (int l = 0; l < 16 && path == null; ++l) {
						path = RandomPositionGenerator.findRandomTargetBlockTowards(this, 8, 7, Vec3.createVectorHelper(homeX, homeY, homeZ));
					}
					if (path != null) {
						getNavigator().tryMoveToXYZ(path.xCoord, path.yCoord, path.zCoord, homeSpeed);
					}
				}
				setHomeArea(homeX, homeY, homeZ, homeRange);
			}
		}
		if (isChilly && motionX * motionX + motionY * motionY + motionZ * motionZ >= 0.01) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			double d1 = boundingBox.minY + MathHelper.randomFloatClamp(rand, 0.2f, 0.7f) * height;
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			GOT.proxy.spawnParticle("chill", d, d1, d2, -motionX * 0.5, 0.0, -motionZ * 0.5);
		}
	}

	public void onPlayerStartTracking(EntityPlayerMP entityplayer) {
		hiredNPCInfo.sendBasicData(entityplayer);
		familyInfo.sendData(entityplayer);
		questInfo.sendData(entityplayer);
		sendCombatStance(entityplayer);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		if (!worldObj.isRemote) {
			if (spawnRidingHorse && (!(this instanceof GOTBannerBearer) || canBannerBearerSpawnRiding)) {
				GOTNPCMount mount = createMountToRide();
				EntityCreature livingMount = (EntityCreature) mount;
				livingMount.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				if (worldObj.func_147461_a(livingMount.boundingBox).isEmpty()) {
					livingMount.onSpawnWithEgg(null);
					worldObj.spawnEntityInWorld(livingMount);
					mountEntity(livingMount);
					if (!(mount instanceof GOTEntityNPC)) {
						setRidingHorse(true);
						mount.setBelongsToNPC(true);
						GOTMountFunctions.setNavigatorRangeFromNPC(mount, this);
					}
				}
			}
			if (traderNPCInfo.getBuyTrades() != null && rand.nextInt(10000) == 0) {
				for (GOTTradeEntry trade : traderNPCInfo.getBuyTrades()) {
					trade.setCost(trade.getCost() * 100);
				}
				familyInfo.setName("Мойша Рабинович");
			}
		}
		return data;
	}

	public void playTradeSound() {
		playSound("got:event.trade", 0.5f, 1.0f + (rand.nextFloat() - rand.nextFloat()) * 0.1f);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		loadingFromNBT = true;
		super.readEntityFromNBT(nbt);
		familyInfo.readFromNBT(nbt);
		questInfo.readFromNBT(nbt);
		hiredNPCInfo.readFromNBT(nbt);
		traderNPCInfo.readFromNBT(nbt);
		npcItemsInv.readFromNBT(nbt);
		hiredReplacedInv.readFromNBT(nbt);
		if (nbt.hasKey("NPCHomeRadius")) {
			int x = nbt.getInteger("NPCHomeX");
			int y = nbt.getInteger("NPCHomeY");
			int z = nbt.getInteger("NPCHomeZ");
			int r = nbt.getInteger("NPCHomeRadius");
			setHomeArea(x, y, z, r);
		}
		if (nbt.hasKey("NPCPersistent")) {
			isNPCPersistent = nbt.getBoolean("NPCPersistent");
		}
		hurtOnlyByPlates = nbt.getBoolean("HurtOnlyByPlates");
		ridingMount = nbt.getBoolean("RidingHorse");
		isPassive = nbt.getBoolean("NPCPassive");
		isTraderEscort = nbt.getBoolean("TraderEscort");
		if (nbt.hasKey("BonusFactions")) {
			NBTTagList bonusTags = nbt.getTagList("BonusFactions", 8);
			for (int i = 0; i < bonusTags.tagCount(); ++i) {
				String fName = bonusTags.getStringTagAt(i);
				GOTFaction f = GOTFaction.forName(fName);
				if (f == null) {
					continue;
				}
				killBonusFactions.add(f);
			}
		}
		if (nbt.hasKey("InvasionID")) {
			String invID = nbt.getString("InvasionID");
			try {
				invasionID = UUID.fromString(invID);
			} catch (IllegalArgumentException e) {
				FMLLog.warning("Hummel009: Error loading NPC - %s is not a valid invasion UUID", invID);
				e.printStackTrace();
			}
		}
		setInitialHome = nbt.getBoolean("SetInitHome");
		initHomeX = nbt.getInteger("InitHomeX");
		initHomeY = nbt.getInteger("InitHomeY");
		initHomeZ = nbt.getInteger("InitHomeZ");
		initHomeRange = nbt.getInteger("InitHomeR");
		loadingFromNBT = false;
	}

	public void refreshCurrentAttackMode() {
		onAttackModeChange(currentAttackMode, ridingMount);
	}

	public void rescaleNPC(float f) {
		super.setSize(npcWidth * f, npcHeight * f);
	}

	public void sendCombatStance(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketNPCCombatStance(getEntityId(), combatStance);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendIsEatingPacket(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketNPCIsEating(getEntityId(), npcItemsInv.getIsEating());
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendIsEatingToWatchers() {
		int x = MathHelper.floor_double(posX) >> 4;
		int z = MathHelper.floor_double(posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) worldObj).getPlayerManager();
		List<EntityPlayer> players = worldObj.playerEntities;
		for (EntityPlayer obj : players) {
			if (playermanager.isPlayerWatchingChunk((EntityPlayerMP) obj, x, z)) {
				sendIsEatingPacket((EntityPlayerMP) obj);
			}
		}
	}

	public void sendSpeechBank(EntityPlayer entityplayer, String speechBank) {
		sendSpeechBank(entityplayer, speechBank, null);
	}

	public void sendSpeechBank(EntityPlayer entityplayer, String speechBank, CharSequence location, CharSequence objective) {
		if (GOT.isUkraine()) {
			GOTSpeech.sendSpeech(entityplayer, this, "Слава Україні!");
		} else {
			GOTSpeech.sendSpeech(entityplayer, this, GOTSpeech.getRandomSpeechForPlayer(this, speechBank, entityplayer, location, objective));
		}
		markNPCSpoken();
	}

	public void sendSpeechBank(EntityPlayer entityplayer, String speechBank, GOTMiniQuest miniquest) {
		String objective = null;
		if (miniquest != null) {
			objective = miniquest.getProgressedObjectiveInSpeech();
		}
		sendSpeechBank(entityplayer, speechBank, null, objective);
	}

	@Override
	public void setAttackTarget(EntityLivingBase target) {
		boolean speak = target != null && getEntitySenses().canSee(target) && rand.nextInt(3) == 0;
		setAttackTarget(target, speak);
	}

	@SuppressWarnings("all")
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		EntityLivingBase prevEntityTarget = getAttackTarget();
		super.setAttackTarget(target);
		hiredNPCInfo.onSetTarget(target, prevEntityTarget);
		if (target != null && !target.getUniqueID().equals(prevAttackTarget)) {
			prevAttackTarget = target.getUniqueID();
			if (!worldObj.isRemote) {
				EntityPlayer entityplayer;
				String speechBank;
				if (getAttackSound() != null) {
					worldObj.playSoundAtEntity(this, getAttackSound(), getSoundVolume(), getSoundPitch());
				}
				if (target instanceof EntityPlayer && speak && (speechBank = getSpeechBank(entityplayer = (EntityPlayer) target)) != null) {
					IEntitySelector selectorAttackingNPCs = new IEntitySelector() {

						@Override
						public boolean isEntityApplicable(Entity entity) {
							if (entity instanceof GOTEntityNPC) {
								GOTEntityNPC npc = (GOTEntityNPC) entity;
								return npc.isAIEnabled() && npc.isEntityAlive() && npc.getAttackTarget() == entityplayer;
							}
							return false;
						}
					};
					double range = 16.0;
					List<? extends Entity> nearbyMobs = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(range, range, range), selectorAttackingNPCs);
					if (nearbyMobs.size() <= 5) {
						sendSpeechBank(entityplayer, speechBank);
					}
				}
			}
		}
	}

	public void setConquestSpawning(boolean flag) {
		isConquestSpawning = flag;
	}

	@Override
	public void setCustomNameTag(String name) {
		if (canRenameNPC() || loadingFromNBT) {
			super.setCustomNameTag(name);
		}
	}

	@Override
	public void setDead() {
		super.setDead();
		if (deathTime == 0 && ridingEntity != null) {
			ridingEntity.setDead();
		}
	}

	public void setIsLegendaryNPC() {
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		isLegendaryNPC = true;
		spawnRidingHorse = false;
		questInfo.setIsLegendaryQuest();
	}

	public void setRidingHorse(boolean flag) {
		ridingMount = flag;
		double d = getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
		d = flag ? d * 1.5 : d / 1.5;
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(d);
	}

	@Override
	public void setSize(float f, float f1) {
		boolean flag = npcWidth > 0.0f;
		npcWidth = f;
		npcHeight = f1;
		if (!flag) {
			rescaleNPC(1.0f);
		}
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}

	public void setupNPCGender() {
	}

	public void setupNPCName() {
	}

	public boolean shouldBurningPanic() {
		return true;
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}

	public boolean shouldRenderNPCChest() {
		return (!(this instanceof GOTEntityProstitute) || !hasCustomNameTag() || !"Ayase".equalsIgnoreCase(getCustomNameTag())) && !familyInfo.isMale() && !isChild() && getEquipmentInSlot(3) == null;
	}

	public boolean shouldRenderNPCHair() {
		return true;
	}

	public void spawnFoodParticles() {
		if (getHeldItem() == null) {
			return;
		}
		if (worldObj.isRemote) {
			for (int i = 0; i < 5; ++i) {
				Vec3 vec1 = Vec3.createVectorHelper((rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
				vec1.rotateAroundX(-rotationPitch * 3.1415927f / 180.0f);
				vec1.rotateAroundY(-rotationYaw * 3.1415927f / 180.0f);
				Vec3 vec2 = Vec3.createVectorHelper((rand.nextFloat() - 0.5) * 0.3, -rand.nextFloat() * 0.6 - 0.3, 0.6);
				vec2.rotateAroundX(-rotationPitch * 3.1415927f / 180.0f);
				vec2.rotateAroundY(-rotationYaw * 3.1415927f / 180.0f);
				vec2 = vec2.addVector(posX, posY + getEyeHeight(), posZ);
				worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(getHeldItem().getItem()), vec2.xCoord, vec2.yCoord, vec2.zCoord, vec1.xCoord, vec1.yCoord + 0.05, vec1.zCoord);
			}
		} else {
			IMessage packet = new GOTPacketNPCFX(getEntityId(), GOTPacketNPCFX.FXType.EATING);
			GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(this, 32.0));
		}
	}

	public void spawnHearts() {
		if (worldObj.isRemote) {
			for (int i = 0; i < 8; ++i) {
				double d = rand.nextGaussian() * 0.02;
				double d1 = rand.nextGaussian() * 0.02;
				double d2 = rand.nextGaussian() * 0.02;
				worldObj.spawnParticle("heart", posX + rand.nextFloat() * width * 2.0f - width, posY + 0.5 + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0f - width, d, d1, d2);
			}
		} else {
			IMessage packet = new GOTPacketNPCFX(getEntityId(), GOTPacketNPCFX.FXType.HEARTS);
			GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(this, 32.0));
		}
	}

	public void spawnSmokes() {
		if (worldObj.isRemote) {
			for (int i = 0; i < 8; ++i) {
				double d = rand.nextGaussian() * 0.02;
				double d1 = rand.nextGaussian() * 0.02;
				double d2 = rand.nextGaussian() * 0.02;
				worldObj.spawnParticle("smoke", posX + rand.nextFloat() * width * 2.0f - width, posY + 0.5 + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0f - width, d, d1, d2);
			}
		} else {
			IMessage packet = new GOTPacketNPCFX(getEntityId(), GOTPacketNPCFX.FXType.SMOKE);
			GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(this, 32.0));
		}
	}

	public boolean speakTo(EntityPlayer entityplayer) {
		String speechBank = getSpeechBank(entityplayer);
		if (speechBank != null) {
			sendSpeechBank(entityplayer, speechBank);
			if (getTalkAchievement() != null) {
				GOTLevelData.getData(entityplayer).addAchievement(getTalkAchievement());
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("all")
	public void updateCombat() {
		EntityLivingBase entity;
		if (!worldObj.isRemote && getAttackTarget() != null && (!(entity = getAttackTarget()).isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
			setAttackTarget(null);
		}
		boolean changedMounted = false;
		boolean changedAttackMode = false;
		if (!worldObj.isRemote) {
			boolean isRidingMountNow;
			isRidingMountNow = ridingEntity instanceof EntityLiving && ridingEntity.isEntityAlive() && !(ridingEntity instanceof GOTEntityNPC);
			if (ridingMount != isRidingMountNow) {
				setRidingHorse(isRidingMountNow);
				changedMounted = true;
			}
		}
		if (!worldObj.isRemote && !isChild()) {
			boolean carryingSpearWithBackup;
			ItemStack weapon = getEquipmentInSlot(0);
			carryingSpearWithBackup = weapon != null && weapon.getItem() instanceof GOTItemSpear && npcItemsInv.getSpearBackup() != null;
			if (getAttackTarget() != null) {
				double d = getDistanceSqToEntity(getAttackTarget());
				if (d < getMeleeRangeSq() || carryingSpearWithBackup) {
					if (currentAttackMode != AttackMode.MELEE) {
						currentAttackMode = AttackMode.MELEE;
						changedAttackMode = true;
					}
				} else if (d < getMaxCombatRangeSq() && currentAttackMode != AttackMode.RANGED) {
					currentAttackMode = AttackMode.RANGED;
					changedAttackMode = true;
				}
			} else if (currentAttackMode != AttackMode.IDLE) {
				currentAttackMode = AttackMode.IDLE;
				changedAttackMode = true;
			}
			if (!firstUpdatedAttackMode) {
				firstUpdatedAttackMode = true;
				changedAttackMode = true;
			}
		}
		if (changedAttackMode || changedMounted) {
			onAttackModeChange(currentAttackMode, ridingMount);
		}
		if (!worldObj.isRemote) {
			prevCombatStance = combatCooldown > 0;
			if (getAttackTarget() != null) {
				combatCooldown = 40;
			} else if (combatCooldown > 0) {
				--combatCooldown;
			}
			combatStance = combatCooldown > 0;
			if (combatStance != prevCombatStance) {
				int x = MathHelper.floor_double(posX) >> 4;
				int z = MathHelper.floor_double(posZ) >> 4;
				PlayerManager playermanager = ((WorldServer) worldObj).getPlayerManager();
				List<EntityPlayer> players = worldObj.playerEntities;
				for (EntityPlayer obj : players) {
					if (playermanager.isPlayerWatchingChunk((EntityPlayerMP) obj, x, z)) {
						sendCombatStance((EntityPlayerMP) obj);
					}
				}
			}
		}
	}

	@SuppressWarnings("all")
	public void updateNearbyBanners() {
		if (getFaction() == GOTFaction.UNALIGNED) {
			nearbyBannerFactor = 0;
		} else {
			double range = 16.0;
			List<GOTBannerBearer> bannerBearers = worldObj.selectEntitiesWithinAABB(GOTBannerBearer.class, boundingBox.expand(range, range, range), new IEntitySelector() {

				@Override
				public boolean isEntityApplicable(Entity entity) {
					EntityLivingBase living = (EntityLivingBase) entity;
					return living != GOTEntityNPC.this && living.isEntityAlive() && GOT.getNPCFaction(living) == getFaction();
				}
			});
			nearbyBannerFactor = Math.min(bannerBearers.size(), 5);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		familyInfo.writeToNBT(nbt);
		questInfo.writeToNBT(nbt);
		hiredNPCInfo.writeToNBT(nbt);
		traderNPCInfo.writeToNBT(nbt);
		npcItemsInv.writeToNBT(nbt);
		hiredReplacedInv.writeToNBT(nbt);
		nbt.setInteger("NPCHomeX", getHomePosition().posX);
		nbt.setInteger("NPCHomeY", getHomePosition().posY);
		nbt.setInteger("NPCHomeZ", getHomePosition().posZ);
		nbt.setInteger("NPCHomeRadius", (int) func_110174_bM());
		nbt.setBoolean("NPCPersistent", isNPCPersistent);
		nbt.setBoolean("HurtOnlyByPlates", hurtOnlyByPlates);
		nbt.setBoolean("RidingHorse", ridingMount);
		nbt.setBoolean("NPCPassive", isPassive);
		nbt.setBoolean("TraderEscort", isTraderEscort);
		if (!killBonusFactions.isEmpty()) {
			NBTTagList bonusTags = new NBTTagList();
			for (GOTFaction f : killBonusFactions) {
				String fName = f.codeName();
				bonusTags.appendTag(new NBTTagString(fName));
			}
			nbt.setTag("BonusFactions", bonusTags);
		}
		if (invasionID != null) {
			nbt.setString("InvasionID", invasionID.toString());
		}
		nbt.setBoolean("SetInitHome", setInitialHome);
		nbt.setInteger("InitHomeX", initHomeX);
		nbt.setInteger("InitHomeY", initHomeY);
		nbt.setInteger("InitHomeZ", initHomeZ);
		nbt.setInteger("InitHomeR", initHomeRange);
	}

	public enum AttackMode {
		MELEE, RANGED, IDLE

	}

}
