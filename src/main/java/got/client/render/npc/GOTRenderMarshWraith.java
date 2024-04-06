package got.client.render.npc;

import got.client.model.GOTModelMarshWraith;
import got.common.entity.essos.mossovy.GOTEntityMarshWraith;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderMarshWraith extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/essos/mossovy/wraith/marshWraith.png");

	public GOTRenderMarshWraith() {
		super(new GOTModelMarshWraith(), 0.5f);
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		float f1 = 0.9375f;
		GL11.glScalef(f1, f1, f1);
		GOTEntityMarshWraith wraith = (GOTEntityMarshWraith) entity;
		if (wraith.getSpawnFadeTime() < 30) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getSpawnFadeTime() / 30.0f);
		} else if (wraith.getDeathFadeTime() > 0) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getDeathFadeTime() / 30.0f);
		}
	}
}
