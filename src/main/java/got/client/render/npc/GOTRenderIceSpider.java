package got.client.render.npc;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderIceSpider extends GOTRenderSpiderBase {

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/westeros/ice/spider.png");
	}
}
