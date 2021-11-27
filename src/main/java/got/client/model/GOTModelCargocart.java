package got.client.model;

import got.common.entity.other.GOTEntityCargocart;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class GOTModelCargocart extends ModelBase {
	public ModelRenderer boardb = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
	public ModelRenderer boardsl;
	public ModelRenderer boardsr;
	public ModelRenderer boardf;
	public ModelRenderer shaft;
	public ModelRenderer stickb;
	public ModelRenderer cargo;
	public ModelRenderer cargo1;
	public ModelRenderer cargo2;
	public ModelRenderer cargo3;
	public ModelRenderer leftWheel;
	public ModelRenderer rightWheel;

	public GOTModelCargocart() {
		ModelRenderer box;
		int i;
		boardb.addBox(-11.0f, 6.0f, -14.5f, 22, 1, 29);
		boardb.addBox(-12.5f, 4.0f, 0.0f, 25, 2, 2);
		shaft = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		shaft.rotateAngleX = 0.07f;
		shaft.addBox(-8.0f, 10.5f, -34.5f, 1, 2, 20);
		shaft.addBox(7.0f, 10.5f, -34.5f, 1, 2, 20);
		boardb.addChild(shaft);
		boardf = new ModelRenderer(this, 0, 30).setTextureSize(128, 128);
		boardf.addBox(-12.0f, 7.0f, -14.5f, 24, 10, 1);
		boardb.addChild(boardf);
		stickb = new ModelRenderer(this, 0, 4).setTextureSize(128, 128);
		stickb.addBox(-12.0f, 6.0f, 14.5f, 2, 11, 1);
		stickb.addBox(10.0f, 6.0f, 14.5f, 2, 11, 1);
		boardb.addChild(stickb);
		boardsl = new ModelRenderer(this, 0, 74).setTextureSize(128, 128);
		boardsl.addBox(-12.0f, 9.0f, -13.5f, 1, 3, 28);
		boardsl.addBox(-12.0f, 14.0f, -13.5f, 1, 3, 28);
		boardb.addChild(boardsl);
		boardsr = new ModelRenderer(this, 0, 74).setTextureSize(128, 128);
		boardsr.mirror = true;
		boardsr.addBox(11.0f, 9.0f, -13.5f, 1, 3, 28);
		boardsr.addBox(11.0f, 14.0f, -13.5f, 1, 3, 28);
		boardb.addChild(boardsr);
		cargo = new ModelRenderer(this, 4, 48).setTextureSize(128, 128);
		cargo.addBox(-10.5f, 7.0f, -12.5f, 8, 8, 8);
		cargo1 = new ModelRenderer(this, 29, 32).setTextureSize(128, 128);
		cargo1.addBox(-1.0f, 7.0f, -12.5f, 11, 10, 10);
		cargo2 = new ModelRenderer(this, 57, 74).setTextureSize(128, 128);
		cargo2.addBox(-10.5f, 7.0f, -1.5f, 20, 8, 10);
		cargo3 = new ModelRenderer(this, 36, 52).setTextureSize(128, 128);
		cargo3.setRotationPoint(-9.5f, 15.0f, -10.5f);
		cargo3.addBox(0.0f, 0.0f, 0.0f, 3, 2, 20);
		cargo3.rotateAngleY = 0.35f;
		leftWheel = new ModelRenderer(this, 0, 42).setTextureSize(128, 128);
		leftWheel.setRotationPoint(-14.5f, 5.0f, 1.0f);
		leftWheel.addBox(1.0f, -1.0f, -1.0f, 1, 2, 2);
		for (i = 0; i < 8; ++i) {
			box = new ModelRenderer(this, 0, 42).setTextureSize(128, 128);
			box.addBox(0.5f, 1.0f, -0.5f, 1, 9, 1);
			box.addBox(0.5f, 9.86f, -4.5f, 1, 1, 9);
			box.rotateAngleX = i * 0.7853981f;
			leftWheel.addChild(box);
		}
		rightWheel = new ModelRenderer(this, 0, 42).setTextureSize(128, 128);
		rightWheel.mirror = true;
		rightWheel.setRotationPoint(13.5f, 5.0f, 1.0f);
		rightWheel.addBox(-1.0f, -1.0f, -1.0f, 1, 2, 2);
		for (i = 0; i < 8; ++i) {
			box = new ModelRenderer(this, 0, 42).setTextureSize(128, 128);
			box.mirror = true;
			box.addBox(-0.5f, 1.0f, -0.5f, 1, 9, 1);
			box.addBox(-0.5f, 9.86f, -4.5f, 1, 1, 9);
			box.rotateAngleX = i * 0.7853981f;
			rightWheel.addChild(box);
		}
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
		boardb.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
		leftWheel.rotateAngleX = ((GOTEntityCargocart) entity).getWheelRotation();
		rightWheel.rotateAngleX = ((GOTEntityCargocart) entity).getWheelRotation();
		leftWheel.render(scale);
		rightWheel.render(scale);
		if (((GOTEntityCargocart) entity).load > 3) {
			cargo.render(scale);
			if (((GOTEntityCargocart) entity).load > 8) {
				cargo1.render(scale);
				if (((GOTEntityCargocart) entity).load > 16) {
					cargo2.render(scale);
					if (((GOTEntityCargocart) entity).load > 31) {
						cargo3.render(scale);
					}
				}
			}
		}
	}
}
