package got.client.render.animal;

import got.client.model.GOTModelSwan;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderSwan extends RenderLiving {
	public GOTRenderSwan() {
		super(new GOTModelSwan(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/swan.png");
	}
}
