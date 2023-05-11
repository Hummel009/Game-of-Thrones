package got.client.render.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.model.GOTModelMammoth;
import got.common.entity.animal.GOTEntityMammoth;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GOTRenderMammoth extends RenderLiving {

	public GOTRenderMammoth() {
		super(new GOTModelMammoth(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/mammoth.png");
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