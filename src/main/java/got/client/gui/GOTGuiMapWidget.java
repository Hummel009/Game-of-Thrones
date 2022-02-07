package got.client.gui;

import net.minecraft.util.StatCollector;

public class GOTGuiMapWidget {
	private int xPos;
	private int yPos;
	private int width;
	private String name;
	private int texUBase;
	private int texVBase;
	private int texVIndex;
	private boolean visible = true;

	public GOTGuiMapWidget(int x, int y, int w, String s, int u, int v) {
		xPos = x;
		yPos = y;
		setWidth(w);
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
		return texVBase + texVIndex * getWidth();
	}

	public String getTranslatedName() {
		return StatCollector.translateToLocal("got.gui.map.widget." + name);
	}

	public int getWidth() {
		return width;
	}

	public boolean isMouseOver(int mouseX, int mouseY, int mapWidth, int mapHeight) {
		return isVisible() && mouseX >= getMapXPos(mapWidth) && mouseX < getMapXPos(mapWidth) + getWidth() && mouseY >= getMapYPos(mapHeight) && mouseY < getMapYPos(mapHeight) + getWidth();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setTexVIndex(int i) {
		texVIndex = i;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
