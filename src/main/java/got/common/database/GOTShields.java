package got.common.database;

import java.util.*;

import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public enum GOTShields {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), HILLMEN(GOTFaction.HILL_TRIBES), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), WESTERLANDSGUARD(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), REACHGUARD(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YITI(GOTFaction.YI_TI), YITI_FRONTIER(GOTFaction.YI_TI), YITI_SAMURAI(GOTFaction.YI_TI), ASSHAI(GOTFaction.ASSHAI), SUMMER(GOTFaction.SUMMER_ISLANDS), SOTHORYOS(GOTFaction.SOTHORYOS), GOLDENCOMPANY, ALCOHOLIC, ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_VALYRIAN, TARGARYEN(false, GOT.getDevs());

	private ShieldType shieldType;
	private int shieldID;
	private UUID[] exclusiveUUIDs;
	private GOTFaction alignmentFaction;
	private ResourceLocation shieldTexture;
	private boolean isHidden;

	GOTShields() {
		this(ShieldType.ACHIEVABLE, false, new String[0]);
	}

	GOTShields(boolean hidden, String... players) {
		this(ShieldType.EXCLUSIVE, hidden, players);
	}

	GOTShields(GOTFaction faction) {
		this(ShieldType.ALIGNMENT, false, new String[0]);
		alignmentFaction = faction;
	}

	GOTShields(ShieldType type, boolean hidden, String... players) {
		setShieldType(type);
		setShieldID(getShieldType().getList().size());
		getShieldType().getList().add(this);
		setShieldTexture(new ResourceLocation("got:textures/shield/" + name().toLowerCase() + ".png"));
		setExclusiveUUIDs(new UUID[players.length]);
		for (int i = 0; i < players.length; ++i) {
			String s = players[i];
			getExclusiveUUIDs()[i] = UUID.fromString(s);
		}
		isHidden = hidden;
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (getShieldType() == ShieldType.ALIGNMENT) {
			return GOTLevelData.getData(entityplayer).getAlignment(alignmentFaction) >= 100.0f;
		}
		if (this == ACHIEVEMENT_BRONZE) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 10;
		}
		if (this == ACHIEVEMENT_SILVER) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 20;
		}
		if (this == ACHIEVEMENT_GOLD) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 50;
		}
		if (this == ACHIEVEMENT_VALYRIAN) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 100;
		}
		if (this == ALCOHOLIC) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.GAIN_HIGH_ALCOHOL_TOLERANCE);
		}
		if (this == GOLDENCOMPANY) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.HIRE_GOLDEN_COMPANY);
		}
		if (getShieldType() == ShieldType.EXCLUSIVE) {
			for (UUID uuid : getExclusiveUUIDs()) {
				if (!uuid.equals(entityplayer.getUniqueID())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public UUID[] getExclusiveUUIDs() {
		return exclusiveUUIDs;
	}

	public String getShieldDesc() {
		if (getShieldType() == ShieldType.ALIGNMENT) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.shields." + name() + ".desc");
	}

	public int getShieldID() {
		return shieldID;
	}

	public String getShieldName() {
		return StatCollector.translateToLocal("got.shields." + name() + ".name");
	}

	public ResourceLocation getShieldTexture() {
		return shieldTexture;
	}

	public ShieldType getShieldType() {
		return shieldType;
	}

	public void setExclusiveUUIDs(UUID[] exclusiveUUIDs) {
		this.exclusiveUUIDs = exclusiveUUIDs;
	}

	public void setShieldID(int shieldID) {
		this.shieldID = shieldID;
	}

	public void setShieldTexture(ResourceLocation shieldTexture) {
		this.shieldTexture = shieldTexture;
	}

	public void setShieldType(ShieldType shieldType) {
		this.shieldType = shieldType;
	}

	public static void preInit() {
	}

	public static GOTShields shieldForName(String shieldName) {
		for (GOTShields shield : GOTShields.values()) {
			if (!shield.name().equals(shieldName)) {
				continue;
			}
			return shield;
		}
		return null;
	}

	public enum ShieldType {
		ALIGNMENT, ACHIEVABLE, EXCLUSIVE;

		private List<GOTShields> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.shields.category." + name());
		}

		public List<GOTShields> getList() {
			return list;
		}

		public void setList(List<GOTShields> list) {
			this.list = list;
		}
	}

}
