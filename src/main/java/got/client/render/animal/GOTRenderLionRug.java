package got.client.render.animal;

import got.client.model.GOTModelLionRug;
import got.common.entity.animal.GOTEntityLionRug;
import got.common.item.other.GOTItemLionRug;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderLionRug extends GOTRenderRugBase {
	public GOTRenderLionRug() {
		super(new GOTModelLionRug());
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityLionRug rug = (GOTEntityLionRug) entity;
		GOTItemLionRug.LionRugType type = rug.getRugType();
		if (type == GOTItemLionRug.LionRugType.LION) {
			return GOTRenderLion.getTextureLion();
		}
		if (type == GOTItemLionRug.LionRugType.LIONESS) {
			return GOTRenderLion.getTextureLioness();
		}
		return new ResourceLocation("");
	}
}
