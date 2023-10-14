package got.client.render.animal;

import got.client.model.GOTModelBison;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityBison;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderBison extends RenderLiving {
	public static GOTRandomSkins bisonSkins;

	public GOTRenderBison() {
		super(new GOTModelBison(), 0.5f);
		bisonSkins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/bison");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTRandomSkinEntity bison = (GOTRandomSkinEntity) entity;
		return bisonSkins.getRandomSkin(bison);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityBison animal = (GOTEntityBison) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
	}
}
