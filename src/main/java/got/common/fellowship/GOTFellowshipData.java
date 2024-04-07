package got.common.fellowship;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLevelData;
import got.common.util.GOTLog;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GOTFellowshipData {
	private static final Map<UUID, GOTFellowship> FELLOWSHIP_MAP = new HashMap<>();

	private static boolean needsLoad = true;

	private GOTFellowshipData() {
	}

	public static void addFellowship(GOTFellowship fs) {
		if (!FELLOWSHIP_MAP.containsKey(fs.getFellowshipID())) {
			FELLOWSHIP_MAP.put(fs.getFellowshipID(), fs);
		}
	}

	public static boolean anyDataNeedsSave() {
		for (GOTFellowship fs : FELLOWSHIP_MAP.values()) {
			if (!fs.needsSave()) {
				continue;
			}
			return true;
		}
		return false;
	}

	private static void destroyAllFellowshipData() {
		FELLOWSHIP_MAP.clear();
	}

	public static GOTFellowship getActiveFellowship(UUID fsID) {
		GOTFellowship fs = getFellowship(fsID);
		if (fs != null && fs.isDisbanded()) {
			return null;
		}
		return fs;
	}

	public static GOTFellowship getFellowship(UUID fsID) {
		GOTFellowship fs = FELLOWSHIP_MAP.get(fsID);
		if (fs == null && (fs = loadFellowship(fsID)) != null) {
			FELLOWSHIP_MAP.put(fsID, fs);
		}
		return fs;
	}

	private static File getFellowshipDat(UUID fsID) {
		return new File(getFellowshipDir(), fsID.toString() + ".dat");
	}

	private static File getFellowshipDir() {
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

	private static GOTFellowship loadFellowship(UUID fsID) {
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
			for (GOTFellowship fs : FELLOWSHIP_MAP.values()) {
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

	private static void saveFellowship(GOTFellowship fs) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			fs.save(nbt);
			GOTLevelData.saveNBTToFile(getFellowshipDat(fs.getFellowshipID()), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT fellowship data for %s", fs.getFellowshipID());
			e.printStackTrace();
		}
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTFellowshipData.needsLoad = needsLoad;
	}
}