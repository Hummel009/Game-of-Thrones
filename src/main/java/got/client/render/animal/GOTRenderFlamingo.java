package got.client.render.animal;

import got.client.model.GOTModelFlamingo;
import got.common.entity.animal.GOTEntityFlamingo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GOTRenderFlamingo extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/flamingo/flamingo.png");
	private static final ResourceLocation TEXTURE_CHICK = new ResourceLocation("got:textures/entity/animal/flamingo/chick.png");

	public GOTRenderFlamingo() {
		super(new GOTModelFlamingo(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityLivingBase) entity).isChild() ? TEXTURE_CHICK : TEXTURE;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		GOTEntityFlamingo flamingo = (GOTEntityFlamingo) entity;
		float f1 = flamingo.field_756_e + (flamingo.field_752_b - flamingo.field_756_e) * f;
		float f2 = flamingo.field_757_d + (flamingo.destPos - flamingo.field_757_d) * f;
		return (MathHelper.sin(f1) + 1.0f) * f2;
	}
}
