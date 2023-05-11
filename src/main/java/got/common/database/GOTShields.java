package got.common.database;

import got.GOT;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum GOTShields {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), HILLMEN(GOTFaction.HILL_TRIBES), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), WESTERLANDSGUARD(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), REACHGUARD(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YITI(GOTFaction.YI_TI), YITI_FRONTIER(GOTFaction.YI_TI), YITI_SAMURAI(GOTFaction.YI_TI), ASSHAI(GOTFaction.ASSHAI), SUMMER(GOTFaction.SUMMER_ISLANDS), SOTHORYOS(GOTFaction.SOTHORYOS), GOLDENCOMPANY, ALCOHOLIC, ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_VALYRIAN, TARGARYEN(false, GOT.devs);

	public ShieldType shieldType;
	public int shieldID;
	public UUID[] exclusiveUUIDs;
	public GOTFaction alignmentFaction;
	public ResourceLocation shieldTexture;
	public boolean isHidden;

	GOTShields() {
		this(ShieldType.ACHIEVABLE, false, new ArrayList<>());
	}

	GOTShields(boolean hidden, List<String> players) {
		this(ShieldType.EXCLUSIVE, hidden, players);
	}

	GOTShields(GOTFaction faction) {
		this(ShieldType.ALIGNMENT, false, new ArrayList<>());
		alignmentFaction = faction;
	}

	GOTShields(ShieldType type, boolean hidden, List<String> players) {
		shieldType = type;
		shieldID = shieldType.list.size();
		shieldType.list.add(this);
		shieldTexture = new ResourceLocation("got:textures/shield/" + name().toLowerCase() + ".png");
		exclusiveUUIDs = new UUID[players.size()];
		for (int i = 0; i < players.size(); ++i) {
			String s = players.get(i);
			exclusiveUUIDs[i] = UUID.fromString(s);
		}
		isHidden = hidden;
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
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.gainHighAlcoholTolerance);
		}
		if (this == GOLDENCOMPANY) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.hireGoldenCompany);
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

	public enum ShieldType {
		ALIGNMENT, ACHIEVABLE, EXCLUSIVE;

		public List<GOTShields> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.shields.category." + name());
		}
	}

}
