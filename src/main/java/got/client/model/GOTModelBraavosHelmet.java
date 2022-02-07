package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelBraavosHelmet extends GOTModelBiped {
	public GOTModelBraavosHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHeadwear = new ModelRenderer(this, 32, 0);
		bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
		bipedHead.setTextureOffset(0, 0);
		bipedHead.addBox(-0.5f, -11.0f - f, -3.0f, 1, 3, 1, 0.0f);
		bipedHead.addBox(-0.5f, -10.0f - f, 2.0f, 1, 2, 1, 0.0f);
		bipedHead.setTextureOffset(0, 16).addBox(0.0f, -13.0f - f, -6.0f, 0, 4, 12, 0.0f);
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
