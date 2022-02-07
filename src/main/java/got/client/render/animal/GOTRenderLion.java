package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelLion;
import got.common.entity.animal.*;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderLion extends RenderLiving {
	private static ResourceLocation textureLion = new ResourceLocation("got:textures/entity/animal/lion/lion.png");
	private static ResourceLocation textureLioness = new ResourceLocation("got:textures/entity/animal/lion/lioness.png");

	public GOTRenderLion() {
		super(new GOTModelLion(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityLionBase lion = (GOTEntityLionBase) entity;
		return lion instanceof GOTEntityLioness ? getTextureLioness() : getTextureLion();
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

	public static ResourceLocation getTextureLion() {
		return textureLion;
	}

	public static ResourceLocation getTextureLioness() {
		return textureLioness;
	}

	public static void setTextureLion(ResourceLocation textureLion) {
		GOTRenderLion.textureLion = textureLion;
	}

	public static void setTextureLioness(ResourceLocation textureLioness) {
		GOTRenderLion.textureLioness = textureLioness;
	}
}
