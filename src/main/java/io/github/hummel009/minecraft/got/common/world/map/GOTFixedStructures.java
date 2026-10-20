package io.github.hummel009.minecraft.got.common.world.map;

import io.github.hummel009.minecraft.got.GOT;
import io.github.hummel009.minecraft.got.common.GOTConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.*;

public enum GOTFixedStructures {
	NIGHT_KING(613, 314);

	private static final int GRID_SHIFT = 10;
	private static final int STRUCTURE_RANGE = 256;

	private static Map<Long, List<GOTWaypoint>> grid;

	private final int xCoord;
	private final int zCoord;

	GOTFixedStructures(double x, double z) {
		xCoord = GOTWaypoint.mapToWorldX(x);
		zCoord = GOTWaypoint.mapToWorldZ(z);
	}

	private static long packKey(int gx, int gz) {
		return (long) gx << 32 | gz & 0xFFFFFFFFL;
	}

	private static List<GOTWaypoint> gridGet(int gx, int gz) {
		List<GOTWaypoint> list = grid.get(packKey(gx, gz));
		return list == null ? Collections.emptyList() : list;
	}

	private static void ensureGrid() {
		if (grid != null) {
			return;
		}
		Map<Long, List<GOTWaypoint>> built = new HashMap<>();
		for (GOTWaypoint wp : GOTWaypoint.values()) {
			int wx = wp.getCoordX();
			int wz = wp.getCoordZ();
			int minGX = wx - STRUCTURE_RANGE >> GRID_SHIFT;
			int maxGX = wx + STRUCTURE_RANGE >> GRID_SHIFT;
			int minGZ = wz - STRUCTURE_RANGE >> GRID_SHIFT;
			int maxGZ = wz + STRUCTURE_RANGE >> GRID_SHIFT;
			for (int gx = minGX; gx <= maxGX; ++gx) {
				for (int gz = minGZ; gz <= maxGZ; ++gz) {
					long key = packKey(gx, gz);
					built.computeIfAbsent(key, k -> new ArrayList<>()).add(wp);
				}
			}
		}
		grid = built;
	}

	private static boolean anyWaypointNear(int x, int z, int range) {
		ensureGrid();
		double rangeSq = (double) range * range;
		int minGX = x - range >> GRID_SHIFT;
		int maxGX = x + range >> GRID_SHIFT;
		int minGZ = z - range >> GRID_SHIFT;
		int maxGZ = z + range >> GRID_SHIFT;
		for (int gx = minGX; gx <= maxGX; ++gx) {
			for (int gz = minGZ; gz <= maxGZ; ++gz) {
				for (GOTWaypoint wp : gridGet(gx, gz)) {
					double dx = x - wp.getCoordX();
					double dz = z - wp.getCoordZ();
					if (dx * dx + dz * dz < rangeSq) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean[] isMountainOrStructureNear(World world, int x, int z) {
		boolean mountainNear = false;
		boolean structureNear = false;
		if (hasMapFeatures(world)) {
			if (GOTMountains.mountainAt(x, z)) {
				mountainNear = true;
			}
			structureNear = structureNear(x, z, STRUCTURE_RANGE);
			if (!structureNear) {
				structureNear = anyWaypointNear(x, z, STRUCTURE_RANGE);
			}
			boolean roadNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.ROAD) >= 0.0f;
			boolean wallNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.WALL) >= 0.0f;
			boolean linkerNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.LINKER) >= 0.0f;

			if (!structureNear && (roadNear || wallNear || linkerNear)) {
				structureNear = true;
			}
		}
		return new boolean[]{mountainNear, structureNear};
	}

	public static boolean fixedAt(int i, int k, GOTAbstractWaypoint waypoint) {
		int x = GOTWaypoint.mapToWorldX(waypoint.getImgX());
		int z = GOTWaypoint.mapToWorldZ(waypoint.getImgY());
		return fixedAtfixedAtMapImageCoords(i, k, x, z);
	}

	private static boolean fixedAtfixedAtMapImageCoords(int i, int k, int x, int z) {
		return i >> 4 == x >> 4 && k >> 4 == z >> 4;
	}

	public static boolean hasMapFeatures(World world) {
		return GOTConfig.generateMapFeatures && world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
	}

	public static boolean structureNear(int x, int z, int range) {
		for (GOTFixedStructures str : values()) {
			double dx = x - str.xCoord;
			double dz = z - str.zCoord;
			double distSq = dx * dx + dz * dz;
			double rangeSq = range * range;
			if (distSq >= rangeSq) {
				continue;
			}
			return true;
		}
		return false;
	}

	public double distanceSqTo(EntityLivingBase entity) {
		double dx = entity.posX - (xCoord + 0.5);
		double dz = entity.posZ - (zCoord + 0.5);
		return dx * dx + dz * dz;
	}
}