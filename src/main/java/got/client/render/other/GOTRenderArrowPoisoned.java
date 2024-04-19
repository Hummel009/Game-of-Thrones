package got.client.render.other;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderArrowPoisoned extends RenderArrow {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/model/arrow_poisoned.png");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}
}