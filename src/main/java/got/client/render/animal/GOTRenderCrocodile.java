package got.client.render.animal;

import got.client.model.GOTModelCrocodile;
import got.common.entity.animal.GOTEntityCrocodile;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderCrocodile extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/animal/crocodile.png");

	public GOTRenderCrocodile() {
		super(new GOTModelCrocodile(), 0.75f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float snapTime = ((GOTEntityCrocodile) entity).getSnapTime();
		if (snapTime > 0.0f) {
			snapTime -= f;
		}
		return snapTime / 20.0f;
	}
}
