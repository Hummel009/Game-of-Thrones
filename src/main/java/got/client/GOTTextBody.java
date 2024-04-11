package got.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class GOTTextBody {
	private static final String LINEBREAK = "<BR>";
	private final List<TextColor> list = new ArrayList<>();
	private final int defaultColor;
	private int textWidth;

	public GOTTextBody(int c) {
		defaultColor = c;
		textWidth = 100;
	}

	public void add(String s) {
		add(s, defaultColor);
	}

	public void add(String s, int c) {
		list.add(new TextColor(s, c));
	}

	public void addLinebreak() {
		add(LINEBREAK);
	}

	private int getColor(int i) {
		return list.get(i).getColor();
	}

	public String getText(int i) {
		return list.get(i).getText();
	}

	private int getTotalLines(FontRenderer fr) {
		int lines = 0;
		for (int i = 0; i < size(); ++i) {
			String part = getText(i);
			List<String> lineList = fr.listFormattedStringToWidth(part, textWidth);
			lines += lineList.size();
		}
		return lines;
	}

	public float renderAndReturnScroll(FontRenderer fr, int x, int yTop, int yBottom, float scroll) {
		int ySize = yBottom - yTop;
		int numLines = getTotalLines(fr);
		int lineHeight = fr.FONT_HEIGHT;
		float scroll2 = Math.max(scroll, 0.0f);
		float scroll1 = Math.min(scroll2, numLines - MathHelper.floor_double((float) ySize / lineHeight));
		int d1 = Math.round(scroll1);
		int y = yTop;
		y += ySize / lineHeight * lineHeight;
		y -= lineHeight;
		int maxLines = ySize / lineHeight;
		if (numLines < maxLines) {
			y -= (maxLines - numLines) * lineHeight;
		}
		for (int i = size() - 1; i >= 0; --i) {
			String part = getText(i);
			int color = getColor(i);
			List<String> lineList = fr.listFormattedStringToWidth(part, textWidth);
			for (int l = lineList.size() - 1; l >= 0; --l) {
				StringBuilder line = new StringBuilder(lineList.get(l));
				if (d1 > 0) {
					--d1;
				} else {
					if (y < yTop) {
						return scroll1;
					}
					if (line.toString().equals(LINEBREAK)) {
						line = new StringBuilder();
						char br = '-';
						while (fr.getStringWidth(line.toString() + br) < textWidth) {
							line.append(br);
						}
					}
					fr.drawString(line.toString(), x, y, color);
					y -= lineHeight;
				}
			}
		}
		return scroll1;
	}

	public void set(int i, String s) {
		list.get(i).setText(s);
	}

	@SuppressWarnings("unused")
	public int getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(int w) {
		textWidth = w;
	}

	public int size() {
		return list.size();
	}

	private static class TextColor {
		private final int color;

		private String text;

		private TextColor(String s, int c) {
			text = s;
			color = c;
		}

		private int getColor() {
			return color;
		}

		private String getText() {
			return text;
		}

		private void setText(String text) {
			this.text = text;
		}
	}

}
