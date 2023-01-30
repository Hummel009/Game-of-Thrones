package got.client.render.npc;

import org.lwjgl.opengl.GL11;

import got.client.GOTTextures;
import got.client.model.GOTModelHuman;
import got.client.render.other.*;
import got.common.entity.essos.*;
import got.common.entity.essos.asshai.GOTEntityAsshaiAlchemist;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPC extends GOTRenderBiped {
	public String path;
	public float size;
	public GOTModelHuman eyesModel = new GOTModelHuman();

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

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		if (entity instanceof GOTEntityShryke || entity instanceof GOTEntityAsshaiAlchemist || entity instanceof GOTEntityAsshaiArchmag || entity instanceof GOTEntityIfekevron) {
			ResourceLocation eyes = GOTTextures.getEyesTexture(this.getEntityTexture(entity), new int[][] { { 9, 12 }, { 13, 12 } }, 2, 1);
			GOTGlowingEyes.renderGlowingEyes(entity, eyes, eyesModel, f, f1, f2, f3, f4, f5);
		}
	}
}