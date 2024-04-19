package got.client.gui;

import got.common.GOTLevelData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonTableSwitcher extends GuiButton {
	private static final RenderItem ITEM_RENDERER = new RenderItem();

	private final Block table;

	public GOTGuiButtonTableSwitcher(int i, int x, int y, String s, Block table) {
		super(i, x, y, 16, 16, s);
		this.table = table;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		Block block = GOTLevelData.getData(mc.thePlayer).getTableSwitched() ? table : Blocks.crafting_table;
		if (!func_146115_a()) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glEnable(3042);
			zLevel = 100.0f;
			ITEM_RENDERER.zLevel = 100.0f;
			GL11.glEnable(2896);
			GL11.glEnable(32826);
			ITEM_RENDERER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(block), xPosition, yPosition);
			ITEM_RENDERER.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(block), xPosition, yPosition);
			GL11.glDisable(2896);
			ITEM_RENDERER.zLevel = 0.0f;
			zLevel = 0.0f;
			mouseDragged(mc, i, j);
		}
	}
}