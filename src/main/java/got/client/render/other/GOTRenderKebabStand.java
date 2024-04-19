package got.client.render.other;

import got.client.model.GOTModelKebabStand;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class GOTRenderKebabStand extends TileEntitySpecialRenderer {
	private static final Map<String, ResourceLocation> STAND_TEXTURES = new HashMap<>();

	private static final GOTModelKebabStand STAND_MODEL = new GOTModelKebabStand();
	private static final ResourceLocation RAW_TEXTURE = new ResourceLocation("got:textures/model/kebab/raw.png");
	private static final ResourceLocation COOKED_TEXTURE = new ResourceLocation("got:textures/model/kebab/cooked.png");

	private static ResourceLocation getStandTexture(GOTTileEntityKebabStand kebabStand) {
		String s = kebabStand.getStandTextureName();
		if (!StringUtils.isNullOrEmpty(s)) {
			s = '_' + s;
		}
		ResourceLocation r = STAND_TEXTURES.get(s = "stand" + s);
		if (r == null) {
			r = new ResourceLocation("got:textures/model/kebab/" + s + ".png");
			STAND_TEXTURES.put(s, r);
		}
		return r;
	}

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
			case 2:
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 5:
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 3:
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			case 4:
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		}
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float scale = 0.0625f;
		bindTexture(getStandTexture(kebabStand));
		STAND_MODEL.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		int meatAmount = kebabStand.getMeatAmount();
		if (meatAmount > 0) {
			boolean cooked = kebabStand.isCooked();
			if (cooked) {
				bindTexture(COOKED_TEXTURE);
			} else {
				bindTexture(RAW_TEXTURE);
			}
			float spin = kebabStand.getKebabSpin(f);
			STAND_MODEL.renderKebab(scale, meatAmount, spin);
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}