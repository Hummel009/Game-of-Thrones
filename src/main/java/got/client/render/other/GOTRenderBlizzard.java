package got.client.render.other;

import got.common.entity.animal.GOTEntityBlizzard;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderBlizzard extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/entity/ulthos/blizzard.png");
	private int f;

	public GOTRenderBlizzard() {
		super(new ModelBlaze(), 0.5F);
		f = ((ModelBlaze) mainModel).func_78104_a();
	}

	@Override
	public void doRender(Entity entity, double d1, double d2, double d3, float d4, float d5) {
		doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	@Override
	public void doRender(EntityLiving entity, double d1, double d2, double d3, float d4, float d5) {
		doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	@Override
	public void doRender(EntityLivingBase entity, double d1, double d2, double d3, float d4, float d5) {
		doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	private void doRender(GOTEntityBlizzard entity, double d1, double d2, double d3, float d4, float d5) {
		int i = ((ModelBlaze) mainModel).func_78104_a();
		if (i != f) {
			f = i;
			mainModel = new ModelBlaze();
		}
		super.doRender(entity, d1, d2, d3, d4, d5);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TEXTURE;
	}
}