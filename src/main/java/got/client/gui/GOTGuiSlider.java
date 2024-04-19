package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GOTGuiSlider extends GuiButton {
	private final String baseDisplayString;

	private String overrideStateString;

	private boolean valueOnly;
	private boolean dragging;

	private float sliderValue = 1.0f;

	private int numberDigits;
	private int minValue;
	private int maxValue;

	public GOTGuiSlider(int id, int x, int y, int width, int height, String s) {
		super(id, x, y, width, height, s);
		baseDisplayString = s;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (overrideStateString != null) {
			displayString = overrideStateString;
		} else {
			int value = getSliderValue();
			displayString = String.valueOf(value);
			if (numberDigits > 0) {
				displayString = String.format("%0" + numberDigits + 'd', value);
			}
		}
		if (!valueOnly) {
			displayString = baseDisplayString + ": " + displayString;
		}
		super.drawButton(mc, i, j);
	}

	@Override
	public int getHoverState(boolean flag) {
		return 0;
	}

	public int getSliderValue() {
		return minValue + Math.round(sliderValue * (maxValue - minValue));
	}

	public void setSliderValue(int value) {
		int value1 = MathHelper.clamp_int(value, minValue, maxValue);
		sliderValue = (float) (value1 - minValue) / (maxValue - minValue);
	}

	@Override
	public void mouseDragged(Minecraft mc, int i, int j) {
		if (visible && enabled) {
			if (dragging) {
				sliderValue = (float) (i - (xPosition + 4)) / (width - 8);
				if (sliderValue < 0.0f) {
					sliderValue = 0.0f;
				}
				if (sliderValue > 1.0f) {
					sliderValue = 1.0f;
				}
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int i, int j) {
		if (super.mousePressed(mc, i, j)) {
			sliderValue = (float) (i - (xPosition + 4)) / (width - 8);
			if (sliderValue < 0.0f) {
				sliderValue = 0.0f;
			}
			if (sliderValue > 1.0f) {
				sliderValue = 1.0f;
			}
			dragging = true;
			return true;
		}
		return false;
	}

	@Override
	public void mouseReleased(int i, int j) {
		dragging = false;
	}

	public void setMinMaxValues(int min, int max) {
		minValue = min;
		maxValue = max;
	}

	public void setValueOnly() {
		valueOnly = true;
	}

	@SuppressWarnings("unused")
	public String getOverrideStateString() {
		return overrideStateString;
	}

	public void setOverrideStateString(String s) {
		overrideStateString = s;
	}

	@SuppressWarnings("unused")
	public int getNumberDigits() {
		return numberDigits;
	}

	public void setNumberDigits(int i) {
		numberDigits = i;
	}

	public boolean isDragging() {
		return dragging;
	}
}