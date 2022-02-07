package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;

public abstract class GOTGuiMenuBase extends GOTGuiMenuWBBase {
	private static RenderItem renderItem = new RenderItem();
	private int xSize = 200;
	private int ySize = 256;
	private int guiLeft;
	private int guiTop;
	private GuiButton buttonMenuReturn;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == getButtonMenuReturn()) {
			mc.displayGuiScreen(new GOTGuiMenu());
		}
		super.actionPerformed(button);
	}

	public GuiButton getButtonMenuReturn() {
		return buttonMenuReturn;
	}

	@Override
	public int getGuiLeft() {
		return guiLeft;
	}

	@Override
	public int getGuiTop() {
		return guiTop;
	}

	@Override
	public int getxSize() {
		return xSize;
	}

	@Override
	public int getySize() {
		return ySize;
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2);
		setGuiTop((height - getySize()) / 2);
		int buttonH = 20;
		int buttonGap = 35;
		int minGap = 10;
		setButtonMenuReturn(new GOTGuiButtonLeftRight(1000, true, 0, getGuiTop() + (getySize() + buttonH) / 4, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getButtonMenuReturn());
		getButtonMenuReturn().xPosition = Math.min(0 + buttonGap, getGuiLeft() - minGap - getButtonMenuReturn().width);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == GOTKeyHandler.keyBindingMenu.getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}

	public void setButtonMenuReturn(GuiButton buttonMenuReturn) {
		this.buttonMenuReturn = buttonMenuReturn;
	}

	@Override
	public void setGuiLeft(int guiLeft) {
		this.guiLeft = guiLeft;
	}

	@Override
	public void setGuiTop(int guiTop) {
		this.guiTop = guiTop;
	}

	@Override
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	@Override
	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public static RenderItem getRenderItem() {
		return renderItem;
	}

	public static void setRenderItem(RenderItem renderItem) {
		GOTGuiMenuBase.renderItem = renderItem;
	}
}
