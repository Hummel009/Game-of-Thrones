package got.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class GOTTextBody {
	public static String LINEBREAK = "<BR>";
	public List<TextColor> list = new ArrayList<>();
	public int defaultColor;
	public int textWidth;

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

	public int getColor(int i) {
		return list.get(i).color;
	}

	public String getText(int i) {
		return list.get(i).text;
	}

	public int getTotalLines(FontRenderer fr) {
		int lines = 0;
		for (int i = 0; i < size(); ++i) {
			String part = getText(i);
			List<String> lineList = fr.listFormattedStringToWidth(part, textWidth);
			for (String line : lineList) {
				++lines;
			}
		}
		return lines;
	}

	public void render(FontRenderer fr, int x, int y) {
		renderAndReturnScroll(fr, x, y, Integer.MAX_VALUE, Float.MAX_VALUE);
	}

	public float renderAndReturnScroll(FontRenderer fr, int x, int yTop, int yBottom, float scroll) {
		int ySize = yBottom - yTop;
		int numLines = getTotalLines(fr);
		int lineHeight = fr.FONT_HEIGHT;
		scroll = Math.max(scroll, 0.0f);
		scroll = Math.min(scroll, numLines - MathHelper.floor_double((float) ySize / lineHeight));
		int d1 = Math.round(scroll);
		int y = yTop;
		y += ySize / lineHeight * lineHeight;
		y -= lineHeight;
		int maxLines = ySize / lineHeight;
		if (numLines < maxLines) {
			y -= (maxLines - numLines) * lineHeight;
		}
		block0:
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
						break block0;
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
		return scroll;
	}

	public void set(int i, String s) {
		list.get(i).text = s;
	}

	public void set(int i, String s, int c) {
		list.get(i).text = s;
		list.get(i).color = c;
	}

	public void setTextWidth(int w) {
		textWidth = w;
	}

	public int size() {
		return list.size();
	}

	public static class TextColor {
		public String text;
		public int color;

		public TextColor(String s, int c) {
			text = s;
			color = c;
		}
	}

}
