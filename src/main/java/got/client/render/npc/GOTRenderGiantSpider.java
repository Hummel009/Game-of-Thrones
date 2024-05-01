package got.client.render.npc;

import got.common.entity.other.GOTEntityGiantSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderGiantSpider extends GOTRenderSpiderBase {
	private static final ResourceLocation[] SPIDER_SKINS = {new ResourceLocation("got:textures/entity/ulthos/spider.png"), new ResourceLocation("got:textures/entity/ulthos/spiderSlowness.png"), new ResourceLocation("got:textures/entity/ulthos/spiderPoison.png")};

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityGiantSpider spider = (GOTEntityGiantSpider) entity;
		return SPIDER_SKINS[spider.getSpiderType()];
	}
}