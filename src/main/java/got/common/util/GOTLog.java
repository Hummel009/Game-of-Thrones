package got.common.util;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.server.MinecraftServer;

public class GOTLog {
	public static Logger logger;

	public static void findLogger() {
		try {
			for (Field f : MinecraftServer.class.getDeclaredFields()) {
				GOTReflection.unlockFinalField(f);
				Object obj = f.get(null);
				if (obj instanceof Logger) {
					logger = (Logger) obj;
					logger.info("Hummel009: Found logger");
					break;
				}
			}
		} catch (Exception e) {
			FMLLog.warning("Hummel009: Failed to find logger!");
			e.printStackTrace();
		}
	}

	public static void info(String s) {
		logger.info(s);
	}
}
