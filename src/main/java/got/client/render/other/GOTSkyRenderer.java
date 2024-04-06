package got.client.render.other;

import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class GOTSkyRenderer extends IRenderHandler {
	private static final ResourceLocation MOON_TEXTURE = new ResourceLocation("got:textures/sky/moon.png");
	private static final ResourceLocation SUN_TEXTURE = new ResourceLocation("got:textures/sky/sun.png");
	private static final ResourceLocation BIG_STAR_TEXTURE = new ResourceLocation("got:textures/sky/bigstar.png");
	private final GOTRandomSkins skyTextures;
	private ResourceLocation currentSkyTexture;
	private final int glSkyList;
	private final int glSkyList2;

	public GOTSkyRenderer() {
		int k;
		int j;
		skyTextures = GOTRandomSkins.loadSkinsList("got:textures/sky/night");
		Tessellator tessellator = Tessellator.instance;
		glSkyList = GLAllocation.generateDisplayLists(3);
		GL11.glNewList(glSkyList, 4864);
		int b2 = 64;
		int i = 256 / b2 + 2;
		float f = 16.0f;
		for (j = -b2 * i; j <= b2 * i; j += b2) {
			for (k = -b2 * i; k <= b2 * i; k += b2) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j, f, k);
				tessellator.addVertex(j + b2, f, k);
				tessellator.addVertex(j + b2, f, k + b2);
				tessellator.addVertex(j, f, k + b2);
				tessellator.draw();
			}
		}
		GL11.glEndList();
		glSkyList2 = glSkyList + 1;
		GL11.glNewList(glSkyList2, 4864);
		f = -16.0f;
		tessellator.startDrawingQuads();
		for (j = -b2 * i; j <= b2 * i; j += b2) {
			for (k = -b2 * i; k <= b2 * i; k += b2) {
				tessellator.addVertex(j + b2, f, k);
				tessellator.addVertex(j, f, k);
				tessellator.addVertex(j, f, k + b2);
				tessellator.addVertex(j + b2, f, k + b2);
			}
		}
		tessellator.draw();
		GL11.glEndList();
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		world.theProfiler.startSection("gotSky");
		boolean renderSkyFeatures = world.provider.isSurfaceWorld();
		int i = MathHelper.floor_double(mc.renderViewEntity.posX);
		int k = MathHelper.floor_double(mc.renderViewEntity.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiome && renderSkyFeatures) {
			renderSkyFeatures = ((GOTBiome) biome).hasSky();
		}
		GL11.glDisable(3553);
		Vec3 skyColor = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float skyR = (float) skyColor.xCoord;
		float skyG = (float) skyColor.yCoord;
		float skyB = (float) skyColor.zCoord;
		if (mc.gameSettings.anaglyph) {
			float newSkyR = (skyR * 30.0f + skyG * 59.0f + skyB * 11.0f) / 100.0f;
			float newSkyG = (skyR * 30.0f + skyG * 70.0f) / 100.0f;
			float newSkyB = (skyR * 30.0f + skyB * 70.0f) / 100.0f;
			skyR = newSkyR;
			skyG = newSkyG;
			skyB = newSkyB;
		}
		GL11.glColor3f(skyR, skyG, skyB);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(2912);
		GL11.glColor3f(skyR, skyG, skyB);
		GL11.glCallList(glSkyList);
		GL11.glDisable(2912);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		RenderHelper.disableStandardItemLighting();
		float[] sunrise = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		if (sunrise != null) {
			GL11.glDisable(3553);
			GL11.glShadeModel(7425);
			GL11.glPushMatrix();
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0f ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			float r = sunrise[0];
			float g = sunrise[1];
			g *= 1.2f;
			float b = sunrise[2];
			if (mc.gameSettings.anaglyph) {
				float r1 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
				float g1 = (r * 30.0f + g * 70.0f) / 100.0f;
				float b1 = (r * 30.0f + b * 70.0f) / 100.0f;
				r = r1;
				g = g1;
				b = b1;
			}
			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(r, g, b, sunrise[3]);
			tessellator.addVertex(0.0, 100.0, 0.0);
			tessellator.setColorRGBA_F(sunrise[0], sunrise[1], sunrise[2], 0.0f);
			int passes = 16;
			for (int l = 0; l <= passes; ++l) {
				float angle = l * 3.1415927f * 2.0f / passes;
				float sin = MathHelper.sin(angle);
				float cos = MathHelper.cos(angle);
				tessellator.addVertex(sin * 120.0f, cos * 120.0f, -cos * 40.0f * sunrise[3]);
			}
			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(7424);
		}
		GL11.glPushMatrix();
		if (renderSkyFeatures) {
			GL11.glEnable(3553);
			GL11.glBlendFunc(770, 1);
			float rainBrightness = 1.0f - world.getRainStrength(partialTicks);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
			float x = 0.0f;
			float y = 0.0f;
			float z = 0.0f;
			GL11.glTranslatef(x, y, z);
			GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
			float starBrightness = world.getStarBrightness(partialTicks) * rainBrightness;
			if (starBrightness > 0.0f) {
				if (currentSkyTexture == null) {
					currentSkyTexture = skyTextures.getRandomSkin();
				}
				mc.renderEngine.bindTexture(currentSkyTexture);
				GL11.glPushMatrix();
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, starBrightness);
				GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
				renderSkyboxSide(tessellator, 4);
				GL11.glPushMatrix();
				GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				renderSkyboxSide(tessellator, 1);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				renderSkyboxSide(tessellator, 0);
				GL11.glPopMatrix();
				GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
				renderSkyboxSide(tessellator, 5);
				GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
				renderSkyboxSide(tessellator, 2);
				GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
				renderSkyboxSide(tessellator, 3);
				GL11.glPopMatrix();
			} else {
				currentSkyTexture = null;
			}
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
			GL11.glBlendFunc(770, 771);
			mc.renderEngine.bindTexture(SUN_TEXTURE);
			float rSun = 10.0f;
			for (int pass = 0; pass <= 1; ++pass) {
				if (pass == 0) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
				} else if (sunrise != null) {
					float sunriseBlend = sunrise[3];
					GL11.glColor4f(1.0f, 0.9f, 0.2f, sunriseBlend * 0.5f * rainBrightness);
				}
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-rSun, 100.0, -rSun, 0.0, 0.0);
				tessellator.addVertexWithUV(rSun, 100.0, -rSun, 1.0, 0.0);
				tessellator.addVertexWithUV(rSun, 100.0, rSun, 1.0, 1.0);
				tessellator.addVertexWithUV(-rSun, 100.0, rSun, 0.0, 1.0);
				tessellator.draw();
			}
			GL11.glBlendFunc(770, 1);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
			int phases = GOTWorldProvider.MOON_PHASES;
			int moonPhase = GOTWorldProvider.getGOTMoonPhase();
			boolean lunarEclipse = GOTWorldProvider.isLunarEclipse();
			if (lunarEclipse) {
				GL11.glColor3f(1.0f, 0.6f, 0.4f);
			}
			mc.renderEngine.bindTexture(MOON_TEXTURE);
			float rMoon = 10.0f;
			float f14 = (float) moonPhase / phases;
			float f15 = 0.0f;
			float f16 = (float) (moonPhase + 1) / phases;
			float f17 = 1.0f;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-rMoon, -100.0, rMoon, f16, f17);
			tessellator.addVertexWithUV(rMoon, -100.0, rMoon, f14, f17);
			tessellator.addVertexWithUV(rMoon, -100.0, -rMoon, f14, f15);
			tessellator.addVertexWithUV(-rMoon, -100.0, -rMoon, f16, f15);
			tessellator.draw();
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			float celestialAngle = world.getCelestialAngle(partialTicks);
			float f0 = celestialAngle - 0.5f;
			float f1 = Math.abs(f0);
			float eMin = 0.15f;
			float eMax = 0.3f;
			if (f1 >= eMin && f1 <= eMax) {
				float eMid = (eMin + eMax) / 2.0f;
				float eHalfWidth = eMax - eMid;
				float eBright = MathHelper.cos((f1 - eMid) / eHalfWidth * 3.1415927f / 2.0f);
				eBright *= eBright;
				float eAngle = Math.signum(f0) * 18.0f;
				GL11.glPushMatrix();
				GL11.glRotatef(eAngle, 1.0f, 0.0f, 0.0f);
				GL11.glBlendFunc(770, 1);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, eBright * rainBrightness);
				mc.renderEngine.bindTexture(BIG_STAR_TEXTURE);
				float rEarendil = 1.5f;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-rEarendil, 100.0, -rEarendil, 0.0, 0.0);
				tessellator.addVertexWithUV(rEarendil, 100.0, -rEarendil, 1.0, 0.0);
				tessellator.addVertexWithUV(rEarendil, 100.0, rEarendil, 1.0, 1.0);
				tessellator.addVertexWithUV(-rEarendil, 100.0, rEarendil, 0.0, 1.0);
				tessellator.draw();
				GL11.glPopMatrix();
			}
			GL11.glDisable(3553);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(2912);
		GL11.glPopMatrix();
		GL11.glDisable(3553);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();
		if (d0 < 0.0) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 12.0f, 0.0f);
			GL11.glCallList(glSkyList2);
			GL11.glPopMatrix();
			float f8 = 1.0f;
			float f9 = -((float) (d0 + 65.0));
			float f10 = -f8;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.draw();
		}
		if (world.provider.isSkyColored()) {
			GL11.glColor3f(skyR * 0.2f + 0.04f, skyG * 0.2f + 0.04f, skyB * 0.6f + 0.1f);
		} else {
			GL11.glColor3f(skyR, skyG, skyB);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -((float) (d0 - 16.0)), 0.0f);
		GL11.glCallList(glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDepthMask(true);
		world.theProfiler.endSection();
	}

	private void renderSkyboxSide(Tessellator tessellator, int side) {
		double u = side % 3 / 3.0;
		double v = (double) side / 3 / 2.0;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-100.0, -100.0, -100.0, u, v);
		tessellator.addVertexWithUV(-100.0, -100.0, 100.0, u, v + 0.5);
		tessellator.addVertexWithUV(100.0, -100.0, 100.0, u + 0.3333333333333333, v + 0.5);
		tessellator.addVertexWithUV(100.0, -100.0, -100.0, u + 0.3333333333333333, v);
		tessellator.draw();
	}
}