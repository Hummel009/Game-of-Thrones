package got.client.gui;

import got.client.render.other.GOTRenderBiped;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketMiniquestOffer;
import got.common.quest.GOTMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

public class GOTGuiMiniquestOffer extends GOTGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/quest/miniquest.png");
	public static RenderItem renderItem = new RenderItem();
	public GOTMiniQuest theMiniQuest;
	public GOTEntityNPC theNPC;
	public String description;
	public Random rand;
	public int openTick;
	public int xSize = 256;
	public int ySize = 200;
	public int guiLeft;
	public int guiTop;
	public int descriptionX = 85;
	public int descriptionY = 30;
	public int descriptionWidth = 160;
	public int npcX = 46;
	public int npcY = 90;
	public GuiButton buttonAccept;
	public GuiButton buttonDecline;
	public boolean sentClosePacket = false;
	public NPCAction npcAction;
	public int actionTick = 0;
	public int actionTime;
	public float actionSlow;
	public float headYaw;
	public float prevHeadYaw;
	public float headPitch;
	public float prevHeadPitch;

	public GOTGuiMiniquestOffer(GOTMiniQuest quest, GOTEntityNPC npc) {
		theMiniQuest = quest;
		theNPC = npc;
		rand = theNPC.getRNG();
		openTick = 0;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			boolean close = false;
			if (button == buttonAccept) {
				GOTPacketMiniquestOffer.sendClosePacket(mc.thePlayer, theNPC, true);
				close = true;
			} else if (button == buttonDecline) {
				GOTPacketMiniquestOffer.sendClosePacket(mc.thePlayer, theNPC, false);
				close = true;
			}
			if (close) {
				sentClosePacket = true;
				mc.thePlayer.closeScreen();
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		if (description == null) {
			description = GOTSpeech.formatSpeech(theMiniQuest.quoteStart, mc.thePlayer, null, theMiniQuest.getObjectiveInSpeech());
		}
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String name = theNPC.getNPCName();
		this.drawCenteredString(name, guiLeft + xSize / 2, guiTop + 8, 8019267);
		renderNPC(guiLeft + npcX, guiTop + npcY, guiLeft + npcX - i, guiTop + npcY - j, f);
		fontRendererObj.drawSplitString(description, guiLeft + descriptionX, guiTop + descriptionY, descriptionWidth, 8019267);
		String objective = theMiniQuest.getQuestObjective();
		int objWidth = fontRendererObj.getStringWidth(objective);
		int objY = guiTop + ySize - 50;
		this.drawCenteredString(objective, guiLeft + xSize / 2, objY, 8019267);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int iconW = 16;
		int iconB = 6;
		int iconY = objY + fontRendererObj.FONT_HEIGHT / 2 - iconW / 2;
		renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), theMiniQuest.getQuestIcon(), guiLeft + xSize / 2 - objWidth / 2 - iconW - iconB, iconY);
		renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), theMiniQuest.getQuestIcon(), guiLeft + xSize / 2 + objWidth / 2 + iconB, iconY);
		GL11.glDisable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonAccept = new GOTGuiButton(0, guiLeft + xSize / 2 - 20 - 80, guiTop + ySize - 30, 80, 20, StatCollector.translateToLocal("got.gui.miniquestOffer.accept"));
		buttonList.add(buttonAccept);
		buttonDecline = new GOTGuiButton(1, guiLeft + xSize / 2 + 20, guiTop + ySize - 30, 80, 20, StatCollector.translateToLocal("got.gui.miniquestOffer.decline"));
		buttonList.add(buttonDecline);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (!sentClosePacket) {
			GOTPacketMiniquestOffer.sendClosePacket(mc.thePlayer, theNPC, false);
			sentClosePacket = true;
		}
	}

	public boolean recursiveCheckForModel(ModelRenderer base, ModelRenderer match) {
		if (base == match) {
			return true;
		}
		if (base.childModels != null) {
			for (Object obj : base.childModels) {
				ModelRenderer part = (ModelRenderer) obj;
				if (recursiveCheckForModel(part, match)) {
					return true;
				}
			}
		}
		return false;
	}

	public void renderNPC(int i, int j, float dx, float dy, float f) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		float scale = 70.0f;
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		GL11.glTranslatef(i, j, 40.0f);
		GL11.glScalef(-scale, -scale, -scale);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef((float) Math.atan(dx / 40.0f) * 20.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-((float) Math.atan(dy / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
		GL11.glTranslatef(0.0f, theNPC.yOffset, 0.0f);
		RenderManager.instance.playerViewY = 180.0f;
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		Render render = RenderManager.instance.getEntityRenderObject(theNPC);
		if (render instanceof GOTRenderBiped) {
			GOTRenderBiped npcRenderer = (GOTRenderBiped) render;
			ModelBiped model = npcRenderer.modelBipedMain;
			model.isChild = theNPC.isChild();
			mc.getTextureManager().bindTexture(npcRenderer.getEntityTexture(theNPC));
			GL11.glTranslatef(0.0f, -model.bipedHead.rotationPointY / 16.0f, 0.0f);
			float yaw = prevHeadYaw + (headYaw - prevHeadYaw) * f;
			float pitch = prevHeadPitch + (headPitch - prevHeadPitch) * f;
			yaw = (float) Math.toDegrees(yaw);
			pitch = (float) Math.toDegrees(pitch);
			GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
			model.bipedHeadwear.rotateAngleX = 0.0f;
			model.bipedHead.rotateAngleX = 0.0f;
			model.bipedHeadwear.rotateAngleY = 0.0f;
			model.bipedHead.rotateAngleY = 0.0f;
			model.bipedHeadwear.rotateAngleZ = 0.0f;
			model.bipedHead.rotateAngleZ = 0.0f;
			model.bipedHead.render(0.0625f);
			model.bipedHeadwear.render(0.0625f);
			for (int pass = 0; pass < 4; ++pass) {
				int l;
				ModelRenderer part;
				int shouldRenderPass = npcRenderer.shouldRenderPass(theNPC, pass, 1.0f);
				if (shouldRenderPass > 0) {
					model = npcRenderer.npcRenderPassModel;
					model.isChild = theNPC.isChild();
					List modelParts = model.boxList;
					boolean[] prevShowModels = new boolean[modelParts.size()];
					for (l = 0; l < modelParts.size(); ++l) {
						part = (ModelRenderer) modelParts.get(l);
						prevShowModels[l] = part.showModel;
						boolean isHeadPart = false;
						if (recursiveCheckForModel(model.bipedHead, part) || recursiveCheckForModel(model.bipedHeadwear, part)) {
							isHeadPart = true;
						}
						if (!isHeadPart) {
							part.showModel = false;
						}
					}
					model.render(theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
					if ((shouldRenderPass & 0xF0) == 16) {
						npcRenderer.func_82408_c(theNPC, pass, 1.0f);
						model.render(theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
					}
					for (l = 0; l < modelParts.size(); ++l) {
						part = (ModelRenderer) modelParts.get(l);
						part.showModel = prevShowModels[l];
					}
				}
			}
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!theNPC.isEntityAlive() || mc.thePlayer.getDistanceToEntity(theNPC) > 8.0f) {
			mc.thePlayer.closeScreen();
		}
		prevHeadYaw = headYaw;
		prevHeadPitch = headPitch;
		if (npcAction == null) {
			if (openTick < 100) {
				npcAction = NPCAction.TALKING;
				actionTime = 100;
				actionSlow = 1.0f;
			} else if (rand.nextInt(200) == 0) {
				npcAction = NPCAction.getRandomAction(rand);
				if (npcAction != null) {
					switch (npcAction) {
						case LOOKING:
							actionTime = 60 + rand.nextInt(60);
							actionSlow = 1.0f;
							break;
						case LOOKING_UP:
							actionTime = 30 + rand.nextInt(50);
							actionSlow = 1.0f;
							break;
						case SHAKING:
							actionTime = 100 + rand.nextInt(60);
							actionSlow = 1.0f;
							break;
						case TALKING:
							actionTime = 40 + rand.nextInt(60);
							actionSlow = 1.0f;
							break;
					}
				}
			}
		} else {
			++actionTick;
		}
		if (npcAction != null) {
			if (actionTick >= actionTime) {
				npcAction = null;
				actionTick = 0;
				actionTime = 0;
			} else {
				switch (npcAction) {
					case LOOKING:
						float slow = actionSlow * 16.0f;
						headYaw = MathHelper.sin(actionTick / slow) * (float) Math.toRadians(60.0);
						headPitch = (MathHelper.sin(actionTick / slow * 2.0f) + 1.0f) / 2.0f * (float) Math.toRadians(-15.0);
						break;
					case LOOKING_UP:
						headYaw = 0.0f;
						headPitch = (float) Math.toRadians(-20.0);
						break;
					case SHAKING:
						actionSlow += 0.01f;
						headYaw = MathHelper.sin(actionTick / actionSlow) * (float) Math.toRadians(30.0);
						headPitch += (float) Math.toRadians(0.4);
						break;
					case TALKING:
						if (actionTick % 20 == 0) {
							actionSlow = 0.7f + rand.nextFloat() * 1.5f;
						}
						float slow1 = actionSlow * 2.0f;
						headYaw = MathHelper.sin(actionTick / slow1) * (float) Math.toRadians(10.0);
						headPitch = (MathHelper.sin(actionTick / slow1 * 2.0f) + 1.0f) / 2.0f * (float) Math.toRadians(-20.0);
						break;
				}
			}
		} else {
			headYaw = 0.0f;
			headPitch = MathHelper.sin(openTick * 0.07f) * (float) Math.toRadians(5.0);
		}
		++openTick;
	}

	public enum NPCAction {
		TALKING(1.0f), SHAKING(0.1f), LOOKING(0.3f), LOOKING_UP(0.4f);

		public static float totalWeight = -1.0f;

		public float weight;

		NPCAction(float f) {
			weight = f;
		}

		public static NPCAction getRandomAction(Random rand) {
			if (totalWeight <= 0.0f) {
				totalWeight = 0.0f;
				for (NPCAction action : NPCAction.values()) {
					totalWeight += action.weight;
				}
			}
			float f = rand.nextFloat();
			f *= totalWeight;
			NPCAction chosen = null;
			for (NPCAction action : NPCAction.values()) {
				f -= action.weight;
				if (f <= 0.0f) {
					chosen = action;
					break;
				}
			}
			return chosen;
		}
	}

}
