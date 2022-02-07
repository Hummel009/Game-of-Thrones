package got.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class GOTGuiScrollPane {
	private int scrollWidgetWidth;
	private int scrollWidgetHeight;
	private int barColor;
	private int widgetColor;
	private int scrollBarX0;
	private int paneX0;
	private int paneY0;
	private int paneY1;
	private boolean hasScrollBar;
	private float currentScroll;
	private boolean isScrolling;
	private boolean mouseOver;
	private boolean wasMouseDown;

	public GOTGuiScrollPane(int ww, int wh) {
		scrollWidgetWidth = ww;
		scrollWidgetHeight = wh;
		barColor = -1711276033;
		widgetColor = -1426063361;
	}

	public void drawScrollBar() {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x0 = getScrollBarX0() + scrollWidgetWidth / 2;
		int y0 = getPaneY0();
		int y1 = getPaneY1();
		Gui.drawRect(x0, y0, x0 + 1, y1, barColor);
		int scroll = (int) (currentScroll * (y1 - y0 - scrollWidgetHeight));
		x0 = getScrollBarX0();
		int x1 = x0 + scrollWidgetWidth;
		y1 = (y0 += scroll) + scrollWidgetHeight;
		Gui.drawRect(x0, y0, x1, y1, widgetColor);
	}

	public int[] getMinMaxIndices(List list, int displayed) {
		int size = list.size();
		int min = 0 + Math.round(currentScroll * (size - displayed));
		int max = displayed - 1 + Math.round(currentScroll * (size - displayed));
		min = Math.max(min, 0);
		max = Math.min(max, size - 1);
		return new int[] { min, max };
	}

	public int getPaneX0() {
		return paneX0;
	}

	public int getPaneY0() {
		return paneY0;
	}

	public int getPaneY1() {
		return paneY1;
	}

	public int getScrollBarX0() {
		return scrollBarX0;
	}

	public boolean isHasScrollBar() {
		return hasScrollBar;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void mouseDragScroll(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		if (!isHasScrollBar()) {
			resetScroll();
		} else {
			int x0 = getPaneX0();
			int x1 = getScrollBarX0() + scrollWidgetWidth;
			int y0 = getPaneY0();
			int y1 = getPaneY1();
			setMouseOver(i >= x0 && j >= y0 && i < x1 && j < y1);
			x0 = getScrollBarX0();
			boolean mouseOverScroll = i >= x0 && j >= y0 && i < x1 && j < y1;
			if (!wasMouseDown && isMouseDown && mouseOverScroll) {
				isScrolling = true;
			}
			if (!isMouseDown) {
				isScrolling = false;
			}
			if (isScrolling) {
				currentScroll = (j - y0 - scrollWidgetHeight / 2.0f) / ((float) (y1 - y0) - (float) scrollWidgetHeight);
				currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
			}
		}
		wasMouseDown = isMouseDown;
	}

	public void mouseWheelScroll(int delta, int size) {
		currentScroll -= (float) delta / (float) size;
		currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
	}

	public void resetScroll() {
		currentScroll = 0.0f;
		isScrolling = false;
	}

	public GOTGuiScrollPane setColors(int c1, int c2) {
		int alphaMask = -16777216;
		if ((c1 & alphaMask) == 0) {
			c1 |= alphaMask;
		}
		if ((c2 & alphaMask) == 0) {
			c2 |= alphaMask;
		}
		barColor = c1;
		widgetColor = c2;
		return this;
	}

	public void setHasScrollBar(boolean hasScrollBar) {
		this.hasScrollBar = hasScrollBar;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public void setPaneX0(int paneX0) {
		this.paneX0 = paneX0;
	}

	public void setPaneY0(int paneY0) {
		this.paneY0 = paneY0;
	}

	public void setPaneY1(int paneY1) {
		this.paneY1 = paneY1;
	}

	public void setScrollBarX0(int scrollBarX0) {
		this.scrollBarX0 = scrollBarX0;
	}
}
