package got.common.entity.other.utils;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTBannerBearer;
import got.common.entity.other.iface.GOTHireableBase;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemCoin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTUnitTradeEntry {
	private final Class<? extends Entity> entityClass;
	private final int initialCost;

	private Class<? extends Entity> mountClass;

	private GOTHireableInfo.Task task = GOTHireableInfo.Task.WARRIOR;
	private OathType oathType = OathType.NONE;

	private Item mountArmor;

	private String name;
	private String extraInfo;

	private float reputationRequired;
	private float mountArmorChance;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTUnitTradeEntry(Class<? extends Entity> c, Class<? extends Entity> c1, String s, int cost, float reputation) {
		this(c, cost, reputation);
		mountClass = c1;
		name = s;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTUnitTradeEntry(Class<? extends Entity> c, int cost, float reputation) {
		entityClass = c;
		initialCost = cost;
		reputationRequired = reputation;
		if (GOTBannerBearer.class.isAssignableFrom(entityClass)) {
			extraInfo = "Banner";
		}
	}

	public EntityLiving createHiredMount(World world) {
		if (mountClass == null) {
			return null;
		}
		EntityLiving entity = (EntityLiving) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(mountClass), world);
		if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).initCreatureForHire(null);
			((GOTEntityNPC) entity).refreshCurrentAttackMode();
		} else {
			entity.onSpawnWithEgg(null);
		}
		if (mountArmor != null && world.rand.nextFloat() < mountArmorChance && entity instanceof GOTEntityHorse) {
			((GOTEntityHorse) entity).setMountArmor(new ItemStack(mountArmor));
		}
		return entity;
	}

	public int getCost(EntityPlayer entityplayer, GOTHireableBase trader) {
		float f;
		float cost = initialCost;
		GOTFaction fac = trader.getFaction();
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		float reputation = pd.getReputation(fac);
		boolean takenOath = pd.hasTakenOathTo(fac);
		float repSurplus = Math.max(reputation - reputationRequired, 0.0f);
		if (takenOath) {
			f = repSurplus / 1500.0f;
		} else {
			cost *= 2.0f;
			f = repSurplus / 2000.0f;
		}
		f = MathHelper.clamp_float(f, 0.0f, 1.0f);
		cost *= 1.0f - f * 0.5f;
		int costI = Math.round(cost);
		return Math.max(costI, 1);
	}

	public String getFormattedExtraInfo() {
		return StatCollector.translateToLocal("got.unitinfo." + extraInfo);
	}

	public GOTEntityNPC getOrCreateHiredNPC(World world) {
		GOTEntityNPC entity = (GOTEntityNPC) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(entityClass), world);
		entity.initCreatureForHire(null);
		entity.refreshCurrentAttackMode();
		return entity;
	}

	public OathType getOathType() {
		return oathType;
	}

	private GOTUnitTradeEntry setOathType(OathType t) {
		oathType = t;
		return this;
	}

	public String getUnitTradeName() {
		if (mountClass == null) {
			String entityName = GOTEntityRegistry.getStringFromClass(entityClass);
			return StatCollector.translateToLocal("entity." + entityName + ".name");
		}
		return StatCollector.translateToLocal("got.unit." + name);
	}

	public boolean hasExtraInfo() {
		return extraInfo != null;
	}

	public boolean hasRequiredCostAndReputation(EntityPlayer entityplayer, GOTHireableBase trader) {
		int coins = GOTItemCoin.getInventoryValue(entityplayer, false);
		if (coins < getCost(entityplayer, trader)) {
			return false;
		}
		GOTFaction fac = trader.getFaction();
		if (!oathType.canAcceptPlayer(entityplayer, fac)) {
			return false;
		}
		float reputation = GOTLevelData.getData(entityplayer).getReputation(fac);
		return reputation >= reputationRequired;
	}

	public void hireUnit(EntityPlayer entityplayer, GOTHireableBase trader, String squadron) {
		if (hasRequiredCostAndReputation(entityplayer, trader)) {
			GOTHireableBase.onUnitTrade(entityplayer);
			int cost = getCost(entityplayer, trader);
			GOTItemCoin.takeCoins(cost, entityplayer);
			((GOTEntityNPC) trader).playTradeSound();
			World world = entityplayer.worldObj;
			GOTEntityNPC hiredNPC = getOrCreateHiredNPC(world);
			if (hiredNPC != null) {
				boolean unitExists;
				EntityLiving mount = null;
				if (mountClass != null) {
					mount = createHiredMount(world);
				}
				hiredNPC.getHireableInfo().hireUnit(entityplayer, !(unitExists = world.loadedEntityList.contains(hiredNPC)), trader.getFaction(), this, squadron, mount);
				if (!unitExists) {
					world.spawnEntityInWorld(hiredNPC);
					if (mount != null) {
						world.spawnEntityInWorld(mount);
					}
				}
			}
		}
	}

	private GOTUnitTradeEntry setMountArmor(Item item, float chance) {
		mountArmor = item;
		mountArmorChance = chance;
		return this;
	}

	public GOTUnitTradeEntry setOathExclusive() {
		return setOathType(OathType.FACTION);
	}

	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}

	public Class<? extends Entity> getMountClass() {
		return mountClass;
	}

	public int getInitialCost() {
		return initialCost;
	}

	public float getReputationRequired() {
		return reputationRequired;
	}

	public void setReputationRequired(float reputationRequired) {
		this.reputationRequired = reputationRequired;
	}

	public GOTHireableInfo.Task getTask() {
		return task;
	}

	public GOTUnitTradeEntry setTask(GOTHireableInfo.Task task) {
		this.task = task;
		return this;
	}

	public GOTUnitTradeEntry setMountArmor(Item item) {
		return setMountArmor(item, 1.0f);
	}

	public enum OathType {
		NONE(0), FACTION(1);

		private final int typeID;

		OathType(int i) {
			typeID = i;
		}

		public static OathType forID(int i) {
			for (OathType t : values()) {
				if (t.typeID != i) {
					continue;
				}
				return t;
			}
			return NONE;
		}

		public boolean canAcceptPlayer(EntityPlayer entityplayer, GOTFaction fac) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			return this == NONE || this == FACTION && pd.hasTakenOathTo(fac);
		}

		public String getCommandReqText(GOTFaction fac) {
			if (this == NONE) {
				return null;
			}
			return StatCollector.translateToLocalFormatted("got.hiredNPC.commandReq.oath." + name(), fac.factionName());
		}

		public int getTypeID() {
			return typeID;
		}
	}
}