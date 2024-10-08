package got.client.gui;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.client.GOTClientProxy;
import got.client.GOTTextures;
import got.client.GOTTickHandlerClient;
import got.common.GOTConfig;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.*;
import got.common.network.GOTPacketClientMQEvent;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketOathSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.stream.Stream;

public class GOTGuiFactions extends GOTGuiMenuBase {
	public static final ResourceLocation FACTIONS_TEXTURE = new ResourceLocation("got:textures/gui/factions.png");

	private static final ResourceLocation FACTIONS_TEXTURE_FULL = new ResourceLocation("got:textures/gui/factions_full.png");

	private static final int PAGE_Y = 46;
	private static final int PAGE_WIDTH = 256;
	private static final int PAGE_HEIGHT = 128;
	private static final int PAGE_BORDER_TOP = 12;
	private static final int PAGE_MAP_X = 159;
	private static final int PAGE_MAP_Y = 22;
	private static final int PAGE_MAP_SIZE = 80;

	private static List<GOTFaction> currentFactionList;

	private static Page currentPage = Page.FRONT;
	private static GOTDimension currentDimension;
	private static GOTDimension prevDimension;
	private static GOTDimension.DimensionRegion currentRegion;
	private static GOTDimension.DimensionRegion prevRegion;

	private final GOTGuiMap mapDrawGui;
	private final GOTGuiScrollPane scrollPaneAlliesEnemies;

	private final int scrollBarWidth;
	private final int scrollBarHeight;
	private final int scrollBarX;
	private final int scrollBarY;
	private final int scrollBarBorder;
	private final int scrollWidgetWidth;
	private final int scrollWidgetHeight;
	private final int scrollAlliesEnemiesX;

	private List<Object> currentAlliesEnemies;
	private Map<GOTFaction, Float> playerReputationMap;

	private GOTFaction currentFaction;

	private GuiButton buttonRegions;
	private GuiButton buttonPagePrev;
	private GuiButton buttonPageNext;
	private GuiButton buttonFactionMap;

	private GOTGuiButtonOath buttonOath;
	private GOTGuiButtonOath buttonOathConfirm;
	private GOTGuiButtonOath buttonOathRevoke;

	private String otherPlayerName;

	private boolean isScrolling;
	private boolean wasMouseDown;
	private boolean isOtherPlayer;
	private boolean isTakingOath;
	private boolean isRevokingOath;

	private float currentScroll;

	private int numDisplayedAlliesEnemies;
	private int currentFactionIndex;
	private int prevFactionIndex;

	public GOTGuiFactions() {
		sizeX = PAGE_WIDTH;
		currentScroll = 0.0f;
		isScrolling = false;
		scrollBarWidth = 240;
		scrollBarHeight = 14;
		scrollBarX = sizeX / 2 - scrollBarWidth / 2;
		scrollBarY = 180;
		scrollBarBorder = 1;
		scrollWidgetWidth = 17;
		scrollWidgetHeight = 12;
		scrollPaneAlliesEnemies = new GOTGuiScrollPane(7, 7).setColors(5521198, 8019267);
		scrollAlliesEnemiesX = 138;
		isOtherPlayer = false;
		isTakingOath = false;
		isRevokingOath = false;
		mapDrawGui = new GOTGuiMap();
	}

