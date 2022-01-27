package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelWingedHelmet extends GOTModelBiped {
	public GOTModelWingedHelmet() {
		this(0.0f);
	}

	public GOTModelWingedHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		ModelRenderer wingLeft = new ModelRenderer(this, 33, 0);
		wingLeft.setRotationPoint(-4.0f - f, -8.0f - f, 0.0f);
		wingLeft.addBox(-6.0f, -6.0f, 0.0f, 6, 16, 0, 0.0f);
		wingLeft.rotateAngleY = (float) Math.toRadians(25.0);
		bipedHead.addChild(wingLeft);
		ModelRenderer wingRight = new ModelRenderer(this, 33, 0);
		wingRight.mirror = true;
		wingRight.setRotationPoint(4.0f + f, -8.0f - f, 0.0f);
		wingRight.addBox(0.0f, -6.0f, 0.0f, 6, 16, 0, 0.0f);
		wingRight.rotateAngleY = (float) Math.toRadians(-25.0);
		bipedHead.addChild(wingRight);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
