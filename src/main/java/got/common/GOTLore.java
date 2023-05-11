package got.common;

import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.database.GOTNames;
import got.common.util.GOTLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.MathHelper;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTLore {
	public static String newline = "\n";
	public static String codeMetadata = "#";
	public static String codeTitle = "title:";
	public static String codeAuthor = "author:";
	public static String codeCategory = "types:";
	public static String codeCategorySeparator = ",";
	public static String codeReward = "reward";
	public String loreName;
	public String loreTitle;
	public String loreAuthor;
	public String loreText;
	public List<LoreCategory> loreCategories;
	public boolean isRewardable;

	public GOTLore(String name, String title, String auth, String text, List<LoreCategory> categories, boolean reward) {
		loreName = name;
		loreTitle = title;
		loreAuthor = auth;
		loreText = text;
		loreCategories = categories;
		isRewardable = reward;
	}

	public static GOTLore getMultiRandomLore(Iterable<LoreCategory> categories, Random random, boolean rewardsOnly) {
		ArrayList<GOTLore> allLore = new ArrayList<>();
		for (LoreCategory c : categories) {
			for (GOTLore lore : c.loreList) {
				if (!allLore.contains(lore) && (!rewardsOnly || lore.isRewardable)) {
					allLore.add(lore);
				}
			}
		}
		if (!allLore.isEmpty()) {
			return allLore.get(random.nextInt(allLore.size()));
		}
		return null;
	}

	public static void onInit() {
		HashMap<String, BufferedReader> loreReaders = new HashMap<>();
		ZipFile zip = null;
		try {
			ModContainer mc = GOT.getModContainer();
			if (mc.getSource().isFile()) {
				zip = new ZipFile(mc.getSource());
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					String path;
					ZipEntry entry = entries.nextElement();
					String s = entry.getName();
					if (s.startsWith(path = "assets/got/texts/" + GOTConfig.languageCode + "/lore/") && s.endsWith(".txt")) {
						s = s.substring(path.length());
						int i = s.indexOf(".txt");
						try {
							s = s.substring(0, i);
							BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), StandardCharsets.UTF_8));
							loreReaders.put(s, reader);
						} catch (Exception e) {
							GOTLog.logger.error("Failed to load GOT lore {}from zip file", s);
							e.printStackTrace();
						}
					}
				}
			} else {
				File nameBankDir = new File(GOT.class.getResource("/assets/got/texts/" + GOTConfig.languageCode + "/lore/").toURI());
				for (File file : nameBankDir.listFiles()) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						GOTLog.logger.error("Failed to load GOT lore {} from MCP folder; name bank files must be in .txt format", s);
					} else {
						try {
							s = s.substring(0, i);
							BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file)), StandardCharsets.UTF_8));
							loreReaders.put(s, reader);
						} catch (Exception e) {
							GOTLog.logger.error("Failed to load GOT lore {} from MCP folder", s);
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			GOTLog.logger.error("Failed to load GOT lore");
			e.printStackTrace();
		}
		for (Entry<String, BufferedReader> entry : loreReaders.entrySet()) {
			String loreName = entry.getKey();
			BufferedReader reader = entry.getValue();
			try {
				Object categoryString;
				String line;
				String title = "";
				String author = "";
				ArrayList<LoreCategory> categories = new ArrayList<>();
				StringBuilder text = new StringBuilder();
				boolean reward = false;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(codeMetadata)) {
						String metadata = line.substring(codeMetadata.length());
						if (metadata.startsWith(codeTitle)) {
							title = metadata.substring(codeTitle.length());
							continue;
						}
						if (metadata.startsWith(codeAuthor)) {
							author = metadata.substring(codeAuthor.length());
							continue;
						}
						if (metadata.startsWith(codeCategory)) {
							categoryString = metadata.substring(codeCategory.length());
							while (((String) categoryString).length() > 0) {
								Object categoryName = null;
								int indexOf = ((String) categoryString).indexOf(codeCategorySeparator);
								if (indexOf >= 0) {
									categoryName = ((String) categoryString).substring(0, indexOf);
									categoryString = ((String) categoryString).substring(indexOf + 1);
								} else {
									categoryName = categoryString;
									categoryString = "";
								}
								if (categoryName == null) {
									continue;
								}
								if ("all".equals(categoryName)) {
									for (LoreCategory category : LoreCategory.values()) {
										if (!categories.contains(category)) {
											categories.add(category);
										}
									}
									continue;
								}
								LoreCategory category = LoreCategory.forName((String) categoryName);
								if (category != null) {
									if (categories.contains(category)) {
										continue;
									}
									categories.add(category);
									continue;
								}
								GOTLog.logger.warn("Hummel009: Loading lore {}, no category exists for name {}", loreName, (String) categoryName);
							}
							continue;
						}
						if (!metadata.startsWith(codeReward)) {
							continue;
						}
						reward = true;
						continue;
					}
					text.append(line);
					text.append(newline);
				}
				reader.close();
				GOTLore lore = new GOTLore(loreName, title, author, text.toString(), categories, reward);
				for (LoreCategory category : categories) {
					category.addLore(lore);
				}
			} catch (Exception e) {
				GOTLog.logger.error("Failed to load GOT lore: {}", loreName);
				e.printStackTrace();
			}
		}
		for (LoreCategory category : LoreCategory.values()) {
			int num = category.loreList.size();
			int numReward = 0;
			for (GOTLore lore : category.loreList) {
				if (lore.isRewardable) {
					++numReward;
				}
			}
			GOTLog.logger.info("Hummel009: Category {} has loaded {} lore texts, of which {} rewardable", category.categoryName, num, numReward);
		}
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> organisePages(String loreText) {
		ArrayList<String> loreTextPages = new ArrayList<>();
		String remainingText = loreText;
		ArrayList<String> splitTxtWords = new ArrayList<>();
		while (remainingText.length() > 0) {
			String part;
			if (remainingText.startsWith(newline)) {
				part = newline;
				if (!splitTxtWords.isEmpty()) {
					splitTxtWords.add(part);
				}
				remainingText = remainingText.substring(part.length());
				continue;
			}
			part = "";
			int indexOf = remainingText.indexOf(newline);
			if (indexOf >= 0) {
				part = remainingText.substring(0, indexOf);
			} else {
				part = remainingText;
			}
			Collections.addAll(splitTxtWords, StringUtils.split(part, " "));
			remainingText = remainingText.substring(part.length());
		}
		while (!splitTxtWords.isEmpty()) {
			int i;
			String pageText = "";
			int numLines = 0;
			String currentLine = "";
			int usedWords = 0;
			for (i = 0; i < splitTxtWords.size(); ++i) {
				String word = splitTxtWords.get(i);
				if (pageText.length() + word.length() > 256) {
					break;
				}
				if (word.equals(newline)) {
					if (currentLine.length() > 0) {
						pageText = pageText + currentLine;
						currentLine = "";
						numLines++;
						if (numLines >= 13) {
							break;
						}
					}
					++usedWords;
					if (pageText.length() <= 0) {
						continue;
					}
					pageText = pageText + word;
					numLines++;
					if (numLines < 13) {
						continue;
					}
					break;
				}
				currentLine = currentLine + word;
				++usedWords;
				if (i < splitTxtWords.size() - 1) {
					currentLine = currentLine + " ";
				}
				if (currentLine.length() >= 17) {
					pageText = pageText + currentLine;
					currentLine = "";
					numLines++;
					if (numLines >= 13) {
						break;
					}
				}
			}
			if (currentLine.length() > 0) {
				pageText = pageText + currentLine;
				currentLine = "";
				++numLines;
			}
			for (i = 0; i < usedWords; ++i) {
				splitTxtWords.remove(0);
			}
			loreTextPages.add(pageText);
		}
		return loreTextPages;
	}

	public ItemStack createLoreBook(Random random) {
		ItemStack itemstack = new ItemStack(Items.written_book);
		NBTTagCompound data = new NBTTagCompound();
		itemstack.setTagCompound(data);
		String title = formatRandom(loreTitle, random);
		String author = formatRandom(loreAuthor, random);
		String text = formatRandom(loreText, random);
		List<String> textPages = organisePages(text);
		data.setString("title", title);
		data.setString("author", author);
		NBTTagList pages = new NBTTagList();
		for (String pageText : textPages) {
			pages.appendTag(new NBTTagString(pageText));
		}
		data.setTag("pages", pages);
		return itemstack;
	}

	public String formatRandom(String text, Random random) {
		int lastIndexStart = -1;
		do {
			String formatted;
			String unformatted;
			block16:
			{
				String s1;
				int indexStart = text.indexOf('{', lastIndexStart + 1);
				int indexEnd = text.indexOf('}');
				lastIndexStart = indexStart;
				if (indexStart < 0 || indexEnd <= indexStart) {
					break;
				}
				unformatted = text.substring(indexStart, indexEnd + 1);
				formatted = unformatted.substring(1, unformatted.length() - 1);
				if (formatted.startsWith("num:")) {
					try {
						s1 = formatted.substring("num:".length());
						int i1 = s1.indexOf(codeCategorySeparator);
						String s2 = s1.substring(0, i1);
						String s3 = s1.substring(i1 + codeCategorySeparator.length());
						int min = Integer.parseInt(s2);
						int max = Integer.parseInt(s3);
						int number = MathHelper.getRandomIntegerInRange(random, min, max);
						formatted = String.valueOf(number);
					} catch (Exception e) {
						GOTLog.logger.error("Hummel009: Error formatting number {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("name:")) {
					try {
						String namebank = s1 = formatted.substring("name:".length());
						if (!GOTNames.nameBankExists(namebank)) {
							GOTLog.logger.error("Hummel009: No namebank exists for {}!", namebank);
							break block16;
						}
						formatted = GOTNames.getRandomName(namebank, random);
					} catch (Exception e) {
						GOTLog.logger.error("Hummel009: Error formatting name {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("choose:")) {
					try {
						String remaining = formatted.substring("choose:".length());
						ArrayList<String> words = new ArrayList<>();
						while (remaining.length() > 0) {
							String word;
							int indexOf = remaining.indexOf('/');
							if (indexOf >= 0) {
								word = remaining.substring(0, indexOf);
								remaining = remaining.substring(indexOf + "/".length());
							} else {
								word = remaining;
								remaining = "";
							}
							words.add(word);
						}
						formatted = words.get(random.nextInt(words.size()));
					} catch (Exception e) {
						GOTLog.logger.error("Hummel009: Error formatting choice {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				}
			}
			text = Pattern.compile(unformatted, 16).matcher(text).replaceFirst(Matcher.quoteReplacement(formatted));
		} while (true);
		return text;
	}

	public enum LoreCategory {
		WESTEROS("westeros"), ESSOS("essos"), YITI("yiti"), ASSHAI("asshai"), SOTHORYOS("sothoryos"), MOSSOVY("mossovy");

		public static String allCode = "all";
		public String categoryName;
		public List<GOTLore> loreList = new ArrayList<>();

		LoreCategory(String s) {
			categoryName = s;
		}

		public static LoreCategory forName(String s) {
			for (LoreCategory r : values()) {
				if (s.equalsIgnoreCase(r.categoryName)) {
					return r;
				}
			}
			return null;
		}

		public void addLore(GOTLore lore) {
			loreList.add(lore);
		}
	}

}