	private static boolean hasScrollBar() {
		return currentFactionList.size() > 1;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonRegions) {
				List<GOTDimension.DimensionRegion> regionList = currentDimension.getDimensionRegions();
				if (!regionList.isEmpty()) {
					int i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					updateCurrentDimensionAndFaction();
					setCurrentScrollFromFaction();
					scrollPaneAlliesEnemies.resetScroll();
					isTakingOath = false;
					isRevokingOath = false;
				}
			} else if (button == goBack) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button == buttonPagePrev) {
				Page newPage = currentPage.prev();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isTakingOath = false;
					isRevokingOath = false;
				}
			} else if (button == buttonPageNext) {
				Page newPage = currentPage.next();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isTakingOath = false;
					isRevokingOath = false;
				}
			} else if (button == buttonFactionMap) {
				GOTGuiMap factionGuiMap = new GOTGuiMap();
				factionGuiMap.setControlZone(currentFaction);
				mc.displayGuiScreen(factionGuiMap);
			} else if (button == buttonOath) {
				if (GOTLevelData.getData(mc.thePlayer).hasTakenOathTo(currentFaction)) {
					isRevokingOath = true;
				} else {
					isTakingOath = true;
				}
			} else if (button == buttonOathConfirm) {
				IMessage packet = new GOTPacketOathSet(currentFaction);
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
				isTakingOath = false;
			} else if (button == buttonOathRevoke) {
				IMessage packet = new GOTPacketOathSet(null);
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
				isRevokingOath = false;
				mc.displayGuiScreen(null);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public void drawButtonHoveringText(List<String> list, int i, int j) {
		func_146283_a(list, i, j);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		List<String> desc;
		int stringWidth;
		GOTPlayerData clientPD = GOTLevelData.getData(mc.thePlayer);
		boolean mouseOverWarCrimes = false;
		if (!isTakingOath && !isRevokingOath) {
			buttonPagePrev.enabled = currentPage.prev() != null;
			buttonPageNext.enabled = currentPage.next() != null;
			buttonFactionMap.enabled = currentPage != Page.RANKS && currentFaction.isPlayableReputationFaction() && currentFaction.getFactionDimension() == GOTDimension.GAME_OF_THRONES;
			buttonFactionMap.visible = buttonFactionMap.enabled;
			if (!GOTFaction.controlZonesEnabled(mc.theWorld)) {
				buttonFactionMap.enabled = false;
				buttonFactionMap.visible = false;
			}
			if (!isOtherPlayer && currentPage == Page.FRONT) {
				if (clientPD.hasTakenOathTo(currentFaction)) {
					buttonOath.setBroken(buttonOath.func_146115_a());
					buttonOath.enabled = true;
					buttonOath.visible = true;
					buttonOath.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.revokeOath"));
				} else {
					buttonOath.setBroken(false);
					buttonOath.visible = clientPD.getOathFaction() == null && currentFaction.isPlayableReputationFaction() && clientPD.getReputation(currentFaction) >= 0.0f;
					buttonOath.enabled = buttonOath.visible && clientPD.hasOathReputation(currentFaction);
					String desc1 = StatCollector.translateToLocal("got.gui.factions.oath");
					String desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.oathReq", GOTReputationValues.formatRepForDisplay(currentFaction.getOathReputation()));
					buttonOath.setDisplayLines(desc1, desc2);
				}
			} else {
				buttonOath.enabled = false;
				buttonOath.visible = false;
			}
			buttonOathConfirm.enabled = false;
			buttonOathConfirm.visible = false;
			buttonOathRevoke.enabled = false;
			buttonOathRevoke.visible = false;
		} else {
			buttonPagePrev.enabled = false;
			buttonPageNext.enabled = false;
			buttonFactionMap.enabled = false;
			buttonFactionMap.visible = false;
			buttonOath.enabled = false;
			buttonOath.visible = false;
			if (isTakingOath) {
				buttonOathConfirm.visible = true;
				buttonOathConfirm.enabled = clientPD.canMakeNewOath() && clientPD.canOathTo(currentFaction);
				buttonOathConfirm.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.oath"));
				buttonOathRevoke.enabled = false;
				buttonOathRevoke.visible = false;
			} else {
				buttonOathConfirm.enabled = false;
				buttonOathConfirm.visible = false;
				buttonOathRevoke.enabled = true;
				buttonOathRevoke.visible = true;
				buttonOathRevoke.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.revokeOath"));
			}
		}
		setupScrollBar(i, j);
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (useFullPageTexture()) {
			mc.getTextureManager().bindTexture(FACTIONS_TEXTURE_FULL);
		} else {
			mc.getTextureManager().bindTexture(FACTIONS_TEXTURE);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop + PAGE_Y, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);
		if (currentRegion != null && currentDimension.getDimensionRegions().size() > 1) {
			buttonRegions.displayString = currentRegion.getRegionName();
			buttonRegions.enabled = true;
			buttonRegions.visible = true;
		} else {
			buttonRegions.displayString = "";
			buttonRegions.enabled = false;
			buttonRegions.visible = false;
		}
		if (currentFaction != null) {
			float reputation = isOtherPlayer && playerReputationMap != null ? playerReputationMap.get(currentFaction) : clientPD.getReputation(currentFaction);
			int x = guiLeft + sizeX / 2;
			int y = guiTop;
			GOTTickHandlerClient.renderReputationBar(reputation, currentFaction, x, y, true, false, true, true);
			String s = currentFaction.factionSubtitle();
			drawCenteredString(s, x, y + fontRendererObj.FONT_HEIGHT + 22, 16777215);
			if (!useFullPageTexture()) {
				if (currentFaction.getFactionMapInfo() != null) {
					GOTMapRegion mapInfo = currentFaction.getFactionMapInfo();
					int mapX = mapInfo.getMapX();
					int mapY = mapInfo.getMapY();
					int mapR = mapInfo.getRadius();
					int xMin = guiLeft + PAGE_MAP_X;
					int xMax = xMin + PAGE_MAP_SIZE;
					int yMin = guiTop + PAGE_Y + PAGE_MAP_Y;
					int yMax = yMin + PAGE_MAP_SIZE;
					int mapBorder = 1;
					drawRect(xMin - mapBorder, yMin - mapBorder, xMax + mapBorder, yMax + mapBorder, -16777216);
					float zoom = (float) PAGE_MAP_SIZE / (mapR * 2);
					float zoomExp = (float) Math.log(zoom) / (float) Math.log(2.0);
					mapDrawGui.setFakeMapProperties(mapX, mapY, zoom, zoomExp, zoom);
					int[] statics = GOTGuiMap.setFakeStaticProperties(PAGE_MAP_SIZE, PAGE_MAP_SIZE, xMin, xMax, yMin, yMax);
					mapDrawGui.setEnableZoomOutWPFading(false);
					boolean sepia = GOTConfig.enableSepiaMap;
					mapDrawGui.renderMapAndOverlay(sepia, 1.0f, true);
					GOTGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
				}
				int wcX = guiLeft + PAGE_MAP_X + 3;
				int wcY = guiTop + PAGE_Y + PAGE_MAP_Y + PAGE_MAP_SIZE + 5;
				int wcWidth = 8;
				mc.getTextureManager().bindTexture(FACTIONS_TEXTURE);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				if (currentFaction.isApprovesWarCrimes()) {
					drawTexturedModalRect(wcX, wcY, 33, 142, wcWidth, wcWidth);
				} else {
					drawTexturedModalRect(wcX, wcY, 41, 142, wcWidth, wcWidth);
				}
				if (i >= wcX && i < wcX + wcWidth && j >= wcY && j < wcY + wcWidth) {
					mouseOverWarCrimes = true;
				}
			}
			int pageBorderLeft = 16;
			x = guiLeft + pageBorderLeft;
			y = guiTop + PAGE_Y + PAGE_BORDER_TOP;
			if (!isTakingOath && !isRevokingOath) {
				int index;
				switch (currentPage) {
					case ALLIES:
					case ENEMIES:
						int avgBgColor = GOTTextures.computeAverageFactionPageColor(FACTIONS_TEXTURE, 20, 20, 120, 80);
						int[] minMax = scrollPaneAlliesEnemies.getMinMaxIndices(currentAlliesEnemies, numDisplayedAlliesEnemies);
						for (index = minMax[0]; index <= minMax[1]; ++index) {
							Object listObj = currentAlliesEnemies.get(index);
							if (listObj instanceof GOTFactionRelations.Relation) {
								GOTFactionRelations.Relation rel = (GOTFactionRelations.Relation) listObj;
								s = StatCollector.translateToLocalFormatted("got.gui.factions.relationHeader", rel.getDisplayName());
								fontRendererObj.drawString(s, x, y, 8019267);
							} else if (listObj instanceof GOTFaction) {
								GOTFaction fac = (GOTFaction) listObj;
								s = StatCollector.translateToLocalFormatted("got.gui.factions.list", fac.factionName());
								fontRendererObj.drawString(s, x, y, GOTTextures.findContrastingColor(fac.getFactionColor(), avgBgColor));
							}
							y += fontRendererObj.FONT_HEIGHT;
						}
						break;
					case FRONT:
						if (isOtherPlayer) {
							s = StatCollector.translateToLocalFormatted("got.gui.factions.pageOther", otherPlayerName);
							fontRendererObj.drawString(s, x, y, 8019267);
							y += fontRendererObj.FONT_HEIGHT * 2;
						}
						String reputationInfo = StatCollector.translateToLocal("got.gui.factions.reputation");
						fontRendererObj.drawString(reputationInfo, x, y, 8019267);
						String reputationString = GOTReputationValues.formatRepForDisplay(reputation);
						GOTTickHandlerClient.drawReputationText(fontRendererObj, x + fontRendererObj.getStringWidth(reputationInfo) + 5, y, reputationString, 1.0f);
						x = guiLeft + pageBorderLeft;
						GOTFactionRank curRank = currentFaction.getRank(reputation);
						String rankName = curRank.getFullNameWithGender(clientPD);
						fontRendererObj.drawString(rankName, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
						y += fontRendererObj.FONT_HEIGHT * 2;
						if (!isOtherPlayer) {
							GOTFactionData factionData = clientPD.getFactionData(currentFaction);
							if (reputation >= 0.0f) {
								float conq;
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.enemiesKilled", factionData.getEnemiesKilled());
								fontRendererObj.drawString(s, x, y, 8019267);
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.trades", factionData.getTradeCount());
								fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.hires", factionData.getHireCount());
								fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.miniquests", factionData.getMiniQuestsCompleted());
								fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
								y += fontRendererObj.FONT_HEIGHT;
								if (clientPD.hasTakenOathTo(currentFaction) && (conq = factionData.getConquestEarned()) != 0.0f) {
									int conqInt = Math.round(conq);
									s = StatCollector.translateToLocalFormatted("got.gui.factions.data.conquest", conqInt);
									fontRendererObj.drawString(s, x, y, 8019267);
									y += fontRendererObj.FONT_HEIGHT;
								}
							}
							if (reputation <= 0.0f) {
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.npcsKilled", factionData.getNPCsKilled());
								fontRendererObj.drawString(s, x, y, 8019267);
							}
							if (buttonOath.visible && clientPD.hasTakenOathTo(currentFaction)) {
								s = StatCollector.translateToLocal("got.gui.factions.oathTaken");
								int px = buttonOath.xPosition + buttonOath.width + 8;
								int py = buttonOath.yPosition + buttonOath.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
								fontRendererObj.drawString(s, px, py, 16711680);
							}
						}
						break;
					case RANKS:
						GOTFactionRank curRank1 = currentFaction.getRank(clientPD);
						int[] minMax1 = scrollPaneAlliesEnemies.getMinMaxIndices(currentAlliesEnemies, numDisplayedAlliesEnemies);
						for (index = minMax1[0]; index <= minMax1[1]; ++index) {
							Object listObj = currentAlliesEnemies.get(index);
							if (listObj instanceof String) {
								s = (String) listObj;
								fontRendererObj.drawString(s, x, y, 8019267);
							} else if (listObj instanceof GOTFactionRank) {
								GOTFactionRank rank = (GOTFactionRank) listObj;
								String rankName1 = rank.getShortNameWithGender(clientPD);
								String rankRep = GOTReputationValues.formatRepForDisplay(rank.getReputation());
								if (rank == GOTFactionRank.RANK_ENEMY) {
									rankRep = "-";
								}
								boolean hiddenRankName = !clientPD.hasTakenOathTo(currentFaction) && rank.getReputation() > currentFaction.getOathReputation() && rank.getReputation() > currentFaction.getRankAbove(curRank1).getReputation();
								if (hiddenRankName) {
									rankName1 = StatCollector.translateToLocal("got.gui.factions.rank?");
								}
								s = StatCollector.translateToLocalFormatted("got.gui.factions.listRank", rankName1, rankRep);
								if (rank == curRank1) {
									GOTTickHandlerClient.drawReputationText(fontRendererObj, x, y, s, 1.0f);
								} else {
									fontRendererObj.drawString(s, x, y, 8019267);
								}
							}
							y += fontRendererObj.FONT_HEIGHT;
						}
						break;
				}
				if (scrollPaneAlliesEnemies.isHasScrollBar()) {
					scrollPaneAlliesEnemies.drawScrollBar();
				}
			} else {
				int stringWidth2 = PAGE_WIDTH - pageBorderLeft * 2;
				Collection<String> displayLines = new ArrayList<>();
				if (isTakingOath) {
					if (clientPD.canMakeNewOath()) {
						if (clientPD.canOathTo(currentFaction)) {
							String desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.oathDesc1", currentFaction.factionName());
							displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
							displayLines.add("");
							desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.oathDesc2");
							displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
						}
					} else {
						GOTFaction brokenOath = clientPD.getBrokenOathFaction();
						String brokenOathName = brokenOath == null ? StatCollector.translateToLocal("got.gui.factions.oathUnknown") : brokenOath.factionName();
						String desc3 = StatCollector.translateToLocalFormatted("got.gui.factions.oathBreakCooldown", currentFaction.factionName(), brokenOathName);
						displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc3, stringWidth2));
						displayLines.add("");
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						mc.getTextureManager().bindTexture(FACTIONS_TEXTURE);
						drawTexturedModalRect(guiLeft + PAGE_WIDTH / 2 - 97, guiTop + PAGE_Y + 56, 0, 240, 194, 16);
						float cdFrac = (float) clientPD.getOathBreakCooldown() / clientPD.getOathBreakCooldownStart();
						drawTexturedModalRect(guiLeft + PAGE_WIDTH / 2 - 75, guiTop + PAGE_Y + 60, 22, 232, MathHelper.ceiling_float_int(cdFrac * 150.0f), 8);
					}
				} else {
					String desc5 = StatCollector.translateToLocalFormatted("got.gui.factions.revokeOathDesc1", currentFaction.factionName());
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
					displayLines.add("");
					desc5 = StatCollector.translateToLocalFormatted("got.gui.factions.revokeOathDesc2");
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
				}
				for (String line : displayLines) {
					fontRendererObj.drawString(line, x, y, 8019267);
					y += mc.fontRenderer.FONT_HEIGHT;
				}
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(FACTIONS_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 0, 128, scrollBarWidth, scrollBarHeight);
			int factions = currentFactionList.size();
			for (int index = 0; index < factions; ++index) {
				GOTFaction faction = currentFactionList.get(index);
				float[] factionColors = faction.getFactionRGB();
				float shade = 0.6f;
				GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
				float xMin = (float) index / factions;
				float xMax = (float) (index + 1) / factions;
				xMin = guiLeft + scrollBarX + scrollBarBorder + xMin * (scrollBarWidth - scrollBarBorder * 2);
				xMax = guiLeft + scrollBarX + scrollBarBorder + xMax * (scrollBarWidth - scrollBarBorder * 2);
				float yMin = guiTop + scrollBarY + scrollBarBorder;
				float yMax = guiTop + scrollBarY + scrollBarHeight - scrollBarBorder;
				float minU = scrollBarBorder / 256.0f;
				float maxU = (scrollBarWidth - scrollBarBorder) / 256.0f;
				float minV = (128 + scrollBarBorder) / 256.0f;
				float maxV = (128 + scrollBarHeight - scrollBarBorder) / 256.0f;
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(xMin, yMax, zLevel, minU, maxV);
				tessellator.addVertexWithUV(xMax, yMax, zLevel, maxU, maxV);
				tessellator.addVertexWithUV(xMax, yMin, zLevel, maxU, minV);
				tessellator.addVertexWithUV(xMin, yMin, zLevel, minU, minV);
				tessellator.draw();
			}
			mc.getTextureManager().bindTexture(FACTIONS_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int scroll = (int) (currentScroll * (scrollBarWidth - scrollBarBorder * 2 - scrollWidgetWidth));
			drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder + scroll, guiTop + scrollBarY + scrollBarBorder, 0, 142, scrollWidgetWidth, scrollWidgetHeight);
		}
		super.drawScreen(i, j, f);
		if (buttonFactionMap.enabled && buttonFactionMap.func_146115_a()) {
			float z = zLevel;
			String s = StatCollector.translateToLocal("got.gui.factions.viewMap");
			stringWidth = 200;
			desc = fontRendererObj.listFormattedStringToWidth(s, stringWidth);
			func_146283_a(desc, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (mouseOverWarCrimes) {
			float z = zLevel;
			String warCrimes = currentFaction.isApprovesWarCrimes() ? "got.gui.factions.warCrimesYes" : "got.gui.factions.warCrimesNo";
			warCrimes = StatCollector.translateToLocal(warCrimes);
			stringWidth = 200;
			desc = fontRendererObj.listFormattedStringToWidth(warCrimes, stringWidth);
			func_146283_a(desc, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int k = Mouse.getEventDWheel();
		if (k != 0) {
			k = Integer.signum(k);
			if (scrollPaneAlliesEnemies.isHasScrollBar() && scrollPaneAlliesEnemies.isMouseOver()) {
				int l = currentAlliesEnemies.size() - numDisplayedAlliesEnemies;
				scrollPaneAlliesEnemies.mouseWheelScroll(k, l);
			} else {
				if (k < 0) {
					currentFactionIndex = Math.min(currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
				}
				if (k > 0) {
					currentFactionIndex = Math.max(currentFactionIndex - 1, 0);
				}
				setCurrentScrollFromFaction();
				scrollPaneAlliesEnemies.resetScroll();
				isTakingOath = false;
				isRevokingOath = false;
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - sizeX) / 2;
		guiTop = (height - sizeY) / 2 + 20;
		buttonRegions = new GOTGuiButton(0, guiLeft + sizeX / 2 + 5, guiTop + 200, 120, 20, "");
		buttonList.add(buttonRegions);
		goBack = new GOTGuiButton(0, guiLeft + sizeX / 2 - 125, guiTop + 200, 120, 20, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(goBack);
		buttonPagePrev = new GOTGuiButtonFactionsPage(1, guiLeft + 8, guiTop + PAGE_Y + 104, false);
		buttonList.add(buttonPagePrev);
		buttonPageNext = new GOTGuiButtonFactionsPage(2, guiLeft + 232, guiTop + PAGE_Y + 104, true);
		buttonList.add(buttonPageNext);
		buttonFactionMap = new GOTGuiButtonFactionsMap(3, guiLeft + PAGE_MAP_X + PAGE_MAP_SIZE - 3 - 8, guiTop + PAGE_Y + PAGE_MAP_Y + 3);
		buttonList.add(buttonFactionMap);
		buttonOath = new GOTGuiButtonOath(this, 4, guiLeft + 14, guiTop + PAGE_Y + PAGE_HEIGHT - 42, "");
		buttonList.add(buttonOath);
		buttonOathConfirm = new GOTGuiButtonOath(this, 5, guiLeft + PAGE_WIDTH / 2 - 16, guiTop + PAGE_Y + PAGE_HEIGHT - 50, "");
		buttonList.add(buttonOathConfirm);
		buttonOathRevoke = new GOTGuiButtonOath(this, 6, guiLeft + PAGE_WIDTH / 2 - 16, guiTop + PAGE_Y + PAGE_HEIGHT - 50, "");
		buttonList.add(buttonOathRevoke);
		buttonOathRevoke.setBroken(true);
		prevDimension = currentDimension = GOTDimension.GAME_OF_THRONES;
		currentFaction = GOTLevelData.getData(mc.thePlayer).getViewingFaction();
		prevRegion = currentRegion = currentFaction.getFactionRegion();
		currentFactionList = currentRegion.getFactionList();
		prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		setCurrentScrollFromFaction();
		if (mc.currentScreen == this) {
			IMessage packet = new GOTPacketClientMQEvent(GOTPacketClientMQEvent.ClientMQEvent.FACTIONS);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (isTakingOath) {
				isTakingOath = false;
				return;
			}
			if (isRevokingOath) {
				isRevokingOath = false;
				return;
			}
			if (isOtherPlayer) {
				mc.thePlayer.closeScreen();
				return;
			}
		}
		super.keyTyped(c, i);
	}

	private void setCurrentScrollFromFaction() {
		currentScroll = (float) currentFactionIndex / (currentFactionList.size() - 1);
	}

	public void setOtherPlayer(String name, Map<GOTFaction, Float> reputations) {
		isOtherPlayer = true;
		otherPlayerName = name;
		playerReputationMap = reputations;
	}

	private void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = guiLeft + scrollBarX;
		int j1 = guiTop + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = true;
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (i - i1 - scrollWidgetWidth / 2.0f) / ((float) (i2 - i1) - scrollWidgetWidth);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
			currentFactionIndex = Math.round(currentScroll * (currentFactionList.size() - 1));
			scrollPaneAlliesEnemies.resetScroll();
		}
		//noinspection StreamToLoop
		if (Stream.of(Page.ALLIES, Page.ENEMIES, Page.RANKS).anyMatch(page -> currentPage == page)) {
			switch (currentPage) {
				case ALLIES:
					List<GOTFaction> friends;
					currentAlliesEnemies = new ArrayList<>();
					List<GOTFaction> allies = currentFaction.getOthersOfRelation(GOTFactionRelations.Relation.ALLY);
					if (!allies.isEmpty()) {
						currentAlliesEnemies.add(GOTFactionRelations.Relation.ALLY);
						currentAlliesEnemies.addAll(allies);
					}
					if (!(friends = currentFaction.getOthersOfRelation(GOTFactionRelations.Relation.FRIEND)).isEmpty()) {
						if (!currentAlliesEnemies.isEmpty()) {
							currentAlliesEnemies.add(null);
						}
						currentAlliesEnemies.add(GOTFactionRelations.Relation.FRIEND);
						currentAlliesEnemies.addAll(friends);
					}
					break;
				case ENEMIES:
					List<GOTFaction> enemies;
					currentAlliesEnemies = new ArrayList<>();
					List<GOTFaction> mortals = currentFaction.getOthersOfRelation(GOTFactionRelations.Relation.MORTAL_ENEMY);
					if (!mortals.isEmpty()) {
						currentAlliesEnemies.add(GOTFactionRelations.Relation.MORTAL_ENEMY);
						currentAlliesEnemies.addAll(mortals);
					}
					if (!(enemies = currentFaction.getOthersOfRelation(GOTFactionRelations.Relation.ENEMY)).isEmpty()) {
						if (!currentAlliesEnemies.isEmpty()) {
							currentAlliesEnemies.add(null);
						}
						currentAlliesEnemies.add(GOTFactionRelations.Relation.ENEMY);
						currentAlliesEnemies.addAll(enemies);
					}
					break;
				case RANKS:
					currentAlliesEnemies = new ArrayList<>();
					currentAlliesEnemies.add(StatCollector.translateToLocal("got.gui.factions.rankHeader"));
					if (GOTLevelData.getData(mc.thePlayer).getReputation(currentFaction) <= 0.0f) {
						currentAlliesEnemies.add(GOTFactionRank.RANK_ENEMY);
					}
					GOTFactionRank rank = GOTFactionRank.RANK_NEUTRAL;
					do {
						currentAlliesEnemies.add(rank);
						GOTFactionRank nextRank = currentFaction.getRankAbove(rank);
						if (nextRank == null || nextRank.isDummyRank() || currentAlliesEnemies.contains(nextRank)) {
							break;
						}
						rank = nextRank;
					} while (true);
					break;
			}
			scrollPaneAlliesEnemies.setHasScrollBar(false);
			numDisplayedAlliesEnemies = currentAlliesEnemies.size();
			if (numDisplayedAlliesEnemies > 10) {
				numDisplayedAlliesEnemies = 10;
				scrollPaneAlliesEnemies.setHasScrollBar(true);
			}
			scrollPaneAlliesEnemies.setPaneX0(guiLeft);
			scrollPaneAlliesEnemies.setScrollBarX0(guiLeft + scrollAlliesEnemiesX);
			if (currentPage == Page.RANKS) {
				scrollPaneAlliesEnemies.setScrollBarX0(scrollPaneAlliesEnemies.getScrollBarX0() + 50);
			}
			scrollPaneAlliesEnemies.setPaneY0(guiTop + PAGE_Y + PAGE_BORDER_TOP);
			scrollPaneAlliesEnemies.setPaneY1(scrollPaneAlliesEnemies.getPaneY0() + fontRendererObj.FONT_HEIGHT * numDisplayedAlliesEnemies);
		} else {
			scrollPaneAlliesEnemies.setHasScrollBar(false);
		}
		scrollPaneAlliesEnemies.mouseDragScroll(i, j);
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapDrawGui.setWorldAndResolution(mc, i, j);
	}

	private void updateCurrentDimensionAndFaction() {
		GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
		Map<GOTDimension.DimensionRegion, GOTFaction> lastViewedRegions = new EnumMap<>(GOTDimension.DimensionRegion.class);
		if (currentFactionIndex != prevFactionIndex) {
			currentFaction = currentFactionList.get(currentFactionIndex);
		}
		prevFactionIndex = currentFactionIndex;
		currentDimension = GOTDimension.GAME_OF_THRONES;
		if (currentDimension != prevDimension) {
			currentRegion = currentDimension.getDimensionRegions().get(0);
		}
		if (currentRegion != prevRegion) {
			pd.setRegionLastViewedFaction(prevRegion, currentFaction);
			lastViewedRegions.put(prevRegion, currentFaction);
			currentFactionList = currentRegion.getFactionList();
			currentFaction = pd.getRegionLastViewedFaction(currentRegion);
			prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		}
		prevDimension = currentDimension;
		prevRegion = currentRegion;
		GOTFaction prevFaction = pd.getViewingFaction();
		boolean changes = currentFaction != prevFaction;
		if (changes) {
			pd.setViewingFaction(currentFaction);
			GOTClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			isTakingOath = false;
			isRevokingOath = false;
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCurrentDimensionAndFaction();
		GOTPlayerData playerData = GOTLevelData.getData(mc.thePlayer);
		if (isTakingOath && !playerData.hasOathReputation(currentFaction)) {
			isTakingOath = false;
		}
		if (isRevokingOath && !playerData.hasTakenOathTo(currentFaction)) {
			isRevokingOath = false;
		}
	}

	private boolean useFullPageTexture() {
		return isTakingOath || isRevokingOath || currentPage == Page.RANKS;
	}

	private enum Page {
		FRONT, RANKS, ALLIES, ENEMIES;

		private Page next() {
			int i = ordinal();
			if (i == values().length - 1) {
				return null;
			}
			i++;
			return values()[i];
		}

		private Page prev() {
			int i = ordinal();
			if (i == 0) {
				return null;
			}
			i--;
			return values()[i];
		}
	}
}