package got.client.sound;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.common.GOTDimension;
import got.common.util.*;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class GOTMusic implements IResourceManagerReloadListener {
	public static File musicDir;
	public static String jsonFilename = "music.json";
	public static String musicResourcePath = "musicpacks";
	public static GOTMusicResourceManager trackResourceManager;
	public static List<GOTMusicTrack> allTracks;
	public static Map<GOTBiomeMusic.MusicRegion, GOTRegionTrackPool> regionTracks;
	public static boolean initSubregions;
	public static Random musicRand;

	static {
		trackResourceManager = new GOTMusicResourceManager();
		allTracks = new ArrayList<>();
		regionTracks = new HashMap<>();
		initSubregions = false;
		musicRand = new Random();
	}

	public GOTMusic() {
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 event) {
		Minecraft.getMinecraft();
		if (!allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof GOTMusicTrack)) {
			if (GOTMusic.isGOTDimension()) {
				event.result = null;
				return;
			}
			if (GOTMusic.isMenuMusic() && !GOTMusic.getTracksForRegion(GOTBiomeMusic.MENU, null).isEmpty()) {
				event.result = null;
			}
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		GOTMusic.loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager) resourcemanager);
	}

	public void update() {
		GOTMusicTicker.update(musicRand);
	}

	public static void addTrackToRegions(GOTMusicTrack track) {
		allTracks.add(track);
		for (GOTBiomeMusic region : track.getAllRegions()) {
			if (region.hasNoSubregions()) {
				GOTMusic.getTracksForRegion(region, null).addTrack(track);
			} else {
				for (String sub : track.getRegionInfo(region).getSubregions()) {
					GOTMusic.getTracksForRegion(region, sub).addTrack(track);
				}
			}
		}
	}

	public static void generateReadme() throws IOException {
		File readme = new File(musicDir, "readme.txt");
		readme.createNewFile();
		PrintStream writer = new PrintStream(new FileOutputStream(readme));
		ResourceLocation template = new ResourceLocation("got:music/readme.txt");
		InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8.name()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if ("#REGIONS#".equals(line)) {
				writer.println("all");
				for (Enum region : GOTBiomeMusic.values()) {
					StringBuilder regionString = new StringBuilder();
					regionString.append(((GOTBiomeMusic) region).regionName);
					List<String> subregions = ((GOTBiomeMusic) region).getAllSubregions();
					if (!subregions.isEmpty()) {
						String subs = "";
						for (String s : subregions) {
							if (subs.length() > 0) {
								subs = subs + ", ";
							}
							subs = subs + s;
						}
						regionString.append(": {").append(subs).append("}");
					}
					writer.println(regionString.toString());
				}
				continue;
			}
			if ("#CATEGORIES#".equals(line)) {
				for (Enum category : GOTMusicCategory.values()) {
					String catString = ((GOTMusicCategory) category).categoryName;
					writer.println(catString);
				}
				continue;
			}
			writer.println(line);
		}
		writer.close();
		reader.close();
	}

	public static GOTRegionTrackPool getTracksForRegion(GOTBiomeMusic region, String sub) {
		if (region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
			GOTBiomeMusic.MusicRegion key = region.getSubregion(sub);
			GOTRegionTrackPool regionPool = regionTracks.get(key);
			if (regionPool == null) {
				regionPool = new GOTRegionTrackPool(region, sub);
				regionTracks.put(key, regionPool);
			}
			return regionPool;
		}
		GOTLog.logger.warn("Hummel009: No subregion " + sub + " for region " + region.regionName + "!");
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
			JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8.name()));
			JsonParser parser = new JsonParser();
			ArrayList<GOTMusicTrack> packTracks = new ArrayList<>();
			JsonObject root = parser.parse(reader).getAsJsonObject();
			JsonArray rootArray = root.get("tracks").getAsJsonArray();
			for (JsonElement e : rootArray) {
				JsonObject trackData = e.getAsJsonObject();
				String filename = trackData.get("file").getAsString();
				ZipEntry trackEntry = zip.getEntry("assets/musicpacks/" + filename);
				if (trackEntry == null) {
					GOTLog.logger.warn("Hummel009: Track " + filename + " in pack " + zip.getName() + " does not exist!");
					continue;
				}
				InputStream trackStream = zip.getInputStream(trackEntry);
				GOTMusicTrack track = new GOTMusicTrack(filename);
				if (trackData.has("title")) {
					String title = trackData.get("title").getAsString();
					track.setTitle(title);
				}
				JsonArray regions = trackData.get("regions").getAsJsonArray();
				for (Object r : regions) {
					GOTBiomeMusic region;
					JsonObject regionData = ((JsonElement) r).getAsJsonObject();
					String regionName = regionData.get("name").getAsString();
					boolean allRegions = false;
					if ("all".equalsIgnoreCase(regionName)) {
						region = null;
						allRegions = true;
					} else {
						region = GOTBiomeMusic.forName(regionName);
						if (region == null) {
							GOTLog.logger.warn("Hummel009: No region named " + regionName + "!");
							continue;
						}
					}
					ArrayList<String> subregionNames = new ArrayList<>();
					if (region != null && regionData.has("sub")) {
						JsonArray subList = regionData.get("sub").getAsJsonArray();
						for (Object s : subList) {
							String sub = ((JsonElement) s).getAsString();
							if (region.hasSubregion(sub)) {
								subregionNames.add(sub);
								continue;
							}
							GOTLog.logger.warn("Hummel009: No subregion " + sub + " for region " + region.regionName + "!");
						}
					}
					ArrayList<GOTMusicCategory> regionCategories = new ArrayList<>();
					if (region != null && regionData.has("categories")) {
						JsonArray catList = regionData.get("categories").getAsJsonArray();
						Iterator<JsonElement> s = catList.iterator();
						while (s.hasNext()) {
							JsonElement cat = s.next();
							String categoryName = cat.getAsString();
							GOTMusicCategory category = GOTMusicCategory.forName(categoryName);
							if (category != null) {
								regionCategories.add(category);
								continue;
							}
							GOTLog.logger.warn("Hummel009: No category named " + categoryName + "!");
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
							continue;
						}
						for (GOTMusicCategory cat : regionCategories) {
							regInfo.addCategory(cat);
						}
					}
				}
				if (trackData.has("authors")) {
					JsonArray authorList = trackData.get("authors").getAsJsonArray();
					Iterator<JsonElement> r = authorList.iterator();
					while (r.hasNext()) {
						JsonElement a = r.next();
						String author = a.getAsString();
						track.addAuthor(author);
					}
				}
				track.loadTrack(trackStream);
				packTracks.add(track);
			}
			reader.close();
			GOTLog.logger.info("Hummel009: Successfully loaded music pack " + zip.getName() + " with " + packTracks.size() + " tracks");
		}
	}

	public static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
		musicDir = new File(mcDir, musicResourcePath);
		if (!musicDir.exists()) {
			musicDir.mkdirs();
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
		for (File file : musicDir.listFiles()) {
			if ((file.isFile() && file.getName().endsWith(".zip"))) {
				try {
					FileResourcePack resourcePack = new FileResourcePack(file);
					resourceMgr.reloadResourcePack(resourcePack);
					ZipFile zipFile = new ZipFile(file);
					GOTMusic.loadMusicPack(zipFile, resourceMgr);
				} catch (Exception e) {
					GOTLog.logger.warn("Hummel009: Failed to load music pack " + file.getName() + "!");
					e.printStackTrace();
				}
			}
		}
		try {
			GOTMusic.generateReadme();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Reflect {
		public static SoundRegistry getSoundRegistry() {
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			try {
				return (SoundRegistry) ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, "sndRegistry", "field_147697_e");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}

		public static void putDomainResourceManager(String domain, IResourceManager manager) {
			SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			try {
				Map map = (Map) ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, masterManager, "domainResourceManagers", "field_110548_a");
				map.put(domain, manager);
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}
	}

}
