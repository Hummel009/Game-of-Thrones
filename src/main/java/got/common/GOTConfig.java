package got.common;

import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.util.GOTModChecker;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@SuppressWarnings({"WeakerAccess", "PublicField"})
public class GOTConfig {
	private static final String CATEGORY_LANGUAGE = "1_language";
	private static final String CATEGORY_GAMEPLAY = "2_gameplay";
	private static final String CATEGORY_GUI = "3_gui";
	private static final String CATEGORY_ENVIRONMENT = "4_environment";
	private static final String CATEGORY_MISC = "5_misc";

	public static String languageCode = "ru";
	public static boolean allowBannerProtection;
	public static boolean allowBountyQuests;
	public static boolean allowMiniquests;
	public static boolean allowSelfProtectingBanners;
	public static boolean alwaysShowReputation;
	public static boolean bladeGlow;
	public static boolean canAlwaysEat;
	public static boolean changedHunger;
	public static boolean checkUpdates;
	public static boolean compassExtraInfo;
	public static boolean customMainMenu;
	public static boolean cwpLog;
	public static boolean displayReputationAboveHead;
	public static boolean displayCoinCounts;
	public static boolean displayMusicTrack;
	public static boolean dropMutton;
	public static boolean drunkMessages;
	public static boolean enableAmbience;
	public static boolean enableConquest;
	public static boolean enableDothrakiSkirmish;
	public static boolean enableFastTravel;
	public static boolean enableBrotherhoodCreation;
	public static boolean enableFrostfangsMist;
	public static boolean enableGOTSky;
	public static boolean enableInvasions;
	public static boolean enableOnscreenCompass;
	public static boolean enableQuestTracker;
	public static boolean enableSepiaMap;
	public static boolean enableSunFlare;
	public static boolean enableTitles;
	public static boolean enableVillagerTrading;
	public static boolean enchantingAutoRemoveVanilla;
	public static boolean enchantingGOT;
	public static boolean enchantingVanilla;
	public static boolean fellowPlayerHealthBars;
	public static boolean fixRenderDistance;
	public static boolean generateMapFeatures;
	public static boolean hiredUnitHealthBars;
	public static boolean hiredUnitIcons;
	public static boolean immersiveSpeech;
	public static boolean immersiveSpeechChatLog;
	public static boolean knownWorldRespawning;
	public static boolean lgbt;
	public static boolean mapLabels;
	public static boolean mapLabelsConquest;
	public static boolean meleeAttackMeter;
	public static boolean osrsMap;
	public static boolean preventMessageExploit;
	public static boolean removeDiamondArmorRecipes;
	public static boolean removeGoldenAppleRecipes;
	public static boolean strictFactionTitleRequirements;
	public static boolean trackingQuestRight;
	public static boolean walkerFireDamage;

	public static int kwrBedRespawnThreshold;
	public static int kwrMaxRespawn;
	public static int kwrMinRespawn;
	public static int kwrWorldRespawnThreshold;
	public static int reputationXOffset;
	public static int reputationYOffset;
	public static int bannerWarningCooldown;
	public static int cloudRange;
	public static int customWaypointMinY;
	public static int brotherhoodMaxSize;
	public static int forceMapLocations;
	public static int gridScale;
	public static int dragonFireballCooldown;
	public static int mobSpawnInterval;
	public static int musicIntervalMax;
	public static int musicIntervalMenuMax;
	public static int musicIntervalMenuMin;
	public static int musicIntervalMin;
	public static int playerDataClearingInterval;
	public static int preventTraderKidnap;

	private static Configuration config;

	private GOTConfig() {
	}

	public static boolean areStrictFactionTitleRequirementsEnabled(World world) {
		if (!world.isRemote) {
			return strictFactionTitleRequirements;
		}
		return GOTLevelData.isClientSideThisServerStrictFactionTitleRequirements();
	}

	public static int getCustomWaypointMinY(World world) {
		if (!world.isRemote) {
			return customWaypointMinY;
		}
		return GOTLevelData.getClientSideThisServerCustomWaypointMinY();
	}

