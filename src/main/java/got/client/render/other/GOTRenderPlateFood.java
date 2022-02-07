package got.client.render.other;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import got.common.entity.other.GOTPlateFallingInfo;
import got.common.tileentity.GOTTileEntityPlate;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class GOTRenderPlateFood extends TileEntitySpecialRenderer {
	private Random rand = new Random();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntityPlate plate = (GOTTileEntityPlate) tileentity;
		ItemStack plateItem = plate.getFoodItem();
		GOTPlateFallingInfo fallInfo = plate.plateFallInfo;
		if (fallInfo == null) {
		} else {
			fallInfo.getPlateOffsetY(f);
		}
		if (plateItem != null) {
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glEnable(32826);
			GL11.glTranslatef((float) d + 0.5F, (float) d1, (float) d2 + 0.5F);
			bindTexture(TextureMap.locationItemsTexture);
			IIcon icon = plateItem.getIconIndex();
			Tessellator tessellator = Tessellator.instance;
			float f4 = icon.getMinU();
			float f1 = icon.getMaxU();
			float f22 = icon.getMinV();
			float f3 = icon.getMaxV();
			int foods = plateItem.stackSize;
			float lowerOffset = 0.125F;
			for (int l = 0; l < foods; l++) {
				GL11.glPushMatrix();
				float offset = 0.0F;
				if (fallInfo != null) {
					offset = fallInfo.getFoodOffsetY(l, f);
				}
				offset = Math.max(offset, lowerOffset);
				GL11.glTranslatef(0.0F, offset, 0.0F);
				lowerOffset = offset + 0.03125F;
				rand.setSeed(plate.xCoord * 3129871 ^ plate.zCoord * 116129781L ^ plate.yCoord + l * 5930563L);
				float rotation = rand.nextFloat() * 360.0F;
				GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(-0.25F, -0.25F, 0.0F);
				GL11.glScalef(0.5625F, 0.5625F, 0.5625F);
				ItemRenderer.renderItemIn2D(tessellator, f1, f22, f4, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
				GL11.glPopMatrix();
			}
			GL11.glDisable(32826);
			GL11.glEnable(2884);
			GL11.glPopMatrix();
		}
	}
}
