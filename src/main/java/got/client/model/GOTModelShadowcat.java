package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GOTModelShadowcat extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer tail;

	public GOTModelShadowcat() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0f, -4.0f, -7.0f, 6, 5, 4);
		head.setRotationPoint(0.0f, 10.0f, -7.5f);
		head.setTextureSize(64, 64);
		head.mirror = true;
		setRotation(head, 0.0f, 0.0f, 0.0f);
		ModelRenderer snout = new ModelRenderer(this, 0, 9);
		snout.addBox(-1.5f, 0.0f, -2.0f, 3, 3, 2);
		snout.setRotationPoint(0.0f, 8.0f, -14.5f);
		snout.setTextureSize(64, 64);
		snout.mirror = true;
		setRotation(snout, 0.0f, 0.0f, 0.0f);
		convertToChild(head, snout);
		ModelRenderer neck = new ModelRenderer(this, 20, 0);
		neck.addBox(-2.5f, -2.5f, -5.0f, 5, 5, 5);
		neck.setRotationPoint(0.0f, 10.0f, -7.5f);
		neck.setTextureSize(64, 64);
		neck.mirror = true;
		setRotation(neck, -0.3141593f, 0.0f, 0.0f);
		convertToChild(head, neck);
		body1 = new ModelRenderer(this, 20, 10);
		body1.addBox(-3.5f, -4.0f, 0.0f, 7, 8, 8);
		body1.setRotationPoint(0.0f, 10.0f, -8.5f);
		body1.setTextureSize(64, 64);
		body1.mirror = true;
		setRotation(body1, -0.0349066f, 0.0f, 0.0f);
		body2 = new ModelRenderer(this, 20, 26);
		body2.addBox(-3.0f, -3.0f, 0.0f, 6, 7, 10);
		body2.setRotationPoint(0.0f, 10.5f, -1.5f);
		body2.setTextureSize(64, 64);
		body2.mirror = true;
		setRotation(body2, 0.0349066f, 0.0f, 0.0f);
		ModelRenderer ear1 = new ModelRenderer(this, 11, 11);
		ear1.addBox(-1.0f, -1.0f, 0.0f, 2, 1, 1);
		ear1.setRotationPoint(-2.0f, 6.0f, -12.5f);
		ear1.setTextureSize(64, 64);
		ear1.mirror = true;
		setRotation(ear1, 0.0f, 0.0f, 0.0f);
		convertToChild(head, ear1);
		ModelRenderer ear2 = new ModelRenderer(this, 11, 11);
		ear2.addBox(-1.0f, -1.0f, 0.0f, 2, 1, 1);
		ear2.setRotationPoint(2.0f, 6.0f, -12.5f);
		ear2.setTextureSize(64, 64);
		ear2.mirror = true;
		setRotation(ear2, 0.0f, 0.0f, 0.0f);
		convertToChild(head, ear2);
		leg1 = new ModelRenderer(this, 0, 14);
		leg1.addBox(-1.0f, 0.0f, -1.5f, 2, 10, 3);
		leg1.setRotationPoint(-1.9f, 14.0f, 6.0f);
		leg1.setTextureSize(64, 64);
		leg1.mirror = true;
		setRotation(leg1, 0.0f, 0.0f, 0.0f);
		leg2 = new ModelRenderer(this, 0, 14);
		leg2.addBox(-1.0f, 0.0f, -1.5f, 2, 10, 3);
		leg2.setRotationPoint(1.9f, 14.0f, 6.0f);
		leg2.setTextureSize(64, 64);
		leg2.mirror = true;
		setRotation(leg2, 0.0f, 0.0f, 0.0f);
		leg3 = new ModelRenderer(this, 0, 14);
		leg3.addBox(-1.0f, 0.0f, -1.5f, 2, 10, 3);
		leg3.setRotationPoint(-2.0f, 14.0f, -6.0f);
		leg3.setTextureSize(64, 64);
		leg3.mirror = true;
		setRotation(leg3, 0.0f, 0.0f, 0.0f);
		leg4 = new ModelRenderer(this, 0, 14);
		leg4.addBox(-1.0f, 0.0f, -1.5f, 2, 10, 3);
		leg4.setRotationPoint(2.0f, 14.0f, -6.0f);
		leg4.setTextureSize(64, 64);
		leg4.mirror = true;
		setRotation(leg4, 0.0f, 0.0f, 0.0f);
		tail = new ModelRenderer(this, 11, 14);
		tail.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2);
		tail.setRotationPoint(0.0f, 8.0f, 7.5f);
		tail.setTextureSize(64, 64);
		tail.mirror = true;
		setRotation(tail, 0.4363323f, 0.0f, 0.0f);
	}

	public static void convertToChild(ModelRenderer parent, ModelRenderer child) {
		// move child rotation point to be relative to parent
		child.rotationPointX -= parent.rotationPointX;
		child.rotationPointY -= parent.rotationPointY;
		child.rotationPointZ -= parent.rotationPointZ;
		// make rotations relative to parent
		child.rotateAngleX -= parent.rotateAngleX;
		child.rotateAngleY -= parent.rotateAngleY;
		child.rotateAngleZ -= parent.rotateAngleZ;
		// create relationship
		parent.addChild(child);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			GL11.glPushMatrix();
			float f6 = 2.0f;
			GL11.glScalef(1.3f / f6, 1.3f / f6, 1.3f / f6);
			GL11.glTranslatef(0.0f, 17.0f * f5, 2.0f * f5);
			head.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			body1.render(f5);
			body2.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			body1.render(f5);
			body2.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase living, float f1, float f2, float f3) {
		body1.rotationPointY = 13.0f;
		body2.rotationPointY = 13.0f;
		head.rotationPointY = 11.4f;
		tail.rotationPointY = 10.0f;
		leg1.rotationPointY = 14.0f;
		leg2.rotationPointY = 14.0f;
		leg3.rotationPointY = 14.0f;
		leg4.rotationPointY = 14.0f;
		leg1.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
		leg2.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float) Math.PI) * 1.4f * f2;
		leg3.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float) Math.PI) * 1.4f * f2;
		leg4.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.rotateAngleX = f4 / 57.295776f;
		head.rotateAngleY = f3 / 114.59155f;
		head.rotationPointZ = -7.2f;
		tail.rotateAngleZ = MathHelper.cos(f * 0.666f) * 0.5f * f1;
	}
}
