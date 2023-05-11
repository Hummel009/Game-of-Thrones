package got.common.world.map;

import com.google.common.io.Files;
import cpw.mods.fml.common.FMLLog;
import got.common.GOTConfig;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GOTCustomWaypointLogger {
	public static Charset CHARSET = StandardCharsets.UTF_8;
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM");
	public static DateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MM-dd");
	public static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	public static void log(String function, EntityPlayer entityplayer, GOTCustomWaypoint cwp) {
		if (!GOTConfig.cwpLog) {
			return;
		}
		try {
			File logFile;
			File dupeLogDir;
			LocalDateTime date = LocalDateTime.now();
			StringBuilder logLine = new StringBuilder(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", MONTH_DATE_FORMAT.format(date), TIME_FORMAT.format(date), function, entityplayer.getCommandSenderName(), entityplayer.getPersistentID(), cwp.getCodeName(), cwp.getXCoord(), cwp.getYCoordSaved(), cwp.getZCoord(), cwp.isShared(), cwp.isShared() ? cwp.getSharingPlayerName() : "N/A", cwp.isShared() ? cwp.getSharingPlayerID() : "N/A"));
			if (cwp.isShared()) {
				List<UUID> fsIDs = cwp.getSharedFellowshipIDs();
				for (UUID id : fsIDs) {
					GOTFellowship fellowship = GOTFellowshipData.getActiveFellowship(id);
					if (fellowship == null || !fellowship.containsPlayer(entityplayer.getUniqueID())) {
						continue;
					}
					logLine.append(",");
					logLine.append(fellowship.getName());
				}
			}
			if (!(dupeLogDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "got_cwp_logs")).exists()) {
				dupeLogDir.mkdirs();
			}
			if (!(logFile = new File(dupeLogDir, DATE_FORMAT.format(date) + ".csv")).exists()) {
				Files.append("date,time,function,username,UUID,wp_name,x,y,z,shared,sharer_name,sharer_UUID,common_fellowships" + System.lineSeparator(), logFile, CHARSET);
			}
			Files.append(logLine.append(System.lineSeparator()).toString(), logFile, CHARSET);
		} catch (IOException e) {
			FMLLog.warning("Error logging custom waypoint activities");
			e.printStackTrace();
		}
	}

	public static void logCreate(EntityPlayer entityplayer, GOTCustomWaypoint cwp) {
		log("CREATE", entityplayer, cwp);
	}

	public static void logDelete(EntityPlayer entityplayer, GOTCustomWaypoint cwp) {
		log("DELETE", entityplayer, cwp);
	}

	public static void logRename(EntityPlayer entityplayer, GOTCustomWaypoint cwp) {
		log("RENAME", entityplayer, cwp);
	}

	public static void logTravel(EntityPlayer entityplayer, GOTCustomWaypoint cwp) {
		log("TRAVEL", entityplayer, cwp);
	}
}
