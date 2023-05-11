package got.client.gui;

import got.client.GOTClientProxy;
import got.common.database.GOTSpeech;
import got.common.util.GOTFunctions;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GOTGuiFastTravel extends GOTGuiScreenBase {
	public static ResourceLocation ftSound = new ResourceLocation("got:event.fastTravel");
	public static float zoomInAmount = 0.5f;
	public static float zoomInIncr = 0.008333334f;
	public static float mapSpeedMax = 2.0f;
	public static float mapSpeedIncr = 0.01f;
	public static float mapAccel = 0.2f;
	public static float wpReachedDistance = 1.0f;
	public GOTGuiMap mapGui;
	public GOTGuiRendererMap mapRenderer;
	public int tickCounter;
	public GOTAbstractWaypoint theWaypoint;
	public int startX;
	public int startZ;
	public String message;
	public boolean chunkLoaded;
	public boolean playedSound;
	public float zoomBase;
	public float mapScaleFactor;
	public float currentZoom;
	public float prevZoom;
	public boolean finishedZoomIn;
	public float mapSpeed;
	public float mapVelX;
	public float mapVelY;
	public boolean reachedWP;

	public GOTGuiFastTravel(GOTAbstractWaypoint waypoint, int x, int z) {
		theWaypoint = waypoint;
		startX = x;
		startZ = z;
		message = GOTSpeech.getRandomSpeech("standart/special/fast_travel");
		mapGui = new GOTGuiMap();
		mapRenderer = new GOTGuiRendererMap();
		mapRenderer.setSepia(true);
		mapRenderer.mapX = GOTWaypoint.worldToMapX(startX);
		mapRenderer.mapY = GOTWaypoint.worldToMapZ(startZ);
		float dx = theWaypoint.getX() - mapRenderer.mapX;
		float dy = theWaypoint.getY() - mapRenderer.mapY;
		float distSq = dx * dx + dy * dy;
		float dist = (float) Math.sqrt(distSq);
		mapScaleFactor = dist / 100.0f;
		zoomBase = -((float) (Math.log(mapScaleFactor * 0.3f) / Math.log(2.0)));
		currentZoom = prevZoom = zoomBase + 0.5f;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		GL11.glEnable(3008);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		mapRenderer.zoomExp = prevZoom + (currentZoom - prevZoom) * f;
		mapRenderer.zoomStable = (float) Math.pow(2.0, zoomBase);
		mapRenderer.renderMap(this, mapGui, f);
		mapRenderer.renderVignettes(this, zLevel, 4);
		GL11.glEnable(3042);
		String title = StatCollector.translateToLocalFormatted("got.fastTravel.travel", theWaypoint.getDisplayName());
		String titleExtra = new String[]{"", ".", "..", "..."}[tickCounter / 10 % 4];
		List messageLines = fontRendererObj.listFormattedStringToWidth(message, width - 100);
		String skipText = StatCollector.translateToLocalFormatted("got.fastTravel.skip", GameSettings.getKeyDisplayString(mc.gameSettings.keyBindInventory.getKeyCode()));
		float boxAlpha = 0.5f;
		int boxColor = (int) (boxAlpha * 255.0f) << 24 | 0;
		int fh = fontRendererObj.FONT_HEIGHT;
		int border = fh * 2;
		if (chunkLoaded) {
			Gui.drawRect(0, 0, width, 0 + border + fh * 3 + border, boxColor);
		} else {
			Gui.drawRect(0, 0, width, 0 + border + fh + border, boxColor);
		}
		int messageY = height - border - messageLines.size() * fh;
		Gui.drawRect(0, messageY - border, width, height, boxColor);
		GL11.glDisable(3042);
		fontRendererObj.drawStringWithShadow(title + titleExtra, width / 2 - fontRendererObj.getStringWidth(title) / 2, 0 + border, 16777215);
		for (Object obj : messageLines) {
			String s1 = (String) obj;
			this.drawCenteredString(fontRendererObj, s1, width / 2, messageY, 16777215);
			messageY += fh;
		}
		if (chunkLoaded) {
			float skipAlpha = GOTFunctions.triangleWave(tickCounter + f, 0.3f, 1.0f, 80.0f);
			int skipColor = 0xFFFFFF | GOTClientProxy.getAlphaInt(skipAlpha) << 24;
			GL11.glEnable(3042);
			fontRendererObj.drawString(skipText, width / 2 - fontRendererObj.getStringWidth(skipText) / 2, 0 + border + fh * 2, skipColor);
		}
		GL11.glDisable(3042);
		super.drawScreen(i, j, f);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (chunkLoaded && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapGui.setWorldAndResolution(mc, i, j);
	}

	@Override
	public void updateScreen() {
		if (!chunkLoaded && GOTClientProxy.doesClientChunkExist(mc.theWorld, theWaypoint.getXCoord(), theWaypoint.getZCoord())) {
			chunkLoaded = true;
		}
		if (!playedSound) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(ftSound));
			playedSound = true;
		}
		mapRenderer.updateTick();
		++tickCounter;
		prevZoom = currentZoom;
		if (!reachedWP) {
			float dy;
			float dx = theWaypoint.getX() - mapRenderer.mapX;
			float distSq = dx * dx + (dy = theWaypoint.getY() - mapRenderer.mapY) * dy;
			float dist = (float) Math.sqrt(distSq);
			if (dist <= 1.0f * mapScaleFactor) {
				reachedWP = true;
				mapSpeed = 0.0f;
				mapVelX = 0.0f;
				mapVelY = 0.0f;
			} else {
				mapSpeed += 0.01f;
				mapSpeed = Math.min(mapSpeed, 2.0f);
				float vXNew = dx / dist * mapSpeed;
				float vYNew = dy / dist * mapSpeed;
				float a = 0.2f;
				mapVelX += (vXNew - mapVelX) * a;
				mapVelY += (vYNew - mapVelY) * a;
			}
			mapRenderer.mapX += mapVelX * mapScaleFactor;
			mapRenderer.mapY += mapVelY * mapScaleFactor;
			currentZoom -= 0.008333334f;
			currentZoom = Math.max(currentZoom, zoomBase);
		} else {
			currentZoom += 0.008333334f;
			currentZoom = Math.min(currentZoom, zoomBase + 0.5f);
			if (currentZoom >= zoomBase + 0.5f) {
				finishedZoomIn = true;
			}
		}
		if (chunkLoaded && reachedWP && finishedZoomIn) {
			mc.displayGuiScreen(null);
		}
	}
}