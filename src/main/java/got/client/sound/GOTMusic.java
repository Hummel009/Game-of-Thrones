package got.client.sound;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.common.GOTDimension;
import got.common.util.GOTLog;
import got.common.util.GOTReflection;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTMusic implements IResourceManagerReloadListener {
	public static File musicDir;
	public static String jsonFilename = "music.json";
	public static String musicResourcePath = "musicpacks";
	public static GOTMusicResourceManager trackResourceManager = new GOTMusicResourceManager();
	public static List<GOTMusicTrack> allTracks = new ArrayList<>();
	public static Map<GOTBiomeMusic.MusicRegion, GOTRegionTrackPool> regionTracks = new HashMap<>();
	public static boolean initSubregions;
	public static Random musicRand = new Random();

	public GOTMusic() {
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void addTrackToRegions(GOTMusicTrack track) {
		allTracks.add(track);
		for (GOTBiomeMusic region : track.getAllRegions()) {
			if (region.hasNoSubregions()) {
				Objects.requireNonNull(getTracksForRegion(region, null)).addTrack(track);
			} else {
				for (String sub : track.getRegionInfo(region).getSubregions()) {
					Objects.requireNonNull(getTracksForRegion(region, sub)).addTrack(track);
				}
			}
		}
	}

	public static void generateReadme() throws IOException {
		File readme = new File(musicDir, "readme.txt");
		boolean created = readme.createNewFile();
		if (!created) {
			GOTLog.logger.info("GOTMusic: file wasn't created");
		}
		PrintStream writer = new PrintStream(Files.newOutputStream(readme.toPath()), false, StandardCharsets.UTF_8.name());
		ResourceLocation template = new ResourceLocation("got:music/readme.txt");
		InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8));
		String line;
		while ((line = reader.readLine()) != null) {
			if ("#REGIONS#".equals(line)) {
				writer.println("all");
				for (GOTBiomeMusic region : GOTBiomeMusic.values()) {
					StringBuilder regionString = new StringBuilder();
					regionString.append(region.regionName);
					List<String> subregions = region.getAllSubregions();
					if (!subregions.isEmpty()) {
						StringBuilder subs = new StringBuilder();
						for (String s : subregions) {
							if (subs.length() > 0) {
								subs.append(", ");
							}
							subs.append(s);
						}
						regionString.append(": {").append(subs).append("}");
					}
					writer.println(regionString);
				}
			} else if ("#CATEGORIES#".equals(line)) {
				for (GOTMusicCategory category : GOTMusicCategory.values()) {
					String catString = category.categoryName;
					writer.println(catString);
				}
			} else {
				writer.println(line);
			}
		}
		writer.close();
		reader.close();
	}

	public static GOTRegionTrackPool getTracksForRegion(GOTBiomeMusic region, String sub) {
		if (region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
			GOTBiomeMusic.MusicRegion key = region.getSubregion(sub);
			return regionTracks.computeIfAbsent(key, k -> new GOTRegionTrackPool(region, sub));
		}
		GOTLog.logger.warn("Hummel009: No subregion {} for region {}!", sub, region.regionName);
		return null;
	}

	public static boolean isGOTDimension() {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		return entityplayer != null && world != null && world.provider instanceof GOTWorldProvider;
	}

	public static boolean isMenuMusic() {
		Minecraft mc = Minecraft.getMinecraft();
		return mc.func_147109_W() == MusicTicker.MusicType.MENU;
	}

	public static void loadMusicPack(ZipFile zip, SimpleReloadableResourceManager resourceMgr) throws IOException {
		ZipEntry entry = zip.getEntry(jsonFilename);
		if (entry != null) {
			InputStream stream = zip.getInputStream(entry);
			JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8));
			JsonParser parser = new JsonParser();
			ArrayList<GOTMusicTrack> packTracks = new ArrayList<>();
			JsonObject root = parser.parse(reader).getAsJsonObject();
			JsonArray rootArray = root.get("tracks").getAsJsonArray();
			for (JsonElement e : rootArray) {
				JsonObject trackData = e.getAsJsonObject();
				String filename = trackData.get("file").getAsString();
				ZipEntry trackEntry = zip.getEntry("assets/gotmusic/" + filename);
				if (trackEntry == null) {
					GOTLog.logger.warn("Hummel009: Track {} in pack {} does not exist!", filename, zip.getName());
				} else {
					InputStream trackStream = zip.getInputStream(trackEntry);
					GOTMusicTrack track = new GOTMusicTrack(filename);
					if (trackData.has("title")) {
						String title = trackData.get("title").getAsString();
						track.setTitle(title);
					}
					JsonArray regions = trackData.get("regions").getAsJsonArray();
					for (JsonElement r : regions) {
						GOTBiomeMusic region;
						JsonObject regionData = r.getAsJsonObject();
						String regionName = regionData.get("name").getAsString();
						boolean allRegions = false;
						if ("all".equalsIgnoreCase(regionName)) {
							region = null;
							allRegions = true;
						} else {
							region = GOTBiomeMusic.forName(regionName);
							if (region == null) {
								GOTLog.logger.warn("Hummel009: No region named {}!", regionName);
								continue;
							}
						}
						ArrayList<String> subregionNames = new ArrayList<>();
						if (region != null && regionData.has("sub")) {
							JsonArray subList = regionData.get("sub").getAsJsonArray();
							for (JsonElement s : subList) {
								String sub = s.getAsString();
								if (region.hasSubregion(sub)) {
									subregionNames.add(sub);
								} else {
									GOTLog.logger.warn("Hummel009: No subregion {} for region {}!", sub, region.regionName);
								}
							}
						}
						ArrayList<GOTMusicCategory> regionCategories = new ArrayList<>();
						if (region != null && regionData.has("categories")) {
							JsonArray catList = regionData.get("categories").getAsJsonArray();
							for (JsonElement cat : catList) {
								String categoryName = cat.getAsString();
								GOTMusicCategory category = GOTMusicCategory.forName(categoryName);
								if (category != null) {
									regionCategories.add(category);
								} else {
									GOTLog.logger.warn("Hummel009: No category named {}!", categoryName);
								}
							}
						}
						double weight = -1.0;
						if (regionData.has("weight")) {
							weight = regionData.get("weight").getAsDouble();
						}
						ArrayList<GOTBiomeMusic> regionsAdd = new ArrayList<>();
						if (allRegions) {
							regionsAdd.addAll(Arrays.asList(GOTBiomeMusic.values()));
						} else {
							regionsAdd.add(region);
						}
						for (GOTBiomeMusic reg : regionsAdd) {
							GOTTrackRegionInfo regInfo = track.createRegionInfo(reg);
							if (weight >= 0.0) {
								regInfo.setWeight(weight);
							}
							if (subregionNames.isEmpty()) {
								regInfo.addAllSubregions();
							} else {
								for (String sub : subregionNames) {
									regInfo.addSubregion(sub);
								}
							}
							if (regionCategories.isEmpty()) {
								regInfo.addAllCategories();
							} else {
								for (GOTMusicCategory cat : regionCategories) {
									regInfo.addCategory(cat);
								}
							}
						}
					}
					if (trackData.has("authors")) {
						JsonArray authorList = trackData.get("authors").getAsJsonArray();
						for (JsonElement a : authorList) {
							String author = a.getAsString();
							track.addAuthor(author);
						}
					}
					track.loadTrack(trackStream);
					packTracks.add(track);
				}
			}
			reader.close();
			GOTLog.logger.info("Hummel009: Successfully loaded music pack {} with {} tracks", zip.getName(), packTracks.size());
		}
	}

	public static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
		musicDir = new File(mcDir, musicResourcePath);
		if (!musicDir.exists()) {
			boolean created = musicDir.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTMusic: directory wasn't created");
			}
		}
		allTracks.clear();
		regionTracks.clear();
		if (!initSubregions) {
			for (GOTDimension dim : GOTDimension.values()) {
				for (GOTBiome biome : dim.biomeList) {
					if (biome != null) {
						biome.getBiomeMusic();
					}
				}
			}
			initSubregions = true;
		}
		for (File file : Objects.requireNonNull(musicDir.listFiles())) {
			if (file.isFile() && file.getName().endsWith(".zip")) {
				try {
					FileResourcePack resourcePack = new FileResourcePack(file);
					resourceMgr.reloadResourcePack(resourcePack);
					ZipFile zipFile = new ZipFile(file);
					loadMusicPack(zipFile, resourceMgr);
				} catch (Exception e) {
					GOTLog.logger.warn("Hummel009: Failed to load music pack {}!", file.getName());
					e.printStackTrace();
				}
			}
		}
		try {
			generateReadme();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 event) {
		if (!allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof GOTMusicTrack)) {
			if (isGOTDimension()) {
				event.result = null;
				return;
			}
			if (isMenuMusic() && !Objects.requireNonNull(getTracksForRegion(GOTBiomeMusic.MENU, null)).isEmpty()) {
				event.result = null;
			}
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager) resourcemanager);
	}

	public void update() {
		GOTMusicTicker.update(musicRand);
	}

	public static class Reflect {
		public static SoundRegistry getSoundRegistry() {
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			try {
				return ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, "sndRegistry", "field_147697_e");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static void putDomainResourceManager(String domain, IResourceManager manager) {
			SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			try {
				Map<String, IResourceManager> map = ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, masterManager, "domainResourceManagers", "field_110548_a");
				map.put(domain, manager);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}
	}

}
