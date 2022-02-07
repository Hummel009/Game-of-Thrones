package got.client.model;

import got.common.entity.other.GOTEntityPlowcart;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class GOTModelPlowcart extends ModelBase {
	private ModelRenderer axis;
	private ModelRenderer[] triangle = new ModelRenderer[3];
	private ModelRenderer shaft;
	private ModelRenderer shaftConnector;
	private ModelRenderer plowShaftUpper;
	private ModelRenderer plowShaftLower;
	private ModelRenderer plowBlade;
	private ModelRenderer plowHandle;
	private ModelRenderer plowHandleGrip;
	private ModelRenderer leftWheel;
	private ModelRenderer rightWheel;

	public GOTModelPlowcart() {
		ModelRenderer rim;
		ModelRenderer spoke;
		int i;
		axis = new ModelRenderer(this, 0, 0);
		axis.addBox(-12.5f, 4.0f, 0.0f, 25, 2, 2);
		triangle[0] = new ModelRenderer(this, 0, 4);
		triangle[0].addBox(-7.5f, -9.0f, 0.0f, 15, 2, 2);
		triangle[1] = new ModelRenderer(this, 8, 11);
		triangle[1].addBox(-5.0f, -9.0f, 0.0f, 2, 14, 2);
		triangle[1].rotateAngleZ = -0.175f;
		triangle[2] = new ModelRenderer(this, 8, 11);
		triangle[2].addBox(3.0f, -9.0f, 0.0f, 2, 14, 2);
		triangle[2].rotateAngleZ = 0.175f;
		triangle[2].mirror = true;
		shaft = new ModelRenderer(this, 0, 8);
		shaft.setRotationPoint(0.0f, 0.0f, -14.0f);
		shaft.rotateAngleY = 1.5707964f;
		shaft.rotateAngleZ = -0.07f;
		shaft.addBox(0.0f, 0.0f, -8.0f, 20, 2, 1);
		shaft.addBox(0.0f, 0.0f, 7.0f, 20, 2, 1);
		shaftConnector = new ModelRenderer(this, 0, 27);
		shaftConnector.setRotationPoint(0.0f, 0.0f, -14.0f);
		shaftConnector.rotateAngleY = 1.5707964f;
		shaftConnector.rotateAngleZ = -0.26f;
		shaftConnector.addBox(-16.0f, 0.0f, -8.0f, 16, 2, 1);
		shaftConnector.addBox(-16.0f, 0.0f, 7.0f, 16, 2, 1);
		plowShaftUpper = new ModelRenderer(this, 56, 0);
		plowShaftUpper.addBox(-1.0f, -2.0f, -2.0f, 2, 30, 2);
		plowShaftUpper.setRotationPoint(0.0f, -7.0f, 0.0f);
		plowShaftLower = new ModelRenderer(this, 42, 4);
		plowShaftLower.addBox(-1.0f, -0.7f, -0.7f, 2, 10, 2);
		plowShaftLower.setRotationPoint(0.0f, 28.0f, -1.0f);
		plowShaftLower.rotateAngleX = 0.7853982f;
		plowShaftUpper.addChild(plowShaftLower);
		plowBlade = new ModelRenderer(this, 16, 11);
		plowBlade.addBox(0.0f, -4.0f, -0.5f, 8, 15, 1);
		plowBlade.rotateAngleY = 1.5707964f;
		plowShaftLower.addChild(plowBlade);
		plowHandle = new ModelRenderer(this, 50, 4);
		plowHandle.addBox(-0.5f, 0.0f, -0.5f, 1, 18, 1);
		plowHandle.setRotationPoint(0.0f, 33.0f, 5.0f);
		plowHandle.rotateAngleX = 1.5707964f;
		plowShaftUpper.addChild(plowHandle);
		plowHandleGrip = new ModelRenderer(this, 50, 23);
		plowHandleGrip.addBox(-0.5f, 0.0f, -1.0f, 1, 5, 1);
		plowHandleGrip.setRotationPoint(0.0f, 32.8f, 21.0f);
		plowHandleGrip.rotateAngleX = 0.7853982f;
		plowShaftUpper.addChild(plowHandleGrip);
		leftWheel = new ModelRenderer(this, 34, 4);
		leftWheel.setRotationPoint(14.5f, 5.0f, 1.0f);
		leftWheel.addBox(-2.0f, -1.0f, -1.0f, 1, 2, 2);
		for (i = 0; i < 8; ++i) {
			rim = new ModelRenderer(this, 0, 11);
			rim.addBox(-1.5f, -4.5f, 9.86f, 1, 9, 1);
			rim.rotateAngleX = i * 0.7853982f;
			leftWheel.addChild(rim);
			spoke = new ModelRenderer(this, 4, 11);
			spoke.addBox(-1.5f, 1.0f, -0.5f, 1, 9, 1);
			spoke.rotateAngleX = i * 0.7853982f;
			leftWheel.addChild(spoke);
		}
		rightWheel = new ModelRenderer(this, 34, 4);
		rightWheel.mirror = true;
		rightWheel.setRotationPoint(-14.5f, 5.0f, 1.0f);
		rightWheel.addBox(1.0f, -1.0f, -1.0f, 1, 2, 2);
		for (i = 0; i < 8; ++i) {
			rim = new ModelRenderer(this, 0, 11);
			rim.addBox(0.5f, -4.5f, 9.86f, 1, 9, 1);
			rim.rotateAngleX = i * 0.7853982f;
			rightWheel.addChild(rim);
			spoke = new ModelRenderer(this, 4, 11);
			spoke.addBox(0.5f, 1.0f, -0.5f, 1, 9, 1);
			spoke.rotateAngleX = i * 0.7853982f;
			rightWheel.addChild(spoke);
		}
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
		axis.render(scale);
		shaft.renderWithRotation(scale);
		shaftConnector.renderWithRotation(scale);
		for (int i = 0; i < 3; ++i) {
			triangle[i].render(scale);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
		leftWheel.rotateAngleX = ((GOTEntityPlowcart) entity).getWheelRotation();
		rightWheel.rotateAngleX = ((GOTEntityPlowcart) entity).getWheelRotation();
		leftWheel.render(scale);
		rightWheel.render(scale);
		plowShaftUpper.rotateAngleX = ((GOTEntityPlowcart) entity).getPlowing() ? 0.7853982f : 1.2566371f;
		plowShaftUpper.render(scale);
	}
}
