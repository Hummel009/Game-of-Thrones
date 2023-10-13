package got.client.render.npc;

import org.lwjgl.opengl.GL11;

import got.client.GOTSpeechClient;
import got.client.model.GOTModelTyrionLannister;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderTyrionLannister extends GOTRenderBiped {
	public GOTRenderTyrionLannister() {
		super(new GOTModelTyrionLannister(), 0.5f);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityNPC legend = (GOTEntityNPC) entity;
		super.doRender(legend, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && !GOTSpeechClient.hasSpeech(legend)) {
			func_147906_a(legend, legend.getCommandSenderName(), d, d1 + 0.15, d2, 64);
		}
	}

	@Override
	public void func_82421_b() {
		field_82423_g = new GOTModelTyrionLannister(1.0f);
		field_82425_h = new GOTModelTyrionLannister(0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/legendary/tyrion_lannister.png");
	}

	@Override
	public float getHeldItemYTranslation() {
		return 0.075f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(0.75f, 0.75f, 0.75f);
	}
}
