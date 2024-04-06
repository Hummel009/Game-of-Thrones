package got.client.render.npc;

import got.client.model.GOTModelMossovyWerewolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderMossovyWerewolf extends RenderLiving {
	private static final ResourceLocation SKIN = new ResourceLocation("got:textures/entity/animal/werewolf.png");

	public GOTRenderMossovyWerewolf() {
		super(new GOTModelMossovyWerewolf(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return SKIN;
	}
}