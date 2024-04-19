package got.client.render.other;

import got.client.GOTTextures;
import got.common.tileentity.GOTTileEntityCommandTable;
import got.common.world.genlayer.GOTGenLayerWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class GOTRenderCommandTable extends TileEntitySpecialRenderer {
	public void renderInvTable() {
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
		renderTableAt(0.0, 0.0, 0.0, viewer.posX, viewer.posZ, 0);
		bindTexture(TextureMap.locationBlocksTexture);
	}

	private void renderTableAt(double d, double d1, double d2, double viewerX, double viewerZ, int zoomExp) {
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glDisable(2896);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.1f, (float) d2 + 0.5f);
		float posX = Math.round(viewerX / GOTGenLayerWorld.SCALE) + GOTGenLayerWorld.ORIGIN_X;
		float posY = Math.round(viewerZ / GOTGenLayerWorld.SCALE) + GOTGenLayerWorld.ORIGIN_Z;
		int viewportWidth = 400;
		viewportWidth = (int) Math.round(viewportWidth * Math.pow(2.0, zoomExp));
		double radius = 0.9;
		float minX = posX - (float) viewportWidth / 2;
		float maxX = posX + (float) viewportWidth / 2;
		if (minX < 0.0f) {
			posX = (float) viewportWidth / 2;
		}
		if (maxX >= GOTGenLayerWorld.getImageWidth()) {
			posX = GOTGenLayerWorld.getImageWidth() - (float) viewportWidth / 2;
		}
		float minY = posY - (float) viewportWidth / 2;
		float maxY = posY + (float) viewportWidth / 2;
		if (minY < 0.0f) {
			posY = (float) viewportWidth / 2;
		}
		if (maxY >= GOTGenLayerWorld.getImageHeight()) {
			posY = GOTGenLayerWorld.getImageHeight() - (float) viewportWidth / 2;
		}
		double minU = (double) (posX - viewportWidth / 2) / GOTGenLayerWorld.getImageWidth();
		double maxU = (double) (posX + viewportWidth / 2) / GOTGenLayerWorld.getImageWidth();
		double minV = (double) (posY - viewportWidth / 2) / GOTGenLayerWorld.getImageHeight();
		double maxV = (double) (posY + viewportWidth / 2) / GOTGenLayerWorld.getImageHeight();
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GOTTextures.drawMap(true, -radius, radius, -radius, radius, 0.0, minU, maxU, minV, maxV, 1.0f);
		GOTTextures.drawMapOverlay(-radius, radius, -radius, radius, 0.0);
		double compassInset = radius * 0.05;
		GOTTextures.drawMapCompassBottomLeft(-radius + compassInset, radius - compassInset, -0.01, 0.15 * radius * 0.0625);
		GL11.glDisable(3553);
		Tessellator tess = Tessellator.instance;
		double rRed = radius + 0.015;
		double rBlack = rRed + 0.015;
		GL11.glTranslatef(0.0f, 0.0f, 0.01f);
		tess.startDrawingQuads();
		tess.setColorOpaque_I(-6156032);
		tess.addVertex(-rRed, rRed, 0.0);
		tess.addVertex(rRed, rRed, 0.0);
		tess.addVertex(rRed, -rRed, 0.0);
		tess.addVertex(-rRed, -rRed, 0.0);
		tess.draw();
		GL11.glTranslatef(0.0f, 0.0f, 0.01f);
		tess.startDrawingQuads();
		tess.setColorOpaque_I(-16777216);
		tess.addVertex(-rBlack, rBlack, 0.0);
		tess.addVertex(rBlack, rBlack, 0.0);
		tess.addVertex(rBlack, -rBlack, 0.0);
		tess.addVertex(-rBlack, -rBlack, 0.0);
		tess.draw();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
		GL11.glEnable(2896);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntityCommandTable table = (GOTTileEntityCommandTable) tileentity;
		renderTableAt(d, d1, d2, tileentity.xCoord + 0.5, tileentity.zCoord + 0.5, table.getZoomExp());
	}
}