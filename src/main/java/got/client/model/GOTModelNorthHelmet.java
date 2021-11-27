package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelNorthHelmet extends GOTModelBiped {
	public GOTModelNorthHelmet() {
		this(0.0f);
	}

	public GOTModelNorthHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(0, 16).addBox(-1.5f, -9.0f, -3.5f, 3, 1, 7, f);
		bipedHead.setTextureOffset(20, 16).addBox(-0.5f, -10.0f, -3.5f, 1, 1, 7, f);
		bipedHead.setTextureOffset(24, 0).addBox(-1.5f, -10.5f - f, -4.5f - f, 3, 4, 1, 0.0f);
		bipedHead.setTextureOffset(24, 5).addBox(-0.5f, -11.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
		bipedHead.setTextureOffset(28, 5).addBox(-0.5f, -6.5f - f, -4.5f - f, 1, 1, 1, 0.0f);
		bipedHead.setTextureOffset(32, 0).addBox(-1.5f, -9.5f - f, 3.5f + f, 3, 3, 1, 0.0f);
		bipedHead.setTextureOffset(32, 4).addBox(-0.5f, -10.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
		bipedHead.setTextureOffset(36, 4).addBox(-0.5f, -6.5f - f, 3.5f + f, 1, 1, 1, 0.0f);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
