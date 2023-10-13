package got.client.render.animal;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelFish;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFish extends RenderLiving {
	public static Map<String, GOTRandomSkins> fishTypeSkins = new HashMap<>();

	public GOTRenderFish() {
		super(new GOTModelFish(), 0.0f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityFish fish = (GOTEntityFish) entity;
		String type = fish.getFishTextureDir();
		GOTRandomSkins skins = getFishSkins(type);
		return skins.getRandomSkin(fish);
	}

	public GOTRandomSkins getFishSkins(String s) {
		GOTRandomSkins skins = fishTypeSkins.get(s);
		if (skins == null) {
			skins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/fish/" + s);
			fishTypeSkins.put(s, skins);
		}
		return skins;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		if (!entity.isInWater()) {
			GL11.glTranslatef(0.0f, -0.05f, 0.0f);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
		}
	}
}
