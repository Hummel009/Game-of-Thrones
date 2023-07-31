package got.common.fellowship;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLevelData;
import got.common.util.GOTLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.util.*;

public class GOTFellowshipData {
	public static Map<UUID, GOTFellowship> fellowshipMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean doFullClearing;

	public static void addFellowship(GOTFellowship fs) {
		if (!fellowshipMap.containsKey(fs.getFellowshipID())) {
			fellowshipMap.put(fs.getFellowshipID(), fs);
		}
	}

	public static boolean anyDataNeedsSave() {
		for (GOTFellowship fs : fellowshipMap.values()) {
			if (!fs.needsSave()) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static void destroyAllFellowshipData() {
		fellowshipMap.clear();
	}

	public static GOTFellowship getActiveFellowship(UUID fsID) {
		GOTFellowship fs = getFellowship(fsID);
		if (fs != null && fs.isDisbanded()) {
			return null;
		}
		return fs;
	}

	public static GOTFellowship getFellowship(UUID fsID) {
		GOTFellowship fs = fellowshipMap.get(fsID);
		if (fs == null && (fs = loadFellowship(fsID)) != null) {
			fellowshipMap.put(fsID, fs);
		}
		return fs;
	}

	public static File getFellowshipDat(UUID fsID) {
		return new File(getFellowshipDir(), fsID.toString() + ".dat");
	}

	public static File getFellowshipDir() {
		File fsDir = new File(GOTLevelData.getOrCreateGOTDir(), "fellowships");
		if (!fsDir.exists()) {
			boolean created = fsDir.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTFellowshipData: directory wasn't created");
			}
		}
		return fsDir;
	}

	public static void loadAll() {
		try {
			destroyAllFellowshipData();
			needsLoad = false;
			saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT fellowship data");
			e.printStackTrace();
		}
	}

	public static GOTFellowship loadFellowship(UUID fsID) {
		File fsDat = getFellowshipDat(fsID);
		try {
			NBTTagCompound nbt = GOTLevelData.loadNBTFromFile(fsDat);
			if (nbt.hasNoTags()) {
				return null;
			}
			GOTFellowship fs = new GOTFellowship(fsID);
			fs.load(nbt);
			return fs;
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT fellowship data for %s", fsDat.getName());
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAll() {
		try {
			for (GOTFellowship fs : fellowshipMap.values()) {
				if (!fs.needsSave()) {
					continue;
				}
				saveFellowship(fs);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT fellowship data");
			e.printStackTrace();
		}
	}

	public static void saveAndClearFellowship(GOTFellowship fs) {
		if (fellowshipMap.containsValue(fs)) {
			saveFellowship(fs);
			fellowshipMap.remove(fs.getFellowshipID());
		} else {
			FMLLog.severe("Attempted to clear GOT fellowship data for %s; no data found", fs.getFellowshipID());
		}
	}

	public static void saveAndClearUnusedFellowships() {
		if (doFullClearing) {
			Collection<GOTFellowship> clearing = new ArrayList<>();
			for (GOTFellowship fs : fellowshipMap.values()) {
				boolean foundMember = false;
				for (EntityPlayer entityplayer : (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
					if (!fs.containsPlayer(entityplayer.getUniqueID())) {
						continue;
					}
					foundMember = true;
					break;
				}
				if (foundMember) {
					continue;
				}
				clearing.add(fs);
			}
			for (GOTFellowship fs : clearing) {
				saveAndClearFellowship(fs);
			}
		}
	}

	public static void saveFellowship(GOTFellowship fs) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			fs.save(nbt);
			GOTLevelData.saveNBTToFile(getFellowshipDat(fs.getFellowshipID()), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT fellowship data for %s", fs.getFellowshipID());
			e.printStackTrace();
		}
	}
}
