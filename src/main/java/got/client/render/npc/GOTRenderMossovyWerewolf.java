package got.client.render.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelMossovyWerewolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(value = Side.CLIENT)
public class GOTRenderMossovyWerewolf extends RenderLiving {

	public GOTRenderMossovyWerewolf() {
		super(new GOTModelMossovyWerewolf(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/werewolf.png");
	}
}