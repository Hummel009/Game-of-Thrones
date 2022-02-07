package got.common.faction;

public class GOTMapRegion {
	private int mapX;
	private int mapY;
	private int radius;

	public GOTMapRegion(int x, int y, int r) {
		setMapX(x);
		setMapY(y);
		setRadius(r);
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

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
