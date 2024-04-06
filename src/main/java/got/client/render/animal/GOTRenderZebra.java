package got.client.render.animal;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderZebra extends GOTRenderHorse {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/zebra.png");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}
}
