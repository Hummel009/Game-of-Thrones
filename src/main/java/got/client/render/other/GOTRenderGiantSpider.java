package got.client.render.other;

import got.client.render.npc.GOTRenderSpiderBase;
import got.common.entity.animal.GOTEntityUlthosSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderGiantSpider extends GOTRenderSpiderBase {
	public static ResourceLocation[] spiderSkins = { new ResourceLocation("got:mob/ulthos/spider.png"), new ResourceLocation("got:mob/ulthos/spiderSlowness.png"), new ResourceLocation("got:mob/ulthos/spiderPoison.png") };

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityUlthosSpider spider = (GOTEntityUlthosSpider) entity;
		return spiderSkins[spider.getSpiderType()];
	}
}
