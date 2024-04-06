package got.client.render.other;

import got.client.model.GOTModelUnsmeltery;
import got.common.block.other.GOTBlockForgeBase;
import got.common.tileentity.GOTTileEntityUnsmeltery;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderUnsmeltery extends TileEntitySpecialRenderer {
	private static final ModelBase UNSMELTERY_MODEL = new GOTModelUnsmeltery();
	private static final ResourceLocation IDLE_TEXTURE = new ResourceLocation("got:textures/model/unsmeltery/idle.png");
	private static final ResourceLocation ACTIVE_TEXTURE = new ResourceLocation("got:textures/model/unsmeltery/active.png");

	public void renderInvUnsmeltery() {
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		renderTileEntityAt(null, 0.0, 0.0, 0.0, 0.0f);
		GL11.glEnable(32826);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GOTTileEntityUnsmeltery unsmeltery = (GOTTileEntityUnsmeltery) tileentity;
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		GL11.glScalef(1.0f, -1.0f, -1.0f);
		float rotation = 0.0f;
		float rocking = 0.0f;
		if (unsmeltery != null) {
			switch (unsmeltery.getBlockMetadata() & 7) {
				case 2:
					rotation = 180.0f;
					break;
				case 3:
					rotation = 0.0f;
					break;
				case 4:
					rotation = 90.0f;
					break;
				case 5:
					rotation = 270.0f;
			}
			rocking = unsmeltery.getRockingAmount(f);
		}
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		boolean useActiveTexture = unsmeltery != null && GOTBlockForgeBase.isForgeActive(unsmeltery.getWorldObj(), unsmeltery.xCoord, unsmeltery.yCoord, unsmeltery.zCoord);
		if (useActiveTexture) {
			bindTexture(ACTIVE_TEXTURE);
		} else {
			bindTexture(IDLE_TEXTURE);
		}
		UNSMELTERY_MODEL.render(null, rocking, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}
}
