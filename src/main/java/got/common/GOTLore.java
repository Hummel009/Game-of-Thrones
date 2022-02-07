package got.common;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.ModContainer;
import got.GOT;
import got.common.database.*;
import got.common.util.GOTLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;

public class GOTLore {
	private static String newline = "\n";
	private static String codeMetadata = "#";
	private static String codeTitle = "title:";
	private static String codeAuthor = "author:";
	private static String codeCategory = "types:";
	private static String codeCategorySeparator = ",";
	private static String codeReward = "reward";
	private String loreName;
	private String loreTitle;
	private String loreAuthor;
	private String loreText;
	private boolean isRewardable;

	private GOTLore(String name, String title, String auth, String text, List<LoreCategory> categories, boolean reward) {
		loreName = name;
		loreTitle = title;
		loreAuthor = auth;
		loreText = text;
		isRewardable = reward;
	}

	public ItemStack createLoreBook(Random random) {
		ItemStack itemstack = new ItemStack(Items.written_book);
		NBTTagCompound data = new NBTTagCompound();
		itemstack.setTagCompound(data);
		String title = formatRandom(loreTitle, random);
		String author = formatRandom(loreAuthor, random);
		String text = formatRandom(loreText, random);
		List<String> textPages = GOTLore.organisePages(text);
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
		int lastIndexStart = -1;
		do {
			String formatted;
			String unformatted;
			block16: {
				String s1;
				int indexStart = text.indexOf("{", lastIndexStart + 1);
				int indexEnd = text.indexOf("}");
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
						GOTLog.getLogger().error("Hummel009: Error formatting number " + unformatted + " in text: " + loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("name:")) {
					try {
						String namebank = s1 = formatted.substring("name:".length());
						if (!GOTNames.nameBankExists(namebank)) {
							GOTLog.getLogger().error("Hummel009: No namebank exists for " + namebank + "!");
							break block16;
						}
						formatted = GOTNames.getRandomName(namebank, random);
					} catch (Exception e) {
						GOTLog.getLogger().error("Hummel009: Error formatting name " + unformatted + " in text: " + loreName);
						e.printStackTrace();
					}
				} else if (formatted.startsWith("choose:")) {
					try {
						String remaining = formatted.substring("choose:".length());
						ArrayList<String> words = new ArrayList<>();
						while (remaining.length() > 0) {
							String word;
							int indexOf = remaining.indexOf("/");
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
						GOTLog.getLogger().error("Hummel009: Error formatting choice " + unformatted + " in text: " + loreName);
						e.printStackTrace();
					}
				}
			}
			text = Pattern.compile(unformatted, 16).matcher(text).replaceFirst(Matcher.quoteReplacement(formatted));
		} while (true);
		return text;
	}

	public static GOTLore getMultiRandomLore(List<LoreCategory> categories, Random random, boolean rewardsOnly) {
		ArrayList<GOTLore> allLore = new ArrayList<>();
		for (LoreCategory c : categories) {
			for (GOTLore lore : c.loreList) {
				if (allLore.contains(lore) || rewardsOnly && !lore.isRewardable) {
					continue;
				}
				allLore.add(lore);
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
					if (!s.startsWith(path = "assets/got/lore/" + GOTConfig.getLanguageCode() + "/") || !s.endsWith(".txt")) {
						continue;
					}
					s = s.substring(path.length());
					int i = s.indexOf(".txt");
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
						loreReaders.put(s, reader);
					} catch (Exception e) {
						GOTLog.getLogger().error("Failed to load GOT lore " + s + "from zip file");
						e.printStackTrace();
					}
				}
			} else {
				File nameBankDir = new File(GOTRegistry.class.getResource("/assets/got/lore/" + GOTConfig.getLanguageCode()).toURI());
				for (File file : nameBankDir.listFiles()) {
					String s = file.getName();
					int i = s.indexOf(".txt");
					if (i < 0) {
						GOTLog.getLogger().error("Failed to load GOT lore " + s + " from MCP folder; name bank files must be in .txt format");
						continue;
					}
					try {
						s = s.substring(0, i);
						BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file)), Charsets.UTF_8.name()));
						loreReaders.put(s, reader);
					} catch (Exception e) {
						GOTLog.getLogger().error("Failed to load GOT lore " + s + " from MCP folder");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			GOTLog.getLogger().error("Failed to load GOT lore");
			e.printStackTrace();
		}
		for (Map.Entry entry : loreReaders.entrySet()) {
			String loreName = (String) entry.getKey();
			BufferedReader reader = (BufferedReader) entry.getValue();
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
										if (categories.contains(category)) {
											continue;
										}
										categories.add(category);
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
								GOTLog.getLogger().warn("Hummel009: Loading lore " + loreName + ", no category exists for name " + (String) categoryName);
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
				Iterator<LoreCategory> categoryString1 = categories.iterator();
				while (categoryString1.hasNext()) {
					LoreCategory category = categoryString1.next();
					category.addLore(lore);
				}
			} catch (Exception e) {
				GOTLog.getLogger().error("Failed to load GOT lore: " + loreName);
				e.printStackTrace();
			}
		}
		for (LoreCategory category : LoreCategory.values()) {
			int num = category.loreList.size();
			int numReward = 0;
			for (GOTLore lore : category.loreList) {
				if (!lore.isRewardable) {
					continue;
				}
				++numReward;
			}
			GOTLog.getLogger().info("Hummel009: Category " + category.categoryName + " has loaded " + num + " lore texts, of which " + numReward + " rewardable");
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
			part = indexOf >= 0 ? remainingText.substring(0, indexOf) : remainingText;
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
				if (currentLine.length() < 17) {
					continue;
				}
				pageText = pageText + currentLine;
				currentLine = "";
				numLines++;
				if (numLines >= 13) {
					break;
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

	public enum LoreCategory {
		WESTEROS("westeros"), ESSOS("essos"), YITI("yiti"), ASSHAI("asshai"), SOTHORYOS("sothoryos");

		private String categoryName;
		private List<GOTLore> loreList = new ArrayList<>();

		LoreCategory(String s) {
			categoryName = s;
		}

		private void addLore(GOTLore lore) {
			loreList.add(lore);
		}

		private static LoreCategory forName(String s) {
			for (LoreCategory r : LoreCategory.values()) {
				if (!s.equalsIgnoreCase(r.categoryName)) {
					continue;
				}
				return r;
			}
			return null;
		}
	}

}