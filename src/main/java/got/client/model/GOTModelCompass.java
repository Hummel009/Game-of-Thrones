package got.client.model;

import org.lwjgl.opengl.GL11;

import got.client.render.other.GOTRenderCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GOTModelCompass extends ModelBase {
	private static GOTModelCompass compassModel = new GOTModelCompass();
	private static ResourceLocation compassTexture = new ResourceLocation("got:textures/misc/compass.png");
	private ModelRenderer compass;
	private ModelBase ringotel = new GOTModelPortal(0);
	
	private GOTModelCompass() {
		textureWidth = 32;
		textureHeight = 32;
		compass = new ModelRenderer(this, 0, 0);
		compass.setRotationPoint(0.0f, 0.0f, 0.0f);
		compass.addBox(-16.0f, 0.0f, -16.0f, 32, 0, 32);
	}

	public void render(float scale, float rotation) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(2884);
		GL11.glNormal3f(0.0f, 0.0f, 0.0f);
		GL11.glEnable(32826);
		GL11.glScalef(1.0f, 1.0f, -1.0f);
		GL11.glRotatef(40.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		texturemanager.bindTexture(compassTexture);
		compass.render(scale * 2.0f);
		texturemanager.bindTexture(GOTRenderCompass.getCompassTexture());
		ringotel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}

	public static GOTModelCompass getCompassModel() {
		return compassModel;
	}

	public static void setCompassModel(GOTModelCompass compassModel) {
		GOTModelCompass.compassModel = compassModel;
	}
}
