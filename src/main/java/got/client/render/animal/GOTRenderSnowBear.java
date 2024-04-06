package got.client.render.animal;

import got.client.model.GOTModelBear;
import got.common.entity.animal.GOTEntitySnowBear;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderSnowBear extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/polarbear.png");

	public GOTRenderSnowBear() {
		super(new GOTModelBear(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntitySnowBear animal = (GOTEntitySnowBear) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.65f, 0.65f, 0.65f);
		} else {
			GL11.glScalef(1.3f, 1.3f, 1.3f);
		}
	}
}