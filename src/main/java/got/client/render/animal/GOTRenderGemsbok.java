package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelGemsbok;
import got.common.entity.animal.GOTEntityGemsbok;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderGemsbok extends RenderLiving {
	public static ResourceLocation texture = new ResourceLocation("got:mob/animal/gemsbok.png");

	public GOTRenderGemsbok() {
		super(new GOTModelGemsbok(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityGemsbok animal = (GOTEntityGemsbok) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}
}
