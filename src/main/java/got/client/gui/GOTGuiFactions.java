package got.client.gui;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.math.IntMath;

import got.client.*;
import got.common.*;
import got.common.faction.*;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.*;

public class GOTGuiFactions extends GOTGuiMenuWBBase {
	private static ResourceLocation factionsTexture = new ResourceLocation("got:textures/gui/factions.png");
	private static ResourceLocation factionsTextureFull = new ResourceLocation("got:textures/gui/factions_full.png");
	private static GOTDimension currentDimension;
	private static GOTDimension prevDimension;
	private static GOTDimension.DimensionRegion currentRegion;
	private static GOTDimension.DimensionRegion prevRegion;
	private static List<GOTFaction> currentFactionList;
	private static Page currentPage = Page.FRONT;
	private int currentFactionIndex = 0;
	private int prevFactionIndex = 0;
	private GOTFaction currentFaction;
	private int pageY = 46;
	private int pageWidth = 256;
	private int pageHeight = 128;
	private int pageBorderLeft = 16;
	private int pageBorderTop = 12;
	private int pageMapX = 159;
	private int pageMapY = 22;
	private int pageMapSize = 80;
	private GOTGuiMap mapDrawGui;
	private GuiButton buttonRegions;
	private GuiButton buttonPagePrev;
	private GuiButton buttonPageNext;
	private GuiButton buttonFactionMap;
	private GOTGuiButtonPledge buttonPledge;
	private GOTGuiButtonPledge buttonPledgeConfirm;
	private GOTGuiButtonPledge buttonPledgeRevoke;
	private float currentScroll;
	private boolean isScrolling;
	private boolean wasMouseDown;
	private int scrollBarWidth;
	private int scrollBarHeight;
	private int scrollBarX;
	private int scrollBarY;
	private int scrollBarBorder;
	private int scrollWidgetWidth;
	private int scrollWidgetHeight;
	private GOTGuiScrollPane scrollPaneAlliesEnemies;
	private int scrollAlliesEnemiesX;
	private int numDisplayedAlliesEnemies;
	private List currentAlliesEnemies;
	private boolean isOtherPlayer;
	private String otherPlayerName;
	private Map<GOTFaction, Float> playerAlignmentMap;
	private boolean isPledging;
	private boolean isUnpledging;

