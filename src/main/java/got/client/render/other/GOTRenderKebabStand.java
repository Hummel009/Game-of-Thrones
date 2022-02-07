package got.client.render.other;

import java.util.*;

import org.lwjgl.opengl.GL11;

import got.client.model.GOTModelKebabStand;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class GOTRenderKebabStand extends TileEntitySpecialRenderer {
	private static GOTModelKebabStand standModel = new GOTModelKebabStand();
	private static Map<String, ResourceLocation> standTextures = new HashMap<>();
	private static ResourceLocation rawTexture = new ResourceLocation("got:textures/model/kebab/raw.png");
	private static ResourceLocation cookedTexture = new ResourceLocation("got:textures/model/kebab/cooked.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntityKebabStand kebabStand = (GOTTileEntityKebabStand) tileentity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		int meta = kebabStand.getBlockMetadata();
		switch (meta) {
		case 2: {
			GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
			break;
		}
		case 5: {
			GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
			break;
		}
		case 3: {
			GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
			break;
		}
		case 4: {
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		}
		}
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float scale = 0.0625f;
		bindTexture(GOTRenderKebabStand.getStandTexture(kebabStand));
		standModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		int meatAmount = kebabStand.getMeatAmount();
		if (meatAmount > 0) {
			boolean cooked = kebabStand.isCooked();
			if (cooked) {
				bindTexture(cookedTexture);
			} else {
				bindTexture(rawTexture);
			}
			float spin = kebabStand.getKebabSpin(f);
			standModel.renderKebab(scale, meatAmount, spin);
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	private static ResourceLocation getStandTexture(GOTTileEntityKebabStand kebabStand) {
		ResourceLocation r;
		String s = kebabStand.getStandTextureName();
		if (!StringUtils.isNullOrEmpty(s)) {
			s = "_" + s;
		}
		r = standTextures.get(s = "stand" + s);
		if (r == null) {
			r = new ResourceLocation("got:textures/model/kebab/" + s + ".png");
			standTextures.put(s, r);
		}
		return r;
	}
}
