package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import got.client.model.GOTModelMammoth;
import got.common.entity.animal.GOTEntityMammoth;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(value = Side.CLIENT)
public class GOTRenderMammoth extends RenderLiving {

	public GOTRenderMammoth() {
		super(new GOTModelMammoth(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:mob/animal/mammoth.png");
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityMammoth animal = (GOTEntityMammoth) entity;
		if (!animal.isChild()) {
			GL11.glScalef(2.0f, 2.0f, 2.0f);
		}
	}
}