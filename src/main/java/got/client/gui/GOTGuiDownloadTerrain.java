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
	private static final GOTGuiMap MAP_GUI = new GOTGuiMap();
	private static final GOTGuiRendererMap MAP_RENDERER = new GOTGuiRendererMap();

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
			MAP_RENDERER.setPrevMapX(x);
			MAP_RENDERER.setMapX(x);
			MAP_RENDERER.setPrevMapY(y);
			MAP_RENDERER.setMapY(y);
			MAP_RENDERER.setZoomExp(-1.0f);
			MAP_RENDERER.setZoomStable((float) Math.pow(2.0, -1.00000001192092896));
			int x0 = 0;
			int x1 = width;
			int y0 = 0;
			int y1 = height;
			MAP_RENDERER.renderMap(MAP_GUI, f, x0, y0, x1, y1);
			MAP_RENDERER.renderVignettes(this, zLevel, 1, x0, y0, x1, y1);
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
		MAP_GUI.setWorldAndResolution(mc, i, j);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
	}
}