package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.entity.essos.yiti.GOTEntityYiTiBombardier;
import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerHiredWarriorInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.*;

public class GOTGuiHiredWarriorInventory extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/npc/hiredWarrior.png");
	public GOTEntityNPC theNPC;
	public GOTContainerHiredWarriorInventory containerInv;

	public GOTGuiHiredWarriorInventory(InventoryPlayer inv, GOTEntityNPC entity) {
		super(new GOTContainerHiredWarriorInventory(inv, entity));
		theNPC = entity;
		containerInv = (GOTContainerHiredWarriorInventory) inventorySlots;
		ySize = 188;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theNPC instanceof GOTEntityYiTiBombardier) {
			Slot slotBomb = containerInv.getSlotFromInventory(containerInv.proxyInv, 5);
			Slot slotMelee = containerInv.getSlotFromInventory(containerInv.proxyInv, 4);
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
