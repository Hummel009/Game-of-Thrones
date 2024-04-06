package got.client.event.forge;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.sound.*;
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
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GOTMusic implements IResourceManagerReloadListener {
	private static final String MUSIC_RESOURCE_PATH = "musicpacks";
	private static final Collection<GOTMusicTrack> ALL_TRACKS = new ArrayList<>();
	private static final Map<GOTMusicRegion.Sub, GOTRegionTrackPool> REGION_TRACKS = new HashMap<>();
	private static final Random MUSIC_RAND = new Random();

	private static File musicDir;
	private static boolean initSubregions;

	public static void addTrackToRegions(GOTMusicTrack track) {
		ALL_TRACKS.add(track);
		for (GOTMusicRegion region : track.getAllRegions()) {
			if (region.hasNoSubregions()) {
				getTracksForRegion(region, null).addTrack(track);
				continue;
			}
			for (String sub : track.getRegionInfo(region).getSubregions()) {
				getTracksForRegion(region, sub).addTrack(track);
			}
		}
	}

	private static void generateReadme() throws IOException {
		File readme = new File(musicDir, "readme.txt");
		boolean created = readme.createNewFile();
		if (!created) {
			GOTLog.logger.info("Hummel009: file wasn't created");
		}
		PrintStream writer = new PrintStream(Files.newOutputStream(readme.toPath()));
		ResourceLocation template = new ResourceLocation("got:music/readme.txt");
		InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8));
		String line;
		while ((line = reader.readLine()) != null) {
			if ("#REGIONS#".equals(line)) {
				writer.println("all");
				for (GOTMusicRegion region : GOTMusicRegion.values()) {
					StringBuilder regionString = new StringBuilder();
					regionString.append(region.getRegionName());
					List<String> subregions = region.getAllSubregions();
					if (!subregions.isEmpty()) {
						StringBuilder subs = new StringBuilder();
						for (String s : subregions) {
							if (subs.length() > 0) {
								subs.append(", ");
							}
							subs.append(s);
						}
						regionString.append(": {").append(subs).append('}');
					}
					writer.println(regionString);
				}
				continue;
			}
			if ("#CATEGORIES#".equals(line)) {
				for (GOTMusicCategory category : GOTMusicCategory.values()) {
					String catString = category.categoryName;
					writer.println(catString);
				}
				continue;
			}
			writer.println(line);
		}
		writer.close();
		reader.close();
	}

	public static GOTRegionTrackPool getTracksForRegion(GOTMusicRegion region, String sub) {
		if (region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
			GOTMusicRegion.Sub key = region.getSubregion(sub);
			GOTRegionTrackPool regionPool = REGION_TRACKS.get(key);
			if (regionPool == null) {
				regionPool = new GOTRegionTrackPool(region);
				REGION_TRACKS.put(key, regionPool);
			}
			return regionPool;
		}
		GOTLog.logger.warn("Hummel009: No subregion {} for region {}!", sub, region.getRegionName());
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

	private static void loadMusicPack(ZipFile zip) throws IOException {
		ZipEntry entry = zip.getEntry("music.json");
		if (entry != null) {
			InputStream stream = zip.getInputStream(entry);
			JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8));
			JsonParser parser = new JsonParser();
			List<GOTMusicTrack> packTracks = new ArrayList<>();
			JsonObject root = parser.parse(reader).getAsJsonObject();
			JsonArray rootArray = root.get("tracks").getAsJsonArray();
			for (JsonElement e : rootArray) {
				JsonObject trackData = e.getAsJsonObject();
				String filename = trackData.get("file").getAsString();
				ZipEntry trackEntry = zip.getEntry("assets/musicpacks/" + filename);
				if (trackEntry == null) {
					GOTLog.logger.warn("Hummel009: Track {} in pack {} does not exist!", filename, zip.getName());
					continue;
				}
				GOTMusicTrack track = new GOTMusicTrack(filename);
				if (trackData.has("title")) {
					String title = trackData.get("title").getAsString();
					track.setTitle(title);
				}
				JsonArray regions = trackData.get("regions").getAsJsonArray();
				for (JsonElement r : regions) {
					GOTMusicRegion region;
					JsonObject regionData = r.getAsJsonObject();
					String regionName = regionData.get("name").getAsString();
					boolean allRegions = false;
					if ("all".equalsIgnoreCase(regionName)) {
						region = null;
						allRegions = true;
					} else {
						region = GOTMusicRegion.forName(regionName);
						if (region == null) {
							GOTLog.logger.warn("Hummel009: No region named {}!", regionName);
							continue;
						}
					}
					Collection<String> subregionNames = new ArrayList<>();
					if (region != null && regionData.has("sub")) {
						JsonArray subList = regionData.get("sub").getAsJsonArray();
						for (JsonElement s : subList) {
							String sub = s.getAsString();
							if (region.hasSubregion(sub)) {
								subregionNames.add(sub);
								continue;
							}
							GOTLog.logger.warn("Hummel009: No subregion {} for region {}!", sub, region.getRegionName());
						}
					}
					Collection<GOTMusicCategory> regionCategories = new ArrayList<>();
					if (region != null && regionData.has("categories")) {
						JsonArray catList = regionData.get("categories").getAsJsonArray();
						for (JsonElement cat : catList) {
							String categoryName = cat.getAsString();
							GOTMusicCategory category = GOTMusicCategory.forName(categoryName);
							if (category != null) {
								regionCategories.add(category);
								continue;
							}
							GOTLog.logger.warn("Hummel009: No category named {}!", categoryName);
						}
					}
					double weight = -1.0D;
					if (regionData.has("weight")) {
						weight = regionData.get("weight").getAsDouble();
					}
					Collection<GOTMusicRegion> regionsAdd = new ArrayList<>();
					if (allRegions) {
						regionsAdd.addAll(Arrays.asList(GOTMusicRegion.values()));
					} else {
						regionsAdd.add(region);
					}
					for (GOTMusicRegion reg : regionsAdd) {
						GOTTrackRegionInfo regInfo = track.createRegionInfo(reg);
						if (weight >= 0.0D) {
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
					for (JsonElement a : authorList) {
						String author = a.getAsString();
						track.addAuthor(author);
					}
				}
				track.loadTrack();
				packTracks.add(track);
			}
			reader.close();
			GOTLog.logger.info("Hummel009: Successfully loaded music pack {} with {} tracks", zip.getName(), packTracks.size());
		}
	}

	private static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
		musicDir = new File(mcDir, MUSIC_RESOURCE_PATH);
		if (!musicDir.exists()) {
			boolean created = musicDir.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTMusic: directory wasn't created");
			}
		}
		ALL_TRACKS.clear();
		REGION_TRACKS.clear();
		if (!initSubregions) {
			for (GOTDimension dim : GOTDimension.values()) {
				for (GOTBiome biome : dim.biomeList) {
					if (biome == null) {
						continue;
					}
					biome.getBiomeMusic();
				}
			}
			initSubregions = true;
		}
		for (File file : musicDir.listFiles()) {
			if (!file.isFile() || !file.getName().endsWith(".zip")) {
				continue;
			}
			try {
				IResourcePack resourcePack = new FileResourcePack(file);
				resourceMgr.reloadResourcePack(resourcePack);
				ZipFile zipFile = new ZipFile(file);
				loadMusicPack(zipFile);
			} catch (Exception e) {
				GOTLog.logger.warn("Hummel009: Failed to load music pack {}!", file.getName());
				e.printStackTrace();
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
		if (!ALL_TRACKS.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof GOTMusicTrack)) {
			if (isGOTDimension()) {
				event.result = null;
				return;
			}
			if (isMenuMusic() && !getTracksForRegion(GOTMusicRegion.MENU, null).isEmpty()) {
				event.result = null;
			}
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager) resourcemanager);
	}

	public void update() {
		GOTMusicTicker.update(MUSIC_RAND);
	}

	public static class Reflect {
		private Reflect() {
		}

		public static SoundRegistry getSoundRegistry() {
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			try {
				return ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, new String[]{"sndRegistry", "field_147697_e"});
			} catch (Exception e) {
				GOTReflection.logFailure(e);
				return null;
			}
		}
	}
}