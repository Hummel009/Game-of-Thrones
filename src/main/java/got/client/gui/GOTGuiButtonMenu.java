package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;

public class GOTGuiButtonMenu extends GuiButton {
	private Class<? extends GOTGuiMenuWBBase> menuScreenClass;
	private int menuKeyCode;

	public GOTGuiButtonMenu(GOTGuiMenu gui, int i, int x, int y, Class<? extends GOTGuiMenuWBBase> cls, String s, int key) {
		super(i, x, y, 32, 32, s);
		menuScreenClass = cls;
		setMenuKeyCode(key);
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
			mc.getTextureManager().bindTexture(GOTGuiMenu.getMenuIconsTexture());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, 0 + (enabled ? 0 : width * 2) + (field_146123_n ? width : 0), id * height, width, height);
			mouseDragged(mc, i, j);
		}
	}

	public int getMenuKeyCode() {
		return menuKeyCode;
	}

	public GOTGuiMenuWBBase openMenu() {
		try {
			return menuScreenClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setMenuKeyCode(int menuKeyCode) {
		this.menuKeyCode = menuKeyCode;
	}
}