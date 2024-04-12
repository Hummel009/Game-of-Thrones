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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTLore {
	private static final String NEW_LINE = "\n";
	private static final String CODE_METADATA = "#";
	private static final String CODE_TITLE = "title:";
	private static final String CODE_AUTHOR = "author:";
	private static final String CODE_CATEGORY = "types:";
	private static final String CODE_CATEGORY_SEPARATOR = ",";
	private static final String CODE_REWARD = "reward";

	private final String loreName;
	private final String loreTitle;
	private final String loreAuthor;
	private final String loreText;
	private final boolean isRewardable;

	private GOTLore(String name, String title, String auth, String text, boolean reward) {
		loreName = name;
		loreTitle = title;
		loreAuthor = auth;
		loreText = text;
		isRewardable = reward;
	}

	public static GOTLore getMultiRandomLore(Iterable<LoreCategory> categories, Random random, boolean rewardsOnly) {
		ArrayList<GOTLore> allLore = new ArrayList<>();
		for (LoreCategory c : categories) {
			for (GOTLore lore : c.getLoreList()) {
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
		Map<String, BufferedReader> loreReaders = new HashMap<>();
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
							GOTLog.getLogger().error("Failed to onInit GOT lore {}from zip file", s);
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
						GOTLog.getLogger().error("Failed to onInit GOT lore {} from MCP folder; name bank files must be in .txt format", s);
					} else {
						try {
							s = s.substring(0, i);
							BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(Files.newInputStream(file.toPath())), StandardCharsets.UTF_8));
							loreReaders.put(s, reader);
						} catch (Exception e) {
							GOTLog.getLogger().error("Failed to onInit GOT lore {} from MCP folder", s);
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			GOTLog.getLogger().error("Failed to onInit GOT lore");
			e.printStackTrace();
		}
		for (Map.Entry<String, BufferedReader> entry : loreReaders.entrySet()) {
			String loreName = entry.getKey();
			BufferedReader reader = entry.getValue();
			try {
				String categoryString;
				String line;
				String title = "";
				String author = "";
				Collection<LoreCategory> categories = new ArrayList<>();
				StringBuilder text = new StringBuilder();
				boolean reward = false;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(CODE_METADATA)) {
						String metadata = line.substring(CODE_METADATA.length());
						if (metadata.startsWith(CODE_TITLE)) {
							title = metadata.substring(CODE_TITLE.length());
							continue;
						}
						if (metadata.startsWith(CODE_AUTHOR)) {
							author = metadata.substring(CODE_AUTHOR.length());
							continue;
						}
						if (metadata.startsWith(CODE_CATEGORY)) {
							categoryString = metadata.substring(CODE_CATEGORY.length());
							while (!categoryString.isEmpty()) {
								String categoryName;
								int indexOf = categoryString.indexOf(CODE_CATEGORY_SEPARATOR);
								if (indexOf >= 0) {
									categoryName = categoryString.substring(0, indexOf);
									categoryString = categoryString.substring(indexOf + 1);
								} else {
									categoryName = categoryString;
									categoryString = "";
								}
								if ("all".equals(categoryName)) {
									for (LoreCategory category : LoreCategory.values()) {
										if (!categories.contains(category)) {
											categories.add(category);
										}
									}
									continue;
								}
								LoreCategory category = LoreCategory.forName(categoryName);
								if (category != null) {
									if (categories.contains(category)) {
										continue;
									}
									categories.add(category);
									continue;
								}
								GOTLog.getLogger().warn("Hummel009: Loading lore {}, no category exists for name {}", loreName, categoryName);
							}
							continue;
						}
						if (!metadata.startsWith(CODE_REWARD)) {
							continue;
						}
						reward = true;
						continue;
					}
					text.append(line);
					text.append(NEW_LINE);
				}
				reader.close();
				GOTLore lore = new GOTLore(loreName, title, author, text.toString(), reward);
				for (LoreCategory category : categories) {
					category.addLore(lore);
				}
			} catch (Exception e) {
				GOTLog.getLogger().error("Failed to onInit GOT lore: {}", loreName);
				e.printStackTrace();
			}
		}
		for (LoreCategory category : LoreCategory.values()) {
			int num = category.getLoreList().size();
			int numReward = 0;
			for (GOTLore lore : category.getLoreList()) {
				if (lore.isRewardable) {
					++numReward;
				}
			}
			GOTLog.getLogger().info("Hummel009: Category {} has loaded {} lore texts, of which {} rewardable", category.getCategoryName(), num, numReward);
		}
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String> organisePages(String loreText) {
		List<String> loreTextPages = new ArrayList<>();
		String remainingText = loreText;
		ArrayList<String> splitTxtWords = new ArrayList<>();
		while (!remainingText.isEmpty()) {
			String part;
			if (remainingText.startsWith(NEW_LINE)) {
				part = NEW_LINE;
				if (!splitTxtWords.isEmpty()) {
					splitTxtWords.add(part);
				}
				remainingText = remainingText.substring(part.length());
				continue;
			}
			int indexOf = remainingText.indexOf(NEW_LINE);
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
			StringBuilder pageText = new StringBuilder();
			int numLines = 0;
			StringBuilder currentLine = new StringBuilder();
			int usedWords = 0;
			for (i = 0; i < splitTxtWords.size(); ++i) {
				String word = splitTxtWords.get(i);
				if (pageText.length() + word.length() > 256) {
					break;
				}
				if (word.equals(NEW_LINE)) {
					if (currentLine.length() > 0) {
						pageText.append(currentLine);
						currentLine = new StringBuilder();
						numLines++;
						if (numLines >= 13) {
							break;
						}
					}
					++usedWords;
					if (pageText.length() == 0) {
						continue;
					}
					pageText.append(word);
					numLines++;
					if (numLines < 13) {
						continue;
					}
					break;
				}
				currentLine.append(word);
				++usedWords;
				if (i < splitTxtWords.size() - 1) {
					currentLine.append(' ');
				}
				if (currentLine.length() >= 17) {
					pageText.append(currentLine);
					currentLine = new StringBuilder();
					numLines++;
					if (numLines >= 13) {
						break;
					}
				}
			}
			if (currentLine.length() > 0) {
				pageText.append(currentLine);
				currentLine.setLength(0);
				++numLines;
			}
			for (i = 0; i < usedWords; ++i) {
				splitTxtWords.remove(0);
			}
			loreTextPages.add(pageText.toString());
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

	private String formatRandom(String text, Random random) {
		String text1 = text;
		int lastIndexStart = -1;
		do {
			String formatted;
			String unformatted;
			block16:
			{
				String s1;
				int indexStart = text1.indexOf('{', lastIndexStart + 1);
				int indexEnd = text1.indexOf('}');
				lastIndexStart = indexStart;
				if (indexStart < 0 || indexEnd <= indexStart) {
					return text1;
				}
				unformatted = text1.substring(indexStart, indexEnd + 1);
				formatted = unformatted.substring(1, unformatted.length() - 1);
				if (formatted.startsWith("num:")) {
					try {
						s1 = formatted.substring("num:".length());
						int i1 = s1.indexOf(CODE_CATEGORY_SEPARATOR);
						String s2 = s1.substring(0, i1);
						String s3 = s1.substring(i1 + CODE_CATEGORY_SEPARATOR.length());
						int min = Integer.parseInt(s2);
						int max = Integer.parseInt(s3);
						int number = MathHelper.getRandomIntegerInRange(random, min, max);
						formatted = String.valueOf(number);
					} catch (Exception e) {
						GOTLog.getLogger().error("Hummel009: Error formatting number {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("name:")) {
					try {
						String namebank = formatted.substring("name:".length());
						if (!GOTNames.nameBankExists(namebank)) {
							GOTLog.getLogger().error("Hummel009: No namebank exists for {}!", namebank);
							break block16;
						}
						formatted = GOTNames.getRandomName(namebank, random);
					} catch (Exception e) {
						GOTLog.getLogger().error("Hummel009: Error formatting name {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("choose:")) {
					try {
						String remaining = formatted.substring("choose:".length());
						ArrayList<String> words = new ArrayList<>();
						while (!remaining.isEmpty()) {
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
						GOTLog.getLogger().error("Hummel009: Error formatting choice {} in text: {}", unformatted, loreName);
						e.printStackTrace();
					}
				}
			}
			text1 = Pattern.compile(unformatted, Pattern.LITERAL).matcher(text1).replaceFirst(Matcher.quoteReplacement(formatted));
		} while (true);
	}

	public enum LoreCategory {
		WESTEROS("westeros"), ESSOS("essos"), YITI("yiti"), ASSHAI("asshai"), SOTHORYOS("sothoryos"), MOSSOVY("mossovy");

		private final Collection<GOTLore> loreList = new ArrayList<>();
		private final String categoryName;

		LoreCategory(String s) {
			categoryName = s;
		}

		private static LoreCategory forName(String s) {
			for (LoreCategory r : values()) {
				if (s.equalsIgnoreCase(r.categoryName)) {
					return r;
				}
			}
			return null;
		}

		private void addLore(GOTLore lore) {
			loreList.add(lore);
		}

		public Collection<GOTLore> getLoreList() {
			return loreList;
		}

		private String getCategoryName() {
			return categoryName;
		}
	}
}