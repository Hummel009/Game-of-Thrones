package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;

public abstract class GOTGuiMenuBase extends GOTGuiMenuWBBase {
	protected static final RenderItem RENDER_ITEM = new RenderItem();
	protected int xSize = 200;
	protected int ySize = 256;
	protected int guiLeft;
	protected int guiTop;
	protected GuiButton buttonMenuReturn;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonMenuReturn) {
			mc.displayGuiScreen(new GOTGuiMenu());
		}
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int buttonH = 20;
		int buttonGap = 35;
		int minGap = 10;
		buttonMenuReturn = new GOTGuiButtonLeftRight(1000, true, 0, guiTop + (ySize + buttonH) / 4, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(buttonMenuReturn);
		buttonMenuReturn.xPosition = Math.min(buttonGap, guiLeft - minGap - buttonMenuReturn.width);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == GOTKeyHandler.keyBindingMenu.getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}

	protected int getSizeX() {
		return xSize;
	}

	protected void setSizeX(int xSize) {
		this.xSize = xSize;
	}
}
