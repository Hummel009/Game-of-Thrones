package got.client.model;

import net.minecraft.client.model.ModelRenderer;

public class GOTModelYiTiHelmet extends GOTModelBiped {
	public GOTModelYiTiHelmet() {
		this(0.0f, false);
	}

	public GOTModelYiTiHelmet(float f, boolean bisonHorns) {
		super(f);
		if (bisonHorns) {
			textureWidth = 64;
			textureHeight = 64;
		} else {
			textureWidth = 64;
			textureHeight = 32;
		}
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(0, 16).addBox(-5.5f, -8.5f - f, -5.5f, 11, 2, 11, 0.0f);
		bipedHead.setTextureOffset(32, 8).addBox(-3.5f, -9.5f - f, -3.5f, 7, 1, 7, 0.0f);
		bipedHead.setTextureOffset(50, 16).addBox(0.0f, -10.5f - f, -4.5f - f, 0, 3, 4, 0.0f);
		ModelRenderer horn = new ModelRenderer(this, 44, 16);
		horn.setRotationPoint(0.0f, 0.0f, 0.0f);
		horn.addBox(-0.5f, -14.0f - f, -2.0f - f, 1, 8, 2, 0.0f);
		horn.rotateAngleX = 0.3490658503988659f;
		bipedHead.addChild(horn);
		bipedHead.setTextureOffset(24, 0).addBox(-1.0f, -8.0f - f, 4.0f + f, 2, 4, 1, 0.0f);
		bipedHead.setTextureOffset(32, 2).addBox(-6.0f, -12.0f - f, 5.0f + f, 12, 4, 0, 0.0f);
		ModelRenderer crest = new ModelRenderer(this, 32, 0);
		crest.setRotationPoint(0.0f, -12.0f - f, 5.0f + f);
		crest.addBox(-6.0f, -2.0f, 0.0f, 12, 2, 0, 0.0f);
		crest.rotateAngleX = 0.5235987755982988f;
		bipedHead.addChild(crest);
		if (bisonHorns) {
			ModelRenderer bisonHornRight = new ModelRenderer(this, 0, 32);
			bisonHornRight.setRotationPoint(-1.0f - f, -8.0f - f, 0.0f);
			bisonHornRight.addBox(-7.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
			ModelRenderer bisonHornRight1 = new ModelRenderer(this, 0, 38);
			bisonHornRight1.setRotationPoint(-7.0f, 0.0f, 0.0f);
			bisonHornRight1.addBox(-5.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
			ModelRenderer bisonHornRight2 = new ModelRenderer(this, 0, 42);
			bisonHornRight2.setRotationPoint(-5.0f, 0.0f, 0.0f);
			bisonHornRight2.addBox(-3.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
			ModelRenderer bisonHornLeft = new ModelRenderer(this, 0, 32);
			bisonHornLeft.mirror = true;
			bisonHornLeft.setRotationPoint(1.0f + f, -8.0f - f, 0.0f);
			bisonHornLeft.addBox(0.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
			ModelRenderer bisonHornLeft1 = new ModelRenderer(this, 0, 38);
			bisonHornLeft1.mirror = true;
			bisonHornLeft1.setRotationPoint(7.0f, 0.0f, 0.0f);
			bisonHornLeft1.addBox(-1.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
			ModelRenderer bisonHornLeft2 = new ModelRenderer(this, 0, 42);
			bisonHornLeft2.mirror = true;
			bisonHornLeft2.setRotationPoint(5.0f, 0.0f, 0.0f);
			bisonHornLeft2.addBox(-1.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
			bisonHornRight.rotateAngleZ = 0.6981317007977318f;
			bisonHornLeft.rotateAngleZ = -bisonHornRight.rotateAngleZ;
			bisonHornRight1.rotateAngleZ = -0.5235988f;
			bisonHornLeft1.rotateAngleZ = -bisonHornRight1.rotateAngleZ;
			bisonHornRight2.rotateAngleZ = -0.5235988f;
			bisonHornLeft2.rotateAngleZ = -bisonHornRight2.rotateAngleZ;
			bipedHead.addChild(bisonHornRight);
			bisonHornRight.addChild(bisonHornRight1);
			bisonHornRight1.addChild(bisonHornRight2);
			bipedHead.addChild(bisonHornLeft);
			bisonHornLeft.addChild(bisonHornLeft1);
			bisonHornLeft1.addChild(bisonHornLeft2);
		}
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
