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
	private static final ResourceLocation WEAPONS_TEXTURE = new ResourceLocation("got:textures/entity/westeros/giant/weapons.png");
	private static final GOTModelGiant SHIRT_MODEL = new GOTModelGiant(1.0f, 0);
	private static final GOTModelGiant TROUSERS_MODEL = new GOTModelGiant(0.75f, 1);

	private final String type;

	private GOTEntityThrownRock heldRock;

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
		scaleGiant(false);
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		super.renderEquippedItems(entity, f);
		GL11.glPushMatrix();
		bindTexture(WEAPONS_TEXTURE);
		rendergiantWeapon(entity, f);
		GL11.glPopMatrix();
	}

	private void rendergiantWeapon(EntityLivingBase entity, float f) {
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
				scaleGiant(true);
				renderManager.renderEntityWithPosYaw(heldRock, 0.0, 0.0, 0.0, 0.0f, f);
			}
		} else {
			((GOTModelGiant) mainModel).renderWoodenClubWithSpikes(0.0625f);
		}
	}

	private void scaleGiant(boolean inverse) {
		float scale = 1.6f;
		if (inverse) {
			scale = 1.0f / scale;
		}
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0) {
			SHIRT_MODEL.onGround = mainModel.onGround;
			setRenderPassModel(SHIRT_MODEL);
			return 1;
		}
		if (pass == 1) {
			setRenderPassModel(TROUSERS_MODEL);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}