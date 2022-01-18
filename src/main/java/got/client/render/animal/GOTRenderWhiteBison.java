package got.client.render.animal;

import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityWhiteBison;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWhiteBison extends GOTRenderBison {
	public static GOTRandomSkins wbisonSkins;

	public GOTRenderWhiteBison() {
		wbisonSkins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/wbison");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityWhiteBison bison = (GOTEntityWhiteBison) entity;
		return wbisonSkins.getRandomSkin(bison);
	}
}
