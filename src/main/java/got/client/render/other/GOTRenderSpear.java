package got.client.render.other;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;
import got.common.entity.other.GOTEntitySpear;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.*;

public class GOTRenderSpear extends Render {

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntitySpear spear = (GOTEntitySpear) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(spear.prevRotationYaw + (spear.rotationYaw - spear.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(spear.prevRotationPitch + (spear.rotationPitch - spear.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		GL11.glEnable(32826);
		float f2 = spear.shake - f1;
		if (f2 > 0.0F) {
			float f3 = -MathHelper.sin(f2 * 3.0F) * f2;
			GL11.glRotatef(f3, 0.0F, 0.0F, 1.0F);
		}
		GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		ItemStack itemstack = spear.getProjectileItem();
		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
		if (customRenderer != null) {
			customRenderer.renderItem(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, new Object[2]);
		} else {
			FMLLog.severe("Error rendering spear: no custom renderer for " + itemstack.toString());
		}
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}
}
