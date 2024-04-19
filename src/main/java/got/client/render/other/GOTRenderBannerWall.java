package got.client.render.other;

import got.client.model.GOTModelBannerWall;
import got.common.entity.other.GOTEntityBannerWall;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderBannerWall extends Render {
	private static final GOTModelBannerWall MODEL = new GOTModelBannerWall();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityBannerWall banner = (GOTEntityBannerWall) entity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		GL11.glRotatef(f, 0.0f, 1.0f, 0.0f);
		bindTexture(GOTRenderBanner.STAND_TEXTURE);
		MODEL.renderPost(0.0625f);
		bindTexture(GOTRenderBanner.getBannerTexture(banner.getBannerType()));
		MODEL.renderBanner(0.0625f);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
		if (RenderManager.debugBoundingBox) {
			GL11.glPushMatrix();
			GL11.glDepthMask(false);
			GL11.glDisable(3553);
			GL11.glDisable(2896);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			AxisAlignedBB aabb = banner.boundingBox.copy().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			RenderGlobal.drawOutlinedBoundingBox(aabb, 16777215);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glEnable(3553);
			GL11.glEnable(2896);
			GL11.glEnable(2884);
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return GOTRenderBanner.STAND_TEXTURE;
	}
}