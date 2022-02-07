package got.client.render.other;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelPlowcart;
import got.common.entity.other.GOTEntityCart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderPlowcart extends Render {
	private static ResourceLocation TEXTURE = new ResourceLocation("got:textures/model/plowcart.png");
	private ModelBase model = new GOTModelPlowcart();

	public GOTRenderPlowcart() {
		renderManager = RenderManager.instance;
		shadowSize = 0.6f;
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((GOTEntityCart) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(GOTEntityCart entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		setupTranslation(x, y, z);
		setupRotation(entityYaw);
		bindEntityTexture(entity);
		model.render(entity, partialTicks, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	public void setupRotation(float entityYaw) {
		GL11.glRotatef(180.0f - entityYaw, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
	}

	public void setupTranslation(double x, double y, double z) {
		GL11.glTranslatef((float) x, (float) y + 1.0f, (float) z);
	}
}
