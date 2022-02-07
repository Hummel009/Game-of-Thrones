package got.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class GOTModelLion extends ModelBase {
	private ModelRenderer head;
	private ModelRenderer mane;
	private ModelRenderer body;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer tail;

	public GOTModelLion() {
		textureWidth = 128;
		textureHeight = 64;
		setHead(new ModelRenderer(this, 48, 0));
		getHead().setRotationPoint(0.0f, 3.0f, -10.0f);
		getHead().addBox(-5.0f, -6.0f, -10.0f, 10, 10, 10);
		getHead().setTextureOffset(78, 0).addBox(-3.0f, -1.0f, -14.0f, 6, 5, 4);
		getHead().setTextureOffset(98, 0).addBox(-1.0f, -2.0f, -14.2f, 2, 2, 5);
		getHead().setTextureOffset(0, 0);
		getHead().addBox(-4.0f, -9.0f, -7.5f, 3, 3, 1);
		getHead().mirror = true;
		getHead().addBox(1.0f, -9.0f, -7.5f, 3, 3, 1);
		setMane(new ModelRenderer(this, 0, 0));
		getMane().setRotationPoint(0.0f, 3.0f, -10.0f);
		getMane().addBox(-8.0f, -10.0f, -6.0f, 16, 16, 8);
		setBody(new ModelRenderer(this, 0, 24));
		getBody().setRotationPoint(0.0f, 6.0f, 1.0f);
		getBody().addBox(-7.0f, -6.5f, -11.0f, 14, 14, 24);
		setLeg1(new ModelRenderer(this, 52, 24));
		getLeg1().setRotationPoint(-4.0f, 4.0f, 11.0f);
		getLeg1().addBox(-6.0f, -2.0f, -3.5f, 6, 10, 8);
		getLeg1().setTextureOffset(106, 24).addBox(-5.5f, 8.0f, -2.5f, 5, 12, 5);
		setLeg2(new ModelRenderer(this, 52, 24));
		getLeg2().setRotationPoint(4.0f, 4.0f, 11.0f);
		getLeg2().mirror = true;
		getLeg2().addBox(0.0f, -2.0f, -3.5f, 6, 10, 8);
		getLeg2().setTextureOffset(106, 24).addBox(0.5f, 8.0f, -2.5f, 5, 12, 5);
		setLeg3(new ModelRenderer(this, 80, 24));
		getLeg3().setRotationPoint(-4.0f, 5.0f, -5.0f);
		getLeg3().addBox(-6.0f, -2.0f, -3.5f, 6, 9, 7);
		getLeg3().setTextureOffset(106, 24).addBox(-5.5f, 7.0f, -2.5f, 5, 12, 5);
		setLeg4(new ModelRenderer(this, 80, 24));
		getLeg4().setRotationPoint(4.0f, 5.0f, -5.0f);
		getLeg4().mirror = true;
		getLeg4().addBox(0.0f, -2.0f, -3.5f, 6, 9, 7);
		getLeg4().setTextureOffset(106, 24).addBox(0.5f, 7.0f, -2.5f, 5, 12, 5);
		setTail(new ModelRenderer(this, 100, 50));
		getTail().setRotationPoint(0.0f, 4.0f, 13.0f);
		getTail().addBox(-1.0f, -1.0f, 0.0f, 2, 2, 12);
		getTail().setTextureOffset(86, 57).addBox(-1.5f, -1.5f, 12.0f, 3, 3, 4);
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

	public ModelRenderer getMane() {
		return mane;
	}

	public ModelRenderer getTail() {
		return tail;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		getHead().render(f5);
		getMane().render(f5);
		getBody().render(f5);
		getLeg1().render(f5);
		getLeg2().render(f5);
		getLeg3().render(f5);
		getLeg4().render(f5);
		getTail().render(f5);
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

	public void setMane(ModelRenderer mane) {
		this.mane = mane;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		getHead().rotateAngleX = (float) Math.toRadians(f4);
		getHead().rotateAngleY = (float) Math.toRadians(f3);
		getMane().rotateAngleX = getHead().rotateAngleX;
		getMane().rotateAngleY = getHead().rotateAngleY;
		getLeg1().rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		getLeg2().rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		getLeg3().rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		getLeg4().rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		getTail().rotateAngleX = (float) Math.toRadians(-60.0);
		getTail().rotateAngleX += MathHelper.cos(f * 0.3f) * 0.5f * f1;
	}

	public void setTail(ModelRenderer tail) {
		this.tail = tail;
	}
}
