package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import got.client.model.GOTModelManticore;
import got.common.entity.animal.GOTEntityManticore;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

@SideOnly(value = Side.CLIENT)
public class GOTRenderManticore extends RenderLiving {
	public GOTRenderManticore() {
		super(new GOTModelManticore(), 0.5f);
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation("got:textures/entity/animal/manticore.png");
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float strikeTime = ((GOTEntityManticore) entity).getStrikeTime();
		if (strikeTime > 0.0f) {
			strikeTime -= f;
		}
		return strikeTime / 20.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glScalef(0.2f, 0.2f, 0.2f);
	}
}