package got.client.render.npc;

import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRandomSkins;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.iface.GOTRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPCMonofolder extends GOTRenderBiped {
	private final String path;

	public GOTRenderFactionNPCMonofolder(String texture) {
		super(new GOTModelHuman(), 0.5f);
		path = texture;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTRandomSkinEntity mob = (GOTRandomSkinEntity) entity;
		return GOTRandomSkins.loadSkinsList("got:textures/entity/" + path).getRandomSkin(mob);
	}
}