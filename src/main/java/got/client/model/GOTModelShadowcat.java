package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GOTModelShadowcat extends ModelBase {
	// fields
	ModelRenderer head;
	ModelRenderer snout;
	ModelRenderer neck;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer ear1;
	ModelRenderer ear2;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer tail;

	public GOTModelShadowcat() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3F, -4F, -7F, 6, 5, 4);
		head.setRotationPoint(0F, 10F, -7.5F);
		head.setTextureSize(64, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		snout = new ModelRenderer(this, 0, 9);
		snout.addBox(-1.5F, 0F, -2F, 3, 3, 2);
		snout.setRotationPoint(0F, 8F, -14.5F);
		snout.setTextureSize(64, 64);
		snout.mirror = true;
		setRotation(snout, 0F, 0F, 0F);
		convertToChild(head, snout);
		neck = new ModelRenderer(this, 20, 0);
		neck.addBox(-2.5F, -2.5F, -5F, 5, 5, 5);
		neck.setRotationPoint(0F, 10F, -7.5F);
		neck.setTextureSize(64, 64);
		neck.mirror = true;
		setRotation(neck, -0.3141593F, 0F, 0F);
		convertToChild(head, neck);
		body1 = new ModelRenderer(this, 20, 10);
		body1.addBox(-3.5F, -4F, 0F, 7, 8, 8);
		body1.setRotationPoint(0F, 10F, -8.5F);
		body1.setTextureSize(64, 64);
		body1.mirror = true;
		setRotation(body1, -0.0349066F, 0F, 0F);
		body2 = new ModelRenderer(this, 20, 26);
		body2.addBox(-3F, -3F, 0F, 6, 7, 10);
		body2.setRotationPoint(0F, 10.5F, -1.5F);
		body2.setTextureSize(64, 64);
		body2.mirror = true;
		setRotation(body2, 0.0349066F, 0F, 0F);
		ear1 = new ModelRenderer(this, 11, 11);
		ear1.addBox(-1F, -1F, 0F, 2, 1, 1);
		ear1.setRotationPoint(-2F, 6F, -12.5F);
		ear1.setTextureSize(64, 64);
		ear1.mirror = true;
		setRotation(ear1, 0F, 0F, 0F);
		convertToChild(head, ear1);
		ear2 = new ModelRenderer(this, 11, 11);
		ear2.addBox(-1F, -1F, 0F, 2, 1, 1);
		ear2.setRotationPoint(2F, 6F, -12.5F);
		ear2.setTextureSize(64, 64);
		ear2.mirror = true;
		setRotation(ear2, 0F, 0F, 0F);
		convertToChild(head, ear2);
		leg1 = new ModelRenderer(this, 0, 14);
		leg1.addBox(-1F, 0F, -1.5F, 2, 10, 3);
		leg1.setRotationPoint(-1.9F, 14F, 6F);
		leg1.setTextureSize(64, 64);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 14);
		leg2.addBox(-1F, 0F, -1.5F, 2, 10, 3);
		leg2.setRotationPoint(1.9F, 14F, 6F);
		leg2.setTextureSize(64, 64);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 14);
		leg3.addBox(-1F, 0F, -1.5F, 2, 10, 3);
		leg3.setRotationPoint(-2F, 14F, -6F);
		leg3.setTextureSize(64, 64);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 14);
		leg4.addBox(-1F, 0F, -1.5F, 2, 10, 3);
		leg4.setRotationPoint(2F, 14F, -6F);
		leg4.setTextureSize(64, 64);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		tail = new ModelRenderer(this, 11, 14);
		tail.addBox(-1F, 0F, -1F, 2, 12, 2);
		tail.setRotationPoint(0F, 8F, 7.5F);
		tail.setTextureSize(64, 64);
		tail.mirror = true;
		setRotation(tail, 0.4363323F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.3F / f6, 1.3F / f6, 1.3F / f6);
			GL11.glTranslatef(0.0F, 17.0F * f5, 2.0F * f5);
			head.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
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
		body1.rotationPointY = 13.0F;
		body2.rotationPointY = 13.0F;
		head.rotationPointY = 11.4F;
		tail.rotationPointY = 10.0F;
		leg1.rotationPointY = 14.0F;
		leg2.rotationPointY = 14.0F;
		leg3.rotationPointY = 14.0F;
		leg4.rotationPointY = 14.0F;
		leg1.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		leg2.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
		leg3.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
		leg4.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.rotateAngleX = f4 / (180F / (float) Math.PI);
		head.rotateAngleY = f3 / (360F / (float) Math.PI);
		head.rotationPointZ = -7.2F;
		tail.rotateAngleZ = MathHelper.cos(f * 0.666F) * 0.5F * f1;
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
}
