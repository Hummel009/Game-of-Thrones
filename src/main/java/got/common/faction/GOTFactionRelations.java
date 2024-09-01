package got.common.faction;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTLevelData;
import got.common.network.GOTPacketFactionRelations;
import got.common.network.GOTPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTFactionRelations {
	public static final Map<FactionPair, Relation> DEFAULT_MAP = new HashMap<>();

	private static final Map<FactionPair, Relation> OVERRIDE_MAP = new HashMap<>();

	private static boolean needsLoad = true;
	private static boolean needsSave;

	private GOTFactionRelations() {
	}

	private static Relation getFromDefaultMap(FactionPair key) {
		if (DEFAULT_MAP.containsKey(key)) {
			return DEFAULT_MAP.get(key);
		}
		return Relation.NEUTRAL;
	}

	public static Relation getRelations(GOTFaction f1, GOTFaction f2) {
		if (f1 == GOTFaction.NEUTRAL || f2 == GOTFaction.NEUTRAL) {
			return Relation.NEUTRAL;
		}
		if (f1 == GOTFaction.HOSTILE || f2 == GOTFaction.HOSTILE) {
			return Relation.MORTAL_ENEMY;
		}
		if (f1 == f2) {
			return Relation.ALLY;
		}
		FactionPair key = new FactionPair(f1, f2);
		if (OVERRIDE_MAP.containsKey(key)) {
			return OVERRIDE_MAP.get(key);
		}
		return getFromDefaultMap(key);
	}

	private static File getRelationsFile() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "faction_relations.dat");
	}

	public static void load() {
		try {
			NBTTagCompound facData = GOTLevelData.loadNBTFromFile(getRelationsFile());
			OVERRIDE_MAP.clear();
			NBTTagList relationTags = facData.getTagList("Overrides", 10);
			for (int i = 0; i < relationTags.tagCount(); ++i) {
				NBTTagCompound nbt = relationTags.getCompoundTagAt(i);
				FactionPair pair = FactionPair.readFromNBT(nbt);
				Relation rel = Relation.forName(nbt.getString("Rel"));
				if (pair == null || rel == null) {
					continue;
				}
				OVERRIDE_MAP.put(pair, rel);
			}
			needsLoad = false;
			save();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT faction relations");
			e.printStackTrace();
		}
	}

	private static void markDirty() {
		needsSave = true;
	}

	public static boolean needsSave() {
		return needsSave;
	}

	public static void overrideRelations(GOTFaction f1, GOTFaction f2, Relation relation) {
		setRelations(f1, f2, relation, false);
	}

	public static void resetAllRelations() {
		boolean wasEmpty = OVERRIDE_MAP.isEmpty();
		OVERRIDE_MAP.clear();
		if (!wasEmpty) {
			markDirty();
			GOTPacketFactionRelations pkt = GOTPacketFactionRelations.reset();
			sendPacketToAll(pkt);
		}
	}

	public static void save() {
		try {
			File datFile = getRelationsFile();
			if (!datFile.exists()) {
				GOTLevelData.saveNBTToFile(datFile, new NBTTagCompound());
			}
			NBTTagCompound facData = new NBTTagCompound();
			NBTTagList relationTags = new NBTTagList();
			for (Map.Entry<FactionPair, Relation> e : OVERRIDE_MAP.entrySet()) {
				FactionPair pair = e.getKey();
				Relation rel = e.getValue();
				NBTTagCompound nbt = new NBTTagCompound();
				pair.writeToNBT(nbt);
				nbt.setString("Rel", rel.codeName());
				relationTags.appendTag(nbt);
			}
			facData.setTag("Overrides", relationTags);
			GOTLevelData.saveNBTToFile(datFile, facData);
			needsSave = false;
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT faction relations");
			e.printStackTrace();
		}
	}

	public static void sendAllRelationsTo(EntityPlayerMP entityplayer) {
		GOTPacketFactionRelations pkt = GOTPacketFactionRelations.fullMap(OVERRIDE_MAP);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(pkt, entityplayer);
	}

	private static void sendPacketToAll(IMessage packet) {
		MinecraftServer srv = MinecraftServer.getServer();
		if (srv != null) {
			for (EntityPlayerMP entityplayer : (List<EntityPlayerMP>) srv.getConfigurationManager().playerEntityList) {
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
			}
		}
	}

	public static void setRelations(GOTFaction f1, GOTFaction f2, Relation relation) {
		setRelations(f1, f2, relation, true);
	}

	private static void setRelations(GOTFaction f1, GOTFaction f2, Relation relation, boolean isDefault) {
		if (f1 == GOTFaction.NEUTRAL || f2 == GOTFaction.NEUTRAL) {
			throw new IllegalArgumentException("Cannot alter NEUTRAL!");
		}
		if (f1 == GOTFaction.HOSTILE || f2 == GOTFaction.HOSTILE) {
			throw new IllegalArgumentException("Cannot alter HOSTILE!");
		}
		if (f1 == f2) {
			throw new IllegalArgumentException("Cannot alter a faction's relations with itself!");
		}
		FactionPair key = new FactionPair(f1, f2);
		if (isDefault) {
			if (relation == Relation.NEUTRAL) {
				DEFAULT_MAP.remove(key);
			} else {
				DEFAULT_MAP.put(key, relation);
			}
		} else {
			Relation defaultRelation = getFromDefaultMap(key);
			if (relation == defaultRelation) {
				OVERRIDE_MAP.remove(key);
			} else {
				OVERRIDE_MAP.put(key, relation);
			}
			markDirty();
			GOTPacketFactionRelations pkt = GOTPacketFactionRelations.oneEntry(key, relation);
			sendPacketToAll(pkt);
		}
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTFactionRelations.needsLoad = needsLoad;
	}

	public enum Relation {
		ALLY, FRIEND, NEUTRAL, ENEMY, MORTAL_ENEMY;

		public static Relation forID(int id) {
			for (Relation rel : values()) {
				if (rel.ordinal() != id) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static Relation forName(String name) {
			for (Relation rel : values()) {
				if (!rel.codeName().equals(name)) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static List<String> listRelationNames() {
			List<String> names = new ArrayList<>();
			for (Relation rel : values()) {
				names.add(rel.codeName());
			}
			return names;
		}

		private String codeName() {
			return name();
		}

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.faction.rel." + codeName());
		}
	}

	public static class FactionPair {
		private final GOTFaction fac1;
		private final GOTFaction fac2;

		public FactionPair(GOTFaction f1, GOTFaction f2) {
			fac1 = f1;
			fac2 = f2;
		}

		private static FactionPair readFromNBT(NBTTagCompound nbt) {
			GOTFaction f1 = GOTFaction.forName(nbt.getString("FacPair1"));
			GOTFaction f2 = GOTFaction.forName(nbt.getString("FacPair2"));
			if (f1 != null && f2 != null) {
				return new FactionPair(f1, f2);
			}
			return null;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof FactionPair) {
				FactionPair pair = (FactionPair) obj;
				return fac1 == pair.fac1 && fac2 == pair.fac2 || fac1 == pair.fac2 && fac2 == pair.fac1;
			}
			return false;
		}

		public GOTFaction getLeft() {
			return fac1;
		}

		public GOTFaction getRight() {
			return fac2;
		}

		@Override
		public int hashCode() {
			int f1 = fac1.ordinal();
			int f2 = fac2.ordinal();
			int lower = Math.min(f1, f2);
			int upper = Math.max(f1, f2);
			return upper << 16 | lower;
		}

		private void writeToNBT(NBTTagCompound nbt) {
			nbt.setString("FacPair1", fac1.codeName());
			nbt.setString("FacPair2", fac2.codeName());
		}
	}
}