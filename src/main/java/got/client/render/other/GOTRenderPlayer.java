package got.client.render.other;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.*;
import got.common.*;
import got.common.database.*;
import got.common.faction.GOTAlignmentValues;
import got.common.fellowship.GOTFellowshipClient;
import got.common.world.GOTWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

public class GOTRenderPlayer {
	public Minecraft mc = Minecraft.getMinecraft();
	public RenderManager renderManager = RenderManager.instance;
	public ModelBiped playerModel = new ModelBiped(0.0f);

	public GOTRenderPlayer() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void postRender(RenderPlayerEvent.Post event) {
		float yOffset;
		EntityPlayer entityplayer = event.entityPlayer;
		float tick = event.partialRenderTick;
		double d0 = RenderManager.renderPosX;
		double d1 = RenderManager.renderPosY;
		double d2 = RenderManager.renderPosZ;
		float f0 = (float) entityplayer.lastTickPosX + (float) (entityplayer.posX - entityplayer.lastTickPosX) * tick;
		float f1 = (float) entityplayer.lastTickPosY + (float) (entityplayer.posY - entityplayer.lastTickPosY) * tick;
		float f2 = (float) entityplayer.lastTickPosZ + (float) (entityplayer.posZ - entityplayer.lastTickPosZ) * tick;
		float fr0 = f0 - (float) d0;
		float fr1 = f1 - (float) d1;
		float fr2 = f2 - (float) d2;
		yOffset = entityplayer.isPlayerSleeping() ? -1.5f : 0.0f;
		if (shouldRenderAlignment(entityplayer) && (mc.theWorld.provider instanceof GOTWorldProvider || GOTConfig.alwaysShowAlignment)) {
			float range;
			GOTPlayerData clientPD = GOTLevelData.getData(mc.thePlayer);
			GOTPlayerData otherPD = GOTLevelData.getData(entityplayer);
			float alignment = otherPD.getAlignment(clientPD.getViewingFaction());
			double dist = entityplayer.getDistanceSqToEntity(renderManager.livingPlayer);
			if (dist < (range = RendererLivingEntity.NAME_TAG_RANGE) * range) {
				FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
				GL11.glPushMatrix();
				GL11.glTranslatef(fr0, fr1, fr2);
				GL11.glTranslatef(0.0f, entityplayer.height + 0.6f + yOffset, 0.0f);
				GL11.glNormal3f(0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
				GL11.glScalef(-1.0f, -1.0f, 1.0f);
				float scale = 0.025f;
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(2896);
				GL11.glDepthMask(false);
				GL11.glDisable(2929);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				String sAlign = GOTAlignmentValues.formatAlignForDisplay(alignment);
				mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
				GOTTickHandlerClient.drawTexturedModalRect(-MathHelper.floor_double((fr.getStringWidth(sAlign) + 18) / 2.0), -19.0, 0, 36, 16, 16);
				GOTTickHandlerClient.drawAlignmentText(fr, 18 - MathHelper.floor_double((fr.getStringWidth(sAlign) + 18) / 2.0), -12, sAlign, 1.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glDisable(3042);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glEnable(2896);
				GL11.glDisable(32826);
				GL11.glPopMatrix();
			}
		}
		if (shouldRenderFellowPlayerHealth(entityplayer)) {
			GOTNPCRendering.renderHealthBar(entityplayer, fr0, fr1, fr2, new int[] { 16375808, 12006707 }, null);
		}
	}

	@SubscribeEvent
	public void preRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
		EntityPlayer entityplayer = event.entityPlayer;
		float tick = event.partialRenderTick;
		GOTShields shield = GOTLevelData.getData(entityplayer).getShield();
		GOTCapes cape = GOTLevelData.getData(entityplayer).getCape();
		double d = entityplayer.field_71091_bM + (entityplayer.field_71094_bP - entityplayer.field_71091_bM) * tick - (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * tick);
		double d1 = entityplayer.field_71096_bN + (entityplayer.field_71095_bQ - entityplayer.field_71096_bN) * tick - (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * tick);
		double d2 = entityplayer.field_71097_bO + (entityplayer.field_71085_bR - entityplayer.field_71097_bO) * tick - (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * tick);
		float f6 = entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * tick;
		double d3 = MathHelper.sin(f6 * 3.1415927f / 180.0f);
		double d4 = -MathHelper.cos(f6 * 3.1415927f / 180.0f);
		float f7 = (float) d1 * 10.0f;
		if (f7 < -6.0f) {
			f7 = -6.0f;
		}
		if (f7 > 32.0f) {
			f7 = 32.0f;
		}
		float f8 = (float) (d * d3 + d2 * d4) * 100.0f;
		float f9 = (float) (d * d4 - d2 * d3) * 100.0f;
		if (f8 < 0.0f) {
			f8 = 0.0f;
		}
		float f10 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * tick;
		f7 += MathHelper.sin((entityplayer.prevDistanceWalkedModified + (entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified) * tick) * 6.0f) * 32.0f * f10;
		if (entityplayer.isSneaking()) {
			f7 += 25.0f;
		}
		if (shield != null) {
			if (!entityplayer.isInvisible()) {
				GOTRenderShield.renderShield(shield, entityplayer, event.renderer.modelBipedMain);
			} else if (!entityplayer.isInvisibleToPlayer(mc.thePlayer)) {
				GL11.glPushMatrix();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
				GL11.glDepthMask(false);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glAlphaFunc(516, 0.003921569f);
				GOTRenderShield.renderShield(shield, entityplayer, event.renderer.modelBipedMain);
				GL11.glDisable(3042);
				GL11.glAlphaFunc(516, 0.1f);
				GL11.glPopMatrix();
				GL11.glDepthMask(true);
			}
		}
		if (cape != null && (shield == null || shield != null && !GOTRenderShield.renderOnBack)) {
			if (!entityplayer.isInvisible()) {

				Minecraft mc = Minecraft.getMinecraft();
				ResourceLocation capeTexture = cape.capeTexture;
				GL11.glPushMatrix();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(0.0f, 0.0f, 0.125f);
				GL11.glRotatef(6.0f + f8 / 2.0f + f7, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(f9 / 2.0f, 0.0f, 0.0f, 1.0f);
				GL11.glRotatef(-f9 / 2.0f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				mc.getTextureManager().bindTexture(capeTexture);
				event.renderer.modelBipedMain.renderCloak(0.0625f);
				GL11.glPopMatrix();

			} else if (!entityplayer.isInvisibleToPlayer(mc.thePlayer)) {
				GL11.glPushMatrix();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
				GL11.glDepthMask(false);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glAlphaFunc(516, 0.003921569f);

				Minecraft mc = Minecraft.getMinecraft();
				ResourceLocation capeTexture = cape.capeTexture;
				GL11.glPushMatrix();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(0.0f, 0.0f, 0.125f);
				GL11.glRotatef(6.0f + f8 / 2.0f + f7, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(f9 / 2.0f, 0.0f, 0.0f, 1.0f);
				GL11.glRotatef(-f9 / 2.0f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				mc.getTextureManager().bindTexture(capeTexture);
				event.renderer.modelBipedMain.renderCloak(0.0625f);
				GL11.glPopMatrix();

				GL11.glDisable(3042);
				GL11.glAlphaFunc(516, 0.1f);
				GL11.glPopMatrix();
				GL11.glDepthMask(true);
			}
		}
	}

	public boolean shouldRenderAlignment(EntityPlayer entityplayer) {
		if (GOTConfig.displayAlignmentAboveHead && shouldRenderPlayerHUD(entityplayer)) {
			if (GOTLevelData.getData(entityplayer).getHideAlignment()) {
				String playerName = entityplayer.getCommandSenderName();
				List<GOTFellowshipClient> fellowships = GOTLevelData.getData(mc.thePlayer).getClientFellowships();
				for (GOTFellowshipClient fs : fellowships) {
					if (fs.isPlayerIn(playerName)) {
						return true;
					}
				}
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean shouldRenderFellowPlayerHealth(EntityPlayer entityplayer) {
		if (GOTConfig.fellowPlayerHealthBars && shouldRenderPlayerHUD(entityplayer)) {
			List<GOTFellowshipClient> fellowships = GOTLevelData.getData(mc.thePlayer).getClientFellowships();
			for (GOTFellowshipClient fs : fellowships) {
				if (fs.isPlayerIn(entityplayer.getCommandSenderName())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean shouldRenderPlayerHUD(EntityPlayer entityplayer) {
		if (Minecraft.isGuiEnabled()) {
			return entityplayer != renderManager.livingPlayer && !entityplayer.isSneaking() && !entityplayer.isInvisibleToPlayer(mc.thePlayer);
		}
		return false;
	}
}
