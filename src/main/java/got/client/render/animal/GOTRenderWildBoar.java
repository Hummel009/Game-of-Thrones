package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelBoar;
import got.common.entity.animal.GOTEntityWildBoar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWildBoar extends RenderLiving {
	public static ResourceLocation boarSkin = new ResourceLocation("got:mob/animal/boar/boar.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("got:mob/animal/boar/saddle.png");

	public GOTRenderWildBoar() {
		super(new GOTModelBoar(), 0.7f);
		setRenderPassModel(new GOTModelBoar(0.5f));
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityWildBoar boar = (GOTEntityWildBoar) entity;
		super.doRender(boar, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && GOTRenderWildBoar.isRobert(boar)) {
			func_147906_a(boar, boar.getCommandSenderName(), d, d1 + 1, d2, 64);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityWildBoar boar = (GOTEntityWildBoar) entity;
		return GOTRenderHorse.getLayeredMountTexture(boar, boarSkin);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityWildBoar boar = (GOTEntityWildBoar) entity;
		if (boar.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else if (GOTRenderWildBoar.isRobert(boar)) {
			GL11.glScalef(2.0f, 2.0f, 2.0f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTEntityWildBoar) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}

	public static boolean isRobert(GOTEntityWildBoar boar) {
		return boar.hasCustomNameTag() && "robert".equalsIgnoreCase(boar.getCustomNameTag());
	}
}
