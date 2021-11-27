package got.client.render.other;

import org.lwjgl.opengl.GL11;

import got.client.GOTClientProxy;
import got.common.GOTConfig;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class GOTRenderBlade implements IItemRenderer {
	public double distance;
	public GOTRenderLargeItem largeItemRenderer;
	public GOTRenderLargeItem.ExtraLargeIconToken tokenGlowing;

	public GOTRenderBlade(double d, GOTRenderLargeItem large) {
		distance = d;
		largeItemRenderer = large;
		if (largeItemRenderer != null) {
			tokenGlowing = largeItemRenderer.extraIcon("glowing");
		}
	}

	@Override
	public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
		return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object... data) {
		EntityLivingBase entity = (EntityLivingBase) data[1];
		Item item = itemstack.getItem();
		entity.worldObj.theProfiler.startSection("blade");
		boolean glows;
		glows = true;
		if (glows) {
			GL11.glDisable(2896);
		}
		if (largeItemRenderer != null) {
			if (glows) {
				largeItemRenderer.renderLargeItem(tokenGlowing);
			} else {
				largeItemRenderer.renderLargeItem();
			}
		} else {
			IIcon icon = ((EntityLivingBase) data[1]).getItemIcon(itemstack, 0);
			if (glows) {
				icon = ((GOTItemSword) item).glowingIcon;
			}
			icon = RenderBlocks.getInstance().getIconSafe(icon);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			int width = icon.getIconWidth();
			int height = icon.getIconWidth();
			Tessellator tessellator = Tessellator.instance;
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
		}
		if (itemstack != null && itemstack.hasEffect(0)) {
			GOTClientProxy.renderEnchantmentEffect();
		}
		if (glows) {
			GL11.glEnable(2896);
			if (GOTConfig.bladeGlow) {
				for (int i = 0; i < 4; ++i) {
					GOTClientProxy.renderEnchantmentEffect();
				}
			}
		}
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		entity.worldObj.theProfiler.endSection();
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
		return false;
	}
}