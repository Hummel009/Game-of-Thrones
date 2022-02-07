package got.client;

import org.lwjgl.opengl.GL11;

import got.common.item.GOTWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTAttackTiming {
	private static Minecraft mc = Minecraft.getMinecraft();
	private static ResourceLocation meterTexture = new ResourceLocation("got:textures/gui/attackMeter.png");
	private static RenderItem itemRenderer = new RenderItem();
	private static int attackTime;
	private static int prevAttackTime;
	private static int fullAttackTime;
	private static ItemStack attackItem;
	private static int lastCheckTick = -1;

	public static void doAttackTiming() {
		int currentTick = GOTTickHandlerClient.getClientTick();
		if (lastCheckTick == -1) {
			lastCheckTick = currentTick;
		} else if (lastCheckTick == currentTick) {
			return;
		}
		if (GOTAttackTiming.mc.thePlayer == null) {
			GOTAttackTiming.reset();
		} else {
			KeyBinding attackKey = GOTAttackTiming.mc.gameSettings.keyBindAttack;
			boolean pressed = attackKey.isPressed();
			if (pressed) {
				KeyBinding.onTick(attackKey.getKeyCode());
			}
			if (pressed && GOTAttackTiming.mc.objectMouseOver != null && GOTAttackTiming.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && GOTAttackTiming.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
				if (getAttackTime() <= 0) {
					ItemStack itemstack = GOTAttackTiming.mc.thePlayer.getHeldItem();
					setAttackTime(setFullAttackTime(GOTWeaponStats.getAttackTimePlayer(itemstack)));
					attackItem = itemstack;
				}
				lastCheckTick = currentTick;
			}
		}
	}

	public static int getAttackTime() {
		return attackTime;
	}

	public static int getFullAttackTime() {
		return fullAttackTime;
	}

	public static int getPrevAttackTime() {
		return prevAttackTime;
	}

	public static void renderAttackMeter(ScaledResolution resolution, float partialTicks) {
		if (getFullAttackTime() > 0) {
			float attackTimeF = getPrevAttackTime() + (getAttackTime() - getPrevAttackTime()) * partialTicks;
			attackTimeF /= getFullAttackTime();
			float meterAmount = 1.0f - attackTimeF;
			int minX = resolution.getScaledWidth() / 2 + 120;
			int maxX = resolution.getScaledWidth() - 20;
			int maxY = resolution.getScaledHeight() - 10;
			int minY = maxY - 10;
			double lerpX = minX + (maxX - minX) * meterAmount;
			Tessellator tessellator = Tessellator.instance;
			mc.getTextureManager().bindTexture(meterTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			double minU = 0.0;
			double maxU = 1.0;
			double minV = 0.0;
			double maxV = 0.0625;
			double lerpU = minU + (maxU - minU) * meterAmount;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(minX, minY, 0.0, minU, minV);
			tessellator.addVertexWithUV(minX, maxY, 0.0, minU, maxV);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(lerpX, minY, 0.0, lerpU, minV + maxV);
			tessellator.addVertexWithUV(lerpX, maxY, 0.0, lerpU, maxV + maxV);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV + maxV);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV + maxV);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(minX, minY, 0.0, minU, minV + maxV * 2.0);
			tessellator.addVertexWithUV(minX, maxY, 0.0, minU, maxV + maxV * 2.0);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV + maxV * 2.0);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV + maxV * 2.0);
			tessellator.draw();
			if (attackItem != null) {
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glEnable(32826);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int iconX = (minX + maxX) / 2 - 8;
				int iconY = (minY + maxY) / 2 - 8;
				itemRenderer.renderItemAndEffectIntoGUI(GOTAttackTiming.mc.fontRenderer, mc.getTextureManager(), attackItem, iconX, iconY);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	public static void reset() {
		setAttackTime(0);
		setPrevAttackTime(0);
		setFullAttackTime(0);
		attackItem = null;
	}

	public static void setAttackTime(int attackTime) {
		GOTAttackTiming.attackTime = attackTime;
	}

	public static int setFullAttackTime(int fullAttackTime) {
		GOTAttackTiming.fullAttackTime = fullAttackTime;
		return fullAttackTime;
	}

	public static void setPrevAttackTime(int prevAttackTime) {
		GOTAttackTiming.prevAttackTime = prevAttackTime;
	}

	public static void update() {
		setPrevAttackTime(getAttackTime());
		if (getAttackTime() > 0) {
			setAttackTime(getAttackTime() - 1);
		} else {
			GOTAttackTiming.reset();
		}
	}
}
