package got.client.render.animal;

import got.client.model.GOTModelOryx;
import got.common.entity.animal.GOTEntityOryx;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderOryx extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/oryx.png");

	public GOTRenderOryx() {
		super(new GOTModelOryx(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityOryx animal = (GOTEntityOryx) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}
}