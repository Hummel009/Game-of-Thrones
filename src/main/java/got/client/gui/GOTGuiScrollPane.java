package got.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

public class GOTGuiScrollPane {
	private final int scrollWidgetWidth;
	private final int scrollWidgetHeight;

	private int barColor;
	private int widgetColor;
	private float currentScroll;
	private boolean isScrolling;
	private boolean wasMouseDown;

	private int scrollBarX0;
	private int paneX0;
	private int paneY0;
	private int paneY1;
	private boolean hasScrollBar;
	private boolean mouseOver;

	public GOTGuiScrollPane(int ww, int wh) {
		scrollWidgetWidth = ww;
		scrollWidgetHeight = wh;
		barColor = -1711276033;
		widgetColor = -1426063361;
	}

	public void drawScrollBar() {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x0 = scrollBarX0 + scrollWidgetWidth / 2;
		int y0 = paneY0;
		int y1 = paneY1;
		Gui.drawRect(x0, y0, x0 + 1, y1, barColor);
		int scroll = (int) (currentScroll * (y1 - y0 - scrollWidgetHeight));
		x0 = scrollBarX0;
		int x1 = x0 + scrollWidgetWidth;
		y1 = (y0 += scroll) + scrollWidgetHeight;
		Gui.drawRect(x0, y0, x1, y1, widgetColor);
	}

	public int[] getMinMaxIndices(Collection<?> list, int displayed) {
		int size = list.size();
		int min = Math.round(currentScroll * (size - displayed));
		int max = displayed - 1 + Math.round(currentScroll * (size - displayed));
		min = Math.max(min, 0);
		max = Math.min(max, size - 1);
		return new int[]{min, max};
	}

	public void mouseDragScroll(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		if (hasScrollBar) {
			int x0 = paneX0;
			int x1 = scrollBarX0 + scrollWidgetWidth;
			int y0 = paneY0;
			int y1 = paneY1;
			mouseOver = i >= x0 && j >= y0 && i < x1 && j < y1;
			x0 = scrollBarX0;
			boolean mouseOverScroll = i >= x0 && j >= y0 && i < x1 && j < y1;
			if (!wasMouseDown && isMouseDown && mouseOverScroll) {
				isScrolling = true;
			}
			if (!isMouseDown) {
				isScrolling = false;
			}
			if (isScrolling) {
				currentScroll = (j - y0 - scrollWidgetHeight / 2.0f) / ((float) (y1 - y0) - scrollWidgetHeight);
				currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
			}
		} else {
			resetScroll();
		}
		wasMouseDown = isMouseDown;
	}

	public void mouseWheelScroll(int delta, int size) {
		currentScroll -= (float) delta / size;
		currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
	}

	public void resetScroll() {
		currentScroll = 0.0f;
		isScrolling = false;
	}

	public GOTGuiScrollPane setColors(int c1, int c2) {
		int c11 = c1;
		int c21 = c2;
		int alphaMask = -16777216;
		if ((c11 & alphaMask) == 0) {
			c11 |= alphaMask;
		}
		if ((c21 & alphaMask) == 0) {
			c21 |= alphaMask;
		}
		barColor = c11;
		widgetColor = c21;
		return this;
	}

	public int getScrollBarX0() {
		return scrollBarX0;
	}

	public void setScrollBarX0(int scrollBarX0) {
		this.scrollBarX0 = scrollBarX0;
	}

	public int getPaneX0() {
		return paneX0;
	}

	public void setPaneX0(int paneX0) {
		this.paneX0 = paneX0;
	}

	public int getPaneY0() {
		return paneY0;
	}

	public void setPaneY0(int paneY0) {
		this.paneY0 = paneY0;
	}

	public int getPaneY1() {
		return paneY1;
	}

	public void setPaneY1(int paneY1) {
		this.paneY1 = paneY1;
	}

	public boolean isHasScrollBar() {
		return hasScrollBar;
	}

	public void setHasScrollBar(boolean hasScrollBar) {
		this.hasScrollBar = hasScrollBar;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
}
