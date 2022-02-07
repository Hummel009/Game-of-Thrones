package got.client.render.other;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderArrowFire extends RenderArrow {
	private static ResourceLocation arrowFireTexture = new ResourceLocation("got:textures/model/arrowFire.png");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return arrowFireTexture;
	}
}
