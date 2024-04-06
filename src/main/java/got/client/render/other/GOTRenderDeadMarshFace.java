package got.client.render.other;

import got.client.effect.GOTEntityDeadMarshFace;
import got.client.model.GOTModelMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderDeadMarshFace extends Render {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/essos/mossovy/wraith/marshWraith.png");
	private static final ModelBase MODEL = new GOTModelMarshWraith();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityDeadMarshFace face = (GOTEntityDeadMarshFace) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glEnable(32826);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, face.getFaceAlpha());
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(face.rotationYaw, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(face.rotationPitch, 0.0f, 1.0f, 0.0f);
		bindEntityTexture(face);
		MODEL.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glDisable(3042);
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}
}
