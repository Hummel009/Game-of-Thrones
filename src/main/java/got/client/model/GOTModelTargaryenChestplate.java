package got.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class GOTModelTargaryenChestplate extends GOTModelBiped {
	public GOTModelTargaryenChestplate() {
		textureWidth = 64;
		textureHeight = 64;
		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 0, -4.0F, 0.0F, -2.0F, 8, 12, 4, 1.01F));
		ModelRenderer wings = new ModelRenderer(this);
		wings.setRotationPoint(0.0F, -3.0F, 0.0F);
		bipedBody.addChild(wings);
		ModelRenderer leftWing = new ModelRenderer(this);
		leftWing.setRotationPoint(0.0F, 5.0F, 3.5F);
		wings.addChild(leftWing);
		setRotationAngle(leftWing, 0.0F, 0.3927F, 0.0F);
		leftWing.cubeList.add(new ModelBox(leftWing, 10, 18, 3.0F, -9.0F, -1.5F, 0, 18, 11, 0.0F));
		ModelRenderer rightWing = new ModelRenderer(this);
		rightWing.setRotationPoint(0.0F, 5.0F, 3.5F);
		wings.addChild(rightWing);
		setRotationAngle(rightWing, 0.0F, -0.3927F, 0.0F);
		rightWing.cubeList.add(new ModelBox(rightWing, 10, 18, -3.0F, -9.0F, -1.5F, 0, 18, 11, 0.0F));
		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 24, 0, -3.0F, -2.0F, -2.0F, 4, 12, 4, 1.0F));
		ModelRenderer rightPauldron = new ModelRenderer(this);
		rightPauldron.setRotationPoint(-2.0F, -2.0F, 0.0F);
		bipedRightArm.addChild(rightPauldron);
		setRotationAngle(rightPauldron, -3.1416F, 0.0F, 2.8798F);
		rightPauldron.cubeList.add(new ModelBox(rightPauldron, 8, 17, -3.0F, -2.25F, -3.5F, 5, 2, 7, 0.0F));
		rightPauldron.cubeList.add(new ModelBox(rightPauldron, 32, 16, -3.0F, -5.0F, 0.0F, 8, 14, 0, 0.0F));
		ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightArm.addChild(bone2);
		ModelRenderer cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-2.5984F, 9.0F, 0.031F);
		bone2.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -0.9599F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 49, 18, -0.5F, -2.5F, 2.0F, 3, 7, 0, 0.0F));
		ModelRenderer cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-3.5F, 8.5F, -3.0F);
		bone2.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.6109F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 49, 18, -2.5F, -3.0F, 0.0F, 3, 7, 0, 0.0F));
		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 24, 0, -1.0F, -2.0F, -2.0F, 4, 12, 4, 1.0F));
		bipedLeftArm.mirror = true;
		ModelRenderer leftPauldron = new ModelRenderer(this);
		leftPauldron.setRotationPoint(0.0F, -2.0F, 0.0F);
		bipedLeftArm.addChild(leftPauldron);
		setRotationAngle(leftPauldron, 0.0F, 0.0F, 0.2618F);
		leftPauldron.cubeList.add(new ModelBox(leftPauldron, 8, 17, -1.0F, -2.25F, -3.5F, 5, 2, 7, 0.0F));
		leftPauldron.cubeList.add(new ModelBox(leftPauldron, 32, 16, -1.0F, -5.0F, 0.0F, 8, 14, 0, 0.0F));
		ModelRenderer bone = new ModelRenderer(this);
		bone.setRotationPoint(4.25F, 8.5F, -1.0F);
		bipedLeftArm.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 49, 18, -1.5F, -3.5F, 0.0F, 3, 7, 0, 0.0F));
		ModelRenderer cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(2.0F, 2.0F, 2.0F);
		bone.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 1.1781F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 49, 18, -3.5F, -3.5F, 0.5F, 3, 7, 0, 0.0F));
	}

	private static void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}