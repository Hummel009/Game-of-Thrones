package got.client.render.other;

import java.util.*;

import org.lwjgl.opengl.GL11;

import got.GOT;
import got.client.GOTClientProxy;
import got.client.model.GOTModelBanner;
import got.common.GOTBannerProtection;
import got.common.entity.other.GOTEntityBanner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

public class GOTRenderBanner extends Render {
	private static Map<GOTItemBanner.BannerType, ResourceLocation> bannerTextures = new HashMap<>();
	private static ResourceLocation standTexture = new ResourceLocation("got:textures/banner/stand.png");
	private static GOTModelBanner model = new GOTModelBanner();
	private static Frustrum bannerFrustum = new Frustrum();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		int light;
		int ly;
		int lx;
		GOTEntityBanner banner = (GOTEntityBanner) entity;
		Minecraft mc = Minecraft.getMinecraft();
		boolean debug = mc.gameSettings.showDebugInfo;
		boolean protecting = banner.isProtectingTerritory();
		GOTBannerProtection.forPlayer(mc.thePlayer).protects(banner);
		boolean renderBox = debug && protecting;
		boolean seeThroughWalls = renderBox && mc.thePlayer.capabilities.isCreativeMode;
		int protectColor = 65280;
		bannerFrustum.setPosition(d + RenderManager.renderPosX, d1 + RenderManager.renderPosY, d2 + RenderManager.renderPosZ);
		if (bannerFrustum.isBoundingBoxInFrustum(banner.boundingBox)) {
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glTranslatef((float) d, (float) d1 + 1.5f, (float) d2);
			GL11.glScalef(-1.0f, -1.0f, 1.0f);
			GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.0f, 0.01f, 0.0f);
			if (seeThroughWalls) {
				GL11.glDisable(2929);
				GL11.glDisable(3553);
				GL11.glDisable(2896);
				light = GOTClientProxy.getTesselatorMaxBrightness();
				lx = light % 65536;
				ly = light / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
				GL11.glColor4f((protectColor >> 16 & 0xFF) / 255.0f, (protectColor >> 8 & 0xFF) / 255.0f, (protectColor >> 0 & 0xFF) / 255.0f, 1.0f);
			}
			bindTexture(this.getStandTexture(entity));
			model.renderStand(0.0625f);
			model.renderPost(0.0625f);
			bindTexture(this.getBannerTexture(entity));
			model.renderBanner(0.0625f);
			if (seeThroughWalls) {
				GL11.glEnable(2929);
				GL11.glEnable(3553);
				GL11.glEnable(2896);
			}
			GL11.glEnable(2884);
			GL11.glPopMatrix();
		}
		if (renderBox) {
			GL11.glPushMatrix();
			GL11.glDepthMask(false);
			GL11.glDisable(3553);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			light = GOTClientProxy.getTesselatorMaxBrightness();
			lx = light % 65536;
			ly = light / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx / 1.0f, ly / 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(2896);
			AxisAlignedBB aabb = banner.createProtectionCube().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			RenderGlobal.drawOutlinedBoundingBox(aabb, protectColor);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glEnable(2896);
			GL11.glEnable(3553);
			GL11.glEnable(2884);
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}

	private ResourceLocation getBannerTexture(Entity entity) {
		GOTEntityBanner banner = (GOTEntityBanner) entity;
		return GOTRenderBanner.getBannerTexture(banner.getBannerType());
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return this.getStandTexture(entity);
	}

	private ResourceLocation getStandTexture(Entity entity) {
		GOTEntityBanner banner = (GOTEntityBanner) entity;
		return GOTRenderBanner.getStandTexture(banner.getBannerType());
	}

	public static ResourceLocation getBannerTexture(GOTItemBanner.BannerType type) {
		ResourceLocation r = bannerTextures.get(type);
		if (r == null) {
			if (GOT.isAprilFools()) {
				r = new ResourceLocation("got:textures/banner/null.png");
			} else {
				r = new ResourceLocation("got:textures/banner/" + type.bannerName + ".png");
			}
			bannerTextures.put(type, r);
		}
		return r;
	}

	public static ResourceLocation getStandTexture(GOTItemBanner.BannerType type) {
		return standTexture;
	}
}
