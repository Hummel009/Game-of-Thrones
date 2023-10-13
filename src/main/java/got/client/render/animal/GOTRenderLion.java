package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelLion;
import got.common.entity.animal.GOTEntityLionBase;
import got.common.entity.animal.GOTEntityLioness;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderLion extends RenderLiving {
	public static ResourceLocation textureLion = new ResourceLocation("got:textures/entity/animal/lion/lion.png");
	public static ResourceLocation textureLioness = new ResourceLocation("got:textures/entity/animal/lion/lioness.png");

	public GOTRenderLion() {
		super(new GOTModelLion(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityLionBase lion = (GOTEntityLionBase) entity;
		return lion instanceof GOTEntityLioness ? textureLioness : textureLion;
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
