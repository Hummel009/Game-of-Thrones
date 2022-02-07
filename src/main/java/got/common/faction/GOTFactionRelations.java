package got.common.faction;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTLevelData;
import got.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

public class GOTFactionRelations {
	public static Map<FactionPair, Relation> defaultMap = new HashMap<>();
	public static Map<FactionPair, Relation> overrideMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean needsSave = false;

	public static Relation getFromDefaultMap(FactionPair key) {
		if (defaultMap.containsKey(key)) {
			return defaultMap.get(key);
		}
		return Relation.NEUTRAL;
	}

	public static Relation getRelations(GOTFaction f1, GOTFaction f2) {
		if ((f1 == GOTFaction.UNALIGNED || f2 == GOTFaction.UNALIGNED) && f1 != GOTFaction.WHITE_WALKER && f2 != GOTFaction.WHITE_WALKER) {
			return Relation.NEUTRAL;
		}
		if (f1 == GOTFaction.HOSTILE || f2 == GOTFaction.HOSTILE) {
			return Relation.MORTAL_ENEMY;
		}
		if ((f1 == GOTFaction.WHITE_WALKER || f2 == GOTFaction.WHITE_WALKER) && f1 != f2) {
			return Relation.MORTAL_ENEMY;
		}
		if (f1 == f2) {
			return Relation.ALLY;
		}
		FactionPair key = new FactionPair(f1, f2);
		if (overrideMap.containsKey(key)) {
			return overrideMap.get(key);
		}
		return GOTFactionRelations.getFromDefaultMap(key);
	}

	public static File getRelationsFile() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "faction_relations.dat");
	}

	public static void load() {
		try {
			NBTTagCompound facData = GOTLevelData.loadNBTFromFile(GOTFactionRelations.getRelationsFile());
			overrideMap.clear();
			NBTTagList relationTags = facData.getTagList("Overrides", 10);
			for (int i = 0; i < relationTags.tagCount(); ++i) {
				NBTTagCompound nbt = relationTags.getCompoundTagAt(i);
				FactionPair pair = FactionPair.readFromNBT(nbt);
				Relation rel = Relation.forName(nbt.getString("Rel"));
				if (pair == null || rel == null) {
					continue;
				}
				overrideMap.put(pair, rel);
			}
			needsLoad = false;
			GOTFactionRelations.save();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT faction relations");
			e.printStackTrace();
		}
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static boolean needsSave() {
		return needsSave;
	}

	public static void overrideRelations(GOTFaction f1, GOTFaction f2, Relation relation) {
		GOTFactionRelations.setRelations(f1, f2, relation, false);
	}

	public static void resetAllRelations() {
		boolean wasEmpty = overrideMap.isEmpty();
		overrideMap.clear();
		if (!wasEmpty) {
			GOTFactionRelations.markDirty();
			GOTPacketFactionRelations pkt = GOTPacketFactionRelations.reset();
			GOTFactionRelations.sendPacketToAll(pkt);
		}
	}

	public static void save() {
		try {
			File datFile = GOTFactionRelations.getRelationsFile();
			if (!datFile.exists()) {
				GOTLevelData.saveNBTToFile(datFile, new NBTTagCompound());
			}
			NBTTagCompound facData = new NBTTagCompound();
			NBTTagList relationTags = new NBTTagList();
			for (Map.Entry<FactionPair, Relation> e : overrideMap.entrySet()) {
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
		GOTPacketFactionRelations pkt = GOTPacketFactionRelations.fullMap(overrideMap);
		GOTPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
	}

	public static void sendPacketToAll(IMessage packet) {
		MinecraftServer srv = MinecraftServer.getServer();
		if (srv != null) {
			for (Object obj : srv.getConfigurationManager().playerEntityList) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
				GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static void setRelations(GOTFaction f1, GOTFaction f2, Relation relation) {
		GOTFactionRelations.setRelations(f1, f2, relation, true);
	}

	public static void setRelations(GOTFaction f1, GOTFaction f2, Relation relation, boolean isDefault) {
		if (f1 == GOTFaction.UNALIGNED || f2 == GOTFaction.UNALIGNED) {
			throw new IllegalArgumentException("Cannot alter UNALIGNED!");
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
				defaultMap.remove(key);
			} else {
				defaultMap.put(key, relation);
			}
		} else {
			Relation defaultRelation = GOTFactionRelations.getFromDefaultMap(key);
			if (relation == defaultRelation) {
				overrideMap.remove(key);
			} else {
				overrideMap.put(key, relation);
			}
			GOTFactionRelations.markDirty();
			GOTPacketFactionRelations pkt = GOTPacketFactionRelations.oneEntry(key, relation);
			GOTFactionRelations.sendPacketToAll(pkt);
		}
	}

	public static class FactionPair {
		public GOTFaction fac1;
		public GOTFaction fac2;

		public FactionPair(GOTFaction f1, GOTFaction f2) {
			fac1 = f1;
			fac2 = f2;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof FactionPair) {
				FactionPair pair = (FactionPair) obj;
				if (fac1 == pair.fac1 && fac2 == pair.fac2 || fac1 == pair.fac2 && fac2 == pair.fac1) {
					return true;
				}
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

		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setString("FacPair1", fac1.codeName());
			nbt.setString("FacPair2", fac2.codeName());
		}

		public static FactionPair readFromNBT(NBTTagCompound nbt) {
			GOTFaction f1 = GOTFaction.forName(nbt.getString("FacPair1"));
			GOTFaction f2 = GOTFaction.forName(nbt.getString("FacPair2"));
			if (f1 != null && f2 != null) {
				return new FactionPair(f1, f2);
			}
			return null;
		}
	}

	public enum Relation {
		ALLY, FRIEND, NEUTRAL, ENEMY, MORTAL_ENEMY;

		public String codeName() {
			return name();
		}

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.faction.rel." + codeName());
		}

		public static Relation forID(int id) {
			for (Relation rel : Relation.values()) {
				if (rel.ordinal() != id) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static Relation forName(String name) {
			for (Relation rel : Relation.values()) {
				if (!rel.codeName().equals(name)) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static List<String> listRelationNames() {
			ArrayList<String> names = new ArrayList<>();
			for (Relation rel : Relation.values()) {
				names.add(rel.codeName());
			}
			return names;
		}
	}

}
