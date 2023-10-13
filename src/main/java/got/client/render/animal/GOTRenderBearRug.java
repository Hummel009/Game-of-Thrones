package got.client.render.animal;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelBearRug;
import got.common.entity.animal.GOTEntityBearRug;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderBearRug extends GOTRenderRugBase {
	public GOTRenderBearRug() {
		super(new GOTModelBearRug());
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTEntityBearRug rug = (GOTEntityBearRug) entity;
		return GOTRenderBear.getBearSkin(rug.getRugType());
	}

	@Override
	public void preRenderCallback() {
		GL11.glScalef(1.2f, 1.2f, 1.2f);
	}
}
