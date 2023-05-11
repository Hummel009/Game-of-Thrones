package got.client.render.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelShadowcat;
import got.common.entity.animal.GOTEntityShadowcat;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value = Side.CLIENT)
public class GOTRenderShadowcat extends RenderLiving {
	public GOTRenderShadowcat() {
		super(new GOTModelShadowcat(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/shadowcat.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityShadowcat animal = (GOTEntityShadowcat) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.75f, 0.75f, 0.75f);
		} else {
			GL11.glScalef(1.5f, 1.5f, 1.5f);
		}
	}
}