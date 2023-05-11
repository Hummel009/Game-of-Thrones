package got.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GOTModelWalrus extends ModelBase {
	ModelRenderer Body;
	ModelRenderer Neck;
	ModelRenderer Head;
	ModelRenderer Snout;
	ModelRenderer Tusk1;
	ModelRenderer Flipper1;
	ModelRenderer Flipper2;
	ModelRenderer Tail1;
	ModelRenderer Tusk2;
	ModelRenderer Tail2;
	ModelRenderer Tail3;
	ModelRenderer Flipper3;
	ModelRenderer Flipper4;

	public GOTModelWalrus() {
		textureWidth = 256;
		textureHeight = 64;
		Body = new ModelRenderer(this, 180, 0);
		Body.addBox(0.0f, 0.0f, 0.0f, 12, 11, 26);
		Body.setRotationPoint(-6.0f, 13.0f, -6.5f);
		Body.setTextureSize(256, 64);
		Body.mirror = true;
		setRotation(Body, 0.0f, 0.0f, 0.0f);
		Neck = new ModelRenderer(this, 211, 38);
		Neck.addBox(0.0f, 0.0f, 0.0f, 10, 8, 12);
		Neck.setRotationPoint(-4.533333f, 11.0f, -12.33333f);
		Neck.setTextureSize(256, 64);
		Neck.mirror = true;
		setRotation(Neck, -0.2974289f, 0.0f, 0.0f);
		Head = new ModelRenderer(this, 146, 12);
		Head.addBox(0.0f, 0.0f, 0.0f, 9, 8, 7);
		Head.setRotationPoint(-4.0f, 11.0f, -19.0f);
		Head.setTextureSize(256, 64);
		Head.mirror = true;
		setRotation(Head, 0.0f, 0.0f, 0.0f);
		Snout = new ModelRenderer(this, 154, 0);
		Snout.addBox(0.0f, 0.0f, 0.0f, 7, 6, 5);
		Snout.setRotationPoint(-3.0f, 13.46667f, -23.0f);
		Snout.setTextureSize(256, 64);
		Snout.mirror = true;
		setRotation(Snout, 0.4461433f, 0.0f, 0.0f);
		Tusk1 = new ModelRenderer(this, 142, 0);
		Tusk1.addBox(0.0f, 0.0f, 0.0f, 1, 5, 1);
		Tusk1.setRotationPoint(-2.5f, 16.0f, -21.0f);
		Tusk1.setTextureSize(256, 64);
		Tusk1.mirror = true;
		setRotation(Tusk1, 0.0f, 0.0f, 0.0f);
		Flipper1 = new ModelRenderer(this, 0, 0);
		Flipper1.addBox(0.0f, 0.0f, 0.0f, 9, 1, 6);
		Flipper1.setRotationPoint(5.0f, 20.0f, 0.0f);
		Flipper1.setTextureSize(256, 64);
		Flipper1.mirror = true;
		setRotation(Flipper1, 0.0f, -0.3490659f, 0.3141593f);
		Flipper2 = new ModelRenderer(this, 0, 8);
		Flipper2.addBox(0.0f, 0.0f, 0.0f, 9, 1, 6);
		Flipper2.setRotationPoint(-5.0f, 20.0f, 0.0f);
		Flipper2.setTextureSize(256, 64);
		Flipper2.mirror = true;
		setRotation(Flipper2, 0.0f, -0.3490659f, 2.658271f);
		Tail1 = new ModelRenderer(this, 96, 0);
		Tail1.addBox(0.0f, 0.0f, 0.0f, 10, 9, 12);
		Tail1.setRotationPoint(-5.0f, 14.0f, 19.0f);
		Tail1.setTextureSize(256, 64);
		Tail1.mirror = true;
		setRotation(Tail1, 0.0f, 0.0f, 0.0f);
		Tusk2 = new ModelRenderer(this, 148, 0);
		Tusk2.addBox(0.0f, 0.0f, 0.0f, 1, 5, 1);
		Tusk2.setRotationPoint(2.5f, 16.0f, -21.0f);
		Tusk2.setTextureSize(256, 64);
		Tusk2.mirror = true;
		setRotation(Tusk2, 0.0f, 0.0f, 0.0174533f);
		Tail2 = new ModelRenderer(this, 63, 0);
		Tail2.addBox(0.0f, 0.0f, 0.0f, 7, 7, 8);
		Tail2.setRotationPoint(-3.5f, 15.0f, 30.0f);
		Tail2.setTextureSize(256, 64);
		Tail2.mirror = true;
		setRotation(Tail2, -0.2602503f, 0.0f, 0.0f);
		Tail3 = new ModelRenderer(this, 183, 38);
		Tail3.addBox(0.0f, 0.0f, 0.0f, 5, 5, 8);
		Tail3.setRotationPoint(-2.5f, 18.0f, 36.0f);
		Tail3.setTextureSize(256, 64);
		Tail3.mirror = true;
		setRotation(Tail3, 0.0f, 0.0f, 0.0f);
		Flipper3 = new ModelRenderer(this, 147, 28);
		Flipper3.addBox(0.0f, 0.0f, 0.0f, 5, 1, 11);
		Flipper3.setRotationPoint(-4.0f, 20.4f, 39.0f);
		Flipper3.setTextureSize(256, 64);
		Flipper3.mirror = true;
		setRotation(Flipper3, -0.1487144f, -0.4089647f, 0.0f);
		Flipper4 = new ModelRenderer(this, 112, 22);
		Flipper4.addBox(0.0f, 0.0f, 0.0f, 5, 1, 11);
		Flipper4.setRotationPoint(-1.0f, 20.4f, 41.0f);
		Flipper4.setTextureSize(256, 64);
		Flipper4.mirror = true;
		setRotation(Flipper4, -0.1487144f, 0.3717861f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		Neck.render(f5);
		Head.render(f5);
		Snout.render(f5);
		Tusk1.render(f5);
		Flipper1.render(f5);
		Flipper2.render(f5);
		Tail1.render(f5);
		Tusk2.render(f5);
		Tail2.render(f5);
		Tail3.render(f5);
		Flipper3.render(f5);
		Flipper4.render(f5);
	}

	public void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}
}
