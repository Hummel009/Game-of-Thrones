package got.client.render.animal;

import got.client.model.GOTModelMidge;
import got.common.entity.animal.GOTEntityMidges;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderMidges extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/midge.png");

	private float renderTick;

	public GOTRenderMidges() {
		super(new GOTModelMidge(), 0.0f);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		renderTick = f1;
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bindEntityTexture(entity);
		mainModel.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GOTEntityMidges midges = (GOTEntityMidges) entity;
		for (GOTEntityMidges.Midge midge : midges.midges) {
			GL11.glPushMatrix();
			GL11.glTranslatef(midge.midge_prevPosX + (midge.midge_posX - midge.midge_prevPosX) * renderTick, midge.midge_prevPosY + (midge.midge_posY - midge.midge_prevPosY) * renderTick, midge.midge_prevPosZ + (midge.midge_posZ - midge.midge_prevPosZ) * renderTick);
			GL11.glRotatef(midge.midge_rotation, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(0.2f, 0.2f, 0.2f);
			mainModel.render(entity, f, f1, f2, f3, f4, f5);
			GL11.glPopMatrix();
		}
	}
}
