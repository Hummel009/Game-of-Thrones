package got.common;

import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

import java.util.*;

public enum GOTDimension {
	GAME_OF_THRONES("GameOfThrones", 99, GOTWorldProvider.class, true, 99, EnumSet.of(DimensionRegion.EAST_ESSOS, DimensionRegion.WEST_ESSOS, DimensionRegion.WESTEROS, DimensionRegion.OTHER));

	public String dimensionName;
	public int dimensionID;
	public Class<? extends WorldProvider> providerClass;
	public boolean loadSpawn;
	public GOTBiome[] biomeList = new GOTBiome[256];
	public Map<Integer, Integer> colorsToBiomeIDs = new HashMap<>();
	public List<GOTBiome> majorBiomes = new ArrayList<>();
	public Collection<GOTFaction> factionList = new ArrayList<>();
	public List<DimensionRegion> dimensionRegions = new ArrayList<>();
	public int spawnCap;
	public List<GOTAchievement.Category> achievementCategories = new ArrayList<>();
	public Collection<GOTAchievement> allAchievements = new ArrayList<>();

	GOTDimension(String s, int i, Class<? extends WorldProvider> c, boolean flag, int spawns, Collection<DimensionRegion> regions) {
		dimensionName = s;
		dimensionID = i;
		providerClass = c;
		loadSpawn = flag;
		spawnCap = spawns;
		dimensionRegions.addAll(regions);
		for (DimensionRegion r : dimensionRegions) {
			r.setDimension(this);
		}
	}

	public static GOTDimension forName(String s) {
		for (GOTDimension dim : values()) {
			if (dim.dimensionName.equals(s)) {
				return dim;
			}
		}
		return null;
	}

	public static GOTDimension getCurrentDimension(World world) {
		WorldProvider provider;
		if (world != null && (provider = world.provider) instanceof GOTWorldProvider) {
			return ((GOTWorldProvider) provider).getGOTDimension();
		}
		return GAME_OF_THRONES;
	}

	public static GOTDimension getCurrentDimensionWithFallback(World world) {
		GOTDimension dim = getCurrentDimension(world);
		if (dim == null) {
			return GAME_OF_THRONES;
		}
		return dim;
	}

	public static void onInit() {
		for (GOTDimension dim : values()) {
			DimensionManager.registerProviderType(dim.dimensionID, dim.providerClass, dim.loadSpawn);
			DimensionManager.registerDimension(dim.dimensionID, dim.dimensionID);
		}
	}

	public String getDimensionName() {
		return StatCollector.translateToLocal(getUntranslatedDimensionName());
	}

	public String getUntranslatedDimensionName() {
		return "got.dimension." + dimensionName;
	}

	public enum DimensionRegion {
		WESTEROS("westeros"), WEST_ESSOS("westEssos"), EAST_ESSOS("eastEssos"), OTHER("other");

		public String regionName;
		public GOTDimension dimension;
		public List<GOTFaction> factionList = new ArrayList<>();

		DimensionRegion(String s) {
			regionName = s;
		}

		public static DimensionRegion forID(int ID) {
			for (DimensionRegion r : values()) {
				if (r.ordinal() == ID) {
					return r;
				}
			}
			return null;
		}

		public static DimensionRegion forName(String regionName) {
			for (DimensionRegion r : values()) {
				if (r.codeName().equals(regionName)) {
					return r;
				}
			}
			return null;
		}

		public String codeName() {
			return regionName;
		}

		public GOTDimension getDimension() {
			return dimension;
		}

		public void setDimension(GOTDimension dim) {
			dimension = dim;
		}

		public String getRegionName() {
			return StatCollector.translateToLocal("got.dimension." + dimension.dimensionName + "." + codeName());
		}
	}

}
