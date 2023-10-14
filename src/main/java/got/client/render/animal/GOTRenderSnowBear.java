package got.client.render.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelBear;
import got.common.entity.animal.GOTEntitySnowBear;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GOTRenderSnowBear extends RenderLiving {

	public GOTRenderSnowBear() {
		super(new GOTModelBear(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/polarbear.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntitySnowBear animal = (GOTEntitySnowBear) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.65f, 0.65f, 0.65f);
		} else {
			GL11.glScalef(1.3f, 1.3f, 1.3f);
		}
	}
}