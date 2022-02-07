package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.database.GOTRegistry;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiHornSelect extends GOTGuiScreenBase {
	private static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/horn_select.png");
	private static RenderItem itemRenderer = new RenderItem();
	private int xSize = 176;
	private int ySize = 256;
	private int guiLeft;
	private int guiTop;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketHornSelect packet = new GOTPacketHornSelect(button.id);
			GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = StatCollector.translateToLocal("got.gui.hornSelect.title");
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 4210752);
		super.drawScreen(i, j, f);
		for (Object element : buttonList) {
			GuiButton button = (GuiButton) element;
			itemRenderer.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(GOTRegistry.commandHorn, 1, button.id), button.xPosition - 22, button.yPosition + 2);
		}
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonList.add(new GuiButton(1, guiLeft + 40, guiTop + 40, 120, 20, StatCollector.translateToLocal("got.gui.hornSelect.haltReady")));
		buttonList.add(new GuiButton(3, guiLeft + 40, guiTop + 75, 120, 20, StatCollector.translateToLocal("got.gui.hornSelect.summon")));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
		if (itemstack == null || itemstack.getItem() != GOTRegistry.commandHorn || itemstack.getItemDamage() != 0) {
			mc.thePlayer.closeScreen();
		}
	}
}
