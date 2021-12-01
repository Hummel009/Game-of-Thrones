package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityWhiteBison;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWhiteBison extends GOTRenderBison {
	public static GOTRandomSkins wbisonSkins;

	public GOTRenderWhiteBison() {
		wbisonSkins = GOTRandomSkins.loadSkinsList("got:mob/animal/wbison");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityWhiteBison kine = (GOTEntityWhiteBison) entity;
		return wbisonSkins.getRandomSkin(kine);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityWhiteBison animal = (GOTEntityWhiteBison) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.55f, 0.55f, 0.55f);
		} else {
			GL11.glScalef(1.15f, 1.15f, 1.15f);
		}
	}
}
