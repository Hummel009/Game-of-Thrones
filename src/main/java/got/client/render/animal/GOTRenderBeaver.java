package got.client.render.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelBeaver;
import got.common.entity.animal.GOTEntityBeaver;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GOTRenderBeaver extends RenderLiving {
	public GOTRenderBeaver() {
		super(new GOTModelBeaver(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/" + (isHtf(entity) ? "htf" : "beaver") + ".png");
	}

	public static boolean isHtf(Entity entity) {
		GOTEntityBeaver boar = (GOTEntityBeaver) entity;
		return boar.hasCustomNameTag() && "умейка".equalsIgnoreCase(boar.getCustomNameTag());
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityBeaver animal = (GOTEntityBeaver) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}
}