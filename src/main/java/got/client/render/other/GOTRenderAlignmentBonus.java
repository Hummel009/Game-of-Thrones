package got.client.render.other;

import got.client.GOTClientProxy;
import got.client.GOTTickHandlerClient;
import got.client.effect.GOTEntityAlignmentBonus;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTAlignmentBonusMap;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class GOTRenderAlignmentBonus extends Render {
	public GOTRenderAlignmentBonus() {
		shadowSize = 0.0f;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		float renderBonus;
		EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		GOTFaction viewingFaction = playerData.getViewingFaction();
		GOTEntityAlignmentBonus alignmentBonus = (GOTEntityAlignmentBonus) entity;
		GOTFaction mainFaction = alignmentBonus.mainFaction;
		GOTAlignmentBonusMap factionBonusMap = alignmentBonus.factionBonusMap;
		GOTFaction renderFaction = null;
		boolean showConquest = false;
		if (alignmentBonus.conquestBonus > 0.0f && playerData.isPledgedTo(viewingFaction)) {
			renderFaction = viewingFaction;
			showConquest = true;
		} else if (alignmentBonus.conquestBonus < 0.0f && (viewingFaction == mainFaction || playerData.isPledgedTo(viewingFaction))) {
			renderFaction = viewingFaction;
			showConquest = true;
		} else if (!factionBonusMap.isEmpty()) {
			if (factionBonusMap.containsKey(viewingFaction)) {
				renderFaction = viewingFaction;
			} else if (factionBonusMap.size() == 1 && mainFaction.isPlayableAlignmentFaction()) {
				renderFaction = mainFaction;
			} else if (mainFaction.isPlayableAlignmentFaction() && alignmentBonus.prevMainAlignment >= 0.0f && factionBonusMap.get(mainFaction) < 0.0f) {
				renderFaction = mainFaction;
			} else {
				float alignment;
				for (Map.Entry<GOTFaction, Float> entry : factionBonusMap.entrySet()) {
					GOTFaction faction = entry.getKey();
					if (faction.isPlayableAlignmentFaction() && entry.getValue() > 0.0f) {
						alignment = playerData.getAlignment(faction);
						if (renderFaction == null || alignment > playerData.getAlignment(renderFaction)) {
							renderFaction = faction;
						}
					}
				}
				if (renderFaction == null) {
					if (mainFaction.isPlayableAlignmentFaction() && factionBonusMap.get(mainFaction) < 0.0f) {
						renderFaction = mainFaction;
					} else {
						for (Map.Entry<GOTFaction, Float> entry : factionBonusMap.entrySet()) {
							GOTFaction faction = entry.getKey();
							if (faction.isPlayableAlignmentFaction() && entry.getValue() < 0.0f) {
								alignment = playerData.getAlignment(faction);
								if (renderFaction == null || alignment > playerData.getAlignment(renderFaction)) {
									renderFaction = faction;
								}
							}
						}
					}
				}
			}
		}
		renderBonus = factionBonusMap.getOrDefault(renderFaction, 0.0f);
		if (renderFaction != null && (renderBonus != 0.0f || showConquest)) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1, (float) d2);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(-0.025f, -0.025f, 0.025f);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			GL11.glDisable(2929);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			int age = alignmentBonus.particleAge;
			float alpha = age < 60 ? 1.0f : (80 - age) / 20.0f;
			renderBonusText(alignmentBonus, playerData, viewingFaction, renderFaction, !factionBonusMap.isEmpty(), renderBonus, showConquest, alpha);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(3042);
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
			GL11.glEnable(2896);
			GL11.glPopMatrix();
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return GOTClientProxy.alignmentTexture;
	}

	public void renderBonusText(GOTEntityAlignmentBonus alignmentBonus, GOTPlayerData playerData, GOTFaction viewingFaction, GOTFaction renderFaction, boolean showAlign, float align, boolean showConquest, float alpha) {
		boolean isViewingFaction;
		Minecraft mc = Minecraft.getMinecraft();
		FontRenderer fr = mc.fontRenderer;
		String strAlign = GOTAlignmentValues.formatAlignForDisplay(align);
		String name = alignmentBonus.name;
		float conq = alignmentBonus.conquestBonus;
		GL11.glPushMatrix();
		isViewingFaction = renderFaction == viewingFaction;
		if (!isViewingFaction) {
			float scale = 0.5f;
			GL11.glScalef(scale, scale, 1.0f);
			strAlign = strAlign + " (" + renderFaction.factionName() + "...)";
		}
		int x = -MathHelper.floor_double((fr.getStringWidth(strAlign) + 18) / 2.0);
		int y = -12;
		if (showAlign) {
			bindEntityTexture(alignmentBonus);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			GOTTickHandlerClient.drawTexturedModalRect(x, y - 5, 0, 36, 16, 16);
			GOTTickHandlerClient.drawAlignmentText(fr, x + 18, y, strAlign, alpha);
			GOTTickHandlerClient.drawAlignmentText(fr, -MathHelper.floor_double(fr.getStringWidth(name) / 2.0), y += 14, name, alpha);
		}
		if (showConquest && conq != 0.0f) {
			boolean negative = conq < 0.0f;
			String strConq = GOTAlignmentValues.formatConqForDisplay(conq, true);
			x = -MathHelper.floor_double((fr.getStringWidth(strConq) + 18) / 2.0);
			if (showAlign) {
				y += 16;
			}
			bindEntityTexture(alignmentBonus);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			GOTTickHandlerClient.drawTexturedModalRect(x, y - 5, negative ? 16 : 0, 228, 16, 16);
			GOTTickHandlerClient.drawConquestText(fr, x + 18, y, strConq, negative, alpha);
		}
		GL11.glPopMatrix();
	}
}
