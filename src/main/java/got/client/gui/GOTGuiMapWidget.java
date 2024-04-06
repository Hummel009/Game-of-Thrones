package got.client.gui;

import net.minecraft.util.StatCollector;

public class GOTGuiMapWidget {
	private final int xPos;
	private final int yPos;
	private final String name;
	private final int texUBase;
	private final int texVBase;

	private int texVIndex;

	private int width;
	private boolean visible = true;

	public GOTGuiMapWidget(int x, int y, int w, String s, int u, int v) {
		xPos = x;
		yPos = y;
		width = w;
		name = s;
		texUBase = u;
		texVBase = v;
	}

	public int getMapXPos(int mapWidth) {
		return xPos < 0 ? mapWidth + xPos : xPos;
	}

	public int getMapYPos(int mapHeight) {
		return yPos < 0 ? mapHeight + yPos : yPos;
	}

	public int getTexU() {
		return texUBase;
	}

	public int getTexV() {
		return texVBase + texVIndex * width;
	}

	public String getTranslatedName() {
		return StatCollector.translateToLocal("got.gui.map.widget." + name);
	}

	public boolean isMouseOver(int mouseX, int mouseY, int mapWidth, int mapHeight) {
		return visible && mouseX >= getMapXPos(mapWidth) && mouseX < getMapXPos(mapWidth) + width && mouseY >= getMapYPos(mapHeight) && mouseY < getMapYPos(mapHeight) + width;
	}

	public void setTexVIndex(int i) {
		texVIndex = i;
	}

	public int getTexVIndex() {
		return texVIndex;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
