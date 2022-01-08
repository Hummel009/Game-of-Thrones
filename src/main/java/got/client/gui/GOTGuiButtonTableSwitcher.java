package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.GOTLevelData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GOTGuiButtonTableSwitcher extends GuiButton {
	public Block table;
	public RenderItem render = new RenderItem();

	public GOTGuiButtonTableSwitcher(int i, int x, int y, String s, Block table) {
		super(i, x, y, 16, 16, s);
		this.table = table;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		Block block = !GOTLevelData.getData(mc.thePlayer).getTableSwitched() ? Blocks.crafting_table : table;
		if (!func_146115_a()) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glEnable(3042);
			zLevel = 100.0f;
			render.zLevel = 100.0f;
			GL11.glEnable(2896);
			GL11.glEnable(32826);
			render.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(block), xPosition, yPosition);
			render.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(block), xPosition, yPosition);
			GL11.glDisable(2896);
			render.zLevel = 0.0f;
			zLevel = 0.0f;
			mouseDragged(mc, i, j);
		}
	}
}
