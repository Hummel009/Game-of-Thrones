package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelDeerHelmet extends GOTModelBiped {
	public GOTModelDeerHelmet() {
		this(0.0f);
	}

	public GOTModelDeerHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(32, 0).addBox(-5.0f, -9.0f, 0.0f, 10, 6, 3, f);
		ModelRenderer crest = new ModelRenderer(this, 0, 16);
		crest.setRotationPoint(0.0f, -f, 0.0f);
		crest.addBox(-8.0f, -23.0f, 0.0f, 16, 14, 0, 0.0f);
		crest.rotateAngleX = -0.17453292519943295f;
		bipedHead.addChild(crest);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
