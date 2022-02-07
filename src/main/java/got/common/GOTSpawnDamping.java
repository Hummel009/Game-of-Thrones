package got.common;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTSpawnDamping {
	private static Map<String, Float> spawnDamping = new HashMap<>();
	private static String TYPE_NPC = "got_npc";
	private static boolean needsSave = true;

	public static int getBaseSpawnCapForInfo(String type, World world) {
		if (type.equals(getTypeNPC())) {
			return GOTDimension.getCurrentDimension(world).getSpawnCap();
		}
		EnumCreatureType creatureType = EnumCreatureType.valueOf(type);
		if (creatureType != null) {
			return creatureType.getMaxNumberOfCreature();
		}
		return -1;
	}

	public static int getCreatureSpawnCap(EnumCreatureType type, World world) {
		return GOTSpawnDamping.getSpawnCap(type.name(), type.getMaxNumberOfCreature(), world);
	}

	private static File getDataFile() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "spawn_damping.dat");
	}

	public static int getNPCSpawnCap(World world) {
		return GOTSpawnDamping.getSpawnCap(getTypeNPC(), GOTDimension.getCurrentDimension(world).getSpawnCap(), world);
	}

	public static int getSpawnCap(String type, int baseCap, int players) {
		float stationaryPointValue;
		float damp = GOTSpawnDamping.getSpawnDamping(type);
		float dampFraction = (players - 1) * damp;
		dampFraction = MathHelper.clamp_float(dampFraction, 0.0f, 1.0f);
		stationaryPointValue = 0.5f + damp / 2.0f;
		if (dampFraction > stationaryPointValue) {
			dampFraction = stationaryPointValue;
		}
		int capPerPlayer = Math.round(baseCap * (1.0f - dampFraction));
		return Math.max(capPerPlayer, 1);
	}

	private static int getSpawnCap(String type, int baseCap, World world) {
		int players = world.playerEntities.size();
		return GOTSpawnDamping.getSpawnCap(type, baseCap, players);
	}

	public static float getSpawnDamping(String type) {
		float f = 0.0f;
		if (spawnDamping.containsKey(type)) {
			f = spawnDamping.get(type);
		}
		return f;
	}

	public static String getTypeNPC() {
		return TYPE_NPC;
	}

	public static boolean isNeedsSave() {
		return needsSave;
	}

	public static void loadAll() {
		try {
			File datFile = GOTSpawnDamping.getDataFile();
			NBTTagCompound spawnData = GOTLevelData.loadNBTFromFile(datFile);
			spawnDamping.clear();
			if (spawnData.hasKey("Damping")) {
				NBTTagList typeTags = spawnData.getTagList("Damping", 10);
				for (int i = 0; i < typeTags.tagCount(); ++i) {
					NBTTagCompound nbt = typeTags.getCompoundTagAt(i);
					String type = nbt.getString("Type");
					float damping = nbt.getFloat("Damp");
					if (StringUtils.isBlank(type)) {
						continue;
					}
					spawnDamping.put(type, damping);
				}
			}
			setNeedsSave(true);
			GOTSpawnDamping.saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT spawn damping");
			e.printStackTrace();
		}
	}

	private static void markDirty() {
		setNeedsSave(true);
	}

	public static void resetAll() {
		spawnDamping.clear();
		GOTSpawnDamping.markDirty();
	}

	public static void saveAll() {
		try {
			File datFile = GOTSpawnDamping.getDataFile();
			if (!datFile.exists()) {
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), new FileOutputStream(datFile));
			}
			NBTTagCompound spawnData = new NBTTagCompound();
			NBTTagList typeTags = new NBTTagList();
			for (Map.Entry<String, Float> e : spawnDamping.entrySet()) {
				String type = e.getKey();
				float damping = e.getValue();
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Type", type);
				nbt.setFloat("Damp", damping);
				typeTags.appendTag(nbt);
			}
			spawnData.setTag("Damping", typeTags);
			GOTLevelData.saveNBTToFile(datFile, spawnData);
			setNeedsSave(false);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT spawn damping");
			e.printStackTrace();
		}
	}

	public static void setNeedsSave(boolean needsSave) {
		GOTSpawnDamping.needsSave = needsSave;
	}

	public static void setSpawnDamping(String type, float damping) {
		spawnDamping.put(type, damping);
		GOTSpawnDamping.markDirty();
	}

	public static void setTypeNPC(String tYPE_NPC) {
		TYPE_NPC = tYPE_NPC;
	}
}
