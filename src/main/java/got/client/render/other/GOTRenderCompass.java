package got.client.render.other;

import org.lwjgl.opengl.GL11;

import got.client.GOTClientProxy;
import got.client.model.GOTModelPortal;
import got.common.entity.other.GOTEntityPortal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRenderCompass extends Render {
	private static ResourceLocation compassTexture = new ResourceLocation("got:textures/misc/portal.png");
	private static ModelBase ringotel = new GOTModelPortal(0);
	
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityPortal portal = (GOTEntityPortal) entity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d, (float) d1 + 0.75f, (float) d2);
		GL11.glNormal3f(0.0f, 0.0f, 0.0f);
		GL11.glEnable(32826);
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		float portalScale = portal.getScale();
		if (portalScale < GOTEntityPortal.MAX_SCALE) {
			portalScale += f1;
			GL11.glScalef(portalScale /= GOTEntityPortal.MAX_SCALE, portalScale, portalScale);
		}
		GL11.glRotatef(portal.getPortalRotation(f1), 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
		bindTexture(getEntityTexture(portal));
		float scale = 0.0625f;
		ringotel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		GL11.glDisable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Tessellator.instance.setBrightness(GOTClientProxy.TESSELLATOR_MAX_BRIGHTNESS);
		GL11.glEnable(2896);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return getCompassTexture();
	}

	public static ResourceLocation getCompassTexture() {
		return compassTexture;
	}

	public static void setCompassTexture(ResourceLocation compassTexture) {
		GOTRenderCompass.compassTexture = compassTexture;
	}
}
