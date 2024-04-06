package got.client.render.animal;

import got.client.model.GOTModelDikDik;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderDikDik extends RenderLiving {
	private static final GOTRandomSkins TEXTURES = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/dikdik");

	public GOTRenderDikDik() {
		super(new GOTModelDikDik(), 0.8f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTRandomSkinEntity dikdik = (GOTRandomSkinEntity) entity;
		return TEXTURES.getRandomSkin(dikdik);
	}
}
