package got.client.gui;

import got.common.database.GOTRegistry;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketHornSelect;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiHornSelect extends GOTGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/horn_select.png");
	public static RenderItem itemRenderer = new RenderItem();
	public int xSize = 176;
	public int ySize = 256;
	public int guiLeft;
	public int guiTop;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketHornSelect packet = new GOTPacketHornSelect(button.id);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = StatCollector.translateToLocal("got.gui.hornSelect.title");
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 4210752);
		super.drawScreen(i, j, f);
		for (GuiButton element : buttonList) {
			GuiButton button = element;
			itemRenderer.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(GOTRegistry.commandHorn, 1, button.id), button.xPosition - 22, button.yPosition + 2);
		}
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonList.add(new GOTGuiButton(1, guiLeft + 40, guiTop + 40, 120, 20, StatCollector.translateToLocal("got.gui.hornSelect.haltReady")));
		buttonList.add(new GOTGuiButton(3, guiLeft + 40, guiTop + 75, 120, 20, StatCollector.translateToLocal("got.gui.hornSelect.summon")));
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
