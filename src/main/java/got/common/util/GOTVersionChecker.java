package got.common.util;

import java.io.*;
import java.net.URL;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.*;

public class GOTVersionChecker {
	public static String versionURL = "https://raw.githubusercontent.com/Hummel009/got/master/version.txt";
	public static boolean checkedUpdate = false;

	public static void checkForUpdates() {
		if (!checkedUpdate) {
			Thread checkThread = new Thread("GOT Update Checker") {

				@Override
				public void run() {
					try {
						String line;
						URL url = new URL(versionURL);
						BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream()));
						String updateVersion = "";
						while ((line = updateReader.readLine()) != null) {
							updateVersion = updateVersion.concat(line);
						}
						updateReader.close();
						updateVersion = updateVersion.trim();
						String currentVersion = GOT.VERSION;
						if (!updateVersion.equals(currentVersion)) {
							ChatComponentText component = new ChatComponentText("Game of Thrones Mod:");
							component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
							EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
							if (entityplayer != null) {
								entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.update", component, updateVersion));
							}
						}
					} catch (Exception e) {
						GOTLog.logger.warn("GOT: Version check failed");
						e.printStackTrace();
					}
				}
			};
			checkedUpdate = true;
			checkThread.setDaemon(true);
			checkThread.start();
		}
	}

}
