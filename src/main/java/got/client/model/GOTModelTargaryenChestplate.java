package got.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class GOTModelTargaryenChestplate extends GOTModelBiped {
	public ModelRenderer Wings;
	public ModelRenderer LeftWing;
	public ModelRenderer RightWing;
	public ModelRenderer RightPauldron;
	public ModelRenderer bone2;
	public ModelRenderer cube_r1;
	public ModelRenderer cube_r2;
	public ModelRenderer LeftPauldron;
	public ModelRenderer bone;
	public ModelRenderer cube_r3;

	public GOTModelTargaryenChestplate() {
		textureWidth = 64;
		textureHeight = 64;

		bipedBody = new ModelRenderer(this);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody.cubeList.add(new ModelBox(bipedBody, 0, 0, -4.0F, 0.0F, -2.0F, 8, 12, 4, 1.01F));

		Wings = new ModelRenderer(this);
		Wings.setRotationPoint(0.0F, -3.0F, 0.0F);
		bipedBody.addChild(Wings);

		LeftWing = new ModelRenderer(this);
		LeftWing.setRotationPoint(0.0F, 5.0F, 3.5F);
		Wings.addChild(LeftWing);
		setRotationAngle(LeftWing, 0.0F, 0.3927F, 0.0F);
		LeftWing.cubeList.add(new ModelBox(LeftWing, 10, 18, 3.0F, -9.0F, -1.5F, 0, 18, 11, 0.0F));

		RightWing = new ModelRenderer(this);
		RightWing.setRotationPoint(0.0F, 5.0F, 3.5F);
		Wings.addChild(RightWing);
		setRotationAngle(RightWing, 0.0F, -0.3927F, 0.0F);
		RightWing.cubeList.add(new ModelBox(RightWing, 10, 18, -3.0F, -9.0F, -1.5F, 0, 18, 11, 0.0F));

		bipedRightArm = new ModelRenderer(this);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedRightArm.cubeList.add(new ModelBox(bipedRightArm, 24, 0, -3.0F, -2.0F, -2.0F, 4, 12, 4, 1.0F));

		RightPauldron = new ModelRenderer(this);
		RightPauldron.setRotationPoint(-2.0F, -2.0F, 0.0F);
		bipedRightArm.addChild(RightPauldron);
		setRotationAngle(RightPauldron, -3.1416F, 0.0F, 2.8798F);
		RightPauldron.cubeList.add(new ModelBox(RightPauldron, 8, 17, -3.0F, -2.25F, -3.5F, 5, 2, 7, 0.0F));
		RightPauldron.cubeList.add(new ModelBox(RightPauldron, 32, 16, -3.0F, -5.0F, 0.0F, 8, 14, 0, 0.0F));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightArm.addChild(bone2);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-2.5984F, 9.0F, 0.031F);
		bone2.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -0.9599F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 49, 18, -0.5F, -2.5F, 2.0F, 3, 7, 0, 0.0F));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-3.5F, 8.5F, -3.0F);
		bone2.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.6109F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 49, 18, -2.5F, -3.0F, 0.0F, 3, 7, 0, 0.0F));

		bipedLeftArm = new ModelRenderer(this);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedLeftArm.cubeList.add(new ModelBox(bipedLeftArm, 24, 0, -1.0F, -2.0F, -2.0F, 4, 12, 4, 1.0F));
		bipedLeftArm.mirror = true;

		LeftPauldron = new ModelRenderer(this);
		LeftPauldron.setRotationPoint(0.0F, -2.0F, 0.0F);
		bipedLeftArm.addChild(LeftPauldron);
		setRotationAngle(LeftPauldron, 0.0F, 0.0F, 0.2618F);
		LeftPauldron.cubeList.add(new ModelBox(LeftPauldron, 8, 17, -1.0F, -2.25F, -3.5F, 5, 2, 7, 0.0F));
		LeftPauldron.cubeList.add(new ModelBox(LeftPauldron, 32, 16, -1.0F, -5.0F, 0.0F, 8, 14, 0, 0.0F));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(4.25F, 8.5F, -1.0F);
		bipedLeftArm.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 49, 18, -1.5F, -3.5F, 0.0F, 3, 7, 0, 0.0F));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(2.0F, 2.0F, 2.0F);
		bone.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 1.1781F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 49, 18, -3.5F, -3.5F, 0.5F, 3, 7, 0, 0.0F));
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}