package got.client.gui;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import got.client.GOTTextBody;
import got.common.*;
import got.common.database.GOTSpeech;
import got.common.faction.GOTAlignmentValues;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class GOTGuiQuestBook extends GOTGuiScreenBase {
	private static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/quest/questBook.png");
	private static ResourceLocation guiTexture_miniquests = new ResourceLocation("got:textures/gui/quest/questBook_miniquests.png");
	private static RenderItem renderItem = new RenderItem();
	private static boolean viewCompleted = false;
	private static Page page;
	private int xSize = 420;
	private int ySize = 256;
	private int guiLeft;
	private int guiTop;
	private int pageWidth = 186;
	private int pageTop = 18;
	private int pageBorder = 10;
	private boolean wasMouseDown;
	private int lastMouseY;
	private int scrollBarWidth = 12;
	private int scrollBarHeight = 216;
	private int scrollBarX = xSize / 2 + pageWidth;
	private int scrollBarY = 18;
	private int scrollBarBorder = 1;
	private int scrollWidgetWidth = 10;
	private int scrollWidgetHeight = 17;
	private boolean isScrolling = false;
	private float currentScroll = 0.0f;
	private Map<GOTMiniQuest, Pair<Integer, Integer>> displayedMiniQuests = new HashMap<>();
	private int maxDisplayedMiniQuests = 4;
	private int qPanelWidth = 170;
	private int qPanelHeight = 45;
	private int qPanelBorder = 4;
	private int qDelX = 158;
	private int qDelY = 4;
	private int qTrackX = 148;
	private int qTrackY = 4;
	private int qWidgetSize = 8;
	private int diaryWidth = 170;
	private int diaryHeight = 218;
	private int diaryX = xSize / 2 - pageBorder - pageWidth / 2 - diaryWidth / 2;
	private int diaryY = ySize / 2 - diaryHeight / 2 - 1;
	private int diaryBorder = 6;
	private boolean mouseInDiary = false;
	private boolean isDiaryScrolling = false;
	private float diaryScroll;
	private GOTMiniQuest selectedMiniquest;
	private GOTMiniQuest deletingMiniquest;
	private int trackTicks;
	private GuiButton buttonViewActive;
	private GuiButton buttonViewCompleted;
	private GuiButton buttonQuestDelete;
	private GuiButton buttonQuestDeleteCancel;

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
				GOTPacketDeleteMiniquest packet = new GOTPacketDeleteMiniquest(deletingMiniquest);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
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
		return hasScrollBar() && getMiniQuests().size() > maxDisplayedMiniQuests;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		displayedMiniQuests.clear();
		setupScrollBar(i, j);
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(getGuiTexture());
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize, 512);
		int x = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
		int y = guiTop + 30;
		if (page == Page.MINIQUESTS && selectedMiniquest == null) {
			float scale = 2.0f;
			float invScale = 1.0f / scale;
			x = (int) (x * invScale);
			y = (int) (y * invScale);
			GL11.glScalef(scale, scale, scale);
			this.drawCenteredString(page.getTitle(), x, y, 8019267);
			GL11.glScalef(invScale, invScale, invScale);
			x = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
			y = guiTop + 50;
			if (viewCompleted) {
				this.drawCenteredString(StatCollector.translateToLocal("got.gui.redBook.mq.viewComplete"), x, y, 8019267);
			} else {
				this.drawCenteredString(StatCollector.translateToLocal("got.gui.redBook.mq.viewActive"), x, y, 8019267);
			}
		}
		if (page == Page.MINIQUESTS) {
			if (selectedMiniquest == null) {
				this.drawCenteredString(GOTDate.AegonCalendar.getDate().getDateName(false), guiLeft + xSize / 2 - pageBorder - pageWidth / 2, guiTop + ySize - 30, 8019267);
				this.drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.redBook.mq.numActive", getPlayerData().getActiveMiniQuests().size()), x, guiTop + 120, 8019267);
				this.drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.redBook.mq.numComplete", getPlayerData().getCompletedMiniQuestsTotal()), x, guiTop + 140, 8019267);
			} else {
				GOTMiniQuest quest = selectedMiniquest;
				mc.getTextureManager().bindTexture(getGuiTexture());
				float[] questRGB = quest.getQuestColorComponents();
				GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
				x = guiLeft + diaryX;
				y = guiTop + diaryY;
				this.drawTexturedModalRect(x, y, 0, 256, diaryWidth, diaryHeight, 512);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int textW = diaryWidth - diaryBorder * 2;
				int textBottom = y + diaryHeight - diaryBorder;
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
						String line = pageText.getText(l);
						line = EnumChatFormatting.STRIKETHROUGH + line;
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
						if (quest.getAlignmentRewarded() != 0.0f) {
							String alignS = GOTAlignmentValues.formatAlignForDisplay(quest.getAlignmentRewarded());
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
			if (deletingMiniquest == null) {
				List miniquests = getMiniQuests();
				if (!(miniquests = new ArrayList<GOTMiniQuest>(miniquests)).isEmpty()) {
					if (viewCompleted) {
						miniquests = Lists.reverse(miniquests);
					} else {
						Collections.sort(miniquests, new GOTMiniQuest.SorterAlphabetical());
					}
					int size = miniquests.size();
					int min = 0 + Math.round(currentScroll * (size - maxDisplayedMiniQuests));
					int max = maxDisplayedMiniQuests - 1 + Math.round(currentScroll * (size - maxDisplayedMiniQuests));
					min = Math.max(min, 0);
					max = Math.min(max, size - 1);
					for (int index = min; index <= max; ++index) {
						GOTMiniQuest quest = (GOTMiniQuest) miniquests.get(index);
						int displayIndex = index - min;
						int questX = guiLeft + xSize / 2 + pageBorder;
						int questY = guiTop + pageTop + displayIndex * (4 + qPanelHeight);
						renderMiniQuestPanel(quest, questX, questY, i, j);
						displayedMiniQuests.put(quest, Pair.of(questX, questY));
					}
				}
			} else {
				String deleteText = viewCompleted ? StatCollector.translateToLocal("got.gui.redBook.mq.deleteCmp") : StatCollector.translateToLocal("got.gui.redBook.mq.delete");
				List deleteTextLines = fontRendererObj.listFormattedStringToWidth(deleteText, pageWidth);
				int lineX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2;
				int lineY = guiTop + 50;
				for (Object obj : deleteTextLines) {
					String line = (String) obj;
					this.drawCenteredString(line, lineX, lineY, 8019267);
					lineY += fontRendererObj.FONT_HEIGHT;
				}
				int questX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2 - qPanelWidth / 2;
				int questY = guiTop + pageTop + 80;
				renderMiniQuestPanel(deletingMiniquest, questX, questY, i, j);
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(guiTexture_miniquests);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 244, 0, scrollBarWidth, scrollBarHeight);
			if (canScroll()) {
				int scroll = (int) (currentScroll * (scrollBarHeight - scrollBarBorder * 2 - scrollWidgetHeight));
				this.drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder + scroll, 224, 0, scrollWidgetWidth, scrollWidgetHeight);
			} else {
				this.drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder, 234, 0, scrollWidgetWidth, scrollWidgetHeight);
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
				int j = getMiniQuests().size() - maxDisplayedMiniQuests;
				currentScroll -= (float) i / (float) j;
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
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int buttonX = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
		int buttonY = guiTop + 80;
		buttonViewActive = new GOTGuiButton(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.translateToLocal("got.gui.redBook.mq.viewActive"));
		buttonList.add(buttonViewActive);
		buttonViewCompleted = new GOTGuiButton(3, buttonX + 10, buttonY, 60, 20, StatCollector.translateToLocal("got.gui.redBook.mq.viewComplete"));
		buttonList.add(buttonViewCompleted);
		buttonX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2;
		buttonY = guiTop + ySize - 60;
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
					i1 = questX + qDelX;
					j1 = questY + qDelY;
					i2 = i1 + qWidgetSize;
					j2 = j1 + qWidgetSize;
					if (i >= i1 && j >= j1 && i < i2 && j < j2) {
						selectedMiniquest = deletingMiniquest = quest;
						diaryScroll = 0.0f;
						return;
					}
					if (viewCompleted) {
						continue;
					}
					i1 = questX + qTrackX;
					j1 = questY + qTrackY;
					i2 = i1 + qWidgetSize;
					j2 = j1 + qWidgetSize;
					if (i < i1 || j < j1 || i >= i2 || j >= j2) {
						continue;
					}
					trackOrUntrack(quest);
					return;
				}
			}
			if (page == Page.MINIQUESTS && deletingMiniquest == null) {
				for (Map.Entry<GOTMiniQuest, Pair<Integer, Integer>> entry : displayedMiniQuests.entrySet()) {
					quest = entry.getKey();
					questX = entry.getValue().getLeft();
					questY = entry.getValue().getRight();
					i1 = questX;
					j1 = questY;
					i2 = i1 + qPanelWidth;
					j2 = j1 + qPanelHeight;
					if (i < i1 || j < j1 || i >= i2 || j >= j2) {
						continue;
					}
					selectedMiniquest = quest;
					diaryScroll = 0.0f;
					return;
				}
				if (!mouseInDiary && !isScrolling) {
					selectedMiniquest = null;
					diaryScroll = 0.0f;
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	public void renderMiniQuestPanel(GOTMiniQuest quest, int questX, int questY, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		boolean mouseInPanel = mouseX >= questX && mouseX < questX + qPanelWidth && mouseY >= questY && mouseY < questY + qPanelHeight;
		boolean mouseInDelete = mouseX >= questX + qDelX && mouseX < questX + qDelX + qWidgetSize && mouseY >= questY + qDelY && mouseY < questY + qDelY + qWidgetSize;
		boolean mouseInTrack = mouseX >= questX + qTrackX && mouseX < questX + qTrackX + qWidgetSize && mouseY >= questY + qTrackY && mouseY < questY + qTrackY + qWidgetSize;
		boolean isTracking = quest == getPlayerData().getTrackingMiniQuest();
		mc.getTextureManager().bindTexture(guiTexture_miniquests);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (mouseInPanel || quest == selectedMiniquest) {
			this.drawTexturedModalRect(questX, questY, 0, qPanelHeight, qPanelWidth, qPanelHeight);
		} else {
			this.drawTexturedModalRect(questX, questY, 0, 0, qPanelWidth, qPanelHeight);
		}
		float[] questRGB = quest.getQuestColorComponents();
		GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
		GL11.glEnable(3008);
		this.drawTexturedModalRect(questX, questY, 0, qPanelHeight * 2, qPanelWidth, qPanelHeight);
		GL11.glDisable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		String questName = quest.getEntityName();
		String factionName = quest.getFactionSubtitle();
		if (quest.isFailed()) {
			questName = EnumChatFormatting.STRIKETHROUGH + questName;
			factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
		}
		fontRendererObj.drawString(questName, questX + qPanelBorder, questY + qPanelBorder, 8019267);
		fontRendererObj.drawString(factionName, questX + qPanelBorder, questY + qPanelBorder + fontRendererObj.FONT_HEIGHT, 8019267);
		if (quest.isFailed()) {
			fontRendererObj.drawString(quest.getQuestFailureShorthand(), questX + qPanelBorder, questY + 25, 16711680);
		} else if (isTracking && trackTicks > 0) {
			fontRendererObj.drawString(StatCollector.translateToLocal("got.gui.redBook.mq.tracking"), questX + qPanelBorder, questY + 25, 8019267);
		} else {
			String objective = quest.getQuestObjective();
			int maxObjLength = qPanelWidth - qPanelBorder * 2 - 18;
			if (fontRendererObj.getStringWidth(objective) >= maxObjLength) {
				String ellipsis = "...";
				while (fontRendererObj.getStringWidth(objective + ellipsis) >= maxObjLength) {
					objective = objective.substring(0, objective.length() - 1);
					while (Character.isWhitespace(objective.charAt(objective.length() - 1))) {
						objective = objective.substring(0, objective.length() - 1);
					}
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
			mc.getTextureManager().bindTexture(guiTexture_miniquests);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int delU = qPanelWidth;
			int delV = 0;
			if (mouseInDelete) {
				delV += qWidgetSize;
			}
			this.drawTexturedModalRect(questX + qDelX, questY + qDelY, delU, delV, qWidgetSize, qWidgetSize);
			if (!viewCompleted) {
				int trackU = qPanelWidth + qWidgetSize;
				int trackV = 0;
				if (mouseInTrack) {
					trackV += qWidgetSize;
				}
				if (isTracking) {
					trackU += qWidgetSize;
				}
				this.drawTexturedModalRect(questX + qTrackX, questY + qTrackY, trackU, trackV, qWidgetSize, qWidgetSize);
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), quest.getQuestIcon(), questX + 149, questY + 24);
		GL11.glDisable(2896);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = i - guiLeft;
		int j1 = j - guiTop;
		mouseInDiary = selectedMiniquest != null ? i1 >= diaryX && i1 < diaryX + diaryWidth && j1 >= diaryY && j1 < diaryY + diaryHeight : false;
		boolean mouseInScrollBar = i1 >= scrollBarX && i1 < scrollBarX + scrollBarWidth && j1 >= scrollBarY && j1 < scrollBarY + scrollBarHeight;
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
			currentScroll = (j - (guiTop + scrollBarY) - scrollWidgetHeight / 2.0f) / ((float) scrollBarHeight - (float) scrollWidgetHeight);
			currentScroll = Math.max(currentScroll, 0.0f);
			currentScroll = Math.min(currentScroll, 1.0f);
		} else if (isDiaryScrolling) {
			float d = (float) (lastMouseY - j) / (float) fontRendererObj.FONT_HEIGHT;
			diaryScroll -= d;
		}
		lastMouseY = j;
	}

	public void trackOrUntrack(GOTMiniQuest quest) {
		GOTMiniQuest tracking = getPlayerData().getTrackingMiniQuest();
		GOTMiniQuest newTracking;
		newTracking = quest == tracking ? null : quest;
		GOTPacketMiniquestTrack packet = new GOTPacketMiniquestTrack(newTracking);
		GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
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

	public static ResourceLocation getGuiTexture() {
		return guiTexture;
	}

	public static void setGuiTexture(ResourceLocation guiTexture) {
		GOTGuiQuestBook.guiTexture = guiTexture;
	}

	public enum Page {
		MINIQUESTS("miniquests");

		private String name;

		Page(String s) {
			name = s;
		}

		private String getTitle() {
			return StatCollector.translateToLocal("got.gui.redBook.page." + name);
		}
	}

}
