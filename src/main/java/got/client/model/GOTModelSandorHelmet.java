package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelSandorHelmet extends GOTModelBiped {
	private ModelRenderer panelRight;
	private ModelRenderer panelLeft;
	private ModelRenderer panelBack;
	private ModelRenderer panelTop;

	public GOTModelSandorHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.setTextureOffset(34, 16).addBox(-4.5f, -9.0f, -2.5f, 9, 2, 5, f);
		bipedHead.setTextureOffset(0, 17).addBox(-2.5f, -10.0f, -7.0f, 5, 3, 12, f);
		bipedHead.setTextureOffset(34, 23).addBox(-1.0f, -10.4f, -7.2f, 2, 2, 7, f);
		bipedHead.setTextureOffset(0, 0).addBox(-2.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
		bipedHead.mirror = true;
		bipedHead.addBox(1.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
		panelRight = new ModelRenderer(this, 32, 0);
		panelRight.addBox(-5.0f - f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
		panelRight.rotateAngleZ = (float) Math.toRadians(4.0);
		panelLeft = new ModelRenderer(this, 32, 0);
		panelLeft.mirror = true;
		panelLeft.addBox(5.0f + f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
		panelLeft.rotateAngleZ = (float) Math.toRadians(-4.0);
		panelBack = new ModelRenderer(this, 44, 0);
		panelBack.addBox(-4.0f, -8.0f, 4.8f + f, 8, 10, 0, 0.0f);
		panelBack.rotateAngleX = (float) Math.toRadians(4.0);
		panelTop = new ModelRenderer(this, 52, 25);
		panelTop.addBox(-2.5f, -16.0f - f, -2.0f, 5, 7, 0, 0.0f);
		panelTop.rotateAngleX = (float) Math.toRadians(-10.0);
		bipedHead.addChild(panelRight);
		bipedHead.addChild(panelLeft);
		bipedHead.addChild(panelBack);
		bipedHead.addChild(panelTop);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
