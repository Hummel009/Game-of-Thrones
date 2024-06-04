package got.client.gui;

import com.google.common.math.IntMath;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.client.GOTClientProxy;
import got.client.GOTKeyHandler;
import got.client.GOTTextures;
import got.client.GOTTickHandlerClient;
import got.common.GOTConfig;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTItems;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTControlZone;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import got.common.fellowship.GOTFellowshipClient;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.world.biome.GOTBiome;
import got.common.world.genlayer.GOTGenLayerWorld;
import got.common.world.map.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.*;
import net.minecraft.world.chunk.EmptyChunk;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

public class GOTGuiMap extends GOTGuiMenuBaseReturn {
	public static final ResourceLocation MAP_ICONS_TEXTURE = new ResourceLocation("got:textures/map/mapScreen.png");

	private static final Map<GOTDimension.DimensionRegion, GOTFaction> LAST_VIEWED_REGIONS = new EnumMap<>(GOTDimension.DimensionRegion.class);
	private static final Map<UUID, PlayerLocationInfo> PLAYER_LOCATIONS = new HashMap<>();
	private static final Collection<GOTGuiMapWidget> MAP_WIDGETS = new ArrayList<>();

	private static final ResourceLocation CONQUEST_TEXTURE = new ResourceLocation("got:textures/map/conquest.png");
	private static final ItemStack QUEST_BOOK_ICON = new ItemStack(GOTItems.questBook);

	private static final int FAC_SCROLL_WIDTH = 240;
	private static final int FAC_SCROLL_HEIGHT = 14;
	private static final int FAC_SCROLL_WIDGET_WIDTH = 17;
	private static final int ZOOM_TICKS_MAX = 6;

	private static boolean showWP = true;
	private static boolean showCWP = true;
	private static boolean fullscreen = true;
	private static boolean showHiddenSWP;

	private static List<GOTFaction> currentFactionList;

	private static GOTDimension.DimensionRegion currentRegion;
	private static GOTDimension.DimensionRegion prevRegion;

	private static int mapWidth;
	private static int mapHeight;
	private static int mapXMin;
	private static int mapXMax;
	private static int mapYMin;
	private static int mapYMax;
	private static int zoomPower;

	private final Set<GOTFaction> requestedFacGrids = EnumSet.noneOf(GOTFaction.class);
	private final Set<GOTFaction> receivedFacGrids = EnumSet.noneOf(GOTFaction.class);
	private final Map<GOTFaction, List<GOTConquestZone>> facConquestGrids = new EnumMap<>(GOTFaction.class);

	private final GOTGuiScrollPane scrollPaneWPShares = new GOTGuiScrollPane(9, 8);

	private GOTGuiMapWidget widgetAddCWP;
	private GOTGuiMapWidget widgetDelCWP;
	private GOTGuiMapWidget widgetRenameCWP;
	private GOTGuiMapWidget widgetToggleWPs;
	private GOTGuiMapWidget widgetToggleCWPs;
	private GOTGuiMapWidget widgetToggleHiddenSWPs;
	private GOTGuiMapWidget widgetZoomIn;
	private GOTGuiMapWidget widgetZoomOut;
	private GOTGuiMapWidget widgetFullScreen;
	private GOTGuiMapWidget widgetSepia;
	private GOTGuiMapWidget widgetLabels;
	private GOTGuiMapWidget widgetShareCWP;
	private GOTGuiMapWidget widgetHideSWP;
	private GOTGuiMapWidget widgetUnhideSWP;

	private List<UUID> displayedWPShareList;

	private GOTFaction conquestViewingFaction;
	private GOTFaction controlZoneFaction;

	private GOTAbstractWaypoint selectedWaypoint;
	private GOTFellowshipClient mouseOverRemoveSharedFellowship;
	private GOTGuiFellowships fellowshipDrawGUI;
	private GuiTextField nameWPTextField;
	private GuiButton buttonConquestRegions;
	private GuiButton buttonOverlayFunction;

	private String[] overlayLines;

	private boolean enableZoomOutWPFading = true;
	private boolean creatingWaypoint;
	private boolean creatingWaypointNew;
	private boolean deletingWaypoint;
	private boolean hasControlZones;
	private boolean hasOverlay;
	private boolean isConquestGrid;
	private boolean isFacScrolling;
	private boolean isMouseWithinMap;
	private boolean isPlayerOp;
	private boolean loadingConquestGrid;
	private boolean mouseControlZone;
	private boolean mouseControlZoneReduced;
	private boolean mouseInFacScroll;
	private boolean renamingWaypoint;
	private boolean sharingWaypoint;
	private boolean sharingWaypointNew;
	private boolean wasMouseDown;

	private float currentFacScroll;
	private float highestViewedConqStr;
	private float posX;
	private float posXMove;
	private float posY;
	private float posYMove;
	private float prevPosX;
	private float prevPosY;
	private float zoomExp;
	private float zoomScale;
	private float zoomScaleStable;

	private int currentFactionIndex;
	private int displayedWPShares;
	private int facScrollX;
	private int facScrollY;
	private int isMouseButtonDown;
	private int mouseXCoord;
	private int mouseZCoord;
	private int prevFactionIndex;
	private int prevMouseX;
	private int prevMouseY;
	private int tickCounter;
	private int ticksUntilRequestFac = 40;
	private int zoomTicks;
	private int prevZoomPower = zoomPower;

	public GOTGuiMap() {
		if (!GOTGenLayerWorld.loadedBiomeImage()) {
			GOTGenLayerWorld.tryLoadBiomeImage();
		}
	}

	public static void addPlayerLocationInfo(GameProfile player, double x, double z) {
		if (player.isComplete()) {
			PLAYER_LOCATIONS.put(player.getId(), new PlayerLocationInfo(player, x, z));
		}
	}

	public static void clearPlayerLocations() {
		PLAYER_LOCATIONS.clear();
	}

	private static boolean isOSRS() {
		return GOTConfig.osrsMap;
	}

	public static int[] setFakeStaticProperties(int w, int h, int xmin, int xmax, int ymin, int ymax) {
		int[] ret = {mapWidth, mapHeight, mapXMin, mapXMax, mapYMin, mapYMax};
		mapWidth = w;
		mapHeight = h;
		mapXMin = xmin;
		mapXMax = xmax;
		mapYMin = ymin;
		mapYMax = ymax;
		return ret;
	}

	public static boolean isShowWP() {
		return showWP;
	}

	public static void setShowWP(boolean showWP) {
		GOTGuiMap.showWP = showWP;
	}

	public static boolean isShowCWP() {
		return showCWP;
	}

	public static void setShowCWP(boolean showCWP) {
		GOTGuiMap.showCWP = showCWP;
	}

	public static boolean isShowHiddenSWP() {
		return showHiddenSWP;
	}

	public static void setShowHiddenSWP(boolean showHiddenSWP) {
		GOTGuiMap.showHiddenSWP = showHiddenSWP;
	}

	private static void endMapClipping() {
		GL11.glDisable(3089);
	}

	private static boolean isKeyDownAndNotMouse(KeyBinding keybinding) {
		return keybinding.getKeyCode() >= 0 && GameSettings.isKeyDown(keybinding);
	}

	private static boolean isValidWaypointName(CharSequence name) {
		return !StringUtils.isBlank(name);
	}

	private static boolean isWaypointVisible(GOTAbstractWaypoint wp) {
		if (wp instanceof GOTCustomWaypoint) {
			GOTCustomWaypoint cwp = (GOTCustomWaypoint) wp;
			return (!cwp.isShared() || !cwp.isSharedHidden() || showHiddenSWP) && showCWP;
		}
		return showWP;
	}

