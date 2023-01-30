package got.common.database;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

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
		return GOTSpeech.getSpeechBank(bankName).getRandomSpeech(rand);
	}

	public static String getRandomSpeechForPlayer(GOTEntityNPC entity, String speechBankName, EntityPlayer entityplayer) {
		return GOTSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
	}

	public static String getRandomSpeechForPlayer(GOTEntityNPC entity, String speechBankName, EntityPlayer entityplayer, String location, String objective) {
		String s = GOTSpeech.getRandomSpeech(speechBankName);
		s = GOTSpeech.formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = GOTDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static String getSpeechAtLine(String bankName, int i) {
		return GOTSpeech.getSpeechBank(bankName).getSpeechAtLine(i);
	}

	public static SpeechBank getSpeechBank(String name) {
		SpeechBank bank = allSpeechBanks.get(name);
		if (bank != null) {
			return bank;
		}
		return new SpeechBank("dummy_" + name, true, Arrays.asList("Speech bank " + name + " could not be found!"));
	}

	public static String getSpeechLineForPlayer(GOTEntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer) {
		return GOTSpeech.getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, null, null);
	}

	public static String getSpeechLineForPlayer(GOTEntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer, String location, String objective) {
		String s = GOTSpeech.getSpeechAtLine(speechBankName, i);
		s = GOTSpeech.formatSpeech(s, entityplayer, location, objective);
		if (entity.isDrunkard()) {
			float f = entity.getDrunkenSpeechFactor();
			s = GOTDrunkenSpeech.getDrunkenSpeech(s, f);
		}
		return s;
	}

	public static void messageAllPlayers(IChatComponent message) {
		if (MinecraftServer.getServer() == null) {
			return;
		}
		for (EntityPlayer player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			player.addChatMessage(message);
		}
	}

	public static void messageAllPlayersInWorld(World world, IChatComponent message) {
		for (Object player : world.playerEntities) {
			((EntityPlayer) player).addChatMessage(message);
		}
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
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load GOT speech bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File speechBankDir = new File(GOTRegistry.class.getResource("/assets/got/texts/" + GOTConfig.languageCode + "/speech/").toURI());
				Collection<File> subfiles = FileUtils.listFiles(speechBankDir, null, true);
				for (File subfile : subfiles) {
					String s = subfile.getPath();
					s = s.substring(speechBankDir.getPath().length() + 1);
					int i = (s = s.replace(File.separator, "/")).indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to load GOT speech bank " + s + " from MCP folder; speech bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(subfile)), Charsets.UTF_8.name()));
						speechBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load GOT speech bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to load GOT speech banks");
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
					bank = new SpeechBank(speechBankName.getKey(), random, speeches);
				} else {
					bank = new SpeechBank(speechBankName.getKey(), random, allLines);
				}
				allSpeechBanks.put(speechBankName.getKey(), bank);
			} catch (Exception e) {
				FMLLog.severe("Failed to load GOT speech bank " + speechBankName.getKey());
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
		GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
	}

	public static void sendSpeechAndChatMessage(EntityPlayer entityplayer, GOTEntityNPC entity, String speechBankName) {
		String name = entity.getCommandSenderName();
		String speech = GOTSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
		String message = EnumChatFormatting.YELLOW + "<" + name + ">" + EnumChatFormatting.WHITE + " " + speech;
		ChatComponentText component = new ChatComponentText(message);
		entityplayer.addChatMessage(component);
		GOTSpeech.sendSpeech(entityplayer, entity, speech);
	}

	public static void sendSpeechBankWithChatMsg(EntityPlayer entityplayer, GOTEntityNPC entity, String speechBankName) {
		String speech = GOTSpeech.getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
		GOTSpeech.sendSpeech(entityplayer, entity, speech, true);
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
