package got.common;

import java.io.*;

import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.world.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class GOTTime {
	public static int DAY_LENGTH = 48000;
	public static long totalTime;
	public static long worldTime;
	public static boolean needsLoad;

	static {
		needsLoad = true;
	}

	public static void addWorldTime(long time) {
		worldTime += time;
	}

	public static void advanceToMorning() {
		long l = worldTime + DAY_LENGTH;
		GOTTime.setWorldTime(l - l % DAY_LENGTH);
	}

	public static File getTimeDat() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "GOTTime.dat");
	}

	public static void load() {
		try {
			NBTTagCompound timeData = GOTLevelData.loadNBTFromFile(GOTTime.getTimeDat());
			totalTime = timeData.getLong("GOTTotalTime");
			worldTime = timeData.getLong("GOTWorldTime");
			needsLoad = false;
			GOTTime.save();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT time data");
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			File time_dat = GOTTime.getTimeDat();
			if (!time_dat.exists()) {
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), new FileOutputStream(time_dat));
			}
			NBTTagCompound timeData = new NBTTagCompound();
			timeData.setLong("GOTTotalTime", totalTime);
			timeData.setLong("GOTWorldTime", worldTime);
			GOTLevelData.saveNBTToFile(time_dat, timeData);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT time data");
			e.printStackTrace();
		}
	}

	public static void setWorldTime(long time) {
		worldTime = time;
	}

	public static void update() {
		MinecraftServer server = MinecraftServer.getServer();
		WorldServer overworld = server.worldServerForDimension(0);
		if (GOT.doDayCycle(overworld)) {
			++worldTime;
		}
		++totalTime;
		for (WorldServer world : server.worldServers) {
			if (world.provider instanceof GOTWorldProvider) {
				GOTWorldInfo worldinfo = (GOTWorldInfo) world.getWorldInfo();
				worldinfo.got_setTotalTime(totalTime);
				worldinfo.got_setWorldTime(worldTime);
			}
		}
	}
}
