package got.client.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.*;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.*;
import net.minecraftforge.client.ForgeHooksClient;

public class GOTGuiMainMenu extends GuiMainMenu {
	private static ResourceLocation titleTexture = new ResourceLocation("textures/gui/title/minecraft.png");
	private static ResourceLocation menuOverlay = new ResourceLocation("got:textures/gui/menu_overlay.png");
	private static GOTGuiRendererMap mapRenderer;
	private static int tickCounter;
	private static Random rand;
	private static boolean isFirstMenu;
	private static List<GOTWaypoint> waypointRoute;
	private static int currentWPIndex;
	private static boolean randomWPStart;
	private static float mapSpeed;
	private static float mapVelX;
	private static float mapVelY;
	static {
		rand = new Random();
		isFirstMenu = true;
		waypointRoute = new ArrayList<>();
		randomWPStart = false;
	}
	private GOTGuiMap mapGui;
	private boolean fadeIn = isFirstMenu;
	private long firstRenderTime;

	public GOTGuiMainMenu() {
		isFirstMenu = false;
		mapGui = new GOTGuiMap();
		mapRenderer = new GOTGuiRendererMap();
		mapRenderer.setSepia(false);
		if (waypointRoute.isEmpty()) {
			GOTGuiMainMenu.setupWaypoints();
			currentWPIndex = randomWPStart ? rand.nextInt(waypointRoute.size()) : 0;
		}
		GOTWaypoint wp = waypointRoute.get(currentWPIndex);
		GOTGuiMainMenu.mapRenderer.setPrevMapX(GOTGuiMainMenu.mapRenderer.setMapX(wp.getX()));
		GOTGuiMainMenu.mapRenderer.setPrevMapY(GOTGuiMainMenu.mapRenderer.setMapY(wp.getY()));
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		GL11.glEnable(3008);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		if (firstRenderTime == 0L && fadeIn) {
			firstRenderTime = System.currentTimeMillis();
		}
		float fade = fadeIn ? (System.currentTimeMillis() - firstRenderTime) / 1000.0f : 1.0f;
		float fadeAlpha = fadeIn ? MathHelper.clamp_float(fade - 1.0f, 0.0f, 1.0f) : 1.0f;
		GOTGuiMainMenu.mapRenderer.setZoomExp(-0.1f + MathHelper.cos((tickCounter + f) * 0.003f) * 0.8f);
		if (fadeIn) {
			float slowerFade = fade * 0.5f;
			float fadeInZoom = MathHelper.clamp_float(1.0f - slowerFade, 0.0f, 1.0f) * -1.5f;
			GOTGuiMainMenu.mapRenderer.setZoomExp(GOTGuiMainMenu.mapRenderer.getZoomExp() + fadeInZoom);
		}
		GOTGuiMainMenu.mapRenderer.setZoomStable((float) Math.pow(2.0, -0.10000000149011612));
		mapRenderer.renderMap(this, mapGui, f);
		mapRenderer.renderVignettes(this, zLevel, 2);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeIn ? MathHelper.clamp_float(1.0f - fade, 0.0f, 1.0f) : 0.0f);
		mc.getTextureManager().bindTexture(menuOverlay);
		Gui.func_152125_a(0, 0, 0.0f, 0.0f, 16, 128, width, height, 16.0f, 128.0f);
		int fadeAlphaI = MathHelper.ceiling_float_int(fadeAlpha * 255.0f) << 24;
		if ((fadeAlphaI & 0xFC000000) != 0) {
			int short1 = 274;
			int k = width / 2 - short1 / 2;
			int b0 = 30;
			mc.getTextureManager().bindTexture(titleTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeAlpha);
			drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 155, 44);
			drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
			String modSubtitle = StatCollector.translateToLocal("got.menu.title");
			drawString(fontRendererObj, modSubtitle, width / 2 - fontRendererObj.getStringWidth(modSubtitle) / 2, 80, -1);
			List brandings = Lists.reverse((List) FMLCommonHandler.instance().getBrandings(true));
			for (int l = 0; l < brandings.size(); ++l) {
				String brd = (String) brandings.get(l);
				if (Strings.isNullOrEmpty(brd)) {
					continue;
				}
				drawString(fontRendererObj, brd, 2, height - (10 + l * (fontRendererObj.FONT_HEIGHT + 1)), -1);
			}
			ForgeHooksClient.renderMainMenu(this, fontRendererObj, width, height);
			String copyright = "Powered by Hummel009";
			drawString(fontRendererObj, copyright, width - fontRendererObj.getStringWidth(copyright) - 2, height - 10, -1);
			String field_92025_p = (String) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92025_p");
			String field_146972_A = (String) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_146972_A");
			int field_92024_r = (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92024_r");
			int field_92022_t = (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92022_t");
			int field_92021_u = (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92021_u");
			int field_92020_v = (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92020_v");
			int field_92019_w = (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92019_w");
			if (field_92025_p != null && field_92025_p.length() > 0) {
				Gui.drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
				drawString(fontRendererObj, field_92025_p, field_92022_t, field_92021_u, -1);
				drawString(fontRendererObj, field_146972_A, (width - field_92024_r) / 2, ((GuiButton) buttonList.get(0)).yPosition - 12, -1);
			}
			for (Object button : buttonList) {
				((GuiButton) button).drawButton(mc, i, j);
			}
			for (Object label : labelList) {
				((GuiLabel) label).func_146159_a(mc, i, j);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int lowerButtonMaxY = 0;
		for (Object obj : buttonList) {
			GuiButton button = (GuiButton) obj;
			int buttonMaxY = button.yPosition + button.height;
			if (buttonMaxY <= lowerButtonMaxY) {
				continue;
			}
			lowerButtonMaxY = buttonMaxY;
		}
		int idealMoveDown = 50;
		int lowestSuitableHeight = height - 25;
		int moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
		moveDown = Math.max(moveDown, 0);
		for (int i = 0; i < buttonList.size(); ++i) {
			GuiButton button = (GuiButton) buttonList.get(i);
			button.yPosition += moveDown;
			if (button.getClass() != GuiButton.class) {
				continue;
			}
			GOTGuiButton newButton = new GOTGuiButton(button.id, button.xPosition, button.yPosition, button.width, button.height, button.displayString);
			buttonList.set(i, newButton);
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapGui.setWorldAndResolution(mc, i, j);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
		mapRenderer.updateTick();
		GOTWaypoint wp = waypointRoute.get(currentWPIndex);
		float dx = wp.getX() - GOTGuiMainMenu.mapRenderer.getMapX();
		float dy = wp.getY() - GOTGuiMainMenu.mapRenderer.getMapY();
		float distSq = dx * dx + dy * dy;
		float dist = (float) Math.sqrt(distSq);
		if (dist <= 12.0f) {
			if (++currentWPIndex >= waypointRoute.size()) {
				currentWPIndex = 0;
			}
		} else {
			mapSpeed += 0.01f;
			mapSpeed = Math.min(mapSpeed, 0.8f);
			float vXNew = dx / dist * mapSpeed;
			float vYNew = dy / dist * mapSpeed;
			float a = 0.02f;
			mapVelX += (vXNew - mapVelX) * a;
			mapVelY += (vYNew - mapVelY) * a;
		}
		GOTGuiMainMenu.mapRenderer.setMapX(GOTGuiMainMenu.mapRenderer.getMapX() + mapVelX);
		GOTGuiMainMenu.mapRenderer.setMapY(GOTGuiMainMenu.mapRenderer.getMapY() + mapVelY);
	}

	public static void setupWaypoints() {
		waypointRoute.clear();
		waypointRoute.add(GOTWaypoint.ThreeEyedRavenCave);
		waypointRoute.add(GOTWaypoint.CastleBlack);
		waypointRoute.add(GOTWaypoint.Winterfell);
		waypointRoute.add(GOTWaypoint.Riverrun);
		waypointRoute.add(GOTWaypoint.CasterlyRock);
		waypointRoute.add(GOTWaypoint.KingsLanding);
		waypointRoute.add(GOTWaypoint.StormsEnd);
		waypointRoute.add(GOTWaypoint.Highgarden);
		waypointRoute.add(GOTWaypoint.Sunspear);
		waypointRoute.add(GOTWaypoint.Pentos);
		waypointRoute.add(GOTWaypoint.OldGhis);
		waypointRoute.add(GOTWaypoint.Tiqui);
		waypointRoute.add(GOTWaypoint.Asshai);
		waypointRoute.add(GOTWaypoint.Ulthos);
		waypointRoute.add(GOTWaypoint.Zamettar);
		waypointRoute.add(GOTWaypoint.FourteenFlames);
		waypointRoute.add(GOTWaypoint.KingsLanding);
	}
}
