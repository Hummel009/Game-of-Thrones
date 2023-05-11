package got.common.database;

import got.GOT;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum GOTCapes {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), NIGHT(GOTFaction.NIGHT_WATCH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), KINGSGUARD(GOTFaction.CROWNLANDS), ROYALGUARD(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.VOLANTIS), PENTOS(GOTFaction.PENTOS), NORVOS(GOTFaction.NORVOS), BRAAVOS(GOTFaction.BRAAVOS), TYROSH(GOTFaction.TYROSH), LORATH(GOTFaction.LORATH), QOHOR(GOTFaction.QOHOR), LYS(GOTFaction.LYS), MYR(GOTFaction.MYR), QARTH(GOTFaction.QARTH), GHISCAR(GOTFaction.GHISCAR), UNSULLIED(GOTFaction.GHISCAR), YITI(GOTFaction.YI_TI), YITI_FRONTIER(GOTFaction.YI_TI), YITI_SAMURAI(GOTFaction.YI_TI), ASSHAI(GOTFaction.ASSHAI), TARGARYEN(false, GOT.devs);

	public CapeType capeType;
	public int capeID;
	public UUID[] exclusiveUUIDs;
	public GOTFaction alignmentFaction;
	public ResourceLocation capeTexture;
	public boolean isHidden;

	GOTCapes(boolean hidden, List<String> players) {
		this(CapeType.EXCLUSIVE, hidden, players);
	}

	GOTCapes(CapeType type, boolean hidden, List<String> players) {
		capeType = type;
		capeID = capeType.list.size();
		capeType.list.add(this);
		capeTexture = new ResourceLocation("got:textures/cape/" + name().toLowerCase() + ".png");
		exclusiveUUIDs = new UUID[players.size()];
		for (int i = 0; i < players.size(); ++i) {
			String s = players.get(i);
			exclusiveUUIDs[i] = UUID.fromString(s);
		}
		isHidden = hidden;
	}

	GOTCapes(GOTFaction faction) {
		this(CapeType.ALIGNMENT, false, new ArrayList<>());
		alignmentFaction = faction;
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

	public static void preInit() {
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (capeType == CapeType.ALIGNMENT) {
			return GOTLevelData.getData(entityplayer).getAlignment(alignmentFaction) >= 100.0f;
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
		if (capeType == CapeType.ALIGNMENT) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.capes." + name() + ".desc");
	}

	public String getCapeName() {
		return StatCollector.translateToLocal("got.capes." + name() + ".name");
	}

	public enum CapeType {
		ALIGNMENT, EXCLUSIVE;

		public List<GOTCapes> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.capes.category." + name());
		}
	}

}
