package got.client.render.other;

import got.GOT;
import got.client.model.GOTModelBanner;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;
import java.util.Map;

public class GOTRenderBanner extends Render {
	public static final ResourceLocation STAND_TEXTURE = new ResourceLocation("got:textures/banner/stand.png");

	private static final Map<GOTItemBanner.BannerType, ResourceLocation> BANNER_TEXTURES = new EnumMap<>(GOTItemBanner.BannerType.class);
	private static final ICamera BANNER_FRUSTUM = new Frustrum();
	private static final GOTModelBanner MODEL = new GOTModelBanner();

	public static ResourceLocation getBannerTexture(GOTItemBanner.BannerType type) {
		ResourceLocation r = BANNER_TEXTURES.get(type);
		if (r == null) {
			if (GOT.isAprilFools()) {
				r = new ResourceLocation("got:textures/banner/null.png");
			} else {
				r = new ResourceLocation("got:textures/banner/" + type.getBannerName() + ".png");
			}
			BANNER_TEXTURES.put(type, r);
		}
		return r;
	}

	private static ResourceLocation getBannerTexture(Entity entity) {
		GOTEntityBanner banner = (GOTEntityBanner) entity;
		return getBannerTexture(banner.getBannerType());
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityBanner banner = (GOTEntityBanner) entity;
		Minecraft mc = Minecraft.getMinecraft();
		boolean debug = mc.gameSettings.showDebugInfo;
		boolean protecting = banner.isProtectingTerritory();
		boolean renderBox = debug && protecting;
		boolean seeThroughWalls = renderBox && (mc.thePlayer.capabilities.isCreativeMode || banner.clientside_playerHasPermissionInSurvival());
		int protectColor = 65280;
		BANNER_FRUSTUM.setPosition(d + RenderManager.renderPosX, d1 + RenderManager.renderPosY, d2 + RenderManager.renderPosZ);
		if (BANNER_FRUSTUM.isBoundingBoxInFrustum(banner.boundingBox)) {
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glTranslatef((float) d, (float) d1 + 1.5F, (float) d2);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			GL11.glRotatef(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.01F, 0.0F);
			if (seeThroughWalls) {
				GL11.glDisable(2929);
				GL11.glDisable(3553);
				GL11.glDisable(2896);
				int light = 15728880;
				int lx = light % 65536;
				int ly = light / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
				GL11.glColor4f(0 / 255.0F, (protectColor >> 8 & 0xFF) / 255.0F, 0 / 255.0F, 1.0F);
			}
			bindTexture(STAND_TEXTURE);
			MODEL.renderStand(0.0625F);
			MODEL.renderPost(0.0625F);
			bindTexture(getBannerTexture(entity));
			MODEL.renderBanner(0.0625F);
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
			int light = 15728880;
			int lx = light % 65536;
			int ly = light / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(2896);
			AxisAlignedBB aabb = banner.createProtectionCube().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderGlobal.drawOutlinedBoundingBox(aabb, protectColor);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(2896);
			GL11.glEnable(3553);
			GL11.glEnable(2884);
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return STAND_TEXTURE;
	}
}