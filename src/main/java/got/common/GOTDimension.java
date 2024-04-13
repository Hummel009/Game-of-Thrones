package got.common;

import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

import java.util.*;

public enum GOTDimension {
	GAME_OF_THRONES("GameOfThrones", 99, GOTWorldProvider.class, true, 99, EnumSet.of(DimensionRegion.EAST_ESSOS, DimensionRegion.WEST_ESSOS, DimensionRegion.WESTEROS, DimensionRegion.OTHER));

	private final GOTBiome[] biomeList = new GOTBiome[256];

	private final Collection<GOTFaction> factionList = new ArrayList<>();
	private final Collection<GOTAchievement> allAchievements = new ArrayList<>();
	private final List<GOTBiome> majorBiomes = new ArrayList<>();
	private final List<DimensionRegion> dimensionRegions = new ArrayList<>();
	private final List<GOTAchievement.Category> achievementCategories = new ArrayList<>();
	private final Map<Integer, Integer> colorsToBiomeIDs = new HashMap<>();

	private final Class<? extends WorldProvider> providerClass;
	private final String dimensionName;

	private final boolean loadSpawn;
	private final int dimensionID;
	private final int spawnCap;

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

	public static void onInit() {
		for (GOTDimension dim : values()) {
			DimensionManager.registerProviderType(dim.dimensionID, dim.providerClass, dim.loadSpawn);
			DimensionManager.registerDimension(dim.dimensionID, dim.dimensionID);
		}
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public String getTranslatedDimensionName() {
		return StatCollector.translateToLocal(getUntranslatedDimensionName());
	}

	private String getUntranslatedDimensionName() {
		return "got.dimension." + dimensionName;
	}

	public int getDimensionID() {
		return dimensionID;
	}

	public Map<Integer, Integer> getColorsToBiomeIDs() {
		return colorsToBiomeIDs;
	}

	public List<GOTBiome> getMajorBiomes() {
		return majorBiomes;
	}

	public Collection<GOTFaction> getFactionList() {
		return factionList;
	}

	public List<DimensionRegion> getDimensionRegions() {
		return dimensionRegions;
	}

	public int getSpawnCap() {
		return spawnCap;
	}

	public List<GOTAchievement.Category> getAchievementCategories() {
		return achievementCategories;
	}

	public Collection<GOTAchievement> getAllAchievements() {
		return allAchievements;
	}

	public GOTBiome[] getBiomeList() {
		return biomeList;
	}

	public enum DimensionRegion {
		WESTEROS("westeros"), WEST_ESSOS("westEssos"), EAST_ESSOS("eastEssos"), OTHER("other");

		private final List<GOTFaction> factionList = new ArrayList<>();
		private final String regionName;

		private GOTDimension dimension;

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
			return StatCollector.translateToLocal("got.dimension." + dimension.dimensionName + '.' + codeName());
		}

		public List<GOTFaction> getFactionList() {
			return factionList;
		}
	}
}