package got.common;

import java.io.File;
import java.util.*;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.util.GOTModChecker;
import net.minecraft.world.World;
import net.minecraftforge.common.config.*;

public class GOTConfig {
	public static Configuration config;
	public static List<String> allCategories;
	public static String CATEGORY_LANGUAGE;
	public static String CATEGORY_GAMEPLAY;
	public static String CATEGORY_GUI;
	public static String CATEGORY_ENVIRONMENT;
	public static String CATEGORY_MISC;
	public static boolean allowBannerProtection;
	public static boolean allowSelfProtectingBanners;
	public static boolean allowMiniquests;
	public static boolean allowBountyQuests;
	public static boolean enableFastTravel;
	public static boolean enableConquest;
	public static boolean removeGoldenAppleRecipes;
	public static boolean enablePortals;
	public static boolean enableDothrakiSkirmish;
	public static boolean enchantingVanilla;
	public static boolean enchantingGOT;
	public static boolean clearMap;
	public static boolean enchantingAutoRemoveVanilla;
	public static int bannerWarningCooldown;
	public static boolean dropMutton;
	public static boolean drunkMessages;
	public static boolean GOTRespawning;
	public static int GOTBedRespawnThreshold;
	public static int GOTWorldRespawnThreshold;
	public static int GOTMinRespawn;
	public static int GOTMaxRespawn;
	public static boolean generateMapFeatures;
	public static boolean changedHunger;
	public static boolean canAlwaysEat;
	public static int forceMapLocations;
	public static boolean enableInvasions;
	public static boolean removeDiamondArmorRecipes;
	public static int preventTraderKidnap;
	public static boolean alwaysShowAlignment;
	public static int alignmentXOffset;
	public static int alignmentYOffset;
	public static boolean displayAlignmentAboveHead;
	public static boolean enableSepiaMap;
	public static boolean osrsMap;
	public static boolean enableOnscreenCompass;
	public static boolean compassExtraInfo;
	public static boolean hiredUnitHealthBars;
	public static boolean hiredUnitIcons;
	public static boolean bladeGlow;
	public static boolean immersiveSpeech;
	public static boolean immersiveSpeechChatLog;
	public static boolean meleeAttackMeter;
	public static boolean mapLabels;
	public static boolean mapLabelsConquest;
	public static boolean enableQuestTracker;
	public static boolean trackingQuestRight;
	public static boolean customMainMenu;
	public static boolean fellowPlayerHealthBars;
	public static boolean displayCoinCounts;
	public static boolean enableGOTSky;
	public static boolean enableFrostfangsMist;
	public static boolean enableAmbience;
	public static boolean enableSunFlare;
	public static int cloudRange;
	public static boolean checkUpdates;
	public static boolean strTimelapse;
	public static int strTimelapseInterval;
	public static boolean fixMobSpawning;
	public static int mobSpawnInterval;
	public static int musicIntervalMin;
	public static int musicIntervalMax;
	public static boolean displayMusicTrack;
	public static int musicIntervalMenuMin;
	public static int musicIntervalMenuMax;
	public static boolean fixRenderDistance;
	public static boolean preventMessageExploit;
	public static boolean cwpLog;
	public static String languageCode = "ru";
	public static int fellowshipMaxSize;
	public static int playerDataClearingInterval;
	public static int MIN_PLAYER_DATA_CLEARING_INTERVAL = 600;
	public static boolean enableColouredRoofs;

	static {
		allCategories = new ArrayList<>();
		CATEGORY_LANGUAGE = GOTConfig.getCategory("1_language");
		CATEGORY_GAMEPLAY = GOTConfig.getCategory("2_gameplay");
		CATEGORY_GUI = GOTConfig.getCategory("3_gui");
		CATEGORY_ENVIRONMENT = GOTConfig.getCategory("4_environment");
		CATEGORY_MISC = GOTConfig.getCategory("5_misc");
	}

	public static String getCategory(String category) {
		allCategories.add(category);
		return category;
	}

	public static List<IConfigElement> getConfigElements() {
		ArrayList<IConfigElement> list = new ArrayList<>();
		for (String category : allCategories) {
			list.addAll(new ConfigElement(config.getCategory(category)).getChildElements());
		}
		return list;
	}

