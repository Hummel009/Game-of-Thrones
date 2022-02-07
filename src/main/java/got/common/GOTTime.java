package got.common;

import java.io.*;

import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.world.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class GOTTime {
	private static int DAY_LENGTH = 48000;
	private static long totalTime;
	private static long worldTime;
	private static boolean needsLoad;

	static {
		setNeedsLoad(true);
	}

	public static void addWorldTime(long time) {
		worldTime += time;
	}

	public static void advanceToMorning() {
		long l = worldTime + getDayLength();
		GOTTime.setWorldTime(l - l % getDayLength());
	}

	public static int getDayLength() {
		return DAY_LENGTH;
	}

	private static File getTimeDat() {
		return new File(GOTLevelData.getOrCreateGOTDir(), "GOTTime.dat");
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void load() {
		try {
			NBTTagCompound timeData = GOTLevelData.loadNBTFromFile(GOTTime.getTimeDat());
			totalTime = timeData.getLong("GOTTotalTime");
			worldTime = timeData.getLong("GOTWorldTime");
			setNeedsLoad(false);
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

	public static void setDayLength(int dAY_LENGTH) {
		DAY_LENGTH = dAY_LENGTH;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTTime.needsLoad = needsLoad;
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
			if (!(world.provider instanceof GOTWorldProvider)) {
				continue;
			}
			GOTWorldInfo worldinfo = (GOTWorldInfo) world.getWorldInfo();
			worldinfo.got_setTotalTime(totalTime);
			worldinfo.got_setWorldTime(worldTime);
		}
	}
}
