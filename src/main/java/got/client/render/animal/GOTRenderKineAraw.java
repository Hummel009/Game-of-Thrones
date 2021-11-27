package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityKineAraw;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderKineAraw extends GOTRenderBison {
	public static GOTRandomSkins kineSkins;

	public GOTRenderKineAraw() {
		kineSkins = GOTRandomSkins.loadSkinsList("got:mob/animal/kineAraw");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityKineAraw kine = (GOTEntityKineAraw) entity;
		return kineSkins.getRandomSkin(kine);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityKineAraw animal = (GOTEntityKineAraw) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.55f, 0.55f, 0.55f);
		} else {
			GL11.glScalef(1.15f, 1.15f, 1.15f);
		}
	}
}
