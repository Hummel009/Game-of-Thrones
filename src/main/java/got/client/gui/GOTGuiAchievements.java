package got.client.gui;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.math.IntMath;

import got.common.*;
import got.common.database.GOTAchievement;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.*;

public class GOTGuiAchievements extends GOTGuiMenuBase {
	private static ResourceLocation pageTexture = new ResourceLocation("got:textures/gui/achievements/page.png");
	private static ResourceLocation iconsTexture = new ResourceLocation("got:textures/gui/achievements/icons.png");
	private static GOTDimension currentDimension;
	private static GOTDimension prevDimension;
	private static GOTAchievement.Category currentCategory;
	private ArrayList currentCategoryTakenAchievements = new ArrayList();
	private ArrayList currentCategoryUntakenAchievements = new ArrayList();
	private int currentCategoryTakenCount;
	private int currentCategoryUntakenCount;
	private GOTGuiButtonAchievements buttonCategoryPrev;
	private GOTGuiButtonAchievements buttonCategoryNext;
	private float currentScroll = 0.0f;
	private boolean isScrolling = false;
	private boolean wasMouseDown;
	private int catScrollAreaX0;
	private int catScrollAreaX1;
	private int catScrollAreaY0;
	private int catScrollAreaY1;
	private boolean wasInCategoryScrollBar;
	private int prevMouseX;
	private int mouseX;
	private int mouseY;

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
		int min = 0 + Math.round(currentScroll * (size - 4));
		int max = 3 + Math.round(currentScroll * (size - 4));
		if (max > size - 1) {
			max = size - 1;
		}
		for (int i = min; i <= max; ++i) {
			GOTAchievement achievement;
			boolean hasAchievement;
			if (i < currentCategoryTakenCount) {
				achievement = (GOTAchievement) currentCategoryTakenAchievements.get(i);
				hasAchievement = true;
			} else {
				achievement = (GOTAchievement) currentCategoryUntakenAchievements.get(i - currentCategoryTakenCount);
				hasAchievement = false;
			}
			int offset = 47 + 50 * (i - min);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			mc.getTextureManager().bindTexture(getIconsTexture());
			this.drawTexturedModalRect(getGuiLeft() + 9, getGuiTop() + offset, 0, hasAchievement ? 0 : 50, 190, 50);
			int iconLeft = getGuiLeft() + 12;
			int iconTop = getGuiTop() + offset + 3;
			GL11.glEnable(2896);
			GL11.glEnable(2884);
			getRenderItem().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), achievement.getIcon(), iconLeft, iconTop);
			GL11.glDisable(2896);
			if (!hasAchievement) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, 300.0f);
				Gui.drawRect(iconLeft, iconTop, iconLeft + 16, iconTop + 16, -2013265920);
				GL11.glPopMatrix();
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int textColour = hasAchievement ? 8019267 : 5652783;
			mc.fontRenderer.drawString(achievement.getTitle(mc.thePlayer), getGuiLeft() + 33, getGuiTop() + offset + 5, textColour);
			mc.fontRenderer.drawSplitString(achievement.getDescription(mc.thePlayer), getGuiLeft() + 12, getGuiTop() + offset + 24, 184, textColour);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			if (!hasAchievement) {
				continue;
			}
			mc.getTextureManager().bindTexture(getIconsTexture());
			this.drawTexturedModalRect(getGuiLeft() + 179, getGuiTop() + offset + 2, 190, 17, 16, 16);
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
		int scrollBarX0 = getGuiLeft() + 201;
		int scrollBarX1 = scrollBarX0 + 12;
		int scrollBarY0 = getGuiTop() + 48;
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
		this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, getxSize(), getySize());
		String categoryName = currentCategory.getDisplayName();
		categoryName = StatCollector.translateToLocalFormatted("got.gui.achievements.category", categoryName, currentCategoryTakenCount, currentCategoryTakenCount + currentCategoryUntakenCount);
		this.drawCenteredString(categoryName, getGuiLeft() + getxSize() / 2, getGuiTop() + 28, 8019267);
		buttonCategoryPrev.setButtonCategory(getCategoryAtRelativeIndex(-1));
		buttonCategoryNext.setButtonCategory(getCategoryAtRelativeIndex(1));
		super.drawScreen(i, j, f);
		int catScrollCentre = getGuiLeft() + getxSize() / 2;
		int catScrollX = catScrollCentre - 76;
		int catScrollY = getGuiTop() + 13;
		mc.getTextureManager().bindTexture(getIconsTexture());
		this.drawTexturedModalRect(catScrollX, catScrollY, 0, 100, 152, 10);
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
			mc.getTextureManager().bindTexture(getIconsTexture());
			GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
			this.drawTexturedModalRect(catX0, catY0, catX0 - catScrollAreaX0 + 0, 100, catX1 - catX0, catY1 - catY0);
		}
		mc.getTextureManager().bindTexture(getIconsTexture());
		this.drawTexturedModalRect(catScrollX, catScrollY, 0, 110, 152, 10);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(getIconsTexture());
		if (hasScrollBar()) {
			int offset = (int) (currentScroll * 181.0f);
			this.drawTexturedModalRect(scrollBarX0, scrollBarY0 + offset, 190, 0, 10, 17);
		} else {
			this.drawTexturedModalRect(scrollBarX0, scrollBarY0, 200, 0, 10, 17);
		}
		drawAchievements(i, j);
	}

	private GOTAchievement.Category getCategoryAtRelativeIndex(int i) {
		List<GOTAchievement.Category> categories = GOTGuiAchievements.currentDimension.getAchievementCategories();
		int index = categories.indexOf(currentCategory);
		index += i;
		index = IntMath.mod(index, GOTGuiAchievements.currentDimension.getAchievementCategories().size());
		return GOTGuiAchievements.currentDimension.getAchievementCategories().get(index);
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
			currentScroll = (float) (currentScroll - (double) i / (double) j);
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	private boolean hasScrollBar() {
		return currentCategoryTakenCount + currentCategoryUntakenCount > 4;
	}

	@Override
	public void initGui() {
		setxSize(220);
		super.initGui();
		buttonCategoryPrev = new GOTGuiButtonAchievements(0, true, getGuiLeft() + 14, getGuiTop() + 13);
		buttonList.add(buttonCategoryPrev);
		buttonCategoryNext = new GOTGuiButtonAchievements(1, false, getGuiLeft() + 191, getGuiTop() + 13);
		buttonList.add(buttonCategoryNext);
		updateAchievementLists();
	}

	private boolean isMouseInCategoryScrollBar() {
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
			currentCategory = GOTGuiAchievements.currentDimension.getAchievementCategories().get(0);
		}
		prevDimension = currentDimension;
		currentCategoryTakenAchievements.clear();
		currentCategoryUntakenAchievements.clear();
		for (GOTAchievement achievement : GOTGuiAchievements.currentCategory.getList()) {
			if (!achievement.canPlayerEarn(mc.thePlayer)) {
				continue;
			}
			if (GOTLevelData.getData(mc.thePlayer).hasAchievement(achievement)) {
				currentCategoryTakenAchievements.add(achievement);
				continue;
			}
			currentCategoryUntakenAchievements.add(achievement);
		}
		currentCategoryTakenCount = currentCategoryTakenAchievements.size();
		currentCategoryUntakenCount = currentCategoryUntakenAchievements.size();
		GOTLevelData.getData(mc.thePlayer).getEarnedAchievements(currentDimension).size();
		for (GOTAchievement achievement : GOTGuiAchievements.currentDimension.getAllAchievements()) {
			if (!achievement.canPlayerEarn(mc.thePlayer)) {
				continue;
			}
		}
		Comparator<GOTAchievement> sorter = GOTAchievement.sortForDisplay(mc.thePlayer);
		Collections.sort(currentCategoryTakenAchievements, sorter);
		Collections.sort(currentCategoryUntakenAchievements, sorter);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateAchievementLists();
		prevMouseX = mouseX;
		wasInCategoryScrollBar = isMouseInCategoryScrollBar();
	}

	public static ResourceLocation getIconsTexture() {
		return iconsTexture;
	}

	public static void setIconsTexture(ResourceLocation iconsTexture) {
		GOTGuiAchievements.iconsTexture = iconsTexture;
	}
}
