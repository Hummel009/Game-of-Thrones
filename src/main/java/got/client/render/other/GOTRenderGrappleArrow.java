package got.client.render.other;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import got.common.entity.other.GOTEntityGrapplingArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class GOTRenderGrappleArrow extends Render {
	public static ResourceLocation LEASH_KNOT_TEXTURES = new ResourceLocation("textures/entity/lead_knot.png");

	public Item item;
	public ItemStack itemstack;

	public GOTRenderGrappleArrow(Item itemIn) {
		item = itemIn;
		itemstack = new ItemStack(item);

	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GOTEntityGrapplingArrow arrow = (GOTEntityGrapplingArrow) entity;
		if (arrow == null || arrow.isDead) {
			return;
		}

		EntityLivingBase e = (EntityLivingBase) arrow.shootingEntity;

		if (e == null || e.isDead) {
			return;
		}

		IIcon iicon = item.getIconFromDamage(0);

		Vec3 offset = Vec3.createVectorHelper(0, 0, 0);
		if (!arrow.attached) {
			if (arrow.righthand) {
				offset = Vec3.createVectorHelper(1 * -0.36D, -0.175D, 0.45D);

			} else {
				offset = Vec3.createVectorHelper(1 * 0.36D, -0.175D, 0.45D);

			}
			offset.rotateAroundX(-(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * partialTicks) * 0.017453292F);
			offset.rotateAroundY(-(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks) * 0.017453292F);

			double dist = e.getDistanceToEntity(arrow);
			double mult = 1 - (dist / 10.0);
			if (mult <= 0) {
				offset = Vec3.createVectorHelper(0, 0, 0);
			} else {
				offset = Vec3.createVectorHelper(offset.xCoord * mult, offset.yCoord * mult, offset.zCoord * mult);

				x += offset.xCoord;
				y += offset.yCoord;
				z += offset.zCoord;
			}
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		bindEntityTexture(arrow);
		Tessellator tessellator = Tessellator.instance;

		func_77026_a(tessellator, iicon);

		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.draw();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		bindEntityTexture(entity);
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		tessellator.startDrawing(7);
		tessellator.addVertex(-0.5D, -0.5D, 0.0D);

		tessellator.addVertex(0.5D, -0.5D, 0.0D);

		tessellator.addVertex(0.5D, 0.5D, 0.0D);

		tessellator.addVertex(-0.5D, 0.5D, 0.0D);

		tessellator.draw();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

		int k = 1;
		float f7 = e.getSwingProgress(partialTicks);
		float f8 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float) Math.PI);
		float f9 = (e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * partialTicks) * 0.017453292F;
		double d0 = MathHelper.sin(f9);
		double d1 = MathHelper.cos(f9);
		double d2 = k * 0.35D;
		double d4;
		double d5;
		double d6;
		double d7;

		if ((renderManager.options == null || renderManager.options.thirdPersonView <= 0) && e == Minecraft.getMinecraft().thePlayer) {
			Vec3 V;
			if (arrow.righthand) {
				V = Vec3.createVectorHelper(k * -0.36D, -0.175D, 0.45D);

			} else {
				V = Vec3.createVectorHelper(k * 0.36D, -0.175D, 0.45D);

			}
			V.rotateAroundX(-(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * partialTicks) * 0.017453292F);
			V.rotateAroundY(-(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks) * 0.017453292F);
			V.rotateAroundY(f8 * 0.5F);
			V.rotateAroundX(-f8 * 0.7F);
			d4 = e.prevPosX + (e.posX - e.prevPosX) * partialTicks + V.xCoord;
			d5 = e.prevPosY + (e.posY - e.prevPosY) * partialTicks + V.yCoord;
			d6 = e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks + V.zCoord;
			d7 = e.getEyeHeight();
		} else {
			d4 = e.prevPosX + (e.posX - e.prevPosX) * partialTicks - d1 * d2 - d0 * 0.8D;
			d5 = e.prevPosY + e.getEyeHeight() + (e.posY - e.prevPosY) * partialTicks - 0.45D;
			d6 = e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks - d0 * d2 + d1 * 0.8D;
			d7 = e.isSneaking() ? -0.1875D : 0.0D;
		}

		double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
		double d8 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
		double d9 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

		double d10 = ((float) (d4 - d13)) - offset.xCoord;
		double d11 = ((float) (d5 - d8)) + d7 - offset.yCoord;
		double d12 = ((float) (d6 - d9)) - offset.zCoord;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		tessellator.startDrawing(5);

		double taut = arrow.taut;

		boolean reverse = false;
		if (arrow.posY < e.posY + 1.62) {
			reverse = true;
		}

		double X;
		double Y;
		double Z;
		for (int i1 = 0; i1 <= 16; ++i1) {
			float R = 0.5F;
			float G = 0.4F;
			float B = 0.3F;

			if (i1 % 2 == 0) {
				R *= 0.7F;
				G *= 0.7F;
				B *= 0.7F;
			}

			float f10 = i1 / 16.0F;
			X = x + d10 * f10;
			Z = z + d12 * f10;
			if (reverse) {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (f10 * f10 + f10) * 0.5D);
			} else {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (Math.sqrt(f10)));
			}

			tessellator.setColorRGBA_F(R, G, B, 1.0F);
			tessellator.addVertex(X, Y + 0.025D, Z);

			tessellator.addVertex(X - 0.025D, Y, Z - 0.025D);

		}

		tessellator.draw();
		tessellator.startDrawing(5);

		for (int i1 = 0; i1 <= 16; ++i1) {
			float R = 0.5F;
			float G = 0.4F;
			float B = 0.3F;

			if (i1 % 2 == 0) {
				R *= 0.7F;
				G *= 0.7F;
				B *= 0.7F;
			}

			float f10 = i1 / 16.0F;
			X = x + d10 * f10;
			Z = z + d12 * f10;
			if (reverse) {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (f10 * f10 + f10) * 0.5D);
			} else {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (Math.sqrt(f10)));
			}
			tessellator.setColorRGBA_F(R, G, B, 1.0F);
			tessellator.addVertex(X + 0.025D, Y, Z - 0.025D);

			tessellator.addVertex(X, Y + 0.025D, Z);

		}

		tessellator.draw();
		tessellator.startDrawing(5);

		for (int i1 = 0; i1 <= 16; ++i1) {
			float R = 0.5F;
			float G = 0.4F;
			float B = 0.3F;

			if (i1 % 2 == 0) {
				R *= 0.7F;
				G *= 0.7F;
				B *= 0.7F;
			}

			float f10 = i1 / 16.0F;
			X = x + d10 * f10;
			Z = z + d12 * f10;
			if (reverse) {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (f10 * f10 + f10) * 0.5D);
			} else {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (Math.sqrt(f10)));
			}
			tessellator.setColorRGBA_F(R, G, B, 1.0F);
			tessellator.addVertex(X, Y - 0.025D, Z);

			tessellator.addVertex(X + 0.025D, Y, Z - 0.025D);

		}

		tessellator.draw();
		tessellator.startDrawing(5);

		for (int i1 = 0; i1 <= 16; ++i1) {
			float R = 0.5F;
			float G = 0.4F;
			float B = 0.3F;

			if (i1 % 2 == 0) {
				R *= 0.7F;
				G *= 0.7F;
				B *= 0.7F;
			}

			float f10 = i1 / 16.0F;
			X = x + d10 * f10;
			Z = z + d12 * f10;
			if (reverse) {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (f10 * f10 + f10) * 0.5D);
			} else {
				Y = y + (d11 * f10) * taut + (1 - taut) * (d11 * (Math.sqrt(f10)));
			}
			tessellator.setColorRGBA_F(R, G, B, 1.0F);
			tessellator.addVertex(X - 0.025D, Y, Z - 0.025D);

			tessellator.addVertex(X, Y - 0.025D, Z);

		}

		tessellator.draw();
		tessellator.startDrawing(5);
		X = x + d10;
		Y = y + d11;
		Z = z + d12;
		tessellator.setColorRGBA_F(0.5F * 0.7F, 0.4F * 0.7F, 0.3F * 0.7F, 1.0F);
		tessellator.addVertex(X, Y - 0.025D, Z);

		tessellator.addVertex(X - 0.025D, Y, Z - 0.025D);

		tessellator.addVertex(X, Y + 0.025D, Z);

		tessellator.addVertex(X + 0.025D, Y, Z - 0.025D);

		tessellator.addVertex(X, Y - 0.025D, Z);

		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);

	}

	public void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_) {
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		p_77026_1_.startDrawingQuads();
		p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
		p_77026_1_.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		p_77026_1_.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		p_77026_1_.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		p_77026_1_.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		p_77026_1_.draw();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}

	public ItemStack getStackToRender(Entity entityIn) {
		return itemstack;
	}
}
