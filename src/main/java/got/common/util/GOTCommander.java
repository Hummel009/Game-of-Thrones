package got.common.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import got.client.GOTTextures;
import got.client.gui.GOTGuiMainMenu;
import got.common.GOTDimension;
import got.common.GOTDimension.DimensionRegion;
import got.common.database.*;
import got.common.database.GOTCapes.CapeType;
import got.common.database.GOTShields.ShieldType;
import got.common.faction.*;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.quest.*;
import got.common.world.biome.*;
import got.common.world.feature.GOTTreeType;
import got.common.world.genlayer.GOTGenLayerWorld;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.other.GOTStructureScan;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class GOTCommander {

	public static GOTCapes addAlignmentCape(String enumName, GOTFaction faction) {
		Class<?>[] classArr = new Class[] { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	public static GOTShields addAlignmentShield(String enumName, GOTFaction faction) {
		Class<?>[] classArr = new Class[] { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	public static BannerType addBanner(String enumName, int id, String bannerName, GOTFaction faction) {
		Class[] classArr = { Integer.TYPE, String.class, GOTFaction.class };
		Object[] args = { id, bannerName, faction };
		BannerType banner = EnumHelper.addEnum(BannerType.class, enumName, classArr, args);
		BannerType.bannerTypes.add(banner);
		HashMap bannerForIDMap = (HashMap) ReflectionHelper.getPrivateValue(BannerType.class, null, "bannerForID");
		bannerForIDMap.put(banner.bannerID, banner);
		ReflectionHelper.setPrivateValue(BannerType.class, null, bannerForIDMap, "bannerForID");
		return banner;
	}

	public static GOTCapes addCape(String enumName, boolean hidden, String... players) {
		return GOTCommander.addCape(enumName, CapeType.EXCLUSIVE, hidden, players);
	}

	public static GOTCapes addCape(String enumName, CapeType type, boolean hidden, String... players) {
		Class<?>[] classArr = new Class[] { CapeType.class, boolean.class, String[].class };
		Object[] args = { type, Boolean.valueOf(hidden), players };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	public static void addDefaultOres(GOTBiomeDecorator decorator) {
		GOTCommander.findAndInvokeMethod(GOTBiomeDecorator.class, decorator, "addDefaultOres");
	}

	public static DimensionRegion addDimensionRegion(String enumName, String regionName) {
		Class<?>[] classArr = new Class[] { String.class };
		Object[] args = { regionName };
		return EnumHelper.addEnum(DimensionRegion.class, enumName, classArr, args);
	}

	public static GOTFaction addFaction(String enumName, int color, DimensionRegion region) {
		return GOTCommander.addFaction(enumName, color, GOTDimension.GAME_OF_THRONES, region, true, true, -2147483648, null);
	}

	public static GOTFaction addFaction(String enumName, int color, GOTDimension dim, DimensionRegion region, boolean player, boolean registry, int alignment, GOTMapRegion mapInfo) {
		Class<?>[] classArr = new Class[] { int.class, GOTDimension.class, DimensionRegion.class, boolean.class, boolean.class, int.class, GOTMapRegion.class };
		Object[] args = { Integer.valueOf(color), dim, region, Boolean.valueOf(player), Boolean.valueOf(registry), Integer.valueOf(alignment), mapInfo };
		return EnumHelper.addEnum(GOTFaction.class, enumName, classArr, args);
	}

	public static GOTInvasions addInvasion(String enumName, GOTFaction faction) {
		return GOTCommander.addInvasion(enumName, faction, null);
	}

	public static GOTInvasions addInvasion(String enumName, GOTFaction faction, String subfaction) {
		Class<?>[] classArr = new Class[] { GOTFaction.class, String.class };
		Object[] args = { faction, subfaction };
		return EnumHelper.addEnum(GOTInvasions.class, enumName, classArr, args);
	}

	public static GOTLabels addMapLabel(String enumName, GOTBiome biomeLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return GOTCommander.addMapLabel(enumName, biomeLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	public static GOTLabels addMapLabel(String enumName, Object label, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		Class<?>[] classArr = new Class[] { Object.class, int.class, int.class, float.class, int.class, float.class, float.class };
		Object[] args = { label, Integer.valueOf(x), Integer.valueOf(y), Float.valueOf(scale), Integer.valueOf(angle), Float.valueOf(zoomMin), Float.valueOf(zoomMan) };
		return EnumHelper.addEnum(GOTLabels.class, enumName, classArr, args);
	}

	public static GOTLabels addMapLabel(String enumName, String stringLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return GOTCommander.addMapLabel(enumName, stringLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	public static void addMiniQuest(GOTMiniQuestFactory factory, GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest> questFactory) {
		GOTCommander.findAndInvokeMethod(questFactory, GOTMiniQuestFactory.class, factory, "addQuest", GOTMiniQuest.QuestFactoryBase.class);
	}

	public static GOTMiniQuestFactory addMiniQuestFactory(String enumName, String name) {
		Class<?>[] classArr = new Class[] { String.class };
		Object[] args = { name };
		return EnumHelper.addEnum(GOTMiniQuestFactory.class, enumName, classArr, args);
	}

	public static GOTMountains addMountain(String name, float x, float z, float h, int r) {
		return GOTCommander.addMountain(name, x, z, h, r, 0);
	}

	public static GOTMountains addMountain(String name, float x, float z, float h, int r, int lava) {
		Class<?>[] classArr = new Class[] { float.class, float.class, float.class, int.class, int.class };
		Object[] args = { Float.valueOf(x), Float.valueOf(z), Float.valueOf(h), Integer.valueOf(r), Integer.valueOf(lava) };
		return EnumHelper.addEnum(GOTMountains.class, name, classArr, args);
	}

	public static void addNameBank(Map<String, String[]> nameBanks) {
		Map<String, String[]> allNameBanks = (Map<String, String[]>) ReflectionHelper.getPrivateValue(GOTNames.class, null, "allNameBanks");
		allNameBanks.putAll(nameBanks);
		ReflectionHelper.setPrivateValue(GOTNames.class, null, allNameBanks, "allNameBanks");
	}

	public static GOTShields addShield(String enumName, boolean hidden, String... players) {
		return GOTCommander.addShield(enumName, ShieldType.EXCLUSIVE, hidden, players);
	}

	public static GOTShields addShield(String enumName, ShieldType type, boolean hidden, String... players) {
		Class<?>[] classArr = new Class[] { ShieldType.class, boolean.class, String[].class };
		Object[] args = { type, Boolean.valueOf(hidden), players };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	public static GOTFaction addSpecialFaction(String enumName) {
		return GOTCommander.addFaction(enumName, 0, GOTDimension.GAME_OF_THRONES, null, true, true, -2147483648, null);
	}

	public static void addSpeechBank(String name, boolean rand, List<String> lines) {
		Class<?> speechBankClass = GOTSpeech.class.getDeclaredClasses()[0];
		Object speechBank = findAndInvokeConstructor(new Object[] { name, Boolean.valueOf(rand), lines }, speechBankClass, String.class, boolean.class, List.class);
		Map<String, Object> allSpeechBanks = (Map<String, Object>) ReflectionHelper.getPrivateValue(GOTSpeech.class, null, "allSpeechBanks");
		allSpeechBanks.put(name, speechBank);
		ReflectionHelper.setPrivateValue(GOTSpeech.class, null, allSpeechBanks, "allSpeechBanks");
	}

	public static void addSTRScan(Map<String, GOTStructureScan> scans) {
		Map<String, GOTStructureScan> allScans = (Map<String, GOTStructureScan>) ReflectionHelper.getPrivateValue(GOTStructureScan.class, null, "allLoadedScans");
		allScans.putAll(scans);
		ReflectionHelper.setPrivateValue(GOTStructureScan.class, null, allScans, "allLoadedScans");
	}

	public static GOTTreeType addTreeType(String enumName, Object treeFactory) {
		Class<?>[] classArr = null;
		try {
			classArr = new Class[] { Class.forName("got.common.world.feature.GOTTreeType$ITreeFactory") };
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Object[] args = { treeFactory };
		return EnumHelper.addEnum(GOTTreeType.class, enumName, classArr, args);
	}

	public static GOTWaypoint addWaypoint(String name, Region waypointRegion, GOTFaction faction, int x, int z) {
		return GOTCommander.addWaypoint(name, waypointRegion, faction, x, z, false);
	}

	public static GOTWaypoint addWaypoint(String name, Region waypointRegion, GOTFaction faction, int x, int z, boolean hidden) {
		Class<?>[] classArr = new Class[] { Region.class, GOTFaction.class, int.class, int.class, boolean.class };
		Object[] args = { waypointRegion, faction, Integer.valueOf(x), Integer.valueOf(z), Boolean.valueOf(hidden) };
		return EnumHelper.addEnum(GOTWaypoint.class, name, classArr, args);
	}

	public static Region addWaypointRegion(String name) {
		Class<?>[] classArr = new Class[0];
		Object[] args = {};
		return EnumHelper.addEnum(Region.class, name, classArr, args);
	}

	public static void changeRegion(GOTFaction faction, DimensionRegion newRegion) {
		faction.factionRegion.factionList.remove(faction);
		newRegion.factionList.add(faction);
		faction.factionRegion = newRegion;
	}

	public static <E> E findAndInvokeConstructor(Object[] args, Class<E> clazz, Class<?>... parameterTypes) {
		Constructor<E> constructor = GOTCommander.findConstructor(clazz, parameterTypes);
		constructor.setAccessible(true);
		try {
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <E> E findAndInvokeConstructor(Object[] args, String className, Class<?>... parameterTypes) {
		try {
			return (E) GOTCommander.findAndInvokeConstructor(args, Class.forName(className), parameterTypes);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <E> E findAndInvokeConstructor(String className, Class<?>... parameterTypes) {
		return GOTCommander.findAndInvokeConstructor(new Object[0], className, parameterTypes);
	}

	public static <T, E> T findAndInvokeMethod(Class<? super E> clazz, E instance, String methodName) {
		return GOTCommander.findAndInvokeMethod(new Object[0], clazz, instance, methodName);
	}

	public static <T, E> T findAndInvokeMethod(Object arg, Class<? super E> clazz, E instance, String methodName, Class<?>... methodTypes) {
		return GOTCommander.findAndInvokeMethod(new Object[] { arg }, clazz, instance, new String[] { methodName }, methodTypes);
	}

	public static <T, E> T findAndInvokeMethod(Object[] arg, Class<? super E> clazz, E instance, String methodName, Class<?>... methodTypes) {
		return GOTCommander.findAndInvokeMethod(arg, clazz, instance, new String[] { methodName }, methodTypes);
	}

	public static <T, E> T findAndInvokeMethod(Object[] args, Class<? super E> clazz, E instance, String[] methodNames, Class<?>... methodTypes) {
		Method addControlZoneMethod = ReflectionHelper.findMethod(clazz, instance, methodNames, methodTypes);
		try {
			return (T) addControlZoneMethod.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <E> Constructor<E> findConstructor(Class<E> clazz, Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ModContainer getContainer(ResourceLocation res) {
		ModContainer modContainer = Loader.instance().getIndexedModList().get(res.getResourceDomain());
		if (modContainer == null) {
			throw new IllegalArgumentException("Can't find the mod container for the domain " + res.getResourceDomain());
		}
		return modContainer;
	}

	public static GOTBiome getGOTBiome(String name) {
		return ReflectionHelper.getPrivateValue(GOTBiome.class, null, name);
	}

	public static GOTCreativeTabs getGOTCreativeTab(String name) {
		return ReflectionHelper.getPrivateValue(GOTCreativeTabs.class, null, name);
	}

	public static BufferedImage getImage(InputStream in) {
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static InputStream getInputStream(ModContainer container, String path) {
		return container.getClass().getResourceAsStream(path);
	}

	public static InputStream getInputStream(ResourceLocation res) {
		return GOTCommander.getInputStream(GOTCommander.getContainer(res), GOTCommander.getPath(res));
	}

	public static <E, T> List<T> getObjectFieldsOfType(Class<? extends E> clazz, Class<? extends T> type) {
		return getObjectFieldsOfType(clazz, null, type);
	}

	public static <E, T> List<T> getObjectFieldsOfType(Class<? extends E> clazz, E instance, Class<? extends T> type) {
		ArrayList<Object> list = new ArrayList<>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field == null) {
					continue;
				}
				Object fieldObj = null;
				if (Modifier.isStatic(field.getModifiers())) {
					fieldObj = field.get(null);
				} else if (instance != null) {
					fieldObj = field.get(instance);
				}
				if (fieldObj == null || !type.isAssignableFrom(fieldObj.getClass())) {
					continue;
				}
				list.add(fieldObj);
			}
		} catch (IllegalAccessException | IllegalArgumentException exception) {
		}
		return (List<T>) list;
	}

	public static String getPath(ResourceLocation res) {
		return "/assets/" + res.getResourceDomain() + "/" + res.getResourcePath();
	}

	public static void removeBlock(Block... blocks) {
		for (Block block : blocks) {
			block.setCreativeTab(null);
		}
	}

	public static void removeCape(GOTCapes cape) {
		ReflectionHelper.setPrivateValue(GOTCapes.class, cape, true, "isHidden");
	}

	public static void removeCapes(GOTCapes... capes) {
		for (GOTCapes cape : capes) {
			GOTCommander.removeCape(cape);
		}
	}

	public static void removeCapesExcept(GOTCapes... capes) {
		block0: for (GOTCapes cape : GOTCapes.values()) {
			for (GOTCapes excludedCape : capes) {
				if (excludedCape == cape) {
					continue block0;
				}
			}
			GOTCommander.removeCape(cape);
		}
	}

	public static void removeDimensionRegions() {
		GOTDimension.GAME_OF_THRONES.dimensionRegions.clear();
	}

	public static void removeFaction(GOTFaction faction) {
		faction.allowPlayer = false;
		faction.hasFixedAlignment = true;
		faction.fixedAlignment = 0;
		if (faction.factionDimension != null) {
			faction.factionDimension.factionList.remove(faction);
		}
		if (faction.factionRegion != null) {
			faction.factionRegion.factionList.remove(faction);
		}
		faction.factionDimension = null;
		faction.factionRegion = null;
		GOTCommander.removeFactionSpheres(faction);
	}

	public static void removeFactionRelations() {
		ReflectionHelper.setPrivateValue(GOTFactionRelations.class, null, new HashMap<>(), "defaultMap");
	}

	public static void removeFactions(GOTFaction... factions) {
		for (GOTFaction faction : factions) {
			GOTCommander.removeFaction(faction);
		}
	}

	public static void removeFactionsExcept(GOTFaction... factions) {
		block0: for (GOTFaction faction : GOTFaction.values()) {
			for (GOTFaction excludedFaction : factions) {
				if (excludedFaction == faction) {
					continue block0;
				}
			}
			GOTCommander.removeFaction(faction);
		}
	}

	public static void removeFactionSpheres(GOTFaction faction) {
		ReflectionHelper.setPrivateValue(GOTFaction.class, faction, new ArrayList(), "controlZones");
	}

	public static void removeItem(Item... items) {
		for (Item item : items) {
			item.setCreativeTab(null);
		}
	}

	public static void removeMapLabel(GOTLabels label) {
		ReflectionHelper.setPrivateValue(GOTLabels.class, label, 1.0E-4F, "maxZoom");
		ReflectionHelper.setPrivateValue(GOTLabels.class, label, 0, "minZoom");
	}

	public static void removeMapLabels(GOTLabels... labels) {
		for (GOTLabels label : labels) {
			GOTCommander.removeMapLabel(label);
		}
	}

	public static void removeMapLabelsExcept(GOTLabels... labels) {
		block0: for (GOTLabels label : GOTLabels.values()) {
			for (GOTLabels excludedLabel : labels) {
				if (excludedLabel == label) {
					continue block0;
				}
			}
			GOTCommander.removeMapLabel(label);
		}
	}

	public static void removeMiniQuestFactory(GOTMiniQuestFactory factory) {
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, null, "baseSpeechGroup");
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, null, "questAchievement");
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, new ArrayList(), "loreCategories");
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, null, "alignmentRewardOverride");
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, false, "noAlignRewardForEnemy");
		ReflectionHelper.setPrivateValue(GOTMiniQuestFactory.class, factory, new HashMap<>(), "questFactories");
	}

	public static void removeRoads() {
		Object dataBase = findAndInvokeConstructor("got.common.world.map.GOTRoads$RoadPointDatabase");
		ReflectionHelper.setPrivateValue(GOTRoads.class, null, dataBase, "roadPointDatabase");
	}

	public static void removeShield(GOTShields shield) {
		ReflectionHelper.setPrivateValue(GOTShields.class, shield, true, "isHidden");
	}

	public static void removeShields(GOTShields... shields) {
		for (GOTShields shield : shields) {
			GOTCommander.removeShield(shield);
		}
	}

	public static void removeShieldsExcept(GOTShields... shields) {
		block0: for (GOTShields shield : GOTShields.values()) {
			for (GOTShields excludedShield : shields) {
				if (excludedShield == shield) {
					continue block0;
				}
			}
			GOTCommander.removeShield(shield);
		}
	}

	public static void removeWalls() {
		Object dataBase = findAndInvokeConstructor("got.common.world.map.GOTWalls$WallPointDatabase");
		ReflectionHelper.setPrivateValue(GOTWalls.class, null, dataBase, "wallPointDatabase");
	}

	public static void removeWaypoint(GOTWaypoint wp) {
		ReflectionHelper.setPrivateValue(GOTWaypoint.class, wp, true, "isHidden");
		ReflectionHelper.setPrivateValue(GOTWaypoint.class, wp, GOTFaction.HOSTILE, "faction");
	}

	public static void removeWaypoints(GOTWaypoint... wps) {
		for (GOTWaypoint wp : wps) {
			GOTCommander.removeWaypoint(wp);
		}
	}

	public static void removeWaypointsExcept(GOTWaypoint... wps) {
		block0: for (GOTWaypoint wp : GOTWaypoint.values()) {
			for (GOTWaypoint excludedWp : wps) {
				if (excludedWp == wp) {
					continue block0;
				}
			}
			GOTCommander.removeWaypoint(wp);
		}
	}

	@SideOnly(value = Side.CLIENT)
	public static void setClientMapImage(ResourceLocation res) {
		ResourceLocation sepiaMapTexture;
		ReflectionHelper.setPrivateValue(GOTTextures.class, null, res, "mapTexture");
		try {
			BufferedImage mapImage = GOTCommander.getImage(Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream());
			sepiaMapTexture = GOTCommander.findAndInvokeMethod(new Object[] { mapImage, new ResourceLocation("got:textures/map_sepia") }, GOTTextures.class, null, "convertToSepia", BufferedImage.class, ResourceLocation.class);
		} catch (IOException e) {
			FMLLog.severe("Failed to generate GOT sepia map");
			e.printStackTrace();
			sepiaMapTexture = res;
		}
		ReflectionHelper.setPrivateValue(GOTTextures.class, null, sepiaMapTexture, "sepiaMapTexture");
	}

	public static void setDimensionForRegion(DimensionRegion region, GOTDimension dimension) {
		region.setDimension(dimension);
		dimension.dimensionRegions.add(region);
	}

	public static void setMainMenuWaypointRoute(List<GOTWaypoint> waypointRoute) {
		ReflectionHelper.setPrivateValue(GOTGuiMainMenu.class, null, waypointRoute, "waypointRoute");
	}

	public static void setServerMapImage(ResourceLocation res) {
		BufferedImage img = getImage(getInputStream(res));
		GOTGenLayerWorld.imageWidth = img.getWidth();
		GOTGenLayerWorld.imageHeight = img.getHeight();
		int[] colors = img.getRGB(0, 0, GOTGenLayerWorld.imageWidth, GOTGenLayerWorld.imageHeight, null, 0, GOTGenLayerWorld.imageWidth);
		byte[] biomeImageData = new byte[GOTGenLayerWorld.imageWidth * GOTGenLayerWorld.imageHeight];
		for (int i = 0; i < colors.length; ++i) {
			int color = colors[i];
			Integer biomeID = GOTDimension.GAME_OF_THRONES.colorsToBiomeIDs.get(color);
			if (biomeID == null) {
				continue;
			}
			biomeImageData[i] = (byte) biomeID.intValue();
		}
		ReflectionHelper.setPrivateValue(GOTGenLayerWorld.class, null, biomeImageData, "biomeImageData");
	}
}