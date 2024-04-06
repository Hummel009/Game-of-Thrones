package got.client.render.npc;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderIceSpider extends GOTRenderSpiderBase {
	private static final ResourceLocation SKIN = new ResourceLocation("got:textures/entity/westeros/ice/spider.png");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return SKIN;
	}
}
