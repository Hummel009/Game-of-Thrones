package got.common.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import got.client.GOTTextures;
import got.common.*;
import got.common.GOTDimension.DimensionRegion;
import got.common.database.*;
import got.common.database.GOTSpeech.SpeechBank;
import got.common.faction.*;
import got.common.item.other.GOTItemBanner;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.genlayer.GOTGenLayerWorld;
import got.common.world.map.*;
import got.common.world.map.GOTRoads.RoadPointDatabase;
import got.common.world.map.GOTWalls.WallPointDatabase;
import got.common.world.structure.other.GOTStructureScan;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class GOTAPI {
	public static GOTAchievement.Category addAchievementCategory(String enumName, GOTBiome biome) {
		Class[] classArr = { GOTBiome.class };
		Object[] args = { biome };
		return EnumHelper.addEnum(GOTAchievement.Category.class, enumName, classArr, args);
	}

	public static GOTAchievement.Category addAchievementCategory(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTAchievement.Category.class, enumName, classArr, args);
	}

	public static GOTCapes addAlignmentCape(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	public static GOTShields addAlignmentShield(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	public static GOTItemBanner.BannerType addBanner(String enumName, int id, String bannerName, GOTFaction faction) {
		Class[] classArr = { Integer.TYPE, String.class, GOTFaction.class };
		Object[] args = { id, bannerName, faction };
		GOTItemBanner.BannerType banner = EnumHelper.addEnum(GOTItemBanner.BannerType.class, enumName, classArr, args);
		GOTItemBanner.BannerType.bannerForID.put(banner.bannerID, banner);
		GOTItemBanner.BannerType.bannerTypes.add(banner);
		return banner;
	}

	public static GOTCapes addCape(String enumName, boolean hidden, String... players) {
		return addCape(enumName, GOTCapes.CapeType.EXCLUSIVE, hidden, players);
	}

	public static GOTCapes addCape(String enumName, GOTCapes.CapeType type, boolean hidden, String... players) {
		Class[] classArr = { GOTCapes.CapeType.class, Boolean.TYPE, String[].class };
		Object[] args = { type, hidden, players };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	public static DimensionRegion addDimensionRegion(String enumName, String regionName) {
		Class[] classArr = { String.class };
		Object[] args = { regionName };
		return EnumHelper.addEnum(DimensionRegion.class, enumName, classArr, args);
	}

	public static GOTFaction addFaction(String enumName, int color, DimensionRegion region) {
		return addFaction(enumName, color, GOTDimension.GAME_OF_THRONES, region, true, true, Integer.MIN_VALUE, null);
	}

	public static GOTFaction addFaction(String enumName, int color, GOTDimension dim, DimensionRegion region, boolean player, boolean registry, int alignment, GOTMapRegion mapInfo) {
		Class[] classArr = { Integer.TYPE, GOTDimension.class, DimensionRegion.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, GOTMapRegion.class };
		Object[] args = { color, dim, region, player, registry, alignment, mapInfo };
		return EnumHelper.addEnum(GOTFaction.class, enumName, classArr, args);
	}

	public static GOTInvasions addInvasion(String enumName, GOTFaction faction) {
		return addInvasion(enumName, faction, null);
	}

	public static GOTInvasions addInvasion(String enumName, GOTFaction faction, String subfaction) {
		Class[] classArr = { GOTFaction.class, String.class };
		Object[] args = { faction, subfaction };
		return EnumHelper.addEnum(GOTInvasions.class, enumName, classArr, args);
	}

	public static GOTLore.LoreCategory addLoreCategory(String enumName, String name) {
		Class[] classArr = { String.class };
		Object[] args = { name };
		return EnumHelper.addEnum(GOTLore.LoreCategory.class, enumName, classArr, args);
	}

	public static void addLoreToLoreCategory(GOTLore.LoreCategory category, GOTLore lore) {
		category.loreList.add(lore);
	}

	public static GOTMapLabels addMapLabel(String enumName, GOTBiome biomeLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return addMapLabel(enumName, (Object) biomeLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	private static GOTMapLabels addMapLabel(String enumName, Object label, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		Class[] classArr = { Object.class, Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE };
		Object[] args = { label, x, y, Float.valueOf(scale), angle, Float.valueOf(zoomMin), Float.valueOf(zoomMan) };
		return EnumHelper.addEnum(GOTMapLabels.class, enumName, classArr, args);
	}

	public static GOTMapLabels addMapLabel(String enumName, String stringLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return addMapLabel(enumName, (Object) stringLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	public static GOTMiniQuestFactory addMiniQuestFactory(String enumName, String name) {
		Class[] classArr = { String.class };
		Object[] args = { name };
		return EnumHelper.addEnum(GOTMiniQuestFactory.class, enumName, classArr, args);
	}

	public static GOTMountains addMountain(String name, double x, double z, float h, int r) {
		return addMountain(name, x, z, h, r, 0);
	}

	public static GOTMountains addMountain(String name, double x, double z, float h, int r, int lava) {
		Class[] classArr = { Double.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE };
		Object[] args = { x, z, Float.valueOf(h), r, lava };
		return EnumHelper.addEnum(GOTMountains.class, name, classArr, args);
	}

	public static void addNameBanks(Map<String, String[]> nameBanks) {
		GOTNames.allNameBanks.putAll(nameBanks);
	}

	public static GOTShields addShield(String enumName, boolean hidden, String... players) {
		return addShield(enumName, GOTShields.ShieldType.EXCLUSIVE, hidden, players);
	}

	public static GOTShields addShield(String enumName, GOTShields.ShieldType type, boolean hidden, String... players) {
		Class[] classArr = { GOTShields.ShieldType.class, Boolean.TYPE, String[].class };
		Object[] args = { type, hidden, players };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	public static void addSpeechBank(String name, boolean rand, List<String> lines) {
		GOTSpeech.allSpeechBanks.put(name, new SpeechBank(name, rand, lines));
	}

	public static void addSTRScans(Map<String, GOTStructureScan> scans) {
		GOTStructureScan.allLoadedScans.putAll(scans);
	}

	public static GOTTreeType addTreeType(String enumName, Object treeFactory) {
		Class[] classArr = null;
		try {
			classArr = new Class[] { Class.forName("GOT.common.world.feature.GOTTreeType$ITreeFactory") };
		} catch (ClassNotFoundException e) {
			GOTLog.logger.error("Failed to find the ITreeFactory interface.");
			e.printStackTrace();
		}
		Object[] args = { treeFactory };
		return EnumHelper.addEnum(GOTTreeType.class, enumName, classArr, args);
	}

	public static GOTWaypoint addWaypoint(String name, GOTWaypoint.Region region, GOTFaction faction, double x, double z, boolean hidden) {
		Class[] classArr = { GOTWaypoint.Region.class, GOTFaction.class, Double.TYPE, Double.TYPE, Boolean.TYPE };
		Object[] args = { region, faction, x, z, hidden };
		return EnumHelper.addEnum(GOTWaypoint.class, name, classArr, args);
	}

	public static GOTWaypoint addWaypoint(String name, GOTWaypoint.Region region, GOTFaction faction, int x, int z) {
		return addWaypoint(name, region, faction, x, z, false);
	}

	public static GOTWaypoint.Region addWaypointRegion(String name) {
		Class[] classArr = {};
		Object[] args = {};
		return EnumHelper.addEnum(GOTWaypoint.Region.class, name, classArr, args);
	}

	public static void changeDimensionRegion(GOTFaction faction, DimensionRegion newRegion) {
		faction.factionRegion.factionList.remove(faction);
		newRegion.factionList.add(faction);
		faction.factionRegion = newRegion;
	}

	public static void clearAllRelations() {
		GOTFactionRelations.defaultMap.clear();
	}

	public static void clearMiniQuestFactory(GOTMiniQuestFactory factory) {
		factory.baseSpeechGroup = null;
		factory.questAchievement = null;
		factory.loreCategories.clear();
		factory.alignmentRewardOverride = null;
		factory.noAlignRewardForEnemy = false;
		factory.questFactories.clear();
	}

	public static void clearRoadDataBase() {
		GOTRoads.allRoads.clear();
		GOTRoads.roadPointDatabase = new RoadPointDatabase();
	}

	public static void clearWallDataBase() {
		GOTWalls.allWalls.clear();
		GOTWalls.wallPointDatabase = new WallPointDatabase();
	}

	public static void disableGOTUpdateChecker() {
		GOTVersionChecker.checkedUpdate = true;
	}

	private static <E> E findAndInvokeConstructor(Object[] args, Class<E> clazz, Class<?>... parameterTypes) {
		Constructor<E> constructor = findConstructor(clazz, parameterTypes);
		constructor.setAccessible(true);
		try {
			return constructor.newInstance(args);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
			GOTLog.logger.error("Error when initializing constructor from class " + clazz.getSimpleName() + " with parameters " + args);
			e.printStackTrace();
			return null;
		}
	}

	private static <T, E> T findAndInvokeMethod(Object[] args, Class<? super E> clazz, E instance, String[] methodNames, Class<?>... methodTypes) {
		Method method = ReflectionHelper.findMethod(clazz, instance, methodNames, (Class[]) methodTypes);
		try {
			return (T) method.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			GOTLog.logger.error("Error when getting method " + methodNames[0] + " from class " + clazz.getSimpleName());
			e.printStackTrace();
			return null;
		}
	}

	private static <E> Constructor<E> findConstructor(Class<E> clazz, Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			GOTLog.logger.error("Error when getting constructor from class " + clazz.getSimpleName() + " with parameters " + parameterTypes);
			e.printStackTrace();
			return null;
		}
	}

	private static ModContainer getContainer(ResourceLocation res) {
		ModContainer modContainer = Loader.instance().getIndexedModList().get(res.getResourceDomain());
		if (modContainer == null) {
			throw new IllegalArgumentException("Can't find the mod container for the domain " + res.getResourceDomain());
		}
		return modContainer;
	}

	private static BufferedImage getImage(InputStream in) {
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			GOTLog.logger.error("Failed to convert a input stream into a buffered image.");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	private static InputStream getInputStream(ModContainer container, String path) {
		return container.getClass().getResourceAsStream(path);
	}

	private static InputStream getInputStream(ResourceLocation res) {
		return getInputStream(getContainer(res), getPath(res));
	}

	public static <E, T> List<T> getObjectFieldsOfType(Class<? extends E> clazz, Class<? extends T> type) {
		return getObjectFieldsOfType(clazz, null, type);
	}

	private static <E, T> List<T> getObjectFieldsOfType(Class<? extends E> clazz, E instance, Class<? extends T> type) {
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
		} catch (IllegalAccessException | IllegalArgumentException e) {
			GOTLog.logger.error("Errored when getting all field from: " + clazz.getName() + " of type: " + type.getName());
		}
		return (List<T>) list;
	}

	private static String getPath(ResourceLocation res) {
		return "/assets/" + res.getResourceDomain() + "/" + res.getResourcePath();
	}

	@SideOnly(value = Side.CLIENT)
	private static ResourceLocation getTextureResourceLocation(InputStream in, String textureName) {
		BufferedImage img = getImage(in);
		if (img != null) {
			return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(textureName, new DynamicTexture(img));
		}
		return null;
	}

	public static Object getTreeFactory(Class<? extends Proxy> proxyClass, InvocationHandler handler) {
		return findAndInvokeConstructor(new Object[] { handler }, proxyClass, InvocationHandler.class);
	}

	public static void hideFromInventory(Object... content) {
		for (Object obj : content) {
			if (obj instanceof Block) {
				((Block) obj).setCreativeTab(null);
			} else if (obj instanceof Item) {
				((Item) obj).setCreativeTab(null);
			}
		}
	}

	public static void removeCapes(GOTCapes... content) {
		for (GOTCapes removal : content) {
			removal.isHidden = true;
		}
	}

	public static void removeCapesExcept(GOTCapes... content) {
		for (GOTCapes removal : GOTCapes.values()) {
			for (GOTCapes excluded : content) {
				if (excluded != removal) {
					removal.isHidden = true;
					break;
				}
			}
		}
	}

	private static void removeFaction(GOTFaction faction) {
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
		faction.controlZones.clear();
	}

	public static void removeFactions(GOTFaction... content) {
		for (GOTFaction removal : content) {
			removeFaction(removal);
		}
	}

	public static void removeFactionsExcept(GOTFaction... content) {
		for (GOTFaction removal : GOTFaction.values()) {
			for (GOTFaction excluded : content) {
				if (excluded != removal) {
					removeFaction(removal);
				}
			}
		}
	}

	private static void removeMapLabel(GOTMapLabels label) {
		label.maxZoom = 1.0E-4f;
		label.minZoom = 0;
	}

	public static void removeMapLabels(GOTMapLabels... content) {
		for (GOTMapLabels removal : content) {
			removeMapLabel(removal);
		}
	}

	public static void removeMapLabelsExcept(GOTMapLabels... content) {
		for (GOTMapLabels removal : GOTMapLabels.values()) {
			for (GOTMapLabels excluded : content) {
				if (excluded != removal) {
					removeMapLabel(removal);
				}
			}
		}
	}

	public static void removeShields(GOTShields... content) {
		for (GOTShields removal : content) {
			removal.isHidden = true;
		}
	}

	public static void removeShieldsExcept(GOTShields... content) {
		for (GOTShields removal : GOTShields.values()) {
			for (GOTShields excluded : content) {
				if (excluded != removal) {
					removal.isHidden = true;
					break;
				}
			}
		}
	}

	public static void removeTitle(GOTTitle title) {
		if (title.titleType == GOTTitle.TitleType.ACHIEVEMENT) {
			title.titleAchievement.achievementTitle = null;
			title.titleAchievement = null;
		}
		title.isHidden = true;
		GOTTitle.allTitles.remove(title);
	}

	private static void removeWaypoint(GOTWaypoint wp) {
		wp.isHidden = true;
		wp.faction = GOTFaction.HOSTILE;
	}

	public static void removeWaypoints(GOTWaypoint... content) {
		for (GOTWaypoint removal : content) {
			removeWaypoint(removal);
		}
	}

	public static void removeWaypointsExcept(GOTWaypoint... content) {
		for (GOTWaypoint removal : GOTWaypoint.values()) {
			for (GOTWaypoint excluded : content) {
				if (excluded != removal) {
					removeWaypoint(removal);
				}
			}
		}
	}

	@SideOnly(value = Side.CLIENT)
	public static void setClientMapImage(ResourceLocation mapTexture) {
		GOTTextures.mapTexture = null;
		try {
			BufferedImage mapImage = getImage(Minecraft.getMinecraft().getResourceManager().getResource(mapTexture).getInputStream());
			GOTTextures.sepiaMapTexture = GOTTextures.convertToSepia(mapImage, new ResourceLocation("GOT:map_sepia"));
		} catch (IOException e) {
			FMLLog.severe("Failed to generate GOT sepia map");
			e.printStackTrace();
			GOTTextures.sepiaMapTexture = mapTexture;
		}
	}

	public static void setDimensionForRegion(DimensionRegion region, GOTDimension dimension) {
		region.setDimension(dimension);
		dimension.dimensionRegions.add(region);
	}

	public static void setEntitySize(Entity entity, float width, float height) {
		findAndInvokeMethod(new Object[] { Float.valueOf(width), Float.valueOf(height) }, Entity.class, entity, new String[] { "setSize", "func_70105_a", "a" }, Float.TYPE, Float.TYPE);
	}

	public static <T, E> void setFinalField(Class<? super T> classToAccess, T instance, E value, String... fieldNames) {
		try {
			GOTReflection.setFinalField(classToAccess, instance, value, fieldNames);
		} catch (Exception e) {
			GOTLog.logger.error("Error when setting field: " + fieldNames[0] + " for class: " + classToAccess.getName());
			e.printStackTrace();
		}
	}

	public static void setServerMapImage(ResourceLocation res) {
		BufferedImage img = getImage(getInputStream(res));
		GOTGenLayerWorld.imageWidth = img.getWidth();
		GOTGenLayerWorld.imageHeight = img.getHeight();
		int[] colors = img.getRGB(0, 0, GOTGenLayerWorld.imageWidth, GOTGenLayerWorld.imageHeight, null, 0, GOTGenLayerWorld.imageWidth);
		GOTGenLayerWorld.biomeImageData = new byte[GOTGenLayerWorld.imageWidth * GOTGenLayerWorld.imageHeight];
		for (int i = 0; i < colors.length; ++i) {
			int color = colors[i];
			Integer biomeID = GOTDimension.GAME_OF_THRONES.colorsToBiomeIDs.get(color);
			if (biomeID != null) {
				GOTGenLayerWorld.biomeImageData[i] = (byte) biomeID.intValue();
				continue;
			}
			GOTLog.logger.error("Found unknown biome on map: " + Integer.toHexString(color) + " at location: " + i % GOTGenLayerWorld.imageWidth + ", " + i / GOTGenLayerWorld.imageWidth);
			GOTGenLayerWorld.biomeImageData[i] = (byte) GOTBiome.ocean.biomeID;
		}
	}
}