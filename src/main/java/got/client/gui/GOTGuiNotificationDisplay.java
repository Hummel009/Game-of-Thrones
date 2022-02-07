package got.client.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;

import got.client.*;
import got.common.database.GOTAchievement;
import got.common.faction.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.*;

public class GOTGuiNotificationDisplay extends Gui {
	private static int guiXSize = 190;
	private static int guiYSize = 32;
	private static RenderItem itemRenderer = new RenderItem();
	private Minecraft mc;
	private int windowWidth;
	private List<Notification> notifications = new ArrayList<>();
	private Set<Notification> notificationsToRemove = new HashSet<>();

	public GOTGuiNotificationDisplay() {
		mc = Minecraft.getMinecraft();
	}

	public void queueAchievement(GOTAchievement achievement) {
		notifications.add(new NotificationAchievement(achievement));
	}

	public void queueConquest(GOTFaction fac, float conq, boolean cleansing) {
		notifications.add(new NotificationConquest(fac, conq, cleansing));
	}

	public void queueFellowshipNotification(IChatComponent message) {
		notifications.add(new NotificationFellowship(message));
	}

	public void updateWindow() {
		if (!notifications.isEmpty()) {
			int index = 0;
			for (Notification notif : notifications) {
				long notifTime = notif.getNotificationTime();
				double d0 = (double) (Minecraft.getSystemTime() - notifTime) / (double) notif.getDurationMs();
				if (d0 < 0.0 || d0 > 1.0) {
					notificationsToRemove.add(notif);
				} else {
					updateWindowScale();
					if (Minecraft.isGuiEnabled()) {
						GL11.glEnable(3008);
						GL11.glDisable(2929);
						GL11.glDepthMask(false);
						double d1 = d0 * 2.0;
						if (d1 > 1.0) {
							d1 = 2.0 - d1;
						}
						d1 *= 4.0;
						if ((d1 = 1.0 - d1) < 0.0) {
							d1 = 0.0;
						}
						d1 *= d1;
						d1 *= d1;
						int i = windowWidth - guiXSize;
						int j = 0 - (int) (d1 * 36.0);
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						GL11.glEnable(3553);
						mc.getTextureManager().bindTexture(GOTGuiAchievements.getIconsTexture());
						GL11.glDisable(2896);
						drawTexturedModalRect(i, j += index * (guiYSize + 8), 0, 200, guiXSize, guiYSize);
						notif.renderText(i + 30, j + 7);
						GL11.glEnable(3008);
						notif.renderIcon(i + 8, j + 8);
					}
				}
				++index;
			}
		}
		if (!notificationsToRemove.isEmpty()) {
			notifications.removeAll(notificationsToRemove);
		}
	}

	public void updateWindowScale() {
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glMatrixMode(5889);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(5888);
		GL11.glLoadIdentity();
		windowWidth = mc.displayWidth;
		int windowHeight = mc.displayHeight;
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		windowWidth = scaledresolution.getScaledWidth();
		windowHeight = scaledresolution.getScaledHeight();
		GL11.glClear(256);
		GL11.glMatrixMode(5889);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, windowWidth, windowHeight, 0.0, 1000.0, 3000.0);
		GL11.glMatrixMode(5888);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
	}

	public abstract static class Notification {
		private Long notificationTime = Minecraft.getSystemTime();

		private Notification() {
		}

		public abstract int getDurationMs();

		private Long getNotificationTime() {
			return notificationTime;
		}

		public abstract void renderIcon(int var1, int var2);

		public abstract void renderText(int var1, int var2);
	}

	public class NotificationAchievement extends Notification {
		private GOTAchievement achievement;

		private NotificationAchievement(GOTAchievement ach) {
			achievement = ach;
		}

		@Override
		public int getDurationMs() {
			return 3000;
		}

		@Override
		public void renderIcon(int x, int y) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(2896);
			GL11.glEnable(32826);
			GL11.glEnable(2903);
			GL11.glEnable(2896);
			itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), achievement.icon, x, y);
			GL11.glDisable(2896);
			GL11.glDepthMask(true);
			GL11.glEnable(2929);
			GL11.glEnable(3008);
			mc.getTextureManager().bindTexture(GOTGuiAchievements.getIconsTexture());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x + 162, y + 1, 190, 17, 16, 16);
		}

		@Override
		public void renderText(int x, int y) {
			mc.fontRenderer.drawString(StatCollector.translateToLocal("achievement.get"), x, y, 8019267);
			mc.fontRenderer.drawString(achievement.getTitle(mc.thePlayer), x, y + 11, 8019267);
		}
	}

	public class NotificationConquest extends Notification {
		private GOTFaction conqFac;
		private float conqValue;
		private boolean isCleansing;

		private NotificationConquest(GOTFaction fac, float conq, boolean clean) {
			conqFac = fac;
			conqValue = conq;
			isCleansing = clean;
		}

		@Override
		public int getDurationMs() {
			return 6000;
		}

		@Override
		public void renderIcon(int x, int y) {
			mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x, y, isCleansing ? 16 : 0, 228, 16, 16);
		}

		@Override
		public void renderText(int x, int y) {
			String conqS = GOTAlignmentValues.formatConqForDisplay(conqValue, false);
			GOTTickHandlerClient.drawConquestText(mc.fontRenderer, x + 1, y, conqS, isCleansing, 1.0f);
			mc.fontRenderer.drawString(StatCollector.translateToLocal("got.gui.map.conquest.notif"), x + mc.fontRenderer.getStringWidth(conqS + " ") + 2, y, 8019267);
			mc.fontRenderer.drawString(EnumChatFormatting.ITALIC + conqFac.factionName(), x, y + 11, 9666921);
		}
	}

	public class NotificationFellowship extends Notification {
		private IChatComponent message;

		private NotificationFellowship(IChatComponent msg) {
			message = msg;
		}

		@Override
		public int getDurationMs() {
			return 6000;
		}

		@Override
		public void renderIcon(int x, int y) {
			mc.getTextureManager().bindTexture(GOTGuiFellowships.getIconsTextures());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x, y, 80, 0, 16, 16);
		}

		@Override
		public void renderText(int x, int y) {
			mc.fontRenderer.drawSplitString(message.getFormattedText(), x, y, 152, 8019267);
		}
	}

}