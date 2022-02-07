package got.client.render.npc;

import org.lwjgl.opengl.GL11;

import got.client.GOTSpeechClient;
import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderLegendaryNPCLayered extends GOTRenderBiped {
	private String name;
	private Boolean enableCape;
	private float height;
	private ModelBiped model = new GOTModelHuman(0.6f, false);

	public GOTRenderLegendaryNPCLayered(String texture) {
		super(new GOTModelHuman(), 0.5f);
		name = texture;
		enableCape = false;
		height = 1.0f;
	}

	public GOTRenderLegendaryNPCLayered(String texture, Boolean hasCape) {
		super(new GOTModelHuman(), 0.5f);
		name = texture;
		enableCape = hasCape;
		height = 1.0f;
	}

	public GOTRenderLegendaryNPCLayered(String texture, float size) {
		super(new GOTModelHuman(), 0.5f);
		name = texture;
		enableCape = false;
		height = size;
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
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/legendary/" + name + "_1.png");
	}

	private ResourceLocation getSecondLayerTexture() {
		return new ResourceLocation("got:textures/entity/legendary/" + name + "_2.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(height, height, height);
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		super.renderEquippedItems(entity, f);
		if (enableCape) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 0.0f, 0.125f);
			GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
			bindTexture(new ResourceLocation("got:textures/entity/legendary/" + name + "_cape.png"));
			getCapeModel().renderCloak(0.0625f);
			GL11.glPopMatrix();
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		GOTEntityNPC legend = (GOTEntityNPC) entity;
		if ((pass == 0 && legend.getEquipmentInSlot(4) == null) || (pass == 1 && legend.getEquipmentInSlot(3) == null)) {
			setRenderPassModel(model);
			bindTexture(getSecondLayerTexture());
			return 1;
		}
		return super.shouldRenderPass(legend, pass, f);
	}
}