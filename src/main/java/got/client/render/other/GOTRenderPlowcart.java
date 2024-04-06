package got.client.render.other;

import got.client.model.GOTModelPlowcart;
import got.common.entity.other.GOTEntityCart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderPlowcart extends Render {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/model/plowcart.png");
	private static final ModelBase MODEL = new GOTModelPlowcart();

	public GOTRenderPlowcart() {
		renderManager = RenderManager.instance;
		shadowSize = 0.6f;
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		doRender((GOTEntityCart) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	private void doRender(GOTEntityCart entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		setupTranslation(x, y, z);
		setupRotation(entityYaw);
		bindEntityTexture(entity);
		MODEL.render(entity, partialTicks, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	private void setupRotation(float entityYaw) {
		GL11.glRotatef(180.0f - entityYaw, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
	}

	private void setupTranslation(double x, double y, double z) {
		GL11.glTranslatef((float) x, (float) y + 1.0f, (float) z);
	}
}
