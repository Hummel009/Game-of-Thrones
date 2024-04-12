package got.client.gui;

import got.common.GOTDimension;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiDownloadTerrain extends GuiDownloadTerrain {
	private final GOTGuiMap mapGui = new GOTGuiMap();
	private final GOTGuiRendererMap mapRenderer = new GOTGuiRendererMap();

	private int tickCounter;

	public GOTGuiDownloadTerrain(NetHandlerPlayClient handler) {
		super(handler);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		int dimension = mc.thePlayer.dimension;
		if (dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) {
			drawBackground(0);
			GL11.glEnable(3008);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			double x = GOTWaypoint.KINGS_LANDING.getImgX();
			double y = GOTWaypoint.KINGS_LANDING.getImgY();
			mapRenderer.setPrevMapX(x);
			mapRenderer.setMapX(x);
			mapRenderer.setPrevMapY(y);
			mapRenderer.setMapY(y);
			mapRenderer.setZoomExp(-1.0f);
			mapRenderer.setZoomStable((float) Math.pow(2.0, -1.00000001192092896));
			int x0 = 0;
			int x1 = width;
			int y0 = 0;
			int y1 = height;
			mapRenderer.renderMap(mapGui, f, x0, y0, x1, y1);
			mapRenderer.renderVignettes(this, zLevel, 1, x0, y0, x1, y1);
			GL11.glDisable(3042);
			String titleExtra = new String[]{"", ".", "..", "..."}[tickCounter / 10 % 4];
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