	public GOTGuiFactions() {
		setxSize(pageWidth);
		currentScroll = 0.0f;
		isScrolling = false;
		scrollBarWidth = 240;
		scrollBarHeight = 14;
		scrollBarX = getxSize() / 2 - scrollBarWidth / 2;
		scrollBarY = 180;
		scrollBarBorder = 1;
		scrollWidgetWidth = 17;
		scrollWidgetHeight = 12;
		scrollPaneAlliesEnemies = new GOTGuiScrollPane(7, 7).setColors(5521198, 8019267);
		scrollAlliesEnemiesX = 138;
		isOtherPlayer = false;
		isPledging = false;
		isUnpledging = false;
		mapDrawGui = new GOTGuiMap();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonRegions) {
				List<GOTDimension.DimensionRegion> regionList = GOTGuiFactions.currentDimension.getDimensionRegions();
				if (!regionList.isEmpty()) {
					int i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					updateCurrentDimensionAndFaction();
					setCurrentScrollFromFaction();
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button.enabled && button == getGoBack()) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button == buttonPagePrev) {
				Page newPage = currentPage.prev();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button == buttonPageNext) {
				Page newPage = currentPage.next();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button == buttonFactionMap) {
				GOTGuiMap factionGuiMap = new GOTGuiMap();
				factionGuiMap.setControlZone(currentFaction);
				mc.displayGuiScreen(factionGuiMap);
			} else if (button == buttonPledge) {
				if (GOTLevelData.getData(mc.thePlayer).isPledgedTo(currentFaction)) {
					isUnpledging = true;
				} else {
					isPledging = true;
				}
			} else if (button == buttonPledgeConfirm) {
				GOTPacketPledgeSet packet = new GOTPacketPledgeSet(currentFaction);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				isPledging = false;
			} else if (button == buttonPledgeRevoke) {
				GOTPacketPledgeSet packet = new GOTPacketPledgeSet(null);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				isUnpledging = false;
				mc.displayGuiScreen(null);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	private boolean canScroll() {
		return true;
	}

	public void drawButtonHoveringText(List list, int i, int j) {
		func_146283_a(list, i, j);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		List desc;
		int stringWidth;
		GOTPlayerData clientPD = GOTLevelData.getData(mc.thePlayer);
		boolean mouseOverWarCrimes = false;
		if (!isPledging && !isUnpledging) {
			buttonPagePrev.enabled = currentPage.prev() != null;
			buttonPageNext.enabled = currentPage.next() != null;
			buttonFactionMap.enabled = currentPage != Page.RANKS && currentFaction.isPlayableAlignmentFaction() && GOTDimension.getCurrentDimension(mc.theWorld) == currentFaction.getFactionDimension();
			buttonFactionMap.visible = buttonFactionMap.enabled;
			if (!GOTFaction.controlZonesEnabled(mc.theWorld)) {
				buttonFactionMap.enabled = false;
				buttonFactionMap.visible = false;
			}
			if (!isOtherPlayer && currentPage == Page.FRONT) {
				if (clientPD.isPledgedTo(currentFaction)) {
					buttonPledge.setBroken(buttonPledge.func_146115_a());
					buttonPledge.enabled = true;
					buttonPledge.visible = true;
					buttonPledge.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.unpledge"));
				} else {
					buttonPledge.setBroken(false);
					buttonPledge.visible = clientPD.getPledgeFaction() == null && currentFaction.isPlayableAlignmentFaction() && clientPD.getAlignment(currentFaction) >= 0.0f;
					buttonPledge.enabled = buttonPledge.visible && clientPD.hasPledgeAlignment(currentFaction);
					String desc1 = StatCollector.translateToLocal("got.gui.factions.pledge");
					String desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.pledgeReq", GOTAlignmentValues.formatAlignForDisplay(currentFaction.getPledgeAlignment()));
					buttonPledge.setDisplayLines(desc1, desc2);
				}
			} else {
				buttonPledge.enabled = false;
				buttonPledge.visible = false;
			}
			buttonPledgeConfirm.enabled = false;
			buttonPledgeConfirm.visible = false;
			buttonPledgeRevoke.enabled = false;
			buttonPledgeRevoke.visible = false;
		} else {
			buttonPagePrev.enabled = false;
			buttonPageNext.enabled = false;
			buttonFactionMap.enabled = false;
			buttonFactionMap.visible = false;
			buttonPledge.enabled = false;
			buttonPledge.visible = false;
			if (isPledging) {
				buttonPledgeConfirm.visible = true;
				buttonPledgeConfirm.enabled = clientPD.canMakeNewPledge() && clientPD.canPledgeTo(currentFaction);
				buttonPledgeConfirm.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.pledge"));
				buttonPledgeRevoke.enabled = false;
				buttonPledgeRevoke.visible = false;
			} else if (isUnpledging) {
				buttonPledgeConfirm.enabled = false;
				buttonPledgeConfirm.visible = false;
				buttonPledgeRevoke.enabled = true;
				buttonPledgeRevoke.visible = true;
				buttonPledgeRevoke.setDisplayLines(StatCollector.translateToLocal("got.gui.factions.unpledge"));
			}
		}
		setupScrollBar(i, j);
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (useFullPageTexture()) {
			mc.getTextureManager().bindTexture(factionsTextureFull);
		} else {
			mc.getTextureManager().bindTexture(getFactionsTexture());
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.drawTexturedModalRect(getGuiLeft(), getGuiTop() + pageY, 0, 0, pageWidth, pageHeight);
		if (currentRegion != null && GOTGuiFactions.currentDimension.getDimensionRegions().size() > 1) {
			buttonRegions.displayString = currentRegion.getRegionName();
			buttonRegions.enabled = true;
			buttonRegions.visible = true;
		} else {
			buttonRegions.displayString = "";
			buttonRegions.enabled = false;
			buttonRegions.visible = false;
		}
		if (currentFaction != null) {
			float alignment = isOtherPlayer && playerAlignmentMap != null ? playerAlignmentMap.get(currentFaction) : clientPD.getAlignment(currentFaction);
			int x = getGuiLeft() + getxSize() / 2;
			int y = getGuiTop();
			GOTTickHandlerClient.renderAlignmentBar(alignment, currentFaction, x, y, true, false, true, true);
			String s = currentFaction.factionSubtitle();
			this.drawCenteredString(s, x, y += fontRendererObj.FONT_HEIGHT + 22, 16777215);
			if (!useFullPageTexture()) {
				if (currentFaction.getFactionMapInfo() != null) {
					GOTMapRegion mapInfo = currentFaction.getFactionMapInfo();
					int mapX = mapInfo.getMapX();
					int mapY = mapInfo.getMapY();
					int mapR = mapInfo.getRadius();
					int xMin = getGuiLeft() + pageMapX;
					int xMax = xMin + pageMapSize;
					int yMin = getGuiTop() + pageY + pageMapY;
					int yMax = yMin + pageMapSize;
					int mapBorder = 1;
					Gui.drawRect(xMin - mapBorder, yMin - mapBorder, xMax + mapBorder, yMax + mapBorder, -16777216);
					float zoom = (float) pageMapSize / (float) (mapR * 2);
					float zoomExp = (float) Math.log(zoom) / (float) Math.log(2.0);
					mapDrawGui.setFakeMapProperties(mapX, mapY, zoom, zoomExp, zoom);
					int[] statics = GOTGuiMap.setFakeStaticProperties(pageMapSize, pageMapSize, xMin, xMax, yMin, yMax);
					mapDrawGui.setEnableZoomOutWPFading(false);
					boolean sepia = GOTConfig.isEnableSepiaMap();
					mapDrawGui.renderMapAndOverlay(sepia, 1.0f, true);
					GOTGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
				}
				int wcX = getGuiLeft() + pageMapX + 3;
				int wcY = getGuiTop() + pageY + pageMapY + pageMapSize + 5;
				int wcWidth = 8;
				mc.getTextureManager().bindTexture(getFactionsTexture());
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				if (currentFaction.isViolent()) {
					this.drawTexturedModalRect(wcX, wcY, 33, 142, wcWidth, wcWidth);
				} else {
					this.drawTexturedModalRect(wcX, wcY, 41, 142, wcWidth, wcWidth);
				}
				if (i >= wcX && i < wcX + wcWidth && j >= wcY && j < wcY + wcWidth) {
					mouseOverWarCrimes = true;
				}
			}
			x = getGuiLeft() + pageBorderLeft;
			y = getGuiTop() + pageY + pageBorderTop;
			if (!isPledging && !isUnpledging) {
				int index;
				if (currentPage == Page.FRONT) {
					if (isOtherPlayer) {
						s = StatCollector.translateToLocalFormatted("got.gui.factions.pageOther", otherPlayerName);
						fontRendererObj.drawString(s, x, y, 8019267);
						y += fontRendererObj.FONT_HEIGHT * 2;
					}
					String alignmentInfo = StatCollector.translateToLocal("got.gui.factions.alignment");
					fontRendererObj.drawString(alignmentInfo, x, y, 8019267);
					String alignmentString = GOTAlignmentValues.formatAlignForDisplay(alignment);
					GOTTickHandlerClient.drawAlignmentText(fontRendererObj, x += fontRendererObj.getStringWidth(alignmentInfo) + 5, y, alignmentString, 1.0f);

					x = getGuiLeft() + pageBorderLeft;
					GOTFactionRank curRank = currentFaction.getRank(alignment);
					String rankName = curRank.getFullNameWithGender(clientPD);
					fontRendererObj.drawString(rankName, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
					y += fontRendererObj.FONT_HEIGHT * 2;
					if (!isOtherPlayer) {
						GOTFactionData factionData = clientPD.getFactionData(currentFaction);
						if (alignment >= 0.0f) {
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
							if (clientPD.isPledgedTo(currentFaction) && (conq = factionData.getConquestEarned()) != 0.0f) {
								int conqInt = Math.round(conq);
								s = StatCollector.translateToLocalFormatted("got.gui.factions.data.conquest", conqInt);
								fontRendererObj.drawString(s, x, y, 8019267);
								y += fontRendererObj.FONT_HEIGHT;
							}
						}
						if (alignment <= 0.0f) {
							s = StatCollector.translateToLocalFormatted("got.gui.factions.data.npcsKilled", factionData.getNPCsKilled());
							fontRendererObj.drawString(s, x, y, 8019267);
							y += fontRendererObj.FONT_HEIGHT;
						}
						if (buttonPledge.visible && clientPD.isPledgedTo(currentFaction)) {
							s = StatCollector.translateToLocal("got.gui.factions.pledged");
							int px = buttonPledge.xPosition + buttonPledge.width + 8;
							int py = buttonPledge.yPosition + buttonPledge.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
							fontRendererObj.drawString(s, px, py, 16711680);
						}
					}
				} else if (currentPage == Page.RANKS) {
					GOTFactionRank curRank = currentFaction.getRank(clientPD);
					int[] minMax = scrollPaneAlliesEnemies.getMinMaxIndices(currentAlliesEnemies, numDisplayedAlliesEnemies);
					for (index = minMax[0]; index <= minMax[1]; ++index) {
						Object listObj = currentAlliesEnemies.get(index);
						if (listObj instanceof String) {
							s = (String) listObj;
							fontRendererObj.drawString(s, x, y, 8019267);
						} else if (listObj instanceof GOTFactionRank) {
							GOTFactionRank rank = (GOTFactionRank) listObj;
							String rankName = rank.getShortNameWithGender(clientPD);
							String rankAlign = GOTAlignmentValues.formatAlignForDisplay(rank.getAlignment());
							if (rank == GOTFactionRank.getRankEnemy()) {
								rankAlign = "-";
							}
							boolean hiddenRankName = false;
							if (!clientPD.isPledgedTo(currentFaction) && rank.getAlignment() > currentFaction.getPledgeAlignment() && rank.getAlignment() > currentFaction.getRankAbove(curRank).getAlignment()) {
								hiddenRankName = true;
							}
							if (hiddenRankName) {
								rankName = StatCollector.translateToLocal("got.gui.factions.rank?");
							}
							s = StatCollector.translateToLocalFormatted("got.gui.factions.listRank", rankName, rankAlign);
							if (rank == curRank) {
								GOTTickHandlerClient.drawAlignmentText(fontRendererObj, x, y, s, 1.0f);
							} else {
								fontRendererObj.drawString(s, x, y, 8019267);
							}
						}
						y += fontRendererObj.FONT_HEIGHT;
					}
				} else if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES) {
					int avgBgColor = GOTTextures.computeAverageFactionPageColor(getFactionsTexture(), 20, 20, 120, 80);
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
				}
				if (scrollPaneAlliesEnemies.isHasScrollBar()) {
					scrollPaneAlliesEnemies.drawScrollBar();
				}
			} else {
				int stringWidth2 = pageWidth - pageBorderLeft * 2;
				ArrayList<String> displayLines = new ArrayList<>();
				if (isPledging) {
					if (clientPD.canMakeNewPledge()) {
						if (clientPD.canPledgeTo(currentFaction)) {
							String desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.pledgeDesc1", currentFaction.factionName());
							displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
							displayLines.add("");
							desc2 = StatCollector.translateToLocalFormatted("got.gui.factions.pledgeDesc2");
							displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
						}
					} else {
						GOTFaction brokenPledge = clientPD.getBrokenPledgeFaction();
						String brokenPledgeName = brokenPledge == null ? StatCollector.translateToLocal("got.gui.factions.pledgeUnknown") : brokenPledge.factionName();
						String desc3 = StatCollector.translateToLocalFormatted("got.gui.factions.pledgeBreakCooldown", currentFaction.factionName(), brokenPledgeName);
						displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc3, stringWidth2));
						displayLines.add("");
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						mc.getTextureManager().bindTexture(getFactionsTexture());
						this.drawTexturedModalRect(getGuiLeft() + pageWidth / 2 - 97, getGuiTop() + pageY + 56, 0, 240, 194, 16);
						float cdFrac = (float) clientPD.getPledgeBreakCooldown() / (float) clientPD.getPledgeBreakCooldownStart();
						this.drawTexturedModalRect(getGuiLeft() + pageWidth / 2 - 75, getGuiTop() + pageY + 60, 22, 232, MathHelper.ceiling_float_int(cdFrac * 150.0f), 8);
					}
				} else if (isUnpledging) {
					String desc5 = StatCollector.translateToLocalFormatted("got.gui.factions.unpledgeDesc1", currentFaction.factionName());
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
					displayLines.add("");
					desc5 = StatCollector.translateToLocalFormatted("got.gui.factions.unpledgeDesc2");
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
				}
				for (String line : displayLines) {
					fontRendererObj.drawString(line, x, y, 8019267);
					y += mc.fontRenderer.FONT_HEIGHT;
				}
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(getFactionsTexture());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(getGuiLeft() + scrollBarX, getGuiTop() + scrollBarY, 0, 128, scrollBarWidth, scrollBarHeight);
			int factions = currentFactionList.size();
			for (int index = 0; index < factions; ++index) {
				GOTFaction faction = currentFactionList.get(index);
				float[] factionColors = faction.getFactionRGB();
				float shade = 0.6f;
				GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
				float xMin = (float) index / (float) factions;
				float xMax = (float) (index + 1) / (float) factions;
				xMin = getGuiLeft() + scrollBarX + scrollBarBorder + xMin * (scrollBarWidth - scrollBarBorder * 2);
				xMax = getGuiLeft() + scrollBarX + scrollBarBorder + xMax * (scrollBarWidth - scrollBarBorder * 2);
				float yMin = getGuiTop() + scrollBarY + scrollBarBorder;
				float yMax = getGuiTop() + scrollBarY + scrollBarHeight - scrollBarBorder;
				float minU = (0 + scrollBarBorder) / 256.0f;
				float maxU = (0 + scrollBarWidth - scrollBarBorder) / 256.0f;
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
			mc.getTextureManager().bindTexture(getFactionsTexture());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			if (canScroll()) {
				int scroll = (int) (currentScroll * (scrollBarWidth - scrollBarBorder * 2 - scrollWidgetWidth));
				this.drawTexturedModalRect(getGuiLeft() + scrollBarX + scrollBarBorder + scroll, getGuiTop() + scrollBarY + scrollBarBorder, 0, 142, scrollWidgetWidth, scrollWidgetHeight);
			}
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
			String warCrimes = currentFaction.isViolent() ? "got.gui.factions.warCrimesYes" : "got.gui.factions.warCrimesNo";
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
				isPledging = false;
				isUnpledging = false;
			}
		}
	}

	private boolean hasScrollBar() {
		return currentFactionList.size() > 1;
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2);
		setGuiTop((height - getySize()) / 2 + 20);
		buttonRegions = new GOTGuiButton(0, getGuiLeft() + getxSize() / 2 + 5, getGuiTop() + 200, 120, 20, "");
		buttonList.add(buttonRegions);
		setGoBack(new GOTGuiButton(0, getGuiLeft() + getxSize() / 2 - 125, getGuiTop() + 200, 120, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
		buttonPagePrev = new GOTGuiButtonFactionsPage(1, getGuiLeft() + 8, getGuiTop() + pageY + 104, false);
		buttonList.add(buttonPagePrev);
		buttonPageNext = new GOTGuiButtonFactionsPage(2, getGuiLeft() + 232, getGuiTop() + pageY + 104, true);
		buttonList.add(buttonPageNext);
		buttonFactionMap = new GOTGuiButtonFactionsMap(3, getGuiLeft() + pageMapX + pageMapSize - 3 - 8, getGuiTop() + pageY + pageMapY + 3);
		buttonList.add(buttonFactionMap);
		buttonPledge = new GOTGuiButtonPledge(this, 4, getGuiLeft() + 14, getGuiTop() + pageY + pageHeight - 42, "");
		buttonList.add(buttonPledge);
		buttonPledgeConfirm = new GOTGuiButtonPledge(this, 5, getGuiLeft() + pageWidth / 2 - 16, getGuiTop() + pageY + pageHeight - 50, "");
		buttonList.add(buttonPledgeConfirm);
		buttonPledgeRevoke = new GOTGuiButtonPledge(this, 6, getGuiLeft() + pageWidth / 2 - 16, getGuiTop() + pageY + pageHeight - 50, "");
		buttonList.add(buttonPledgeRevoke);
		buttonPledgeRevoke.setBroken(true);
		prevDimension = currentDimension = GOTDimension.getCurrentDimension(mc.theWorld);
		currentFaction = GOTLevelData.getData(mc.thePlayer).getViewingFaction();
		prevRegion = currentRegion = currentFaction.getFactionRegion();
		currentFactionList = GOTGuiFactions.currentRegion.getFactionList();
		prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		setCurrentScrollFromFaction();
		if (mc.currentScreen == this) {
			GOTPacketClientMQEvent packet = new GOTPacketClientMQEvent(GOTPacketClientMQEvent.ClientMQEvent.FACTIONS);
			GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (isPledging) {
				isPledging = false;
				return;
			}
			if (isUnpledging) {
				isUnpledging = false;
				return;
			}
			if (isOtherPlayer) {
				mc.thePlayer.closeScreen();
				return;
			}
		}
		super.keyTyped(c, i);
	}

	public void setCurrentScrollFromFaction() {
		currentScroll = (float) currentFactionIndex / (float) (currentFactionList.size() - 1);
	}

	public void setOtherPlayer(String name, Map<GOTFaction, Float> alignments) {
		isOtherPlayer = true;
		otherPlayerName = name;
		playerAlignmentMap = alignments;
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = getGuiLeft() + scrollBarX;
		int j1 = getGuiTop() + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = canScroll();
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (i - i1 - scrollWidgetWidth / 2.0f) / ((float) (i2 - i1) - (float) scrollWidgetWidth);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
			currentFactionIndex = Math.round(currentScroll * (currentFactionList.size() - 1));
			scrollPaneAlliesEnemies.resetScroll();
		}
		if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES || currentPage == Page.RANKS) {
			if (currentPage == Page.ALLIES) {
				List<GOTFaction> friends;
				currentAlliesEnemies = new ArrayList();
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
			} else if (currentPage == Page.ENEMIES) {
				List<GOTFaction> enemies;
				currentAlliesEnemies = new ArrayList();
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
			} else if (currentPage == Page.RANKS) {
				currentAlliesEnemies = new ArrayList();
				currentAlliesEnemies.add(StatCollector.translateToLocal("got.gui.factions.rankHeader"));
				if (GOTLevelData.getData(mc.thePlayer).getAlignment(currentFaction) <= 0.0f) {
					currentAlliesEnemies.add(GOTFactionRank.getRankEnemy());
				}
				GOTFactionRank rank = GOTFactionRank.getRankNeutral();
				do {
					currentAlliesEnemies.add(rank);
					GOTFactionRank nextRank = currentFaction.getRankAbove(rank);
					if (nextRank == null || nextRank.isDummyRank() || currentAlliesEnemies.contains(nextRank)) {
						break;
					}
					rank = nextRank;
				} while (true);
			}
			scrollPaneAlliesEnemies.setHasScrollBar(false);
			numDisplayedAlliesEnemies = currentAlliesEnemies.size();
			if (numDisplayedAlliesEnemies > 10) {
				numDisplayedAlliesEnemies = 10;
				scrollPaneAlliesEnemies.setHasScrollBar(true);
			}
			scrollPaneAlliesEnemies.setPaneX0(getGuiLeft());
			scrollPaneAlliesEnemies.setScrollBarX0(getGuiLeft() + scrollAlliesEnemiesX);
			if (currentPage == Page.RANKS) {
				scrollPaneAlliesEnemies.setScrollBarX0(scrollPaneAlliesEnemies.getScrollBarX0() + 50);
			}
			scrollPaneAlliesEnemies.setPaneY0(getGuiTop() + pageY + pageBorderTop);
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

	public void updateCurrentDimensionAndFaction() {
		boolean changes;
		GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
		HashMap<GOTDimension.DimensionRegion, GOTFaction> lastViewedRegions = new HashMap<>();
		if (currentFactionIndex != prevFactionIndex) {
			currentFaction = currentFactionList.get(currentFactionIndex);
		}
		prevFactionIndex = currentFactionIndex;
		currentDimension = GOTDimension.getCurrentDimension(mc.theWorld);
		if (currentDimension != prevDimension) {
			currentRegion = GOTGuiFactions.currentDimension.getDimensionRegions().get(0);
		}
		if (currentRegion != prevRegion) {
			pd.setRegionLastViewedFaction(prevRegion, currentFaction);
			lastViewedRegions.put(prevRegion, currentFaction);
			currentFactionList = GOTGuiFactions.currentRegion.getFactionList();
			currentFaction = pd.getRegionLastViewedFaction(currentRegion);
			prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		}
		prevDimension = currentDimension;
		prevRegion = currentRegion;
		GOTFaction prevFaction = pd.getViewingFaction();
		changes = currentFaction != prevFaction;
		if (changes) {
			pd.setViewingFaction(currentFaction);
			GOTClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			isPledging = false;
			isUnpledging = false;
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCurrentDimensionAndFaction();
		GOTPlayerData playerData = GOTLevelData.getData(mc.thePlayer);
		if (isPledging && !playerData.hasPledgeAlignment(currentFaction)) {
			isPledging = false;
		}
		if (isUnpledging && !playerData.isPledgedTo(currentFaction)) {
			isUnpledging = false;
		}
	}

	private boolean useFullPageTexture() {
		return isPledging || isUnpledging || currentPage == Page.RANKS;
	}

	public static ResourceLocation getFactionsTexture() {
		return factionsTexture;
	}

	public static void setFactionsTexture(ResourceLocation factionsTexture) {
		GOTGuiFactions.factionsTexture = factionsTexture;
	}

	public enum Page {
		FRONT, RANKS, ALLIES, ENEMIES;

		private Page next() {
			int i = ordinal();
			if (i == Page.values().length - 1) {
				return null;
			}
			i++;
			return Page.values()[i];
		}

		private Page prev() {
			int i = ordinal();
			if (i == 0) {
				return null;
			}
			i--;
			return Page.values()[i];
		}
	}

}