package got.common.faction;

import got.common.world.map.GOTWaypoint;

public class GOTControlZone {
	public int mapX;
	public int mapY;
	public int radius;
	public int xCoord;
	public int zCoord;
	public int radiusCoord;
	public long radiusCoordSq;

	public GOTControlZone(GOTWaypoint wp, int r) {
		this(wp.getX(), wp.getY(), r);
	}

	public GOTControlZone(int x, int y, int r) {
		mapX = x;
		mapY = y;
		radius = r;
		xCoord = GOTWaypoint.mapToWorldX(mapX);
		zCoord = GOTWaypoint.mapToWorldZ(mapY);
		radiusCoord = GOTWaypoint.mapToWorldR(radius);
		radiusCoordSq = (long) radiusCoord * (long) radiusCoord;
	}

	public boolean intersectsWith(GOTControlZone other, int extraMapRadius) {
		double dx = other.xCoord - xCoord;
		double dz = other.zCoord - zCoord;
		double distSq = dx * dx + dz * dz;
		double r12 = radiusCoord + other.radiusCoord + GOTWaypoint.mapToWorldR(extraMapRadius * 2);
		double r12Sq = r12 * r12;
		return distSq <= r12Sq;
	}

	public boolean inZone(double x, double y, double z, int extraMapRange) {
		double dx = x - xCoord;
		double dz = z - zCoord;
		double distSq = dx * dx + dz * dz;
		if (extraMapRange == 0) {
			return distSq <= radiusCoordSq;
		}
		int checkRadius = GOTWaypoint.mapToWorldR(radius + extraMapRange);
		long checkRadiusSq = (long) checkRadius * (long) checkRadius;
		return distSq <= checkRadiusSq;
	}
}
