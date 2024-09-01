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
import java.util.Locale;
import java.util.UUID;

public enum GOTShields {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), HILLMEN(GOTFaction.HILL_TRIBES), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), WESTERLANDSGUARD(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), REACHGUARD(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YI_TI(GOTFaction.YI_TI), YI_TI_BOMBARDIER(GOTFaction.YI_TI), YI_TI_SAMURAI(GOTFaction.YI_TI), ASSHAI(GOTFaction.ASSHAI), SUMMER(GOTFaction.SUMMER_ISLANDS), SOTHORYOS(GOTFaction.SOTHORYOS), GOLDEN_COMPANY, ALCOHOLIC, ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_VALYRIAN, TARGARYEN(false, GOT.DEVS);

	private final int shieldID;
	private final ShieldType shieldType;
	private final ResourceLocation shieldTexture;
	private final UUID[] exclusiveUUIDs;

	private GOTFaction reputationFaction;
	private boolean isHidden;

	GOTShields() {
		this(ShieldType.ACHIEVABLE, false, new ArrayList<>());
	}

	GOTShields(boolean hidden, List<String> players) {
		this(ShieldType.EXCLUSIVE, hidden, players);
	}

	GOTShields(GOTFaction faction) {
		this(ShieldType.REPUTATION, false, new ArrayList<>());
		reputationFaction = faction;
	}

	GOTShields(ShieldType type, boolean hidden, List<String> players) {
		shieldType = type;
		shieldID = shieldType.getShields().size();
		shieldType.getShields().add(this);
		shieldTexture = new ResourceLocation("got:textures/shield/" + name().toLowerCase(Locale.ROOT) + ".png");
		exclusiveUUIDs = new UUID[players.size()];
		for (int i = 0; i < players.size(); ++i) {
			String s = players.get(i);
			exclusiveUUIDs[i] = UUID.fromString(s);
		}
		isHidden = hidden;
	}

	@SuppressWarnings("EmptyMethod")
	public static void preInit() {
	}

	public static GOTShields shieldForName(String shieldName) {
		for (GOTShields shield : values()) {
			if (!shield.name().equals(shieldName)) {
				continue;
			}
			return shield;
		}
		return null;
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (shieldType == ShieldType.REPUTATION) {
			return GOTLevelData.getData(entityplayer).getReputation(reputationFaction) >= 100.0f;
		}
		if (this == ACHIEVEMENT_BRONZE) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 75;
		}
		if (this == ACHIEVEMENT_SILVER) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 150;
		}
		if (this == ACHIEVEMENT_GOLD) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 225;
		}
		if (this == ACHIEVEMENT_VALYRIAN) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 300;
		}
		if (this == ALCOHOLIC) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.gainHighAlcoholTolerance);
		}
		if (this == GOLDEN_COMPANY) {
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
		if (shieldType == ShieldType.REPUTATION) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.shields." + name() + ".desc");
	}

	public String getShieldName() {
		return StatCollector.translateToLocal("got.shields." + name() + ".name");
	}

	public void setHidden(boolean hidden) {
		isHidden = hidden;
	}

	public GOTFaction getReputationFaction() {
		return reputationFaction;
	}

	public ShieldType getShieldType() {
		return shieldType;
	}

	public ResourceLocation getShieldTexture() {
		return shieldTexture;
	}

	public int getShieldID() {
		return shieldID;
	}

	public enum ShieldType {
		REPUTATION, ACHIEVABLE, EXCLUSIVE;

		private final List<GOTShields> shields = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.shields.category." + name());
		}

		public List<GOTShields> getShields() {
			return shields;
		}
	}
}