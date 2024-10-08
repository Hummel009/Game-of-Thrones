package got.common.database;

import got.GOT;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public enum GOTCapes {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), NIGHT(GOTFaction.NIGHT_WATCH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), KINGSGUARD_1(GOTFaction.CROWNLANDS), KINGSGUARD_2(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YI_TI(GOTFaction.YI_TI), YI_TI_BOMBARDIER(GOTFaction.YI_TI), YI_TI_SAMURAI(GOTFaction.YI_TI), ASSHAI(GOTFaction.ASSHAI), TARGARYEN(false, GOT.DEVS);

	private final int capeID;
	private final CapeType capeType;
	private final ResourceLocation capeTexture;
	private final UUID[] exclusiveUUIDs;

	private GOTFaction reputationFaction;
	private boolean isHidden;

	GOTCapes(boolean hidden, List<String> players) {
		this(CapeType.EXCLUSIVE, hidden, players);
	}

	GOTCapes(GOTFaction faction) {
		this(CapeType.REPUTATION, false, new ArrayList<>());
		reputationFaction = faction;
	}

	GOTCapes(CapeType type, boolean hidden, List<String> players) {
		capeType = type;
		capeID = capeType.getCapes().size();
		capeType.getCapes().add(this);
		capeTexture = new ResourceLocation("got:textures/cape/" + name().toLowerCase(Locale.ROOT) + ".png");
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

	public static GOTCapes capeForName(String capeName) {
		for (GOTCapes cape : values()) {
			if (!cape.name().equals(capeName)) {
				continue;
			}
			return cape;
		}
		return null;
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (capeType == CapeType.REPUTATION) {
			return GOTLevelData.getData(entityplayer).getReputation(reputationFaction) >= 100.0f;
		}
		if (capeType == CapeType.EXCLUSIVE) {
			for (UUID uuid : exclusiveUUIDs) {
				if (!uuid.equals(entityplayer.getUniqueID())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public String getCapeDesc() {
		if (capeType == CapeType.REPUTATION) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.capes." + name() + ".desc");
	}

	public String getCapeName() {
		return StatCollector.translateToLocal("got.capes." + name() + ".name");
	}

	public void setHidden(boolean hidden) {
		isHidden = hidden;
	}

	public GOTFaction getReputationFaction() {
		return reputationFaction;
	}

	public CapeType getCapeType() {
		return capeType;
	}

	public ResourceLocation getCapeTexture() {
		return capeTexture;
	}

	public int getCapeID() {
		return capeID;
	}

	public enum CapeType {
		REPUTATION, EXCLUSIVE;

		private final List<GOTCapes> capes = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.capes.category." + name());
		}

		public List<GOTCapes> getCapes() {
			return capes;
		}
	}
}