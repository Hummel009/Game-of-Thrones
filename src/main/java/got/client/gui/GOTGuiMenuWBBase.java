package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;

public abstract class GOTGuiMenuWBBase extends GOTGuiScreenBase {
	protected int xSize = 200;
	protected int ySize = 256;
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
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == GOTKeyHandler.KEY_BINDING_MENU.getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}
}