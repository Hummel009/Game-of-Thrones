package got.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import got.common.world.map.GOTWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GOTGuiMainMenu extends GuiMainMenu {
	private static final ResourceLocation TITLE_TEXTURE = new ResourceLocation("textures/gui/title/minecraft.png");
	private static final ResourceLocation MENU_OVERLAY = new ResourceLocation("got:textures/gui/menu_overlay.png");
	private static final List<GOTWaypoint> WAYPOINT_ROUTE = new ArrayList<>();

	private static boolean isFirstMenu = true;

	private static GOTGuiRendererMap mapRenderer;
	private static int currentWPIndex;
	private static float mapSpeed;
	private static float mapVelX;
	private static float mapVelY;

	private final GOTGuiMap mapGui;

	@SuppressWarnings("FieldMayBeFinal")
	private boolean fadeIn = isFirstMenu;
	private long firstRenderTime;

	public GOTGuiMainMenu() {
		mapGui = new GOTGuiMap();
		isFirstMenu = false;
		mapRenderer = new GOTGuiRendererMap();
		mapRenderer.setSepia(false);
		if (WAYPOINT_ROUTE.isEmpty()) {
			setupWaypoints();
			currentWPIndex = 0;
		}
		GOTWaypoint wp = WAYPOINT_ROUTE.get(currentWPIndex);
		double x = wp.getX();
		double y = wp.getY();
		mapRenderer.setMapX(x);
		mapRenderer.setMapY(y);
		mapRenderer.setPrevMapX(x);
		mapRenderer.setPrevMapY(y);
	}

	private static void setupWaypoints() {
		WAYPOINT_ROUTE.clear();
		WAYPOINT_ROUTE.add(GOTWaypoint.CASTLE_BLACK);
		WAYPOINT_ROUTE.add(GOTWaypoint.WINTERFELL);
		WAYPOINT_ROUTE.add(GOTWaypoint.RIVERRUN);
		WAYPOINT_ROUTE.add(GOTWaypoint.CASTERLY_ROCK);
		WAYPOINT_ROUTE.add(GOTWaypoint.KINGS_LANDING);
		WAYPOINT_ROUTE.add(GOTWaypoint.STORMS_END);
		WAYPOINT_ROUTE.add(GOTWaypoint.HIGHGARDEN);
		WAYPOINT_ROUTE.add(GOTWaypoint.SUNSPEAR);
		WAYPOINT_ROUTE.add(GOTWaypoint.PENTOS);
		WAYPOINT_ROUTE.add(GOTWaypoint.OLD_GHIS);
		WAYPOINT_ROUTE.add(GOTWaypoint.TIQUI);
		WAYPOINT_ROUTE.add(GOTWaypoint.ASSHAI);
		WAYPOINT_ROUTE.add(GOTWaypoint.EAST_COAST);
		WAYPOINT_ROUTE.add(GOTWaypoint.EAST_BAY);
		WAYPOINT_ROUTE.add(GOTWaypoint.SOUTH_ULTHOS);
		WAYPOINT_ROUTE.add(GOTWaypoint.RED_FORESTS);
		WAYPOINT_ROUTE.add(GOTWaypoint.ZAMETTAR);
		WAYPOINT_ROUTE.add(GOTWaypoint.FOURTEEN_FLAMES);
		WAYPOINT_ROUTE.add(GOTWaypoint.KINGS_LANDING);
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
		mapRenderer.setZoomExp(-0.3f);
		if (fadeIn) {
			float slowerFade = fade * 0.5f;
			float fadeInZoom = MathHelper.clamp_float(1.0f - slowerFade, 0.0f, 1.0f) * -1.5f;
			mapRenderer.setZoomExp(mapRenderer.getZoomExp() + fadeInZoom);
		}
		mapRenderer.setZoomStable((float) Math.pow(2.0, -0.10000000149011612));
		mapRenderer.renderMap(this, mapGui, f);
		mapRenderer.renderVignettes(this, zLevel, 2);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeIn ? MathHelper.clamp_float(1.0f - fade, 0.0f, 1.0f) : 0.0f);
		mc.getTextureManager().bindTexture(MENU_OVERLAY);
		func_152125_a(0, 0, 0.0f, 0.0f, 16, 128, width, height, 16.0f, 128.0f);
		int fadeAlphaI = MathHelper.ceiling_float_int(fadeAlpha * 255.0f) << 24;
		if ((fadeAlphaI & 0xFC000000) != 0) {
			int short1 = 274;
			int k = width / 2 - short1 / 2;
			int b0 = 30;
			mc.getTextureManager().bindTexture(TITLE_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeAlpha);
			drawTexturedModalRect(k, b0, 0, 0, 155, 44);
			drawTexturedModalRect(k + 155, b0, 0, 45, 155, 44);
			String modSubtitle = StatCollector.translateToLocal("got.menu.title");
			drawString(fontRendererObj, modSubtitle, width / 2 - fontRendererObj.getStringWidth(modSubtitle) / 2, 80, -1);
			List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
			for (int l = 0; l < brandings.size(); ++l) {
				String brd = brandings.get(l);
				if (!Strings.isNullOrEmpty(brd)) {
					drawString(fontRendererObj, brd, 2, height - (10 + l * (fontRendererObj.FONT_HEIGHT + 1)), -1);
				}
			}
			ForgeHooksClient.renderMainMenu(this, fontRendererObj, width, height);
			String copyright = "Powered by Hummel009";
			drawString(fontRendererObj, copyright, width - fontRendererObj.getStringWidth(copyright) - 2, height - 10, -1);
			String field_92025_p = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92025_p");
			String field_146972_A = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_146972_A");
			int field_92024_r = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92024_r");
			int field_92022_t = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92022_t");
			int field_92021_u = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92021_u");
			int field_92020_v = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92020_v");
			int field_92019_w = ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, "field_92019_w");
			if (field_92025_p != null && !field_92025_p.isEmpty()) {
				drawRect(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
				drawString(fontRendererObj, field_92025_p, field_92022_t, field_92021_u, -1);
				drawString(fontRendererObj, field_146972_A, (width - field_92024_r) / 2, ((List<GuiButton>) buttonList).get(0).yPosition - 12, -1);
			}
			for (GuiButton button : (List<GuiButton>) buttonList) {
				button.drawButton(mc, i, j);
			}
			for (GuiLabel label : (List<GuiLabel>) labelList) {
				label.func_146159_a(mc, i, j);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int lowerButtonMaxY = 0;
		for (GuiButton obj : (List<GuiButton>) buttonList) {
			int buttonMaxY = obj.yPosition + obj.height;
			if (buttonMaxY > lowerButtonMaxY) {
				lowerButtonMaxY = buttonMaxY;
			}
		}
		int idealMoveDown = 50;
		int lowestSuitableHeight = height - 25;
		int moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
		moveDown = Math.max(moveDown, 0);
		for (int i = 0; i < buttonList.size(); ++i) {
			GuiButton button = ((List<GuiButton>) buttonList).get(i);
			button.yPosition += moveDown;
			if (button.getClass() == GuiButton.class) {
				GOTGuiButton newButton = new GOTGuiButton(button.id, button.xPosition, button.yPosition, button.width, button.height, button.displayString);
				buttonList.set(i, newButton);
			}
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
		mapRenderer.updateTick();
		GOTWaypoint wp = WAYPOINT_ROUTE.get(currentWPIndex);
		double dx = wp.getX() - mapRenderer.getMapX();
		double dy = wp.getY() - mapRenderer.getMapY();
		double distSq = dx * dx + dy * dy;
		double dist = Math.sqrt(distSq);
		if (dist <= 12.0f) {
			++currentWPIndex;
			if (currentWPIndex >= WAYPOINT_ROUTE.size()) {
				currentWPIndex = 0;
			}
		} else {
			mapSpeed += 0.01f;
			mapSpeed = Math.min(mapSpeed, 0.8f);
			double vXNew = dx / dist * mapSpeed;
			double vYNew = dy / dist * mapSpeed;
			float a = 0.02f;
			mapVelX = (float) (mapVelX + (vXNew - mapVelX) * a);
			mapVelY = (float) (mapVelY + (vYNew - mapVelY) * a);
		}
		mapRenderer.setMapX(mapRenderer.getMapX() + mapVelX);
		mapRenderer.setMapY(mapRenderer.getMapY() + mapVelY);
	}
}