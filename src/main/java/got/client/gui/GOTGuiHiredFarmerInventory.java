package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerHiredFarmerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiHiredFarmerInventory extends GuiContainer {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/npc/hiredFarmer.png");

	private final GOTEntityNPC theNPC;

	public GOTGuiHiredFarmerInventory(InventoryPlayer inv, GOTEntityNPC entity) {
		super(new GOTContainerHiredFarmerInventory(inv, entity));
		theNPC = entity;
		ySize = 161;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		ItemStack seeds = inventorySlots.getSlot(0).getStack();
		if (seeds == null) {
			drawTexturedModalRect(guiLeft + 80, guiTop + 21, 176, 0, 16, 16);
		}
		if (inventorySlots.getSlot(3).getStack() == null) {
			drawTexturedModalRect(guiLeft + 123, guiTop + 34, 176, 16, 16, 16);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 67, 4210752);
		ItemStack seeds = inventorySlots.getSlot(0).getStack();
		if (seeds != null && seeds.stackSize == 1) {
			s = StatCollector.translateToLocal("got.gui.farmer.oneSeed");
			s = EnumChatFormatting.RED + s;
			fontRendererObj.drawSplitString(s, xSize + 10, 20, 120, 16777215);
		}
	}
}