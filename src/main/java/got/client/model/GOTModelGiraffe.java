package got.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class GOTModelGiraffe extends ModelBase {
	private ModelRenderer body = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
	private ModelRenderer neck;
	private ModelRenderer head;
	private ModelRenderer tail;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;

	public GOTModelGiraffe(float f) {
		getBody().addBox(-6.0f, -8.0f, -13.0f, 12, 16, 26, f);
		getBody().setRotationPoint(0.0f, -11.0f, 0.0f);
		setNeck(new ModelRenderer(this, 0, 44).setTextureSize(128, 64));
		getNeck().addBox(-4.5f, -13.0f, -4.5f, 9, 11, 9, f);
		getNeck().setTextureOffset(78, 0).addBox(-3.0f, -37.0f, -3.0f, 6, 40, 6, f);
		getNeck().setRotationPoint(0.0f, -14.0f, -7.0f);
		setHead(new ModelRenderer(this, 96, 48).setTextureSize(128, 64));
		getHead().addBox(-3.0f, -43.0f, -6.0f, 6, 6, 10, f);
		getHead().setTextureOffset(10, 0).addBox(-4.0f, -45.0f, 1.5f, 1, 3, 2, f);
		getHead().setTextureOffset(17, 0).addBox(3.0f, -45.0f, 1.5f, 1, 3, 2, f);
		getHead().setTextureOffset(0, 0).addBox(-2.5f, -47.0f, 0.0f, 1, 4, 1, f);
		getHead().setTextureOffset(5, 0).addBox(1.5f, -47.0f, 0.0f, 1, 4, 1, f);
		getHead().setTextureOffset(76, 56).addBox(-2.0f, -41.0f, -11.0f, 4, 3, 5, f);
		getHead().setRotationPoint(0.0f, -14.0f, -7.0f);
		setTail(new ModelRenderer(this, 104, 0).setTextureSize(128, 64));
		getTail().addBox(-0.5f, 0.0f, 0.0f, 1, 24, 1, f);
		getTail().setRotationPoint(0.0f, -12.0f, 13.0f);
		setLeg1(new ModelRenderer(this, 112, 0).setTextureSize(128, 64));
		getLeg1().addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		getLeg1().setRotationPoint(-3.9f, -3.0f, 8.0f);
		setLeg2(new ModelRenderer(this, 112, 0).setTextureSize(128, 64));
		getLeg2().addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		getLeg2().setRotationPoint(3.9f, -3.0f, 8.0f);
		getLeg2().mirror = true;
		setLeg3(new ModelRenderer(this, 112, 0).setTextureSize(128, 64));
		getLeg3().addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		getLeg3().setRotationPoint(-3.9f, -3.0f, -7.0f);
		setLeg4(new ModelRenderer(this, 112, 0).setTextureSize(128, 64));
		getLeg4().addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		getLeg4().setRotationPoint(3.9f, -3.0f, -7.0f);
		getLeg4().mirror = true;
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

	public ModelRenderer getNeck() {
		return neck;
	}

	public ModelRenderer getTail() {
		return tail;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (entity.riddenByEntity instanceof EntityPlayer) {
			setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
		} else {
			setDefaultHeadNeckRotation(f, f1, f2, f3, f4, f5);
		}
		getHead().render(f5);
		getBody().render(f5);
		getNeck().render(f5);
		getLeg1().render(f5);
		getLeg2().render(f5);
		getLeg3().render(f5);
		getLeg4().render(f5);
		getTail().render(f5);
	}

	public void setBody(ModelRenderer body) {
		this.body = body;
	}

	public void setDefaultHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
		getHead().setRotationPoint(0.0f, -14.0f, -7.0f);
		getNeck().rotateAngleX = 0.17453294f + f4 / 57.29578f;
		getHead().rotateAngleX = 0.17453294f + f4 / 57.29578f;
		getNeck().rotateAngleY = f3 / 57.29578f;
		getHead().rotateAngleY = f3 / 57.29578f;
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

	public void setNeck(ModelRenderer neck) {
		this.neck = neck;
	}

	public void setRiddenHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
		getHead().setRotationPoint(0.0f, 25.0f, -48.0f);
		getNeck().rotateAngleX = 1.5707964f;
		getNeck().rotateAngleY = 0.0f;
		getHead().rotateAngleX = 0.0f;
		getHead().rotateAngleY = 0.0f;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		getLeg1().rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		getLeg2().rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		getLeg3().rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		getLeg4().rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		getTail().rotateAngleZ = 0.2f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
	}

	public void setTail(ModelRenderer tail) {
		this.tail = tail;
	}
}
