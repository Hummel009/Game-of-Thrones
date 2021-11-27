package got.client.render.animal;

import got.client.model.GOTModelDeer;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityDeer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class GOTRenderDeer extends RenderLiving {
	public static GOTRandomSkins elkSkins;
	public static ResourceLocation saddleTexture;

	static {
		saddleTexture = new ResourceLocation("got:mob/animal/elk/saddle.png");
	}

	public GOTRenderDeer() {
		super(new GOTModelDeer(), 0.5f);
		setRenderPassModel(new GOTModelDeer(0.5f));
		elkSkins = GOTRandomSkins.loadSkinsList("got:mob/animal/elk/elk");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityDeer elk = (GOTEntityDeer) entity;
		ResourceLocation elkSkin = elkSkins.getRandomSkin(elk);
		return GOTRenderHorse.getLayeredMountTexture(elk, elkSkin);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((GOTEntityDeer) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
