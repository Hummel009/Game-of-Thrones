package got.common.database;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import got.GOT;
import got.common.GOTConfig;

public class GOTNames {
	public static Map<String, String[]> allNameBanks = new HashMap<>();

	public static String getAsshaiName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "asshai_male" : "asshai_female", rand);
	}

	public static String getEssosName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "essos_male" : "essos_female", rand);
	}

	public static String getGhiscarName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "ghiscar_male" : "ghiscar_female", rand);
	}

	public static String getGiantName(Random rand) {
		String giant1 = GOTNames.getRandomName("giant_1", rand);
		String giant2 = GOTNames.getRandomName("giant_2", rand);
		String giant3 = GOTNames.getRandomName("giant_2", rand);
		String giant4 = GOTNames.getRandomName("giant_2", rand);
		String giant5 = GOTNames.getRandomName("giant_3", rand);
		return giant1 + giant2 + giant3 + giant4 + giant5;
	}

	public static String getMaesterName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "maester" : "maester", rand);
	}

	public static String[] getNameBank(String nameBankName) {
		return allNameBanks.get(nameBankName);
	}

	public static String getJogosName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "jogos_male" : "jogos_female", rand);
	}

	public static String getLhazarName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "lhazar_male" : "lhazar_female", rand);
	}

	public static String getDothrakiName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "dothraki_male" : "dothraki_female", rand);
	}

	public static String getRandomName(String nameBankName, Random rand) {
		if (allNameBanks.containsKey(nameBankName)) {
			String[] nameBank = GOTNames.getNameBank(nameBankName);
			return nameBank[rand.nextInt(nameBank.length)];
		}
		return "Aboba";
	}

	public static String getSothoryosName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "sothoryos_male" : "sothoryos_female", rand);
	}

	public static String[] getTavernName(Random rand) {
		String prefix = GOTNames.getRandomName("tavern_prefix", rand);
		String suffix = GOTNames.getRandomName("tavern_suffix", rand);
		return new String[] { prefix, suffix };
	}

	public static String getWesterosName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "westeros_male" : "westeros_female", rand);
	}

	public static String getWildName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "wild_male" : "wild_female", rand);
	}

	public static String getYiTiName(Random rand, boolean male) {
		return GOTNames.getRandomName(male ? "yiti_male" : "yiti_female", rand);
	}

	public static boolean nameBankExists(String nameBankName) {
		return GOTNames.getNameBank(nameBankName) != null;
	}

	public static void onInit() {
		HashMap<String, BufferedReader> nameBankNamesAndReaders = new HashMap<>();
		ZipFile zip = null;
		try {
			ModContainer mc = GOT.getModContainer();
			if (mc.getSource().isFile()) {
				zip = new ZipFile(mc.getSource());
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					String s = entry.getName();
					String path = "assets/got/names/" + GOTConfig.languageCode + "/";
					if (!s.startsWith("assets/got/names/" + GOTConfig.languageCode + "/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load GOT name bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File nameBankDir = new File(GOTRegistry.class.getResource("/assets/got/names/" + GOTConfig.languageCode).toURI());
				for (File file : nameBankDir.listFiles()) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to load GOT name bank " + s + " from MCP folder; name bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file)), Charsets.UTF_8.name()));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to load GOT name bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to load GOT name banks");
			e.printStackTrace();
		}
		for (String nameBankName : nameBankNamesAndReaders.keySet()) {
			BufferedReader reader = nameBankNamesAndReaders.get(nameBankName);
			try {
				String line;
				ArrayList<String> nameList = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					nameList.add(line);
				}
				reader.close();
				if (nameList.isEmpty()) {
					FMLLog.severe("GOT name bank " + nameBankName + " is empty!");
					continue;
				}
				String[] nameBank = nameList.toArray(new String[0]);
				allNameBanks.put(nameBankName, nameBank);
			} catch (Exception e) {
				FMLLog.severe("Failed to load GOT name bank " + nameBankName);
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
}
