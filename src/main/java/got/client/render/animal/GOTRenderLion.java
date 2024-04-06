package got.client.render.animal;

import got.client.model.GOTModelLion;
import got.common.entity.animal.GOTEntityLionBase;
import got.common.entity.animal.GOTEntityLioness;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderLion extends RenderLiving {
	public static final ResourceLocation TEXTURE_LION = new ResourceLocation("got:textures/entity/animal/lion/lion.png");
	public static final ResourceLocation TEXTURE_LIONESS = new ResourceLocation("got:textures/entity/animal/lion/lioness.png");

	public GOTRenderLion() {
		super(new GOTModelLion(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityLionBase lion = (GOTEntityLionBase) entity;
		return lion instanceof GOTEntityLioness ? TEXTURE_LIONESS : TEXTURE_LION;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityLionBase animal = (GOTEntityLionBase) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}
}
