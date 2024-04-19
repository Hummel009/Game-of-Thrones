package got.client.render.other;

import got.common.GOTLevelData;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderShield {
	private static final float MODELSCALE = 0.0625f;

	private GOTRenderShield() {
	}

	private static void doRenderShield(float f) {
		int k;
		float f8;
		float f9;
		float f7;
		float minU = 0.0f + f;
		float maxU = 0.5f + f;
		float minV = 0.0f;
		float maxV = 1.0f;
		double depth1 = MODELSCALE * 0.5f * f;
		double depth2 = MODELSCALE * 0.5f * (0.5f + f);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		tessellator.addVertexWithUV(0.0, 0.0, depth1, maxU, maxV);
		tessellator.addVertexWithUV(1.0, 0.0, depth1, minU, maxV);
		tessellator.addVertexWithUV(1.0, 1.0, depth1, minU, minV);
		tessellator.addVertexWithUV(0.0, 1.0, depth1, maxU, minV);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		tessellator.addVertexWithUV(0.0, 1.0, depth2, maxU, minV);
		tessellator.addVertexWithUV(1.0, 1.0, depth2, minU, minV);
		tessellator.addVertexWithUV(1.0, 0.0, depth2, minU, maxV);
		tessellator.addVertexWithUV(0.0, 0.0, depth2, maxU, maxV);
		tessellator.draw();
		float f5 = 0.5f * (maxU - minU) / 32;
		float f6 = 0.5f * (maxV - minV) / 32;
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		for (k = 0; k < 32; ++k) {
			f7 = (float) k / 32;
			f8 = maxU + (minU - maxU) * f7 - f5;
			tessellator.addVertexWithUV(f7, 0.0, depth2, f8, maxV);
			tessellator.addVertexWithUV(f7, 0.0, depth1, f8, maxV);
			tessellator.addVertexWithUV(f7, 1.0, depth1, f8, minV);
			tessellator.addVertexWithUV(f7, 1.0, depth2, f8, minV);
		}
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		for (k = 0; k < 32; ++k) {
			f7 = (float) k / 32;
			f8 = maxU + (minU - maxU) * f7 - f5;
			f9 = f7 + 1.0f / 32;
			tessellator.addVertexWithUV(f9, 1.0, depth2, f8, minV);
			tessellator.addVertexWithUV(f9, 1.0, depth1, f8, minV);
			tessellator.addVertexWithUV(f9, 0.0, depth1, f8, maxV);
			tessellator.addVertexWithUV(f9, 0.0, depth2, f8, maxV);
		}
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		for (k = 0; k < 32; ++k) {
			f7 = (float) k / 32;
			f8 = maxV + (minV - maxV) * f7 - f6;
			f9 = f7 + 1.0f / 32;
			tessellator.addVertexWithUV(0.0, f9, depth1, maxU, f8);
			tessellator.addVertexWithUV(1.0, f9, depth1, minU, f8);
			tessellator.addVertexWithUV(1.0, f9, depth2, minU, f8);
			tessellator.addVertexWithUV(0.0, f9, depth2, maxU, f8);
		}
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		for (k = 0; k < 32; ++k) {
			f7 = (float) k / 32;
			f8 = maxV + (minV - maxV) * f7 - f6;
			tessellator.addVertexWithUV(1.0, f7, depth1, minU, f8);
			tessellator.addVertexWithUV(0.0, f7, depth1, maxU, f8);
			tessellator.addVertexWithUV(0.0, f7, depth2, maxU, f8);
			tessellator.addVertexWithUV(1.0, f7, depth2, minU, f8);
		}
		tessellator.draw();
	}

	public static void renderShield(GOTShields shield, EntityLivingBase entity, ModelBiped model) {
		Minecraft mc = Minecraft.getMinecraft();
		ResourceLocation shieldTexture = shield.getShieldTexture();
		ItemStack held = null;
		ItemStack heldLeft = null;
		ItemStack inUse = null;
		ItemStack chestplate = null;
		if (entity != null) {
			held = entity.getHeldItem();
		}
		if (entity instanceof GOTEntityNPC) {
			heldLeft = ((GOTEntityNPC) entity).getHeldItemLeft();
		}
		if (entity instanceof EntityPlayer) {
			inUse = ((EntityPlayer) entity).getItemInUse();
		}
		if (entity != null) {
			chestplate = entity.getEquipmentInSlot(3);
		}
		boolean holdingSword = held != null && (held.getItem() instanceof ItemSword || held.getItem() instanceof ItemTool) || inUse != null && inUse.getItemUseAction() == EnumAction.bow;
		boolean blocking = holdingSword && inUse != null && inUse.getItemUseAction() == EnumAction.block;
		boolean wearingChestplate = chestplate != null && chestplate.getItem().isValidArmor(chestplate, ((ItemArmor) GOTItems.valyrianChestplate).armorType, entity);
		boolean renderOnBack = (!holdingSword || heldLeft != null) && entity != null;
		boolean doNotRender = entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).npcCape != null && renderOnBack || entity instanceof EntityPlayer && GOTLevelData.getData((EntityPlayer) entity).getCape() != null && renderOnBack;
		if (!doNotRender) {
			GL11.glPushMatrix();
			if (renderOnBack) {
				model.bipedBody.postRender(MODELSCALE);
			} else {
				model.bipedLeftArm.postRender(MODELSCALE);
			}
			GL11.glScalef(-1.5f, -1.5f, 1.5f);
			if (renderOnBack) {
				GL11.glTranslatef(0.5f, -0.8f, 0.0f);
				if (wearingChestplate) {
					GL11.glTranslatef(0.0f, 0.0f, 0.24f);
				} else {
					GL11.glTranslatef(0.0f, 0.0f, 0.16f);
				}
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
			} else if (blocking) {
				GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(-0.4f, -0.9f, -0.15f);
			} else {
				GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(-0.5f, -0.75f, 0.0f);
				if (wearingChestplate) {
					GL11.glTranslatef(0.0f, 0.0f, -0.24f);
				} else {
					GL11.glTranslatef(0.0f, 0.0f, -0.16f);
				}
				GL11.glRotatef(-15.0f, 0.0f, 0.0f, 1.0f);
			}
			mc.getTextureManager().bindTexture(shieldTexture);
			GL11.glEnable(3008);
			doRenderShield(0.0f);
			GL11.glTranslatef(1.0f, 0.0f, 0.0f);
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
			doRenderShield(0.5f);
			GL11.glPopMatrix();
		}
	}
}