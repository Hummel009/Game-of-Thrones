package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import got.client.model.GOTModelElephant;
import got.common.entity.animal.GOTEntityElephant;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(value = Side.CLIENT)
public class GOTRenderElephant extends RenderLiving {

	public GOTRenderElephant() {
		super(new GOTModelElephant(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/elephant.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityElephant animal = (GOTEntityElephant) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.75f, 0.75f, 0.75f);
		} else {
			GL11.glScalef(1.5f, 1.5f, 1.5f);
		}
	}
}