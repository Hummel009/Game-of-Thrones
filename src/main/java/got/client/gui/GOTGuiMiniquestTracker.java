package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.client.GOTTickHandlerClient;
import got.common.*;
import got.common.quest.GOTMiniQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiMiniquestTracker extends Gui {
	private static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/quest/tracker.png");
	private static RenderItem renderItem = new RenderItem();
	private int barX = 16;
	private int barY = 10;
	private int barWidth = 90;
	private int barHeight = 15;
	private int barEdge = 2;
	private int iconWidth = 20;
	private int iconHeight = 20;
	private int gap = 4;
	private GOTMiniQuest trackedQuest;
	private boolean holdingComplete;
	private int completeTime;

	public void drawTracker(Minecraft mc, EntityPlayer entityplayer) {
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = resolution.getScaledWidth();
		resolution.getScaledHeight();
		FontRenderer fr = mc.fontRenderer;
		boolean flip = GOTConfig.trackingQuestRight;
		if (entityplayer != null && trackedQuest != null) {
			float[] questRGB = trackedQuest.getQuestColorComponents();
			ItemStack itemstack = trackedQuest.getQuestIcon();
			String objective = trackedQuest.getQuestObjective();
			String progress = trackedQuest.getQuestProgressShorthand();
			float completion = trackedQuest.getCompletionFactor();
			boolean failed = trackedQuest.isFailed();
			boolean complete = trackedQuest.isCompleted();
			if (failed) {
				objective = trackedQuest.getQuestFailureShorthand();
			} else if (complete) {
				objective = StatCollector.translateToLocal("got.gui.redBook.mq.diary.complete");
			}
			int x = barX;
			int y = barY;
			if (flip) {
				x = width - barX - iconWidth;
			}
			GL11.glEnable(3008);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			mc.getTextureManager().bindTexture(guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x, y, 0, 0, iconWidth, iconHeight);
			int iconX = x + (iconWidth - 16) / 2;
			int iconY = y + (iconHeight - 16) / 2;
			x = flip ? (x = x - (barWidth + gap)) : (x = x + iconWidth + gap);
			int meterWidth = barWidth - barEdge * 2;
			meterWidth = Math.round(meterWidth * completion);
			mc.getTextureManager().bindTexture(guiTexture);
			GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
			drawTexturedModalRect(x + barEdge, y, iconWidth + barEdge, barHeight, meterWidth, barHeight);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x, y, iconWidth, 0, barWidth, barHeight);
			GOTTickHandlerClient.drawAlignmentText(fr, x + barWidth / 2 - fr.getStringWidth(progress) / 2, y + barHeight - barHeight / 2 - fr.FONT_HEIGHT / 2, progress, 1.0f);
			fr.drawSplitString(objective, x, y += barHeight + gap, barWidth, 16777215);
			GL11.glDisable(3042);
			GL11.glDisable(3008);
			if (itemstack != null) {
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(2896);
				GL11.glEnable(32826);
				GL11.glEnable(2896);
				GL11.glEnable(2884);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemstack, iconX, iconY);
				GL11.glDisable(2896);
			}
		}
	}

	public void setTrackedQuest(GOTMiniQuest quest) {
		trackedQuest = quest;
	}

	public void update(Minecraft mc, EntityPlayer entityplayer) {
		if (entityplayer == null) {
			trackedQuest = null;
		} else {
			if (trackedQuest != null && trackedQuest.isCompleted() && !holdingComplete) {
				completeTime = 200;
				holdingComplete = true;
			}
			GOTMiniQuest currentTrackedQuest = GOTLevelData.getData(entityplayer).getTrackingMiniQuest();
			if (completeTime > 0 && currentTrackedQuest == null) {
				--completeTime;
			} else {
				trackedQuest = currentTrackedQuest;
				holdingComplete = false;
			}
		}
	}
}
