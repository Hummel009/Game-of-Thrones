package got.client.render.animal;

import got.client.model.GOTModelButterfly;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityButterfly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;
import java.util.Map;

public class GOTRenderButterfly extends RenderLiving {
	public static Map<GOTEntityButterfly.ButterflyType, GOTRandomSkins> textures = new EnumMap<>(GOTEntityButterfly.ButterflyType.class);

	public GOTRenderButterfly() {
		super(new GOTModelButterfly(), 0.2f);
		for (GOTEntityButterfly.ButterflyType t : GOTEntityButterfly.ButterflyType.values()) {
			textures.put(t, GOTRandomSkins.loadSkinsList("got:textures/entity/animal/butterfly/" + t.textureDir));
		}
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityButterfly butterfly = (GOTEntityButterfly) entity;
		if (butterfly.getButterflyType() == GOTEntityButterfly.ButterflyType.QOHOR) {
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(2896);
		}
		super.doRender(entity, d, d1, d2, f, f1);
		GL11.glEnable(2896);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityButterfly butterfly = (GOTEntityButterfly) entity;
		GOTRandomSkins skins = textures.get(butterfly.getButterflyType());
		return skins.getRandomSkin(butterfly);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		GOTEntityButterfly butterfly = (GOTEntityButterfly) entity;
		if (butterfly.isButterflyStill() && butterfly.flapTime > 0) {
			return butterfly.flapTime - f;
		}
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		GL11.glScalef(0.3f, 0.3f, 0.3f);
	}
}
