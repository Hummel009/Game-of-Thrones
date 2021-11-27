package got.client.render.animal;

import java.util.*;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelBear;
import got.common.entity.animal.GOTEntityBear;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderBear extends RenderLiving {
	public static Map bearSkins = new HashMap();

	public GOTRenderBear() {
		super(new GOTModelBear(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityBear bear = (GOTEntityBear) entity;
		return GOTRenderBear.getBearSkin(bear.getBearType());
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityBear animal = (GOTEntityBear) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.6f, 0.6f, 0.6f);
		} else {
			GL11.glScalef(1.2f, 1.2f, 1.2f);
		}
	}

	public static ResourceLocation getBearSkin(GOTEntityBear.BearType type) {
		String s = type.textureName();
		ResourceLocation skin = (ResourceLocation) bearSkins.get(s);
		if (skin == null) {
			skin = new ResourceLocation("got:mob/animal/bear/" + s + ".png");
			bearSkins.put(s, skin);
		}
		return skin;
	}
}
