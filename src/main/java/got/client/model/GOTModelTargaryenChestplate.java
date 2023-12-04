package got.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class GOTModelTargaryenChestplate extends GOTModelBiped {

	public GOTModelTargaryenChestplate() {
		textureWidth = 64;
		textureHeight = 64;

		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 0, -4.0f, 0.0f, -2.0f, 8, 12, 4, 1.01f));

		ModelRenderer wings = new ModelRenderer(this);
		wings.setRotationPoint(0.0f, -3.0f, 0.0f);
		bipedBody.addChild(wings);

		ModelRenderer leftWing = new ModelRenderer(this);
		leftWing.setRotationPoint(0.0f, 5.0f, 3.5f);
		wings.addChild(leftWing);
		setRotationAngle(leftWing, 0.0f, 0.3927f, 0.0f);
		leftWing.cubeList.add(new ModelBox(leftWing, 10, 18, 3.0f, -9.0f, -1.5f, 0, 18, 11, 0.0f));

		ModelRenderer rightWing = new ModelRenderer(this);
		rightWing.setRotationPoint(0.0f, 5.0f, 3.5f);
		wings.addChild(rightWing);
		setRotationAngle(rightWing, 0.0f, -0.3927f, 0.0f);
		rightWing.cubeList.add(new ModelBox(rightWing, 10, 18, -3.0f, -9.0f, -1.5f, 0, 18, 11, 0.0f));

		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 24, 0, -3.0f, -2.0f, -2.0f, 4, 12, 4, 1.0f));

		ModelRenderer rightPauldron = new ModelRenderer(this);
		rightPauldron.setRotationPoint(-2.0f, -2.0f, 0.0f);
		bipedRightArm.addChild(rightPauldron);
		setRotationAngle(rightPauldron, -3.1416f, 0.0f, 2.8798f);
		rightPauldron.cubeList.add(new ModelBox(rightPauldron, 8, 17, -3.0f, -2.25f, -3.5f, 5, 2, 7, 0.0f));
		rightPauldron.cubeList.add(new ModelBox(rightPauldron, 32, 16, -3.0f, -5.0f, 0.0f, 8, 14, 0, 0.0f));

		ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedRightArm.addChild(bone2);

		ModelRenderer cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-2.5984f, 9.0f, 0.031f);
		bone2.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0f, -0.9599f, 0.0f);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 49, 18, -0.5f, -2.5f, 2.0f, 3, 7, 0, 0.0f));

		ModelRenderer cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-3.5f, 8.5f, -3.0f);
		bone2.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0f, 0.6109f, 0.0f);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 49, 18, -2.5f, -3.0f, 0.0f, 3, 7, 0, 0.0f));

		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 24, 0, -1.0f, -2.0f, -2.0f, 4, 12, 4, 1.0f));
		bipedLeftArm.mirror = true;

		ModelRenderer leftPauldron = new ModelRenderer(this);
		leftPauldron.setRotationPoint(0.0f, -2.0f, 0.0f);
		bipedLeftArm.addChild(leftPauldron);
		setRotationAngle(leftPauldron, 0.0f, 0.0f, 0.2618f);
		leftPauldron.cubeList.add(new ModelBox(leftPauldron, 8, 17, -1.0f, -2.25f, -3.5f, 5, 2, 7, 0.0f));
		leftPauldron.cubeList.add(new ModelBox(leftPauldron, 32, 16, -1.0f, -5.0f, 0.0f, 8, 14, 0, 0.0f));

		ModelRenderer bone = new ModelRenderer(this);
		bone.setRotationPoint(4.25f, 8.5f, -1.0f);
		bipedLeftArm.addChild(bone);
		setRotationAngle(bone, 0.0f, -0.7854f, 0.0f);
		bone.cubeList.add(new ModelBox(bone, 49, 18, -1.5f, -3.5f, 0.0f, 3, 7, 0, 0.0f));

		ModelRenderer cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(2.0f, 2.0f, 2.0f);
		bone.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0f, 1.1781f, 0.0f);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 49, 18, -3.5f, -3.5f, 0.5f, 3, 7, 0, 0.0f));
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}