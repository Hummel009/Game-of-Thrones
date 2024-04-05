package got.client.render.npc;

import got.client.model.GOTModelGiant;
import got.client.render.other.GOTNPCRendering;
import got.client.render.other.GOTRandomSkins;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityThrownRock;
import got.common.entity.other.GOTRandomSkinEntity;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTRenderGiant extends RenderLiving {
	public ResourceLocation weaponsTexture = new ResourceLocation("got:textures/entity/westeros/giant/weapons.png");
	public GOTModelGiant shirtModel = new GOTModelGiant(1.0f, 0);
	public GOTModelGiant trousersModel = new GOTModelGiant(0.75f, 1);
	public GOTEntityThrownRock heldRock;

	public String type;

	public GOTRenderGiant(String texture) {
		super(new GOTModelGiant(), 0.5f);
		type = texture;
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && ((GOTEntityNPC) entity).hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			GOTNPCRendering.renderHiredIcon(entity, d, d1 + 1.0, d2);
			GOTNPCRendering.renderNPCHealthBar(entity, d, d1 + 1.0, d2);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTRandomSkinEntity mob = (GOTRandomSkinEntity) entity;
		return GOTRandomSkins.loadSkinsList("got:textures/entity/westeros/" + type + "/giant").getRandomSkin(mob);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		GOTEntityGiant giant = (GOTEntityGiant) entity;
		scalegiant(giant, false);
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		super.renderEquippedItems(entity, f);
		GL11.glPushMatrix();
		bindTexture(weaponsTexture);
		rendergiantWeapon(entity, f);
		GL11.glPopMatrix();
	}

	public void rendergiantWeapon(EntityLivingBase entity, float f) {
		GOTEntityGiant giant = (GOTEntityGiant) entity;
		if (giant.isThrowingRocks()) {
			if (mainModel.onGround <= 0.0f) {
				if (heldRock == null) {
					heldRock = new GOTEntityThrownRock(giant.worldObj);
				}
				heldRock.setWorld(giant.worldObj);
				heldRock.setPosition(giant.posX, giant.posY, giant.posZ);
				((GOTModelGiant) mainModel).getRightArm().postRender(0.0625f);
				GL11.glTranslatef(0.375f, 1.5f, 0.0f);
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
				scalegiant(giant, true);
				renderManager.renderEntityWithPosYaw(heldRock, 0.0, 0.0, 0.0, 0.0f, f);
			}
		} else {
			((GOTModelGiant) mainModel).renderWoodenClubWithSpikes(0.0625f);
		}
	}

	@Override
	public void rotateCorpse(EntityLivingBase entity, float f, float f1, float f2) {

		super.rotateCorpse(entity, f, f1, f2);
	}

	public void scalegiant(GOTEntityGiant giant, boolean inverse) {
		float scale = 1.6f;
		if (inverse) {
			scale = 1.0f / scale;
		}
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0) {
			shirtModel.onGround = mainModel.onGround;
			setRenderPassModel(shirtModel);
			return 1;
		}
		if (pass == 1) {
			setRenderPassModel(trousersModel);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
