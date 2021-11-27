package got.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GOTModelReachHelmet extends GOTModelBiped {
	public ModelRenderer[] manes;

	public GOTModelReachHelmet() {
		this(0.0f);
	}

	public GOTModelReachHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(0, 16).addBox(-1.0f, -11.5f - f, -4.5f - f, 2, 7, 6, 0.0f);
		manes = new ModelRenderer[3];
		for (int i = 0; i < manes.length; ++i) {
			ModelRenderer mane;
			manes[i] = mane = new ModelRenderer(this, 32, 0);
			mane.setRotationPoint(0.0f, -f, f);
			mane.addBox(0.0f, -11.0f, -1.0f, 0, 14, 12, 0.0f);
			bipedHead.addChild(mane);
		}
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float mid = manes.length / 2.0f - 0.5f;
		for (int i = 0; i < manes.length; ++i) {
			ModelRenderer mane = manes[i];
			mane.rotateAngleX = (mid - Math.abs(i - mid)) / mid * 0.22f;
			mane.rotateAngleY = (i - mid) / mid * 0.17f;
			mane.rotateAngleX += MathHelper.sin(f * 0.4f) * f1 * 0.2f;
		}
	}
}
