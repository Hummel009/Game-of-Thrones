package got.client.gui;

import net.minecraft.util.StatCollector;

public class GOTGuiMapWidget {
	public int xPos;
	public int yPos;
	public int width;
	public String name;
	public int texUBase;
	public int texVBase;
	private int texVIndex;
	public boolean visible = true;

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
}
