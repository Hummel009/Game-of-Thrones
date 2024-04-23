package got.client.render.animal;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.other.iface.GOTRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWhiteOryx extends GOTRenderGemsbok {
	private static final GOTRandomSkins ORYX_TEXTURES = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/whiteOryx");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return ORYX_TEXTURES.getRandomSkin((GOTRandomSkinEntity) entity);
	}
}