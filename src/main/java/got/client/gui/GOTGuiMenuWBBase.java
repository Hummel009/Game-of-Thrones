package got.client.gui;

import got.client.GOTKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;

public abstract class GOTGuiMenuWBBase extends GOTGuiScreenBase {
	private static RenderItem renderItem = new RenderItem();
	private int xSize = 200;
	private int ySize = 256;
	private int guiLeft;
	private int guiTop;
	private GuiButton goBack;

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
	}

	public GuiButton getGoBack() {
		return goBack;
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2);
		setGuiTop((height - getySize()) / 2);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == GOTKeyHandler.getKeyBindingMenu().getKeyCode()) {
			mc.displayGuiScreen(new GOTGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}

	public void setGoBack(GuiButton goBack) {
		this.goBack = goBack;
	}

	public void setGuiLeft(int guiLeft) {
		this.guiLeft = guiLeft;
	}

	public void setGuiTop(int guiTop) {
		this.guiTop = guiTop;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public static RenderItem getRenderItem() {
		return renderItem;
	}

	public static void setRenderItem(RenderItem renderItem) {
		GOTGuiMenuWBBase.renderItem = renderItem;
	}
}
