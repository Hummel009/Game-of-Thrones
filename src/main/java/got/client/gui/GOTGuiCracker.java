package got.client.gui;

import got.common.inventory.GOTContainerCracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiCracker extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("got:textures/gui/cracker.png");

	private final GOTContainerCracker theCracker;

	private GuiButton buttonSeal;

	public GOTGuiCracker(EntityPlayer entityplayer) {
		super(new GOTContainerCracker(entityplayer));
		theCracker = (GOTContainerCracker) inventorySlots;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonSeal && !theCracker.isCrackerInvEmpty()) {
			theCracker.sendSealingPacket(mc.thePlayer);
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public boolean checkHotbarKeys(int i) {
		return false;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = StatCollector.translateToLocal("got.gui.cracker");
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonSeal = new GOTGuiButton(0, guiLeft + xSize / 2 - 40, guiTop + 48, 80, 20, StatCollector.translateToLocal("got.gui.cracker.seal"));
		buttonList.add(buttonSeal);
		buttonSeal.enabled = false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonSeal.enabled = !theCracker.isCrackerInvEmpty();
	}
}