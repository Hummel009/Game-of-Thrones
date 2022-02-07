package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelHarpy extends GOTModelBiped {
	public GOTModelHarpy(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		ModelRenderer hornRight = new ModelRenderer(this, 32, 0);
		hornRight.setRotationPoint(-f, -f, -f);
		hornRight.addBox(-7.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
		hornRight.rotateAngleZ = (float) Math.toRadians(6.0);
		ModelRenderer hornLeft = new ModelRenderer(this, 32, 0);
		hornLeft.setRotationPoint(f, -f, -f);
		hornLeft.mirror = true;
		hornLeft.addBox(4.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
		hornLeft.rotateAngleZ = (float) Math.toRadians(-6.0);
		bipedHead.addChild(hornRight);
		bipedHead.addChild(hornLeft);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
