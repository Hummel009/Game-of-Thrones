package got.client.render.animal;

import got.client.model.GOTModelScorpion;
import got.common.entity.animal.GOTEntityDesertScorpion;
import got.common.entity.animal.GOTEntityScorpionBig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderScorpion extends RenderLiving {
	private static final ResourceLocation JUNGLE_TEXTURE = new ResourceLocation("got:textures/entity/animal/scorpion/jungle.png");
	private static final ResourceLocation DESERT_TEXTURE = new ResourceLocation("got:textures/entity/animal/scorpion/desert.png");

	public GOTRenderScorpion() {
		super(new GOTModelScorpion(), 1.0f);
		setRenderPassModel(new GOTModelScorpion());
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof GOTEntityDesertScorpion) {
			return DESERT_TEXTURE;
		}
		return JUNGLE_TEXTURE;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float strikeTime = ((GOTEntityScorpionBig) entity).getStrikeTime();
		if (strikeTime > 0.0f) {
			strikeTime -= f;
		}
		return strikeTime / 20.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = ((GOTEntityScorpionBig) entity).getScorpionScaleAmount();
		GL11.glScalef(scale, scale, scale);
	}
}