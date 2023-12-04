package got.common;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class GOTSpawnDamping {
	public static Map<String, Float> spawnDamping = new HashMap<>();
	public static String TYPE_NPC = "got_npc";
	public static boolean needsSave = true;

	public static int getBaseSpawnCapForInfo(String type, World world) {
		if (type.equals(TYPE_NPC)) {
			return GOTDimension.getCurrentDimension(world).spawnCap;
		}
		EnumCreatureType creatureType = EnumCreatureType.valueOf(type);
		return creatureType.getMaxNumberOfCreature();
	}

	public static int getCreatureSpawnCap(EnumCreatureType type, World world) {
		return getSpawnCap(type.name(), type.getMaxNumberOfCreature(), world);
	}

	public static File getDataFile() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "spawn_damping.dat");
	}

	public static int getNPCSpawnCap(World world) {
		return getSpawnCap(TYPE_NPC, GOTDimension.getCurrentDimension(world).spawnCap, world);
	}

	public static int getSpawnCap(String type, int baseCap, int players) {
		float damp = getSpawnDamping(type);
		float dampFraction = (players - 1) * damp;
		dampFraction = MathHelper.clamp_float(dampFraction, 0.0f, 1.0f);
		float stationaryPointValue = 0.5f + damp / 2.0f;
		if (dampFraction > stationaryPointValue) {
			dampFraction = stationaryPointValue;
		}
		int capPerPlayer = Math.round(baseCap * (1.0f - dampFraction));
		return Math.max(capPerPlayer, 1);
	}

	public static int getSpawnCap(String type, int baseCap, World world) {
		int players = world.playerEntities.size();
		return getSpawnCap(type, baseCap, players);
	}

	public static float getSpawnDamping(String type) {
		if (spawnDamping.containsKey(type)) {
			return spawnDamping.get(type);
		}
		return 0.0f;
	}

	public static void loadAll() {
		try {
			File datFile = getDataFile();
			NBTTagCompound spawnData = GOTLevelData.loadNBTFromFile(datFile);
			spawnDamping.clear();
			if (spawnData.hasKey("Damping")) {
				NBTTagList typeTags = spawnData.getTagList("Damping", 10);
				for (int i = 0; i < typeTags.tagCount(); ++i) {
					NBTTagCompound nbt = typeTags.getCompoundTagAt(i);
					String type = nbt.getString("Type");
					float damping = nbt.getFloat("Damp");
					if (!StringUtils.isBlank(type)) {
						spawnDamping.put(type, damping);
					}
				}
			}
			needsSave = true;
			saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT spawn damping");
			e.printStackTrace();
		}
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static void resetAll() {
		spawnDamping.clear();
		markDirty();
	}

	public static void saveAll() {
		try {
			File datFile = getDataFile();
			if (!datFile.exists()) {
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), Files.newOutputStream(datFile.toPath()));
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
			needsSave = false;
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT spawn damping");
			e.printStackTrace();
		}
	}

	public static void setSpawnDamping(String type, float damping) {
		spawnDamping.put(type, damping);
		markDirty();
	}
}
