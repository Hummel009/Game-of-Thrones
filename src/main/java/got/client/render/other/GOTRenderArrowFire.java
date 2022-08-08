package got.client.render.other;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderArrowFire extends RenderArrow {
	public static ResourceLocation arrowFireTexture = new ResourceLocation("got:textures/model/arrow_fire.png");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return arrowFireTexture;
	}
}
