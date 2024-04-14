package got.client.render.animal;

import got.client.model.GOTModelDragon;
import got.common.entity.dragon.GOTDragonLifeStageHelper;
import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import static org.lwjgl.opengl.GL11.*;

public class GOTRenderDragon extends RenderLiving {
	private static final ResourceLocation DISSOLVE = new ResourceLocation("got:textures/entity/animal/dragon/dissolve.png");
	private static final ResourceLocation EGG_TEXTURE = new ResourceLocation("got:textures/entity/animal/dragon/egg.obj");
	private static final IModelCustom EGG = AdvancedModelLoader.loadModel(EGG_TEXTURE);

	private GOTModelDragon model;

	public GOTRenderDragon() {
		super(null, 2);
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
		GOTEntityDragon dragon = (GOTEntityDragon) entity;
		mainModel = renderPassModel = model = new GOTModelDragon();
		passSpecialRenderSuper(dragon, x, y, z);
		if (dragon.isEgg()) {
			doRenderEgg(dragon, x, y, z, partialTicks);
		} else {
			super.doRender(dragon, x, y, z, yaw, partialTicks);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return model.getBodyTexture();
	}

	@Override
	public void passSpecialRender(EntityLivingBase entity, double x, double y, double z) {
	}

	private void passSpecialRenderSuper(EntityLivingBase par1EntityLiving, double par2, double par4, double par6) {
		super.passSpecialRender(par1EntityLiving, par2, par4, par6);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float partialTicks) {
		GOTEntityDragon dragon = (GOTEntityDragon) entity;
		float scale = dragon.getScale() * 0.8f;
		glScalef(scale, scale, scale);
	}

	private void doRenderEgg(GOTEntityDragon dragon, double x, double y, double z, float partialTicks) {
		GOTDragonLifeStageHelper lifeStage = dragon.getLifeStageHelper();
		float tickX = lifeStage.getEggWiggleX();
		float tickZ = lifeStage.getEggWiggleZ();
		float rotX = 0;
		float rotZ = 0;
		if (tickX > 0) {
			rotX = (float) Math.sin(tickX - partialTicks) * 8;
		}
		if (tickZ > 0) {
			rotZ = (float) Math.sin(tickZ - partialTicks) * 8;
		}
		glPushMatrix();
		glTranslatef((float) x, (float) y, (float) z);
		glRotatef(rotX, 1, 0, 0);
		glRotatef(rotZ, 0, 0, 1);
		bindTexture(model.getEggTexture());
		EGG.renderAll();
		glPopMatrix();
	}

	@Override
	public void renderModel(EntityLivingBase entity, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
		GOTEntityDragon dragon = (GOTEntityDragon) entity;
		model.setRenderPass(-1);
		if (dragon.getDeathTime() > 0) {
			float alpha = dragon.getDeathTime() / (float) dragon.getMaxDeathTime();
			glDepthFunc(GL_LEQUAL);
			glEnable(GL_ALPHA_TEST);
			glAlphaFunc(GL_GREATER, alpha);
			bindTexture(DISSOLVE);
			model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
			glAlphaFunc(GL_GREATER, 0.1f);
			glDepthFunc(GL_EQUAL);
		}
		super.renderModel(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
	}

	@Override
	public void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4) {
		glRotatef(180 - par3, 0, 1, 0);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float scale) {
		GOTEntityDragon dragon = (GOTEntityDragon) entity;
		model.setRenderPass(pass);
		switch (pass) {
			case 0:
				if (dragon.isSaddled()) {
					bindTexture(model.getSaddleTexture());
					return 1;
				}
				break;
			case 1:
				bindTexture(model.getGlowTexture());
				glEnable(GL_BLEND);
				glBlendFunc(GL_ONE, GL_ONE);
				glDisable(GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 65536, 0);
				return 1;
			case 2:
				glEnable(GL_LIGHTING);
				glDisable(GL_BLEND);
				break;
		}
		return -1;
	}

	@SuppressWarnings("unused")
	public GOTModelDragon getModel() {
		return model;
	}
}
