package got.client.gui;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonMenu extends GuiButton {
	private final Class<? extends GOTGuiMenuWBBase> menuScreenClass;
	private final int menuKeyCode;

	public GOTGuiButtonMenu(int i, int x, int y, Class<? extends GOTGuiMenuWBBase> cls, String s, int key) {
		super(i, x, y, 32, 32, s);
		menuScreenClass = cls;
		menuKeyCode = key;
	}

	public boolean canDisplayMenu() {
		if (menuScreenClass == GOTGuiMap.class) {
			WorldClient world = Minecraft.getMinecraft().theWorld;
			return world != null && world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
		}
		return true;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(GOTGuiMenu.MENU_ICONS_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, (enabled ? 0 : width * 2) + (field_146123_n ? width : 0), id * height, width, height);
			mouseDragged(mc, i, j);
		}
	}

	public GOTGuiMenuWBBase openMenu() {
		try {
			return menuScreenClass.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getMenuKeyCode() {
		return menuKeyCode;
	}
}