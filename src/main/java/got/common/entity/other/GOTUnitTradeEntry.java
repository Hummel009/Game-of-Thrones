package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.animal.GOTEntityHorse;
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
	public Class<? extends Entity> entityClass;
	public Class<? extends Entity> mountClass;
	public Item mountArmor;
	public float mountArmorChance;
	public String name;
	public int initialCost;
	public float alignmentRequired;
	public PledgeType pledgeType = PledgeType.NONE;
	public GOTHiredNPCInfo.Task task = GOTHiredNPCInfo.Task.WARRIOR;
	public String extraInfo;

	public GOTUnitTradeEntry(Class<? extends Entity> c, Class<? extends Entity> c1, String s, int cost, float alignment) {
		this(c, cost, alignment);
		mountClass = c1;
		name = s;
	}

	public GOTUnitTradeEntry(Class<? extends Entity> c, int cost, float alignment) {
		entityClass = c;
		initialCost = cost;
		alignmentRequired = alignment;
		if (GOTBannerBearer.class.isAssignableFrom(entityClass)) {
			setExtraInfo("Banner");
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
		float alignment = pd.getAlignment(fac);
		boolean pledged = pd.isPledgedTo(fac);
		float alignSurplus = Math.max(alignment - alignmentRequired, 0.0f);
		if (pledged) {
			f = alignSurplus / 1500.0f;
		} else {
			cost *= 2.0f;
			f = alignSurplus / 2000.0f;
		}
		f = MathHelper.clamp_float(f, 0.0f, 1.0f);
		cost *= 1.0f - (f *= 0.5f);
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

	public PledgeType getPledgeType() {
		return pledgeType;
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

	public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, GOTHireableBase trader) {
		int coins = GOTItemCoin.getInventoryValue(entityplayer, false);
		if (coins < getCost(entityplayer, trader)) {
			return false;
		}
		GOTFaction fac = trader.getFaction();
		if (!pledgeType.canAcceptPlayer(entityplayer, fac)) {
			return false;
		}
		float alignment = GOTLevelData.getData(entityplayer).getAlignment(fac);
		return alignment >= alignmentRequired;
	}

	public void hireUnit(EntityPlayer entityplayer, GOTHireableBase trader, String squadron) {
		if (hasRequiredCostAndAlignment(entityplayer, trader)) {
			trader.onUnitTrade(entityplayer);
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
				hiredNPC.hiredNPCInfo.hireUnit(entityplayer, !(unitExists = world.loadedEntityList.contains(hiredNPC)), trader.getFaction(), this, squadron, mount);
				if (!unitExists) {
					world.spawnEntityInWorld(hiredNPC);
					if (mount != null) {
						world.spawnEntityInWorld(mount);
					}
				}
			}
		}
	}

	public GOTUnitTradeEntry setExtraInfo(String s) {
		extraInfo = s;
		return this;
	}

	public GOTUnitTradeEntry setMountArmor(Item item) {
		return this.setMountArmor(item, 1.0f);
	}

	public GOTUnitTradeEntry setMountArmor(Item item, float chance) {
		mountArmor = item;
		mountArmorChance = chance;
		return this;
	}

	public GOTUnitTradeEntry setPledgeExclusive() {
		return setPledgeType(PledgeType.FACTION);
	}

	public GOTUnitTradeEntry setPledgeType(PledgeType t) {
		pledgeType = t;
		return this;
	}

	public GOTUnitTradeEntry setTask(GOTHiredNPCInfo.Task t) {
		task = t;
		return this;
	}

	public enum PledgeType {
		NONE(0), FACTION(1);

		public int typeID;

		PledgeType(int i) {
			typeID = i;
		}

		public boolean canAcceptPlayer(EntityPlayer entityplayer, GOTFaction fac) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (this == NONE) {
				return true;
			}
			if (this == FACTION) {
				return pd.isPledgedTo(fac);
			}
			return false;
		}

		public String getCommandReqText(GOTFaction fac) {
			if (this == NONE) {
				return null;
			}
			return StatCollector.translateToLocalFormatted("got.hiredNPC.commandReq.pledge." + name(), fac.factionName());
		}

		public static PledgeType forID(int i) {
			for (PledgeType t : PledgeType.values()) {
				if (t.typeID != i) {
					continue;
				}
				return t;
			}
			return NONE;
		}
	}

}
