package got.common.brotherhood;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLevelData;
import got.common.util.GOTLog;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GOTBrotherhoodData {
	private static final Map<UUID, GOTBrotherhood> BROTHERHOOD_MAP = new HashMap<>();

	private static boolean needsLoad = true;

	private GOTBrotherhoodData() {
	}

	public static void addBrotherhood(GOTBrotherhood fs) {
		if (!BROTHERHOOD_MAP.containsKey(fs.getBrotherhoodID())) {
			BROTHERHOOD_MAP.put(fs.getBrotherhoodID(), fs);
		}
	}

	public static boolean anyDataNeedsSave() {
		for (GOTBrotherhood fs : BROTHERHOOD_MAP.values()) {
			if (!fs.needsSave()) {
				continue;
			}
			return true;
		}
		return false;
	}

	private static void destroyAllBrotherhoodData() {
		BROTHERHOOD_MAP.clear();
	}

	public static GOTBrotherhood getActiveBrotherhood(UUID fsID) {
		GOTBrotherhood fs = getBrotherhood(fsID);
		if (fs != null && fs.isDisbanded()) {
			return null;
		}
		return fs;
	}

	public static GOTBrotherhood getBrotherhood(UUID fsID) {
		GOTBrotherhood fs = BROTHERHOOD_MAP.get(fsID);
		if (fs == null && (fs = loadBrotherhood(fsID)) != null) {
			BROTHERHOOD_MAP.put(fsID, fs);
		}
		return fs;
	}

	private static File getBrotherhoodDat(UUID fsID) {
		return new File(getBrotherhoodDir(), fsID.toString() + ".dat");
	}

	private static File getBrotherhoodDir() {
		File fsDir = new File(GOTLevelData.getOrCreateGOTDir(), "brotherhoods");
		if (!fsDir.exists()) {
			boolean created = fsDir.mkdirs();
			if (!created) {
				GOTLog.getLogger().info("GOTBrotherhoodData: directory wasn't created");
			}
		}
		return fsDir;
	}

	public static void loadAll() {
		try {
			destroyAllBrotherhoodData();
			needsLoad = false;
			saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT brotherhood data");
			e.printStackTrace();
		}
	}

	private static GOTBrotherhood loadBrotherhood(UUID fsID) {
		File fsDat = getBrotherhoodDat(fsID);
		try {
			NBTTagCompound nbt = GOTLevelData.loadNBTFromFile(fsDat);
			if (nbt.hasNoTags()) {
				return null;
			}
			GOTBrotherhood fs = new GOTBrotherhood(fsID);
			fs.load(nbt);
			return fs;
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT brotherhood data for %s", fsDat.getName());
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAll() {
		try {
			for (GOTBrotherhood fs : BROTHERHOOD_MAP.values()) {
				if (!fs.needsSave()) {
					continue;
				}
				saveBrotherhood(fs);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT brotherhood data");
			e.printStackTrace();
		}
	}

	private static void saveBrotherhood(GOTBrotherhood fs) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			fs.save(nbt);
			GOTLevelData.saveNBTToFile(getBrotherhoodDat(fs.getBrotherhoodID()), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT brotherhood data for %s", fs.getBrotherhoodID());
			e.printStackTrace();
		}
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTBrotherhoodData.needsLoad = needsLoad;
	}
}