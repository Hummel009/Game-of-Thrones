package got.common.database;

import java.util.*;

import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public enum GOTShields {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), HILLMEN(GOTFaction.HILL_TRIBES), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), WESTERLANDSGUARD(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), REACHGUARD(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YITI(GOTFaction.YI_TI), YITI_SAMURAI(GOTFaction.YI_TI), YITI_FRONTIER(GOTFaction.YI_TI), SUMMER(GOTFaction.SUMMER_ISLANDS), SOTHORYOS(GOTFaction.SOTHORYOS), GOLDENCOMPANY(true, GOTFaction.UNALIGNED), TARGARYEN(false, GOT.DEVS), ALCOHOLIC, ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_VALYRIAN;

	public ShieldType shieldType;
	public int shieldID;
	public UUID[] exclusiveUUIDs;
	public GOTFaction alignmentFaction;
	public ResourceLocation shieldTexture;
	public boolean isHidden;

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

	GOTShields(boolean hidden, GOTFaction faction) {
		this(ShieldType.ALIGNMENT, hidden, new String[0]);
		alignmentFaction = faction;
	}

	GOTShields(ShieldType type, boolean hidden, String... players) {
		shieldType = type;
		shieldID = shieldType.list.size();
		shieldType.list.add(this);
		shieldTexture = new ResourceLocation("got:shield/" + name().toLowerCase() + ".png");
		exclusiveUUIDs = new UUID[players.length];
		for (int i = 0; i < players.length; ++i) {
			String s = players[i];
			exclusiveUUIDs[i] = UUID.fromString(s);
		}
		isHidden = hidden;
	}

	GOTShields(String... players) {
		this(false, players);
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (shieldType == ShieldType.ALIGNMENT) {
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
		if (shieldType == ShieldType.EXCLUSIVE) {
			for (UUID uuid : exclusiveUUIDs) {
				if (!uuid.equals(entityplayer.getUniqueID())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public String getShieldDesc() {
		if (shieldType == ShieldType.ALIGNMENT) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.shields." + name() + ".desc");
	}

	public String getShieldName() {
		return StatCollector.translateToLocal("got.shields." + name() + ".name");
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

	public static enum ShieldType {
		ALIGNMENT, ACHIEVABLE, EXCLUSIVE;

		public List<GOTShields> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.shields.category." + name());
		}
	}

}
