package got.client;

import got.common.item.GOTWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTAttackTiming {
	private static final Minecraft MC = Minecraft.getMinecraft();
	private static final ResourceLocation METER_TEXTURE = new ResourceLocation("got:textures/gui/attackMeter.png");
	private static final RenderItem ITEM_RENDERER = new RenderItem();

	@SuppressWarnings("PublicField")
	public static int attackTime;

	@SuppressWarnings("PublicField")
	public static int prevAttackTime;

	@SuppressWarnings("PublicField")
	public static int fullAttackTime;

	private static ItemStack attackItem;
	private static int lastCheckTick = -1;

	private GOTAttackTiming() {
	}

	@SuppressWarnings("StatementWithEmptyBody")
	public static void doAttackTiming() {
		int currentTick = GOTTickHandlerClient.clientTick;
		if (lastCheckTick == -1) {
			lastCheckTick = currentTick;
		} else if (lastCheckTick == currentTick) {
			return;
		}
		if (MC.thePlayer == null) {
			reset();
		} else {
			KeyBinding attackKey = MC.gameSettings.keyBindAttack;
			boolean pressed = attackKey.isPressed();
			if (pressed) {
				KeyBinding.onTick(attackKey.getKeyCode());
			}
			if (pressed && MC.objectMouseOver != null && MC.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && MC.objectMouseOver.entityHit instanceof EntityLivingBase) {
				if (attackTime > 0) {
					while (attackKey.isPressed()) {
					}
				} else {
					ItemStack itemstack = MC.thePlayer.getHeldItem();
					attackTime = fullAttackTime = GOTWeaponStats.getAttackTimePlayer(itemstack);
					attackItem = itemstack;
				}
				lastCheckTick = currentTick;
			}
		}
	}

	public static void renderAttackMeter(ScaledResolution resolution, float partialTicks) {
		if (fullAttackTime > 0) {
			float attackTimeF = prevAttackTime + (attackTime - prevAttackTime) * partialTicks;
			attackTimeF /= fullAttackTime;
			float meterAmount = 1.0f - attackTimeF;
			int minX = resolution.getScaledWidth() / 2 + 120;
			int maxX = resolution.getScaledWidth() - 20;
			int maxY = resolution.getScaledHeight() - 10;
			int minY = maxY - 10;
			double lerpX = minX + (maxX - minX) * meterAmount;
			Tessellator tessellator = Tessellator.instance;
			MC.getTextureManager().bindTexture(METER_TEXTURE);
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
				ITEM_RENDERER.renderItemAndEffectIntoGUI(MC.fontRenderer, MC.getTextureManager(), attackItem, iconX, iconY);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	public static void reset() {
		attackTime = 0;
		prevAttackTime = 0;
		fullAttackTime = 0;
		attackItem = null;
	}

	public static void update() {
		prevAttackTime = attackTime;
		if (attackTime > 0) {
			--attackTime;
		} else {
			reset();
		}
	}
}