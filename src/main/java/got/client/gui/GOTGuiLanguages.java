package got.client.gui;

import java.io.*;
import java.net.URI;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.*;

public class GOTGuiLanguages extends GOTGuiMenuWBBase {
	private GOTGuiButton openFolder;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			switch (button.id) {
			case 1: {
				switch (Util.getOSType()) {
				case OSX: {
					try {
						Runtime.getRuntime().exec(new String[] { "/usr/bin/open", new File(Minecraft.getMinecraft().mcDataDir, "config").getAbsolutePath() });
						return;
					} catch (IOException var7) {
						var7.printStackTrace();
						break;
					}
				}
				case WINDOWS: {
					String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new File(Minecraft.getMinecraft().mcDataDir, "config").getAbsolutePath());
					try {
						Runtime.getRuntime().exec(var2);
						return;
					} catch (IOException var6) {
						var6.printStackTrace();
						break;
					}
				}
				default:
					mc.displayGuiScreen(new GOTGuiMenu());
				}
				boolean var8 = false;
				try {
					Class<?> var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop").invoke(null);
					var3.getMethod("browse", URI.class).invoke(var4, new File(mc.mcDataDir, "config").toURI());
				} catch (Throwable var5) {
					var5.printStackTrace();
					var8 = true;
				}
				if (!var8) {
					break;
				}
				System.out.println("Opening via system class!");
				Sys.openURL("file://" + new File(Minecraft.getMinecraft().mcDataDir, "config").getAbsolutePath());
				break;
			}
			case 2: {
				mc.displayGuiScreen(new GOTGuiMenu());
			}
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		String s1 = StatCollector.translateToLocal("got.gui.languages");
		fontRendererObj.drawString(s1, getGuiLeft() + 100 - fontRendererObj.getStringWidth(s1) / 2, getGuiTop() + 10, 16777215);
		String s2 = StatCollector.translateToLocal("got.gui.languages.guide1") + GOT.getLangs() + StatCollector.translateToLocal("got.gui.languages.guide2");
		int x = getGuiLeft() + getxSize() / 2;
		int y = getGuiTop() + 40;
		for (Object element : fontRendererObj.listFormattedStringToWidth(s2, 220)) {
			s2 = (String) element;
			this.drawCenteredString(s2, x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
		}
		super.drawScreen(i, j, f);
		for (Object element : buttonList) {
			GuiButton button = (GuiButton) element;
			if (!(button instanceof GOTGuiButtonOptions)) {
				continue;
			}
			((GOTGuiButtonOptions) button).drawTooltip(mc, i, j);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiTop((height - getySize()) / 2 + 10);
		int buttonX = getGuiLeft() + getxSize() / 2 - 100;
		int buttonY = getGuiTop() + 40;

		openFolder = new GOTGuiButton(1, buttonX, buttonY + 100, 200, 20, StatCollector.translateToLocal("got.gui.openFolder"));
		buttonList.add(openFolder);

		setGoBack(new GOTGuiButton(2, buttonX, buttonY + 140, 200, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
	}
}
