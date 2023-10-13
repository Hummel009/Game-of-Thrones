package got.client.render.other;

import org.lwjgl.opengl.GL11;

import got.common.entity.other.GOTEntityInvasionSpawner;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class GOTRenderInvasionSpawner extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GOTEntityInvasionSpawner spawner = (GOTEntityInvasionSpawner) entity;
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		float rotation = interpolateRotation(spawner.prevSpawnerSpin, spawner.spawnerSpin, f1);
		float scale = 1.5F;
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(scale, scale, scale);
		ItemStack item = spawner.getInvasionItem();
		renderManager.itemRenderer.renderItem(renderManager.livingPlayer, item, 0, IItemRenderer.ItemRenderType.EQUIPPED);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}

	@SuppressWarnings("all")
	public float interpolateRotation(float prevRotation, float newRotation, float tick) {
		float interval;
		for (interval = newRotation - prevRotation; interval < -180.0F; interval += 360.0F) {

		}
		while (interval >= 180.0F) {
			interval -= 360.0F;
		}
		return prevRotation + tick * interval;
	}
}
