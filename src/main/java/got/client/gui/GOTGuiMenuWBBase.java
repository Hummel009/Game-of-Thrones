package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;

public abstract class GOTGuiMenuWBBase extends GOTGuiScreenBase {
	public static RenderItem renderItem = new RenderItem();
	public int xSize = 200;
	public int ySize = 256;
	public int guiLeft;
	public int guiTop;
	public GuiButton goBack;

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
		if (i == GOTKeyHandler.keyBindingMenu.getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}
}
