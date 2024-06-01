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
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/quest/miniquest.png");
	private static final RenderItem ITEM_RENDERER = new RenderItem();

	private static final int X_SIZE = 256;
	private static final int Y_SIZE = 200;

	private final GOTMiniQuest theMiniQuest;
	private final GOTEntityNPC theNPC;
	private final Random rand;

	private NPCAction npcAction;

	private GuiButton buttonAccept;
	private GuiButton buttonDecline;

	private String description;

	private boolean sentClosePacket;

	private float actionSlow;
	private float headYaw;
	private float prevHeadYaw;
	private float headPitch;
	private float prevHeadPitch;

	private int openTick;
	private int guiLeft;
	private int guiTop;
	private int actionTick;
	private int actionTime;

	public GOTGuiMiniquestOffer(GOTMiniQuest quest, GOTEntityNPC npc) {
		theMiniQuest = quest;
		theNPC = npc;
		rand = theNPC.getRNG();
		openTick = 0;
	}

	private static boolean recursiveCheckForModel(ModelRenderer base, ModelRenderer match) {
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
			description = GOTSpeech.formatSpeech(theMiniQuest.getQuoteStart(), mc.thePlayer, null, theMiniQuest.getObjectiveInSpeech());
		}
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, X_SIZE, Y_SIZE);
		String name = theNPC.getNPCName();
		drawCenteredString(name, guiLeft + X_SIZE / 2, guiTop + 8, 8019267);
		int npcY = 90;
		int npcX = 46;
		renderNPC(guiLeft + npcX, guiTop + npcY, guiLeft + npcX - i, guiTop + npcY - j, f);
		int descriptionWidth = 160;
		int descriptionY = 30;
		int descriptionX = 85;
		fontRendererObj.drawSplitString(description, guiLeft + descriptionX, guiTop + descriptionY, descriptionWidth, 8019267);
		String objective = theMiniQuest.getQuestObjective();
		int objWidth = fontRendererObj.getStringWidth(objective);
		int objY = guiTop + Y_SIZE - 50;
		drawCenteredString(objective, guiLeft + X_SIZE / 2, objY, 8019267);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int iconW = 16;
		int iconB = 6;
		int iconY = objY + fontRendererObj.FONT_HEIGHT / 2 - iconW / 2;
		ITEM_RENDERER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), theMiniQuest.getQuestIcon(), guiLeft + X_SIZE / 2 - objWidth / 2 - iconW - iconB, iconY);
		ITEM_RENDERER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), theMiniQuest.getQuestIcon(), guiLeft + X_SIZE / 2 + objWidth / 2 + iconB, iconY);
		GL11.glDisable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - X_SIZE) / 2;
		guiTop = (height - Y_SIZE) / 2;
		buttonAccept = new GOTGuiButton(0, guiLeft + X_SIZE / 2 - 20 - 80, guiTop + Y_SIZE - 30, 80, 20, StatCollector.translateToLocal("got.gui.miniquestOffer.accept"));
		buttonList.add(buttonAccept);
		buttonDecline = new GOTGuiButton(1, guiLeft + X_SIZE / 2 + 20, guiTop + Y_SIZE - 30, 80, 20, StatCollector.translateToLocal("got.gui.miniquestOffer.decline"));
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

	private void renderNPC(int i, int j, float dx, float dy, float f) {
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
					model = npcRenderer.getNpcRenderPassModel();
					model.isChild = theNPC.isChild();
					List<ModelRenderer> modelParts = model.boxList;
					boolean[] prevShowModels = new boolean[modelParts.size()];
					for (l = 0; l < modelParts.size(); ++l) {
						part = modelParts.get(l);
						prevShowModels[l] = part.showModel;
						boolean isHeadPart = recursiveCheckForModel(model.bipedHead, part) || recursiveCheckForModel(model.bipedHeadwear, part);
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
						part = modelParts.get(l);
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
						headYaw = MathHelper.sin(actionTick / slow) * 1.0471975511965976f;
						headPitch = (MathHelper.sin(actionTick / slow * 2.0f) + 1.0f) / 2.0f * -0.2617993877991494f;
						break;
					case LOOKING_UP:
						headYaw = 0.0f;
						headPitch = -0.3490658503988659f;
						break;
					case SHAKING:
						actionSlow += 0.01f;
						headYaw = MathHelper.sin(actionTick / actionSlow) * 0.5235987755982988f;
						headPitch += 0.006981317007977318f;
						break;
					case TALKING:
						if (actionTick % 20 == 0) {
							actionSlow = 0.7f + rand.nextFloat() * 1.5f;
						}
						float slow1 = actionSlow * 2.0f;
						headYaw = MathHelper.sin(actionTick / slow1) * 0.17453292519943295f;
						headPitch = (MathHelper.sin(actionTick / slow1 * 2.0f) + 1.0f) / 2.0f * -0.3490658503988659f;
						break;
				}
			}
		} else {
			headYaw = 0.0f;
			headPitch = MathHelper.sin(openTick * 0.07f) * 0.08726646259971647f;
		}
		++openTick;
	}

	private enum NPCAction {
		TALKING(1.0f), SHAKING(0.1f), LOOKING(0.3f), LOOKING_UP(0.4f);

		private static float totalWeight = -1.0f;

		private final float weight;

		NPCAction(float f) {
			weight = f;
		}

		private static NPCAction getRandomAction(Random rand) {
			if (totalWeight <= 0.0f) {
				totalWeight = 0.0f;
				for (NPCAction action : values()) {
					totalWeight += action.weight;
				}
			}
			float f = rand.nextFloat();
			f *= totalWeight;
			for (NPCAction action : values()) {
				f -= action.weight;
				if (f <= 0.0f) {
					return action;
				}
			}
			return null;
		}
	}
}