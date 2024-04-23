package got.client.render.other;

import got.common.database.GOTItems;
import got.common.entity.other.inanimate.GOTEntityLingeringPotion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GOTRenderLingeringPotion extends RenderSnowball {
	public GOTRenderLingeringPotion() {
		super(GOTItems.lingeringPotion);
	}

	private static void colour(float r, float g, float b) {
		GL11.glColor3f(r, g, b);
	}

	private static void colour(int colour) {
		float r = (colour >> 16 & 255) / 255.0F;
		float g = (colour >> 8 & 255) / 255.0F;
		float b = (colour & 255) / 255.0F;
		colour(r, g, b);
	}

	private static void disable(int cap) {
		GL11.glDisable(cap);
	}

	private static void disableRescaleNormal() {
		disable(GL12.GL_RESCALE_NORMAL);
	}

	private static void enable(int cap) {
		GL11.glEnable(cap);
	}

	private static void enableRescaleNormal() {
		enable(GL12.GL_RESCALE_NORMAL);
	}

	private static void popMatrix() {
		GL11.glPopMatrix();
	}

	private static void pushMatrix() {
		GL11.glPushMatrix();
	}

	private static void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	private static void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	private static void translate(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		if (!(entity instanceof GOTEntityLingeringPotion)) {
			return;
		}
		ItemStack stack = ((GOTEntityLingeringPotion) entity).getStack();
		if (stack == null || stack.getItem() == null) {
			return;
		}
		int passes;
		if (stack.getItem().requiresMultipleRenderPasses()) {
			passes = stack.getItem().getRenderPasses(0);
		} else {
			passes = 1;
		}
		pushMatrix();
		translate(x, y, z);
		enableRescaleNormal();
		scale(0.5F, 0.5F, 0.5F);
		bindEntityTexture(entity);
		for (int pass = 0; pass < passes; pass++) {
			IIcon icon = stack.getItem().getIcon(stack, pass);
			if (icon != null) {
				pushMatrix();
				colour(stack.getItem().getColorFromItemStack(stack, pass));
				renderIcon(icon);
				popMatrix();
			}
		}
		colour(1, 1, 1);
		disableRescaleNormal();
		popMatrix();
	}

	private void renderIcon(IIcon icon) {
		Tessellator tessellator = Tessellator.instance;
		float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();
		rotate(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(-0.5F, -0.25F, 0.0D, minU, maxV);
		tessellator.addVertexWithUV(0.5F, -0.25F, 0.0D, maxU, maxV);
		tessellator.addVertexWithUV(0.5F, 0.75F, 0.0D, maxU, minV);
		tessellator.addVertexWithUV(-0.5F, 0.75F, 0.0D, minU, minV);
		tessellator.draw();
	}
}