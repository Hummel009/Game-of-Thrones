package got.client.render.npc;

import got.client.model.GOTModelHuman;
import got.client.render.other.GOTRenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFactionNPCMonotexture extends GOTRenderBiped {
	public String name;

	public GOTRenderFactionNPCMonotexture(String texture) {
		super(new GOTModelHuman(), 0.5f);
		name = texture;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/" + name + ".png");
	}
}