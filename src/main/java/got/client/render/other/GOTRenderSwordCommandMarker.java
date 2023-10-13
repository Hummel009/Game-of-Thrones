package got.client.render.other;

import org.lwjgl.opengl.GL11;

import got.common.database.GOTItems;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class GOTRenderSwordCommandMarker extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		float rotation = -renderManager.livingPlayer.rotationYaw;
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(135.0F, 0.0F, 0.0F, 1.0F);
		float scale = 1.2F;
		GL11.glTranslatef(-0.75F * scale, 0.0F, 0.03125F * scale);
		GL11.glScalef(scale, scale, scale);
		ItemStack item = new ItemStack(GOTItems.commandSword);
		GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
		GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
		renderManager.itemRenderer.renderItem(renderManager.livingPlayer, item, 0, IItemRenderer.ItemRenderType.EQUIPPED);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}
}
