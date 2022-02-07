package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.client.GOTTextures;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.ResourceLocation;

public class GOTGuiRendererMap {
	private static ResourceLocation vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
	private float prevMapX;
	private float mapX;
	private float prevMapY;
	private float mapY;
	private float zoomExp;
	private float zoomStable;
	private boolean sepia = false;

	public float getMapX() {
		return mapX;
	}

	public float getMapY() {
		return mapY;
	}

	public float getPrevMapX() {
		return prevMapX;
	}

	public float getPrevMapY() {
		return prevMapY;
	}

	public float getZoomExp() {
		return zoomExp;
	}

	public float getZoomStable() {
		return zoomStable;
	}

	public void renderMap(GuiScreen gui, GOTGuiMap mapGui, float f) {
		this.renderMap(gui, mapGui, f, 0, 0, gui.width, gui.height);
	}

	public void renderMap(GuiScreen gui, GOTGuiMap mapGui, float f, int x0, int y0, int x1, int y1) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int oceanColor = GOTTextures.getMapOceanColor(sepia);
		Gui.drawRect(x0, y0, x1, y1, oceanColor);
		float zoom = (float) Math.pow(2.0, getZoomExp());
		float mapPosX = getPrevMapX() + (getMapX() - getPrevMapX()) * f;
		float mapPosY = getPrevMapY() + (getMapY() - getPrevMapY()) * f;
		mapGui.setFakeMapProperties(mapPosX, mapPosY, zoom, getZoomExp(), getZoomStable());
		int[] statics = GOTGuiMap.setFakeStaticProperties(x1 - x0, y1 - y0, x0, x1, y0, y1);
		mapGui.setEnableZoomOutWPFading(false);
		mapGui.renderMapAndOverlay(sepia, 1.0f, true);
		mapGui.renderRoads(false);
		mapGui.renderWaypoints(GOTWaypoint.listAllWaypoints(), 0, 0, 0, false, true);
		GOTGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
	}

	public void renderVignette(GuiScreen gui, double zLevel) {
		this.renderVignette(gui, zLevel, 0, 0, gui.width, gui.height);
	}

	public void renderVignette(GuiScreen gui, double zLevel, int x0, int y0, int x1, int y1) {
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(771, 769, 1, 0);
		float alpha = 1.0f;
		GL11.glColor4f(alpha, alpha, alpha, 1.0f);
		gui.mc.getTextureManager().bindTexture(vignetteTexture);
		double u0 = (double) x0 / (double) gui.width;
		double u1 = (double) x1 / (double) gui.width;
		double v0 = (double) y0 / (double) gui.height;
		double v1 = (double) y1 / (double) gui.height;
		double z = zLevel;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x0, y1, z, u0, v1);
		tessellator.addVertexWithUV(x1, y1, z, u1, v1);
		tessellator.addVertexWithUV(x1, y0, z, u1, v0);
		tessellator.addVertexWithUV(x0, y0, z, u0, v0);
		tessellator.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	}

	public void renderVignettes(GuiScreen gui, double zLevel, int count) {
		for (int l = 0; l < count; ++l) {
			this.renderVignette(gui, zLevel);
		}
	}

	public void renderVignettes(GuiScreen gui, double zLevel, int count, int x0, int y0, int x1, int y1) {
		for (int l = 0; l < count; ++l) {
			this.renderVignette(gui, zLevel, x0, y0, x1, y1);
		}
	}

	public float setMapX(float mapX) {
		this.mapX = mapX;
		return mapX;
	}

	public float setMapY(float mapY) {
		this.mapY = mapY;
		return mapY;
	}

	public void setPrevMapX(float prevMapX) {
		this.prevMapX = prevMapX;
	}

	public void setPrevMapY(float prevMapY) {
		this.prevMapY = prevMapY;
	}

	public void setSepia(boolean flag) {
		sepia = flag;
	}

	public void setZoomExp(float zoomExp) {
		this.zoomExp = zoomExp;
	}

	public void setZoomStable(float zoomStable) {
		this.zoomStable = zoomStable;
	}

	public void updateTick() {
		setPrevMapX(getMapX());
		setPrevMapY(getMapY());
	}
}
