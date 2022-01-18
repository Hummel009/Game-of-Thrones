package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import got.client.model.GOTModelDirewolf;
import got.common.entity.animal.GOTEntityDirewolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(value = Side.CLIENT)
public class GOTRenderDirewolf extends RenderLiving {

	public GOTRenderDirewolf() {
		super(new GOTModelDirewolf(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/direwolf.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityDirewolf animal = (GOTEntityDirewolf) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.5f, 0.5f, 0.5f);
		} else {
		}
	}
}