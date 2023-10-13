package got.client.render.other;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class GOTRenderInvTableCommand implements IItemRenderer {
	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		Block block = Block.getBlockFromItem(itemstack.getItem());
		int meta = itemstack.getItemDamage();
		RenderBlocks rb = (RenderBlocks) data[0];
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.18f, 0.0f);
		float scale = 0.6f;
		GL11.glScalef(scale, scale, scale);
		rb.renderBlockAsItem(block, meta, 1.0f);
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return true;
	}
}
