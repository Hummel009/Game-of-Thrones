package got.common.fellowship;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class GOTFellowshipData {
	public static Map<UUID, GOTFellowship> fellowshipMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean doFullClearing = false;

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
		GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
		if (fs != null && fs.isDisbanded()) {
			return null;
		}
		return fs;
	}

	public static GOTFellowship getFellowship(UUID fsID) {
		GOTFellowship fs = fellowshipMap.get(fsID);
		if (fs == null && (fs = GOTFellowshipData.loadFellowship(fsID)) != null) {
			fellowshipMap.put(fsID, fs);
		}
		return fs;
	}

	public static File getFellowshipDat(UUID fsID) {
		return new File(GOTFellowshipData.getFellowshipDir(), fsID.toString() + ".dat");
	}

	public static File getFellowshipDir() {
		File fsDir = new File(GOTLevelData.getOrCreateGOTDir(), "fellowships");
		if (!fsDir.exists()) {
			fsDir.mkdirs();
		}
		return fsDir;
	}

	public static void loadAll() {
		try {
			GOTFellowshipData.destroyAllFellowshipData();
			needsLoad = false;
			GOTFellowshipData.saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT fellowship data");
			e.printStackTrace();
		}
	}

	public static GOTFellowship loadFellowship(UUID fsID) {
		File fsDat = GOTFellowshipData.getFellowshipDat(fsID);
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
				GOTFellowshipData.saveFellowship(fs);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT fellowship data");
			e.printStackTrace();
		}
	}

	public static void saveAndClearFellowship(GOTFellowship fs) {
		if (fellowshipMap.containsValue(fs)) {
			GOTFellowshipData.saveFellowship(fs);
			fellowshipMap.remove(fs.getFellowshipID());
		} else {
			FMLLog.severe("Attempted to clear GOT fellowship data for %s; no data found", fs.getFellowshipID());
		}
	}

	public static void saveAndClearUnusedFellowships() {
		if (doFullClearing) {
			ArrayList<GOTFellowship> clearing = new ArrayList<>();
			for (GOTFellowship fs : fellowshipMap.values()) {
				boolean foundMember = false;
				for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
					EntityPlayer entityplayer = (EntityPlayer) player;
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
				GOTFellowshipData.saveAndClearFellowship(fs);
			}
		}
	}

	public static void saveFellowship(GOTFellowship fs) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			fs.save(nbt);
			GOTLevelData.saveNBTToFile(GOTFellowshipData.getFellowshipDat(fs.getFellowshipID()), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT fellowship data for %s", fs.getFellowshipID());
			e.printStackTrace();
		}
	}
}
