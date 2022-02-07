package got.client.render.animal;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityWhiteOryx;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWhiteOryx extends GOTRenderGemsbok {
	private GOTRandomSkins oryxSkins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/whiteOryx");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return oryxSkins.getRandomSkin((GOTEntityWhiteOryx) entity);
	}
}
