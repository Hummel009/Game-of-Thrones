package got.common.faction;

public class GOTMapRegion {
	private final int mapX;
	private final int mapY;
	private final int radius;

	public GOTMapRegion(int x, int y, int r) {
		mapX = x;
		mapY = y;
		radius = r;
	}

	public int getMapX() {
		return mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public int getRadius() {
		return radius;
	}
}