package got.client.render.animal;

import got.client.model.GOTModelDeer;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityDeer;
import got.common.entity.other.GOTNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class GOTRenderDeer extends RenderLiving {
	private static final GOTRandomSkins ELK_TEXTURES = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/elk/elk");
	private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation("got:textures/entity/animal/elk/saddle.png");

	public GOTRenderDeer() {
		super(new GOTModelDeer(), 0.5f);
		setRenderPassModel(new GOTModelDeer(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityDeer elk = (GOTEntityDeer) entity;
		ResourceLocation elkSkin = ELK_TEXTURES.getRandomSkin(elk);
		return GOTRenderHorse.getLayeredMountTexture(elk, elkSkin);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTNPCMount) entity).isMountSaddled()) {
			bindTexture(SADDLE_TEXTURE);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
