package got.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GOTModelBear extends ModelBase {
	private ModelRenderer head;
	private ModelRenderer nose;
	private ModelRenderer body;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;

	public GOTModelBear() {
		textureWidth = 128;
		textureHeight = 64;
		setHead(new ModelRenderer(this, 32, 0));
		getHead().setRotationPoint(0.0f, 8.0f, -9.0f);
		getHead().addBox(-4.0f, -5.0f, -4.0f, 8, 9, 6);
		getHead().setTextureOffset(0, 0).addBox(-4.5f, -5.5f, -11.0f, 9, 10, 7);
		nose = new ModelRenderer(this, 0, 17);
		nose.setRotationPoint(0.0f, 0.0f, 0.0f);
		nose.addBox(-2.5f, -2.0f, -17.0f, 5, 6, 6);
		nose.setTextureOffset(0, 29).addBox(-1.5f, -2.5f, -17.5f, 3, 3, 7);
		getHead().addChild(nose);
		ModelRenderer earRight = new ModelRenderer(this, 23, 17);
		earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
		earRight.addBox(-4.0f, -8.0f, -6.0f, 3, 3, 1);
		earRight.rotateAngleZ = (float) Math.toRadians(-15.0);
		getHead().addChild(earRight);
		ModelRenderer earLeft = new ModelRenderer(this, 23, 17);
		earLeft.mirror = true;
		earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
		earLeft.addBox(1.0f, -8.0f, -6.0f, 3, 3, 1);
		earLeft.rotateAngleZ = (float) Math.toRadians(15.0);
		getHead().addChild(earLeft);
		setBody(new ModelRenderer(this, 40, 0));
		getBody().setRotationPoint(0.0f, 10.0f, -2.0f);
		getBody().addBox(-6.0f, -8.0f, -9.0f, 12, 14, 28);
		getBody().setTextureOffset(92, 0).addBox(-2.5f, -6.0f, 19.0f, 5, 5, 2);
		setLeg1(new ModelRenderer(this, 56, 44));
		getLeg1().setRotationPoint(-4.0f, 6.0f, 10.0f);
		getLeg1().addBox(-6.0f, -2.0f, -3.5f, 6, 9, 9);
		getLeg1().setTextureOffset(86, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 11, 5);
		setLeg2(new ModelRenderer(this, 56, 44));
		getLeg2().mirror = true;
		getLeg2().setRotationPoint(4.0f, 6.0f, 10.0f);
		getLeg2().addBox(0.0f, -2.0f, -3.5f, 6, 9, 9);
		getLeg2().setTextureOffset(86, 44).addBox(0.5f, 7.0f, -1.5f, 5, 11, 5);
		setLeg3(new ModelRenderer(this, 0, 44));
		getLeg3().setRotationPoint(-3.0f, 6.0f, -5.0f);
		getLeg3().addBox(-6.0f, -2.0f, -3.0f, 6, 9, 8);
		getLeg3().setTextureOffset(28, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 12, 5);
		setLeg4(new ModelRenderer(this, 0, 44));
		getLeg4().mirror = true;
		getLeg4().setRotationPoint(3.0f, 6.0f, -5.0f);
		getLeg4().addBox(0.0f, -2.0f, -3.0f, 6, 9, 8);
		getLeg4().setTextureOffset(28, 44).addBox(0.5f, 7.0f, -1.5f, 5, 12, 5);
	}

	public ModelRenderer getBody() {
		return body;
	}

	public ModelRenderer getHead() {
		return head;
	}

	public ModelRenderer getLeg1() {
		return leg1;
	}

	public ModelRenderer getLeg2() {
		return leg2;
	}

	public ModelRenderer getLeg3() {
		return leg3;
	}

	public ModelRenderer getLeg4() {
		return leg4;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		getHead().render(f5);
		getBody().render(f5);
		getLeg1().render(f5);
		getLeg2().render(f5);
		getLeg3().render(f5);
		getLeg4().render(f5);
	}

	public void setBody(ModelRenderer body) {
		this.body = body;
	}

	public void setHead(ModelRenderer head) {
		this.head = head;
	}

	public void setLeg1(ModelRenderer leg1) {
		this.leg1 = leg1;
	}

	public void setLeg2(ModelRenderer leg2) {
		this.leg2 = leg2;
	}

	public void setLeg3(ModelRenderer leg3) {
		this.leg3 = leg3;
	}

	public void setLeg4(ModelRenderer leg4) {
		this.leg4 = leg4;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		getHead().rotateAngleX = (float) Math.toRadians(10.0);
		getHead().rotateAngleY = 0.0f;
		getHead().rotateAngleX += (float) Math.toRadians(f4);
		getHead().rotateAngleY += (float) Math.toRadians(f3);
		getLeg1().rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		getLeg2().rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		getLeg3().rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		getLeg4().rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		nose.rotationPointZ = isChild ? 3.0f : 0.0f;
	}
}
