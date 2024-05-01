package got.client.render.npc;

import got.client.model.GOTModelWerewolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderWerewolf extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/werewolf.png");

	public GOTRenderWerewolf() {
		super(new GOTModelWerewolf(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}
}