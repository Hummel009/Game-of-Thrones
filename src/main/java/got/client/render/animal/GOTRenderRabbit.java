package got.client.render.animal;

import got.client.model.GOTModelRabbit;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.animal.GOTEntityRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderRabbit extends RenderLiving {
	public static GOTRandomSkins rabbitSkins;

	public GOTRenderRabbit() {
		super(new GOTModelRabbit(), 0.3f);
		rabbitSkins = GOTRandomSkins.loadSkinsList("got:textures/entity/animal/rabbit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityRabbit rabbit = (GOTEntityRabbit) entity;
		return rabbitSkins.getRandomSkin(rabbit);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		GL11.glScalef(0.75f, 0.75f, 0.75f);
	}
}
