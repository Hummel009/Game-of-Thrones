package got.client.render.animal;

import got.client.model.GOTModelFish;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class GOTRenderFish extends RenderLiving {
	private static final Map<String, GOTRandomSkins> FISH_TEXTURES = new HashMap<>();

	public GOTRenderFish() {
		super(new GOTModelFish(), 0.0f);
	}

	private static GOTRandomSkins getFishSkins(String s) {
		GOTRandomSkins skins = FISH_TEXTURES.get(s);
		if (skins == null) {
			skins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/fish/" + s);
			FISH_TEXTURES.put(s, skins);
		}
		return skins;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityFish fish = (GOTEntityFish) entity;
		String type = fish.getFishTextureDir();
		GOTRandomSkins skins = getFishSkins(type);
		return skins.getRandomSkin(fish);
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