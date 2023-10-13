package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.GOTDimension;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.StatCollector;

public class GOTGuiDownloadTerrain extends GuiDownloadTerrain {
	public GOTGuiMap mapGui = new GOTGuiMap();
	public GOTGuiRendererMap mapRenderer = new GOTGuiRendererMap();
	public int tickCounter;

	public GOTGuiDownloadTerrain(NetHandlerPlayClient handler) {
		super(handler);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		int dimension = mc.thePlayer.dimension;
		if (dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
			drawBackground(0);
			GL11.glEnable(3008);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			mapRenderer.prevMapX = mapRenderer.mapX = GOTWaypoint.KINGS_LANDING.getX();
			mapRenderer.prevMapY = mapRenderer.mapY = GOTWaypoint.KINGS_LANDING.getY();
			mapRenderer.zoomExp = -1.0f;
			mapRenderer.zoomStable = (float) Math.pow(2.0, -1.00000001192092896);
			int x0 = 0;
			int x1 = width;
			int y0 = 0;
			int y1 = height;
			mapRenderer.renderMap(this, mapGui, f, x0, y0, x1, y1);
			mapRenderer.renderVignettes(this, zLevel, 1, x0, y0, x1, y1);
			GL11.glDisable(3042);
			String titleExtra = new String[] { "", ".", "..", "..." }[tickCounter / 10 % 4];
			String s = StatCollector.translateToLocal("got.loading");
			drawCenteredString(fontRendererObj, s + titleExtra, width / 2, height / 2 - 50, 16777215);
		} else {
			super.drawScreen(i, j, f);
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapGui.setWorldAndResolution(mc, i, j);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
	}
}