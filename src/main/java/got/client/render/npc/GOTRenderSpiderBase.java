package got.client.render.npc;

import got.client.GOTTextures;
import got.client.model.GOTModelSpider;
import got.client.render.other.GOTGlowingEyes;
import got.client.render.other.GOTNPCRendering;
import got.common.entity.other.GOTEntitySpiderBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class GOTRenderSpiderBase extends RenderLiving {
	public GOTModelSpider eyesModel = new GOTModelSpider(0.55f);

	public GOTRenderSpiderBase() {
		super(new GOTModelSpider(0.5f), 1.0f);
		setRenderPassModel(new GOTModelSpider(0.5f));
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && ((GOTEntitySpiderBase) entity).hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			GOTNPCRendering.renderHiredIcon(entity, d, d1 + 0.5, d2);
			GOTNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5, d2);
		}
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = ((GOTEntitySpiderBase) entity).getSpiderScaleAmount();
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		ResourceLocation eyes1 = GOTTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{39, 10}, {42, 11}, {44, 11}, {47, 10}}, 2, 2);
		ResourceLocation eyes2 = GOTTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{41, 8}, {42, 9}, {45, 9}, {46, 8}}, 1, 1);
		GOTGlowingEyes.renderGlowingEyes(entity, eyes1, eyesModel, f, f1, f2, f3, f4, f5);
		GOTGlowingEyes.renderGlowingEyes(entity, eyes2, eyesModel, f, f1, f2, f3, f4, f5);
	}
}
