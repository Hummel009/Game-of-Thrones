package got.common.world.map;

import got.common.world.biome.GOTBiome;
import net.minecraft.util.StatCollector;

public enum GOTMapLabels {
	WESTEROS("westeros", 650, 1350, 30.0f, 67, -6.0f, -1.0f), ESSOS("essos", 2200, 1650, 50.0f, -10, -6.0f, -1.0f), SOTHORYOS("sothoryos", 2400, 3600, 40.0f, 60, -6.0f, -1.0f), ULTHOS("ulthos", 4144, 3540, 50.0f, 10, -6.0f, -1.0f), IBBEN("ibben", 2700, 1000, 15.0f, 0, -6.0f, -1.0f), LANG("lang", 3550, 2500, 15.0f, 67, -6.0f, 1.0f), NARROW("narrow", 1050, 1600, 9.0f, 70, -6.0f, 2.0f), JADE("jade", 3050, 3000, 10.0f, 0, -6.0f, -1.0f), SUMMER("summer", 2150, 2530, 15.0f, -10, -6.0f, -1.0f), SUNSET("sunset", 100, 1500, 15.0f, -70, -6.0f, -1.0f), SHIVERING("shivering", 2250, 500, 40.0f, 0, -6.0f, -1.0f);

	private int posX;
	private int posY;
	private float scale;
	private int angle;
	private float minZoom;
	private float maxZoom;
	private GOTBiome biome;
	private String labelName;

	GOTMapLabels(Object label, int x, int y, float f, int r, float z1, float z2) {
		posX = x;
		posY = y;
		scale = f;
		angle = r;
		minZoom = z1;
		maxZoom = z2;
		if (label instanceof GOTBiome) {
			biome = (GOTBiome) label;
		} else {
			labelName = (String) label;
		}
	}

	public static GOTMapLabels[] allMapLabels() {
		return values();
	}

	public int getAngle() {
		return angle;
	}

	public String getDisplayName() {
		if (labelName != null) {
			return StatCollector.translateToLocal("got.map." + labelName);
		}
		return biome.getBiomeDisplayName();
	}

	public float getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
	}

	public float getMinZoom() {
		return minZoom;
	}

	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public float getScale() {
		return scale;
	}
}