	private static void renderGraduatedRects(int x1, int y1, int x2, int y2, int c1, int c2, int w) {
		float[] rgb1 = new Color(c1).getColorComponents(null);
		float[] rgb2 = new Color(c2).getColorComponents(null);
		for (int l = w - 1; l >= 0; --l) {
			float f = (float) l / (w - 1);
			float r = rgb1[0] + (rgb2[0] - rgb1[0]) * f;
			float g = rgb1[1] + (rgb2[1] - rgb1[1]) * f;
			float b = rgb1[2] + (rgb2[2] - rgb1[2]) * f;
			int color = new Color(r, g, b).getRGB() - 16777216;
			drawRect(x1 - l, y1 - l, x2 + l, y2 + l, color);
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonOverlayFunction) {
				if (creatingWaypoint) {
					openOverlayCreateNew();
				} else if (creatingWaypointNew && isValidWaypointName(nameWPTextField.getText())) {
					String waypointName = nameWPTextField.getText();
					IMessage packet = new GOTPacketCreateCWP(waypointName);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					closeOverlay();
				} else if (deletingWaypoint) {
					IMessage packet = new GOTPacketDeleteCWP(selectedWaypoint);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					closeOverlay();
					selectedWaypoint = null;
				} else if (renamingWaypoint && isValidWaypointName(nameWPTextField.getText())) {
					String newName = nameWPTextField.getText();
					IMessage packet = new GOTPacketRenameCWP(selectedWaypoint, newName);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					closeOverlay();
				} else if (sharingWaypoint) {
					openOverlayShareNew();
				} else if (sharingWaypointNew && isExistingUnsharedFellowshipName(nameWPTextField.getText(), (GOTCustomWaypoint) selectedWaypoint)) {
					String fsName = nameWPTextField.getText();
					IMessage packet = new GOTPacketShareCWP(selectedWaypoint, fsName, true);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					openOverlayShare();
				}
			} else if (button == buttonConquestRegions) {
				List<GOTDimension.DimensionRegion> regionList = GOTDimension.GAME_OF_THRONES.getDimensionRegions();
				if (!regionList.isEmpty()) {
					int i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					updateCurrentDimensionAndFaction();
					setCurrentScrollFromFaction();
				}
			} else {
				super.actionPerformed(button);
			}
		}
	}

	private boolean canCreateWaypointAtPosition() {
		int minY = GOTConfig.getCustomWaypointMinY(mc.theWorld);
		return minY < 0 || mc.thePlayer.boundingBox.minY >= minY;
	}

	private boolean canTeleport() {
		if (!isGameOfThrones() || loadingConquestGrid) {
			return false;
		}
		int chunkX = MathHelper.floor_double(mc.thePlayer.posX) >> 4;
		int chunkZ = MathHelper.floor_double(mc.thePlayer.posZ) >> 4;
		if (mc.theWorld.getChunkProvider().provideChunk(chunkX, chunkZ) instanceof EmptyChunk) {
			return false;
		}
		requestIsOp();
		return isPlayerOp;
	}

	private void closeOverlay() {
		hasOverlay = false;
		overlayLines = null;
		creatingWaypoint = false;
		creatingWaypointNew = false;
		deletingWaypoint = false;
		renamingWaypoint = false;
		sharingWaypoint = false;
		sharingWaypointNew = false;
		buttonOverlayFunction.visible = false;
		buttonOverlayFunction.enabled = false;
		nameWPTextField.setText("");
	}

	private void drawFancyRect(int x1, int y1, int x2, int y2) {
		drawRect(x1, y1, x2, y2, -1073741824);
		drawHorizontalLine(x1 - 1, x2, y1 - 1, -6156032);
		drawHorizontalLine(x1 - 1, x2, y2, -6156032);
		drawVerticalLine(x1 - 1, y1 - 1, y2, -6156032);
		drawVerticalLine(x2, y1 - 1, y2, -6156032);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		String s;
		int y;
		int x;
		Tessellator tess = Tessellator.instance;
		zLevel = 0.0f;
		setupMapDimensions();
		setupZoomVariables(f);
		if (isConquestGrid) {
			loadingConquestGrid = !receivedFacGrids.contains(conquestViewingFaction);
			highestViewedConqStr = 0.0f;
			setupConquestScrollBar(i, j);
			buttonConquestRegions.displayString = currentRegion.getRegionName();
			buttonConquestRegions.enabled = true;
			buttonConquestRegions.visible = true;
		}
		posX = prevPosX;
		posY = prevPosY;
		isMouseWithinMap = i >= mapXMin && i < mapXMax && j >= mapYMin && j < mapYMax;
		if (!hasOverlay && !isFacScrolling && zoomTicks == 0 && Mouse.isButtonDown(0)) {
			if ((isMouseButtonDown == 0 || isMouseButtonDown == 1) && isMouseWithinMap) {
				if (isMouseButtonDown == 0) {
					isMouseButtonDown = 1;
				} else {
					float x2 = (i - prevMouseX) / zoomScale;
					float y2 = (j - prevMouseY) / zoomScale;
					posX -= x2;
					posY -= y2;
					if (x2 != 0.0f || y2 != 0.0f) {
						selectedWaypoint = null;
					}
				}
				prevMouseX = i;
				prevMouseY = j;
			}
		} else {
			isMouseButtonDown = 0;
		}
		prevPosX = posX;
		prevPosY = posY;
		posX += posXMove * f;
		posY += posYMove * f;
		boolean isSepia = isConquestGrid || GOTConfig.enableSepiaMap;
		if (isConquestGrid) {
			drawDefaultBackground();
		}
		if (fullscreen || isConquestGrid) {
			mc.getTextureManager().bindTexture(GOTTextures.OVERLAY_TEXTURE);
			if (conquestViewingFaction != null) {
				float[] cqColors = conquestViewingFaction.getFactionRGB();
				GL11.glColor4f(cqColors[0], cqColors[1], cqColors[2], 1.0f);
			} else {
				GL11.glColor4f(0.65f, 0.5f, 0.35f, 1.0f);
			}
			tess.startDrawingQuads();
			if (isConquestGrid) {
				int w = 8;
				int up = 22;
				int down = 54;
				tess.addVertexWithUV(mapXMin - w, mapYMax + down, zLevel, 0.0, 1.0);
				tess.addVertexWithUV(mapXMax + w, mapYMax + down, zLevel, 1.0, 1.0);
				tess.addVertexWithUV(mapXMax + w, mapYMin - up, zLevel, 1.0, 0.0);
				tess.addVertexWithUV(mapXMin - w, mapYMin - up, zLevel, 0.0, 0.0);
			} else {
				tess.addVertexWithUV(0.0, height, zLevel, 0.0, 1.0);
				tess.addVertexWithUV(width, height, zLevel, 1.0, 1.0);
				tess.addVertexWithUV(width, 0.0, zLevel, 1.0, 0.0);
				tess.addVertexWithUV(0.0, 0.0, zLevel, 0.0, 0.0);
			}
			tess.draw();
			int redW = isConquestGrid ? 2 : 4;
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			renderGraduatedRects(mapXMin - 1, mapYMin - 1, mapXMax + 1, mapYMax + 1, -6156032, -16777216, redW);
		} else {
			drawDefaultBackground();
			renderGraduatedRects(mapXMin - 1, mapYMin - 1, mapXMax + 1, mapYMax + 1, -6156032, -16777216, 4);
		}
		setupScrollBars(i, j);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int oceanColor = GOTTextures.getMapOceanColor(isSepia);
		drawRect(mapXMin, mapYMin, mapXMax, mapYMax, oceanColor);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (!isConquestGrid) {
			String title = StatCollector.translateToLocal("got.gui.map.title");
			if (fullscreen) {
				drawCenteredString(title, width / 2, 10, 16777215);
			} else {
				drawCenteredString(title, width / 2, guiTop - 30, 16777215);
			}
		}
		if (hasControlZones && GOTFaction.controlZonesEnabled(mc.theWorld)) {
			renderMapAndOverlay(isSepia, 1.0f, false);
			renderControlZone(i, j);
			GL11.glEnable(3042);
			renderMapAndOverlay(isSepia, 0.5f, true);
			GL11.glDisable(3042);
			String tooltip = null;
			if (mouseControlZone) {
				tooltip = StatCollector.translateToLocal("got.gui.map.controlZoneFull");
			} else if (mouseControlZoneReduced) {
				tooltip = StatCollector.translateToLocal("got.gui.map.controlZoneReduced");
			}
			if (tooltip != null) {
				int strWidth = mc.fontRenderer.getStringWidth(tooltip);
				int strHeight = mc.fontRenderer.FONT_HEIGHT;
				int rectX = i + 12;
				int rectY = j - 12;
				int border = 3;
				int rectWidth = strWidth + border * 2;
				int rectHeight = strHeight + border * 2;
				int mapBorder2 = 2;
				rectX = Math.max(rectX, mapXMin + mapBorder2);
				rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
				rectY = Math.max(rectY, mapYMin + mapBorder2);
				rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
				GL11.glTranslatef(0.0f, 0.0f, 300.0f);
				drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
				mc.fontRenderer.drawString(tooltip, rectX + border, rectY + border, 16777215);
				GL11.glTranslatef(0.0f, 0.0f, -300.0f);
			}
		} else {
			renderMapAndOverlay(isSepia, 1.0f, true);
			if (isConquestGrid && conquestViewingFaction != null) {
				requestConquestGridIfNeed(conquestViewingFaction);
				if (!loadingConquestGrid) {
					GL11.glEnable(3042);
					GL11.glBlendFunc(770, 771);
					setupMapClipping();
					float alphaFunc = GL11.glGetFloat(3010);
					GL11.glAlphaFunc(516, 0.005f);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					List<GOTConquestZone> allZones = facConquestGrids.get(conquestViewingFaction);
					if (allZones == null) {
						allZones = new ArrayList<>();
					}
					List<GOTConquestZone> zonesInView = new ArrayList<>();
					highestViewedConqStr = 0.0f;
					float mouseOverStr = 0.0f;
					GOTConquestZone mouseOverZone = null;
					GOTConquestGrid.ConquestEffective mouseOverEffect = null;
					for (int pass = 0; pass <= 1; ++pass) {
						if (pass != 1 || highestViewedConqStr > 0.0f) {
							List<GOTConquestZone> zoneList = pass == 0 ? allZones : zonesInView;
							for (GOTConquestZone zone : zoneList) {
								float strength = zone.getConquestStrength(conquestViewingFaction, mc.theWorld);
								int[] min = GOTConquestGrid.getMinCoordsOnMap(zone);
								int[] max = GOTConquestGrid.getMaxCoordsOnMap(zone);
								float[] minF = transformMapCoords(min[0], min[1]);
								float[] maxF = transformMapCoords(max[0], max[1]);
								float minX = minF[0];
								float maxX = maxF[0];
								float minY = minF[1];
								float maxY = maxF[1];
								if (maxX >= mapXMin && minX <= mapXMax && maxY >= mapYMin && minY <= mapYMax) {
									if (pass == 0) {
										if (strength > highestViewedConqStr) {
											highestViewedConqStr = strength;
										}
										zonesInView.add(zone);
										continue;
									}
									if (strength > 0.0f) {
										float strFrac = strength / highestViewedConqStr;
										float strAlpha = MathHelper.clamp_float(strFrac, 0.0f, 1.0f);
										strAlpha = Math.max(strAlpha, 0.1f);
										GOTConquestGrid.ConquestEffective effect = GOTConquestGrid.getConquestEffectIn(mc.theWorld, zone, conquestViewingFaction);
										int zoneColor = 0xBB0000 | Math.round(strAlpha * 255.0f) << 24;
										if (effect == GOTConquestGrid.ConquestEffective.EFFECTIVE) {
											drawFloatRect(minX, minY, maxX, maxY, zoneColor);
										} else {
											int zoneColor2 = 0x1E1E1E | Math.round(strAlpha * 255.0f) << 24;
											if (effect == GOTConquestGrid.ConquestEffective.ALLY_BOOST) {
												float zoneYSize = maxY - minY;
												int strips = 8;
												for (int l = 0; l < strips; ++l) {
													float stripYSize = zoneYSize / strips;
													drawFloatRect(minX, minY + stripYSize * l, maxX, minY + stripYSize * (l + 1), l % 2 == 0 ? zoneColor : zoneColor2);
												}
											} else if (effect == GOTConquestGrid.ConquestEffective.NO_EFFECT) {
												drawFloatRect(minX, minY, maxX, maxY, zoneColor2);
											}
										}
										if (i < minX || i >= maxX || j < minY || j >= maxY) {
											continue;
										}
										mouseOverStr = strength;
										mouseOverZone = zone;
										mouseOverEffect = effect;
									}
								}
							}
						}
					}
					GL11.glAlphaFunc(516, alphaFunc);
					if (mouseOverZone != null && i >= mapXMin && i < mapXMax && j >= mapYMin && j < mapYMax) {
						int[] min = GOTConquestGrid.getMinCoordsOnMap(mouseOverZone);
						int[] max = GOTConquestGrid.getMaxCoordsOnMap(mouseOverZone);
						float[] minF = transformMapCoords(min[0], min[1]);
						float[] maxF = transformMapCoords(max[0], max[1]);
						float minX = minF[0];
						float maxX = maxF[0];
						float minY = minF[1];
						float maxY = maxF[1];
						drawFloatRect(minX - 1.0f, minY - 1.0f, maxX + 1.0f, minY, -16777216);
						drawFloatRect(minX - 1.0f, maxY, maxX + 1.0f, maxY + 1.0f, -16777216);
						drawFloatRect(minX - 1.0f, minY, minX, maxY, -16777216);
						drawFloatRect(maxX, minY, maxX + 1.0f, maxY, -16777216);
						drawFloatRect(minX - 2.0f, minY - 2.0f, maxX + 2.0f, minY - 1.0f, -4521984);
						drawFloatRect(minX - 2.0f, maxY + 1.0f, maxX + 2.0f, maxY + 2.0f, -4521984);
						drawFloatRect(minX - 2.0f, minY - 1.0f, minX - 1.0f, maxY + 1.0f, -4521984);
						drawFloatRect(maxX + 1.0f, minY - 1.0f, maxX + 2.0f, maxY + 1.0f, -4521984);
						String tooltip = GOTAlignmentValues.formatConqForDisplay(mouseOverStr, false);
						String subtip = null;
						if (mouseOverEffect == GOTConquestGrid.ConquestEffective.ALLY_BOOST) {
							subtip = StatCollector.translateToLocalFormatted("got.gui.map.conquest.allyBoost", conquestViewingFaction.factionName());
						} else if (mouseOverEffect == GOTConquestGrid.ConquestEffective.NO_EFFECT) {
							subtip = StatCollector.translateToLocal("got.gui.map.conquest.noEffect");
						}
						int strWidth = mc.fontRenderer.getStringWidth(tooltip);
						int subWidth = subtip == null ? 0 : mc.fontRenderer.getStringWidth(subtip);
						int strHeight = mc.fontRenderer.FONT_HEIGHT;
						float guiScale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
						float subScale = guiScale <= 2.0f ? guiScale : guiScale - 1.0f;
						float subScaleRel = subScale / guiScale;
						int rectX = i + 12;
						int rectY = j - 12;
						int border = 3;
						int iconSize = 16;
						int rectWidth = border * 2 + Math.max(strWidth + iconSize + border, (int) (subWidth * subScaleRel));
						int rectHeight = Math.max(strHeight, iconSize) + border * 2;
						if (subtip != null) {
							rectHeight += border + (int) (strHeight * subScaleRel);
						}
						int mapBorder2 = 2;
						rectX = Math.max(rectX, mapXMin + mapBorder2);
						rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
						rectY = Math.max(rectY, mapYMin + mapBorder2);
						rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
						GL11.glTranslatef(0.0f, 0.0f, 300.0f);
						drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
						mc.getTextureManager().bindTexture(GOTClientProxy.ALIGNMENT_TEXTURE);
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						drawTexturedModalRect(rectX + border, rectY + border, 0, 228, iconSize, iconSize);
						mc.fontRenderer.drawString(tooltip, rectX + iconSize + border * 2, rectY + border + (iconSize - strHeight) / 2, 16777215);
						if (subtip != null) {
							GL11.glPushMatrix();
							GL11.glScalef(subScaleRel, subScaleRel, 1.0f);
							int subX = rectX + border;
							int subY = rectY + border * 2 + iconSize;
							mc.fontRenderer.drawString(subtip, Math.round(subX / subScaleRel), Math.round(subY / subScaleRel), 16777215);
							GL11.glPopMatrix();
						}
						GL11.glTranslatef(0.0f, 0.0f, -300.0f);
					}
					endMapClipping();
					GL11.glDisable(3042);
				}
			}
		}
		zLevel += 50.0f;
		GOTTextures.drawMapCompassBottomLeft(mapXMin + 12, mapYMax - 12, zLevel, 1.0);
		zLevel -= 50.0f;
		renderBeziers();
		renderPlayers(i, j);
		if (!loadingConquestGrid) {
			renderMiniQuests(mc.thePlayer, i, j);
		}
		renderWaypoints(0, i, j);
		renderLabels();
		renderWaypoints(1, i, j);
		if (!loadingConquestGrid && selectedWaypoint != null && isWaypointVisible(selectedWaypoint)) {
			if (!hasOverlay) {
				renderWaypointTooltip(selectedWaypoint, true);
			}
		} else {
			selectedWaypoint = null;
		}
		if (isConquestGrid) {
			if (loadingConquestGrid) {
				drawRect(mapXMin, mapYMin, mapXMax, mapYMax, -1429949539);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				mc.getTextureManager().bindTexture(GOTTextures.OVERLAY_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
				tess.startDrawingQuads();
				tess.addVertexWithUV(mapXMin, mapYMax, 0.0, 0.0, 1.0);
				tess.addVertexWithUV(mapXMax, mapYMax, 0.0, 1.0, 1.0);
				tess.addVertexWithUV(mapXMax, mapYMin, 0.0, 1.0, 0.0);
				tess.addVertexWithUV(mapXMin, mapYMin, 0.0, 0.0, 0.0);
				tess.draw();
				StringBuilder loadText;
				GOTConquestGrid.ConquestViewableQuery query = GOTConquestGrid.canPlayerViewConquest(mc.thePlayer, conquestViewingFaction);
				if (query.getResult() == GOTConquestGrid.ConquestViewable.CAN_VIEW) {
					loadText = new StringBuilder(StatCollector.translateToLocal("got.gui.map.conquest.wait"));
					int ellipsis = 1 + tickCounter / 10 % 3;
					for (int l = 0; l < ellipsis; ++l) {
						loadText.append('.');
					}
				} else if (query.getResult() == GOTConquestGrid.ConquestViewable.UNPLEDGED) {
					loadText = new StringBuilder(StatCollector.translateToLocal("got.gui.map.conquest.noPledge"));
				} else {
					GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
					GOTFaction pledgeFac = pd.getPledgeFaction();
					GOTFactionRank needRank = query.getNeedRank();
					String needAlign = GOTAlignmentValues.formatAlignForDisplay(needRank.getAlignment());
					String format = "";
					if (query.getResult() == GOTConquestGrid.ConquestViewable.NEED_RANK) {
						format = "got.gui.map.conquest.needRank";
					}
					loadText = new StringBuilder(StatCollector.translateToLocalFormatted(format, pledgeFac.factionName(), needRank.getFullNameWithGender(pd), needAlign));
				}
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int stringWidth = 250;
				String[] splitNewline = loadText.toString().split(Pattern.quote("\\n"));
				Collection<String> loadLines = new ArrayList<>();
				for (String line : splitNewline) {
					loadLines.addAll(fontRendererObj.listFormattedStringToWidth(line, stringWidth));
				}
				int stringX = mapXMin + mapWidth / 2;
				int stringY = (mapYMin + mapYMax) / 2 - loadLines.size() * fontRendererObj.FONT_HEIGHT / 2;
				for (String s2 : loadLines) {
					drawCenteredString(s2, stringX, stringY, 3748142);
					stringY += fontRendererObj.FONT_HEIGHT;
				}
				GL11.glDisable(3042);
			}
			mc.getTextureManager().bindTexture(CONQUEST_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(mapXMin - 8, mapYMin - 22, 0, 0, mapWidth + 16, mapHeight + 22 + 54, 512);
		}
		zLevel = 100.0f;
		if (!hasOverlay) {
			if (isGameOfThrones() && selectedWaypoint != null) {
				zLevel += 500.0f;
				GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
				boolean hasUnlocked = selectedWaypoint.hasPlayerUnlocked(mc.thePlayer);
				int ftSince = pd.getTimeSinceFT();
				int wpTimeThreshold = pd.getWaypointFTTime(selectedWaypoint, mc.thePlayer);
				int timeRemaining = wpTimeThreshold - ftSince;
				boolean canFastTravel = hasUnlocked && timeRemaining <= 0;
				String notUnlocked = "If you can read this, something has gone hideously wrong";
				if (selectedWaypoint instanceof GOTCustomWaypoint) {
					GOTCustomWaypoint cwp = (GOTCustomWaypoint) selectedWaypoint;
					if (cwp.isShared()) {
						notUnlocked = StatCollector.translateToLocal("got.gui.map.locked.shared");
					}
				} else {
					GOTWaypoint wp = (GOTWaypoint) selectedWaypoint;
					notUnlocked = wp.isCompatibleAlignment(mc.thePlayer) ? StatCollector.translateToLocal("got.gui.map.locked.region") : StatCollector.translateToLocal("got.gui.map.locked.enemy");
				}
				String conquestUnlock = pd.getPledgeFaction() == null ? "" : StatCollector.translateToLocalFormatted("got.gui.map.locked.conquerable", pd.getPledgeFaction().factionName());
				String ftPrompt = StatCollector.translateToLocalFormatted("got.gui.map.fastTravel.prompt", GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_FAST_TRAVEL.getKeyCode()));
				String ftMoreTime = StatCollector.translateToLocalFormatted("got.gui.map.fastTravel.moreTime", GOTLevelData.getHMSTime_Ticks(timeRemaining));
				String ftWaitTime = StatCollector.translateToLocalFormatted("got.gui.map.fastTravel.waitTime", GOTLevelData.getHMSTime_Ticks(wpTimeThreshold));
				if (fullscreen || isConquestGrid) {
					if (!hasUnlocked) {
						if (selectedWaypoint instanceof GOTWaypoint && ((GOTWaypoint) selectedWaypoint).isConquestUnlockable(mc.thePlayer)) {
							renderFullscreenSubtitles(notUnlocked, conquestUnlock);
						} else {
							renderFullscreenSubtitles(notUnlocked);
						}
					} else if (canFastTravel) {
						renderFullscreenSubtitles(ftPrompt, ftWaitTime);
					} else {
						renderFullscreenSubtitles(ftMoreTime, ftWaitTime);
					}
				} else {
					Collection<String> lines = new ArrayList<>();
					if (hasUnlocked) {
						if (canFastTravel) {
							lines.add(ftPrompt);
						} else {
							lines.add(ftMoreTime);
						}
						lines.add(ftWaitTime);
					} else {
						lines.add(notUnlocked);
						if (selectedWaypoint instanceof GOTWaypoint && ((GOTWaypoint) selectedWaypoint).isConquestUnlockable(mc.thePlayer)) {
							lines.add(conquestUnlock);
						}
					}
					int stringHeight = fontRendererObj.FONT_HEIGHT;
					int rectWidth = mapWidth;
					int border = 3;
					int rectHeight = border + (stringHeight + border) * lines.size();
					int x3 = mapXMin + mapWidth / 2 - rectWidth / 2;
					int y3 = mapYMax + 10;
					int strX = mapXMin + mapWidth / 2;
					int strY = y3 + border;
					drawFancyRect(x3, y3, x3 + rectWidth, y3 + rectHeight);
					for (String s3 : lines) {
						drawCenteredString(s3, strX, strY, 16777215);
						strY += stringHeight + border;
					}
				}
				zLevel -= 500.0f;
			} else if (isMouseWithinMap) {
				zLevel += 500.0f;
				float biomePosX = posX + (i - mapXMin - (float) mapWidth / 2) / zoomScale;
				float biomePosZ = posY + (j - mapYMin - (float) mapHeight / 2) / zoomScale;
				int biomePosX_int = MathHelper.floor_double(biomePosX);
				GOTBiome biome = GOTGenLayerWorld.getBiomeOrOcean(biomePosX_int, MathHelper.floor_double(biomePosZ));
				mouseXCoord = Math.round((biomePosX - GOTGenLayerWorld.ORIGIN_X) * GOTGenLayerWorld.SCALE);
				mouseZCoord = Math.round((biomePosZ - GOTGenLayerWorld.ORIGIN_Z) * GOTGenLayerWorld.SCALE);
				String biomeName = biome.getBiomeDisplayName();
				String coords = StatCollector.translateToLocalFormatted("got.gui.map.coords", mouseXCoord, mouseZCoord);
				String teleport = StatCollector.translateToLocalFormatted("got.gui.map.tp", GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_MAP_TELEPORT.getKeyCode()));
				int stringHeight = fontRendererObj.FONT_HEIGHT;
				if (fullscreen || isConquestGrid) {
					renderFullscreenSubtitles(biomeName, coords);
					if (canTeleport()) {
						GL11.glPushMatrix();
						if (isConquestGrid) {
							GL11.glTranslatef((float) (mapXMax - mapXMin) / 2 - 8 - (float) fontRendererObj.getStringWidth(teleport) / 2, 0.0f, 0.0f);
						} else {
							GL11.glTranslatef((float) width / 2 - 30 - (float) fontRendererObj.getStringWidth(teleport) / 2, 0.0f, 0.0f);
						}
						renderFullscreenSubtitles(teleport);
						GL11.glPopMatrix();
					}
				} else {
					int rectWidth = mapWidth;
					int border = 3;
					int rectHeight = border * 3 + stringHeight * 2;
					if (canTeleport()) {
						rectHeight += (stringHeight + border) * 2;
					}
					int x4 = mapXMin + mapWidth / 2 - rectWidth / 2;
					int y4 = mapYMax + 10;
					drawFancyRect(x4, y4, x4 + rectWidth, y4 + rectHeight);
					int strX = mapXMin + mapWidth / 2;
					int strY = y4 + border;
					drawCenteredString(biomeName, strX, strY, 16777215);
					drawCenteredString(coords, strX, strY += stringHeight + border, 16777215);
					if (canTeleport()) {
						drawCenteredString(teleport, strX, strY + (stringHeight + border) * 2, 16777215);
					}
				}
				zLevel -= 500.0f;
			}
		}
		if (isConquestGrid) {
			s = StatCollector.translateToLocalFormatted("got.gui.map.conquest.title", conquestViewingFaction.factionName());
			x = mapXMin + mapWidth / 2;
			y = mapYMin - 14;
			GOTTickHandlerClient.drawAlignmentText(fontRendererObj, x - fontRendererObj.getStringWidth(s) / 2, y, s, 1.0f);
			if (!loadingConquestGrid) {
				int keyBorder = 8;
				int keyWidth = 24;
				int keyHeight = 12;
				int iconSize = 16;
				int iconGap = keyBorder / 2;
				float guiScale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
				float labelScale = guiScale <= 2.0f ? guiScale : guiScale - 1.0f;
				float labelScaleRel = labelScale / guiScale;
				mc.getTextureManager().bindTexture(GOTClientProxy.ALIGNMENT_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				drawTexturedModalRect(mapXMax - keyBorder - iconSize, mapYMax - keyBorder - iconSize, 0, 228, iconSize, iconSize);
				for (int pass = 0; pass <= 1; ++pass) {
					for (int l = 0; l <= 10; ++l) {
						float frac = (10 - l) / 10.0f;
						float strFrac = frac * highestViewedConqStr;
						int x1 = mapXMax - keyBorder - iconSize - iconGap - l * keyWidth;
						int x0 = x1 - keyWidth;
						int y1 = mapYMax - keyBorder - (iconSize - keyHeight) / 2;
						int y0 = y1 - keyHeight;
						if (pass == 0) {
							int color = 0xBB0000 | Math.round(frac * 255.0f) << 24;
							drawRect(x0, y0, x1, y1, color);
						} else if (l % 2 == 0) {
							String keyLabel = GOTAlignmentValues.formatConqForDisplay(strFrac, false);
							int strX = (int) ((x0 + (float) keyWidth / 2) / labelScaleRel);
							int strY = (int) ((y0 + (float) keyHeight / 2) / labelScaleRel) - fontRendererObj.FONT_HEIGHT / 2;
							GL11.glPushMatrix();
							GL11.glScalef(labelScaleRel, labelScaleRel, 1.0f);
							drawCenteredString(keyLabel, strX, strY, 16777215);
							GL11.glPopMatrix();
						}
					}
				}
			}
			if (hasConquestScrollBar()) {
				mc.getTextureManager().bindTexture(GOTGuiFactions.FACTIONS_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				drawTexturedModalRect(facScrollX, facScrollY, 0, 128, FAC_SCROLL_WIDTH, FAC_SCROLL_HEIGHT);
				int factions = currentFactionList.size();
				int facScrollBorder = 1;
				for (int index = 0; index < factions; ++index) {
					GOTFaction faction = currentFactionList.get(index);
					float[] factionColors = faction.getFactionRGB();
					float shade = 0.6f;
					GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
					float xMin = (float) index / factions;
					float xMax = (float) (index + 1) / factions;
					xMin = facScrollX + facScrollBorder + xMin * (FAC_SCROLL_WIDTH - facScrollBorder * 2);
					xMax = facScrollX + facScrollBorder + xMax * (FAC_SCROLL_WIDTH - facScrollBorder * 2);
					float yMin = facScrollY + facScrollBorder;
					float yMax = facScrollY + FAC_SCROLL_HEIGHT - facScrollBorder;
					float minU = facScrollBorder / 256.0f;
					float maxU = (FAC_SCROLL_WIDTH - facScrollBorder) / 256.0f;
					float minV = (128 + facScrollBorder) / 256.0f;
					float maxV = (128 + FAC_SCROLL_HEIGHT - facScrollBorder) / 256.0f;
					tess.startDrawingQuads();
					tess.addVertexWithUV(xMin, yMax, zLevel, minU, maxV);
					tess.addVertexWithUV(xMax, yMax, zLevel, maxU, maxV);
					tess.addVertexWithUV(xMax, yMin, zLevel, maxU, minV);
					tess.addVertexWithUV(xMin, yMin, zLevel, minU, minV);
					tess.draw();
				}
				mc.getTextureManager().bindTexture(GOTGuiFactions.FACTIONS_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int scroll = (int) (currentFacScroll * (FAC_SCROLL_WIDTH - facScrollBorder * 2 - FAC_SCROLL_WIDGET_WIDTH));
				int facScrollWidgetHeight = 12;
				drawTexturedModalRect(facScrollX + facScrollBorder + scroll, facScrollY + facScrollBorder, 0, 142, FAC_SCROLL_WIDGET_WIDTH, facScrollWidgetHeight);
			}
		}
		if (!hasOverlay && hasControlZones) {
			s = StatCollector.translateToLocalFormatted("got.gui.map.controlZones", controlZoneFaction.factionName());
			x = mapXMin + mapWidth / 2;
			y = mapYMin + 20;
			GOTTickHandlerClient.drawAlignmentText(fontRendererObj, x - fontRendererObj.getStringWidth(s) / 2, y, s, 1.0f);
			if (!GOTFaction.controlZonesEnabled(mc.theWorld)) {
				s = StatCollector.translateToLocal("got.gui.map.controlZonesDisabled");
				GOTTickHandlerClient.drawAlignmentText(fontRendererObj, x - fontRendererObj.getStringWidth(s) / 2, mapYMin + mapHeight / 2, s, 1.0f);
			}
		}
		boolean buttonVisible = buttonOverlayFunction.visible;
		buttonOverlayFunction.visible = false;
		super.drawScreen(i, j, f);
		buttonOverlayFunction.visible = buttonVisible;
		renderMapWidgets(i, j);
		if (hasOverlay) {
			GL11.glTranslatef(0.0f, 0.0f, 500.0f);
			int overlayBorder = 10;
			if (fullscreen) {
				overlayBorder = 40;
			}
			int rectX0 = mapXMin + overlayBorder;
			int rectY0 = mapYMin + overlayBorder;
			int rectX1 = mapXMax - overlayBorder;
			int rectY1 = mapYMax - overlayBorder;
			drawFancyRect(rectX0, rectY0, rectX1, rectY1);
			if (overlayLines != null) {
				int stringX = mapXMin + mapWidth / 2;
				int stringY = rectY0 + 3 + mc.fontRenderer.FONT_HEIGHT;
				int stringWidth = (int) ((mapWidth - overlayBorder * 2) * 0.75f);
				Collection<String> displayLines = new ArrayList<>();
				for (String s4 : overlayLines) {
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(s4, stringWidth));
				}
				for (String s5 : displayLines) {
					drawCenteredString(s5, stringX, stringY, 16777215);
					stringY += mc.fontRenderer.FONT_HEIGHT;
				}
				stringY += mc.fontRenderer.FONT_HEIGHT;
				if (sharingWaypoint) {
					fellowshipDrawGUI.clearMouseOverFellowship();
					mouseOverRemoveSharedFellowship = null;
					int iconWidth = 8;
					int iconGap = 8;
					int fullWidth = fellowshipDrawGUI.getSizeX() + iconGap + iconWidth;
					int fsX = mapXMin + mapWidth / 2 - fullWidth / 2;
					int fsY = stringY;
					scrollPaneWPShares.setPaneX0(fsX);
					scrollPaneWPShares.setScrollBarX0(fsX + fullWidth + 2 + 2);
					scrollPaneWPShares.setPaneY0(fsY);
					scrollPaneWPShares.setPaneY1(fsY + (mc.fontRenderer.FONT_HEIGHT + 5) * displayedWPShares);
					scrollPaneWPShares.mouseDragScroll(i, j);
					int[] sharesMinMax = scrollPaneWPShares.getMinMaxIndices(displayedWPShareList, displayedWPShares);
					for (int index = sharesMinMax[0]; index <= sharesMinMax[1]; ++index) {
						UUID fsID = displayedWPShareList.get(index);
						GOTFellowshipClient fs = GOTLevelData.getData(mc.thePlayer).getClientFellowshipByID(fsID);
						if (fs != null) {
							fellowshipDrawGUI.drawFellowshipEntry(fs, fsX, fsY, i, j, false, fullWidth);
							int iconRemoveX = fsX + fellowshipDrawGUI.getSizeX() + iconGap;
							int iconRemoveY = fsY;
							boolean mouseOverRemove = false;
							if (fs == fellowshipDrawGUI.getMouseOverFellowship()) {
								mouseOverRemove = i >= iconRemoveX && i <= iconRemoveX + iconWidth && j >= iconRemoveY && j <= iconRemoveY + iconWidth;
								if (mouseOverRemove) {
									mouseOverRemoveSharedFellowship = fs;
								}
							}
							mc.getTextureManager().bindTexture(GOTGuiFellowships.ICONS_TEXTURES);
							GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
							drawTexturedModalRect(iconRemoveX, iconRemoveY, 8, 16 + (mouseOverRemove ? 0 : iconWidth), iconWidth, iconWidth);
							fsY = stringY += mc.fontRenderer.FONT_HEIGHT + 5;
						}
					}
					if (scrollPaneWPShares.isHasScrollBar()) {
						scrollPaneWPShares.drawScrollBar();
					}
				}
				if (creatingWaypointNew || renamingWaypoint || sharingWaypointNew) {
					nameWPTextField.xPosition = (rectX0 + rectX1) / 2 - nameWPTextField.width / 2;
					nameWPTextField.yPosition = stringY;
					GL11.glPushMatrix();
					GL11.glTranslatef(0.0f, 0.0f, zLevel);
					nameWPTextField.drawTextBox();
					GL11.glPopMatrix();
					if (sharingWaypointNew && isFellowshipAlreadyShared(nameWPTextField.getText(), (GOTCustomWaypoint) selectedWaypoint)) {
						String alreadyShared = StatCollector.translateToLocal("got.gui.map.customWaypoint.shareNew.already");
						int sx = nameWPTextField.xPosition + nameWPTextField.width + 6;
						int sy = nameWPTextField.yPosition + nameWPTextField.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
						fontRendererObj.drawString(alreadyShared, sx, sy, 16711680);
					}
					stringY += nameWPTextField.height + mc.fontRenderer.FONT_HEIGHT;
				}
				stringY += mc.fontRenderer.FONT_HEIGHT;
				if (buttonOverlayFunction.visible) {
					buttonOverlayFunction.enabled = !creatingWaypointNew && !renamingWaypoint || isValidWaypointName(nameWPTextField.getText());
					if (sharingWaypointNew) {
						buttonOverlayFunction.enabled = isExistingUnsharedFellowshipName(nameWPTextField.getText(), (GOTCustomWaypoint) selectedWaypoint);
					}
					buttonOverlayFunction.xPosition = stringX - buttonOverlayFunction.width / 2;
					buttonOverlayFunction.yPosition = stringY;
					if (sharingWaypoint) {
						buttonOverlayFunction.yPosition = rectY1 - 3 - mc.fontRenderer.FONT_HEIGHT - buttonOverlayFunction.height;
					}
					buttonOverlayFunction.drawButton(mc, i, j);
				}
			}
			GL11.glTranslatef(0.0f, 0.0f, -500.0f);
		}
	}

	private GOTFellowshipClient getFellowshipByName(String name) {
		String fsName = StringUtils.strip(name);
		return GOTLevelData.getData(mc.thePlayer).getClientFellowshipByName(fsName);
	}

	private void handleMapKeyboardMovement() {
		prevPosX += posXMove;
		prevPosY += posYMove;
		posXMove = 0.0f;
		posYMove = 0.0f;
		if (!hasOverlay) {
			float move = 12.0f / (float) Math.pow(zoomScale, 0.800000011920929);
			if (isKeyDownAndNotMouse(mc.gameSettings.keyBindLeft) || Keyboard.isKeyDown(203)) {
				posXMove -= move;
			}
			if (isKeyDownAndNotMouse(mc.gameSettings.keyBindRight) || Keyboard.isKeyDown(205)) {
				posXMove += move;
			}
			if (isKeyDownAndNotMouse(mc.gameSettings.keyBindForward) || Keyboard.isKeyDown(200)) {
				posYMove -= move;
			}
			if (isKeyDownAndNotMouse(mc.gameSettings.keyBindBack) || Keyboard.isKeyDown(208)) {
				posYMove += move;
			}
			if (posXMove != 0.0f || posYMove != 0.0f) {
				selectedWaypoint = null;
			}
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int k = Mouse.getEventDWheel();
		if (isConquestGrid && hasConquestScrollBar() && mouseInFacScroll && k != 0) {
			if (k < 0) {
				currentFactionIndex = Math.min(currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
			} else {
				currentFactionIndex = Math.max(currentFactionIndex - 1, 0);
			}
			setCurrentScrollFromFaction();
			return;
		}
		if (!hasOverlay && zoomTicks == 0) {
			if (k < 0 && zoomPower > -3) {
				zoomOut();
				return;
			}
			if (k > 0 && zoomPower < 4) {
				zoomIn();
				return;
			}
		}
		if (hasOverlay && k != 0) {
			k = Integer.signum(k);
			if (scrollPaneWPShares.isHasScrollBar() && scrollPaneWPShares.isMouseOver()) {
				int l = displayedWPShareList.size() - displayedWPShares;
				scrollPaneWPShares.mouseWheelScroll(k, l);
			}
		}
	}

	private boolean hasConquestScrollBar() {
		return isConquestGrid && currentFactionList.size() > 1;
	}

	private boolean hasMapLabels() {
		if (isConquestGrid) {
			return GOTConfig.mapLabelsConquest;
		}
		return GOTConfig.mapLabels;
	}

	@Override
	public void initGui() {
		sizeX = 256;
		sizeY = 256;
		super.initGui();
		if (fullscreen) {
			int midX = width / 2;
			int d = 100;
			buttonMenuReturn.xPosition = midX - d - buttonMenuReturn.width;
			buttonMenuReturn.yPosition = 4;
		}
		if (isConquestGrid || hasControlZones) {
			buttonList.remove(buttonMenuReturn);
		}
		setupMapWidgets();
		if (isConquestGrid) {
			loadingConquestGrid = true;
			setupMapDimensions();
			conquestViewingFaction = GOTLevelData.getData(mc.thePlayer).getPledgeFaction();
			if (conquestViewingFaction == null) {
				conquestViewingFaction = GOTLevelData.getData(mc.thePlayer).getViewingFaction();
			}
			prevRegion = currentRegion = conquestViewingFaction.getFactionRegion();
			currentFactionList = currentRegion.getFactionList();
			prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(conquestViewingFaction);
			LAST_VIEWED_REGIONS.put(currentRegion, conquestViewingFaction);
			facScrollX = mapXMin;
			facScrollY = mapYMax + 8;
			setCurrentScrollFromFaction();
			buttonConquestRegions = new GOTGuiButton(200, mapXMax - 120, mapYMax + 5, 120, 20, "");
			buttonList.add(buttonConquestRegions);
		}
		if (hasControlZones) {
			setupMapDimensions();
			int[] zoneBorders = controlZoneFaction.calculateFullControlZoneWorldBorders();
			int xMin = zoneBorders[0];
			int xMax = zoneBorders[1];
			int zMin = zoneBorders[2];
			int zMax = zoneBorders[3];
			float x = (xMin + xMax) / 2.0f;
			float z = (zMin + zMax) / 2.0f;
			posX = x / GOTGenLayerWorld.SCALE + GOTGenLayerWorld.ORIGIN_X;
			posY = z / GOTGenLayerWorld.SCALE + GOTGenLayerWorld.ORIGIN_Z;
			int zoneWidth = xMax - xMin;
			int zoneHeight = zMax - zMin;
			double mapZoneWidth = (double) zoneWidth / GOTGenLayerWorld.SCALE;
			double mapZoneHeight = (double) zoneHeight / GOTGenLayerWorld.SCALE;
			int zoomPowerWidth = MathHelper.floor_double(Math.log(mapWidth / mapZoneWidth) / Math.log(2.0));
			int zoomPowerHeight = MathHelper.floor_double(Math.log(mapHeight / mapZoneHeight) / Math.log(2.0));
			prevZoomPower = zoomPower = Math.min(zoomPowerWidth, zoomPowerHeight);
		} else if (mc.thePlayer != null) {
			posX = (float) (mc.thePlayer.posX / GOTGenLayerWorld.SCALE) + GOTGenLayerWorld.ORIGIN_X;
			posY = (float) (mc.thePlayer.posZ / GOTGenLayerWorld.SCALE) + GOTGenLayerWorld.ORIGIN_Z;
		}
		prevPosX = posX;
		prevPosY = posY;
		setupZoomVariables(1.0f);
		buttonOverlayFunction = new GuiButton(0, 0, 0, 160, 20, "");
		buttonOverlayFunction.visible = false;
		buttonOverlayFunction.enabled = false;
		buttonList.add(buttonOverlayFunction);
		nameWPTextField = new GuiTextField(fontRendererObj, mapXMin + mapWidth / 2 - 80, mapYMin + 50, 160, 20);
		fellowshipDrawGUI = new GOTGuiFellowships();
		fellowshipDrawGUI.setWorldAndResolution(mc, width, height);
		if (mc.currentScreen == this) {
			IMessage packet = new GOTPacketClientMQEvent(GOTPacketClientMQEvent.ClientMQEvent.MAP);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	private boolean isExistingFellowshipName(String name) {
		GOTFellowshipClient fs = getFellowshipByName(name);
		return fs != null;
	}

	private boolean isExistingUnsharedFellowshipName(String name, GOTCustomWaypoint waypoint) {
		GOTFellowshipClient fs = getFellowshipByName(name);
		return fs != null && !waypoint.hasSharedFellowship(fs.getFellowshipID());
	}

	private boolean isFellowshipAlreadyShared(String name, GOTCustomWaypoint waypoint) {
		return isExistingFellowshipName(name) && !isExistingUnsharedFellowshipName(name, waypoint);
	}

	private boolean isGameOfThrones() {
		return mc.thePlayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID();
	}

	@Override
	public void keyTyped(char c, int i) {
		if (hasOverlay) {
			if (creatingWaypointNew && nameWPTextField.textboxKeyTyped(c, i) || renamingWaypoint && nameWPTextField.textboxKeyTyped(c, i)) {
				return;
			}
			if (sharingWaypointNew && nameWPTextField.textboxKeyTyped(c, i)) {
				return;
			}
			if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
				if (creatingWaypointNew) {
					openOverlayCreate();
				} else if (sharingWaypointNew) {
					openOverlayShare();
				} else {
					closeOverlay();
				}
			}
		} else {
			if (!loadingConquestGrid) {
				GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
				if (i == GOTKeyHandler.KEY_BINDING_FAST_TRAVEL.getKeyCode() && isGameOfThrones() && selectedWaypoint != null && selectedWaypoint.hasPlayerUnlocked(mc.thePlayer) && pd.getTimeSinceFT() >= pd.getWaypointFTTime(selectedWaypoint, mc.thePlayer)) {
					IMessage packet = new GOTPacketFastTravel(selectedWaypoint);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					mc.thePlayer.closeScreen();
					return;
				}
				if (selectedWaypoint == null && i == GOTKeyHandler.KEY_BINDING_MAP_TELEPORT.getKeyCode() && isMouseWithinMap && canTeleport()) {
					IMessage packet = new GOTPacketMapTp(mouseXCoord, mouseZCoord);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					mc.thePlayer.closeScreen();
					return;
				}
			}
			if (hasControlZones && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
				Minecraft.getMinecraft().displayGuiScreen(new GOTGuiFactions());
				return;
			}
			super.keyTyped(c, i);
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		IMessage packet;
		GOTGuiMapWidget mouseWidget = null;
		for (GOTGuiMapWidget widget : MAP_WIDGETS) {
			if (widget.isMouseOver(i - mapXMin, j - mapYMin, mapWidth, mapHeight)) {
				mouseWidget = widget;
				break;
			}
		}
		if (hasOverlay && (creatingWaypointNew || renamingWaypoint || sharingWaypointNew)) {
			nameWPTextField.mouseClicked(i, j, k);
		}
		if (hasOverlay && k == 0 && sharingWaypoint && mouseOverRemoveSharedFellowship != null) {
			String fsName = mouseOverRemoveSharedFellowship.getName();
			packet = new GOTPacketShareCWP(selectedWaypoint, fsName, false);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			return;
		}
		if (!hasOverlay && k == 0 && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint) {
			GOTCustomWaypoint cwp = (GOTCustomWaypoint) selectedWaypoint;
			if (cwp.isShared()) {
				if (mouseWidget == widgetHideSWP) {
					packet = new GOTPacketCWPSharedHide(cwp, true);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					selectedWaypoint = null;
					return;
				}
				if (mouseWidget == widgetUnhideSWP) {
					packet = new GOTPacketCWPSharedHide(cwp, false);
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
					return;
				}
			} else {
				if (mouseWidget == widgetDelCWP) {
					openOverlayDelete();
					return;
				}
				if (mouseWidget == widgetRenameCWP) {
					openOverlayRename();
					return;
				}
				if (mouseWidget == widgetShareCWP) {
					openOverlayShare();
					return;
				}
			}
		}
		if (!hasOverlay && k == 0 && isGameOfThrones() && mouseWidget == widgetAddCWP && canCreateWaypointAtPosition()) {
			openOverlayCreate();
			return;
		}
		if (!hasOverlay && k == 0) {
			if (mouseWidget == widgetToggleWPs) {
				showWP = !showWP;
				GOTClientProxy.sendClientInfoPacket(null, null);
				return;
			}
			if (mouseWidget == widgetToggleCWPs) {
				showCWP = !showCWP;
				GOTClientProxy.sendClientInfoPacket(null, null);
				return;
			}
			if (mouseWidget == widgetToggleHiddenSWPs) {
				showHiddenSWP = !showHiddenSWP;
				GOTClientProxy.sendClientInfoPacket(null, null);
				return;
			}
			if (zoomTicks == 0) {
				if (mouseWidget == widgetZoomIn && zoomPower < 4) {
					zoomIn();
					return;
				}
				if (mouseWidget == widgetZoomOut && zoomPower > -3) {
					zoomOut();
					return;
				}
			}
			if (mouseWidget == widgetFullScreen) {
				fullscreen = !fullscreen;
				ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());
				return;
			}
			if (mouseWidget == widgetSepia) {
				GOTConfig.toggleSepia();
				return;
			}
			if (mouseWidget == widgetLabels) {
				toggleMapLabels();
				return;
			}
		}
		if (!hasOverlay && !loadingConquestGrid && k == 0 && isMouseWithinMap) {
			selectedWaypoint = null;
			double distanceSelectedWP = Double.MAX_VALUE;
			List<GOTAbstractWaypoint> waypoints = GOTLevelData.getData(mc.thePlayer).getAllAvailableWaypoints();
			for (GOTAbstractWaypoint waypoint : waypoints) {
				float dx;
				float dy;
				double distToWP;
				float[] pos;
				boolean unlocked = waypoint.hasPlayerUnlocked(mc.thePlayer);
				boolean hidden = waypoint.isHidden();
				if (isWaypointVisible(waypoint) && (!hidden || unlocked) && (distToWP = Math.sqrt((dx = (pos = transformCoords(waypoint.getCoordX(), waypoint.getCoordZ()))[0] - i) * dx + (dy = pos[1] - j) * dy)) <= 5.0 && distToWP <= distanceSelectedWP) {
					selectedWaypoint = waypoint;
					distanceSelectedWP = distToWP;
				}
			}
		}
		super.mouseClicked(i, j, k);
	}

	private void openOverlayCreate() {
		closeOverlay();
		hasOverlay = true;
		creatingWaypoint = true;
		int numWaypoints = GOTLevelData.getData(mc.thePlayer).getCustomWaypoints().size();
		int maxWaypoints = GOTLevelData.getData(mc.thePlayer).getMaxCustomWaypoints();
		int remaining = maxWaypoints - numWaypoints;
		if (remaining <= 0) {
			overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.allUsed.1", maxWaypoints), "", StatCollector.translateToLocal("got.gui.map.customWaypoint.allUsed.2")};
		} else {
			overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.create.1", numWaypoints, maxWaypoints), "", StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.create.2")};
			buttonOverlayFunction.visible = true;
			buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.create.button");
		}
	}

	private void openOverlayCreateNew() {
		closeOverlay();
		hasOverlay = true;
		creatingWaypointNew = true;
		overlayLines = new String[]{StatCollector.translateToLocal("got.gui.map.customWaypoint.createNew.1")};
		buttonOverlayFunction.visible = true;
		buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.createNew.button");
	}

	private void openOverlayDelete() {
		closeOverlay();
		hasOverlay = true;
		deletingWaypoint = true;
		overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.delete.1", selectedWaypoint.getDisplayName())};
		buttonOverlayFunction.visible = true;
		buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.delete.button");
	}

	private void openOverlayRename() {
		closeOverlay();
		hasOverlay = true;
		renamingWaypoint = true;
		overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.rename.1", selectedWaypoint.getDisplayName())};
		buttonOverlayFunction.visible = true;
		buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.rename.button");
	}

	private void openOverlayShare() {
		closeOverlay();
		hasOverlay = true;
		sharingWaypoint = true;
		overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.share.1", selectedWaypoint.getDisplayName())};
		buttonOverlayFunction.visible = true;
		buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.share.button");
	}

	private void openOverlayShareNew() {
		closeOverlay();
		hasOverlay = true;
		sharingWaypointNew = true;
		overlayLines = new String[]{StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.shareNew.1", selectedWaypoint.getDisplayName())};
		buttonOverlayFunction.visible = true;
		buttonOverlayFunction.displayString = StatCollector.translateToLocal("got.gui.map.customWaypoint.shareNew.button");
	}

	public void receiveConquestGrid(GOTFaction conqFac, List<GOTConquestZone> allZones) {
		if (isConquestGrid) {
			receivedFacGrids.add(conqFac);
			facConquestGrids.put(conqFac, allZones);
		}
	}

	private void renderBeziers() {
		if (!showWP && !showCWP || !GOTFixedStructures.hasMapFeatures(mc.theWorld)) {
			return;
		}
		renderBeziers(hasMapLabels());
	}

	public void renderBeziers(boolean labels) {
		float bezierZoomlerp = (zoomExp + 3.3f) / 2.2f;
		bezierZoomlerp = Math.min(bezierZoomlerp, 1.0f);
		if (!enableZoomOutWPFading) {
			bezierZoomlerp = 1.0f;
		}
		if (bezierZoomlerp > 0.0f) {
			for (GOTBeziers bezier : GOTBeziers.CONTENT) {
				int interval = Math.round(400.0f / zoomScaleStable);
				interval = Math.max(interval, 1);
				for (int i = 0; i < bezier.getBezierPoints().length; i += interval) {
					int clip;
					GOTBeziers.BezierPoint point = bezier.getBezierPoints()[i];
					float[] pos = transformCoords(point.getX(), point.getZ());
					float x = pos[0];
					float y = pos[1];
					if (x >= mapXMin && x < mapXMax && y >= mapYMin && y < mapYMax) {
						double bezierWidth = 1.0;
						int bezierColor = 0;
						float bezierAlpha = bezierZoomlerp;
						if (isOSRS()) {
							bezierWidth = 3.0 * zoomScale;
							bezierColor = 6575407;
							bezierAlpha = 1.0f;
						}
						double bezierWidthLess1 = bezierWidth - 1.0;
						GL11.glDisable(3553);
						GL11.glEnable(3042);
						GL11.glBlendFunc(770, 771);
						Tessellator tessellator = Tessellator.instance;
						tessellator.startDrawingQuads();
						tessellator.setColorRGBA_I(bezierColor, (int) (bezierAlpha * 255.0f));
						tessellator.addVertex(x - bezierWidthLess1, y + bezierWidth, zLevel);
						tessellator.addVertex(x + bezierWidth, y + bezierWidth, zLevel);
						tessellator.addVertex(x + bezierWidth, y - bezierWidthLess1, zLevel);
						tessellator.addVertex(x - bezierWidthLess1, y - bezierWidthLess1, zLevel);
						tessellator.draw();
						GL11.glDisable(3042);
						GL11.glEnable(3553);
					}
					if (labels && x >= mapXMin - (clip = 200) && x <= mapXMax + clip && y >= mapYMin - clip && y <= mapYMax + clip) {
						float zoomlerp = (zoomExp + 1.0f) / 4.0f;
						float scale = zoomlerp = Math.min(zoomlerp, 1.0f);
						int nameWidth = fontRendererObj.getStringWidth(null);
						int nameInterval = (int) ((nameWidth * 2 + 100) * 200.0f / zoomScaleStable);
						if (i % nameInterval < interval) {
							boolean endNear = false;
							double dMax = (nameWidth / 2.0 + 25.0) * scale;
							double dMaxSq = dMax * dMax;
							for (GOTBeziers.BezierPoint end : bezier.getEndpoints()) {
								float dy;
								float[] endPos = transformCoords(end.getX(), end.getZ());
								float endX = endPos[0];
								float dx = x - endX;
								double dSq = dx * dx + (dy = y - endPos[1]) * dy;
								if (dSq < dMaxSq) {
									endNear = true;
									break;
								}
							}
							if (!endNear && zoomlerp > 0.0f) {
								setupMapClipping();
								GL11.glPushMatrix();
								GL11.glTranslatef(x, y, 0.0f);
								GL11.glScalef(scale, scale, scale);
								GOTBeziers.BezierPoint nextPoint = bezier.getBezierPoints()[Math.min(i + 1, bezier.getBezierPoints().length - 1)];
								GOTBeziers.BezierPoint prevPoint = bezier.getBezierPoints()[Math.max(i - 1, 0)];
								double grad = (nextPoint.getZ() - prevPoint.getZ()) / (nextPoint.getX() - prevPoint.getX());
								float angle = (float) Math.atan(grad);
								angle = (float) Math.toDegrees(angle);
								if (Math.abs(angle) > 90.0f) {
									angle += 180.0f;
								}
								GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
								int alphaI = GOTClientProxy.getAlphaInt(zoomlerp * 0.8f);
								GL11.glEnable(3042);
								GL11.glBlendFunc(770, 771);
								int strX = -nameWidth / 2;
								int strY = -15;
								fontRendererObj.drawString(null, strX + 1, strY + 1, alphaI << 24);
								fontRendererObj.drawString(null, strX, strY, 16777215 + (alphaI << 24));
								GL11.glDisable(3042);
								GL11.glPopMatrix();
								endMapClipping();
							}
						}
					}
				}
			}
		}
	}

	private void renderControlZone(int mouseX, int mouseY) {
		List<GOTControlZone> controlZones;
		mouseControlZone = false;
		mouseControlZoneReduced = false;
		GOTFaction faction = controlZoneFaction;
		if (faction.getFactionDimension() == GOTDimension.GAME_OF_THRONES && !(controlZones = faction.getControlZones()).isEmpty()) {
			Tessellator tessellator = Tessellator.instance;
			setupMapClipping();
			GL11.glDisable(3553);
			for (int pass = 0; pass <= 2; ++pass) {
				int color = 16711680;
				if (pass == 1) {
					color = 5570560;
				}
				if (pass == 0) {
					color = 16733525;
				}
				for (GOTControlZone zone : controlZones) {
					float dx;
					float[] trans;
					float dy;
					float rScaled;
					float radius = zone.getRadius();
					if (pass == 2) {
						radius -= 1.0f;
					}
					if (pass == 0) {
						radius = zone.getRadius() + 50;
					}
					float radiusWorld = GOTWaypoint.mapToWorldR(radius);
					tessellator.startDrawing(9);
					tessellator.setColorOpaque_I(color);
					int sides = 100;
					for (int l = sides - 1; l >= 0; --l) {
						float angle = (float) l / sides * 2.0f * 3.1415927f;
						double x = zone.getCoordX();
						double z = zone.getCoordZ();
						float[] trans2 = transformCoords(x + MathHelper.cos(angle) * radiusWorld, z + MathHelper.sin(angle) * radiusWorld);
						tessellator.addVertex(trans2[0], trans2[1], zLevel);
					}
					tessellator.draw();
					if ((!mouseControlZone || !mouseControlZoneReduced) && (dx = mouseX - (trans = transformCoords(zone.getCoordX(), zone.getCoordZ()))[0]) * dx + (dy = mouseY - trans[1]) * dy <= (rScaled = radius * zoomScale) * rScaled) {
						if (pass >= 1) {
							mouseControlZone = true;
						} else {
							mouseControlZoneReduced = true;
						}
					}
				}
			}
			GL11.glEnable(3553);
			endMapClipping();
		}
	}

	private void renderFullscreenSubtitles(String... lines) {
		int strX = mapXMin + mapWidth / 2;
		int strY = mapYMax + 7;
		if (isConquestGrid) {
			strY = mapYMax + 26;
		}
		int border = fontRendererObj.FONT_HEIGHT + 3;
		if (lines.length == 1) {
			strY += border / 2;
		}
		for (String s : lines) {
			drawCenteredString(s, strX, strY, 16777215);
			strY += border;
		}
	}

	private void renderLabels() {
		if (!hasMapLabels()) {
			return;
		}
		setupMapClipping();
		for (GOTMapLabels label : GOTMapLabels.allMapLabels()) {
			float[] pos = transformMapCoords(label.getPosX(), label.getPosY());
			float x = pos[0];
			float y = pos[1];
			float zoomlerp = (zoomExp - label.getMinZoom()) / (label.getMaxZoom() - label.getMinZoom());
			if (zoomlerp > 0.0f && zoomlerp < 1.0f) {
				float alpha = (0.5f - Math.abs(zoomlerp - 0.5f)) / 0.5f;
				alpha *= 0.7f;
				if (isOSRS()) {
					if (alpha < 0.3f) {
						continue;
					}
					alpha = 1.0f;
				}
				GL11.glPushMatrix();
				GL11.glTranslatef(x, y, 0.0f);
				float scale = zoomScale * label.getScale();
				GL11.glScalef(scale, scale, scale);
				if (!isOSRS()) {
					GL11.glRotatef(label.getAngle(), 0.0f, 0.0f, 1.0f);
				}
				int alphaI = (int) (alpha * 255.0f);
				alphaI = MathHelper.clamp_int(alphaI, 4, 255);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				float alphaFunc = GL11.glGetFloat(3010);
				GL11.glAlphaFunc(516, 0.01f);
				String s = label.getDisplayName();
				int strX = -fontRendererObj.getStringWidth(s) / 2;
				int strY = -fontRendererObj.FONT_HEIGHT / 2;
				if (isOSRS()) {
					if (label.getScale() > 2.5f) {
						fontRendererObj.drawString(s, strX + 1, strY + 1, alphaI << 24);
						fontRendererObj.drawString(s, strX, strY, 16755200 + (alphaI << 24));
					} else {
						fontRendererObj.drawString(s, strX + 1, strY + 1, alphaI << 24);
						fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
					}
				} else {
					fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
				}
				GL11.glAlphaFunc(516, alphaFunc);
				GL11.glDisable(3042);
				GL11.glPopMatrix();
			}
		}
		endMapClipping();
	}

	public void renderMapAndOverlay(boolean sepia, float alpha, boolean overlay) {
		int mapXMin_W = mapXMin;
		int mapXMax_W = mapXMax;
		int mapYMin_W = mapYMin;
		int mapYMax_W = mapYMax;
		float mapScaleX = mapWidth / zoomScale;
		float mapScaleY = mapHeight / zoomScale;
		double minU = (double) (posX - mapScaleX / 2.0f) / GOTGenLayerWorld.getImageWidth();
		double maxU = (double) (posX + mapScaleX / 2.0f) / GOTGenLayerWorld.getImageWidth();
		double minV = (double) (posY - mapScaleY / 2.0f) / GOTGenLayerWorld.getImageHeight();
		double maxV = (double) (posY + mapScaleY / 2.0f) / GOTGenLayerWorld.getImageHeight();
		if (minU < 0.0) {
			mapXMin_W = mapXMin + (int) Math.round((0.0 - minU) * GOTGenLayerWorld.getImageWidth() * zoomScale);
			minU = 0.0;
			if (maxU < 0.0) {
				maxU = 0.0;
				mapXMax_W = mapXMin_W;
			}
		}
		if (maxU > 1.0) {
			mapXMax_W = mapXMax - (int) Math.round((maxU - 1.0) * GOTGenLayerWorld.getImageWidth() * zoomScale);
			maxU = 1.0;
			if (minU > 1.0) {
				minU = 1.0;
				mapXMin_W = mapXMax_W;
			}
		}
		if (minV < 0.0) {
			mapYMin_W = mapYMin + (int) Math.round((0.0 - minV) * GOTGenLayerWorld.getImageHeight() * zoomScale);
			minV = 0.0;
			if (maxV < 0.0) {
				maxV = 0.0;
				mapYMax_W = mapYMin_W;
			}
		}
		if (maxV > 1.0) {
			mapYMax_W = mapYMax - (int) Math.round((maxV - 1.0) * GOTGenLayerWorld.getImageHeight() * zoomScale);
			maxV = 1.0;
			if (minV > 1.0) {
				minV = 1.0;
				mapYMin_W = mapYMax_W;
			}
		}
		GOTTextures.drawMap(sepia, mapXMin_W, mapXMax_W, mapYMin_W, mapYMax_W, zLevel, minU, maxU, minV, maxV, alpha);
		if (overlay && !isOSRS()) {
			GOTTextures.drawMapOverlay(mapXMin, mapXMax, mapYMin, mapYMax, zLevel);
		}
	}

	private void renderMapWidgets(int mouseX, int mouseY) {
		widgetAddCWP.setVisible(!hasOverlay && isGameOfThrones());
		widgetAddCWP.setTexVIndex(canCreateWaypointAtPosition() ? 0 : 1);
		widgetDelCWP.setVisible(!hasOverlay && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint && !((GOTCustomWaypoint) selectedWaypoint).isShared());
		widgetRenameCWP.setVisible(!hasOverlay && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint && !((GOTCustomWaypoint) selectedWaypoint).isShared());
		widgetToggleWPs.setVisible(!hasOverlay);
		widgetToggleWPs.setTexVIndex(showWP ? 0 : 1);
		widgetToggleCWPs.setVisible(!hasOverlay);
		widgetToggleCWPs.setTexVIndex(showCWP ? 0 : 1);
		widgetToggleHiddenSWPs.setVisible(!hasOverlay);
		widgetToggleHiddenSWPs.setTexVIndex(showHiddenSWP ? 1 : 0);
		widgetZoomIn.setVisible(!hasOverlay);
		widgetZoomIn.setTexVIndex(zoomPower < 4 ? 0 : 1);
		widgetZoomOut.setVisible(!hasOverlay);
		widgetZoomOut.setTexVIndex(zoomPower > -3 ? 0 : 1);
		widgetFullScreen.setVisible(!hasOverlay);
		widgetSepia.setVisible(!hasOverlay);
		widgetLabels.setVisible(!hasOverlay);
		widgetShareCWP.setVisible(!hasOverlay && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint && !((GOTCustomWaypoint) selectedWaypoint).isShared());
		widgetHideSWP.setVisible(!hasOverlay && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint && ((GOTCustomWaypoint) selectedWaypoint).isShared() && !((GOTCustomWaypoint) selectedWaypoint).isSharedHidden());
		widgetUnhideSWP.setVisible(!hasOverlay && isGameOfThrones() && selectedWaypoint instanceof GOTCustomWaypoint && ((GOTCustomWaypoint) selectedWaypoint).isShared() && ((GOTCustomWaypoint) selectedWaypoint).isSharedHidden());
		GOTGuiMapWidget mouseOverWidget = null;
		for (GOTGuiMapWidget widget : MAP_WIDGETS) {
			if (widget.isVisible()) {
				mc.getTextureManager().bindTexture(MAP_ICONS_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				drawTexturedModalRect(mapXMin + widget.getMapXPos(mapWidth), mapYMin + widget.getMapYPos(mapHeight), widget.getTexU(), widget.getTexV(), widget.getWidth(), widget.getWidth());
				if (widget.isMouseOver(mouseX - mapXMin, mouseY - mapYMin, mapWidth, mapHeight)) {
					mouseOverWidget = widget;
				}
			}
		}
		if (mouseOverWidget != null) {
			float z = zLevel;
			int stringWidth = 200;
			List<String> desc = fontRendererObj.listFormattedStringToWidth(mouseOverWidget.getTranslatedName(), stringWidth);
			func_146283_a(desc, mouseX, mouseY);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	private void renderMiniQuests(EntityPlayer entityplayer, int mouseX, int mouseY) {
		if (hasOverlay) {
			return;
		}
		GOTMiniQuest mouseOverQuest = null;
		int mouseOverQuestX = 0;
		int mouseOverQuestY = 0;
		double distanceMouseOverQuest = Double.MAX_VALUE;
		List<GOTMiniQuest> quests = GOTLevelData.getData(entityplayer).getActiveMiniQuests();
		for (GOTMiniQuest quest : quests) {
			ChunkCoordinates location = quest.getLastLocation();
			if (location != null) {
				float[] pos = transformCoords(location.posX, location.posZ);
				int questX = Math.round(pos[0]);
				int questY = Math.round(pos[1]);
				float scale = 0.5f;
				float invScale = 1.0f / scale;
				IIcon icon = QUEST_BOOK_ICON.getIconIndex();
				int iconWidthHalf = icon.getIconWidth() / 2;
				iconWidthHalf = (int) (iconWidthHalf * scale);
				int iconBorder = iconWidthHalf + 1;
				questX = Math.max(mapXMin + iconBorder, questX);
				questX = Math.min(mapXMax - iconBorder - 1, questX);
				questY = Math.max(mapYMin + iconBorder, questY);
				questY = Math.min(mapYMax - iconBorder - 1, questY);
				int iconX = Math.round(questX * invScale);
				int iconY = Math.round(questY * invScale);
				GL11.glScalef(scale, scale, scale);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glEnable(2896);
				GL11.glEnable(2884);
				ITEM_RENDERER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), QUEST_BOOK_ICON, iconX - iconWidthHalf, iconY - iconWidthHalf);
				GL11.glDisable(2896);
				GL11.glEnable(3008);
				GL11.glScalef(invScale, invScale, invScale);
				double dx = questX - mouseX;
				double dy = questY - mouseY;
				double distToQuest = Math.sqrt(dx * dx + dy * dy);
				if (distToQuest <= iconWidthHalf + 3 && distToQuest <= distanceMouseOverQuest) {
					mouseOverQuest = quest;
					mouseOverQuestX = questX;
					mouseOverQuestY = questY;
					distanceMouseOverQuest = distToQuest;
				}
			}
		}
		if (mouseOverQuest != null && !hasOverlay) {
			String name = mouseOverQuest.getEntityName();
			int stringWidth = mc.fontRenderer.getStringWidth(name);
			int stringHeight = mc.fontRenderer.FONT_HEIGHT;
			int x = mouseOverQuestX;
			int y = mouseOverQuestY;
			y += 7;
			int border = 3;
			int rectWidth = stringWidth + border * 2;
			x -= rectWidth / 2;
			int rectHeight = stringHeight + border * 2;
			int mapBorder2 = 2;
			x = Math.max(x, mapXMin + mapBorder2);
			x = Math.min(x, mapXMax - mapBorder2 - rectWidth);
			y = Math.max(y, mapYMin + mapBorder2);
			y = Math.min(y, mapYMax - mapBorder2 - rectHeight);
			GL11.glTranslatef(0.0f, 0.0f, 300.0f);
			drawFancyRect(x, y, x + rectWidth, y + rectHeight);
			mc.fontRenderer.drawString(name, x + border, y + border, 16777215);
			GL11.glTranslatef(0.0f, 0.0f, -300.0f);
		}
	}

	private double renderPlayerIcon(GameProfile profile, double playerX, double playerY, int mouseX, int mouseY) {
		Tessellator tessellator = Tessellator.instance;
		int iconWidthHalf = 4;
		int iconBorder = iconWidthHalf + 1;
		double playerX2 = Math.max(mapXMin + iconBorder, playerX);
		double playerX1 = Math.min(mapXMax - iconBorder - 1, playerX2);
		double playerY2 = Math.max(mapYMin + iconBorder, playerY);
		double playerY1 = Math.min(mapYMax - iconBorder - 1, playerY2);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		ResourceLocation skin = AbstractClientPlayer.locationStevePng;
		if (profile.getId().equals(mc.thePlayer.getUniqueID())) {
			skin = mc.thePlayer.getLocationSkin();
		} else {
			MinecraftProfileTexture.Type type;
			Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.func_152342_ad().func_152788_a(profile);
			if (map.containsKey(type = MinecraftProfileTexture.Type.SKIN)) {
				skin = mc.func_152342_ad().func_152792_a(map.get(type), type);
			}
		}
		mc.getTextureManager().bindTexture(skin);
		double iconMinU = 0.125;
		double iconMaxU = 0.25;
		double iconMinV = 0.25;
		double iconMaxV = 0.5;
		double playerX_d = playerX1 + 0.5;
		double playerY_d = playerY1 + 0.5;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerY_d + iconWidthHalf, zLevel, iconMinU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerY_d + iconWidthHalf, zLevel, iconMaxU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf, playerY_d - iconWidthHalf, zLevel, iconMaxU, iconMinV);
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf, playerY_d - iconWidthHalf, zLevel, iconMinU, iconMinV);
		tessellator.draw();
		iconMinU = 0.625;
		iconMaxU = 0.75;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5, playerY_d + iconWidthHalf + 0.5, zLevel, iconMinU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5, playerY_d + iconWidthHalf + 0.5, zLevel, iconMaxU, iconMaxV);
		tessellator.addVertexWithUV(playerX_d + iconWidthHalf + 0.5, playerY_d - iconWidthHalf - 0.5, zLevel, iconMaxU, iconMinV);
		tessellator.addVertexWithUV(playerX_d - iconWidthHalf - 0.5, playerY_d - iconWidthHalf - 0.5, zLevel, iconMinU, iconMinV);
		tessellator.draw();
		double dx = playerX1 - mouseX;
		double dy = playerY1 - mouseY;
		return Math.sqrt(dx * dx + dy * dy);
	}

	private void renderPlayers(int mouseX, int mouseY) {
		String mouseOverPlayerName = null;
		double mouseOverPlayerX = 0.0;
		double mouseOverPlayerY = 0.0;
		double distanceMouseOverPlayer = Double.MAX_VALUE;
		int iconWidthHalf = 4;
		Map<UUID, PlayerLocationInfo> playersToRender = new HashMap<>(PLAYER_LOCATIONS);
		if (isGameOfThrones()) {
			playersToRender.put(mc.thePlayer.getUniqueID(), new PlayerLocationInfo(mc.thePlayer.getGameProfile(), mc.thePlayer.posX, mc.thePlayer.posZ));
		}
		for (Map.Entry<UUID, PlayerLocationInfo> entry : playersToRender.entrySet()) {
			int playerY;
			float[] pos;
			int playerX;
			PlayerLocationInfo info = entry.getValue();
			GameProfile profile = info.getProfile();
			String playerName = profile.getName();
			double distToPlayer = renderPlayerIcon(profile, playerX = Math.round((pos = transformCoords(info.getPosX(), info.getPosZ()))[0]), playerY = Math.round(pos[1]), mouseX, mouseY);
			if (distToPlayer <= iconWidthHalf + 3 && distToPlayer <= distanceMouseOverPlayer) {
				mouseOverPlayerName = playerName;
				mouseOverPlayerX = playerX;
				mouseOverPlayerY = playerY;
				distanceMouseOverPlayer = distToPlayer;
			}
		}
		if (mouseOverPlayerName != null && !hasOverlay && !loadingConquestGrid) {
			int strWidth = mc.fontRenderer.getStringWidth(mouseOverPlayerName);
			int strHeight = mc.fontRenderer.FONT_HEIGHT;
			int rectX = (int) Math.round(mouseOverPlayerX);
			int rectY = (int) Math.round(mouseOverPlayerY);
			rectY += iconWidthHalf + 3;
			int border = 3;
			int rectWidth = strWidth + border * 2;
			rectX -= rectWidth / 2;
			int rectHeight = strHeight + border * 2;
			int mapBorder2 = 2;
			rectX = Math.max(rectX, mapXMin + mapBorder2);
			rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
			rectY = Math.max(rectY, mapYMin + mapBorder2);
			rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
			GL11.glTranslatef(0.0f, 0.0f, 300.0f);
			drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
			mc.fontRenderer.drawString(mouseOverPlayerName, rectX + border, rectY + border, 16777215);
			GL11.glTranslatef(0.0f, 0.0f, -300.0f);
		}
	}

	private void renderWaypoints(int pass, int mouseX, int mouseY) {
		if (!showWP && !showCWP && !showHiddenSWP || !GOTFixedStructures.hasMapFeatures(mc.theWorld)) {
			return;
		}
		renderWaypoints(GOTLevelData.getData(mc.thePlayer).getAllAvailableWaypoints(), pass, mouseX, mouseY, hasMapLabels(), false);
	}

	public void renderWaypoints(Iterable<GOTAbstractWaypoint> waypoints, int pass, int mouseX, int mouseY, boolean labels, boolean overrideToggles) {
		setupMapClipping();
		GOTAbstractWaypoint mouseOverWP = null;
		double distanceMouseOverWP = Double.MAX_VALUE;
		float wpZoomlerp = (zoomExp + 3.3F) / 2.2F;
		wpZoomlerp = Math.min(wpZoomlerp, 1.0F);
		if (!enableZoomOutWPFading) {
			wpZoomlerp = 1.0F;
		}
		if (wpZoomlerp > 0.0F) {
			for (GOTAbstractWaypoint waypoint : waypoints) {
				boolean unlocked = mc.thePlayer != null && waypoint.hasPlayerUnlocked(mc.thePlayer);
				boolean hidden = waypoint.isHidden();
				if ((isWaypointVisible(waypoint) || overrideToggles) && (!hidden || unlocked)) {
					float[] pos = transformCoords(waypoint.getCoordX(), waypoint.getCoordZ());
					float x = pos[0];
					float y = pos[1];
					int clip = 200;
					if (x >= mapXMin - clip && x <= mapXMax + clip && y >= mapYMin - clip && y <= mapYMax + clip) {
						if (pass == 0) {
							GL11.glEnable(3042);
							GL11.glBlendFunc(770, 771);
							if (isOSRS()) {
								GL11.glPushMatrix();
								GL11.glScalef(0.33F, 0.33F, 1.0F);
								mc.getTextureManager().bindTexture(GOTTextures.OSRS_TEXTURE);
								GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
								drawTexturedModalRectFloat(x / 0.33F - 8.0F, y / 0.33F - 8.0F, 0, 0, 15.0F, 15.0F);
								GL11.glPopMatrix();
							} else {
								GOTAbstractWaypoint.WaypointLockState state = mc.thePlayer != null ? waypoint.getLockState(mc.thePlayer) : GOTAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
								mc.getTextureManager().bindTexture(MAP_ICONS_TEXTURE);
								GL11.glColor4f(1.0F, 1.0F, 1.0F, wpZoomlerp);
								drawTexturedModalRectFloat(x - 2.0F, y - 2.0F, state.getIconU(), state.getIconV(), 4.0F, 4.0F);
							}
							GL11.glDisable(3042);
							if (labels) {
								float zoomlerp = (zoomExp + 1.0F) / 4.0F;
								zoomlerp = Math.min(zoomlerp, 1.0F);
								if (zoomlerp > 0.0F) {
									GL11.glPushMatrix();
									GL11.glTranslatef(x, y, 0.0F);
									GL11.glScalef(zoomlerp, zoomlerp, zoomlerp);
									float alpha = zoomlerp;
									alpha *= 0.8F;
									int alphaI = (int) (alpha * 255.0F);
									alphaI = MathHelper.clamp_int(alphaI, 4, 255);
									GL11.glEnable(3042);
									GL11.glBlendFunc(770, 771);
									String s = waypoint.getDisplayName();
									int strX = -fontRendererObj.getStringWidth(s) / 2;
									int strY = -15;
									fontRendererObj.drawString(s, strX + 1, strY + 1, alphaI << 24);
									fontRendererObj.drawString(s, strX, strY, 16777215 + (alphaI << 24));
									GL11.glDisable(3042);
									GL11.glPopMatrix();
								}
							}
						}
						if (pass == 1 && waypoint != selectedWaypoint) {
							if (x >= mapXMin - 2 && x <= mapXMax + 2 && y >= mapYMin - 2 && y <= mapYMax + 2) {
								double dx = x - mouseX;
								double dy = y - mouseY;
								double distToWP = Math.sqrt(dx * dx + dy * dy);
								if (distToWP <= 5.0D && distToWP <= distanceMouseOverWP) {
									mouseOverWP = waypoint;
									distanceMouseOverWP = distToWP;
								}
							}
						}
					}
				}
			}
		}
		if (pass == 1 && mouseOverWP != null && !hasOverlay && !loadingConquestGrid) {
			renderWaypointTooltip(mouseOverWP, false);
		}
		endMapClipping();
	}

	private void renderWaypointTooltip(GOTAbstractWaypoint waypoint, boolean selected) {
		String name = waypoint.getDisplayName();
		int wpX = waypoint.getCoordX();
		int wpZ = waypoint.getCoordZ();
		int wpY = waypoint.getCoordYSaved();
		String coords = wpY >= 0 ? StatCollector.translateToLocalFormatted("got.gui.map.coordsY", wpX, wpY, wpZ) : StatCollector.translateToLocalFormatted("got.gui.map.coords", wpX, wpZ);
		String loreText = waypoint.getLoreText(mc.thePlayer);
		float guiScale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
		float loreScale = guiScale - 1.0f;
		if (guiScale <= 2.0f) {
			loreScale = guiScale;
		}
		float loreScaleRel = loreScale / guiScale;
		float loreScaleRelInv = 1.0f / loreScaleRel;
		int loreFontHeight = MathHelper.ceiling_double_int(fontRendererObj.FONT_HEIGHT * loreScaleRel);
		float[] pos = transformCoords(wpX, wpZ);
		int rectX = Math.round(pos[0]);
		int rectY = Math.round(pos[1]);
		rectY += 5;
		int border = 3;
		int stringHeight = fontRendererObj.FONT_HEIGHT;
		int innerRectWidth = fontRendererObj.getStringWidth(name);
		if (selected) {
			innerRectWidth = Math.max(innerRectWidth, fontRendererObj.getStringWidth(coords));
			if (loreText != null) {
				innerRectWidth += 50;
				innerRectWidth = Math.round(innerRectWidth * (loreScaleRel / 0.66667f));
			}
		}
		int rectWidth = innerRectWidth + border * 2;
		rectX -= rectWidth / 2;
		int rectHeight = border * 2 + stringHeight;
		if (selected) {
			rectHeight += border + stringHeight;
			if (loreText != null) {
				rectHeight += border;
				rectHeight += fontRendererObj.listFormattedStringToWidth(loreText, (int) (innerRectWidth * loreScaleRelInv)).size() * loreFontHeight;
				rectHeight += border;
			}
		}
		int mapBorder2 = 2;
		rectX = Math.max(rectX, mapXMin + mapBorder2);
		rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
		rectY = Math.max(rectY, mapYMin + mapBorder2);
		rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
		int stringX = rectX + rectWidth / 2;
		int stringY = rectY + border;
		GL11.glTranslatef(0.0f, 0.0f, 300.0f);
		drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
		drawCenteredString(name, stringX, stringY, 16777215);
		stringY += fontRendererObj.FONT_HEIGHT + border;
		if (selected) {
			drawCenteredString(coords, stringX, stringY, 16777215);
			stringY += fontRendererObj.FONT_HEIGHT + border * 2;
			if (loreText != null) {
				GL11.glPushMatrix();
				GL11.glScalef(loreScaleRel, loreScaleRel, 1.0f);
				List<String> loreLines = fontRendererObj.listFormattedStringToWidth(loreText, (int) (innerRectWidth * loreScaleRelInv));
				for (String loreLine : loreLines) {
					drawCenteredString(loreLine, (int) (stringX * loreScaleRelInv), (int) (stringY * loreScaleRelInv), 16777215);
					stringY += loreFontHeight;
				}
				GL11.glPopMatrix();
			}
		}
		GL11.glTranslatef(0.0f, 0.0f, -300.0f);
	}

	private void requestConquestGridIfNeed(GOTFaction conqFac) {
		if (!requestedFacGrids.contains(conqFac) && ticksUntilRequestFac <= 0) {
			requestedFacGrids.add(conqFac);
			IMessage packet = new GOTPacketConquestGridRequest(conqFac);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	private void requestIsOp() {
		if (mc.isSingleplayer()) {
			IntegratedServer server = mc.getIntegratedServer();
			isPlayerOp = server.worldServers[0].getWorldInfo().areCommandsAllowed() && server.getServerOwner().equalsIgnoreCase(mc.thePlayer.getGameProfile().getName());
		} else {
			IMessage packet = new GOTPacketIsOpRequest();
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	public GOTGuiMap setConquestGrid() {
		isConquestGrid = true;
		return this;
	}

	public void setControlZone(GOTFaction f) {
		hasControlZones = true;
		controlZoneFaction = f;
	}

	private void setCurrentScrollFromFaction() {
		currentFacScroll = (float) currentFactionIndex / (currentFactionList.size() - 1);
	}

	public void setCWPProtectionMessage(IChatComponent message) {
		closeOverlay();
		hasOverlay = true;
		creatingWaypoint = false;
		creatingWaypointNew = false;
		String protection = StatCollector.translateToLocalFormatted("got.gui.map.customWaypoint.protected.1", message.getFormattedText());
		String warning = StatCollector.translateToLocal("got.gui.map.customWaypoint.protected.2");
		overlayLines = new String[]{protection, "", warning};
	}

	public void setFakeMapProperties(float x, float y, float scale, float scaleExp, float scaleStable) {
		posX = x;
		posY = y;
		zoomScale = scale;
		zoomExp = scaleExp;
		zoomScaleStable = scaleStable;
	}

	private void setupConquestScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = facScrollX;
		int j1 = facScrollY;
		int i2 = i1 + FAC_SCROLL_WIDTH;
		int j2 = j1 + FAC_SCROLL_HEIGHT;
		mouseInFacScroll = i >= i1 && j >= j1 && i < i2 && j < j2;
		if (!wasMouseDown && isMouseDown && mouseInFacScroll) {
			isFacScrolling = true;
		}
		if (!isMouseDown) {
			isFacScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isFacScrolling) {
			currentFacScroll = (i - i1 - FAC_SCROLL_WIDGET_WIDTH / 2.0f) / ((float) (i2 - i1) - FAC_SCROLL_WIDGET_WIDTH);
			currentFacScroll = MathHelper.clamp_float(currentFacScroll, 0.0f, 1.0f);
			currentFactionIndex = Math.round(currentFacScroll * (currentFactionList.size() - 1));
		}
	}

	private void setupMapClipping() {
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int scale = sr.getScaleFactor();
		GL11.glEnable(3089);
		GL11.glScissor(mapXMin * scale, (height - mapYMax) * scale, mapWidth * scale, mapHeight * scale);
	}

	private void setupMapDimensions() {
		if (isConquestGrid) {
			int windowWidth = 400;
			int windowHeight = 240;
			mapXMin = width / 2 - windowWidth / 2;
			mapXMax = width / 2 + windowWidth / 2;
			mapYMin = height / 2 - 16 - windowHeight / 2;
			mapYMax = mapYMin + windowHeight;
		} else if (fullscreen) {
			mapXMin = 30;
			mapXMax = width - 30;
			mapYMin = 30;
			mapYMax = height - 30;
		} else {
			int windowWidth = 312;
			mapXMin = width / 2 - windowWidth / 2;
			mapXMax = width / 2 + windowWidth / 2;
			mapYMin = guiTop;
			mapYMax = guiTop + 200;
		}
		mapWidth = mapXMax - mapXMin;
		mapHeight = mapYMax - mapYMin;
	}

	private void setupMapWidgets() {
		MAP_WIDGETS.clear();
		widgetAddCWP = new GOTGuiMapWidget(-16, 6, 10, "addCWP", 0, 0);
		MAP_WIDGETS.add(widgetAddCWP);
		widgetDelCWP = new GOTGuiMapWidget(-16, 20, 10, "delCWP", 10, 0);
		MAP_WIDGETS.add(widgetDelCWP);
		widgetRenameCWP = new GOTGuiMapWidget(-16, 34, 10, "renameCWP", 0, 10);
		MAP_WIDGETS.add(widgetRenameCWP);
		widgetToggleWPs = new GOTGuiMapWidget(-58, 6, 10, "toggleWPs", 80, 0);
		MAP_WIDGETS.add(widgetToggleWPs);
		widgetToggleCWPs = new GOTGuiMapWidget(-44, 6, 10, "toggleCWPs", 90, 0);
		MAP_WIDGETS.add(widgetToggleCWPs);
		widgetToggleHiddenSWPs = new GOTGuiMapWidget(-30, 6, 10, "toggleHiddenSWPs", 100, 0);
		MAP_WIDGETS.add(widgetToggleHiddenSWPs);
		widgetZoomIn = new GOTGuiMapWidget(6, 6, 10, "zoomIn", 30, 0);
		MAP_WIDGETS.add(widgetZoomIn);
		widgetZoomOut = new GOTGuiMapWidget(6, 20, 10, "zoomOut", 40, 0);
		MAP_WIDGETS.add(widgetZoomOut);
		widgetFullScreen = new GOTGuiMapWidget(20, 6, 10, "fullScreen", 50, 0);
		MAP_WIDGETS.add(widgetFullScreen);
		widgetSepia = new GOTGuiMapWidget(34, 6, 10, "sepia", 60, 0);
		MAP_WIDGETS.add(widgetSepia);
		widgetLabels = new GOTGuiMapWidget(-72, 6, 10, "labels", 70, 0);
		MAP_WIDGETS.add(widgetLabels);
		widgetShareCWP = new GOTGuiMapWidget(-16, 48, 10, "shareCWP", 10, 10);
		MAP_WIDGETS.add(widgetShareCWP);
		widgetHideSWP = new GOTGuiMapWidget(-16, 20, 10, "hideSWP", 20, 0);
		MAP_WIDGETS.add(widgetHideSWP);
		widgetUnhideSWP = new GOTGuiMapWidget(-16, 20, 10, "unhideSWP", 20, 10);
		MAP_WIDGETS.add(widgetUnhideSWP);
		if (isConquestGrid) {
			MAP_WIDGETS.clear();
			MAP_WIDGETS.add(widgetToggleWPs);
			MAP_WIDGETS.add(widgetToggleCWPs);
			MAP_WIDGETS.add(widgetToggleHiddenSWPs);
			MAP_WIDGETS.add(widgetZoomIn);
			MAP_WIDGETS.add(widgetZoomOut);
			MAP_WIDGETS.add(widgetLabels);
		}
	}

	private void setupScrollBars(int i, int j) {
		int maxDisplayedWPShares = fullscreen ? 8 : 5;
		if (selectedWaypoint != null && hasOverlay && sharingWaypoint) {
			displayedWPShareList = ((GOTCustomWaypoint) selectedWaypoint).getSharedFellowshipIDs();
			displayedWPShares = displayedWPShareList.size();
			scrollPaneWPShares.setHasScrollBar(false);
			if (displayedWPShares > maxDisplayedWPShares) {
				displayedWPShares = maxDisplayedWPShares;
				scrollPaneWPShares.setHasScrollBar(true);
			}
		} else {
			displayedWPShareList = null;
			displayedWPShares = 0;
			scrollPaneWPShares.setHasScrollBar(false);
			scrollPaneWPShares.mouseDragScroll(i, j);
		}
	}

	private void setupZoomVariables(float f) {
		zoomExp = zoomPower;
		if (zoomTicks > 0) {
			float progress = (ZOOM_TICKS_MAX - (zoomTicks - f)) / ZOOM_TICKS_MAX;
			zoomExp = prevZoomPower + (zoomPower - prevZoomPower) * progress;
		}
		zoomScale = (float) Math.pow(2.0, zoomExp);
		zoomScaleStable = (float) Math.pow(2.0, zoomTicks == 0 ? zoomPower : Math.min(zoomPower, prevZoomPower));
	}

	private void toggleMapLabels() {
		if (isConquestGrid) {
			GOTConfig.toggleMapLabelsConquest();
		} else {
			GOTConfig.toggleMapLabels();
		}
	}

	private float[] transformCoords(double x, double z) {
		return transformCoords((float) x, (float) z);
	}

	private float[] transformCoords(float x, float z) {
		float x1 = x / GOTGenLayerWorld.SCALE + GOTGenLayerWorld.ORIGIN_X;
		float z1 = z / GOTGenLayerWorld.SCALE + GOTGenLayerWorld.ORIGIN_Z;
		return transformMapCoords(x1, z1);
	}

	private float[] transformMapCoords(float x, float z) {
		float x1 = x;
		float z1 = z;
		x1 -= posX;
		z1 -= posY;
		x1 *= zoomScale;
		z1 *= zoomScale;
		return new float[]{x1 + (mapXMin + (float) mapWidth / 2), z1 + (mapYMin + (float) mapHeight / 2)};
	}

	private void updateCurrentDimensionAndFaction() {
		if (currentFactionIndex != prevFactionIndex) {
			conquestViewingFaction = currentFactionList.get(currentFactionIndex);
			ticksUntilRequestFac = 40;
		}
		prevFactionIndex = currentFactionIndex;
		if (currentRegion != prevRegion) {
			LAST_VIEWED_REGIONS.put(prevRegion, conquestViewingFaction);
			currentFactionList = currentRegion.getFactionList();
			conquestViewingFaction = LAST_VIEWED_REGIONS.containsKey(currentRegion) ? LAST_VIEWED_REGIONS.get(currentRegion) : currentRegion.getFactionList().get(0);
			prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(conquestViewingFaction);
			ticksUntilRequestFac = 40;
		}
		prevRegion = currentRegion;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
		if (zoomTicks > 0) {
			--zoomTicks;
		}
		if (creatingWaypointNew || renamingWaypoint || sharingWaypointNew) {
			nameWPTextField.updateCursorCounter();
		}
		handleMapKeyboardMovement();
		if (isConquestGrid) {
			updateCurrentDimensionAndFaction();
			if (ticksUntilRequestFac > 0) {
				--ticksUntilRequestFac;
			}
		}
	}

	private void zoom(int boost) {
		prevZoomPower = zoomPower;
		zoomPower += boost;
		zoomTicks = ZOOM_TICKS_MAX;
		selectedWaypoint = null;
	}

	private void zoomIn() {
		zoom(1);
	}

	private void zoomOut() {
		zoom(-1);
	}

	public void setPlayerOp(boolean playerOp) {
		isPlayerOp = playerOp;
	}

	public void setEnableZoomOutWPFading(boolean enableZoomOutWPFading) {
		this.enableZoomOutWPFading = enableZoomOutWPFading;
	}

	private static class PlayerLocationInfo {
		private final GameProfile profile;
		private final double posX;
		private final double posZ;

		private PlayerLocationInfo(GameProfile p, double x, double z) {
			profile = p;
			posX = x;
			posZ = z;
		}

		private double getPosX() {
			return posX;
		}

		private double getPosZ() {
			return posZ;
		}

		private GameProfile getProfile() {
			return profile;
		}
	}
}