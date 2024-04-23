package got.client.render.animal;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.other.iface.GOTRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWhiteBison extends GOTRenderBison {
	private static final GOTRandomSkins W_BISON_TEXTURES = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/wbison");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTRandomSkinEntity bison = (GOTRandomSkinEntity) entity;
		return W_BISON_TEXTURES.getRandomSkin(bison);
	}
}