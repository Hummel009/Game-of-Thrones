package got.client.render.npc;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelHuman;
import got.client.render.other.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPC extends GOTRenderBiped {
	public String path;
	public float size;

	public GOTRenderFactionNPC(String texture) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		size = 1.0f;
	}

	public GOTRenderFactionNPC(String texture, float height) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		size = height;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityNPC mob = (GOTEntityNPC) entity;
		boolean child = mob.isChild();
		if (mob.familyInfo.isMale()) {
			if (child) {
				return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/malechild").getRandomSkin(mob);
			}
			return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/male").getRandomSkin(mob);
		}
		if (child) {
			return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/femalechild").getRandomSkin(mob);
		}
		return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path + "/female").getRandomSkin(mob);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(size, size, size);
	}
}