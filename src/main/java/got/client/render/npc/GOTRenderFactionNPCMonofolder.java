package got.client.render.npc;

import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRandomSkins;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderFactionNPCMonofolder extends GOTRenderBiped {
	private final String path;
	private final float size;

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
		GOTRandomSkinEntity mob = (GOTRandomSkinEntity) entity;
		return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path).getRandomSkin(mob);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(size, size, size);
	}
}