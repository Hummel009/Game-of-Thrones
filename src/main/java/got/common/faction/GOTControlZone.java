package got.common.faction;

import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTWaypoint;

public class GOTControlZone {
	private final long radiusCoordSq;
	private final int radiusCoord;

	private final int radius;
	private final int xCoord;
	private final int zCoord;

	public GOTControlZone(double x, double y, int r) {
		radius = r;
		xCoord = GOTWaypoint.mapToWorldX(x);
		zCoord = GOTWaypoint.mapToWorldZ(y);
		radiusCoord = GOTWaypoint.mapToWorldR(radius);
		radiusCoordSq = (long) radiusCoord * radiusCoord;
	}

	public GOTControlZone(GOTAbstractWaypoint wp, int r) {
		this(wp.getX(), wp.getY(), r);
	}

	public boolean inZone(double x, double z, int extraMapRange) {
		double dx = x - xCoord;
		double dz = z - zCoord;
		double distSq = dx * dx + dz * dz;
		if (extraMapRange == 0) {
			return distSq <= radiusCoordSq;
		}
		int checkRadius = GOTWaypoint.mapToWorldR(radius + extraMapRange);
		long checkRadiusSq = (long) checkRadius * checkRadius;
		return distSq <= checkRadiusSq;
	}

	public int getRadius() {
		return radius;
	}

	public int getCoordX() {
		return xCoord;
	}

	public int getCoordZ() {
		return zCoord;
	}

	public int getRadiusCoord() {
		return radiusCoord;
	}
}
