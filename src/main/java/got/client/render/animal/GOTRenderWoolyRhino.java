package got.client.render.animal;

import got.client.model.GOTModelRhino;
import got.common.entity.animal.GOTEntityWoolyRhino;
import got.common.entity.other.iface.GOTNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderWoolyRhino extends RenderLiving {
	private static final ResourceLocation RHINO_TEXTURE = new ResourceLocation("got:textures/entity/animal/rhino/wooly.png");
	private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation("got:textures/entity/animal/rhino/saddle.png");

	public GOTRenderWoolyRhino() {
		super(new GOTModelRhino(), 0.5f);
		setRenderPassModel(new GOTModelRhino(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTNPCMount rhino = (GOTNPCMount) entity;
		return GOTRenderHorse.getLayeredMountTexture(rhino, RHINO_TEXTURE);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityWoolyRhino animal = (GOTEntityWoolyRhino) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
			GL11.glScalef(1.0f, 1.0f, 1.0f);
		}
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