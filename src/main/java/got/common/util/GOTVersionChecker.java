package got.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class GOTVersionChecker {
	public static String versionURL = "https://raw.githubusercontent.com/Hummel009/Game-of-Thrones/master/version.txt";
	public static boolean checkedUpdate;

	public static void checkForUpdates() {
		if (!checkedUpdate) {
			Thread checkThread = new Thread("GOT Update Checker") {

				@Override
				public void run() {
					try {
						String line;
						URL url = new URL(versionURL);
						BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
						StringBuilder updateVersion = new StringBuilder();
						while ((line = updateReader.readLine()) != null) {
							updateVersion.append(line);
						}
						updateReader.close();
						updateVersion = new StringBuilder(updateVersion.toString().trim());
						String currentVersion = GOT.VERSION;
						if (!updateVersion.toString().equals(currentVersion)) {
							ChatComponentText component = new ChatComponentText("Game of Thrones Mod:");
							component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
							EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
							if (entityplayer != null) {
								entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.update", component, updateVersion.toString()));
							}
						}
					} catch (Exception e) {
						GOTLog.logger.warn("Hummel009: Version check failed");
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