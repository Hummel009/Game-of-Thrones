package got.common;

import java.util.*;

import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.util.StatCollector;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public enum GOTDimension {
	GAME_OF_THRONES("GameOfThrones", 99, GOTWorldProvider.class, true, 99, EnumSet.of(DimensionRegion.EAST_ESSOS, DimensionRegion.WEST_ESSOS, DimensionRegion.WESTEROS, DimensionRegion.OTHER));

	private String dimensionName;
	private int dimensionID;
	private Class providerClass;
	private boolean loadSpawn;
	private GOTBiome[] biomeList = new GOTBiome[256];
	private Map<Integer, Integer> colorsToBiomeIDs = new HashMap<>();
	private List<GOTBiome> majorBiomes = new ArrayList<>();
	private List<GOTFaction> factionList = new ArrayList<>();
	private List<DimensionRegion> dimensionRegions = new ArrayList<>();
	private int spawnCap;
	private List<GOTAchievement.Category> achievementCategories = new ArrayList<>();
	private List<GOTAchievement> allAchievements = new ArrayList<>();

	GOTDimension(String s, int i, Class c, boolean flag, int spawns, EnumSet<DimensionRegion> regions) {
		dimensionName = s;
		setDimensionID(i);
		providerClass = c;
		loadSpawn = flag;
		setSpawnCap(spawns);
		getDimensionRegions().addAll(regions);
		for (DimensionRegion r : getDimensionRegions()) {
			r.setDimension(this);
		}
	}

	public List<GOTAchievement.Category> getAchievementCategories() {
		return achievementCategories;
	}

	public List<GOTAchievement> getAllAchievements() {
		return allAchievements;
	}

	public GOTBiome[] getBiomeList() {
		return biomeList;
	}

	public Map<Integer, Integer> getColorsToBiomeIDs() {
		return colorsToBiomeIDs;
	}

	public int getDimensionID() {
		return dimensionID;
	}

	public String getDimensionName() {
		return StatCollector.translateToLocal(getUntranslatedDimensionName());
	}

	public List<DimensionRegion> getDimensionRegions() {
		return dimensionRegions;
	}

	public List<GOTFaction> getFactionList() {
		return factionList;
	}

	public List<GOTBiome> getMajorBiomes() {
		return majorBiomes;
	}

	public int getSpawnCap() {
		return spawnCap;
	}

	private String getUntranslatedDimensionName() {
		return "got.dimension." + dimensionName;
	}

	public void setAchievementCategories(List<GOTAchievement.Category> achievementCategories) {
		this.achievementCategories = achievementCategories;
	}

	public void setAllAchievements(List<GOTAchievement> allAchievements) {
		this.allAchievements = allAchievements;
	}

	public void setBiomeList(GOTBiome[] biomeList) {
		this.biomeList = biomeList;
	}

	public void setColorsToBiomeIDs(Map<Integer, Integer> colorsToBiomeIDs) {
		this.colorsToBiomeIDs = colorsToBiomeIDs;
	}

	public void setDimensionID(int dimensionID) {
		this.dimensionID = dimensionID;
	}

	public void setDimensionRegions(List<DimensionRegion> dimensionRegions) {
		this.dimensionRegions = dimensionRegions;
	}

	public void setFactionList(List<GOTFaction> factionList) {
		this.factionList = factionList;
	}

	public void setMajorBiomes(List<GOTBiome> majorBiomes) {
		this.majorBiomes = majorBiomes;
	}

	public void setSpawnCap(int spawnCap) {
		this.spawnCap = spawnCap;
	}

	public static GOTDimension forName(String s) {
		for (GOTDimension dim : GOTDimension.values()) {
			if (!dim.dimensionName.equals(s)) {
				continue;
			}
			return dim;
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
		GOTDimension dim = GOTDimension.getCurrentDimension(world);
		if (dim == null) {
			return GAME_OF_THRONES;
		}
		return dim;
	}

	public static void onInit() {
		for (GOTDimension dim : GOTDimension.values()) {
			DimensionManager.registerProviderType(dim.getDimensionID(), dim.providerClass, dim.loadSpawn);
			DimensionManager.registerDimension(dim.getDimensionID(), dim.getDimensionID());
		}
	}

	public static enum DimensionRegion {
		WESTEROS("westeros"), WEST_ESSOS("westEssos"), EAST_ESSOS("eastEssos"), OTHER("other");

		private String regionName;
		private GOTDimension dimension;
		private List<GOTFaction> factionList = new ArrayList<>();

		DimensionRegion(String s) {
			regionName = s;
		}

		public String codeName() {
			return regionName;
		}

		public GOTDimension getDimension() {
			return dimension;
		}

		public List<GOTFaction> getFactionList() {
			return factionList;
		}

		public String getRegionName() {
			return StatCollector.translateToLocal("got.dimension." + dimension.dimensionName + "." + codeName());
		}

		public void setDimension(GOTDimension dim) {
			dimension = dim;
		}

		public void setFactionList(List<GOTFaction> factionList) {
			this.factionList = factionList;
		}

		public static DimensionRegion forID(int ID) {
			for (DimensionRegion r : DimensionRegion.values()) {
				if (r.ordinal() != ID) {
					continue;
				}
				return r;
			}
			return null;
		}

		public static DimensionRegion forName(String regionName) {
			for (DimensionRegion r : DimensionRegion.values()) {
				if (!r.codeName().equals(regionName)) {
					continue;
				}
				return r;
			}
			return null;
		}
	}

}
