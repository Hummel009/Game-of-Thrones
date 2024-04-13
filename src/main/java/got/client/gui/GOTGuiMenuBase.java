package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;

public abstract class GOTGuiMenuBase extends GOTGuiScreenBase {
	protected int sizeX = 200;
	protected int sizeY = 256;
	protected int guiLeft;
	protected int guiTop;
	protected GuiButton goBack;

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - sizeX) / 2;
		guiTop = (height - sizeY) / 2;
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == GOTKeyHandler.KEY_BINDING_MENU.getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}

	protected int getSizeX() {
		return sizeX;
	}

	@SuppressWarnings("unused")
	protected int getSizeY() {
		return sizeY;
	}
}