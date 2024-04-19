package got.client.render.other;

import got.common.entity.other.GOTEntityPlate;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderPlate extends Render {
	private static final RenderBlocks BLOCK_RENDERER = new RenderBlocks();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityPlate plate = (GOTEntityPlate) entity;
		GL11.glEnable(32826);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
		int i = entity.getBrightnessForRender(f1);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		bindEntityTexture(entity);
		GOTRenderBlocks.renderEntityPlate(plate.getPlateBlock(), BLOCK_RENDERER);
		GL11.glPopMatrix();
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}