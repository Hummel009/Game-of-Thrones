package got.common.world.map;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.GOTConfig;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import got.common.network.GOTPacketConquestGrid;
import got.common.network.GOTPacketConquestNotification;
import got.common.network.GOTPacketHandler;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import got.common.world.genlayer.GOTGenLayerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.io.File;
import java.util.*;

public class GOTConquestGrid {
	private static final int MAP_GRID_SCALE = IntMath.pow(2, 3);
	private static final Map<GridCoordPair, GOTConquestZone> zoneMap = new HashMap<>();
	private static final GOTConquestZone dummyZone = new GOTConquestZone(-999, -999).setDummyZone();
	private static final Collection<GridCoordPair> dirtyZones = new HashSet<>();
	private static final Map<GridCoordPair, List<GOTFaction>> cachedZoneFactions = new HashMap<>();

	private static boolean needsLoad = true;

	private GOTConquestGrid() {
	}

	public static boolean anyChangedZones() {
		return !dirtyZones.isEmpty();
	}

	public static ConquestViewableQuery canPlayerViewConquest(EntityPlayer entityplayer, GOTFaction fac) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		GOTFaction pledged = pd.getPledgeFaction();
		if (pledged != null) {
			if (fac == pledged) {
				return ConquestViewableQuery.canView();
			}
			float align = pd.getAlignment(pledged);
			GOTFactionRank pledgeRank = pledged.getPledgeRank();
			if (fac.isAlly(pledged) || fac.isBadRelation(pledged)) {
				return ConquestViewableQuery.canView();
			}
			GOTFactionRank higherRank = pledged.getRankNAbove(pledgeRank, 2);
			if (align >= higherRank.getAlignment()) {
				return ConquestViewableQuery.canView();
			}
			return new ConquestViewableQuery(ConquestViewable.NEED_RANK, higherRank);
		}
		return new ConquestViewableQuery(ConquestViewable.UNPLEDGED, null);
	}

	private static void checkNotifyConquest(GOTConquestZone zone, EntityPlayer originPlayer, GOTFaction faction, float newConq, float prevConq, boolean isCleansing) {
		if (MathHelper.floor_double(newConq / 50.0f) != MathHelper.floor_double(prevConq / 50.0f) || newConq == 0.0f && prevConq != newConq) {
			World world = originPlayer.worldObj;
			List<EntityPlayer> players = world.playerEntities;
			for (EntityPlayer player : players) {
				GOTFaction pledgeFac;
				GOTPlayerData pd = GOTLevelData.getData(player);
				if (player.getDistanceSqToEntity(originPlayer) > 40000.0 || getZoneByEntityCoords(player) != zone) {
					continue;
				}
				boolean playerApplicable = isCleansing ? (pledgeFac = pd.getPledgeFaction()) != null && pledgeFac.isBadRelation(faction) : pd.isPledgedTo(faction);
				if (!playerApplicable) {
					continue;
				}
				IMessage pkt = new GOTPacketConquestNotification(faction, newConq, isCleansing);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(pkt, (EntityPlayerMP) player);
			}
		}
	}

	public static boolean conquestEnabled(World world) {
		return GOTConfig.enableConquest && world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
	}

	public static float doRadialConquest(World world, GOTConquestZone centralZone, EntityPlayer killingPlayer, GOTFaction pledgeFaction, GOTFaction enemyFaction, float conqGain, float conqCleanse) {
		if (!centralZone.isDummyZone()) {
			float centralConqBonus = 0.0f;
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
					float enemyConq;
					int distSq = i1 * i1 + k1 * k1;
					if (distSq > 12.25f) {
						continue;
					}
					int zoneX = centralZone.getGridX() + i1;
					int zoneZ = centralZone.getGridZ() + k1;
					float dist = MathHelper.sqrt_float(distSq);
					float frac = 1.0f - dist / 3.5f;
					float conqGainHere = frac * conqGain;
					float conqCleanseHere = frac * conqCleanse;
					GOTConquestZone zone = getZoneByGridCoords(zoneX, zoneZ);
					if (zone.isDummyZone()) {
						continue;
					}
					boolean doneEnemyCleansing = false;
					if (enemyFaction != null && (enemyConq = zone.getConquestStrength(enemyFaction, world)) > 0.0f) {
						zone.addConquestStrength(enemyFaction, -conqCleanseHere, world);
						float newEnemyConq = zone.getConquestStrength(enemyFaction, world);
						if (zone == centralZone) {
							centralConqBonus = newEnemyConq - enemyConq;
						}
						if (killingPlayer != null) {
							checkNotifyConquest(zone, killingPlayer, enemyFaction, newEnemyConq, enemyConq, true);
						}
						doneEnemyCleansing = true;
					}
					if (doneEnemyCleansing || pledgeFaction == null) {
						continue;
					}
					float prevZoneConq = zone.getConquestStrength(pledgeFaction, world);
					zone.addConquestStrength(pledgeFaction, conqGainHere, world);
					float newZoneConq = zone.getConquestStrength(pledgeFaction, world);
					if (zone == centralZone) {
						centralConqBonus = newZoneConq - prevZoneConq;
					}
					if (killingPlayer == null) {
						continue;
					}
					checkNotifyConquest(zone, killingPlayer, pledgeFaction, newZoneConq, prevZoneConq, false);
				}
			}
			return centralConqBonus;
		}
		return 0.0f;
	}

	private static File getConquestDir() {
		File dir = new File(GOTLevelData.getOrCreateGOTDir(), "conquest_zones");
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				GOTLog.getLogger().info("GOTConquestGrid: file wasn't created");
			}
		}
		return dir;
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	public static ConquestEffective getConquestEffectIn(World world, GOTConquestZone zone, GOTFaction theFaction) {
		GridCoordPair gridCoords;
		if (!GOTGenLayerWorld.loadedBiomeImage()) {
			new GOTGenLayerWorld();
		}
		List<GOTFaction> cachedFacs = cachedZoneFactions.get(gridCoords = GridCoordPair.forZone(zone));
		if (cachedFacs == null) {
			GOTBiome biome;
			cachedFacs = new ArrayList<>();
			Collection<GOTBiome> includedBiomes = new ArrayList<>();
			int[] mapMin = getMinCoordsOnMap(zone);
			int[] mapMax = getMaxCoordsOnMap(zone);
			int mapXMin = mapMin[0];
			int mapXMax = mapMax[0];
			int mapZMin = mapMin[1];
			int mapZMax = mapMax[1];
			for (int i = mapXMin; i < mapXMax; ++i) {
				for (int k = mapZMin; k < mapZMax; ++k) {
					biome = GOTGenLayerWorld.getBiomeOrOcean(i, k);
					if (includedBiomes.contains(biome)) {
						continue;
					}
					includedBiomes.add(biome);
				}
			}
			block2:
			for (GOTFaction fac : GOTFaction.getPlayableAlignmentFactions()) {
				for (GOTBiome biome2 : includedBiomes) {
					if (!biome2.getNPCSpawnList().isFactionPresent(world, fac)) {
						continue;
					}
					cachedFacs.add(fac);
					continue block2;
				}
			}
			cachedZoneFactions.put(gridCoords, cachedFacs);
		}
		if (cachedFacs.contains(theFaction)) {
			return ConquestEffective.EFFECTIVE;
		}
		for (GOTFaction allyFac : theFaction.getConquestBoostRelations()) {
			if (!cachedFacs.contains(allyFac)) {
				continue;
			}
			return ConquestEffective.ALLY_BOOST;
		}
		return ConquestEffective.NO_EFFECT;
	}

	public static int[] getMaxCoordsOnMap(GOTConquestZone zone) {
		return new int[]{gridToMapCoord(zone.getGridX() + 1), gridToMapCoord(zone.getGridZ() + 1)};
	}

	public static int[] getMinCoordsOnMap(GOTConquestZone zone) {
		return new int[]{gridToMapCoord(zone.getGridX()), gridToMapCoord(zone.getGridZ())};
	}

	private static GOTConquestZone getZoneByEntityCoords(Entity entity) {
		int i = MathHelper.floor_double(entity.posX);
		int k = MathHelper.floor_double(entity.posZ);
		return getZoneByWorldCoords(i, k);
	}

	private static GOTConquestZone getZoneByGridCoords(int i, int k) {
		if (i < 0 || i >= MathHelper.ceiling_float_int((float) GOTGenLayerWorld.getImageWidth() / MAP_GRID_SCALE) || k < 0 || k >= MathHelper.ceiling_float_int((float) GOTGenLayerWorld.getImageHeight() / MAP_GRID_SCALE)) {
			return dummyZone;
		}
		GridCoordPair key = new GridCoordPair(i, k);
		GOTConquestZone zone = zoneMap.get(key);
		if (zone == null) {
			File zoneDat = getZoneDat(key);
			zone = loadZoneFromFile(zoneDat);
			if (zone == null) {
				zone = new GOTConquestZone(i, k);
			}
			zoneMap.put(key, zone);
		}
		return zone;
	}

	public static GOTConquestZone getZoneByWorldCoords(int i, int k) {
		int x = worldToGridX(i);
		int z = worldToGridZ(k);
		return getZoneByGridCoords(x, z);
	}

	private static File getZoneDat(GOTConquestZone zone) {
		GridCoordPair key = GridCoordPair.forZone(zone);
		return getZoneDat(key);
	}

	private static File getZoneDat(GridCoordPair key) {
		return new File(getConquestDir(), key.getGridX() + "." + key.getGridZ() + ".dat");
	}

	private static int gridToMapCoord(int i) {
		return i << 3;
	}

	public static void loadAllZones() {
		try {
			zoneMap.clear();
			dirtyZones.clear();
			File dir = getConquestDir();
			if (dir.exists()) {
				for (File zoneDat : dir.listFiles()) {
					GOTConquestZone zone;
					if (zoneDat.isDirectory() || !zoneDat.getName().endsWith(".dat") || (zone = loadZoneFromFile(zoneDat)) == null) {
						continue;
					}
					GridCoordPair key = GridCoordPair.forZone(zone);
					zoneMap.put(key, zone);
				}
			}
			needsLoad = false;
			FMLLog.info("Hummel009: Loaded %s conquest zones", zoneMap.size());
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT conquest zones");
			e.printStackTrace();
		}
	}

	private static GOTConquestZone loadZoneFromFile(File zoneDat) {
		try {
			NBTTagCompound nbt = GOTLevelData.loadNBTFromFile(zoneDat);
			if (nbt.hasNoTags()) {
				return null;
			}
			GOTConquestZone zone = GOTConquestZone.readFromNBT(nbt);
			if (zone.isEmpty()) {
				return null;
			}
			return zone;
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT conquest zone from %s", zoneDat.getName());
			e.printStackTrace();
			return null;
		}
	}

	public static void markZoneDirty(GOTConquestZone zone) {
		GridCoordPair key = GridCoordPair.forZone(zone);
		if (zoneMap.containsKey(key)) {
			dirtyZones.add(key);
		}
	}

	public static float onConquestKill(EntityPlayer entityplayer, GOTFaction pledgeFaction, GOTFaction enemyFaction, float alignBonus) {
		World world = entityplayer.worldObj;
		if (!world.isRemote && conquestEnabled(world) && GOTLevelData.getData(entityplayer).getEnableConquestKills() && entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) {
			GOTConquestZone centralZone = getZoneByEntityCoords(entityplayer);
			float conqAmount = alignBonus * GOTLevelData.getConquestRate();
			return doRadialConquest(world, centralZone, entityplayer, pledgeFaction, enemyFaction, conqAmount, conqAmount);
		}
		return 0.0f;
	}

	public static void saveChangedZones() {
		try {
			Collection<GridCoordPair> removes = new HashSet<>();
			for (GridCoordPair key : dirtyZones) {
				GOTConquestZone zone = zoneMap.get(key);
				if (zone == null) {
					continue;
				}
				saveZoneToFile(zone);
				if (!zone.isEmpty()) {
					continue;
				}
				removes.add(key);
			}
			dirtyZones.clear();
			for (GridCoordPair key : removes) {
				zoneMap.remove(key);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT conquest zones");
			e.printStackTrace();
		}
	}

	private static void saveZoneToFile(GOTConquestZone zone) {
		File zoneDat = getZoneDat(zone);
		try {
			if (zone.isEmpty()) {
				boolean deleted = zoneDat.delete();
				if (!deleted) {
					GOTLog.getLogger().info("GOTConquestGrid: file wasn't deleted");
				}
			} else {
				NBTTagCompound nbt = new NBTTagCompound();
				zone.writeToNBT(nbt);
				GOTLevelData.saveNBTToFile(zoneDat, nbt);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT conquest zone to %s", zoneDat.getName());
			e.printStackTrace();
		}
	}

	public static void sendConquestGridTo(EntityPlayerMP entityplayer, GOTFaction fac) {
		IMessage pkt = new GOTPacketConquestGrid(fac, zoneMap.values(), entityplayer.worldObj);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(pkt, entityplayer);
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (fac == pd.getPledgeFaction()) {
			pd.addAchievement(GOTAchievement.factionConquest);
		}
	}

	public static void updateZones(World world) {
		MinecraftServer srv;
		if (conquestEnabled(world) && (srv = MinecraftServer.getServer()) != null) {
			int tick = srv.getTickCounter();
			int interval = 6000;
			for (GOTConquestZone zone : zoneMap.values()) {
				if (zone.isEmpty() || IntMath.mod(tick, interval) != IntMath.mod(zone.hashCode(), interval)) {
					continue;
				}
				zone.checkForEmptiness(world);
			}
		}
	}

	private static int worldToGridX(int i) {
		int mapX = i >> 7;
		return mapX + GOTGenLayerWorld.ORIGIN_X >> 3;
	}

	private static int worldToGridZ(int k) {
		int mapZ = k >> 7;
		return mapZ + GOTGenLayerWorld.ORIGIN_Z >> 3;
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTConquestGrid.needsLoad = needsLoad;
	}

	public enum ConquestEffective {
		EFFECTIVE, ALLY_BOOST, NO_EFFECT
	}

	public enum ConquestViewable {
		UNPLEDGED, CAN_VIEW, NEED_RANK
	}

	public static class ConquestViewableQuery {
		private final ConquestViewable result;
		private final GOTFactionRank needRank;

		private ConquestViewableQuery(ConquestViewable res, GOTFactionRank rank) {
			result = res;
			needRank = rank;
		}

		private static ConquestViewableQuery canView() {
			return new ConquestViewableQuery(ConquestViewable.CAN_VIEW, null);
		}

		public GOTFactionRank getNeedRank() {
			return needRank;
		}

		public ConquestViewable getResult() {
			return result;
		}
	}

	private static class GridCoordPair {
		private final int gridX;
		private final int gridZ;

		private GridCoordPair(int i, int k) {
			gridX = i;
			gridZ = k;
		}

		private static GridCoordPair forZone(GOTConquestZone zone) {
			return new GridCoordPair(zone.getGridX(), zone.getGridZ());
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof GridCoordPair)) {
				return false;
			}
			GridCoordPair otherPair = (GridCoordPair) other;
			return gridX == otherPair.gridX && gridZ == otherPair.gridZ;
		}

		@Override
		public int hashCode() {
			int i = 1664525 * gridX + 1013904223;
			int j = 1664525 * (gridZ ^ 0xDEADBEEF) + 1013904223;
			return i ^ j;
		}

		private int getGridX() {
			return gridX;
		}

		private int getGridZ() {
			return gridZ;
		}
	}
}