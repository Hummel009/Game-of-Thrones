package got.client.render.other;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityLingeringPotion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

@SideOnly(Side.CLIENT)
public class GOTRenderLingeringPotion extends RenderSnowball {

	public GOTRenderLingeringPotion() {
		super(GOTRegistry.lingeringPotion);
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

		GOTRenderLingeringPotion.pushMatrix();
		GOTRenderLingeringPotion.translate(x, y, z);
		GOTRenderLingeringPotion.enableRescaleNormal();
		GOTRenderLingeringPotion.scale(0.5F, 0.5F, 0.5F);
		bindEntityTexture(entity);

		for (int pass = 0; pass < passes; pass++) {
			IIcon icon = stack.getItem().getIcon(stack, pass);

			if (icon != null) {
				GOTRenderLingeringPotion.pushMatrix();
				GOTRenderLingeringPotion.colour(stack.getItem().getColorFromItemStack(stack, pass));
				renderIcon(icon);
				GOTRenderLingeringPotion.popMatrix();
			}
		}

		GOTRenderLingeringPotion.colour(1, 1, 1);
		GOTRenderLingeringPotion.disableRescaleNormal();
		GOTRenderLingeringPotion.popMatrix();
	}

	public void renderIcon(IIcon icon) {
		Tessellator tessellator = Tessellator.instance;

		float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();

		GOTRenderLingeringPotion.rotate(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GOTRenderLingeringPotion.rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(-0.5F, -0.25F, 0.0D, minU, maxV);
		tessellator.addVertexWithUV(0.5F, -0.25F, 0.0D, maxU, maxV);
		tessellator.addVertexWithUV(0.5F, 0.75F, 0.0D, maxU, minV);
		tessellator.addVertexWithUV(-0.5F, 0.75F, 0.0D, minU, minV);
		tessellator.draw();
	}

	public static void colour(float r, float g, float b) {
		GL11.glColor3f(r, g, b);
	}

	public static void colour(int colour) {
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		colour(r, g, b);
	}

	public static void disable(int cap) {
		GL11.glDisable(cap);
	}

	public static void disableRescaleNormal() {
		disable(GL12.GL_RESCALE_NORMAL);
	}

	public static void enable(int cap) {
		GL11.glEnable(cap);
	}

	public static void enableRescaleNormal() {
		enable(GL12.GL_RESCALE_NORMAL);
	}

	public static void popMatrix() {
		GL11.glPopMatrix();
	}

	public static void pushMatrix() {
		GL11.glPushMatrix();
	}

	public static void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	public static void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	public static void translate(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
	}
}