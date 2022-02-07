package got.common.faction;

import got.common.world.map.GOTWaypoint;

public class GOTControlZone {
	private int mapY;
	private int radius;
	private int xCoord;
	private int zCoord;
	private int radiusCoord;
	private long radiusCoordSq;

	public GOTControlZone(GOTWaypoint wp, int r) {
		this(wp.getX(), wp.getY(), r);
	}

	public GOTControlZone(int x, int y, int r) {
		int mapX = x;
		mapY = y;
		setRadius(r);
		setxCoord(GOTWaypoint.mapToWorldX(mapX));
		setzCoord(GOTWaypoint.mapToWorldZ(mapY));
		setRadiusCoord(GOTWaypoint.mapToWorldR(getRadius()));
		radiusCoordSq = (long) getRadiusCoord() * (long) getRadiusCoord();
	}

	public int getRadius() {
		return radius;
	}

	public int getRadiusCoord() {
		return radiusCoord;
	}

	public int getxCoord() {
		return xCoord;
	}

	public int getzCoord() {
		return zCoord;
	}

	public boolean intersectsWith(GOTControlZone other, int extraMapRadius) {
		double dx = other.getxCoord() - getxCoord();
		double dz = other.getzCoord() - getzCoord();
		double distSq = dx * dx + dz * dz;
		double r12 = getRadiusCoord() + other.getRadiusCoord() + GOTWaypoint.mapToWorldR(extraMapRadius * 2);
		double r12Sq = r12 * r12;
		return distSq <= r12Sq;
	}

	public boolean inZone(double x, double y, double z, int extraMapRange) {
		double dx = x - getxCoord();
		double dz = z - getzCoord();
		double distSq = dx * dx + dz * dz;
		if (extraMapRange == 0) {
			return distSq <= radiusCoordSq;
		}
		int checkRadius = GOTWaypoint.mapToWorldR(getRadius() + extraMapRange);
		long checkRadiusSq = (long) checkRadius * (long) checkRadius;
		return distSq <= checkRadiusSq;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setRadiusCoord(int radiusCoord) {
		this.radiusCoord = radiusCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public void setzCoord(int zCoord) {
		this.zCoord = zCoord;
	}
}
