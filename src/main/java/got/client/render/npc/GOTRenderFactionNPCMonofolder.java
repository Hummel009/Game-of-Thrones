package got.client.render.npc;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelHuman;
import got.client.render.other.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPCMonofolder extends GOTRenderBiped {
	private String path;
	private float size;

	public GOTRenderFactionNPCMonofolder(String texture) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		size = 1.0f;
	}

	public GOTRenderFactionNPCMonofolder(String texture, float height) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
		size = height;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityNPC mob = (GOTEntityNPC) entity;
		return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path).getRandomSkin(mob);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(size, size, size);
	}
}
