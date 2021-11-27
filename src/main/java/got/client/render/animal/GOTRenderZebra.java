package got.client.render.animal;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderZebra extends GOTRenderHorse {
	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:mob/animal/zebra.png");
	}
}
