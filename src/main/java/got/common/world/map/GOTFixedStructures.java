package got.common.world.map;

import got.GOT;
import got.common.GOTConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public enum GOTFixedStructures {
	NIGHT_KING(613, 314);

	private static long nanoTimeElapsed;

	private final int xCoord;
	private final int zCoord;

	GOTFixedStructures(double x, double z) {
		xCoord = GOTWaypoint.mapToWorldX(x);
		zCoord = GOTWaypoint.mapToWorldZ(z);
	}

	public static boolean[] _mountainNear_structureNear(World world, int x, int z) {
		long l = System.nanoTime();
		boolean mountainNear = false;
		boolean structureNear = false;
		if (hasMapFeatures(world)) {
			if (GOTMountains.mountainAt(x, z)) {
				mountainNear = true;
			}
			structureNear = structureNear(x, z, 256);
			if (!structureNear) {
				for (GOTWaypoint wp : GOTWaypoint.values()) {
					double dz;
					double dx = x - wp.getCoordX();
					double distSq = dx * dx + (dz = z - wp.getCoordZ()) * dz;
					double range = 256.0;
					if (distSq >= range * range) {
						continue;
					}
					structureNear = true;
					break;
				}
			}
			boolean roadNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.ROAD) >= 0.0f;
			boolean wallNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.WALL) >= 0.0f;
			boolean linkerNear = GOTBeziers.isBezierNear(x, z, 32, GOTBeziers.Type.LINKER) >= 0.0f;

			if (!structureNear && (roadNear || wallNear || linkerNear)) {
				structureNear = true;
			}
		}
		nanoTimeElapsed += System.nanoTime() - l;
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

	@SuppressWarnings("unused")
	private static long getNanoTimeElapsed() {
		return nanoTimeElapsed;
	}

	public static void setNanoTimeElapsed(long nanoTimeElapsed) {
		GOTFixedStructures.nanoTimeElapsed = nanoTimeElapsed;
	}

	public double distanceSqTo(EntityLivingBase entity) {
		double dx = entity.posX - (xCoord + 0.5);
		double dz = entity.posZ - (zCoord + 0.5);
		return dx * dx + dz * dz;
	}
}