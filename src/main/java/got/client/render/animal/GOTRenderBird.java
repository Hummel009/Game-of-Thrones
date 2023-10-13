package got.client.render.animal;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelBird;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityBird;
import got.common.entity.animal.GOTEntityGorcrow;
import got.common.entity.animal.GOTEntitySeagull;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GOTRenderBird extends RenderLiving {
	public static Map<String, GOTRandomSkins> birdTypeSkins = new HashMap<>();
	public static boolean renderStolenItem = true;

	public GOTRenderBird() {
		super(new GOTModelBird(), 0.2f);
	}

	public GOTRandomSkins getBirdSkins(String s) {
		GOTRandomSkins skins = birdTypeSkins.get(s);
		if (skins == null) {
			skins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/bird/" + s);
			birdTypeSkins.put(s, skins);
		}
		return skins;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityBird bird = (GOTEntityBird) entity;
		String type = bird.getBirdTextureDir();
		GOTRandomSkins skins = getBirdSkins(type);
		return skins.getRandomSkin(bird);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		GOTEntityBird bird = (GOTEntityBird) entity;
		if (bird.isBirdStill() && bird.flapTime > 0) {
			return bird.flapTime - f;
		}
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		if (entity instanceof GOTEntityGorcrow) {
			float scale = GOTEntityGorcrow.GORCROW_SCALE;
			GL11.glScalef(scale, scale, scale);
		} else if (entity instanceof GOTEntitySeagull) {
			float scale = GOTEntitySeagull.SEAGULL_SCALE;
			GL11.glScalef(scale, scale, scale);
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GOTEntityBird bird = (GOTEntityBird) entity;
		if (renderStolenItem) {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			ItemStack stolenItem = bird.getStolenItem();
			if (stolenItem != null) {
				GL11.glPushMatrix();
				((GOTModelBird) mainModel).head.postRender(0.0625f);
				GL11.glTranslatef(0.05f, 1.4f, -0.1f);
				float scale = 0.25f;
				GL11.glScalef(scale, scale, scale);
				renderManager.itemRenderer.renderItem(entity, stolenItem, 0);
				GL11.glPopMatrix();
			}
		}
	}
}
