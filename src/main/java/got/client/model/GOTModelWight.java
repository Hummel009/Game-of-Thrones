package got.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GOTModelWight extends GOTModelBiped {
	public GOTModelWight() {
		this(0.0f);
	}

	private GOTModelWight(float f) {
		super(f);
		textureWidth = 64;
		textureHeight = 64;
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, -8.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 1.0f);
		bipedBody = new ModelRenderer(this, 0, 16);
		bipedBody.setRotationPoint(0.0f, -8.0f, 0.0f);
		bipedBody.addBox(-4.0f, 0.0f, -3.0f, 8, 32, 6, f);
		bipedRightArm = new ModelRenderer(this, 28, 16);
		bipedRightArm.setRotationPoint(-6.0f, -5.0f, 0.0f);
		bipedRightArm.addBox(-3.0f, -2.0f, -2.5f, 5, 9, 5, f);
		bipedRightArm.setTextureOffset(28, 30).addBox(-2.0f, 7.0f, -1.5f, 3, 10, 3, f);
		bipedLeftArm = new ModelRenderer(this, 28, 16);
		bipedLeftArm.setRotationPoint(6.0f, -5.0f, 0.0f);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-2.0f, -2.0f, -2.5f, 5, 9, 5, f);
		bipedLeftArm.setTextureOffset(28, 30).addBox(-1.0f, 7.0f, -1.5f, 3, 10, 3, f);
		bipedHeadwear.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		f1 = 0.0f;
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedLeftArm.rotateAngleX = bipedRightArm.rotateAngleX;
		bipedLeftArm.rotateAngleY = -bipedRightArm.rotateAngleY;
		bipedLeftArm.rotateAngleZ = -bipedRightArm.rotateAngleZ;
	}
}
