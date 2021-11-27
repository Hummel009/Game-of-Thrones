package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelRhino;
import got.common.entity.animal.GOTEntityWoolyRhino;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWoolyRhino extends RenderLiving {
	public static ResourceLocation rhinoTexture = new ResourceLocation("got:mob/animal/rhino/wooly.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("got:mob/animal/rhino/saddle.png");

	public GOTRenderWoolyRhino() {
		super(new GOTModelRhino(), 0.5f);
		setRenderPassModel(new GOTModelRhino(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityWoolyRhino rhino = (GOTEntityWoolyRhino) entity;
		return GOTRenderHorse.getLayeredMountTexture(rhino, rhinoTexture);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityWoolyRhino animal = (GOTEntityWoolyRhino) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTEntityWoolyRhino) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
