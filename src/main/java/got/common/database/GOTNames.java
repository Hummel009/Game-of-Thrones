package got.common.database;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.GOTConfig;
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

public class GOTNames {
	public static Map<String, String[]> allNameBanks = new HashMap<>();

	public static String getAsshaiName(Random rand, boolean male) {
		return getRandomName(male ? "asshai_male" : "asshai_female", rand);
	}

	public static String getDothrakiName(Random rand, boolean male) {
		return getRandomName(male ? "dothraki_male" : "dothraki_female", rand);
	}

	public static String getEssosName(Random rand, boolean male) {
		return getRandomName(male ? "essos_male" : "essos_female", rand);
	}

	public static String getGhiscarName(Random rand, boolean male) {
		return getRandomName(male ? "ghiscar_male" : "ghiscar_female", rand);
	}

	public static String getGiantName(Random rand) {
		String giant1 = getRandomName("giant_1", rand);
		String giant2 = getRandomName("giant_2", rand);
		String giant3 = getRandomName("giant_2", rand);
		String giant4 = getRandomName("giant_2", rand);
		String giant5 = getRandomName("giant_3", rand);
		return giant1 + giant2 + giant3 + giant4 + giant5;
	}

	public static String getJogosName(Random rand, boolean male) {
		return getRandomName(male ? "jogos_male" : "jogos_female", rand);
	}

	public static String getLhazarName(Random rand, boolean male) {
		return getRandomName(male ? "lhazar_male" : "lhazar_female", rand);
	}

	public static String getMossovyName(Random rand, boolean male) {
		return getRandomName(male ? "mossovy_male" : "mossovy_female", rand);
	}

	public static String[] getNameBank(String nameBankName) {
		return allNameBanks.get(nameBankName);
	}

	public static String getQarthName(Random rand, boolean male) {
		return getRandomName(male ? "qarth_male" : "qarth_female", rand);
	}

	public static String getRandomName(String nameBankName, Random rand) {
		if (allNameBanks.containsKey(nameBankName)) {
			String[] nameBank = getNameBank(nameBankName);
			return nameBank[rand.nextInt(nameBank.length)];
		}
		return "Impostor";
	}

	public static String getSothoryosName(Random rand, boolean male) {
		return getRandomName(male ? "sothoryos_male" : "sothoryos_female", rand);
	}

	public static String[] getTavernName(Random rand) {
		String prefix = getRandomName("tavern_prefix", rand);
		String suffix = getRandomName("tavern_suffix", rand);
		return new String[]{prefix, suffix};
	}

	public static String getWesterosName(Random rand, boolean male) {
		return getRandomName(male ? "westeros_male" : "westeros_female", rand);
	}

	public static String getWildName(Random rand, boolean male) {
		return getRandomName(male ? "wild_male" : "wild_female", rand);
	}

	public static String getYiTiName(Random rand, boolean male) {
		return getRandomName(male ? "yiti_male" : "yiti_female", rand);
	}

	public static boolean nameBankExists(String nameBankName) {
		return getNameBank(nameBankName) != null;
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
					String path = "assets/got/texts/" + GOTConfig.languageCode + "/names/";
					if (!s.startsWith("assets/got/texts/" + GOTConfig.languageCode + "/names/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), StandardCharsets.UTF_8));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT name bank " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File nameBankDir = new File(Objects.requireNonNull(GOT.class.getResource("/assets/got/texts/" + GOTConfig.languageCode + "/names/")).toURI());
				for (File file : Objects.requireNonNull(nameBankDir.listFiles())) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						FMLLog.severe("Failed to onInit GOT name bank " + s + " from MCP folder; name bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(file.toPath())), StandardCharsets.UTF_8));
						nameBankNamesAndReaders.put(s, reader);
					} catch (Exception e) {
						FMLLog.severe("Failed to onInit GOT name bank " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to onInit GOT name banks");
			e.printStackTrace();
		}
		for (Entry<String, BufferedReader> nameBankName : nameBankNamesAndReaders.entrySet()) {
			BufferedReader reader = nameBankName.getValue();
			try {
				String line;
				ArrayList<String> nameList = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					nameList.add(line);
				}
				reader.close();
				if (nameList.isEmpty()) {
					FMLLog.severe("GOT name bank " + nameBankName.getKey() + " is empty!");
					continue;
				}
				String[] nameBank = nameList.toArray(new String[0]);
				allNameBanks.put(nameBankName.getKey(), nameBank);
			} catch (Exception e) {
				FMLLog.severe("Failed to onInit GOT name bank " + nameBankName.getKey());
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
