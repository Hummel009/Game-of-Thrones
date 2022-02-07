package got.common;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.util.GOTModChecker;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class GOTConfig {
	private static Configuration config;
	private static List<String> allCategories;
	private static String CATEGORY_LANGUAGE;
	private static String CATEGORY_GAMEPLAY;
	private static String CATEGORY_GUI;
	private static String CATEGORY_ENVIRONMENT;
	private static String CATEGORY_MISC;
	private static boolean allowBannerProtection;
	private static boolean allowSelfProtectingBanners;
	private static boolean allowMiniquests;
	private static boolean allowBountyQuests;
	private static boolean enableFastTravel;
	private static boolean enableConquest;
	private static boolean removeGoldenAppleRecipes;
	private static boolean enablePortals;
	private static boolean enableDothrakiSkirmish;
	private static boolean enchantingVanilla;
	private static boolean enchantingGOT;
	private static boolean clearMap;
	private static boolean enchantingAutoRemoveVanilla;
	private static int bannerWarningCooldown;
	private static boolean dropMutton;
	private static boolean drunkMessages;
	private static boolean GOTRespawning;
	private static int GOTBedRespawnThreshold;
	private static int GOTWorldRespawnThreshold;
	private static int GOTMinRespawn;
	private static int GOTMaxRespawn;
	private static boolean generateMapFeatures;
	private static boolean changedHunger;
	private static boolean canAlwaysEat;
	private static int forceMapLocations;
	private static boolean enableInvasions;
	private static boolean removeDiamondArmorRecipes;
	private static int preventTraderKidnap;
	private static boolean alwaysShowAlignment;
	private static int alignmentXOffset;
	private static int alignmentYOffset;
	private static boolean displayAlignmentAboveHead;
	private static boolean enableSepiaMap;
	private static boolean osrsMap;
	private static boolean enableOnscreenCompass;
	private static boolean compassExtraInfo;
	private static boolean hiredUnitHealthBars;
	private static boolean hiredUnitIcons;
	private static boolean bladeGlow;
	private static boolean immersiveSpeech;
	private static boolean immersiveSpeechChatLog;
	private static boolean meleeAttackMeter;
	private static boolean mapLabels;
	private static boolean mapLabelsConquest;
	private static boolean enableQuestTracker;
	private static boolean trackingQuestRight;
	private static boolean customMainMenu;
	private static boolean fellowPlayerHealthBars;
	private static boolean displayCoinCounts;
	private static boolean enableGOTSky;
	private static boolean enableFrostfangsMist;
	private static boolean enableAmbience;
	private static boolean enableSunFlare;
	private static int cloudRange;
	private static boolean checkUpdates;
	private static int mobSpawnInterval;
	private static int musicIntervalMin;
	private static int musicIntervalMax;
	private static boolean displayMusicTrack;
	private static int musicIntervalMenuMin;
	private static int musicIntervalMenuMax;
	private static boolean fixRenderDistance;
	private static boolean preventMessageExploit;
	private static boolean cwpLog;
	private static String languageCode = "ru";

	static {
		allCategories = new ArrayList<>();
		CATEGORY_LANGUAGE = GOTConfig.getCategory("1_language");
		CATEGORY_GAMEPLAY = GOTConfig.getCategory("2_gameplay");
		CATEGORY_GUI = GOTConfig.getCategory("3_gui");
		CATEGORY_ENVIRONMENT = GOTConfig.getCategory("4_environment");
		CATEGORY_MISC = GOTConfig.getCategory("5_misc");
	}

	public static int getAlignmentXOffset() {
		return alignmentXOffset;
	}

	public static int getAlignmentYOffset() {
		return alignmentYOffset;
	}

	public static int getBannerWarningCooldown() {
		return bannerWarningCooldown;
	}

	private static String getCategory(String category) {
		allCategories.add(category);
		return category;
	}

	public static int getCloudRange() {
		return cloudRange;
	}

	public static int getForceMapLocations() {
		return forceMapLocations;
	}

	public static int getGOTBedRespawnThreshold() {
		return GOTBedRespawnThreshold;
	}

	public static int getGOTMaxRespawn() {
		return GOTMaxRespawn;
	}

	public static int getGOTMinRespawn() {
		return GOTMinRespawn;
	}

	public static int getGOTWorldRespawnThreshold() {
		return GOTWorldRespawnThreshold;
	}

	public static String getLanguageCode() {
		return languageCode;
	}

	public static int getMobSpawnInterval() {
		return mobSpawnInterval;
	}

	public static int getMusicIntervalMax() {
		return musicIntervalMax;
	}

	public static int getMusicIntervalMenuMax() {
		return musicIntervalMenuMax;
	}

	public static int getMusicIntervalMenuMin() {
		return musicIntervalMenuMin;
	}

	public static int getMusicIntervalMin() {
		return musicIntervalMin;
	}

	public static int getPreventTraderKidnap() {
		return preventTraderKidnap;
	}

	public static boolean isAllowBannerProtection() {
		return allowBannerProtection;
	}

	public static boolean isAllowBountyQuests() {
		return allowBountyQuests;
	}

	public static boolean isAllowMiniquests() {
		return allowMiniquests;
	}

	public static boolean isAllowSelfProtectingBanners() {
		return allowSelfProtectingBanners;
	}

	public static boolean isAlwaysShowAlignment() {
		return alwaysShowAlignment;
	}

	public static boolean isBladeGlow() {
		return bladeGlow;
	}

	public static boolean isCanAlwaysEat() {
		return canAlwaysEat;
	}

	public static boolean isChangedHunger() {
		return changedHunger;
	}

	public static boolean isCheckUpdates() {
		return checkUpdates;
	}

	public static boolean isClearMap() {
		return clearMap;
	}

	public static boolean isCompassExtraInfo() {
		return compassExtraInfo;
	}

	public static boolean isCustomMainMenu() {
		return customMainMenu;
	}

	public static boolean isCwpLog() {
		return cwpLog;
	}

	public static boolean isDisplayAlignmentAboveHead() {
		return displayAlignmentAboveHead;
	}

	public static boolean isDisplayCoinCounts() {
		return displayCoinCounts;
	}

	public static boolean isDisplayMusicTrack() {
		return displayMusicTrack;
	}

	public static boolean isDropMutton() {
		return dropMutton;
	}

	public static boolean isDrunkMessages() {
		return drunkMessages;
	}

	public static boolean isEnableAmbience() {
		return enableAmbience;
	}

	public static boolean isEnableConquest() {
		return enableConquest;
	}

	public static boolean isEnableDothrakiSkirmish() {
		return enableDothrakiSkirmish;
	}

	public static boolean isEnableFastTravel() {
		return enableFastTravel;
	}

	public static boolean isEnableFrostfangsMist() {
		return enableFrostfangsMist;
	}

	public static boolean isEnableGOTSky() {
		return enableGOTSky;
	}

	public static boolean isEnableInvasions() {
		return enableInvasions;
	}

	public static boolean isEnableOnscreenCompass() {
		return enableOnscreenCompass;
	}

	public static boolean isEnablePortals() {
		return enablePortals;
	}

	public static boolean isEnableQuestTracker() {
		return enableQuestTracker;
	}

	public static boolean isEnableSepiaMap() {
		return enableSepiaMap;
	}

	public static boolean isEnableSunFlare() {
		return enableSunFlare;
	}

	public static boolean isEnchantingAutoRemoveVanilla() {
		return enchantingAutoRemoveVanilla;
	}

	public static boolean isEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return isEnchantingVanilla();
		}
		return GOTLevelData.isClientsideThisServerEnchanting();
	}

	public static boolean isEnchantingGOT() {
		return enchantingGOT;
	}

	public static boolean isEnchantingVanilla() {
		return enchantingVanilla;
	}

	public static boolean isFellowPlayerHealthBars() {
		return fellowPlayerHealthBars;
	}

	public static boolean isFixRenderDistance() {
		return fixRenderDistance;
	}

	public static boolean isGenerateMapFeatures() {
		return generateMapFeatures;
	}

	public static boolean isGOTEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return isEnchantingGOT();
		}
		return GOTLevelData.isClientsideThisServerEnchantingGOT();
	}

	public static boolean isGOTRespawning() {
		return GOTRespawning;
	}

	public static boolean isHiredUnitHealthBars() {
		return hiredUnitHealthBars;
	}

	public static boolean isHiredUnitIcons() {
		return hiredUnitIcons;
	}

	public static boolean isImmersiveSpeech() {
		return immersiveSpeech;
	}

	public static boolean isImmersiveSpeechChatLog() {
		return immersiveSpeechChatLog;
	}

	public static boolean isMapLabels() {
		return mapLabels;
	}

	public static boolean isMapLabelsConquest() {
		return mapLabelsConquest;
	}

	public static boolean isMeleeAttackMeter() {
		return meleeAttackMeter;
	}

	public static boolean isOsrsMap() {
		return osrsMap;
	}

	public static boolean isPreventMessageExploit() {
		return preventMessageExploit;
	}

	public static boolean isRemoveDiamondArmorRecipes() {
		return removeDiamondArmorRecipes;
	}

	public static boolean isRemoveGoldenAppleRecipes() {
		return removeGoldenAppleRecipes;
	}

	public static boolean isTrackingQuestRight() {
		return trackingQuestRight;
	}

	public static void load() {
		setLanguageCode(config.getString("languageCode", CATEGORY_LANGUAGE, getLanguageCode(), "Choose:" + GOT.getLangs() + "."));
		setClearMap(config.get(CATEGORY_GAMEPLAY, "No fixed structures and characters", false, "Useful for servers. Disable fixed structures to build your own").getBoolean());
		setAllowBannerProtection(config.get(CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean());
		setAllowSelfProtectingBanners(config.get(CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean());
		setAllowMiniquests(config.get(CATEGORY_GAMEPLAY, "NPCs give mini-quests", true).getBoolean());
		setAllowBountyQuests(config.get(CATEGORY_GAMEPLAY, "NPCs give bounty mini-quests", true, "Allow NPCs to generate mini-quests to kill enemy players").getBoolean());
		setEnableFastTravel(config.get(CATEGORY_GAMEPLAY, "Enable Fast Travel", true).getBoolean());
		setEnableConquest(config.get(CATEGORY_GAMEPLAY, "Enable Conquest", true).getBoolean());
		setRemoveGoldenAppleRecipes(config.get(CATEGORY_GAMEPLAY, "Remove Golden Apple recipes", true).getBoolean());
		setEnablePortals(config.get(CATEGORY_GAMEPLAY, "Enable Game of Thrones Portals", true, "Enable or disable the buildable Game of Thrones portals (excluding the Circle Portal). If disabled, portals can still be made, but will not function").getBoolean());
		setEnableDothrakiSkirmish(config.get(CATEGORY_GAMEPLAY, "Enable Dothraki Skirmishes", true).getBoolean());
		setEnchantingVanilla(config.get(CATEGORY_GAMEPLAY, "Enchanting: Vanilla System", false, "Enable the vanilla enchanting system: if disabled, prevents players from enchanting items, but does not affect existing enchanted items").getBoolean());
		setEnchantingGOT(config.get(CATEGORY_GAMEPLAY, "Enchanting: GOT System", true, "Enable the GOT enchanting system: if disabled, prevents newly crafted items, loot chest items, etc. from having modifiers applied, but does not affect existing modified items").getBoolean());
		setEnchantingAutoRemoveVanilla(config.get(CATEGORY_GAMEPLAY, "Enchanting: Auto-remove vanilla enchants", false, "Intended for servers. If enabled, enchantments will be automatically removed from items").getBoolean());
		setBannerWarningCooldown(config.get(CATEGORY_GAMEPLAY, "Protection Warning Cooldown", 20, "Cooldown time (in ticks) between appearances of the warning message for banner-private land").getInt());
		setDropMutton(config.get(CATEGORY_GAMEPLAY, "Mutton Drops", true, "Enable or disable sheep dropping the mod's mutton items").getBoolean());
		setDrunkMessages(config.get(CATEGORY_GAMEPLAY, "Enable Drunken Messages", true).getBoolean());
		setGOTRespawning(config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Enable", true, "If enabled, when a player dies in Game of Thrones far from their spawn point, they will respawn somewhere near their death point instead").getBoolean());
		setGOTBedRespawnThreshold(config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Bed Threshold", 5000, "Threshold distance from spawn for applying Game of Thrones Respawning when the player's spawn point is a bed").getInt());
		setGOTWorldRespawnThreshold(config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: World Threshold", 2000, "Threshold distance from spawn for applying Game of Thrones respawning when the player's spawn point is the world spawn (no bed)").getInt());
		setGOTMinRespawn(config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Min Respawn Range", 500, "Minimum possible range to place the player from their death point").getInt());
		setGOTMaxRespawn(config.get(CATEGORY_GAMEPLAY, "Game of Thrones Respawning: Max Respawn Range", 1500, "Maximum possible range to place the player from their death point").getInt());
		setGenerateMapFeatures(config.get(CATEGORY_GAMEPLAY, "Generate map features", true).getBoolean());
		setChangedHunger(config.get(CATEGORY_GAMEPLAY, "Hunger changes", true, "Food meter decreases more slowly").getBoolean());
		setCanAlwaysEat(config.get(CATEGORY_GAMEPLAY, "Feast Mode", true, "Food can always be eaten regardless of hunger").getBoolean());
		setForceMapLocations(config.get(CATEGORY_GAMEPLAY, "Force Hide/Show Map Locations", 0, "Force hide or show players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show").getInt());
		setEnableInvasions(config.get(CATEGORY_GAMEPLAY, "Enable Invasions", true).getBoolean());
		setRemoveDiamondArmorRecipes(config.get(CATEGORY_GAMEPLAY, "Remove diamond armour recipes", false).getBoolean());
		setPreventTraderKidnap(config.get(CATEGORY_GAMEPLAY, "Prevent trader transport range", 0, "Prevent transport of structure-bound traders beyond this distance outside their initial home range (0 = disabled)").getInt());
		setAlwaysShowAlignment(config.get(CATEGORY_GUI, "Always show alignment", false, "If set to false, the alignment bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean());
		setAlignmentXOffset(config.get(CATEGORY_GUI, "Alignment x-offset", 0, "Configure the x-position of the alignment bar on-screen. Negative values move it left, positive values right").getInt());
		setAlignmentYOffset(config.get(CATEGORY_GUI, "Alignment y-offset", 0, "Configure the y-position of the alignment bar on-screen. Negative values move it up, positive values down").getInt());
		setDisplayAlignmentAboveHead(config.get(CATEGORY_GUI, "Display alignment above head", true, "Enable or disable the rendering of other players' alignment values above their heads").getBoolean());
		setEnableSepiaMap(config.get(CATEGORY_GUI, "Sepia Map", false, "Display the Game of Thrones map in sepia colours").getBoolean());
		setOsrsMap(config.get(CATEGORY_GUI, "OSRS Map", false, "It's throwback time. (Requires game restart)").getBoolean());
		setEnableOnscreenCompass(config.get(CATEGORY_GUI, "On-screen Compass", true).getBoolean());
		setCompassExtraInfo(config.get(CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean());
		setHiredUnitHealthBars(config.get(CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean());
		setHiredUnitIcons(config.get(CATEGORY_GUI, "Hired NPC Icons", true).getBoolean());
		setBladeGlow(config.get(CATEGORY_GUI, "Animated sword glow", true).getBoolean());
		setImmersiveSpeech(config.get(CATEGORY_GUI, "Immersive Speech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box").getBoolean());
		setImmersiveSpeechChatLog(config.get(CATEGORY_GUI, "Immersive Speech Chat Logs", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled").getBoolean());
		setMeleeAttackMeter(config.get(CATEGORY_GUI, "Melee attack meter", true).getBoolean());
		setMapLabels(config.get(CATEGORY_GUI, "Map Labels", true).getBoolean());
		setMapLabelsConquest(config.get(CATEGORY_GUI, "Map Labels - Conquest", true).getBoolean());
		setEnableQuestTracker(config.get(CATEGORY_GUI, "Enable quest tracker", true).getBoolean());
		setTrackingQuestRight(config.get(CATEGORY_GUI, "Flip quest tracker", false, "Display the quest tracker on the right-hand side of the screen instead of the left").getBoolean());
		setCustomMainMenu(config.get(CATEGORY_GUI, "Custom main menu", true, "Use the mod's custom main menu screen").getBoolean());
		setFellowPlayerHealthBars(config.get(CATEGORY_GUI, "Fellow Player Health Bars", true).getBoolean());
		setDisplayCoinCounts(config.get(CATEGORY_GUI, "Inventory coin counts", true).getBoolean());
		setEnableGOTSky(config.get(CATEGORY_ENVIRONMENT, "Game of Thrones sky", true, "Toggle the new Game of Thrones sky").getBoolean());
		setEnableFrostfangsMist(config.get(CATEGORY_ENVIRONMENT, "Foggy Frostfangs", true, "Toggle mist overlay in the Frostfangs").getBoolean());
		setEnableAmbience(config.get(CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean());
		setEnableSunFlare(config.get(CATEGORY_ENVIRONMENT, "Sun flare", true).getBoolean());
		setCloudRange(config.get(CATEGORY_ENVIRONMENT, "Cloud range", 1024, "Game of Thrones cloud rendering range. To use vanilla clouds, set this to a non-positive value").getInt());
		setCheckUpdates(config.get(CATEGORY_MISC, "Check for updates", true, "Disable this if you will be playing offline").getBoolean());
		config.get(CATEGORY_MISC, "Fix mob spawning lag", true, "Fix a major source of server lag caused by the vanilla mob spawning system").getBoolean();
		setMobSpawnInterval(config.get(CATEGORY_MISC, "Mob spawn interval", 0, "Tick interval between mob spawn cycles (which are then run multiple times to compensate). Higher values may reduce server lag").getInt());
		setMusicIntervalMin(config.get(CATEGORY_MISC, "Music Interval: Min.", 30, "Minimum time (seconds) between GOT music tracks").getInt());
		setMusicIntervalMax(config.get(CATEGORY_MISC, "Music Interval: Max.", 150, "Maximum time (seconds) between GOT music tracks").getInt());
		setDisplayMusicTrack(config.get(CATEGORY_MISC, "Display music track", false, "Display the name of a GOT music track when it begins playing").getBoolean());
		setMusicIntervalMenuMin(config.get(CATEGORY_MISC, "Menu Music Interval: Min.", 10, "Minimum time (seconds) between GOT menu music tracks").getInt());
		setMusicIntervalMenuMax(config.get(CATEGORY_MISC, "Menu Music Interval: Max.", 20, "Maximum time (seconds) between GOT menu music tracks").getInt());
		setFixRenderDistance(config.get(CATEGORY_MISC, "Fix render distance", true, "Fix a vanilla crash caused by having render distance > 16 in the options.txt. NOTE: This will not run if Optifine is installed").getBoolean());
		setPreventMessageExploit(config.get(CATEGORY_MISC, "Fix /msg exploit", true, "Disable usage of @a, @r, etc. in the /msg command, to prevent exploiting it as a player locator").getBoolean());
		setCwpLog(config.get(CATEGORY_MISC, "Custom Waypoint logging", false).getBoolean());

		if (GOTModChecker.isCauldronServer()) {
			FMLLog.info("Hummel009: Successfully detected Cauldron server");
		}
		if (config.hasChanged()) {
			config.save();
		}
	}

	public static void preInit() {
		config = new Configuration(new File("config", "GOT.cfg"));
		GOTConfig.load();
	}

	public static void setAlignmentXOffset(int alignmentXOffset) {
		GOTConfig.alignmentXOffset = alignmentXOffset;
	}

	public static void setAlignmentYOffset(int alignmentYOffset) {
		GOTConfig.alignmentYOffset = alignmentYOffset;
	}

	public static void setAllowBannerProtection(boolean allowBannerProtection) {
		GOTConfig.allowBannerProtection = allowBannerProtection;
	}

	public static void setAllowBountyQuests(boolean allowBountyQuests) {
		GOTConfig.allowBountyQuests = allowBountyQuests;
	}

	public static void setAllowMiniquests(boolean allowMiniquests) {
		GOTConfig.allowMiniquests = allowMiniquests;
	}

	public static void setAllowSelfProtectingBanners(boolean allowSelfProtectingBanners) {
		GOTConfig.allowSelfProtectingBanners = allowSelfProtectingBanners;
	}

	public static void setAlwaysShowAlignment(boolean alwaysShowAlignment) {
		GOTConfig.alwaysShowAlignment = alwaysShowAlignment;
	}

	public static void setBannerWarningCooldown(int bannerWarningCooldown) {
		GOTConfig.bannerWarningCooldown = bannerWarningCooldown;
	}

	public static void setBladeGlow(boolean bladeGlow) {
		GOTConfig.bladeGlow = bladeGlow;
	}

	public static void setCanAlwaysEat(boolean canAlwaysEat) {
		GOTConfig.canAlwaysEat = canAlwaysEat;
	}

	public static void setChangedHunger(boolean changedHunger) {
		GOTConfig.changedHunger = changedHunger;
	}

	public static void setCheckUpdates(boolean checkUpdates) {
		GOTConfig.checkUpdates = checkUpdates;
	}

	public static void setClearMap(boolean clearMap) {
		GOTConfig.clearMap = clearMap;
	}

	public static void setCloudRange(int cloudRange) {
		GOTConfig.cloudRange = cloudRange;
	}

	public static void setCompassExtraInfo(boolean compassExtraInfo) {
		GOTConfig.compassExtraInfo = compassExtraInfo;
	}

	public static void setCustomMainMenu(boolean customMainMenu) {
		GOTConfig.customMainMenu = customMainMenu;
	}

	public static void setCwpLog(boolean cwpLog) {
		GOTConfig.cwpLog = cwpLog;
	}

	public static void setDisplayAlignmentAboveHead(boolean displayAlignmentAboveHead) {
		GOTConfig.displayAlignmentAboveHead = displayAlignmentAboveHead;
	}

	public static void setDisplayCoinCounts(boolean displayCoinCounts) {
		GOTConfig.displayCoinCounts = displayCoinCounts;
	}

	public static void setDisplayMusicTrack(boolean displayMusicTrack) {
		GOTConfig.displayMusicTrack = displayMusicTrack;
	}

	public static void setDropMutton(boolean dropMutton) {
		GOTConfig.dropMutton = dropMutton;
	}

	public static void setDrunkMessages(boolean drunkMessages) {
		GOTConfig.drunkMessages = drunkMessages;
	}

	public static void setEnableAmbience(boolean enableAmbience) {
		GOTConfig.enableAmbience = enableAmbience;
	}

	public static void setEnableConquest(boolean enableConquest) {
		GOTConfig.enableConquest = enableConquest;
	}

	public static void setEnableDothrakiSkirmish(boolean enableDothrakiSkirmish) {
		GOTConfig.enableDothrakiSkirmish = enableDothrakiSkirmish;
	}

	public static void setEnableFastTravel(boolean enableFastTravel) {
		GOTConfig.enableFastTravel = enableFastTravel;
	}

	public static void setEnableFrostfangsMist(boolean enableFrostfangsMist) {
		GOTConfig.enableFrostfangsMist = enableFrostfangsMist;
	}

	public static void setEnableGOTSky(boolean enableGOTSky) {
		GOTConfig.enableGOTSky = enableGOTSky;
	}

	public static void setEnableInvasions(boolean enableInvasions) {
		GOTConfig.enableInvasions = enableInvasions;
	}

	public static void setEnableOnscreenCompass(boolean enableOnscreenCompass) {
		GOTConfig.enableOnscreenCompass = enableOnscreenCompass;
	}

	public static void setEnablePortals(boolean enablePortals) {
		GOTConfig.enablePortals = enablePortals;
	}

	public static void setEnableQuestTracker(boolean enableQuestTracker) {
		GOTConfig.enableQuestTracker = enableQuestTracker;
	}

	public static void setEnableSepiaMap(boolean enableSepiaMap) {
		GOTConfig.enableSepiaMap = enableSepiaMap;
	}

	public static void setEnableSunFlare(boolean enableSunFlare) {
		GOTConfig.enableSunFlare = enableSunFlare;
	}

	public static void setEnchantingAutoRemoveVanilla(boolean enchantingAutoRemoveVanilla) {
		GOTConfig.enchantingAutoRemoveVanilla = enchantingAutoRemoveVanilla;
	}

	public static void setEnchantingGOT(boolean enchantingGOT) {
		GOTConfig.enchantingGOT = enchantingGOT;
	}

	public static void setEnchantingVanilla(boolean enchantingVanilla) {
		GOTConfig.enchantingVanilla = enchantingVanilla;
	}

	public static void setFellowPlayerHealthBars(boolean fellowPlayerHealthBars) {
		GOTConfig.fellowPlayerHealthBars = fellowPlayerHealthBars;
	}

	public static void setFixRenderDistance(boolean fixRenderDistance) {
		GOTConfig.fixRenderDistance = fixRenderDistance;
	}

	public static void setForceMapLocations(int forceMapLocations) {
		GOTConfig.forceMapLocations = forceMapLocations;
	}

	public static void setGenerateMapFeatures(boolean generateMapFeatures) {
		GOTConfig.generateMapFeatures = generateMapFeatures;
	}

	public static void setGOTBedRespawnThreshold(int gOTBedRespawnThreshold) {
		GOTBedRespawnThreshold = gOTBedRespawnThreshold;
	}

	public static void setGOTMaxRespawn(int gOTMaxRespawn) {
		GOTMaxRespawn = gOTMaxRespawn;
	}

	public static void setGOTMinRespawn(int gOTMinRespawn) {
		GOTMinRespawn = gOTMinRespawn;
	}

	public static void setGOTRespawning(boolean gOTRespawning) {
		GOTRespawning = gOTRespawning;
	}

	public static void setGOTWorldRespawnThreshold(int gOTWorldRespawnThreshold) {
		GOTWorldRespawnThreshold = gOTWorldRespawnThreshold;
	}

	public static void setHiredUnitHealthBars(boolean hiredUnitHealthBars) {
		GOTConfig.hiredUnitHealthBars = hiredUnitHealthBars;
	}

	public static void setHiredUnitIcons(boolean hiredUnitIcons) {
		GOTConfig.hiredUnitIcons = hiredUnitIcons;
	}

	public static void setImmersiveSpeech(boolean immersiveSpeech) {
		GOTConfig.immersiveSpeech = immersiveSpeech;
	}

	public static void setImmersiveSpeechChatLog(boolean immersiveSpeechChatLog) {
		GOTConfig.immersiveSpeechChatLog = immersiveSpeechChatLog;
	}

	public static void setLanguageCode(String languageCode) {
		GOTConfig.languageCode = languageCode;
	}

	public static void setMapLabels(boolean mapLabels) {
		GOTConfig.mapLabels = mapLabels;
	}

	public static void setMapLabelsConquest(boolean mapLabelsConquest) {
		GOTConfig.mapLabelsConquest = mapLabelsConquest;
	}

	public static void setMeleeAttackMeter(boolean meleeAttackMeter) {
		GOTConfig.meleeAttackMeter = meleeAttackMeter;
	}

	public static void setMobSpawnInterval(int mobSpawnInterval) {
		GOTConfig.mobSpawnInterval = mobSpawnInterval;
	}

	public static void setMusicIntervalMax(int musicIntervalMax) {
		GOTConfig.musicIntervalMax = musicIntervalMax;
	}

	public static void setMusicIntervalMenuMax(int musicIntervalMenuMax) {
		GOTConfig.musicIntervalMenuMax = musicIntervalMenuMax;
	}

	public static void setMusicIntervalMenuMin(int musicIntervalMenuMin) {
		GOTConfig.musicIntervalMenuMin = musicIntervalMenuMin;
	}

	public static void setMusicIntervalMin(int musicIntervalMin) {
		GOTConfig.musicIntervalMin = musicIntervalMin;
	}

	public static void setOsrsMap(boolean osrsMap) {
		GOTConfig.osrsMap = osrsMap;
	}

	public static void setPreventMessageExploit(boolean preventMessageExploit) {
		GOTConfig.preventMessageExploit = preventMessageExploit;
	}

	public static void setPreventTraderKidnap(int preventTraderKidnap) {
		GOTConfig.preventTraderKidnap = preventTraderKidnap;
	}

	public static void setRemoveDiamondArmorRecipes(boolean removeDiamondArmorRecipes) {
		GOTConfig.removeDiamondArmorRecipes = removeDiamondArmorRecipes;
	}

	public static void setRemoveGoldenAppleRecipes(boolean removeGoldenAppleRecipes) {
		GOTConfig.removeGoldenAppleRecipes = removeGoldenAppleRecipes;
	}

	public static void setStructureTimelapse(boolean flag) {
		boolean strTimelapse = flag;
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse").set(strTimelapse);
		config.save();
	}

	public static void setStructureTimelapseInterval(int i) {
		int strTimelapseInterval = i;
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse Interval").set(strTimelapseInterval);
		config.save();
	}

	public static void setTrackingQuestRight(boolean trackingQuestRight) {
		GOTConfig.trackingQuestRight = trackingQuestRight;
	}

	public static void toggleMapLabels() {
		setMapLabels(!isMapLabels());
		config.getCategory(CATEGORY_GUI).get("Map Labels").set(isMapLabels());
		config.save();
	}

	public static void toggleMapLabelsConquest() {
		setMapLabelsConquest(!isMapLabelsConquest());
		config.getCategory(CATEGORY_GUI).get("Map Labels - Conquest").set(isMapLabelsConquest());
		config.save();
	}

	public static void toggleSepia() {
		setEnableSepiaMap(!isEnableSepiaMap());
		config.getCategory(CATEGORY_GUI).get("Sepia Map").set(isEnableSepiaMap());
		config.save();
	}
}
