package got.client.render.other;

import got.client.GOTClientProxy;
import got.client.GOTTickHandlerClient;
import got.client.effect.GOTEntityReputationBonus;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.faction.GOTReputationBonusMap;
import got.common.faction.GOTReputationValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class GOTRenderReputationBonus extends Render {
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	public GOTRenderReputationBonus() {
		shadowSize = 0.0f;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EntityClientPlayerMP entityplayer = MINECRAFT.thePlayer;
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		GOTFaction viewingFaction = playerData.getViewingFaction();
		GOTEntityReputationBonus reputationBonus = (GOTEntityReputationBonus) entity;
		GOTFaction mainFaction = reputationBonus.getMainFaction();
		GOTReputationBonusMap factionBonusMap = reputationBonus.getFactionBonusMap();
		GOTFaction renderFaction = null;
		boolean showConquest = false;
		if (reputationBonus.getConquestBonus() > 0.0f && playerData.hasTakenOathTo(viewingFaction) || reputationBonus.getConquestBonus() < 0.0f && (viewingFaction == mainFaction || playerData.hasTakenOathTo(viewingFaction))) {
			renderFaction = viewingFaction;
			showConquest = true;
		} else if (!factionBonusMap.isEmpty()) {
			if (factionBonusMap.containsKey(viewingFaction)) {
				renderFaction = viewingFaction;
			} else if (factionBonusMap.size() == 1 && mainFaction.isPlayableReputationFaction() || mainFaction.isPlayableReputationFaction() && reputationBonus.getPrevMainReputation() >= 0.0f && factionBonusMap.get(mainFaction) < 0.0f) {
				renderFaction = mainFaction;
			} else {
				float reputation;
				for (Map.Entry<GOTFaction, Float> entry : factionBonusMap.entrySet()) {
					GOTFaction faction = entry.getKey();
					if (faction.isPlayableReputationFaction() && entry.getValue() > 0.0f) {
						reputation = playerData.getReputation(faction);
						if (renderFaction == null || reputation > playerData.getReputation(renderFaction)) {
							renderFaction = faction;
						}
					}
				}
				if (renderFaction == null) {
					if (mainFaction.isPlayableReputationFaction() && factionBonusMap.get(mainFaction) < 0.0f) {
						renderFaction = mainFaction;
					} else {
						for (Map.Entry<GOTFaction, Float> entry : factionBonusMap.entrySet()) {
							GOTFaction faction = entry.getKey();
							if (faction.isPlayableReputationFaction() && entry.getValue() < 0.0f) {
								reputation = playerData.getReputation(faction);
								if (renderFaction == null || reputation > playerData.getReputation(renderFaction)) {
									renderFaction = faction;
								}
							}
						}
					}
				}
			}
		}
		float renderBonus = factionBonusMap.getOrDefault(renderFaction, 0.0f);
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
			int age = reputationBonus.getParticleAge();
			float alpha = age < 60 ? 1.0f : (80 - age) / 20.0f;
			renderBonusText(reputationBonus, viewingFaction, renderFaction, !factionBonusMap.isEmpty(), renderBonus, showConquest, alpha);
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
		return GOTClientProxy.REPUTATION_TEXTURE;
	}

	private void renderBonusText(GOTEntityReputationBonus reputationBonus, GOTFaction viewingFaction, GOTFaction renderFaction, boolean showRep, float rep, boolean showConquest, float alpha) {
		FontRenderer fr = MINECRAFT.fontRenderer;
		String strRep = GOTReputationValues.formatRepForDisplay(rep);
		String name = reputationBonus.getName();
		float conq = reputationBonus.getConquestBonus();
		GL11.glPushMatrix();
		boolean isViewingFaction = renderFaction == viewingFaction;
		if (!isViewingFaction) {
			float scale = 0.5f;
			GL11.glScalef(scale, scale, 1.0f);
			strRep = strRep + " (" + renderFaction.factionName() + "...)";
		}
		int x = -MathHelper.floor_double((fr.getStringWidth(strRep) + 18) / 2.0);
		int y = -12;
		if (showRep) {
			bindEntityTexture(reputationBonus);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			GOTTickHandlerClient.drawTexturedModalRect(x, y - 5, 0, 36, 16, 16);
			GOTTickHandlerClient.drawReputationText(fr, x + 18, y, strRep, alpha);
			GOTTickHandlerClient.drawReputationText(fr, -MathHelper.floor_double(fr.getStringWidth(name) / 2.0), y += 14, name, alpha);
		}
		if (showConquest && conq != 0.0f) {
			boolean negative = conq < 0.0f;
			String strConq = GOTReputationValues.formatConqForDisplay(conq, true);
			x = -MathHelper.floor_double((fr.getStringWidth(strConq) + 18) / 2.0);
			if (showRep) {
				y += 16;
			}
			bindEntityTexture(reputationBonus);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			GOTTickHandlerClient.drawTexturedModalRect(x, y - 5, negative ? 16 : 0, 228, 16, 16);
			GOTTickHandlerClient.drawConquestText(fr, x + 18, y, strConq, negative, alpha);
		}
		GL11.glPopMatrix();
	}
}