	public static int getFellowshipMaxSize(World world) {
		if (!world.isRemote) {
			return fellowshipMaxSize;
		}
		return GOTLevelData.clientside_thisServer_fellowshipMaxSize;
	}

	public static boolean isEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingVanilla;
		}
		return GOTLevelData.clientside_thisServer_enchanting;
	}

	public static boolean isGOTEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingGOT;
		}
		return GOTLevelData.clientside_thisServer_enchantingGOT;
	}

	public static void load() {
		enableColouredRoofs = config.get(CATEGORY_GAMEPLAY, "Enable coloured roofs in Westeros", false, "Replace default black roofs with dyed clay tiles").getBoolean();
		languageCode = config.getString("languageCode", CATEGORY_LANGUAGE, languageCode, "Choose:" + GOT.langsName + ".");
		clearMap = config.get(CATEGORY_GAMEPLAY, "No fixed structures and characters", false, "Useful for servers. Disable fixed structures to build your own").getBoolean();
		allowBannerProtection = config.get(CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean();
		allowSelfProtectingBanners = config.get(CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean();
		allowMiniquests = config.get(CATEGORY_GAMEPLAY, "NPCs give mini-quests", true).getBoolean();
		allowBountyQuests = config.get(CATEGORY_GAMEPLAY, "NPCs give bounty mini-quests", true, "Allow NPCs to generate mini-quests to kill enemy players").getBoolean();
		enableFastTravel = config.get(CATEGORY_GAMEPLAY, "Enable Fast Travel", true).getBoolean();
		fellowshipMaxSize = config.get(CATEGORY_GAMEPLAY, "Fellowship maximum size", -1, "Maximum player count for Fellowships. Negative = no limit").getInt();
		enableConquest = config.get(CATEGORY_GAMEPLAY, "Enable Conquest", true).getBoolean();
		removeGoldenAppleRecipes = config.get(CATEGORY_GAMEPLAY, "Remove Golden Apple recipes", true).getBoolean();
		enablePortals = config.get(CATEGORY_GAMEPLAY, "Enable Game of Thrones Portals", true, "Enable or disable the buildable Game of Thrones portals (excluding the Circle Portal). If disabled, portals can still be made, but will not function").getBoolean();
		enableDothrakiSkirmish = config.get(CATEGORY_GAMEPLAY, "Enable Dothraki Skirmishes", true).getBoolean();
		enchantingVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Vanilla System", false, "Enable the vanilla enchanting system: if disabled, prevents players from enchanting items, but does not affect existing enchanted items").getBoolean();
		enchantingGOT = config.get(CATEGORY_GAMEPLAY, "Enchanting: GOT System", true, "Enable the GOT enchanting system: if disabled, prevents newly crafted items, loot chest items, etc. from having modifiers applied, but does not affect existing modified items").getBoolean();
		enchantingAutoRemoveVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Auto-remove vanilla enchants", false, "Intended for servers. If enabled, enchantments will be automatically removed from items").getBoolean();
		bannerWarningCooldown = config.get(CATEGORY_GAMEPLAY, "Protection Warning Cooldown", 20, "Cooldown time (in ticks) between appearances of the warning message for banner-public land").getInt();
		dropMutton = config.get(CATEGORY_GAMEPLAY, "Mutton Drops", true, "Enable or disable sheep dropping the mod's mutton items").getBoolean();
		drunkMessages = config.get(CATEGORY_GAMEPLAY, "Enable Drunken Messages", true).getBoolean();
		GOTRespawning = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Enable", true, "If enabled, when a player dies in Game of Thrones far from their spawn point, they will respawn somewhere near their death point instead").getBoolean();
		GOTBedRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Bed Threshold", 5000, "Threshold distance from spawn for applying Game of Thrones Respawning when the player's spawn point is a bed").getInt();
		GOTWorldRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: World Threshold", 2000, "Threshold distance from spawn for applying Game of Thrones respawning when the player's spawn point is the world spawn (no bed)").getInt();
		GOTMinRespawn = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Min Respawn Range", 500, "Minimum possible range to place the player from their death point").getInt();
		GOTMaxRespawn = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Max Respawn Range", 1500, "Maximum possible range to place the player from their death point").getInt();
		generateMapFeatures = config.get(CATEGORY_GAMEPLAY, "Generate map features", true).getBoolean();
		changedHunger = config.get(CATEGORY_GAMEPLAY, "Hunger changes", true, "Food meter decreases more slowly").getBoolean();
		canAlwaysEat = config.get(CATEGORY_GAMEPLAY, "Feast Mode", true, "Food can always be eaten regardless of hunger").getBoolean();
		forceMapLocations = config.get(CATEGORY_GAMEPLAY, "Force Hide/Show Map Locations", 0, "Force hide or show players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show").getInt();
		enableInvasions = config.get(CATEGORY_GAMEPLAY, "Enable Invasions", true).getBoolean();
		removeDiamondArmorRecipes = config.get(CATEGORY_GAMEPLAY, "Remove diamond armour recipes", false).getBoolean();
		preventTraderKidnap = config.get(CATEGORY_GAMEPLAY, "Prevent trader transport range", 0, "Prevent transport of structure-bound traders beyond this distance outside their initial home range (0 = disabled)").getInt();
		playerDataClearingInterval = config.get(CATEGORY_MISC, "Playerdata clearing interval", 600, "Tick interval between clearing offline LOTR-playerdata from the cache. Offline players' data is typically loaded to serve features like fellowships and their shared custom waypoints. Higher values may reduce server lag, as data will have to be reloaded from disk less often, but will result in higher RAM usage to some extent").getInt();
		alwaysShowAlignment = config.get(CATEGORY_GUI, "Always show alignment", false, "If set to false, the alignment bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean();
		alignmentXOffset = config.get(CATEGORY_GUI, "Alignment x-offset", 0, "Configure the x-position of the alignment bar on-screen. Negative values move it left, positive values right").getInt();
		alignmentYOffset = config.get(CATEGORY_GUI, "Alignment y-offset", 0, "Configure the y-position of the alignment bar on-screen. Negative values move it up, positive values down").getInt();
		displayAlignmentAboveHead = config.get(CATEGORY_GUI, "Display alignment above head", true, "Enable or disable the rendering of other players' alignment values above their heads").getBoolean();
		enableSepiaMap = config.get(CATEGORY_GUI, "Sepia Map", false, "Display the Game of Thrones map in sepia colours").getBoolean();
		osrsMap = config.get(CATEGORY_GUI, "OSRS Map", false, "It's throwback time. (Requires game restart)").getBoolean();
		enableOnscreenCompass = config.get(CATEGORY_GUI, "On-screen Compass", true).getBoolean();
		compassExtraInfo = config.get(CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean();
		hiredUnitHealthBars = config.get(CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean();
		hiredUnitIcons = config.get(CATEGORY_GUI, "Hired NPC Icons", true).getBoolean();
		bladeGlow = config.get(CATEGORY_GUI, "Animated sword glow", true).getBoolean();
		immersiveSpeech = config.get(CATEGORY_GUI, "Immersive Speech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box").getBoolean();
		immersiveSpeechChatLog = config.get(CATEGORY_GUI, "Immersive Speech Chat Logs", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled").getBoolean();
		meleeAttackMeter = config.get(CATEGORY_GUI, "Melee attack meter", true).getBoolean();
		mapLabels = config.get(CATEGORY_GUI, "Map Labels", true).getBoolean();
		mapLabelsConquest = config.get(CATEGORY_GUI, "Map Labels - Conquest", true).getBoolean();
		enableQuestTracker = config.get(CATEGORY_GUI, "Enable quest tracker", true).getBoolean();
		trackingQuestRight = config.get(CATEGORY_GUI, "Flip quest tracker", false, "Display the quest tracker on the right-hand side of the screen instead of the left").getBoolean();
		customMainMenu = config.get(CATEGORY_GUI, "Custom main menu", true, "Use the mod's custom main menu screen").getBoolean();
		fellowPlayerHealthBars = config.get(CATEGORY_GUI, "Fellow Player Health Bars", true).getBoolean();
		displayCoinCounts = config.get(CATEGORY_GUI, "Inventory coin counts", true).getBoolean();
		enableGOTSky = config.get(CATEGORY_ENVIRONMENT, "Game of Thrones sky", true, "Toggle the new Game of Thrones sky").getBoolean();
		enableFrostfangsMist = config.get(CATEGORY_ENVIRONMENT, "Foggy Frostfangs", true, "Toggle mist overlay in the Frostfangs").getBoolean();
		enableAmbience = config.get(CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean();
		enableSunFlare = config.get(CATEGORY_ENVIRONMENT, "Sun flare", true).getBoolean();
		cloudRange = config.get(CATEGORY_ENVIRONMENT, "Cloud range", 1024, "Game of Thrones cloud rendering range. To use vanilla clouds, set this to a non-positive value").getInt();
		checkUpdates = config.get(CATEGORY_MISC, "Check for updates", true, "Disable this if you will be playing offline").getBoolean();
		fixMobSpawning = config.get(CATEGORY_MISC, "Fix mob spawning lag", true, "Fix a major source of server lag caused by the vanilla mob spawning system").getBoolean();
		mobSpawnInterval = config.get(CATEGORY_MISC, "Mob spawn interval", 0, "Tick interval between mob spawn cycles (which are then run multiple times to compensate). Higher values may reduce server lag").getInt();
		musicIntervalMin = config.get(CATEGORY_MISC, "Music Interval: Min.", 30, "Minimum time (seconds) between GOT music tracks").getInt();
		musicIntervalMax = config.get(CATEGORY_MISC, "Music Interval: Max.", 150, "Maximum time (seconds) between GOT music tracks").getInt();
		displayMusicTrack = config.get(CATEGORY_MISC, "Display music track", false, "Display the name of a GOT music track when it begins playing").getBoolean();
		musicIntervalMenuMin = config.get(CATEGORY_MISC, "Menu Music Interval: Min.", 10, "Minimum time (seconds) between GOT menu music tracks").getInt();
		musicIntervalMenuMax = config.get(CATEGORY_MISC, "Menu Music Interval: Max.", 20, "Maximum time (seconds) between GOT menu music tracks").getInt();
		fixRenderDistance = config.get(CATEGORY_MISC, "Fix render distance", true, "Fix a vanilla crash caused by having render distance > 16 in the options.txt. NOTE: This will not run if Optifine is installed").getBoolean();
		preventMessageExploit = config.get(CATEGORY_MISC, "Fix /msg exploit", true, "Disable usage of @a, @r, etc. in the /msg command, to prevent exploiting it as a player locator").getBoolean();
		cwpLog = config.get(CATEGORY_MISC, "Custom Waypoint logging", false).getBoolean();

		if (GOTModChecker.isCauldronServer()) {
			FMLLog.info("Hummel009: Successfully detected Cauldron server");
		}
		if (config.hasChanged()) {
			config.save();
		}
	}

	public static void setStructureTimelapse(boolean flag) {
		strTimelapse = flag;
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse").set(strTimelapse);
		config.save();
	}

	public static void setStructureTimelapseInterval(int i) {
		strTimelapseInterval = i;
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse Interval").set(strTimelapseInterval);
		config.save();
	}

	public static void setupAndLoad() {
		config = new Configuration(new File("config", "GOT.cfg"));
		GOTConfig.load();
	}

	public static void toggleMapLabels() {
		mapLabels = !mapLabels;
		config.getCategory(CATEGORY_GUI).get("Map Labels").set(mapLabels);
		config.save();
	}

	public static void toggleMapLabelsConquest() {
		mapLabelsConquest = !mapLabelsConquest;
		config.getCategory(CATEGORY_GUI).get("Map Labels - Conquest").set(mapLabelsConquest);
		config.save();
	}

	public static void toggleSepia() {
		enableSepiaMap = !enableSepiaMap;
		config.getCategory(CATEGORY_GUI).get("Sepia Map").set(enableSepiaMap);
		config.save();
	}
}
