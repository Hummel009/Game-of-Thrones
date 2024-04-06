package got.client.render.animal;

import got.client.model.GOTModelWalrus;
import got.common.entity.animal.GOTEntityWalrus;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderWalrus extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/walrus.png");

	public GOTRenderWalrus() {
		super(new GOTModelWalrus(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityWalrus animal = (GOTEntityWalrus) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.75f, 0.75f, 0.75f);
		} else {
			GL11.glScalef(1.5f, 1.5f, 1.5f);
		}
	}
}