	public static int getBrotherhoodMaxSize(World world) {
		if (!world.isRemote) {
			return brotherhoodMaxSize;
		}
		return GOTLevelData.getClientSideThisServerBrotherhoodMaxSize();
	}

	public static boolean isEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingVanilla;
		}
		return GOTLevelData.isClientSideThisServerEnchanting();
	}

	public static boolean isBrotherhoodCreationEnabled(World world) {
		if (!world.isRemote) {
			return enableBrotherhoodCreation;
		}
		return GOTLevelData.isClientSideThisServerBrotherhoodCreation();
	}

	public static boolean isGOTEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingGOT;
		}
		return GOTLevelData.isClientSideThisServerEnchantingGOT();
	}

	public static void load() {
		languageCode = config.getString("languageCode", CATEGORY_LANGUAGE, languageCode, "Choose:" + GOT.LANGUAGES + '.');

		allowBannerProtection = config.get(CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean();
		allowBountyQuests = config.get(CATEGORY_GAMEPLAY, "NPCs give bounty mini-quests", true, "Allow NPCs to generate mini-quests to kill enemy players").getBoolean();
		allowMiniquests = config.get(CATEGORY_GAMEPLAY, "NPCs give mini-quests", true).getBoolean();
		allowSelfProtectingBanners = config.get(CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean();
		bannerWarningCooldown = config.get(CATEGORY_GAMEPLAY, "Protection Warning Cooldown", 20, "Cooldown time (in ticks) between appearances of the warning message for banner-public land").getInt();
		canAlwaysEat = config.get(CATEGORY_GAMEPLAY, "Feast Mode", true, "Food can always be eaten regardless of hunger").getBoolean();
		changedHunger = config.get(CATEGORY_GAMEPLAY, "Hunger changes", true, "Food meter decreases more slowly").getBoolean();
		customWaypointMinY = config.get(CATEGORY_GAMEPLAY, "Custom waypoint minimum y-level", -1, "Minimum y-coordinate at which a player can create a custom waypoint. Negative value = no limit").getInt();
		dragonFireballCooldown = config.get(CATEGORY_GAMEPLAY, "Dragon Fireball Cooldown", 3).getInt();
		dropMutton = config.get(CATEGORY_GAMEPLAY, "Mutton Drops", true, "Enable or disable sheep dropping the mod's mutton items").getBoolean();
		drunkMessages = config.get(CATEGORY_GAMEPLAY, "Enable Drunken Messages", true).getBoolean();
		enableConquest = config.get(CATEGORY_GAMEPLAY, "Enable Conquest", true).getBoolean();
		enableDothrakiSkirmish = config.get(CATEGORY_GAMEPLAY, "Enable Dothraki Skirmishes", true).getBoolean();
		enableFastTravel = config.get(CATEGORY_GAMEPLAY, "Enable Fast Travel", true).getBoolean();
		enableBrotherhoodCreation = config.get(CATEGORY_GAMEPLAY, "Enable Brotherhood creation", true, "If disabled, admins can still create Brotherhoods using the command").getBoolean();
		enableInvasions = config.get(CATEGORY_GAMEPLAY, "Enable Invasions", true).getBoolean();
		enableTitles = config.get(CATEGORY_GAMEPLAY, "Enable Titles", true).getBoolean();
		enableVillagerTrading = config.get(CATEGORY_GAMEPLAY, "Enable Villager trading", true, "Intended for servers. Enable or disable vanilla villager trading").getBoolean();
		enchantingAutoRemoveVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Auto-remove vanilla enchants", false, "Intended for servers. If enabled, enchantments will be automatically removed from items").getBoolean();
		enchantingGOT = config.get(CATEGORY_GAMEPLAY, "Enchanting: GOT System", true, "Enable the GOT enchanting system: if disabled, prevents newly crafted items, loot chest items, etc. from having modifiers applied, but does not affect existing modified items").getBoolean();
		enchantingVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Vanilla System", false, "Enable the vanilla enchanting system: if disabled, prevents players from enchanting items, but does not affect existing enchanted items").getBoolean();
		brotherhoodMaxSize = config.get(CATEGORY_GAMEPLAY, "Brotherhood maximum size", -1, "Maximum player count for Brotherhoods. Negative = no limit").getInt();
		forceMapLocations = config.get(CATEGORY_GAMEPLAY, "Force Hide/Show Map Locations", 0, "Force hide or show players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show").getInt();
		generateMapFeatures = config.get(CATEGORY_GAMEPLAY, "Generate map features", true).getBoolean();
		gridScale = config.get(CATEGORY_GAMEPLAY, "Grid of the world for generating villages", 12, "Smaller integer = greater chance of spawn, but the chance of intersection is growing too.").getInt();
		knownWorldRespawning = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Enable", true, "If enabled, when a player dies in Game of Thrones far from their spawn point, they will respawn somewhere near their death point instead").getBoolean();
		kwrBedRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Bed Threshold", 5000, "Threshold distance from spawn for applying Game of Thrones Respawning when the player's spawn point is a bed").getInt();
		kwrMaxRespawn = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Max Respawn Range", 1500, "Maximum possible range to place the player from their death point").getInt();
		kwrMinRespawn = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Min Respawn Range", 500, "Minimum possible range to place the player from their death point").getInt();
		kwrWorldRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: World Threshold", 2000, "Threshold distance from spawn for applying Game of Thrones respawning when the player's spawn point is the world spawn (no bed)").getInt();
		lgbt = config.get(CATEGORY_GAMEPLAY, "Enable LGBT marriages between NPC", false, "RENLY BARATHEON TRIGGERED").getBoolean();
		preventTraderKidnap = config.get(CATEGORY_GAMEPLAY, "Prevent trader transport range", 0, "Prevent transport of structure-bound traders beyond this distance outside their initial home range (0 = disabled)").getInt();
		removeDiamondArmorRecipes = config.get(CATEGORY_GAMEPLAY, "Remove diamond armour recipes", false).getBoolean();
		removeGoldenAppleRecipes = config.get(CATEGORY_GAMEPLAY, "Remove Golden Apple recipes", true).getBoolean();
		strictFactionTitleRequirements = config.get(CATEGORY_GAMEPLAY, "Strict faction title requirements", false, "Require a oath to bear faction titles of reputation level equal to the faction's oath level - not just those titles higher than oath level").getBoolean();
		walkerFireDamage = config.get(CATEGORY_GAMEPLAY, "Enable walkers fire damage", false).getBoolean();

		reputationXOffset = config.get(CATEGORY_GUI, "Reputation x-offset", 0, "Configure the x-position of the reputation bar on-screen. Negative values move it left, positive values right").getInt();
		reputationYOffset = config.get(CATEGORY_GUI, "Reputation y-offset", 0, "Configure the y-position of the reputation bar on-screen. Negative values move it up, positive values down").getInt();
		alwaysShowReputation = config.get(CATEGORY_GUI, "Always show reputation", false, "If set to false, the reputation bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean();
		bladeGlow = config.get(CATEGORY_GUI, "Animated sword glow", true).getBoolean();
		compassExtraInfo = config.get(CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean();
		customMainMenu = config.get(CATEGORY_GUI, "Custom main menu", true, "Use the mod's custom main menu screen").getBoolean();
		displayReputationAboveHead = config.get(CATEGORY_GUI, "Display reputation above head", true, "Enable or disable the rendering of other players' reputation values above their heads").getBoolean();
		displayCoinCounts = config.get(CATEGORY_GUI, "Inventory coin counts", true).getBoolean();
		enableOnscreenCompass = config.get(CATEGORY_GUI, "On-screen Compass", true).getBoolean();
		enableQuestTracker = config.get(CATEGORY_GUI, "Enable quest tracker", true).getBoolean();
		enableSepiaMap = config.get(CATEGORY_GUI, "Sepia Map", false, "Display the Game of Thrones map in sepia colours").getBoolean();
		fellowPlayerHealthBars = config.get(CATEGORY_GUI, "Fellow Player Health Bars", true).getBoolean();
		hiredUnitHealthBars = config.get(CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean();
		hiredUnitIcons = config.get(CATEGORY_GUI, "Hired NPC Icons", true).getBoolean();
		immersiveSpeech = config.get(CATEGORY_GUI, "Immersive Speech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box").getBoolean();
		immersiveSpeechChatLog = config.get(CATEGORY_GUI, "Immersive Speech Chat Logs", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled").getBoolean();
		mapLabels = config.get(CATEGORY_GUI, "Map Labels", true).getBoolean();
		mapLabelsConquest = config.get(CATEGORY_GUI, "Map Labels - Conquest", true).getBoolean();
		meleeAttackMeter = config.get(CATEGORY_GUI, "Melee attack meter", true).getBoolean();
		osrsMap = config.get(CATEGORY_GUI, "OSRS Map", false, "It's throwback time. (Requires game restart)").getBoolean();
		trackingQuestRight = config.get(CATEGORY_GUI, "Flip quest tracker", false, "Display the quest tracker on the right-hand side of the screen instead of the left").getBoolean();

		cloudRange = config.get(CATEGORY_ENVIRONMENT, "Cloud range", 1024, "Game of Thrones cloud rendering range. To use vanilla clouds, set this to a non-positive value").getInt();
		enableAmbience = config.get(CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean();
		enableFrostfangsMist = config.get(CATEGORY_ENVIRONMENT, "Foggy Frostfangs", true, "Toggle mist overlay in the Frostfangs").getBoolean();
		enableGOTSky = config.get(CATEGORY_ENVIRONMENT, "Game of Thrones sky", true, "Toggle the new Game of Thrones sky").getBoolean();
		enableSunFlare = config.get(CATEGORY_ENVIRONMENT, "Sun flare", true).getBoolean();

		checkUpdates = config.get(CATEGORY_MISC, "Check for updates", true, "Disable this if you will be playing offline").getBoolean();
		cwpLog = config.get(CATEGORY_MISC, "Custom Waypoint logging", false).getBoolean();
		displayMusicTrack = config.get(CATEGORY_MISC, "Display music track", false, "Display the name of a GOT music track when it begins playing").getBoolean();
		fixRenderDistance = config.get(CATEGORY_MISC, "Fix render distance", true, "Fix a vanilla crash caused by having render distance > 16 in the options.txt. NOTE: This will not run if Optifine is installed").getBoolean();
		mobSpawnInterval = config.get(CATEGORY_MISC, "Mob spawn interval", 0, "Tick interval between mob spawn cycles (which are then run multiple times to compensate). Higher values may reduce server lag").getInt();
		musicIntervalMax = config.get(CATEGORY_MISC, "Music Interval: Max.", 150, "Maximum time (seconds) between GOT music tracks").getInt();
		musicIntervalMenuMax = config.get(CATEGORY_MISC, "Menu Music Interval: Max.", 20, "Maximum time (seconds) between GOT menu music tracks").getInt();
		musicIntervalMenuMin = config.get(CATEGORY_MISC, "Menu Music Interval: Min.", 10, "Minimum time (seconds) between GOT menu music tracks").getInt();
		musicIntervalMin = config.get(CATEGORY_MISC, "Music Interval: Min.", 30, "Minimum time (seconds) between GOT music tracks").getInt();
		playerDataClearingInterval = config.get(CATEGORY_MISC, "Playerdata clearing interval", 600, "Tick interval between clearing offline LOTR-playerdata from the cache. Offline players' data is typically loaded to serve features like brotherhoods and their shared custom waypoints. Higher values may reduce server lag, as data will have to be reloaded from disk less often, but will result in higher RAM usage to some extent").getInt();
		preventMessageExploit = config.get(CATEGORY_MISC, "Fix /msg exploit", true, "Disable usage of @a, @r, etc. in the /msg command, to prevent exploiting it as a player locator").getBoolean();

		if (GOTModChecker.isCauldronServer()) {
			FMLLog.info("Hummel009: Successfully detected Cauldron server");
		}
		if (config.hasChanged()) {
			config.save();
		}
	}

	public static void preInit() {
		config = new Configuration(new File("config", "GOT.cfg"));
		load();
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