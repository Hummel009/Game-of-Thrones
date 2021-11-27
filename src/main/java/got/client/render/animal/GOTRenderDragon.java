package got.client.render.animal;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import got.client.model.GOTModelDragon;
import got.common.entity.dragon.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

public class GOTRenderDragon extends RenderLiving {
	public static boolean updateModel;
	public Map<GOTDragonBreed, GOTModelDragon> stages = new HashMap<>();
	public ResourceLocation dissolve = new ResourceLocation("got:mob/animal/dragon/dissolve.png");
	public ResourceLocation eggTexture = new ResourceLocation("got:mob/animal/dragon/egg.obj");
	public IModelCustom egg = AdvancedModelLoader.loadModel(eggTexture);
	public GOTModelDragon model;

	public GOTRenderDragon() {
		super(null, 2);
		initBreedModels();
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
		doRender((GOTEntityDragon) entity, x, y, z, yaw, partialTicks);
	}

	public void doRender(GOTEntityDragon dragon, double x, double y, double z, float yaw, float partialTicks) {
		setModel(dragon.getBreed());
		passSpecialRender2(dragon, x, y, z);
		if (dragon.isEgg()) {
			renderEgg(dragon, x, y, z, yaw, partialTicks);
		} else {
			super.doRender(dragon, x, y, z, yaw, partialTicks);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((GOTEntityDragon) entity);
	}

	public ResourceLocation getEntityTexture(GOTEntityDragon dragon) {
		return model.bodyTexture;
	}

	public void initBreedModels() {
		stages.clear();
		for (GOTDragonBreed breed : GOTDragonBreedRegistry.getInstance().getBreeds()) {
			stages.put(breed, new GOTModelDragon(breed));
		}
	}

	@Override
	public void passSpecialRender(EntityLivingBase entity, double x, double y, double z) {
	}

	public void passSpecialRender2(EntityLivingBase par1EntityLiving, double par2, double par4, double par6) {
		super.passSpecialRender(par1EntityLiving, par2, par4, par6);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float partialTicks) {
		preRenderCallback((GOTEntityDragon) entity, partialTicks);
	}

	public void preRenderCallback(GOTEntityDragon dragon, float partialTicks) {
		float scale = dragon.getScale() * 0.8f;
		glScalef(scale, scale, scale);
	}

	public void renderEgg(GOTEntityDragon dragon, double x, double y, double z, float pitch, float partialTicks) {
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
		egg.renderAll();
		glPopMatrix();
	}

	@Override
	public void renderModel(EntityLivingBase entity, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
		renderModel((GOTEntityDragon) entity, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
	}

	public void renderModel(GOTEntityDragon dragon, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
		model.renderPass = -1;
		if (dragon.getDeathTime() > 0) {
			float alpha = dragon.getDeathTime() / (float) dragon.getMaxDeathTime();
			glDepthFunc(GL_LEQUAL);
			glEnable(GL_ALPHA_TEST);
			glAlphaFunc(GL_GREATER, alpha);
			bindTexture(dissolve);
			model.render(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
			glAlphaFunc(GL_GREATER, 0.1f);
			glDepthFunc(GL_EQUAL);
		}
		super.renderModel(dragon, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
	}

	@Override
	public void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4) {
		rotateCorpse((GOTEntityDragon) par1EntityLiving, par2, par3, par4);
	}

	public void rotateCorpse(GOTEntityDragon dragon, float par2, float par3, float par4) {
		glRotatef(180 - par3, 0, 1, 0);
	}

	public void setModel(GOTDragonBreed breed) {
		mainModel = renderPassModel = model = stages.get(breed);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float scale) {
		return shouldRenderPass((GOTEntityDragon) entity, pass, scale);
	}

	public int shouldRenderPass(GOTEntityDragon dragon, int pass, float scale) {
		if (pass == 0 && updateModel && dragon.ticksExisted % 20 == 0) {
			initBreedModels();
		}
		model.renderPass = pass;
		switch (pass) {
		case 0:
			if (dragon.isSaddled()) {
				bindTexture(model.saddleTexture);
				return 1;
			}
			break;
		case 1:
			bindTexture(model.glowTexture);
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
}
