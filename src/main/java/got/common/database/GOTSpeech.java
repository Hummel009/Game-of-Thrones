package got.common.database;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.GOTConfig;
import got.common.GOTDrunkenSpeech;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketNPCSpeech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTSpeech {
	public static Map<String, SpeechBank> allSpeechBanks = new HashMap<>();
	public static Random rand = new Random();

	public static String formatSpeech(String speech, EntityPlayer entityplayer, String location, String objective) {
		if (entityplayer != null) {
			speech = speech.replace("#", entityplayer.getCommandSenderName());
		}
		if (location != null) {
			speech = speech.replace("@", location);
		}
		if (objective != null) {
			speech = speech.replace("$", objective);
		}
		return speech;
	}

	public static String getRandomSpeech(String bankName) {
		return getSpeechBank(bankName).getRandomSpeech(rand);
	}

	public static String getRandomSpeechForPlayer(GOTEntityNPC entity, String speechBankName, EntityPlayer entityplayer) {
		return getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
	}

	public static String getRandomSpeechForPlayer(GOTEntityNPC entity, String speechBankName, EntityPlayer entityplayer, String location, String objective) {
		String s = getRandomSpeech(speechBankName);
		s = formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = GOTDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static String getSpeechAtLine(String bankName, int i) {
		return getSpeechBank(bankName).getSpeechAtLine(i);
	}

	public static SpeechBank getSpeechBank(String name) {
		SpeechBank bank = allSpeechBanks.get(name);
		if (bank != null) {
			return bank;
		}
		return new SpeechBank("dummy_" + name, true, Collections.singletonList("Speech bank " + name + " could not be found!"));
	}

	public static String getSpeechLineForPlayer(GOTEntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer) {
		return getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, null, null);
	}

	public static String getSpeechLineForPlayer(GOTEntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer, String location, String objective) {
		String s = getSpeechAtLine(speechBankName, i);
		s = formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = GOTDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static void onInit() {
		HashMap<String, BufferedReader> speechBankNamesAndReaders = new HashMap<>();
		ZipFile zip = null;
		try {
			ModContainer mc = GOT.getModContainer();
			if (mc.getSource().isFile()) {
				zip = new ZipFile(mc.getSource());
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					String s = entry.getName();
					String path = "assets/got/texts/" + GOTConfig.languageCode + "/speech/";
					if (!s.startsWith("assets/got/texts/" + GOTConfig.languageCode + "/speech/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), StandardCharsets.UTF_8));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT speech bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File speechBankDir = new File(Objects.requireNonNull(GOT.class.getResource("/assets/got/texts/" + GOTConfig.languageCode + "/speech/")).toURI());
				Collection<File> subfiles = FileUtils.listFiles(speechBankDir, null, true);
				for (File subfile : subfiles) {
					String s = subfile.getPath();
					s = s.substring(speechBankDir.getPath().length() + 1);
					int i = (s = s.replace(File.separator, "/")).indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to onInit GOT speech bank " + s + " from MCP folder; speech bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(subfile.toPath())), StandardCharsets.UTF_8));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT speech bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to onInit GOT speech banks");
			e.printStackTrace();
		}
		for (Entry<String, BufferedReader> speechBankName : speechBankNamesAndReaders.entrySet()) {
			BufferedReader reader = speechBankName.getValue();
			try {
				String line;
				ArrayList<String> speeches = new ArrayList<>();
				ArrayList<String> allLines = new ArrayList<>();
				boolean random = true;
				while ((line = reader.readLine()) != null) {
					if ("!RANDOM".equals(line)) {
						random = false;
					} else {
						speeches.add(line);
					}
					allLines.add(line);
				}
				reader.close();
				if (speeches.isEmpty()) {
					FMLLog.severe("GOT speech bank " + speechBankName.getKey() + " is empty!");
					continue;
				}
				SpeechBank bank;
				if (random) {
					bank = new SpeechBank(speechBankName.getKey(), true, speeches);
				} else {
					bank = new SpeechBank(speechBankName.getKey(), false, allLines);
				}
				allSpeechBanks.put(speechBankName.getKey(), bank);
			} catch (Exception e) {
				FMLLog.severe("Failed to onInit GOT speech bank " + speechBankName.getKey());
				e.printStackTrace();
			}
		}
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendSpeech(EntityPlayer entityplayer, GOTEntityNPC entity, String speech) {
		sendSpeech(entityplayer, entity, speech, false);
	}

	public static void sendSpeech(EntityPlayer entityplayer, GOTEntityNPC entity, String speech, boolean forceChatMsg) {
		GOTPacketNPCSpeech packet = new GOTPacketNPCSpeech(entity.getEntityId(), speech, forceChatMsg);
		GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
	}

	public static void sendSpeechAndChatMessage(EntityPlayer entityplayer, GOTEntityNPC entity, String speechBankName) {
		String name = entity.getCommandSenderName();
		String speech = getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
		String message = EnumChatFormatting.YELLOW + "<" + name + ">" + EnumChatFormatting.WHITE + " " + speech;
		ChatComponentText component = new ChatComponentText(message);
		entityplayer.addChatMessage(component);
		sendSpeech(entityplayer, entity, speech);
	}

	public static class SpeechBank {
		public String name;
		public boolean isRandom;
		public List<String> speeches;

		public SpeechBank(String s, boolean r, List<String> spc) {
			name = s;
			isRandom = r;
			speeches = spc;
		}

		public String getRandomSpeech(Random random) {
			if (!isRandom) {
				return "ERROR: Tried to retrieve random speech from non-random speech bank " + name;
			}
			String s = speeches.get(rand.nextInt(speeches.size()));
			return internalFormatSpeech(s);
		}

		public String getSpeechAtLine(int line) {
			if (isRandom) {
				return "ERROR: Tried to retrieve indexed speech from random speech bank " + name;
			}
			int index = line - 1;
			if (index >= 0 && index < speeches.size()) {
				String s = speeches.get(index);
				return internalFormatSpeech(s);
			}
			return "ERROR: Speech line " + line + " is out of range!";
		}

		public String internalFormatSpeech(String s) {
			return s;
		}
	}

}
