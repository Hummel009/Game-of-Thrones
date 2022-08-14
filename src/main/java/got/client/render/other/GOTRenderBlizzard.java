package got.client.render.other;

import cpw.mods.fml.relauncher.*;
import got.common.entity.animal.GOTEntityBlizzard;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GOTRenderBlizzard extends RenderLiving {
	private int f;

	public GOTRenderBlizzard() {
		super(new ModelBlaze(), 0.5F);
		f = ((ModelBlaze) mainModel).func_78104_a();
	}

	@Override
	public void doRender(Entity entity, double d1, double d2, double d3, float d4, float d5) {
		this.doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	public void doRender(GOTEntityBlizzard entity, double d1, double d2, double d3, float d4, float d5) {
		int i = ((ModelBlaze) mainModel).func_78104_a();

		if (i != f) {
			f = i;
			mainModel = new ModelBlaze();
		}

		super.doRender(entity, d1, d2, d3, d4, d5);
	}

	@Override
	public void doRender(EntityLiving entity, double d1, double d2, double d3, float d4, float d5) {
		this.doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	@Override
	public void doRender(EntityLivingBase entity, double d1, double d2, double d3, float d4, float d5) {
		this.doRender((GOTEntityBlizzard) entity, d1, d2, d3, d4, d5);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((GOTEntityBlizzard) entity);
	}

	protected ResourceLocation getEntityTexture(GOTEntityBlizzard entity) {
		return new ResourceLocation("got:textures/entity/ulthos/blizzard.png");
	}
}
