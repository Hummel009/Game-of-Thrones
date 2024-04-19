package got.client.gui;

import got.common.entity.essos.yiti.GOTEntityYiTiBombardier;
import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerHiredWarriorInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiHiredWarriorInventory extends GuiContainer {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/npc/hiredWarrior.png");

	private final GOTContainerHiredWarriorInventory containerInv;
	private final GOTEntityNPC theNPC;

	public GOTGuiHiredWarriorInventory(InventoryPlayer inv, GOTEntityNPC entity) {
		super(new GOTContainerHiredWarriorInventory(inv, entity));
		theNPC = entity;
		containerInv = (GOTContainerHiredWarriorInventory) inventorySlots;
		ySize = 188;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theNPC instanceof GOTEntityYiTiBombardier) {
			Slot slotBomb = containerInv.getSlotFromInventory(containerInv.getProxyInv(), 5);
			Slot slotMelee = containerInv.getSlotFromInventory(containerInv.getProxyInv(), 4);
			drawTexturedModalRect(guiLeft + slotBomb.xDisplayPosition - 1, guiTop + slotBomb.yDisplayPosition - 1, slotMelee.xDisplayPosition - 1, slotMelee.yDisplayPosition - 1, 18, 18);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = StatCollector.translateToLocal("got.gui.warrior.openInv");
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 95, 4210752);
	}
}