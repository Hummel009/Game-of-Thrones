package got.client.render.animal;

import got.client.model.GOTModelGiraffe;
import got.common.entity.animal.GOTEntityGiraffe;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderGiraffe extends RenderLiving {
	public static ResourceLocation texture = new ResourceLocation("got:textures/entity/animal/giraffe/giraffe.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("got:textures/entity/animal/giraffe/saddle.png");

	public GOTRenderGiraffe() {
		super(new GOTModelGiraffe(0.0f), 0.5f);
		setRenderPassModel(new GOTModelGiraffe(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityGiraffe animal = (GOTEntityGiraffe) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTEntityGiraffe) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
