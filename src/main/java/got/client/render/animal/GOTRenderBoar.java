package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelBoar;
import got.common.entity.animal.GOTEntityBoar;
import got.common.entity.other.GOTNPCMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderBoar extends RenderLiving {
	public static ResourceLocation boarSkin = new ResourceLocation("got:textures/entity/animal/boar/boar.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("got:textures/entity/animal/boar/saddle.png");

	public GOTRenderBoar() {
		super(new GOTModelBoar(), 0.7f);
		setRenderPassModel(new GOTModelBoar(0.5f));
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityBoar boar = (GOTEntityBoar) entity;
		super.doRender(boar, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && isRobert(boar)) {
			func_147906_a(boar, boar.getCommandSenderName(), d, d1 + 1, d2, 64);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTNPCMount boar = (GOTNPCMount) entity;
		return GOTRenderHorse.getLayeredMountTexture(boar, boarSkin);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityBoar boar = (GOTEntityBoar) entity;
		if (boar.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else if (isRobert(boar)) {
			GL11.glScalef(2.0f, 2.0f, 2.0f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTNPCMount) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}

	public static boolean isRobert(GOTEntityBoar boar) {
		return boar.hasCustomNameTag() && "robert".equalsIgnoreCase(boar.getCustomNameTag());
	}
}
