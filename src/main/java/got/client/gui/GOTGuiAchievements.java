package got.client.gui;

import com.google.common.math.IntMath;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GOTGuiAchievements extends GOTGuiMenuBase {
	public static ResourceLocation pageTexture = new ResourceLocation("got:textures/gui/achievements/page.png");
	public static ResourceLocation iconsTexture = new ResourceLocation("got:textures/gui/achievements/icons.png");
	public static GOTDimension currentDimension;
	public static GOTDimension prevDimension;
	public static GOTAchievement.Category currentCategory;
	public ArrayList<GOTAchievement> currentCategoryTakenAchievements = new ArrayList<>();
	public ArrayList<GOTAchievement> currentCategoryUntakenAchievements = new ArrayList<>();
	public int currentCategoryTakenCount;
	public int currentCategoryUntakenCount;
	public GOTGuiButtonAchievements buttonCategoryPrev;
	public GOTGuiButtonAchievements buttonCategoryNext;
	public int totalTakenCount;
	public int totalAvailableCount;
	public float currentScroll;
	public boolean isScrolling;
	public boolean wasMouseDown;
	public int catScrollAreaX0;
	public int catScrollAreaX1;
	public int catScrollAreaY0;
	public int catScrollAreaY1;
	public boolean wasInCategoryScrollBar;
	public int prevMouseX;
	public int mouseX;
	public int mouseY;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonCategoryPrev) {
				prevCategory();
			} else if (button == buttonCategoryNext) {
				nextCategory();
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public void drawAchievements(int mouseX, int mouseY) {
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2903);
		int size = currentCategoryTakenCount + currentCategoryUntakenCount;
		int min = Math.round(currentScroll * (size - 4));
		int max = 3 + Math.round(currentScroll * (size - 4));
		if (max > size - 1) {
			max = size - 1;
		}
		for (int i = min; i <= max; ++i) {
			GOTAchievement achievement;
			boolean hasAchievement;
			if (i < currentCategoryTakenCount) {
				achievement = currentCategoryTakenAchievements.get(i);
				hasAchievement = true;
			} else {
				achievement = currentCategoryUntakenAchievements.get(i - currentCategoryTakenCount);
				hasAchievement = false;
			}
			int offset = 47 + 50 * (i - min);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			mc.getTextureManager().bindTexture(iconsTexture);
			drawTexturedModalRect(guiLeft + 9, guiTop + offset, 0, hasAchievement ? 0 : 50, 190, 50);
			int iconLeft = guiLeft + 12;
			int iconTop = guiTop + offset + 3;
			GL11.glEnable(2896);
			GL11.glEnable(2884);
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), achievement.icon, iconLeft, iconTop);
			GL11.glDisable(2896);
			if (!hasAchievement) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, 300.0f);
				Gui.drawRect(iconLeft, iconTop, iconLeft + 16, iconTop + 16, -2013265920);
				GL11.glPopMatrix();
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int textColour = hasAchievement ? 8019267 : 5652783;
			mc.fontRenderer.drawString(achievement.getTitle(mc.thePlayer), guiLeft + 33, guiTop + offset + 5, textColour);
			mc.fontRenderer.drawSplitString(achievement.getDescription(mc.thePlayer), guiLeft + 12, guiTop + offset + 24, 184, textColour);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			if (hasAchievement) {
				mc.getTextureManager().bindTexture(iconsTexture);
				drawTexturedModalRect(guiLeft + 179, guiTop + offset + 2, 190, 17, 16, 16);
			}
		}
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		mouseX = i;
		mouseY = j;
		if (wasInCategoryScrollBar) {
			int diff = mouseX - prevMouseX;
			boolean changed = false;
			if (diff >= 4) {
				prevCategory();
				changed = true;
			} else if (diff <= -4) {
				nextCategory();
				changed = true;
			}
			if (changed) {
				mouseX = prevMouseX;
				wasInCategoryScrollBar = false;
			}
		}
		boolean isMouseDown = Mouse.isButtonDown(0);
		int scrollBarX0 = guiLeft + 201;
		int scrollBarX1 = scrollBarX0 + 12;
		int scrollBarY0 = guiTop + 48;
		int scrollBarY1 = scrollBarY0 + 200;
		if (!wasMouseDown && isMouseDown && i >= scrollBarX0 && i < scrollBarX1 && j >= scrollBarY0 && j < scrollBarY1) {
			isScrolling = hasScrollBar();
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - scrollBarY0 - 8.5f) / (scrollBarY1 - scrollBarY0 - 17.0f);
			currentScroll = Math.max(currentScroll, 0.0f);
			currentScroll = Math.min(currentScroll, 1.0f);
		}
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(pageTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String categoryName = currentCategory.getDisplayName();
		categoryName = StatCollector.translateToLocalFormatted("got.gui.achievements.category", categoryName, currentCategoryTakenCount, currentCategoryTakenCount + currentCategoryUntakenCount);
		drawCenteredString(categoryName, guiLeft + xSize / 2, guiTop + 28, 8019267);
		buttonCategoryPrev.buttonCategory = getCategoryAtRelativeIndex(-1);
		buttonCategoryNext.buttonCategory = getCategoryAtRelativeIndex(1);
		super.drawScreen(i, j, f);
		int catScrollCentre = guiLeft + xSize / 2;
		int catScrollY = guiTop + 13;
		mc.getTextureManager().bindTexture(iconsTexture);
		int catScrollX = catScrollCentre - 76;
		drawTexturedModalRect(catScrollX, catScrollY, 0, 100, 152, 10);
		catScrollAreaX0 = catScrollX;
		catScrollAreaX1 = catScrollX + 152;
		catScrollAreaY0 = catScrollY;
		catScrollAreaY1 = catScrollY + 10;
		int catWidth = 16;
		int catCentreWidth = 50;
		int catsEitherSide = (catScrollAreaX1 - catScrollAreaX0) / catWidth + 1;
		for (int l = -catsEitherSide; l <= catsEitherSide; ++l) {
			int thisCatWidth = l == 0 ? catCentreWidth : catWidth;
			int catX = catScrollCentre;
			if (l != 0) {
				int signum = Integer.signum(l);
				catX += (catCentreWidth + catWidth) / 2 * signum;
				catX += (Math.abs(l) - 1) * signum * catWidth;
			}
			int catX0 = catX - thisCatWidth / 2;
			int catX1 = catX + thisCatWidth;
			if (catX0 < catScrollAreaX0) {
				catX0 = catScrollAreaX0;
			}
			if (catX1 > catScrollAreaX1) {
				catX1 = catScrollAreaX1;
			}
			int catY0 = catScrollAreaY0;
			int catY1 = catScrollAreaY1;
			GOTAchievement.Category thisCategory = getCategoryAtRelativeIndex(l);
			float[] catColors = thisCategory.getCategoryRGB();
			mc.getTextureManager().bindTexture(iconsTexture);
			GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
			drawTexturedModalRect(catX0, catY0, catX0 - catScrollAreaX0, 100, catX1 - catX0, catY1 - catY0);
		}
		mc.getTextureManager().bindTexture(iconsTexture);
		drawTexturedModalRect(catScrollX, catScrollY, 0, 110, 152, 10);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(iconsTexture);
		if (hasScrollBar()) {
			int offset = (int) (currentScroll * 181.0f);
			drawTexturedModalRect(scrollBarX0, scrollBarY0 + offset, 190, 0, 10, 17);
		} else {
			drawTexturedModalRect(scrollBarX0, scrollBarY0, 200, 0, 10, 17);
		}
		drawAchievements(i, j);
	}

	public GOTAchievement.Category getCategoryAtRelativeIndex(int i) {
		List<GOTAchievement.Category> categories = currentDimension.achievementCategories;
		int index = categories.indexOf(currentCategory);
		index += i;
		index = IntMath.mod(index, currentDimension.achievementCategories.size());
		return currentDimension.achievementCategories.get(index);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0 && hasScrollBar()) {
			int j = currentCategoryTakenCount + currentCategoryUntakenCount - 4;
			if (i > 0) {
				i = 1;
			}
			if (i < 0) {
				i = -1;
			}
			currentScroll -= (double) i / j;
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	public boolean hasScrollBar() {
		return currentCategoryTakenCount + currentCategoryUntakenCount > 4;
	}

	@Override
	public void initGui() {
		xSize = 220;
		super.initGui();
		buttonCategoryPrev = new GOTGuiButtonAchievements(0, true, guiLeft + 14, guiTop + 13);
		buttonList.add(buttonCategoryPrev);
		buttonCategoryNext = new GOTGuiButtonAchievements(1, false, guiLeft + 191, guiTop + 13);
		buttonList.add(buttonCategoryNext);
		updateAchievementLists();
	}

	public boolean isMouseInCategoryScrollBar() {
		return Mouse.isButtonDown(0) && mouseX >= catScrollAreaX0 && mouseX < catScrollAreaX1 && mouseY >= catScrollAreaY0 && mouseY < catScrollAreaY1;
	}

	public void nextCategory() {
		currentCategory = getCategoryAtRelativeIndex(1);
		currentScroll = 0.0f;
	}

	public void prevCategory() {
		currentCategory = getCategoryAtRelativeIndex(-1);
		currentScroll = 0.0f;
	}

	public void updateAchievementLists() {
		currentDimension = GOTDimension.getCurrentDimension(mc.theWorld);
		if (currentDimension != prevDimension) {
			currentCategory = currentDimension.achievementCategories.get(0);
		}
		prevDimension = currentDimension;
		currentCategoryTakenAchievements.clear();
		currentCategoryUntakenAchievements.clear();
		for (GOTAchievement achievement : currentCategory.list) {
			if (achievement.canPlayerEarn(mc.thePlayer)) {
				if (GOTLevelData.getData(mc.thePlayer).hasAchievement(achievement)) {
					currentCategoryTakenAchievements.add(achievement);
				} else {
					currentCategoryUntakenAchievements.add(achievement);
				}
			}
		}
		currentCategoryTakenCount = currentCategoryTakenAchievements.size();
		currentCategoryUntakenCount = currentCategoryUntakenAchievements.size();
		totalTakenCount = GOTLevelData.getData(mc.thePlayer).getEarnedAchievements(currentDimension).size();
		totalAvailableCount = 0;
		for (GOTAchievement achievement : currentDimension.allAchievements) {
			if (achievement.canPlayerEarn(mc.thePlayer)) {
				++totalAvailableCount;
			}
		}
		Comparator<GOTAchievement> sorter = GOTAchievement.sortForDisplay(mc.thePlayer);
		currentCategoryTakenAchievements.sort(sorter);
		currentCategoryUntakenAchievements.sort(sorter);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateAchievementLists();
		prevMouseX = mouseX;
		wasInCategoryScrollBar = isMouseInCategoryScrollBar();
	}
}
