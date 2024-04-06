package got.client.render.npc;

import got.client.GOTSpeechClient;
import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderGregorClegane extends GOTRenderBiped {
	private static final ResourceLocation ARMOR = new ResourceLocation("got:textures/entity/legendary/gregor_clegane_3.png");
	private static final ResourceLocation TEXTURE_ALIVE = new ResourceLocation("got:textures/entity/legendary/gregor_clegane_1.png");
	private static final ResourceLocation TEXTURE_DEAD = new ResourceLocation("got:textures/entity/legendary/gregor_clegane_2.png");

	private final ModelBiped model = new GOTModelHuman(0.6f, false);

	public GOTRenderGregorClegane() {
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

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		float height = 1.3f;
		GL11.glScalef(height, height, height);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		GOTEntityNPC legend = (GOTEntityNPC) entity;
		if (pass == 0 && legend.getEquipmentInSlot(4) == null || pass == 1 && legend.getEquipmentInSlot(3) == null) {
			setRenderPassModel(model);
			bindTexture(ARMOR);
			return 1;
		}
		return super.shouldRenderPass(legend, pass, f);
	}

	public static class Alive extends GOTRenderGregorClegane {

		@Override
		public ResourceLocation getEntityTexture(Entity entity) {
			return TEXTURE_ALIVE;
		}
	}

	public static class Dead extends GOTRenderGregorClegane {

		@Override
		public ResourceLocation getEntityTexture(Entity entity) {
			return TEXTURE_DEAD;
		}
	}
}