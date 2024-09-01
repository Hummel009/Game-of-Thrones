package got.client.gui;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.client.GOTTextBody;
import got.common.GOTDate;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTSpeech;
import got.common.faction.GOTReputationValues;
import got.common.network.GOTPacketDeleteMiniquest;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMiniquestTrack;
import got.common.quest.GOTMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTGuiQuestBook extends GOTGuiScreenBase {
	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/quest/questBook.png");

	private static final ResourceLocation GUI_TEXTURE_MINIQUESTS = new ResourceLocation("got:textures/gui/quest/questBook_miniquests.png");
	private static final RenderItem ITEM_RENDERER = new RenderItem();

	private static final int X_SIZE = 420;
	private static final int Y_SIZE = 256;
	private static final int PAGE_WIDTH = 186;
	private static final int PAGE_BORDER = 10;
	private static final int SCROLL_BAR_WIDTH = 12;
	private static final int SCROLL_BAR_HEIGHT = 216;
	private static final int SCROLL_BAR_X = X_SIZE / 2 + PAGE_WIDTH;
	private static final int SCROLL_BAR_Y = 18;
	private static final int SCROLL_WIDGET_HEIGHT = 17;
	private static final int MAX_DISPLAYED_MINI_QUESTS = 4;
	private static final int Q_PANEL_WIDTH = 170;
	private static final int Q_PANEL_HEIGHT = 45;
	private static final int Q_DEL_X = 158;
	private static final int Q_DEL_Y = 4;
	private static final int Q_TRACK_X = 148;
	private static final int Q_TRACK_Y = 4;
	private static final int Q_WIDGET_SIZE = 8;
	private static final int DIARY_WIDTH = 170;
	private static final int DIARY_HEIGHT = 218;
	private static final int DIARY_X = X_SIZE / 2 - PAGE_BORDER - PAGE_WIDTH / 2 - DIARY_WIDTH / 2;
	private static final int DIARY_Y = Y_SIZE / 2 - DIARY_HEIGHT / 2 - 1;

	private static Page page;

	private static boolean viewCompleted;

	private final Map<GOTMiniQuest, Pair<Integer, Integer>> displayedMiniQuests = new HashMap<>();

	private GOTMiniQuest deletingMiniquest;
	private GOTMiniQuest selectedMiniquest;

	private GuiButton buttonQuestDelete;
	private GuiButton buttonQuestDeleteCancel;
	private GuiButton buttonViewActive;
	private GuiButton buttonViewCompleted;

	private boolean isDiaryScrolling;
	private boolean isScrolling;
	private boolean mouseInDiary;
	private boolean wasMouseDown;

	private float currentScroll;
	private float diaryScroll;

	private int guiLeft;
	private int guiTop;
	private int lastMouseY;
	private int trackTicks;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonViewActive) {
				viewCompleted = false;
			}
			if (button == buttonViewCompleted) {
				viewCompleted = true;
			}
			if (button == buttonQuestDelete && deletingMiniquest != null) {
				IMessage packet = new GOTPacketDeleteMiniquest(deletingMiniquest);
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
				deletingMiniquest = null;
				selectedMiniquest = null;
				diaryScroll = 0.0f;
			}
			if (button == buttonQuestDeleteCancel && deletingMiniquest != null) {
				deletingMiniquest = null;
			}
		}
	}

	private boolean canScroll() {
		return hasScrollBar() && getMiniQuests().size() > MAX_DISPLAYED_MINI_QUESTS;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		displayedMiniQuests.clear();
		setupScrollBar(i, j);
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, X_SIZE, Y_SIZE, 512);
		int x = guiLeft + X_SIZE / 2 - PAGE_BORDER - PAGE_WIDTH / 2;
		int y = guiTop + 30;
		if (page == Page.MINIQUESTS && selectedMiniquest == null) {
			float scale = 2.0f;
			float invScale = 1.0f / scale;
			x = (int) (x * invScale);
			y = (int) (y * invScale);
			GL11.glScalef(scale, scale, scale);
			drawCenteredString(page.getTitle(), x, y, 8019267);
			GL11.glScalef(invScale, invScale, invScale);
			x = guiLeft + X_SIZE / 2 - PAGE_BORDER - PAGE_WIDTH / 2;
			y = guiTop + 50;
			if (viewCompleted) {
				drawCenteredString(StatCollector.translateToLocal("got.gui.redBook.mq.viewComplete"), x, y, 8019267);
			} else {
				drawCenteredString(StatCollector.translateToLocal("got.gui.redBook.mq.viewActive"), x, y, 8019267);
			}
		}
		if (page == Page.MINIQUESTS) {
			if (selectedMiniquest == null) {
				drawCenteredString(GOTDate.AegonCalendar.getDate().getDateName(false), guiLeft + X_SIZE / 2 - PAGE_BORDER - PAGE_WIDTH / 2, guiTop + Y_SIZE - 30, 8019267);
				drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.redBook.mq.numActive", getPlayerData().getActiveMiniQuests().size()), x, guiTop + 120, 8019267);
				drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.redBook.mq.numComplete", getPlayerData().getCompletedMiniQuestsTotal()), x, guiTop + 140, 8019267);
			} else {
				GOTMiniQuest quest = selectedMiniquest;
				mc.getTextureManager().bindTexture(GUI_TEXTURE);
				float[] questRGB = quest.getQuestColorComponents();
				GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
				x = guiLeft + DIARY_X;
				y = guiTop + DIARY_Y;
				drawTexturedModalRect(x, y, 0, 256, DIARY_WIDTH, DIARY_HEIGHT, 512);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int diaryBorder = 6;
				int textW = DIARY_WIDTH - diaryBorder * 2;
				int textBottom = y + DIARY_HEIGHT - diaryBorder;
				x += diaryBorder;
				y += diaryBorder;
				boolean completed = quest.isCompleted();
				boolean failed = !completed && quest.isFailed();
				String entityName = quest.getEntityName();
				String factionName = quest.getFactionSubtitle();
				GOTTextBody pageText = new GOTTextBody(8019267);
				pageText.setTextWidth(textW);
				String[] dayYear = GOTDate.AegonCalendar.getDate(quest.getDateGiven()).getDayAndYearNames(false);
				pageText.add(dayYear[0]);
				pageText.add(dayYear[1]);
				if (quest.getBiomeGiven() != null) {
					pageText.add(quest.getBiomeGiven().getBiomeDisplayName());
				}
				pageText.add("");
				String startQuote = GOTSpeech.formatSpeech(quest.getQuoteStart(), mc.thePlayer, null, quest.getObjectiveInSpeech());
				startQuote = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.quote", startQuote);
				pageText.add(startQuote);
				pageText.add("");
				List<String> quotesStages = quest.getQuotesStages();
				if (!quotesStages.isEmpty()) {
					for (String s : quotesStages) {
						String stageQuote = GOTSpeech.formatSpeech(s, mc.thePlayer, null, quest.getObjectiveInSpeech());
						stageQuote = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.quote", stageQuote);
						pageText.add(stageQuote);
						pageText.add("");
					}
				}
				String asked = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.asked", entityName, quest.getQuestObjective());
				pageText.add(asked);
				pageText.add("");
				String progress = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.progress", quest.getQuestProgress());
				pageText.add(progress);
				if (quest.isWillHire()) {
					pageText.add("");
					String willHire = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.willHire", entityName);
					pageText.add(willHire);
				}
				if (failed) {
					for (int l = 0; l < pageText.size(); ++l) {
						String line = EnumChatFormatting.STRIKETHROUGH + pageText.getText(l);
						pageText.set(l, line);
					}
					String failureText = quest.getQuestFailure();
					pageText.add(failureText, 16711680);
				}
				if (completed) {
					pageText.add("");
					pageText.addLinebreak();
					pageText.add("");
					dayYear = GOTDate.AegonCalendar.getDate(quest.getDateCompleted()).getDayAndYearNames(false);
					pageText.add(dayYear[0]);
					pageText.add(dayYear[1]);
					pageText.add("");
					String completeQuote = GOTSpeech.formatSpeech(quest.getQuoteComplete(), mc.thePlayer, null, quest.getObjectiveInSpeech());
					completeQuote = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.quote", completeQuote);
					pageText.add(completeQuote);
					pageText.add("");
					String completedText = StatCollector.translateToLocal("got.gui.redBook.mq.diary.complete");
					pageText.add(completedText);
					if (quest.anyRewardsGiven()) {
						pageText.add("");
						String rewardText = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward", entityName);
						pageText.add(rewardText);
						if (quest.getReputationRewarded() != 0.0f) {
							String alignS = GOTReputationValues.formatAlignForDisplay(quest.getReputationRewarded());
							String rewardAlign = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward.align", alignS, factionName);
							pageText.add(rewardAlign);
						}
						if (quest.getCoinsRewarded() != 0.0f) {
							String rewardCoins = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward.coins", quest.getCoinsRewarded());
							pageText.add(rewardCoins);
						}
						if (!quest.getItemsRewarded().isEmpty()) {
							for (ItemStack item : quest.getItemsRewarded()) {
								String rewardItem = item.getItem() instanceof ItemEditableBook ? StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward.book", item.getDisplayName()) : StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward.item", item.getDisplayName(), item.stackSize);
								pageText.add(rewardItem);
							}
						}
					}
					if (quest.isWasHired()) {
						pageText.add("");
						String rewardHired = StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.reward.hired", entityName);
						pageText.add(rewardHired);
					}
				}
				diaryScroll = pageText.renderAndReturnScroll(fontRendererObj, x, y, textBottom, diaryScroll);
			}
			int pageTop = 18;
			if (deletingMiniquest == null) {
				List<GOTMiniQuest> miniquests = getMiniQuests();
				if (!(miniquests = new ArrayList<>(miniquests)).isEmpty()) {
					if (viewCompleted) {
						miniquests = Lists.reverse(miniquests);
					} else {
						miniquests.sort(new GOTMiniQuest.SorterAlphabetical());
					}
					int size = miniquests.size();
					int min = Math.round(currentScroll * (size - MAX_DISPLAYED_MINI_QUESTS));
					int max = MAX_DISPLAYED_MINI_QUESTS - 1 + Math.round(currentScroll * (size - MAX_DISPLAYED_MINI_QUESTS));
					min = Math.max(min, 0);
					max = Math.min(max, size - 1);
					for (int index = min; index <= max; ++index) {
						GOTMiniQuest quest = miniquests.get(index);
						int displayIndex = index - min;
						int questX = guiLeft + X_SIZE / 2 + PAGE_BORDER;
						int questY = guiTop + pageTop + displayIndex * (4 + Q_PANEL_HEIGHT);
						renderMiniQuestPanel(quest, questX, questY, i, j);
						displayedMiniQuests.put(quest, Pair.of(questX, questY));
					}
				}
			} else {
				String deleteText = viewCompleted ? StatCollector.translateToLocal("got.gui.redBook.mq.deleteCmp") : StatCollector.translateToLocal("got.gui.redBook.mq.delete");
				List<String> deleteTextLines = fontRendererObj.listFormattedStringToWidth(deleteText, PAGE_WIDTH);
				int lineX = guiLeft + X_SIZE / 2 + PAGE_BORDER + PAGE_WIDTH / 2;
				int lineY = guiTop + 50;
				for (String obj : deleteTextLines) {
					drawCenteredString(obj, lineX, lineY, 8019267);
					lineY += fontRendererObj.FONT_HEIGHT;
				}
				int questX = guiLeft + X_SIZE / 2 + PAGE_BORDER + PAGE_WIDTH / 2 - Q_PANEL_WIDTH / 2;
				int questY = guiTop + pageTop + 80;
				renderMiniQuestPanel(deletingMiniquest, questX, questY, i, j);
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(GUI_TEXTURE_MINIQUESTS);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(guiLeft + SCROLL_BAR_X, guiTop + SCROLL_BAR_Y, 244, 0, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
			int scrollWidgetWidth = 10;
			int scrollBarBorder = 1;
			if (canScroll()) {
				int scroll = (int) (currentScroll * (SCROLL_BAR_HEIGHT - scrollBarBorder * 2 - SCROLL_WIDGET_HEIGHT));
				drawTexturedModalRect(guiLeft + SCROLL_BAR_X + scrollBarBorder, guiTop + SCROLL_BAR_Y + scrollBarBorder + scroll, 224, 0, scrollWidgetWidth, SCROLL_WIDGET_HEIGHT);
			} else {
				drawTexturedModalRect(guiLeft + SCROLL_BAR_X + scrollBarBorder, guiTop + SCROLL_BAR_Y + scrollBarBorder, 234, 0, scrollWidgetWidth, SCROLL_WIDGET_HEIGHT);
			}
		}
		boolean hasQuestViewButtons = page == Page.MINIQUESTS && selectedMiniquest == null;
		buttonViewActive.visible = hasQuestViewButtons;
		buttonViewActive.enabled = hasQuestViewButtons;
		buttonViewCompleted.enabled = buttonViewCompleted.visible = hasQuestViewButtons;
		boolean hasQuestDeleteButtons = page == Page.MINIQUESTS && deletingMiniquest != null;
		buttonQuestDelete.visible = hasQuestDeleteButtons;
		buttonQuestDelete.enabled = hasQuestDeleteButtons;
		buttonQuestDeleteCancel.enabled = buttonQuestDeleteCancel.visible = hasQuestDeleteButtons;
		if (viewCompleted) {
			buttonQuestDelete.displayString = StatCollector.translateToLocal("got.gui.redBook.mq.deleteCmpYes");
			buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("got.gui.redBook.mq.deleteCmpNo");
		} else {
			buttonQuestDelete.displayString = StatCollector.translateToLocal("got.gui.redBook.mq.deleteYes");
			buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("got.gui.redBook.mq.deleteNo");
		}
		super.drawScreen(i, j, f);
	}

	private List<GOTMiniQuest> getMiniQuests() {
		if (viewCompleted) {
			return getPlayerData().getMiniQuestsCompleted();
		}
		return getPlayerData().getMiniQuests();
	}

	private GOTPlayerData getPlayerData() {
		return GOTLevelData.getData(mc.thePlayer);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0 && (canScroll() || mouseInDiary)) {
			if (i > 0) {
				i = 1;
			}
			if (i < 0) {
				i = -1;
			}
			if (mouseInDiary) {
				diaryScroll += i;
			} else {
				int j = getMiniQuests().size() - MAX_DISPLAYED_MINI_QUESTS;
				currentScroll -= (float) i / j;
				currentScroll = Math.max(currentScroll, 0.0f);
				currentScroll = Math.min(currentScroll, 1.0f);
			}
		}
	}

	private boolean hasScrollBar() {
		return page == Page.MINIQUESTS && deletingMiniquest == null;
	}

	@Override
	public void initGui() {
		if (page == null) {
			page = Page.values()[0];
		}
		guiLeft = (width - X_SIZE) / 2;
		guiTop = (height - Y_SIZE) / 2;
		int buttonX = guiLeft + X_SIZE / 2 - PAGE_BORDER - PAGE_WIDTH / 2;
		int buttonY = guiTop + 80;
		buttonViewActive = new GOTGuiButton(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.translateToLocal("got.gui.redBook.mq.viewActive"));
		buttonList.add(buttonViewActive);
		buttonViewCompleted = new GOTGuiButton(3, buttonX + 10, buttonY, 60, 20, StatCollector.translateToLocal("got.gui.redBook.mq.viewComplete"));
		buttonList.add(buttonViewCompleted);
		buttonX = guiLeft + X_SIZE / 2 + PAGE_BORDER + PAGE_WIDTH / 2;
		buttonY = guiTop + Y_SIZE - 60;
		buttonQuestDelete = new GOTGuiButton(2, buttonX - 10 - 60, buttonY, 60, 20, "");
		buttonList.add(buttonQuestDelete);
		buttonQuestDeleteCancel = new GOTGuiButton(3, buttonX + 10, buttonY, 60, 20, "");
		buttonList.add(buttonQuestDeleteCancel);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (deletingMiniquest != null) {
				deletingMiniquest = null;
				return;
			}
			if (selectedMiniquest != null) {
				selectedMiniquest = null;
				return;
			}
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int mouse) {
		if (mouse == 0) {
			int i1;
			int i2;
			GOTMiniQuest quest;
			int j2;
			int j1;
			int questY;
			int questX;
			if (page == Page.MINIQUESTS && deletingMiniquest == null) {
				for (Map.Entry<GOTMiniQuest, Pair<Integer, Integer>> entry : displayedMiniQuests.entrySet()) {
					quest = entry.getKey();
					questX = entry.getValue().getLeft();
					questY = entry.getValue().getRight();
					i1 = questX + Q_DEL_X;
					j1 = questY + Q_DEL_Y;
					i2 = i1 + Q_WIDGET_SIZE;
					j2 = j1 + Q_WIDGET_SIZE;
					if (i >= i1 && j >= j1 && i < i2 && j < j2) {
						selectedMiniquest = deletingMiniquest = quest;
						diaryScroll = 0.0f;
						return;
					}
					if (!viewCompleted) {
						i1 = questX + Q_TRACK_X;
						j1 = questY + Q_TRACK_Y;
						i2 = i1 + Q_WIDGET_SIZE;
						j2 = j1 + Q_WIDGET_SIZE;
						if (i >= i1 && j >= j1 && i < i2 && j < j2) {
							trackOrUntrack(quest);
							return;
						}
					}
				}
			}
			if (page == Page.MINIQUESTS && deletingMiniquest == null) {
				for (Map.Entry<GOTMiniQuest, Pair<Integer, Integer>> entry : displayedMiniQuests.entrySet()) {
					quest = entry.getKey();
					questX = entry.getValue().getLeft();
					questY = entry.getValue().getRight();
					i1 = questX;
					j1 = questY;
					i2 = i1 + Q_PANEL_WIDTH;
					j2 = j1 + Q_PANEL_HEIGHT;
					if (i >= i1 && j >= j1 && i < i2 && j < j2) {
						selectedMiniquest = quest;
						diaryScroll = 0.0f;
						return;
					}
				}
				if (!mouseInDiary && !isScrolling) {
					selectedMiniquest = null;
					diaryScroll = 0.0f;
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	private void renderMiniQuestPanel(GOTMiniQuest quest, int questX, int questY, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		boolean mouseInPanel = mouseX >= questX && mouseX < questX + Q_PANEL_WIDTH && mouseY >= questY && mouseY < questY + Q_PANEL_HEIGHT;
		boolean mouseInDelete = mouseX >= questX + Q_DEL_X && mouseX < questX + Q_DEL_X + Q_WIDGET_SIZE && mouseY >= questY + Q_DEL_Y && mouseY < questY + Q_DEL_Y + Q_WIDGET_SIZE;
		boolean mouseInTrack = mouseX >= questX + Q_TRACK_X && mouseX < questX + Q_TRACK_X + Q_WIDGET_SIZE && mouseY >= questY + Q_TRACK_Y && mouseY < questY + Q_TRACK_Y + Q_WIDGET_SIZE;
		boolean isTracking = quest == getPlayerData().getTrackingMiniQuest();
		mc.getTextureManager().bindTexture(GUI_TEXTURE_MINIQUESTS);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (mouseInPanel || quest == selectedMiniquest) {
			drawTexturedModalRect(questX, questY, 0, Q_PANEL_HEIGHT, Q_PANEL_WIDTH, Q_PANEL_HEIGHT);
		} else {
			drawTexturedModalRect(questX, questY, 0, 0, Q_PANEL_WIDTH, Q_PANEL_HEIGHT);
		}
		float[] questRGB = quest.getQuestColorComponents();
		GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
		GL11.glEnable(3008);
		drawTexturedModalRect(questX, questY, 0, Q_PANEL_HEIGHT * 2, Q_PANEL_WIDTH, Q_PANEL_HEIGHT);
		GL11.glDisable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		String questName = quest.getEntityName();
		String factionName = quest.getFactionSubtitle();
		if (quest.isFailed()) {
			questName = EnumChatFormatting.STRIKETHROUGH + questName;
			factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
		}
		int qPanelBorder = 4;
		fontRendererObj.drawString(questName, questX + qPanelBorder, questY + qPanelBorder, 8019267);
		fontRendererObj.drawString(factionName, questX + qPanelBorder, questY + qPanelBorder + fontRendererObj.FONT_HEIGHT, 8019267);
		if (quest.isFailed()) {
			fontRendererObj.drawString(quest.getQuestFailureShorthand(), questX + qPanelBorder, questY + 25, 16711680);
		} else if (isTracking && trackTicks > 0) {
			fontRendererObj.drawString(StatCollector.translateToLocal("got.gui.redBook.mq.tracking"), questX + qPanelBorder, questY + 25, 8019267);
		} else {
			String objective = quest.getQuestObjective();
			int maxObjLength = Q_PANEL_WIDTH - qPanelBorder * 2 - 18;
			if (fontRendererObj.getStringWidth(objective) >= maxObjLength) {
				String ellipsis = "...";
				while (fontRendererObj.getStringWidth(objective + ellipsis) >= maxObjLength) {
					do {
						objective = objective.substring(0, objective.length() - 1);
					} while (Character.isWhitespace(objective.charAt(objective.length() - 1)));
				}
				objective = objective + ellipsis;
			}
			fontRendererObj.drawString(objective, questX + qPanelBorder, questY + 25, 8019267);
			String progress = quest.getQuestProgress();
			if (quest.isCompleted()) {
				progress = StatCollector.translateToLocal("got.gui.redBook.mq.complete");
			}
			fontRendererObj.drawString(progress, questX + qPanelBorder, questY + 25 + fontRendererObj.FONT_HEIGHT, 8019267);
		}
		if (deletingMiniquest == null) {
			mc.getTextureManager().bindTexture(GUI_TEXTURE_MINIQUESTS);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int delV = 0;
			if (mouseInDelete) {
				delV += Q_WIDGET_SIZE;
			}
			drawTexturedModalRect(questX + Q_DEL_X, questY + Q_DEL_Y, Q_PANEL_WIDTH, delV, Q_WIDGET_SIZE, Q_WIDGET_SIZE);
			if (!viewCompleted) {
				int trackU = Q_PANEL_WIDTH + Q_WIDGET_SIZE;
				int trackV = 0;
				if (mouseInTrack) {
					trackV += Q_WIDGET_SIZE;
				}
				if (isTracking) {
					trackU += Q_WIDGET_SIZE;
				}
				drawTexturedModalRect(questX + Q_TRACK_X, questY + Q_TRACK_Y, trackU, trackV, Q_WIDGET_SIZE, Q_WIDGET_SIZE);
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		ITEM_RENDERER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), quest.getQuestIcon(), questX + 149, questY + 24);
		GL11.glDisable(2896);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	private void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = i - guiLeft;
		int j1 = j - guiTop;
		mouseInDiary = selectedMiniquest != null && i1 >= DIARY_X && i1 < DIARY_X + DIARY_WIDTH && j1 >= DIARY_Y && j1 < DIARY_Y + DIARY_HEIGHT;
		boolean mouseInScrollBar = i1 >= SCROLL_BAR_X && i1 < SCROLL_BAR_X + SCROLL_BAR_WIDTH && j1 >= SCROLL_BAR_Y && j1 < SCROLL_BAR_Y + SCROLL_BAR_HEIGHT;
		if (!wasMouseDown && isMouseDown) {
			if (mouseInScrollBar) {
				isScrolling = canScroll();
			} else if (mouseInDiary) {
				isDiaryScrolling = true;
			}
		}
		if (!isMouseDown) {
			isScrolling = false;
			isDiaryScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - (guiTop + SCROLL_BAR_Y) - SCROLL_WIDGET_HEIGHT / 2.0f) / ((float) SCROLL_BAR_HEIGHT - SCROLL_WIDGET_HEIGHT);
			currentScroll = Math.max(currentScroll, 0.0f);
			currentScroll = Math.min(currentScroll, 1.0f);
		} else if (isDiaryScrolling) {
			float d = (float) (lastMouseY - j) / fontRendererObj.FONT_HEIGHT;
			diaryScroll -= d;
		}
		lastMouseY = j;
	}

	private void trackOrUntrack(GOTMiniQuest quest) {
		GOTMiniQuest tracking = getPlayerData().getTrackingMiniQuest();
		GOTMiniQuest newTracking = quest == tracking ? null : quest;
		IMessage packet = new GOTPacketMiniquestTrack(newTracking);
		GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		getPlayerData().setTrackingMiniQuest(newTracking);
		trackTicks = 40;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (trackTicks > 0) {
			--trackTicks;
		}
	}

	private enum Page {
		MINIQUESTS("miniquests");

		private final String name;

		Page(String s) {
			name = s;
		}

		private String getTitle() {
			return StatCollector.translateToLocal("got.gui.redBook.page." + name);
		}
	}
}