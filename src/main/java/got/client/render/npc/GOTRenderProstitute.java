package got.client.render.npc;

import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRenderBiped;
import got.common.entity.other.GOTEntityProstitute;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class GOTRenderProstitute extends GOTRenderBiped {
	public static Map<String, ResourceLocation> prostituteSkins = new HashMap<>();
	public GOTRenderProstitute() {
		super(new GOTModelHuman(), 0.5f);
	}

	public static ResourceLocation getProstituteSkin(GOTEntityProstitute.ProstituteType type) {
		String s = type.textureName();
		ResourceLocation skin = (ResourceLocation) prostituteSkins.get(s);
		if (skin == null) {
			skin = new ResourceLocation("got:textures/entity/prostitute/" + s + ".png");
			prostituteSkins.put(s, skin);
		}
		return skin;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityProstitute prostitute = (GOTEntityProstitute) entity;
		return getProstituteSkin(prostitute.getProstituteType());
	}
}
