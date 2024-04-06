package got.client.render.animal;

import got.client.model.GOTModelManticore;
import got.common.entity.animal.GOTEntityScorpionSmall;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderManticore extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/manticore.png");

	public GOTRenderManticore() {
		super(new GOTModelManticore(), 0.5f);
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float strikeTime = ((GOTEntityScorpionSmall) entity).getStrikeTime();
		if (strikeTime > 0.0f) {
			strikeTime -= f;
		}
		return strikeTime / 20.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
	}
}