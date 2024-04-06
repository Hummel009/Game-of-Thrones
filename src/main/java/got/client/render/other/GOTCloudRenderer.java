package got.client.render.other;

import com.google.common.math.IntMath;
import got.client.GOTReflectionClient;
import got.common.GOTConfig;
import got.common.GOTDate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import java.util.Random;

public class GOTCloudRenderer extends IRenderHandler {
	private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("got:textures/sky/clouds.png");
	private static final CloudProperty CLOUD_OPACITY = new CloudProperty(233591206262L, 0.1f, 1.0f, 0.001f);
	private static final CloudProperty CLOUD_SPEED = new CloudProperty(6283905602629L, 0.0f, 0.5f, 0.001f);
	private static final CloudProperty CLOUD_ANGLE = new CloudProperty(360360635650636L, 0.0f, 6.2831855f, 0.01f);
	private static final Random CLOUD_RAND = new Random();

	private static double cloudPosXPre;
	private static double cloudPosX;
	private static double cloudPosZPre;
	private static double cloudPosZ;

	public static void resetClouds() {
		CLOUD_OPACITY.reset();
		CLOUD_SPEED.reset();
		CLOUD_ANGLE.reset();
	}

	public static void updateClouds() {
		CLOUD_OPACITY.update();
		CLOUD_SPEED.update();
		CLOUD_ANGLE.update();
		float angle = CLOUD_ANGLE.getValue(1.0f);
		float speed = CLOUD_SPEED.getValue(1.0f);
		cloudPosXPre = cloudPosX;
		cloudPosX += MathHelper.cos(angle) * speed;
		cloudPosZPre = cloudPosZ;
		cloudPosZ += MathHelper.sin(angle) * speed;
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		if (world.provider.isSurfaceWorld()) {
			world.theProfiler.startSection("gotClouds");
			int cloudRange = GOTConfig.cloudRange;
			GL11.glMatrixMode(5889);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			float fov = GOTReflectionClient.getFOVModifier(mc.entityRenderer, partialTicks, true);
			Project.gluPerspective(fov, (float) mc.displayWidth / mc.displayHeight, 0.05f, cloudRange);
			GL11.glMatrixMode(5888);
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glDepthMask(false);
			GL11.glEnable(3008);
			float alphaFunc = GL11.glGetFloat(3010);
			GL11.glAlphaFunc(516, 0.01f);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glFogi(2917, 9729);
			GL11.glFogf(2915, cloudRange * 0.9f);
			GL11.glFogf(2916, cloudRange);
			if (GLContext.getCapabilities().GL_NV_fog_distance) {
				GL11.glFogi(34138, 34139);
			}
			Tessellator tessellator = Tessellator.instance;
			mc.renderEngine.bindTexture(CLOUD_TEXTURE);
			Vec3 cloudColor = world.getCloudColour(partialTicks);
			float r = (float) cloudColor.xCoord;
			float g = (float) cloudColor.yCoord;
			float b = (float) cloudColor.zCoord;
			if (mc.gameSettings.anaglyph) {
				float r1 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
				float g1 = (r * 30.0f + g * 70.0f) / 100.0f;
				float b1 = (r * 30.0f + b * 70.0f) / 100.0f;
				r = r1;
				g = g1;
				b = b1;
			}
			Vec3 pos = mc.renderViewEntity.getPosition(partialTicks);
			for (int pass = 0; pass < 2; ++pass) {
				int scale = 4096 * IntMath.pow(2, pass);
				double invScaleD = 1.0 / scale;
				double posX = pos.xCoord;
				double posZ = pos.zCoord;
				double posY = pos.yCoord;
				double cloudPosXAdd = cloudPosXPre + (cloudPosX - cloudPosXPre) * partialTicks;
				double cloudPosZAdd = cloudPosZPre + (cloudPosZ - cloudPosZPre) * partialTicks;
				int x = MathHelper.floor_double((posX += cloudPosXAdd / (pass + 1)) / scale);
				int z = MathHelper.floor_double((posZ += cloudPosZAdd / (pass + 1)) / scale);
				double cloudX = posX - x * scale;
				double cloudZ = posZ - z * scale;
				double cloudY = world.provider.getCloudHeight() - posY + 0.33000001311302185 + pass * 50.0f;
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(r, g, b, (0.8f - pass * 0.5f) * CLOUD_OPACITY.getValue(partialTicks));
				for (int i = -cloudRange; i < cloudRange; i += cloudRange) {
					for (int k = -cloudRange; k < cloudRange; k += cloudRange) {
						int xMax = i + cloudRange;
						int zMax = k + cloudRange;
						double uMin = (i + cloudX) * invScaleD;
						double uMax = (xMax + cloudX) * invScaleD;
						double vMin = (k + cloudZ) * invScaleD;
						double vMax = (zMax + cloudZ) * invScaleD;
						tessellator.addVertexWithUV(i, cloudY, zMax, uMin, vMax);
						tessellator.addVertexWithUV(xMax, cloudY, zMax, uMax, vMax);
						tessellator.addVertexWithUV(xMax, cloudY, k, uMax, vMin);
						tessellator.addVertexWithUV(i, cloudY, k, uMin, vMin);
					}
				}
				tessellator.draw();
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glEnable(2884);
			GL11.glDepthMask(true);
			GL11.glAlphaFunc(516, alphaFunc);
			GL11.glDisable(3042);
			GL11.glMatrixMode(5889);
			GL11.glPopMatrix();
			GL11.glMatrixMode(5888);
			GL11.glPopMatrix();
			world.theProfiler.endSection();
		}
	}

	private static class CloudProperty {
		protected long baseSeed;
		protected float currentDayValue;
		protected float value;
		protected float prevValue;
		protected float minValue;
		protected float maxValue;
		protected float interval;

		protected CloudProperty(long l, float min, float max, float i) {
			baseSeed = l;
			value = -1.0f;
			minValue = min;
			maxValue = max;
			interval = i;
		}

		protected float getCurrentDayValue() {
			int day = GOTDate.AegonCalendar.currentDay;
			long seed = day * baseSeed + day + 83025820626792L;
			CLOUD_RAND.setSeed(seed);
			return MathHelper.randomFloatClamp(CLOUD_RAND, minValue, maxValue);
		}

		protected float getValue(float f) {
			return prevValue + (value - prevValue) * f;
		}

		protected void reset() {
			value = -1.0f;
		}

		protected void update() {
			currentDayValue = getCurrentDayValue();
			if (value == -1.0f) {
				prevValue = value = currentDayValue;
			} else {
				prevValue = value;
				if (value > currentDayValue) {
					value -= interval;
					value = Math.max(value, currentDayValue);
				} else if (value < currentDayValue) {
					value += interval;
					value = Math.min(value, currentDayValue);
				}
			}
		}
	}

}
