package got.client.render.npc;

import got.client.GOTSpeechClient;
import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public abstract class GOTRenderLancelLannister extends GOTRenderBiped {
	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("got:textures/entity/legendary/lancel_lannister_1.png");
	private static final ResourceLocation TEXTURE_RELIGIOUS = new ResourceLocation("got:textures/entity/legendary/lancel_lannister_2.png");

	protected GOTRenderLancelLannister() {
		super(new GOTModelHuman(), 0.5f);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityNPC legend = (GOTEntityNPC) entity;
		super.doRender(legend, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && !GOTSpeechClient.hasSpeech(legend)) {
			func_147906_a(legend, legend.getCommandSenderName(), d, d1 + 0.15, d2, 64);
		}
	}

	public static class Normal extends GOTRenderLancelLannister {
		@Override
		public ResourceLocation getEntityTexture(Entity entity) {
			return TEXTURE_NORMAL;
		}
	}

	public static class Religious extends GOTRenderLancelLannister {
		@Override
		public ResourceLocation getEntityTexture(Entity entity) {
			return TEXTURE_RELIGIOUS;
		}
	}
}